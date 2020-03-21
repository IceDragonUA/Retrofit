package com.evaluation.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.evaluation.dagger.data.DataComponent;
import com.evaluation.model.asset.Asset;
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
public class DetailFragment extends Fragment {

    public final String TAG = DetailFragment.class.getCanonicalName();

    private static String BASE_IMAGE_URL = "https://image.tmdb.org/t/p/w780";

    public static DetailFragment newInstance() {
        return new DetailFragment();
    }

    private MainActivity mActivity;

    private PageViewModel mPageViewModel;

    private View mRootView;

    @Inject
    RestAdapter restAdapter;

    @BindView(R.id.logo)
    ImageView logoView;

    @BindView(R.id.title)
    TextView titleView;

    @BindView(R.id.description)
    TextView descriptionView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DataComponent.Injector.getComponent().inject(this);
        mPageViewModel = new ViewModelProvider(requireActivity()).get(PageViewModel.class);
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = inflater.inflate(R.layout.info_layout, container, false);
            ButterKnife.bind(this, mRootView);
            loadAssetDetail();
        }
        return mRootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActivity = (MainActivity) getActivity();
    }

    private void loadAssetDetail() {
        mPageViewModel.getName().observe(requireActivity(), id ->
                restAdapter.getRestApiService().getAssetById(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Asset>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(Asset asset) {
                        Glide.with(mActivity)
                                .load(BASE_IMAGE_URL + asset.getPosterPath())
                                .into(logoView);

                        titleView.setText(asset.getTitle());
                        descriptionView.setText(asset.getOverview());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: ", e);
                    }
                }));


    }
}
