package com.evaluation.fragment;

import android.app.Fragment;

/**
 * @author Vladyslav Havrylenko
 * @since 09.03.2020
 */
public class BaseFragment extends Fragment {
    public final String TAG = this.getClass().getCanonicalName();

    public boolean onBackPressed() {
        return true;
    }
}
