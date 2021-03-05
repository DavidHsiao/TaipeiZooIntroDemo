package android.example.com.taipeizoointrodemo.overview

import android.example.com.taipeizoointrodemo.networkApi.MarsProperty
import android.example.com.taipeizoointrodemo.networkApi.TaipeiZooAreaApi
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
//        TaipeiZooAreaApi.retrofitService.getProperties("resourceAquire").enqueue( object: Callback<GetResult> {
//            override fun onFailure(call: Call<GetResult>, t: Throwable) {
//                _response.value = "Failure: " + t.message
//            }
//
//            override fun onResponse(call: Call<GetResult>, response: Response<GetResult>) {
//                _response.value = "Success: ${response.body()} Taipei Zoo Area retrieved"
//                var qq = 0
//                qq++
//            }
//        })

        //////////////////////////////////////////////////////////////////////////////////////////////////////
//        TaipeiZooAreaApi.retrofitService.fetchPirateList().enqueue(object : Callback<PirateResponse> {
//            override fun onResponse(
//                call: Call<PirateResponse>,
//                response: Response<PirateResponse>
//            ) {
//                Log.d("QQ", "response: ${response.body().toString()}")
//            }
//
//            override fun onFailure(call: Call<PirateResponse>, t: Throwable) {
//                Log.d("QQ", "error: ${t.message}" ?: "Get some error")
//            }
//        })

        //////////////////////////////////////////////////////////////////////////////////////////////////////

        TaipeiZooAreaApi.retrofitService.getProperties().enqueue(object: Callback<List<MarsProperty>> {
            override fun onFailure(call: Call<List<MarsProperty>>, t: Throwable) {
                _response.value = "Failure: " + t.message
            }

            override fun onResponse(call: Call<List<MarsProperty>>, response: Response<List<MarsProperty>>) {
                _response.value = "Success: ${response.body()?.size} Mars properties retrieved"
            }
        })

    }

}
