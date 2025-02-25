<#
.SYNOPSIS
    Stops a Windows service by its internal name.

.DESCRIPTION
    - Accepts an internal service name as a parameter.
    - Checks if the service exists.
    - Stops the service if it is running.
    - Returns a JSON response indicating success or failure.

.PARAMETER internalName
    The internal (system) name of the service to stop.
    Example: "wuauserv" (Windows Update Service)

.EXAMPLE
    Stop the Windows Update service:
        .\StopServiceByName.ps1 -internalName "wuauserv"

    Example output:
    {
        "Service": "wuauserv",
        "Status": "Stopped"
    }

.NOTES
    - Requires administrative privileges to stop certain services.
    - If the service is already stopped, it will not attempt to stop it again.
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

# Check if the service is already stopped
if ($service.Status -eq 'Stopped')
{
    Write-Output "{ `"Service`": `"$internalName`", `"Status`": `"Already Stopped`" }"
    exit 0
}

# Attempt to stop the service
try
{
    Stop-Service -Name $internalName -Force -ErrorAction Stop
    # Verify if the service stopped successfully
    $service = Get-Service -Name $internalName
    Write-Output "{ `"Service`": `"$internalName`", `"Status`": `"$( $service.Status )`" }"
}
catch
{
    # Return an error message in JSON format
    Write-Output "{ `"Error`": `"Failed to stop service '$internalName'`", `"Reason`": `"$( $_.Exception.Message )`" }"
    exit 1
}
