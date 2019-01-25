package nikolaev.postboy.view.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import nikolaev.postboy.R
import nikolaev.postboy.databinding.ActivityMainBinding
import nikolaev.postboy.util.ProgressDialogModel
import nikolaev.postboy.util.showPreLoader
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

        viewModel.progressDialogEvent.observe(this, Observer {

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
