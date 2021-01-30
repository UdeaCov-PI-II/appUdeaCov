package co.edu.udea.udeacov.network.response

data class ManagerResponseDto (
    var id : String? ,
    var documentType: String,
    var documentNumber: String,
    var fullName: String,
    var email: String
)