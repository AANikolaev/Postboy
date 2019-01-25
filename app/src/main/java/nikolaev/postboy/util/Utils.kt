package nikolaev.postboy.util

import android.content.Context
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.core.view.size


fun inflateOptionView(context: Context, layout: Int, root: LinearLayout) {
    val inflater = LayoutInflater.from(context)
    val rowView = inflater.inflate(layout, null)
    root.addView(rowView, root.size)
}