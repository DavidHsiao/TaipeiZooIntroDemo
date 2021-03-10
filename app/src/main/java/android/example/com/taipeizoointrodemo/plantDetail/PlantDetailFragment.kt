package android.example.com.taipeizoointrodemo.plantDetail

import android.example.com.taipeizoointrodemo.databinding.FragmentPlantDetailBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders

class PlantDetailFragment : Fragment(){

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val application = requireNotNull(activity).application
        val binding = FragmentPlantDetailBinding.inflate(inflater)
        binding.lifecycleOwner = this

        // 紀錄從AreaDetailFragment選中的植物資料
        val plant = PlantDetailFragmentArgs.fromBundle(arguments!!).selectedPlant

        // 建立ViewModelFactory
        val viewModelFactory = PlantDetailViewModelFactory(plant, application)

        // 用ViewModelFactory建立ViewModel
        val viewModel = ViewModelProviders.of(
            this, viewModelFactory).get(PlantDetailViewModel::class.java)

        // 設定ViewModel到Binding
        binding.viewModel =viewModel

        // 更改ActionBar的文字
        (activity as AppCompatActivity).supportActionBar?.title = plant.f_Name_Ch

        return binding.root
    }
}
