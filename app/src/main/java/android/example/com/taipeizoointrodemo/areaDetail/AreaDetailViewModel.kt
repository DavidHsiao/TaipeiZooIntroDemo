package android.example.com.taipeizoointrodemo.areaDetail

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

class AreaDetailViewModel(eachAreaResults: EachAreaResults, app: Application) : AndroidViewModel(app) {

    private val TAG = AreaDetailViewModel::class.java.simpleName

    // 用於儲存Retrofit Loading狀態
    private val _status = MutableLiveData<ApiStatus>()

    val status: LiveData<ApiStatus>
        get() = _status

    // 用於儲存某館所有植物的資料
    private val _plant = MutableLiveData<List<PlantResults>>()

    val plant: LiveData<List<PlantResults>>
        get() = _plant

    // 用於儲存跳轉Fragment的指令
    private val _navigateToPlantDetail = MutableLiveData<PlantResults>()

    val navigateToPlantDetail: LiveData<PlantResults>
        get() = _navigateToPlantDetail

    private val _app = app

    // 用於儲存選中的館別資訊
    private val _selectedArea = MutableLiveData<EachAreaResults>()

    val selectedArea: LiveData<EachAreaResults>
        get() = _selectedArea

    // 建立Coroutine使用的Job
    private var viewModelJob = Job()
    // 建立coroutineScope
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    init {
        // 放入選中的館別資訊
        _selectedArea.value = eachAreaResults
        getTaipeiZooPlant()
    }

    // 用於儲存館別開放資訊
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

    // 呼叫植物API
    private fun getTaipeiZooPlant() {

        coroutineScope.launch {
            // Get the Deferred object for our Retrofit request
            var getTaipeiZooPlantDeferred = AreaPlantApi.retrofitService.getPlants(_selectedArea.value!!.E_Name!!)

            try {
                _status.value = ApiStatus.LOADING
                // 等到收到request才繼續執行，await是non-blocking, 目前執行在main thread也OK
                var plantResult = getTaipeiZooPlantDeferred.await()
                _status.value = ApiStatus.DONE
                Log.d(TAG, "getTaipeiZooPlant Success, result count =  ${plantResult.result.count}")

                // 將result資料放入
                if (plantResult.result.count > 0) {
                    _plant.value = plantResult.result.results
                }
            } catch (e: Exception) {
                _status.value = ApiStatus.ERROR
                _plant.value = ArrayList()
                Log.d(TAG, "getTaipeiZooPlant Error, MSG =  ${e.toString()}")
            }
        }
    }

    // 當Fragment消失, ViewModel is destroyed, 因此Loading data的工作需要停止
    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "onCleared()")
        viewModelJob.cancel()
    }

    /// 當點擊RecyclerView中的item時執行
    fun displayPlantDetails(plantResults: PlantResults) {
        _navigateToPlantDetail.value = plantResults
    }

    // 關閉Fragment跳轉的LiveData
    fun displayPlantDetailsComplete() {
        _navigateToPlantDetail.value = null
    }



}
