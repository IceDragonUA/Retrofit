package com.evaluation.dagger.data

import com.evaluation.fragment.DetailFragment
import com.evaluation.fragment.MainFragment
import com.evaluation.retrofit.MainActivity

/**
 * @author Vladyslav Havrylenko
 * @since 09.03.2020
 */
interface DataGraph {
    fun inject(mainFragment: MainFragment)
    fun inject(mainFragment: DetailFragment)
}