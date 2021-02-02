package co.edu.udea.udeacov.fragmentos.lista_solicitudes.viewmodels.viewmodels

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.room.util.StringUtil
import co.edu.udea.udeacov.network.error.ApiErrorHandler
import co.edu.udea.udeacov.network.error.ErrorConstants
import co.edu.udea.udeacov.network.request.ApprovalRequestDto
import co.edu.udea.udeacov.network.request.CreateEntranceRequestDto
import co.edu.udea.udeacov.network.request.EntranceRequestDto
import co.edu.udea.udeacov.network.response.CreateEntranceResponseDto
import co.edu.udea.udeacov.network.response.CreatePermissionResponseDto
import co.edu.udea.udeacov.network.response.PermissionResponseDto
import co.edu.udea.udeacov.network.response.RoleResponseDto
import co.edu.udea.udeacov.network.udeaCovApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class DetalleSolicitudViewModel: ViewModel() {

    //corutinas
    private val viewModelJob = Job()
    private val coroutineScope  = CoroutineScope(viewModelJob + Dispatchers.Main)


    val permissionResponse: LiveData<PermissionResponseDto?>
        get() = _permissionResponse
    private val _permissionResponse = MutableLiveData<PermissionResponseDto?>()

    private val _responseError = MutableLiveData<String?>()
    val responseError: LiveData<String?>
        get() = _responseError

    val entranceResponse: LiveData<CreateEntranceResponseDto?>
        get() = _entranceResponse
    private val _entranceResponse = MutableLiveData<CreateEntranceResponseDto?>()

    private val _roleResponse = MutableLiveData<List<RoleResponseDto>?>()
    val roleResponse: LiveData<List<RoleResponseDto>?>
        get() = _roleResponse

    private val _approvalResponse = MutableLiveData<CreatePermissionResponseDto?>()

    val approvalResponse: LiveData<CreatePermissionResponseDto?>
        get() = _approvalResponse

    val role = MutableLiveData<String?>()

    val isUser : LiveData<Boolean> = Transformations.map(role){
        it != null && "ROLE_USER" == it
    }

    val isHealthAdmin : LiveData<Boolean> = Transformations.map(role){
        it != null && "ROLE_HEALTH_ADMIN" == it
    }

    val isSecurityRole : LiveData<Int> = Transformations.map(role){
        if(it != null && it == "ROLE_SECURITY" ){
             View.VISIBLE
        }else{
            View.GONE
        }
    }

    val entryTextBtn : LiveData<String> = Transformations.map(permissionResponse){
        if(it != null && it.entrance == null ){
           "Registrar entrada"
        }else{
            "Registrar salida"
        }
    }

    val enableEntryBtn : LiveData<Boolean> = Transformations.map(permissionResponse){
       permissionResponse.value != null && (permissionResponse.value?.entrance == null ||
               permissionResponse.value?.entrance?.departureTimeStr.isNullOrBlank() )
    }


    fun getPermissionById(permissionId: String) {
        coroutineScope.launch {
            try{
                _permissionResponse.value = udeaCovApiService.permissionService.getPermissionById(permissionId,true).await()
            }catch (e : Exception) {
                _responseError.value = ApiErrorHandler.getErrorMessage(e, ErrorConstants.DEFAULT_ERROR_MESSAGE_PERMISSIONS)

            }
        }

    }

    fun createEntrance(entranceRequestDto: CreateEntranceRequestDto){
        coroutineScope.launch {
            try{
                _entranceResponse.value = udeaCovApiService.entranceService.createEntrance(true, entranceRequestDto).await()
            }catch (e : Exception) {
                _responseError.value = ApiErrorHandler.getErrorMessage(e, ErrorConstants.DEFAULT_ERROR_MESSAGE_CREATE_ENTRANCE)

            }
        }
    }

    fun getApproverRoles(){
        coroutineScope.launch {
            try{
                _roleResponse.value = udeaCovApiService.roleService.getApproversRoles().await()
            }catch (e : Exception) {
                _responseError.value = ApiErrorHandler.getErrorMessage(e, ErrorConstants.DEFAULT_ERROR_MESSAGE_CREATE_ENTRANCE)

            }
        }
    }

    fun createApproval(id : String, requestDto: ApprovalRequestDto){
        coroutineScope.launch {
            try{
                _approvalResponse.value = udeaCovApiService.permissionService.createApproval(id,requestDto,false).await()
            }catch (e : Exception) {
                _responseError.value = ApiErrorHandler.getErrorMessage(e, ErrorConstants.DEFAULT_ERROR_MESSAGE_CREATE_ENTRANCE)

            }
        }
    }

    fun navigateToListIsDone(){
        _approvalResponse.value = null
        _roleResponse.value = null
        _entranceResponse.value = null
    }


}