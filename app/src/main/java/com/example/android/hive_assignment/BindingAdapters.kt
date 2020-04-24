package com.example.android.hive_assignment

import android.view.View
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.android.hive_assignment.model.Restaurant
import com.example.android.hive_assignment.overview.OnSaveListne
import com.example.android.hive_assignment.overview.RestrauntsAdaptor
import com.example.android.hive_assignment.overview.ZomatoApiStatus
import com.iarcuschin.simpleratingbar.SimpleRatingBar

/**
 * When there is no Zomato restaunt data (data is null), hide the [RecyclerView], otherwise show it.
 */
@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<Restaurant>?) {
    val adapter = recyclerView.adapter as RestrauntsAdaptor
    adapter.submitList(data)
}

/**
 * Uses the Glide library to load an image by URL into an [ImageView]
 */
@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {


    imgUrl?.let {
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        Glide.with(imgView.context)
                .load(imgUri)
                .apply(RequestOptions()
                        .placeholder(R.drawable.loading_animation)
                        .error(R.drawable.ic_broken_image))
                .into(imgView)
    }
}


@BindingAdapter("onSaveOffline")
fun bindCheckBox(checkBox: CheckBox, saveListner: OnSaveListne) {
    saveListner.let {
        checkBox.setOnClickListener {
            saveListner.onSave(checkBox.isChecked)
        }
    }

}


/**
 * This binding adapter displays the [ZomatoApiStatus] of the network request in an image view.  When
 * the request is loading, it displays a loading_animation.  If the request has an error, it
 * displays a broken image to reflect the connection error.  When the request is finished, it
 * hides the image view.
 */
@BindingAdapter("zomatoApiStatus")
fun bindStatus(statusImageView: ImageView, status: ZomatoApiStatus?) {
    when (status) {
        ZomatoApiStatus.LOADING -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.loading_animation)
        }
        ZomatoApiStatus.ERROR -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.ic_connection_error)
        }
        ZomatoApiStatus.DONE -> {
            statusImageView.visibility = View.GONE
        }
    }
}

@BindingAdapter("zomatoApiStatus")
fun bindLoader(statusImageView: ProgressBar, status: ZomatoApiStatus?) {
    when (status) {
        ZomatoApiStatus.LOADING -> {
            statusImageView.visibility = View.VISIBLE
        }
        ZomatoApiStatus.ERROR -> {
            statusImageView.visibility = View.GONE
        }
        ZomatoApiStatus.DONE -> {
            statusImageView.visibility = View.GONE
        }
    }
}


@BindingAdapter("rating")
fun bindRating(ratingBar: SimpleRatingBar, rating: Double) {

    rating.let {
        ratingBar.rating = it.toFloat()
    }

}


@BindingAdapter("price")
fun bindPrice(textView: TextView, price: Int) {

    price.let {

        textView.text = "Average Cost for 2 People  Rs.$price"

    }

}