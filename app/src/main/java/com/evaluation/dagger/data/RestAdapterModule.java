package com.evaluation.dagger.data;

import com.evaluation.network.RestAdapter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * @author Vladyslav Havrylenko
 * @since 09.03.2020
 */
@Module
class RestAdapterModule {

    private static RestAdapterModule instance;

    RestAdapterModule() {
    }

    static RestAdapterModule getInstance() {
        if (instance == null) {
            instance = new RestAdapterModule();
        }
        return instance;
    }

    @Provides
    @Singleton
    RestAdapter provideRestAdapter() {
        return new RestAdapter();
    }

}
