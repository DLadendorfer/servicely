<#
.SYNOPSIS
    This script retrieves a list of all services on the system and converts the output to JSON format.

.DESCRIPTION
    - Uses the `Get-Service` cmdlet to fetch details of all system services.
    - Converts the output to JSON using `ConvertTo-Json`.
    - Optionally, allows formatting adjustments like depth levels.
    - Outputs JSON to the console for easy parsing by other programs.

.EXAMPLE
Running the script:
    .\GetServices.ps1

Example output:
[
    {
        "Status": 4,
        "Name": "AdobeARMservice",
        "DisplayName": "Adobe Acrobat Update Service"
    },
    {
        "Status": 4,
        "Name": "W32Time",
        "DisplayName": "Windows Time"
    }
]

.NOTES
    - The JSON output can be redirected to a file:
        .\GetServices.ps1 > services.json
    - The output is useful for automation, logging, or integration with other tools.
#>

# Retrieve all services on the system
$services = Get-Service

# Convert the service objects into JSON format
# The `-Depth` parameter is optional but helps with nested properties
$jsonOutput = $services | ConvertTo-Json -Depth 2

# Print the JSON output to the console
Write-Output $jsonOutput
