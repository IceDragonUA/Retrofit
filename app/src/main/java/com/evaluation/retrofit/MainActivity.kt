package com.evaluation.retrofit

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import butterknife.BindView
import butterknife.ButterKnife
import com.evaluation.adapter.CustomPagerAdapter
import com.evaluation.dagger.data.DataComponent
import com.evaluation.fragment.DetailFragment
import com.evaluation.fragment.MainFragment
import com.google.android.material.tabs.TabLayout
import java.util.*

class MainActivity : AppCompatActivity() {

    @BindView(R.id.tabs)
    lateinit var mTab: TabLayout

    @BindView(R.id.view_pager)
    lateinit var mViewPager: ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DataComponent.Injector.init()

        setContentView(R.layout.activity_main)

        ButterKnife.bind(this)

        val fragments: MutableList<Fragment> = ArrayList()
        fragments.add(MainFragment.newInstance())
        fragments.add(DetailFragment.newInstance())

        mViewPager.adapter = CustomPagerAdapter(this, fragments, supportFragmentManager)
        mTab.setupWithViewPager(mViewPager)
    }
}