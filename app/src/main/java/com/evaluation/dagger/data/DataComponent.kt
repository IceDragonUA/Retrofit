package com.evaluation.dagger.data

import dagger.Component
import javax.inject.Singleton

/**
 * @author Vladyslav Havrylenko
 * @since 09.03.2020
 */
@Singleton
@Component(modules = [RestAdapterModule::class])
interface DataComponent : DataGraph {

    object Injector {

        lateinit var component: DataComponent
            private set

        fun init() {
            component =
                DaggerDataComponent.builder()
                    .restAdapterModule(RestAdapterModule)
                    .build()
        }

    }
}