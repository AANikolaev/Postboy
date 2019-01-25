package nikolaev.postboy.util


import android.app.Activity
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import nikolaev.postboy.R
import nikolaev.postboy.databinding.PreloaderBinding
import nikolaev.postboy.view.base.BaseActivity


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
    alert?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
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