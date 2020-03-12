package com.evaluation.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.evaluation.adapter.CustomListAdapter;
import com.evaluation.dagger.data.DataComponent;
import com.evaluation.model.search.SearchList;
import com.evaluation.navigation.Navigator;
import com.evaluation.network.RestAdapter;
import com.evaluation.retrofit.MainActivity;
import com.evaluation.retrofit.R;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author Vladyslav Havrylenko
 * @since 09.03.2020
 */
public class MainFragment extends BaseFragment {
    public final String TAG = MainFragment.class.getCanonicalName();

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    private MainActivity mActivity;

    protected View mRootView;

    @Inject
    Navigator mNavigator;

    @Inject
    RestAdapter restAdapter;

    @BindView(R.id.recycler_view)
    RecyclerView recycleView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DataComponent.Injector.getComponent().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = inflater.inflate(R.layout.main_layout, container, false);
            ButterKnife.bind(this, mRootView);
            recycleView.setLayoutManager(new LinearLayoutManager(mActivity));
            loadAssetList();
        }
        return mRootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActivity = (MainActivity) getActivity();
    }

    private void loadAssetList() {
        restAdapter.getRestApiService().getSearchData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<SearchList>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onSuccess(SearchList searchList) {
                        CustomListAdapter adapter = new CustomListAdapter(
                                mActivity,
                                searchList.getSearchResultList(),
                                selectedProject -> mNavigator.showDetailFragment(selectedProject.getId())
                        );
                        recycleView.setAdapter(adapter);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }
}
