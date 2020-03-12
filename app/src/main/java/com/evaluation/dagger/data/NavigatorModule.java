package com.evaluation.dagger.data;

import com.evaluation.navigation.Navigator;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * @author Vladyslav Havrylenko
 * @since 09.03.2020
 */
@Module
class NavigatorModule {

    private static NavigatorModule instance;

    NavigatorModule() {
    }

    static NavigatorModule getInstance() {
        if (instance == null) {
            instance = new NavigatorModule();
        }
        return instance;
    }

    @Provides
    @Singleton
    Navigator provideNavigator() {
        return new Navigator();
    }
}
