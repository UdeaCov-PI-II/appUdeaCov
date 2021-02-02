package co.edu.udea.udeacov.fragmentos.porteria

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import co.edu.udea.udeacov.network.error.ApiErrorHandler
import co.edu.udea.udeacov.network.error.ErrorConstants
import co.edu.udea.udeacov.network.response.PermissionCardResponseDto
import co.edu.udea.udeacov.network.udeaCovApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class FiltroUsuarioPorteriaViewModel: ViewModel() {
    private val viewModelJob = Job()
    private val coroutineScope  = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _permissionCardResponse = MutableLiveData<List<PermissionCardResponseDto>?>()
    val permissionCardResponse: LiveData<List<PermissionCardResponseDto>?>
        get() = _permissionCardResponse

    private val _responseError = MutableLiveData<String?>()
    val responseError: LiveData<String?>
        get() = _responseError


    fun getPermissionBydocTypeAndDocNumber(docType: String, docNumber: String){
        coroutineScope.launch {
            try{
                _permissionCardResponse.value = udeaCovApiService.permissionService.getPermissionBydocTypeAndDocNumber(docType, docNumber, true, true).await()
            }catch (e : Exception) {
                _responseError.value = ApiErrorHandler.getErrorMessage(e, ErrorConstants.DEFAULT_ERROR_MESSAGE_PERMISSIONS)
            }
        }
    }



    fun navToDetailFragmentIsDone(){
        _permissionCardResponse.value = null
    }

}