package android.example.com.taipeizoointrodemo.areaDetail

import android.app.Activity
import android.app.Application
import android.example.com.taipeizoointrodemo.R
import android.example.com.taipeizoointrodemo.constant.ApiStatus
import android.example.com.taipeizoointrodemo.networkApi.AreaPlantApi
import android.example.com.taipeizoointrodemo.networkApi.EachAreaResults
import android.example.com.taipeizoointrodemo.networkApi.PlantResults
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class AreaDetailViewModel(eachAreaResults: EachAreaResults, app: Application, activity: Activity) : AndroidViewModel(app) {

    private val TAG = AreaDetailViewModel::class.java.simpleName

    // The internal MutableLiveData String that stores the status of the most recent request
    private val _status = MutableLiveData<ApiStatus>()

    // The external immutable LiveData for the request status String
    val status: LiveData<ApiStatus>
        get() = _status

    // Internally, we use a MutableLiveData, because we will be updating the MarsProperty with new values
    private val _plant = MutableLiveData<List<PlantResults>>()

    // The external LiveData interface to the property is immutable, so only this class can modify
    val plant: LiveData<List<PlantResults>>
        get() = _plant

    // Internally, we use a MutableLiveData to handle navigation to the selected property
    private val _navigateToPlantDetail = MutableLiveData<PlantResults>()

    // The external immutable LiveData for the navigation property
    val navigateToPlantDetail: LiveData<PlantResults>
        get() = _navigateToPlantDetail

    private val _app = app
    private val _selectedArea = MutableLiveData<EachAreaResults>()

    val selectedArea: LiveData<EachAreaResults>
        get() = _selectedArea

    // 因為使用Coroutine, 所以要建立Job
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)


    init {
        _selectedArea.value = eachAreaResults
        activity.title = selectedArea.value?.E_Name
        getTaipeiZooPlant()
    }

    // The displayPropertyPrice formatted Transformation Map LiveData, which displays the sale
    // or rental price.
    private val _displayMemo = MutableLiveData<String>()
    val displayMemo: LiveData<String>
        get() {
            if (selectedArea.value!!.haveMomoInfo) {
                _displayMemo.value = _selectedArea.value!!.E_Memo
            } else {
                _displayMemo.value = _app.applicationContext.getString(R.string.no_memo_info)
            }
            return _displayMemo
        }

    /**
     * Sets the value of the status LiveData to the Mars API status.
     */
    private fun getTaipeiZooPlant() {

        coroutineScope.launch {
            // Get the Deferred object for our Retrofit request
            var getTaipeiZooPlantDeferred = AreaPlantApi.retrofitService.getPlants(_selectedArea.value!!.E_Name!!)

            // 用try catch一樣可以達到先前callback方式有的error handling
            try {
                _status.value = ApiStatus.LOADING
                // 等到收到request才繼續執行，await是non-blocking, 目前執行在main thread也OK
                // Await the completion of our Retrofit request
                var plantResult = getTaipeiZooPlantDeferred.await()
                _status.value = ApiStatus.DONE
                Log.d(TAG, "getTaipeiZooPlant Success, result count =  ${plantResult.result.count}")

                // 將第一筆result資料放入
                if (plantResult.result.count > 0) {
                    _plant.value = plantResult.result.results
                }
            } catch (e: Exception) {
                _status.value = ApiStatus.ERROR
                _plant.value = ArrayList()
                Log.d(TAG, "getTaipeiZooPlant Error, MSG =  ${e.toString()}")
            }
        }

//                    // Call the MarsApi to enqueue the Retrofit request, implementing the callbacks
//            // 用enqueue是為了在start的時候是在background thread
//        AreaPlantApi.retrofitService.getPlants().enqueue(object : Callback<GetPlantResult> {
//                override fun onFailure(call: Call<GetPlantResult>, t: Throwable) {
//                    Log.d(TAG, "Failure: " + t.message)
//                }
//
//                override fun onResponse(call: Call<GetPlantResult>, response: Response<GetPlantResult>) {
//                    Log.d(TAG, "Success: ${response.body()} Taipei Zoo Area retrieved")
//                }
//            })
////        _response.value = "Set the Mars API Response here!"

    }

    // 當Fragment消失, ViewModel is destroyed, 因此Loading data的工作需要停止
    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "onCleared()")
        viewModelJob.cancel()
    }

    /**
     * When the property is clicked, set the [_navigateToSelectedProperty] [MutableLiveData]
     * @param marsProperty The [MarsProperty] that was clicked on.
     */
    fun displayPlantDetails(plantResults: PlantResults) {
        _navigateToPlantDetail.value = plantResults
    }

    /**
     * After the navigation has taken place, make sure navigateToSelectedProperty is set to null
     */
    fun displayPlantDetailsComplete() {
        _navigateToPlantDetail.value = null
    }



}
