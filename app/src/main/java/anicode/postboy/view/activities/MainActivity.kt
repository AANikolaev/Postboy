package anicode.postboy.view.activities

import android.content.ActivityNotFoundException
import android.content.DialogInterface
import android.content.Intent
import android.content.res.Configuration
import android.content.res.Configuration.UI_MODE_NIGHT_MASK
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import anicode.postboy.R
import anicode.postboy.databinding.ActivityMainBinding
import anicode.postboy.util.ErrorDialogModel
import anicode.postboy.util.Event
import anicode.postboy.util.PRIVACY_POLICE_LINK
import anicode.postboy.util.ProgressDialogModel
import anicode.postboy.util.getNavAnimation
import anicode.postboy.util.showMessageDialogWithSingleAction
import anicode.postboy.util.showPreLoader
import anicode.postboy.view.base.BaseActivity
import anicode.postboy.view.interfaces.IRouter
import anicode.postboy.viewmodel.MainViewModel
import com.google.android.material.navigation.NavigationView
import com.google.firebase.analytics.FirebaseAnalytics


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

        FirebaseAnalytics.getInstance(this)

        setSupportActionBar(binding.appBarMain.toolbar)
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)

        val toggle = ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            binding.appBarMain.toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        binding.navView.setNavigationItemSelectedListener(this)

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
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
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
                navController.navigate(
                    R.id.requestFragment, null,
                    getNavAnimation()
                )
                closeDrawer()
                return true
            }

            R.id.nav_history -> {
                navController.navigate(
                    R.id.historyFragment, null,
                    getNavAnimation()
                )
                closeDrawer()
                return true
            }

            R.id.nav_privacy_policy -> {
                try {
                    val uriUrl = Uri.parse(PRIVACY_POLICE_LINK)
                    val launchBrowser = Intent(Intent.ACTION_VIEW, uriUrl)
                    startActivity(launchBrowser)
                } catch (e: ActivityNotFoundException) {
                    Toast.makeText(
                        this,
                        R.string.browser_not_found,
                        Toast.LENGTH_SHORT
                    ).show()
                }
                closeDrawer()
                return true
            }


            R.id.nav_about -> {
                navController.navigate(
                    R.id.aboutFragment, null,
                    getNavAnimation()
                )
                closeDrawer()
                return true
            }

            else -> return false
        }
    }

    private fun closeDrawer() {
        binding.drawerLayout.closeDrawer(GravityCompat.START)
    }
}
