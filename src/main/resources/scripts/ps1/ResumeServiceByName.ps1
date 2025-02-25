<#
.SYNOPSIS
    Resumes a paused Windows service by its internal name.

.DESCRIPTION
    - Accepts an internal service name as a parameter.
    - Checks if the service exists.
    - Resumes the service if it is currently paused and supports resuming.
    - Returns a JSON response indicating success or failure.

.PARAMETER internalName
    The internal (system) name of the service to resume.
    Example: "wuauserv" (Windows Update Service)

.EXAMPLE
    Resume the Windows Update service:
        .\ResumeServiceByName.ps1 -internalName "wuauserv"

    Example output:
    {
        "Service": "wuauserv",
        "Status": "Running"
    }

.NOTES
    - Requires administrative privileges to resume certain services.
    - Not all services support pausing and resuming.
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

# Check if the service supports pausing and resuming
if (-not ($service.CanPauseAndContinue))
{
    Write-Output "{ `"Error`": `"Service '$internalName' does not support pausing and resuming`" }"
    exit 1
}

# Check if the service is already running
if ($service.Status -eq 'Running')
{
    Write-Output "{ `"Service`": `"$internalName`", `"Status`": `"Already Running`" }"
    exit 0
}

# Check if the service is paused before attempting to resume
if ($service.Status -ne 'Paused')
{
    Write-Output "{ `"Error`": `"Service '$internalName' is not paused and cannot be resumed`" }"
    exit 1
}

# Attempt to resume the service
try
{
    Resume-Service -Name $internalName -ErrorAction Stop
    # Verify if the service resumed successfully
    $service = Get-Service -Name $internalName
    Write-Output "{ `"Service`": `"$internalName`", `"Status`": `"$( $service.Status )`" }"
}
catch
{
    # Return an error message in JSON format
    Write-Output "{ `"Error`": `"Failed to resume service '$internalName'`", `"Reason`": `"$( $_.Exception.Message )`" }"
    exit 1
}
