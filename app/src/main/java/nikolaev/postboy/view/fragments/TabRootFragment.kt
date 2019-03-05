package nikolaev.postboy.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import nikolaev.postboy.R
import nikolaev.postboy.view.adapter.SampleFragmentPagerAdapter

class TabRootFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_tab_root, container, false)

        val viewPager = view.findViewById(R.id.viewpager) as ViewPager
        viewPager.adapter = SampleFragmentPagerAdapter(fragmentManager!!)

        val slidingTabs = view.findViewById(R.id.sliding_tabs) as TabLayout
        slidingTabs.post { slidingTabs.setupWithViewPager(viewPager) }


        return view
    }

}
