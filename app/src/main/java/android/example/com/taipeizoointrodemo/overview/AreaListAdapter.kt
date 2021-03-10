package android.example.com.taipeizoointrodemo.overview

import android.example.com.taipeizoointrodemo.databinding.OverviewItemBinding
import android.example.com.taipeizoointrodemo.networkApi.EachAreaResults
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class AreaListAdapter(val onClickListener: OnClickListener) : ListAdapter<EachAreaResults, AreaListAdapter.TaipeiZooAreaViewHolder>(DiffCallback) {

    class TaipeiZooAreaViewHolder(private var binding: OverviewItemBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(zooArea: EachAreaResults) {
            binding.area = zooArea
            // This is important, because it forces the data binding to execute immediately,
            // which allows the RecyclerView to make the correct view size measurements update immediately
            binding.executePendingBindings()
        }
    }

    // 建立全新的viewholder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaipeiZooAreaViewHolder {
        return TaipeiZooAreaViewHolder(OverviewItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    // Replaces the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: TaipeiZooAreaViewHolder, position: Int) {
        val zooArea = getItem(position)
        // 呼叫onClick Function
        holder.itemView.setOnClickListener {
            onClickListener.onClick(zooArea)
        }

        holder.bind(zooArea)
    }

    // 檢查RecyclerView中各item的內容是否有變
    companion object DiffCallback : DiffUtil.ItemCallback<EachAreaResults>() {

        override fun areItemsTheSame(oldItem: EachAreaResults, newItem: EachAreaResults): Boolean {
            // 三個=代表所有的東西全部都一樣
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: EachAreaResults, newItem: EachAreaResults): Boolean {
            return oldItem._id == newItem._id
        }
    }

    // handles clicks on [RecyclerView] items.
    class OnClickListener(val clickListener: (eachAreaResults: EachAreaResults) -> Unit) {
        fun onClick(eachAreaResults: EachAreaResults) = clickListener(eachAreaResults)
    }


}