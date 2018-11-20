package com.elephantgroup.one.ui.home

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import butterknife.BindView
import com.elephantgroup.one.R
import com.elephantgroup.one.base.BaseActivity
import com.kevin.tabindicator.TabPageIndicatorEx
import java.util.*


class MainActivity : BaseActivity<MainContract.Presenter>(), MainContract.View {

    @BindView(R.id.viewPager)
    lateinit var viewPager: ViewPager

    @BindView(R.id.tabIndicator)
    lateinit var tabIndicator: TabPageIndicatorEx

    override fun getLayoutId(): Int {
        return R.layout.main_layout
    }

    override fun createPresenter(): MainContract.Presenter {
        return MainPresenter(this)
    }

    override fun initData() {
        viewPager = findViewById(R.id.viewPager);
        tabIndicator = findViewById(R.id.tabIndicator);
        initTabIndicator()
        initViewPager()
    }

    private fun initTabIndicator() {
        viewPager.offscreenPageLimit = 4
        tabIndicator.setViewPager(viewPager)
        tabIndicator.setIndicateDisplay(0, false)
        tabIndicator.isGradualChange = true
        tabIndicator.setOnTabSelectedListener { index -> viewPager.setCurrentItem(index, false) }
    }

    private fun initViewPager() {
        val mTabs = ArrayList<Fragment>()
        val mainMessageFragment = MainMessageFragment()
        val mainContactFragment = MainContactFragment()
        val mainDiscoverFragment = MainDiscoverFragment()
        val mainMineFragment = MainMineFragment()
        mTabs.add(mainMessageFragment)
        mTabs.add(mainContactFragment)
        mTabs.add(mainDiscoverFragment)
        mTabs.add(mainMineFragment)
        val mAdapter = object : FragmentPagerAdapter(supportFragmentManager) {
            override fun getCount(): Int {
                return mTabs.size
            }

            override fun getItem(arg0: Int): Fragment {
                return mTabs[arg0]
            }
        }
        viewPager.adapter = mAdapter
    }


    override fun onResult(result: Any, message: String) {

    }

    override fun onError(throwable: Throwable, message: String) {

    }
}
