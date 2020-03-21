package com.evaluation.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.evaluation.retrofit.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @author Vladyslav Havrylenko
 * @since 21.03.2020
 */
public class CustomPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[] { R.string.category_1, R.string.category_2 };
    private final Context mContext;
    private List<Fragment> mFragments;

    public CustomPagerAdapter(Context context, List<Fragment> fragments, @NonNull FragmentManager fragmentManager) {
        super(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        mContext = context;
        mFragments = fragments;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @NotNull
    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

}
