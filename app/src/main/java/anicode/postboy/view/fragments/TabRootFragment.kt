package anicode.postboy.view.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import anicode.postboy.R
import anicode.postboy.view.activities.MainActivity
import anicode.postboy.view.adapter.SampleFragmentPagerAdapter
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.google.android.ump.ConsentInformation
import com.google.android.ump.ConsentRequestParameters
import com.google.android.ump.UserMessagingPlatform
import java.util.concurrent.atomic.AtomicBoolean

class TabRootFragment : Fragment() {

    private lateinit var mAdView: AdView

    private lateinit var consentInformation: ConsentInformation
    private var isMobileAdsInitializeCalled = AtomicBoolean(false)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_tab_root, container, false)

        val viewPager: ViewPager = view.findViewById(R.id.viewpager)
        viewPager.adapter = SampleFragmentPagerAdapter(fragmentManager!!)

        val slidingTabs: TabLayout = view.findViewById(R.id.sliding_tabs)
        slidingTabs.post { slidingTabs.setupWithViewPager(viewPager) }

        (activity as MainActivity).supportActionBar?.hide()

        mAdView = view.findViewById(R.id.adView)

        val params = ConsentRequestParameters.Builder().setTagForUnderAgeOfConsent(false).build()

        consentInformation = UserMessagingPlatform.getConsentInformation(view.context)
        consentInformation.requestConsentInfoUpdate(this.activity!!, params, {
            UserMessagingPlatform.loadAndShowConsentFormIfRequired(
                this.activity!!
            ) {
                if (consentInformation.canRequestAds()) {
                    initializeMobileAdsSdk(view.context)
                }
            }
        }, {})

        if (consentInformation.canRequestAds()) {
            initializeMobileAdsSdk(view.context)
        }


        return view
    }

    private fun initializeMobileAdsSdk(context: Context) {
        if (isMobileAdsInitializeCalled.get()) {
            return
        }
        isMobileAdsInitializeCalled.set(true)

        MobileAds.initialize(context) {}

        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)
    }
}
