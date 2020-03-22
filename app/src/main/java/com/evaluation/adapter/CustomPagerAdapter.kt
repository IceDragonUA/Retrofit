package com.evaluation.adapter

import android.content.Context
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.evaluation.retrofit.R

/**
 * @author Vladyslav Havrylenko
 * @since 21.03.2020
 */
class CustomPagerAdapter(
    private val mContext: Context,
    private val mFragments: List<Fragment>,
    fragmentManager: FragmentManager
) :
    FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    @StringRes
    private val titles = intArrayOf(R.string.category_1, R.string.category_2)

    override fun getPageTitle(position: Int): CharSequence =
        mContext.resources.getString(titles[position])

    override fun getItem(position: Int): Fragment = mFragments[position]

    override fun getCount(): Int = mFragments.size

}