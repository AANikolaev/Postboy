package anicode.postboy.util

import android.widget.TextView
import androidx.databinding.BindingAdapter


@BindingAdapter("setColorText")
fun setColorText(textView: TextView, color: Int) {
    textView.setTextColor(color)
}
