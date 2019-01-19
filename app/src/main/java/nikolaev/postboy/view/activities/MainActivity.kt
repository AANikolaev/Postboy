package nikolaev.postboy.view.activities

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import nikolaev.postboy.R
import nikolaev.postboy.databinding.ActivityMainBinding
import nikolaev.postboy.view.base.BaseActivity
import nikolaev.postboy.view.interfaces.IRouter
import nikolaev.postboy.viewmodel.MainViewModel

class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>(), IRouter<Int> {

    private lateinit var navController: NavController

    override fun obtainViewModel(): MainViewModel =
        ViewModelProviders.of(this).get(MainViewModel::class.java)

    override fun getContentViewLayoutId(): Int = R.layout.activity_main

    override fun onViewModelReady() {
//        viewModel.fragmentRouteToMove.observe(this, Observer {
//            moveToNextFragment(it)
//        })
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
