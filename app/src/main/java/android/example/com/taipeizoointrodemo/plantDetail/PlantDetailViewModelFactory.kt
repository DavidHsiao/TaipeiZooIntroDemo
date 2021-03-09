package android.example.com.taipeizoointrodemo.plantDetail

import android.app.Application
import android.example.com.taipeizoointrodemo.networkApi.PlantResults
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class PlantDetailViewModelFactory(
    private val plantResults: PlantResults,
    private val application: Application
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PlantDetailViewModel::class.java)) {
            return PlantDetailViewModel(plantResults, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}

