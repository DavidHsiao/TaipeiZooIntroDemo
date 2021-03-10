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


        // 紀錄從OverviewFragment選中的館別資料
        val area = AreaDetailFragmentArgs.fromBundle(arguments!!).selectedArea

        // 建立ViewModelFactory
        val viewModelFactory = AreaDetailViewModelFactory(area, application)

        // 用ViewModelFactory建立ViewModel
        val viewModel = ViewModelProviders.of(this, viewModelFactory).get(AreaDetailViewModel::class.java)

        // 設定ViewModel到Binding
        binding.viewModel =viewModel

        // 設定RecyclerView的adapter
        binding.rvPlantList.adapter = PlantListAdapter(PlantListAdapter.OnClickListener{
            viewModel.displayPlantDetails(it)
        })

        // 觀察ViewModel中的變數
        viewModel.navigateToPlantDetail.observe(this, Observer {
            if ( null != it ) {
                // Must find the NavController from the Fragment
                this.findNavController().navigate(AreaDetailFragmentDirections.actionAreaDetailFragmentToPlantDetailFragment(it))
                // 歸零
                // Tell the ViewModel we've made the navigate call to prevent multiple navigation
                viewModel.displayPlantDetailsComplete()
            }
        })

        // 更改ActionBar的文字
        (activity as AppCompatActivity).supportActionBar?.title = area.E_Name

        // 加入TextView HyperLink功能
        binding.tvHyperlink.text = Html.fromHtml("<a href='" + area.E_URL + "'>在網站開啟</a>")
        binding.tvHyperlink.movementMethod = LinkMovementMethod.getInstance()

        return binding.root
    }
}