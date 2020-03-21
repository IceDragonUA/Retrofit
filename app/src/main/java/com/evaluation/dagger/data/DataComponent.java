package com.evaluation.dagger.data;

import javax.inject.Singleton;

import dagger.Component;

/**
 * @author Vladyslav Havrylenko
 * @since 09.03.2020
 */
@Singleton
@Component(modules = {
        RestAdapterModule.class
})
public interface DataComponent extends DataGraph {

    final class Injector {

        private static DataComponent sComponent;

        private Injector() {
        }

        public static void init() {
            sComponent = DaggerDataComponent.builder()
                    .restAdapterModule(RestAdapterModule.getInstance())
                    .build();
        }

        public static DataComponent getComponent() {
            return sComponent;
        }
    }
}
