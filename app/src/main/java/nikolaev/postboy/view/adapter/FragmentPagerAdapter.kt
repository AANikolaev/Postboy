package nikolaev.postboy.view.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import nikolaev.postboy.util.TAB_TITLES
import nikolaev.postboy.view.fragments.ResponseFragment
import nikolaev.postboy.view.fragments.ResponseInfoFragment
import nikolaev.postboy.view.fragments.ResponsePreviewFragment


class SampleFragmentPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    override fun getCount(): Int {
        return TAB_TITLES.size
    }

    override fun getItem(position: Int): Fragment {

        return when (position) {
            0 -> {
                ResponseFragment.newInstance()
            }
            1 -> {
                ResponsePreviewFragment.newInstance()
            }
            2 -> {
                ResponseInfoFragment.newInstance()
            }
            else -> ResponseFragment.newInstance()
        }

    }

    override fun getPageTitle(position: Int): CharSequence {
        return TAB_TITLES[position]
    }
}

