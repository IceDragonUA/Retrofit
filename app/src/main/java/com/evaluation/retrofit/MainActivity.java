package com.evaluation.retrofit;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.evaluation.adapter.CustomPagerAdapter;
import com.evaluation.dagger.data.DataComponent;
import com.evaluation.fragment.DetailFragment;
import com.evaluation.fragment.MainFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.tabs)
    TabLayout mTab;

    @BindView(R.id.view_pager)
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DataComponent.Injector.init();

        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(MainFragment.newInstance());
        fragments.add(DetailFragment.newInstance());

        mViewPager.setAdapter(new CustomPagerAdapter(this, fragments, getSupportFragmentManager()));
        mTab.setupWithViewPager(mViewPager);
    }
}
