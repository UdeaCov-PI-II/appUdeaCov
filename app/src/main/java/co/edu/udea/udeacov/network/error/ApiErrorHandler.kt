package co.edu.udea.udeacov.network.error

import co.edu.udea.udeacov.network.udeaCovApiService
import com.squareup.moshi.JsonAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class ApiErrorHandler{
    companion object{
        suspend fun getErrorMessage(e : Exception, defaultErrorMessage : String) : String {
            var responseError : ResponseErrorDto? = null
            if(e is HttpException){
                e.response()?.errorBody()?.let{ response ->
                    responseError = withContext(Dispatchers.IO){
                        val jsonAdapter: JsonAdapter<ResponseErrorDto> = udeaCovApiService
                            .converter.adapter(ResponseErrorDto::class.java)
                        jsonAdapter.fromJson(response.source())
                    }
                }
            }
            return responseError?.message ?: defaultErrorMessage
        }

    }

}