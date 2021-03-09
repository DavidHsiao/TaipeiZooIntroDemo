package android.example.com.taipeizoointrodemo.areaDetail

import android.example.com.taipeizoointrodemo.databinding.FragmentAreaDetailBinding
import android.os.Bundle
import android.text.Html
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController

class AreaDetailFragment : Fragment(){
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val application = requireNotNull(activity).application
        val binding = FragmentAreaDetailBinding.inflate(inflater)
        binding.lifecycleOwner = this


        // Get the selectedProperty from the fragment arguments with DetailFragmentArgs
        val area = AreaDetailFragmentArgs.fromBundle(arguments!!).selectedArea

        // Create the DetailViewModelFactory using the marsProperty and application
        val viewModelFactory = AreaDetailViewModelFactory(area, application)

        val viewModel = ViewModelProviders.of(
                this, viewModelFactory).get(AreaDetailViewModel::class.java)
        // Get the DetailViewModel from the DetailViewModelFactory and set it in the binding
        binding.viewModel =viewModel

        binding.rvPlantList.adapter = PlantListAdapter(PlantListAdapter.OnClickListener{
            viewModel.displayPlantDetails(it)
        })

        // Observe the navigateToSelectedProperty LiveData and Navigate when it isn't null
        // After navigating, call displayPropertyDetailsComplete() so that the ViewModel is ready
        // for another navigation event.
        viewModel.navigateToPlantDetail.observe(this, Observer {
            if ( null != it ) {
                // Must find the NavController from the Fragment
                this.findNavController().navigate(AreaDetailFragmentDirections.actionAreaDetailFragmentToPlantDetailFragment(it))
                // 歸零
                // Tell the ViewModel we've made the navigate call to prevent multiple navigation
                viewModel.displayPlantDetailsComplete()
            }
        })

        (activity as AppCompatActivity).supportActionBar?.title = area.E_Name

        binding.tvHyperlink.text = Html.fromHtml("<a href='" + area.E_URL + "'>在網站開啟</a>")

        binding.tvHyperlink.movementMethod = LinkMovementMethod.getInstance()

//        var layoutManager = LinearLayoutManager(activity).isAutoMeasureEnabled
//        binding.rvPlantList.layoutManager =layoutManager

        return binding.root
    }
}