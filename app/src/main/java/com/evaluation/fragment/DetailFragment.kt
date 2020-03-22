package com.evaluation.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import butterknife.BindView
import butterknife.ButterKnife
import com.bumptech.glide.Glide
import com.evaluation.dagger.data.DataComponent.Injector.component
import com.evaluation.model.asset.Asset
import com.evaluation.network.RestAdapter
import com.evaluation.retrofit.MainActivity
import com.evaluation.retrofit.R
import com.evaluation.utils.RxUtils
import com.evaluation.utils.RxUtils.disposeSilently
import com.evaluation.viewmodel.PageViewModel
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * @author Vladyslav Havrylenko
 * @since 09.03.2020
 */
class DetailFragment : Fragment() {

    private val TAG = DetailFragment::class.java.canonicalName

    companion object {
        private const val BASE_IMAGE_URL = "https://image.tmdb.org/t/p/w780"

        fun newInstance(): DetailFragment {
            return DetailFragment()
        }
    }

    lateinit var mPageViewModel: PageViewModel

    private var mDisposable: Disposable? = null
    private var mRootView: View? = null

    @Inject
    lateinit var restAdapter: RestAdapter

    @BindView(R.id.logo)
    lateinit var logoView: ImageView

    @BindView(R.id.title)
    lateinit var titleView: TextView

    @BindView(R.id.description)
    lateinit var descriptionView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        component.inject(this)
        mPageViewModel = ViewModelProvider(requireActivity()).get(PageViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (mRootView == null) {
            val view = inflater.inflate(R.layout.info_layout, container, false)
            ButterKnife.bind(this, view)
            mRootView = view
            loadAssetDetail()
        }
        return mRootView
    }

    private fun loadAssetDetail() {
        mPageViewModel.assetId.observe(
            requireActivity(),
            Observer { id: Int ->
                disposeSilently(mDisposable)
                restAdapter.restApiService.getAssetById(id, "en-US")
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : SingleObserver<Asset> {

                        override fun onSubscribe(disposable: Disposable) {
                            mDisposable = disposable
                        }

                        override fun onSuccess(asset: Asset) {

                            activity?.let {
                                Glide.with(it)
                                    .load(BASE_IMAGE_URL + asset.posterPath)
                                    .into(logoView)
                            }

                            titleView.text = asset.title
                            descriptionView.text = asset.overview
                        }

                        override fun onError(e: Throwable) {
                            Log.e(TAG, "onError: ", e)
                        }
                    })
            }
        )
    }
}