package co.edu.udea.udeacov.network.request


data class CreateEntranceRequestDto (
    var permissionId: String = "",
    var temperature: String = "",
    var entry: Boolean = true
)