package android.example.com.taipeizoointrodemo.overview

import android.example.com.taipeizoointrodemo.networkApi.TaipeiZooAeraApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OverviewViewModel : ViewModel() {

    // The internal MutableLiveData String that stores the status of the most recent request
    private val _response = MutableLiveData<String>()

    // The external immutable LiveData for the request status String
    val response: LiveData<String>
        get() = _response

    /**
     * Call getMarsRealEstateProperties() on init so we can display status immediately.
     */
    init {
        getTaipeiZooArea()
    }

    /**
     * Sets the value of the status LiveData to the Mars API status.
     */
    private fun getTaipeiZooArea() {

        // Call the MarsApi to enqueue the Retrofit request, implementing the callbacks
        // 用enqueue是為了在start的時候是在background thread
        TaipeiZooAeraApi.retrofitService.getProperties("resourceAquire").enqueue( object: Callback<String> {
            override fun onFailure(call: Call<String>, t: Throwable) {
                _response.value = "Failure: " + t.message
            }

            override fun onResponse(call: Call<String>, response: Response<String>) {
                _response.value = response.body()
                var qq = 0
                qq++
            }
        })
    }

}
