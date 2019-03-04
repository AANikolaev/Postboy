package nikolaev.postboy.util

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.size
import nikolaev.postboy.R
import java.util.*


fun inflateOptionView(context: Context, layout: Int, root: LinearLayout) {
    val rowView = LayoutInflater.from(context).inflate(layout, null)
    root.addView(rowView, root.size)
}

fun inflateOptionViewWithData(
    context: Context,
    layout: Int,
    root: LinearLayout,
    listPair: ArrayList<Pair<String, String>>
) {
    for (i in listPair) {
        val rowView = LayoutInflater.from(context).inflate(layout, null)

        val t: TextView = rowView.findViewById(R.id.textViewKey)
        t.text = i.first
        val tw: TextView = rowView.findViewById(R.id.textViewKey)
        tw.text = i.second
        root.addView(rowView, root.size)

        Log.d("+", "${i.first} ${i.second}")
    }
}