package android.example.com.taipeizoointrodemo.overview

import android.example.com.taipeizoointrodemo.R
import android.example.com.taipeizoointrodemo.databinding.FragmentOverviewBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController

class OverviewFragment : Fragment() {


    /**
     * Inflates the layout with Data Binding, sets its lifecycle owner to the OverviewFragment
     * to enable Data Binding to observe LiveData, and sets up the RecyclerView with an adapter.
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val binding = FragmentOverviewBinding.inflate(inflater)

        // 建立ViewModelFactory
        val viewModelFactory = OverviewViewModelFactory()

        // 用ViewModelFactory建立ViewModel
        val viewModel = ViewModelProviders.of(this, viewModelFactory).get(OverviewViewModel::class.java)

        // 設定ViewModel到Binding
        binding.viewModel = viewModel

        // 讓Data Binding依照Fragment的lifecycle來觀察LiveData
        binding.lifecycleOwner = this

        // 設定RecyclerView的adapter
        binding.areaList.adapter = AreaListAdapter(AreaListAdapter.OnClickListener{
            viewModel.displayAreaDetails(it)
        })

        // 觀察ViewModel中的變數
        viewModel.navigateToSelectedArea.observe(this, Observer {
            if ( null != it ) {
                // 跳轉Fragment
                this.findNavController().navigate(OverviewFragmentDirections.actionOverviewFragmentToAreaDetailFragment(it))
                // 歸零
                viewModel.displayAreaDetailsComplete()
            }
        })

        // 更改ActionBar的文字
        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.taipei_zoo)
        return binding.root
    }

    /**
     * Inflates the overflow menu that contains filtering options.
     */
//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        inflater.inflate(R.menu.overflow_menu, menu)
//        super.onCreateOptionsMenu(menu, inflater)
//    }
}
