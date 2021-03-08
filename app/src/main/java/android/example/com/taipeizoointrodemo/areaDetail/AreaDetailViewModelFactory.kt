package android.example.com.taipeizoointrodemo.areaDetail

import android.app.Activity
import android.app.Application
import android.example.com.taipeizoointrodemo.networkApi.EachAreaResults
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class AreaDetailViewModelFactory(
    private val eachAreaResults: EachAreaResults,
    private val application: Application,
    private val activity: Activity
) : ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AreaDetailViewModel::class.java)) {
            return AreaDetailViewModel(eachAreaResults, application, activity) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}