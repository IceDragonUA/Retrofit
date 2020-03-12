package com.evaluation.dagger.data

import com.evaluation.fragment.DetailFragment
import com.evaluation.fragment.MainFragment
import com.evaluation.retrofit.MainActivity

/**
 * @author Vladyslav Havrylenko
 * @since 09.03.2020
 */
internal interface DataGraph {
    fun inject(mainActivity: MainActivity)
    fun inject(mainFragment: MainFragment)
    fun inject(mainFragment: DetailFragment)
}