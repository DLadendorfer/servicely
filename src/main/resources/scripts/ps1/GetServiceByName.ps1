<#
.SYNOPSIS
    Retrieves details of a specific Windows service by its internal name and outputs it in JSON format.

.DESCRIPTION
    - Accepts an internal service name as a parameter.
    - Uses `Get-Service` to retrieve service details.
    - Filters the services using `Where-Object` to match the specified name.
    - Converts the result to JSON format for easy integration with other applications.
    - Includes basic error handling in case the service is not found.

.PARAMETER internalName
    The internal (system) name of the service to retrieve.
    Example: "wuauserv" (Windows Update Service)

.EXAMPLE
    Retrieve the Windows Update service:
        .\GetServiceByName.ps1 -internalName "wuauserv"

    Example output:
    {
        "Status": 4,
        "Name": "wuauserv",
        "DisplayName": "Windows Update"
    }

.NOTES
    - The JSON output can be redirected to a file:
        .\GetServiceByName.ps1 -internalName "wuauserv" > service.json
    - If the service is not found, an appropriate message is returned.
#>

# Define a parameter for the internal service name
param(
    [Parameter(Mandatory = $true, HelpMessage = "Enter the internal service name (e.g., 'wuauserv')")]
    [string] $internalName
)

# Retrieve the service that matches the specified internal name
$service = Get-Service | Where-Object { $_.Name -eq $internalName }

# Check if the service was found
if ($service)
{
    # Convert the service object to JSON format
    $jsonOutput = $service | ConvertTo-Json -Depth 2
    Write-Output $jsonOutput
}
else
{
    # Output a JSON-formatted error message if the service does not exist
    Write-Output "{ `"Error`": `"Service '$internalName' not found`" }"
}
