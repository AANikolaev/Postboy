package nikolaev.postboy.view.activities

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import nikolaev.postboy.R
import nikolaev.postboy.databinding.ActivityMainBinding
import nikolaev.postboy.util.*
import nikolaev.postboy.view.base.BaseActivity
import nikolaev.postboy.view.interfaces.IRouter
import nikolaev.postboy.viewmodel.MainViewModel

class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>(), IRouter<Int> {

    private lateinit var navController: NavController
    private var preloader: AlertDialog? = null

    override fun obtainViewModel(): MainViewModel =
        ViewModelProviders.of(this).get(MainViewModel::class.java)

    override fun getContentViewLayoutId(): Int = R.layout.activity_main

    override fun onViewModelReady() {
        viewModel.progressDialogEvent.observe(this, Observer<ProgressDialogModel> {
            preloader = if (it?.isProgressDialogNeeded == true && preloader == null) {
                showPreLoader(this, it.text, true)
            } else {
                preloader?.dismiss()
                null
            }
        })

        viewModel.errorDialogEvent.observe(this, Observer<Event<ErrorDialogModel>> {
            it?.getContentIfNotHandled()?.let { errorDialogModel ->
                showMessageDialogWithSingleAction(this,
                    errorDialogModel.errorMessage
                        ?: getString(R.string.sing_up_basics_error_dialog_message_default),
                    getString(R.string.message_dialog_default_cancle_button_text),
                    DialogInterface.OnClickListener { dialog, _ ->
                        dialog.cancel()
                    })
            }
        })

        viewModel.nextFragment.observe(this, Observer {
            moveToNextFragment(it)
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
    }

    override fun moveToNextFragment(i: Int) {
        navController.navigate(i)
    }

    fun onClickDeleteHeader(v: View) {
        viewModel.oNDeleteHeadersView.postValue(v)
    }

    fun onClickDeleteParameters(v: View) {
        viewModel.oNDeleteParameterView.postValue(v)
    }

}
