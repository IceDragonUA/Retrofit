package com.evaluation.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.evaluation.adapter.CustomListAdapter;
import com.evaluation.dagger.data.DataComponent;
import com.evaluation.model.search.SearchList;
import com.evaluation.network.RestAdapter;
import com.evaluation.retrofit.MainActivity;
import com.evaluation.retrofit.R;
import com.evaluation.viewmodel.PageViewModel;

import org.jetbrains.annotations.NotNull;

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
public class MainFragment extends Fragment {

    public final String TAG = MainFragment.class.getCanonicalName();

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    private MainActivity mActivity;

    private PageViewModel mPageViewModel;

    private View mRootView;

    @Inject
    RestAdapter restAdapter;

    @BindView(R.id.recycler_view)
    RecyclerView recycleView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DataComponent.Injector.getComponent().inject(this);

        mPageViewModel = new ViewModelProvider(requireActivity()).get(PageViewModel.class);
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
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
                                selectedSearchResult -> mPageViewModel.setName(selectedSearchResult.getId())
                        );
                        recycleView.setAdapter(adapter);
                        mPageViewModel.setName(searchList.getSearchResultList().get(0).getId());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }
}
