package android.example.com.taipeizoointrodemo

import android.example.com.taipeizoointrodemo.areaDetail.PlantListAdapter
import android.example.com.taipeizoointrodemo.constant.ApiStatus
import android.example.com.taipeizoointrodemo.networkApi.EachAreaResults
import android.example.com.taipeizoointrodemo.networkApi.PlantResults
import android.example.com.taipeizoointrodemo.overview.AreaListAdapter
import android.view.View
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

// @BindingAdapter = 跟databinding說我要執行這項
// Bind OverviewFragment的RecyclerView Adapter
@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<EachAreaResults>?) {
    val adapter = recyclerView.adapter as AreaListAdapter
    adapter.submitList(data)
}

// Bind AreaDetailFragment的RecyclerView Adapter
@BindingAdapter("plantListData")
fun bindPlantRecyclerView(recyclerView: RecyclerView, data: List<PlantResults>?) {
    val adapter = recyclerView.adapter as PlantListAdapter
    adapter.submitList(data)
}

// 使用Glide Library去Laod URL image到ImageView
// 這邊的"imageUrl"定義好之後 就可以在xml中的app:imageUrl使用，app:後面使用的名稱就是在此定義
@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        // convert imageURL to URI
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        // load image uri to imageview
        Glide.with(imgView.context)
            .load(imgUri)
            .apply(
                RequestOptions()
                // 當resource loading會顯示
                .placeholder(R.drawable.loading_animation)
                // 當出現錯誤時會顯示
                .error(R.drawable.ic_broken_image))
            .into(imgView)
    }
}

/**
 * This binding adapter displays the [zooAreaApiStatus] of the network request in an image view.  When
 * the request is loading, it displays a loading_animation.  If the request has an error, it
 * displays a broken image to reflect the connection error.  When the request is finished, it
 * hides the image view.
 */

// 用於顯示Loading animation的狀態與顯示與否
@BindingAdapter("zooAreaApiStatus")
fun bindStatus(statusImageView: ImageView, status: ApiStatus?) {
    when (status) {
        ApiStatus.LOADING -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.loading_animation)
        }
        ApiStatus.ERROR -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.ic_connection_error)
        }
        ApiStatus.DONE -> {
            statusImageView.visibility = View.GONE
        }
    }
}

