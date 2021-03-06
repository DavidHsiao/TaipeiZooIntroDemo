package android.example.com.taipeizoointrodemo.areaDetail

import android.app.Application
import android.example.com.taipeizoointrodemo.R
import android.example.com.taipeizoointrodemo.networkApi.EachAreaResults
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations

class AreaDetailViewModel(eachAreaResults: EachAreaResults, app: Application) : AndroidViewModel(app) {

    private val _app = app
    private val _selectedArea = MutableLiveData<EachAreaResults>()

    val selectedArea: LiveData<EachAreaResults>
        get() = _selectedArea

    init {
        _selectedArea.value = eachAreaResults
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


}
