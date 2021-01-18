package co.edu.udea.udeacov.network.error

data class ResponseErrorDto (
    val timestamp: String = "",
    val status : Int = 0,
    val error : String = "",
    var message : String = "",
    val path : String = ""
)