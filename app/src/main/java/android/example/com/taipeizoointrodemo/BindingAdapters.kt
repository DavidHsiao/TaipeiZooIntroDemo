package android.example.com.taipeizoointrodemo

import android.example.com.taipeizoointrodemo.networkApi.EachAreaResults
import android.example.com.taipeizoointrodemo.overview.AreaListAdapter
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

/**
 * When there is no Mars property data (data is null), hide the [RecyclerView], otherwise show it.
 */
@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<EachAreaResults>?) {
    val adapter = recyclerView.adapter as AreaListAdapter
    adapter.submitList(data)
}

/**
 * Uses the Glide library to load an image by URL into an [ImageView]
 */
// @BindingAdapter = 跟databinding說我要執行這項
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
