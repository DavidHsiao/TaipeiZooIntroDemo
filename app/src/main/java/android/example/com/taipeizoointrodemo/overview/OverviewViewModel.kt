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


class OverviewViewModel() : ViewModel() {

    private val TAG = OverviewViewModel::class.java.simpleName

    // 用於儲存Retrofit Loading狀態
    private val _status = MutableLiveData<ApiStatus>()

    val status: LiveData<ApiStatus>
        get() = _status

    // 用於儲存所有館別資訊
    private val _area = MutableLiveData<List<EachAreaResults>>()

    val area: LiveData<List<EachAreaResults>>
        get() = _area

    // 用於儲存跳轉Fragment的指令
    private val _navigateToSelectedArea = MutableLiveData<EachAreaResults>()

    val navigateToSelectedArea: LiveData<EachAreaResults>
        get() = _navigateToSelectedArea


    // 建立Coroutine使用的Job
    private var viewModelJob = Job()
    // 建立coroutineScope
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    init {
        getTaipeiZooArea()
    }

    // 呼叫館別API
    private fun getTaipeiZooArea() {

        coroutineScope.launch {
            // Get the Deferred object for our Retrofit request
            var getTaipeiZooAreaDeferred = TaipeiZooAreaApi.retrofitService.getArea("resourceAquire")

            try {
                _status.value = ApiStatus.LOADING
                // 等到收到request才繼續執行，await是non-blocking, 目前執行在main thread也OK
                var listResult = getTaipeiZooAreaDeferred.await()
                _status.value = ApiStatus.DONE
                Log.d(TAG, "getTaipeiZooArea Success, result count =  ${listResult.result.count}")


                if (listResult.result.count > 0) {
                    // 將result資料放入
                    _area.value = listResult.result.results
                }
            } catch (e: Exception) {
                _status.value = ApiStatus.ERROR
                _area.value = ArrayList()
                Log.d(TAG, "getTaipeiZooArea Error, MSG =  ${e.message}")
            }
        }
    }

    // 當Fragment消失, ViewModel is destroyed, 因此Loading data的工作需要停止
    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "onCleared()")
        viewModelJob.cancel()
    }

    // 當點擊RecyclerView中的item時執行
    fun displayAreaDetails(eachAreaResults: EachAreaResults) {
        _navigateToSelectedArea.value = eachAreaResults
    }

    // 關閉Fragment跳轉的LiveData
    fun displayAreaDetailsComplete() {
        _navigateToSelectedArea.value = null
    }


}
