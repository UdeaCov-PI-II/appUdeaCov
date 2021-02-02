package co.edu.udea.udeacov.fragmentos.lista_solicitudes.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import co.edu.udea.udeacov.network.error.ApiErrorHandler
import co.edu.udea.udeacov.network.error.ErrorConstants
import co.edu.udea.udeacov.network.response.PermissionResponseDto
import co.edu.udea.udeacov.network.udeaCovApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ListaSolicitudViewModel: ViewModel() {

    //corutinas
    private val viewModelJob = Job()
    private val coroutineScope  = CoroutineScope(viewModelJob + Dispatchers.Main)


    val permissionResponse: LiveData<PermissionResponseDto?>
        get() = _permissionResponse

    private val _permissionResponse = MutableLiveData<PermissionResponseDto?>()

    private val _responseError = MutableLiveData<String?>()
    val responseError: LiveData<String?>
        get() = _responseError



    fun getList(permissionId: String) {
        coroutineScope.launch {
            try{
                _permissionResponse.value = udeaCovApiService.permissionService.getList().await()
            }catch (e : Exception) {
                _responseError.value = ApiErrorHandler.getErrorMessage(e, ErrorConstants.DEFAULT_ERROR_MESSAGE_PERMISSIONS)

            }
        }

    }
}