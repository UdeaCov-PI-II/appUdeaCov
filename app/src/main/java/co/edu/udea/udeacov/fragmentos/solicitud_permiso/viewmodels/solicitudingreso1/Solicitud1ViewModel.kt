package co.edu.udea.udeacov.fragmentos.solicitud_permiso.viewmodels.solicitudingreso1

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import co.edu.udea.udeacov.network.error.ApiErrorHandler
import co.edu.udea.udeacov.network.error.ErrorConstants
import co.edu.udea.udeacov.network.response.UnitResponseDto
import co.edu.udea.udeacov.network.udeaCovApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class Solicitud1ViewModel: ViewModel() {
    private val viewModelJob = Job()
    private val coroutineScope  = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _unitsResponse = MutableLiveData<List<UnitResponseDto>?>()

    val unitNameSelected = MutableLiveData<String?>()

    private val _responseError = MutableLiveData<String?>()
    val responseError: LiveData<String?>
        get() = _responseError

    //Obtengo la unidad academica seleccionada por el usuario, la busco por el nombre y retorno el objeto completo asociado a ese nombre
    val unitSelected: LiveData<UnitResponseDto?> = Transformations.map(unitNameSelected) {
        it?.let {
            _unitsResponse.value?.stream()?.filter{unit ->
                unit.name == it
            }?.findAny()?.get()
        }
    }

    // Retorno una lista con el nombre de las unidades academicas, se muestra en la vista del usuario
    val unitsResponse: LiveData<List<String>>
        get() = Transformations.map(_unitsResponse) {
            it?.map { element ->
                element.name
            }
        }

    //se encarga de consumir el servicio para extraer la información de las unidades academicas
    fun getUnits(){
        coroutineScope.launch {
            try{
                _unitsResponse.value = udeaCovApiService.unitService.getUnits().await()
            }catch (e : Exception) {
                _responseError.value = ApiErrorHandler.getErrorMessage(e, ErrorConstants.DEFAULT_ERROR_MESSAGE_UNITS)
            }
        }
    }


}