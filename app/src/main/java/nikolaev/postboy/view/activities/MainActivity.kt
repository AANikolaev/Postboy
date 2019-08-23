package nikolaev.postboy.view.activities

import android.content.DialogInterface
import android.content.Intent
import android.content.res.Configuration
import android.content.res.Configuration.UI_MODE_NIGHT_MASK
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import com.crashlytics.android.Crashlytics
import com.google.android.material.navigation.NavigationView
import io.fabric.sdk.android.Fabric
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
                    getString(R.string.message_dialog_default_cancel_button_text),
                    DialogInterface.OnClickListener { dialog, _ ->
                        dialog.cancel()
                    })
            }
        })

        viewModel.nextFragment.observe(this, Observer {
            moveToNextFragment(it)
        })
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Fabric.with(this, Crashlytics())

        setSupportActionBar(toolbar)
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)

        val toggle = ActionBarDrawerToggle(
            this,
            drawer_layout,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        if (getCurrentNightMode() == Configuration.UI_MODE_NIGHT_YES) {
            window.decorView.systemUiVisibility = 0
        } else {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
        window.statusBarColor = ContextCompat.getColor(this, R.color.colorBackground)
    }

    private fun getCurrentNightMode(): Int {
        return resources.configuration.uiMode and UI_MODE_NIGHT_MASK
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
                moveToNextFragment(R.id.requestFragment)
            }
            R.id.nav_history -> {
                navController.navigate(
                    R.id.historyFragment, null,
                    NavOptions.Builder().setPopUpTo(R.id.requestFragment, false).build()
                )
            }
            R.id.nav_privacy_policy -> {
                val uriUrl = Uri.parse(PRIVACY_POLICE_LINK)
                val launchBrowser = Intent(Intent.ACTION_VIEW, uriUrl)
                startActivity(launchBrowser)
            }
            R.id.nav_about -> {
                navController.navigate(
                    R.id.aboutFragment, null,
                    NavOptions.Builder().setPopUpTo(R.id.requestFragment, false).build()
                )
            }
            R.id.nav_share -> {
                shareProject(this)
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
