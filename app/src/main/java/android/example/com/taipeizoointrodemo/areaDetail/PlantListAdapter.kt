package android.example.com.taipeizoointrodemo.areaDetail

import android.example.com.taipeizoointrodemo.databinding.PlantItemBinding
import android.example.com.taipeizoointrodemo.networkApi.PlantResults
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class PlantListAdapter(val onClickListener: OnClickListener) : ListAdapter<PlantResults, PlantListAdapter.TaipeiZooPlantViewHolder>(DiffCallback){


    class TaipeiZooPlantViewHolder(private var binding: PlantItemBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(zooPlant: PlantResults) {
            binding.plant = zooPlant
            // This is important, because it forces the data binding to execute immediately,
            // which allows the RecyclerView to make the correct view size measurements

            // update immediately
            binding.executePendingBindings()
        }
    }

    /**
     * Create new [RecyclerView] item views (invoked by the layout manager)
     */
    // 觸發時會建立全新的viewholder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaipeiZooPlantViewHolder {
        return TaipeiZooPlantViewHolder(PlantItemBinding.inflate(LayoutInflater.from(parent.context)))
    }
    /**
     * Replaces the contents of a view (invoked by the layout manager)
     */
    override fun onBindViewHolder(holder: TaipeiZooPlantViewHolder, position: Int) {
        val zooPlant = getItem(position)
        // Call the onClick Function from the onClickListener in a lambda from setOnClickListener
        holder.itemView.setOnClickListener {
            onClickListener.onClick(zooPlant)
        }

        holder.bind(zooPlant)
    }

    /**
     * Allows the RecyclerView to determine which items have changed when the [List] of [MarsProperty]
     * has been updated.
     */
    companion object DiffCallback : DiffUtil.ItemCallback<PlantResults>() {
        override fun areItemsTheSame(oldItem: PlantResults, newItem: PlantResults): Boolean {
            // 三個=代表所有的東西全部都一樣
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: PlantResults, newItem: PlantResults): Boolean {
            return oldItem._id == newItem._id
        }
    }

    /**
     * Custom listener that handles clicks on [RecyclerView] items.  Passes the [MarsProperty]
     * associated with the current item to the [onClick] function.
     * @param clickListener lambda that will be called with the current [MarsProperty]
     */
    class OnClickListener(val clickListener: (plantResults: PlantResults) -> Unit) {
        fun onClick(plantResults: PlantResults) = clickListener(plantResults)
    }

}
