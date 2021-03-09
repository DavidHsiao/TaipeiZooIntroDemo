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

        // Get the selectedProperty from the fragment arguments with DetailFragmentArgs
        val plant = PlantDetailFragmentArgs.fromBundle(arguments!!).selectedPlant

        // Create the DetailViewModelFactory using the marsProperty and application
        val viewModelFactory = PlantDetailViewModelFactory(plant, application)

        val viewModel = ViewModelProviders.of(
            this, viewModelFactory).get(PlantDetailViewModel::class.java)
        // Get the DetailViewModel from the DetailViewModelFactory and set it in the binding
        binding.viewModel =viewModel

        (activity as AppCompatActivity).supportActionBar?.title = plant.f_Name_Ch

        return binding.root
    }
}
