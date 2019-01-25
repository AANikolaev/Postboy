package nikolaev.postboy.util


import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import nikolaev.postboy.R
import nikolaev.postboy.databinding.PreloaderBinding
import nikolaev.postboy.view.base.BaseActivity

fun showPreLoader(
    activity: BaseActivity<*, *>,
    descriptionText: String? = null, isNeedToClearQueueAfterClick: Boolean
): AlertDialog? {
    if (activity == null) {
        return null
    }
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
