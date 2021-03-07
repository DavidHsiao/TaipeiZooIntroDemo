package android.example.com.taipeizoointrodemo.overview

import android.example.com.taipeizoointrodemo.constant.ApiStatus
import android.example.com.taipeizoointrodemo.networkApi.EachAreaResults
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
    private val _status = MutableLiveData<ApiStatus>()

    // The external immutable LiveData for the request status String
    val status: LiveData<ApiStatus>
        get() = _status

    // Internally, we use a MutableLiveData, because we will be updating the MarsProperty with new values
    private val _area = MutableLiveData<List<EachAreaResults>>()

    // The external LiveData interface to the property is immutable, so only this class can modify
    val area: LiveData<List<EachAreaResults>>
        get() = _area

    // Internally, we use a MutableLiveData to handle navigation to the selected property
    private val _navigateToSelectedArea = MutableLiveData<EachAreaResults>()

    // The external immutable LiveData for the navigation property
    val navigateToSelectedArea: LiveData<EachAreaResults>
        get() = _navigateToSelectedArea


    // 因為使用Coroutine, 所以要建立Job
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    /**
     * Call getTaipeiZooArea() on init so we can display status immediately.
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
            var getTaipeiZooAreaDeferred = TaipeiZooAreaApi.retrofitService.getArea("resourceAquire")

            // 用try catch一樣可以達到先前callback方式有的error handling
            try {
                _status.value = ApiStatus.LOADING
                // 等到收到request才繼續執行，await是non-blocking, 目前執行在main thread也OK
                // Await the completion of our Retrofit request
                var listResult = getTaipeiZooAreaDeferred.await()
                _status.value = ApiStatus.DONE
                Log.d(TAG, "getTaipeiZooArea Success, result count =  ${listResult.result.count}")

                // 將第一筆result資料放入
                if (listResult.result.count > 0) {
                    _area.value = listResult.result.results
                }
            } catch (e: Exception) {
                _status.value = ApiStatus.ERROR
                _area.value = ArrayList()
                Log.d(TAG, "getTaipeiZooArea Error, MSG =  ${e.toString()}")
            }
        }
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
    fun displayAreaDetails(eachAreaResults: EachAreaResults) {
        _navigateToSelectedArea.value = eachAreaResults
    }

    /**
     * After the navigation has taken place, make sure navigateToSelectedProperty is set to null
     */
    fun displayAreaDetailsComplete() {
        _navigateToSelectedArea.value = null
    }


}
