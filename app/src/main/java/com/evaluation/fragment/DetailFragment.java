package com.evaluation.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.evaluation.dagger.data.DataComponent;
import com.evaluation.model.asset.Asset;
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
public class DetailFragment extends BaseFragment {
    public final String TAG = DetailFragment.class.getCanonicalName();

    public static final int NO_SELECTION = -1;

    private static final String EXTRA_ASSET_ID = "EXTRA_ASSET_ID";
    private static String BASE_IMAGE_URL = "https://image.tmdb.org/t/p/w780";

    public static DetailFragment newInstance(int assetId) {
        Bundle args = new Bundle();
        args.putInt(EXTRA_ASSET_ID, assetId);
        DetailFragment fragment = new DetailFragment();
        fragment.setArguments(args);
        return fragment;
    }
    private MainActivity mActivity;

    protected View mRootView;

    @Inject
    RestAdapter restAdapter;

    @BindView(R.id.logo)
    ImageView logoView;

    @BindView(R.id.title)
    TextView titleView;

    @BindView(R.id.description)
    TextView descriptionView;

    private int assetId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DataComponent.Injector.getComponent().inject(this);

        assetId = getArguments().getInt(EXTRA_ASSET_ID, NO_SELECTION);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = inflater.inflate(R.layout.info_layout, container, false);
            ButterKnife.bind(this, mRootView);
            loadAssetDetail(assetId);
        }
        return mRootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActivity = (MainActivity) getActivity();
    }

    private void loadAssetDetail(int assetId) {
        restAdapter.getRestApiService().getAssetById(assetId)
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

                    }
                });
    }

    public boolean onBackPressed() {
        return false;
    }
}
