package android.example.com.taipeizoointrodemo.areaDetail

import android.example.com.taipeizoointrodemo.databinding.FragmentAreaDetailBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders

class AreaDetailFragment : Fragment(){
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val application = requireNotNull(activity).application
        val binding = FragmentAreaDetailBinding.inflate(inflater)
        binding.setLifecycleOwner(this)

        // Get the selectedProperty from the fragment arguments with DetailFragmentArgs
        val marsProperty = AreaDetailFragmentArgs.fromBundle(arguments!!).selectedArea

        // Create the DetailViewModelFactory using the marsProperty and application
        val viewModelFactory = AreaDetailViewModelFactory(marsProperty, application)

        val viewModel = ViewModelProviders.of(
                this, viewModelFactory).get(AreaDetailViewModel::class.java)
        // Get the DetailViewModel from the DetailViewModelFactory and set it in the binding
        binding.viewModel =viewModel

        binding.rvPlantList.adapter = PlantListAdapter(PlantListAdapter.OnClickListener{
            viewModel.displayPlantDetails(it)
        })

        return binding.root
    }
}