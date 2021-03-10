package android.example.com.taipeizoointrodemo.plantDetail

import android.app.Application
import android.example.com.taipeizoointrodemo.R
import android.example.com.taipeizoointrodemo.areaDetail.AreaDetailViewModel
import android.example.com.taipeizoointrodemo.networkApi.PlantResults
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations

class PlantDetailViewModel (plantResults: PlantResults, app: Application):AndroidViewModel(app){
    private val TAG = AreaDetailViewModel::class.java.simpleName

    // 用於儲存選中的植物資訊
    private var _selectedPlant = MutableLiveData<PlantResults>()

    val selectedPlant: LiveData<PlantResults>
        get() = _selectedPlant

    val displayLastUpdateTime = Transformations.map(selectedPlant) {
        app.applicationContext.getString(R.string.display_last_update_time, it.f_Update)
    }

    init {
        _selectedPlant.value = plantResults
    }

}

