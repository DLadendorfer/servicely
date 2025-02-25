<#
.SYNOPSIS
    Restarts a Windows service by its internal name.

.DESCRIPTION
    - Accepts an internal service name as a parameter.
    - Checks if the service exists.
    - Stops the service if it is running.
    - Starts the service after stopping.
    - Returns a JSON response indicating success or failure.

.PARAMETER internalName
    The internal (system) name of the service to restart.
    Example: "wuauserv" (Windows Update Service)

.EXAMPLE
    Restart the Windows Update service:
        .\RestartServiceByName.ps1 -internalName "wuauserv"

    Example output:
    {
        "Service": "wuauserv",
        "Status": "Restarted"
    }

.NOTES
    - Requires administrative privileges to restart certain services.
    - If the service is already stopped, it will simply start it.
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

# Attempt to stop the service if it's running
if ($service.Status -eq 'Running')
{
    try
    {
        Stop-Service -Name $internalName -Force -ErrorAction Stop
        Start-Sleep -Seconds 2  # Allow some time for the service to stop
    }
    catch
    {
        Write-Output "{ `"Error`": `"Failed to stop service '$internalName'`", `"Reason`": `"$( $_.Exception.Message )`" }"
        exit 1
    }
}

# Attempt to start the service
try
{
    Start-Service -Name $internalName -ErrorAction Stop
    # Verify if the service started successfully
    $service = Get-Service -Name $internalName
    Write-Output "{ `"Service`": `"$internalName`", `"Status`": `"Restarted`"}"
}
catch
{
    Write-Output "{ `"Error`": `"Failed to restart service '$internalName'`", `"Reason`": `"$( $_.Exception.Message )`"
}"
    exit 1
}
