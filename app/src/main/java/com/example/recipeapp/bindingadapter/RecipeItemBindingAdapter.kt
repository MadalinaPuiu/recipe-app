package com.example.recipeapp.bindingadapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.recipeapp.R

class RecipeItemBindingAdapter {
    companion object {

        @BindingAdapter("setNumberOfLikes")
        @JvmStatic
        fun setNumberOfLikes(textView: TextView, likes: Int) {
            textView.text = likes.toString()
        }

        @BindingAdapter("setMinutes")
        @JvmStatic
        fun setMinutes(textView: TextView, minutes: Int) {
            textView.text = minutes.toString()
        }

        @BindingAdapter("showVeganIcon")
        @JvmStatic
        fun showVeganIcon(view: ImageView, isVegan: Boolean) {
            view.visibility = if (isVegan) View.VISIBLE else View.GONE
        }

        @BindingAdapter("loadImageFromUrl")
        @JvmStatic
        fun loadImageFromUrl(view: ImageView, url: String) {
            Glide.with(view.context)
                .load(url)
                .error(R.drawable.ic_cookie)
                .into(view)
        }
    }
}
