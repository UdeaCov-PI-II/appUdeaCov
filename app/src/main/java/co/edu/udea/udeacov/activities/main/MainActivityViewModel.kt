package co.edu.udea.udeacov.activities.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import co.edu.udea.udeacov.network.error.ApiErrorHandler
import co.edu.udea.udeacov.network.error.ErrorConstants
import co.edu.udea.udeacov.network.request.AuthRequestDto
import co.edu.udea.udeacov.network.response.AuthResponseDto
import co.edu.udea.udeacov.network.udeaCovApiService
import kotlinx.coroutines.*


class MainActivityViewModel : ViewModel() {
    private val viewModelJob = Job()
    private val coroutineScope  = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _authResponse = MutableLiveData<AuthResponseDto?>()

    val authResponse: LiveData<AuthResponseDto?>
        get() = _authResponse


    private val _responseError = MutableLiveData<String?>()

    val responseError: LiveData<String?>
        get() = _responseError


    fun signIn(username : String , password : String){
        coroutineScope.launch {
            try{
                val authRequest = AuthRequestDto(username, password)
                _authResponse.value = udeaCovApiService.authService.authenticate(true,authRequest).await()
            }catch (e : Exception) {
                _responseError.value = ApiErrorHandler.getErrorMessage(e, ErrorConstants.DEFAULT_ERROR_MESSAGE_AUTH)
            }
        }
    }

    fun signInIsCompleted(){
        _authResponse.value = null
    }

    fun showErrorIsCompleted(){
        _responseError.value = null
    }



}