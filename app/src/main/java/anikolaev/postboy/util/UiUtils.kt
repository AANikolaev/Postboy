package anikolaev.postboy.util


import android.app.Activity
import android.content.DialogInterface
import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.view.Gravity
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavOptions
import anikolaev.postboy.R
import anikolaev.postboy.databinding.PreloaderBinding
import anikolaev.postboy.view.base.BaseActivity
import okhttp3.Headers


fun showPreLoader(
    activity: BaseActivity<*, *>,
    descriptionText: String? = null, isNeedToClearQueueAfterClick: Boolean
): AlertDialog? {

    val binding = DataBindingUtil.inflate<PreloaderBinding>(
        activity.layoutInflater,
        R.layout.preloader,
        null,
        false
    )
    binding.description = descriptionText ?: activity
        .getString(R.string.pre_loader_description_text_default)

    val builder = AlertDialog.Builder(activity)
    builder.setView(binding.root)

    val alert = builder.create()
    alert.setCanceledOnTouchOutside(false)
    alert.setCancelable(true)
    if (isNeedToClearQueueAfterClick) {
        alert.setOnCancelListener {
            (activity.viewModel).clearRequestQueue()
        }
    }
    alert.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    alert.show()

    return alert
}

fun showMessageDialogWithSingleAction(
    activity: Activity,
    message: String,
    buttonTitle: String,
    listener: DialogInterface.OnClickListener
) {

    val alert = AlertDialog.Builder(activity)
        .setMessage(message)
        .setCancelable(false)
        .setNegativeButton(
            buttonTitle,
            listener
        )
        .create()
    alert.show()

    val messageView = alert.findViewById<TextView>(android.R.id.message)
    messageView?.gravity = Gravity.CENTER
}

fun setColorCodeInfo(code: Int, resources: Resources): Int {
    return when (code) {
        in 100..199 -> resources.getColor(R.color.colorGrey)
        in 200..299 -> resources.getColor(R.color.google_green)
        in 300..399 -> resources.getColor(R.color.google_yellow)
        in 400..499 -> resources.getColor(R.color.google_red)
        in 500..599 -> resources.getColor(R.color.google_red)
        else -> resources.getColor(R.color.google_red)
    }
}

fun headersToCharSequence(headers: Headers, resources: Resources): CharSequence {
    val spannableStringBuilder = SpannableStringBuilder()
    for (i in 0 until headers.size()) {
        val value = SpannableString(headers.value(i))
        value.setSpan(
            ForegroundColorSpan(resources.getColor(R.color.colorGrey)),
            0, value.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        spannableStringBuilder.append(headers.name(i)).append(": ").append(value).append("\n")

    }
    spannableStringBuilder.delete(spannableStringBuilder.length - 1, spannableStringBuilder.length)

    return spannableStringBuilder
}

fun setMethodColor(method: String): Int {
    return when (method) {
        "GET" -> 0xFF0F9D58.toInt()
        "POST" -> 0xFF4285F4.toInt()
        "PUT" -> 0xFFF4B400.toInt()
        "DELETE" -> 0xFFDB4437.toInt()
        else -> 0xFF0F9D58.toInt()
    }
}

fun getNavAnimation() = NavOptions.Builder()
    .setEnterAnim(android.R.anim.fade_in)
    .setExitAnim(android.R.anim.fade_out)
    .build()