package nikolaev.postboy.view.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import nikolaev.postboy.util.TAB_TITLES
import nikolaev.postboy.view.fragments.ResponseFragment


class SampleFragmentPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getCount(): Int {
        return TAB_TITLES.size
    }

    override fun getItem(position: Int): Fragment {

        return when (position) {
            0 -> {
                ResponseFragment()
            }
            1 -> {
                ResponseFragment()
            }
            2 -> {
                ResponseFragment()
            }
            else -> ResponseFragment()
        }

    }

    override fun getPageTitle(position: Int): CharSequence {
        return TAB_TITLES[position]
    }
}

