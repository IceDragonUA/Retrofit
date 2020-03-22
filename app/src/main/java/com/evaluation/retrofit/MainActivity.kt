package com.evaluation.retrofit

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.evaluation.adapter.CustomPagerAdapter
import com.evaluation.dagger.data.DataComponent
import com.evaluation.fragment.DetailFragment
import com.evaluation.fragment.MainFragment
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DataComponent.Injector.init()

        setContentView(R.layout.activity_main)

        val fragments: MutableList<Fragment> = ArrayList()
        fragments.add(MainFragment.newInstance())
        fragments.add(DetailFragment.newInstance())

        viewPager.adapter = CustomPagerAdapter(this, fragments, supportFragmentManager)
        tabs.setupWithViewPager(viewPager)
    }
}