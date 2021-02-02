package co.edu.udea.udeacov.fragmentos.solicitud_permiso.viewmodels.solicitudingreso2

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import co.edu.udea.udeacov.network.error.ApiErrorHandler
import co.edu.udea.udeacov.network.error.ErrorConstants
import co.edu.udea.udeacov.network.request.PermissionRequestDto
import co.edu.udea.udeacov.network.response.CreatePermissionResponseDto
import co.edu.udea.udeacov.network.response.LocationResponseDTO
import co.edu.udea.udeacov.network.udeaCovApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class Solicitud2ViewModel: ViewModel() {
    //Para usar corutinas
    private val viewModelJob = Job()
    private val coroutineScope  = CoroutineScope(viewModelJob + Dispatchers.Main)


    private val _locationsResponse = MutableLiveData<List<LocationResponseDTO>?>()
    val locationNameSelected = MutableLiveData<String?>()

    private val _responseError = MutableLiveData<String?>()
    val responseError: LiveData<String?>
        get() = _responseError

    private val _createpermissionResponse = MutableLiveData<CreatePermissionResponseDto?>()

    val createpermissionResponse: LiveData<CreatePermissionResponseDto?>
        get() = _createpermissionResponse


    //Retorno el id de las seccionales(location), segun el nombre que recibo del usuario
    val locationIdSelected: LiveData<String?> = Transformations.map(locationNameSelected) {
        it?.let {
            _locationsResponse.value?.stream()?.filter{location ->
                location.name == it
            }?.findAny()?.get()?.id
        }
    }

    //Lista de las seccionales(location) que se le mostraran al usuario en la vista
    val locationsResponse: LiveData<List<String>>
        get() = Transformations.map(_locationsResponse) {
            it?.map { element ->
                element.name
            }
        }

    //Metodo que consume el servicio para retornar las seccionales(locations) de la BD
    fun getLocations(){
        coroutineScope.launch {
            try{
                _locationsResponse.value = udeaCovApiService.locationService.getLocations().await()
            }catch (e : Exception) {
                _responseError.value = ApiErrorHandler.getErrorMessage(e, ErrorConstants.DEFAULT_ERROR_MESSAGE_LOCATIONS)
            }
        }
    }

    //Método para consumir el servicio de crear una solicitud de permiso
    fun createPermission(permissionRequest: PermissionRequestDto){
        coroutineScope.launch {
            try{
                _createpermissionResponse.value = udeaCovApiService.permissionService.createPermission(true, permissionRequest).await()
            }catch (e : Exception) {
                _responseError.value = ApiErrorHandler.getErrorMessage(e, ErrorConstants.DEFAULT_ERROR_MESSAGE_PERMISSIONS)

            }
        }
    }

    //metodo para controlar el evento de mostrar error
    fun showErrorIsCompleted(){
        _responseError.value = null
    }

    fun navigationIsCompleted(){
        _createpermissionResponse.value = null
    }

}