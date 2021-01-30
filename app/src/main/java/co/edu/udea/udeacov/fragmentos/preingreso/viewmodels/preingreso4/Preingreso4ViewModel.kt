package co.edu.udea.udeacov.fragmentos.preingreso.viewmodels.preingreso4

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import co.edu.udea.udeacov.network.error.ApiErrorHandler
import co.edu.udea.udeacov.network.error.ErrorConstants
import co.edu.udea.udeacov.network.request.SignUpRequestDto
import co.edu.udea.udeacov.network.response.SignUpResponseDto
import co.edu.udea.udeacov.network.udeaCovApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class Preingreso4ViewModel: ViewModel() {
    private val viewModelJob = Job()
    private val coroutineScope  = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _signUpResponse = MutableLiveData<SignUpResponseDto?>()
    private val _responseError = MutableLiveData<String?>()

    val responseError: LiveData<String?>
        get() = _responseError

    val signUpResponse: LiveData<SignUpResponseDto?>
        get() = _signUpResponse

    fun signup(signUpRequest: SignUpRequestDto){
        coroutineScope.launch {
            try{
                _signUpResponse.value = udeaCovApiService.authService.signup(true, signUpRequest).await()
            }catch (e : Exception) {
                _responseError.value = ApiErrorHandler.getErrorMessage(e, ErrorConstants.DEFAULT_ERROR_MESSAGE_SIGN_IN)

            }
        }
    }

    fun showErrorIsCompleted(){
        _responseError.value = null
    }

    fun navigationIsCompleted(){
        _signUpResponse.value = null
    }


}