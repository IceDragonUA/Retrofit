package com.evaluation.navigation;

import android.app.FragmentTransaction;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.evaluation.fragment.BaseFragment;
import com.evaluation.fragment.DetailFragment;
import com.evaluation.fragment.MainFragment;
import com.evaluation.retrofit.MainActivity;
import com.evaluation.retrofit.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Vladyslav Havrylenko
 * @since 09.03.2020
 */
public class Navigator {

    private MainActivity mActivity;

    @BindView(R.id.fragment)
    ViewGroup mFragment;

    public void init(MainActivity activity) {
        mActivity = activity;
        ButterKnife.bind(this, mActivity);
    }

    public void showMainFragment() {
        MainFragment mainFragment = MainFragment.newInstance();
        showContentFragment(mainFragment);
    }

    public void showDetailFragment(int assetId) {
        DetailFragment detailFragment = DetailFragment.newInstance(assetId);
        showContentFragment(detailFragment);
    }

    private void showContentFragment(BaseFragment contentFragment) {
        FragmentTransaction fragmentTransaction = mActivity.getFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment, contentFragment, contentFragment.TAG)
                .addToBackStack(contentFragment.TAG);
        fragmentTransaction.commitAllowingStateLoss();
    }

    @Nullable
    public BaseFragment getContentFragment() {
        return (BaseFragment) mActivity.getFragmentManager().findFragmentById(R.id.fragment);
    }

}
