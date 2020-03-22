package com.evaluation.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.evaluation.dagger.data.DataComponent.Injector.component
import com.evaluation.model.asset.Asset
import com.evaluation.network.RestAdapter
import com.evaluation.retrofit.R
import com.evaluation.utils.RxUtils.disposeSilently
import com.evaluation.viewmodel.PageViewModel
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.info_layout.*
import javax.inject.Inject

/**
 * @author Vladyslav Havrylenko
 * @since 09.03.2020
 */
class DetailFragment : Fragment() {

    private val TAG = DetailFragment::class.java.canonicalName

    companion object {
        private const val BASE_IMAGE_URL = "https://image.tmdb.org/t/p/w780"

        fun newInstance(): DetailFragment = DetailFragment()
    }

    lateinit var mPageViewModel: PageViewModel

    private var mDisposable: Disposable? = null
    private var mRootView: View? = null

    @Inject
    lateinit var restAdapter: RestAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        component.inject(this)
        mPageViewModel = ViewModelProvider(requireActivity()).get(PageViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (mRootView == null) {
            mRootView = inflater.inflate(R.layout.info_layout, container, false)
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
                                    .into(logo)
                            }

                            title.text = asset.title
                            description.text = asset.overview
                        }

                        override fun onError(e: Throwable) {
                            Log.e(TAG, "onError: ", e)
                        }
                    })
            }
        )
    }
}