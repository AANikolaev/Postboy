package nikolaev.postboy.view

import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import nikolaev.postboy.R
import nikolaev.postboy.databinding.ActivityMainBinding
import nikolaev.postboy.view.base.BaseActivity
import nikolaev.postboy.viewmodel.ActivityMainViewModel

class MainActivity : BaseActivity<ActivityMainViewModel, ActivityMainBinding>() {

    override fun initViewModel(): ActivityMainViewModel {
        return ViewModelProviders.of(this).get(ActivityMainViewModel::class.java)
    }

    override fun initBinding(): ActivityMainBinding {
        return DataBindingUtil.setContentView(this, R.layout.activity_main)
    }

}
