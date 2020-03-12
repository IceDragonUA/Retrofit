package com.evaluation.retrofit;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.evaluation.dagger.data.DataComponent;
import com.evaluation.fragment.BaseFragment;
import com.evaluation.navigation.Navigator;

import javax.inject.Inject;

import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @Inject
    Navigator mNavigator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DataComponent.Injector.init();

        setContentView(R.layout.activity_main);

        DataComponent.Injector.getComponent().inject(this);

        ButterKnife.bind(this);

        mNavigator.init(this);
        mNavigator.showMainFragment();
    }

    @Override
    public void onBackPressed() {
        BaseFragment currentFragment = mNavigator.getContentFragment();

        // Dispatch back event to the current fragment.
        if (currentFragment != null && currentFragment.onBackPressed()) {
            return;
        }
        super.onBackPressed();
    }
}
