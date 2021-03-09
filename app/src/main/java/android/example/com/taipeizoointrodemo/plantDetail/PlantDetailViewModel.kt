package android.example.com.taipeizoointrodemo.plantDetail

import android.app.Application
import android.example.com.taipeizoointrodemo.areaDetail.AreaDetailViewModel
import android.example.com.taipeizoointrodemo.constant.ApiStatus
import android.example.com.taipeizoointrodemo.networkApi.PlantResults
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class PlantDetailViewModel (plantResults: PlantResults, app: Application):AndroidViewModel(app){
    private val TAG = AreaDetailViewModel::class.java.simpleName

    // The internal MutableLiveData String that stores the status of the most recent request
    private val _status = MutableLiveData<ApiStatus>()

    // The external immutable LiveData for the request status String
    val status: LiveData<ApiStatus>
        get() = _status

    private val _selectedPlant = MutableLiveData<PlantResults>()

    val selectedPlant: LiveData<PlantResults>
        get() = _selectedPlant

    init {
        _selectedPlant.value = plantResults
    }

    // 當Fragment消失, ViewModel is destroyed, 因此Loading data的工作需要停止
    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "onCleared()")
    }

}

