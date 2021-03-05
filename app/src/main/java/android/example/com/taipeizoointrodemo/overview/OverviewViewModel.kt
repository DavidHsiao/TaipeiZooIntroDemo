package android.example.com.taipeizoointrodemo.overview

import android.example.com.taipeizoointrodemo.networkApi.TaipeiZooAreaApi
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class OverviewViewModel : ViewModel() {

    private val TAG = OverviewViewModel::class.java.simpleName

    // The internal MutableLiveData String that stores the status of the most recent request
    private val _response = MutableLiveData<String>()

    // The external immutable LiveData for the request status String
    val response: LiveData<String>
        get() = _response

    // 因為使用Coroutine, 所以要建立Job
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

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

        coroutineScope.launch {
            // Get the Deferred object for our Retrofit request
            var getTaipeiZooAreaDeferred = TaipeiZooAreaApi.retrofitService.getProperties("resourceAquire")

            // 用try catch一樣可以達到先前callback方式有的error handling
            try {
                // 等到收到request才繼續執行，await是non-blocking, 目前執行在main thread也OK
                // Await the completion of our Retrofit request
                var listResult = getTaipeiZooAreaDeferred.await()
                _response.value = "Success: ${listResult.result.count} Mars properties retrieved"
                Log.d(TAG, "Success: ${listResult.result.count} Mars properties retrieved")
            } catch (e: Exception) {
                _response.value = "Failure: ${e.message}"
            }
        }
    }

    // 當Fragment消失, ViewModel is destroyed, 因此Loading data的工作需要停止
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

}
