package co.edu.udea.udeacov.network.request

data class ApprovalRequestDto(
    var userId : String = "",
    var approved : Boolean = false,
    var reason : String = "",
    var action : String = ""
)