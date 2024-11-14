package com.truongdc.android.base.common.extensions

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.truongdc.android.base.common.constant.Constants

fun ImageView.loadImageCircleWithUrl(url: String) {
    Glide.with(this)
        .load(Constants.BASE_URL_IMAGE + url)
        .circleCrop()
        .into(this)
}

fun ImageView.loadImageWithUrl(url: String) {
    Glide.with(this)
        .load(Constants.BASE_URL_IMAGE + url)
        .into(this)
}
