<#
.SYNOPSIS
    Starts a Windows service by its internal name.

.DESCRIPTION
    - Accepts an internal service name as a parameter.
    - Checks if the service exists.
    - Starts the service if it is stopped.
    - Returns a JSON response indicating success or failure.

.PARAMETER internalName
    The internal (system) name of the service to start.
    Example: "wuauserv" (Windows Update Service)

.EXAMPLE
    Start the Windows Update service:
        .\StartServiceByName.ps1 -internalName "wuauserv"

    Example output:
    {
        "Service": "wuauserv",
        "Status": "Running"
    }

.NOTES
    - Requires administrative privileges to start certain services.
    - If the service is already running, it will not attempt to restart it.
#>

# Define a parameter for the service name
param(
    [Parameter(Mandatory = $true, HelpMessage = "Enter the internal service name (e.g., 'wuauserv')")]
    [string] $internalName
)

# Retrieve the service
$service = Get-Service -Name $internalName -ErrorAction SilentlyContinue

# Check if the service exists
if ($null -eq $service)
{
    Write-Output "{ `"Error`": `"Service '$internalName' not found`" }"
    exit 1
}

# Check if the service is already running
if ($service.Status -eq 'Running')
{
    Write-Output "{ `"Service`": `"$internalName`", `"Status`": `"Already Running`" }"
    exit 0
}

# Attempt to start the service
try
{
    Start-Service -Name $internalName -ErrorAction Stop
    # Verify if the service started successfully
    $service = Get-Service -Name $internalName
    Write-Output "{ `"Service`": `"$internalName`", `"Status`": `"$( $service.Status )`" }"
}
catch
{
    # Return an error message in JSON format
    Write-Output "{ `"Error`": `"Failed to start service '$internalName'`", `"Reason`": `"$( $_.Exception.Message )`" }"
    exit 1
}
