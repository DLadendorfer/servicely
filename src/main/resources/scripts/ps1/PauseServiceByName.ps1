<#
.SYNOPSIS
    Pauses a Windows service by its internal name.

.DESCRIPTION
    - Accepts an internal service name as a parameter.
    - Checks if the service exists.
    - Pauses the service if it supports pausing and is currently running.
    - Returns a JSON response indicating success or failure.

.PARAMETER internalName
    The internal (system) name of the service to pause.
    Example: "wuauserv" (Windows Update Service)

.EXAMPLE
    Pause the Windows Update service:
        .\PauseServiceByName.ps1 -internalName "wuauserv"

    Example output:
    {
        "Service": "wuauserv",
        "Status": "Paused"
    }

.NOTES
    - Requires administrative privileges to pause certain services.
    - Not all services support pausing; an error message will be returned if the service does not support it.
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

# Check if the service supports pausing
if (-not ($service.CanPauseAndContinue))
{
    Write-Output "{ `"Error`": `"Service '$internalName' does not support pausing`" }"
    exit 1
}

# Check if the service is already paused
if ($service.Status -eq 'Paused')
{
    Write-Output "{ `"Service`": `"$internalName`", `"Status`": `"Already Paused`" }"
    exit 0
}

# Check if the service is running before attempting to pause
if ($service.Status -ne 'Running')
{
    Write-Output "{ `"Error`": `"Service '$internalName' is not running and cannot be paused`" }"
    exit 1
}

# Attempt to pause the service
try
{
    Suspend-Service -Name $internalName -ErrorAction Stop
    # Verify if the service paused successfully
    $service = Get-Service -Name $internalName
    Write-Output "{ `"Service`": `"$internalName`", `"Status`": `"$( $service.Status )`" }"
}
catch
{
    # Return an error message in JSON format
    Write-Output "{ `"Error`": `"Failed to pause service '$internalName'`", `"Reason`": `"$( $_.Exception.Message )`" }"
    exit 1
}
