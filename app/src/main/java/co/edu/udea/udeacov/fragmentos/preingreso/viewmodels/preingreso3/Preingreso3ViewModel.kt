package co.edu.udea.udeacov.fragmentos.preingreso.viewmodels.preingreso3

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import co.edu.udea.udeacov.network.error.ApiErrorHandler
import co.edu.udea.udeacov.network.error.ErrorConstants
import co.edu.udea.udeacov.network.response.LocationResponseDTO
import co.edu.udea.udeacov.network.udeaCovApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class Preingreso3ViewModel : ViewModel() {
    private val viewModelJob = Job()
    private val coroutineScope  = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _locationsResponse = MutableLiveData<List<LocationResponseDTO>?>()

    val locationsResponse: LiveData<List<String>>
        get() = Transformations.map(_locationsResponse) {
            it?.map { element ->
                element.name
            }
        }

    private val _responseError = MutableLiveData<String?>()

    val responseError: LiveData<String?>
        get() = _responseError


    fun getLocations(){
        coroutineScope.launch {
            try{
                _locationsResponse.value = udeaCovApiService.locationService.getLocations().await()
            }catch (e : Exception) {
                _responseError.value = ApiErrorHandler.getErrorMessage(e, ErrorConstants.DEFAULT_ERROR_MESSAGE_LOCATIONS)
            }
        }
    }

}