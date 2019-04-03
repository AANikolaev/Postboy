package nikolaev.postboy.view.activities

import android.content.DialogInterface
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.core.view.GravityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import nikolaev.postboy.R
import nikolaev.postboy.databinding.ActivityMainBinding
import nikolaev.postboy.util.*
import nikolaev.postboy.view.base.BaseActivity
import nikolaev.postboy.view.interfaces.IRouter
import nikolaev.postboy.viewmodel.MainViewModel

class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>(), IRouter<Int>,
    NavigationView.OnNavigationItemSelectedListener {

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
        setSupportActionBar(toolbar)
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)

        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun moveToNextFragment(i: Int) {
        navController.navigate(i)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_rest -> {
                supportActionBar?.title = resources.getString(R.string.app_name)
                moveToNextFragment(R.id.requestFragment)
            }
            R.id.nav_history -> {
                supportActionBar?.show()
                supportActionBar?.title = resources.getString(R.string.nav_history)
                moveToNextFragment(R.id.historyFragment)
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
