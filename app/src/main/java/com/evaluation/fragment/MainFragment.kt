package com.evaluation.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.evaluation.adapter.CustomListAdapter
import com.evaluation.dagger.data.DataComponent.Injector.component
import com.evaluation.model.search.SearchList
import com.evaluation.network.RestAdapter
import com.evaluation.retrofit.R
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
class MainFragment : Fragment() {

    private val TAG = MainFragment::class.java.canonicalName

    private var mCurrentPage = 1
    private var mPageCount = Int.MAX_VALUE
    private var mSearchTerm: String = ""
    private var mIsLoading = false

    lateinit var mPageViewModel: PageViewModel

    private var mDisposable: Disposable? = null
    private var mRootView: View? = null

    @Inject
    lateinit var restAdapter: RestAdapter

    @BindView(R.id.searchView)
    lateinit var mSearchView: SearchView


    @BindView(R.id.listView)
    lateinit var mListView: RecyclerView

    lateinit var mAdapter: CustomListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        component.inject(this)
        mPageViewModel = ViewModelProvider(requireActivity()).get(PageViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (mRootView == null) {
            val view = inflater.inflate(R.layout.main_layout, container, false)
            ButterKnife.bind(this, view)
            mRootView = view

            val layoutManager = LinearLayoutManager(activity)
            mListView.layoutManager = layoutManager
            mListView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val visibleItemCount = layoutManager.childCount
                    val totalItemCount = layoutManager.itemCount
                    val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
                    if (!mIsLoading) {
                        if (visibleItemCount + firstVisibleItemPosition >= totalItemCount &&
                            firstVisibleItemPosition >= 0 &&
                            totalItemCount >= PAGE_SIZE) {
                                mIsLoading = true
                                mCurrentPage++
                                loadAssetList(mSearchTerm, true)
                        }
                    }
                }
            })

            mSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    mCurrentPage = 1
                    loadAssetList(query, false)
                    return false
                }

                override fun onQueryTextChange(newText: String): Boolean {
                    mCurrentPage = 1
                    loadAssetList(newText, false)
                    return false
                }
            })
        }
        return mRootView
    }

    private fun loadAssetList(query: String, insert: Boolean) {
        if (mCurrentPage > mPageCount) {
            return
        }
        disposeSilently(mDisposable)
        restAdapter.restApiService.getSearchData(query, "en-US", mCurrentPage, false)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<SearchList?> {

                override fun onSubscribe(disposable: Disposable) {
                    mDisposable = disposable
                }

                override fun onSuccess(searchList: SearchList) {
                    mIsLoading = false

                    if (searchList.searchResultList.isEmpty()) {
                        return
                    }

                    if (insert) {
                        mAdapter.insertList(mAdapter.itemCount, searchList.searchResultList)
                    } else {
                        mAdapter = CustomListAdapter(activity, searchList.searchResultList) { mPageViewModel.setAssetId(it.id) }
                        mListView.adapter = mAdapter
                        mPageViewModel.setAssetId(searchList.searchResultList[0].id)
                    }
                    mPageCount = searchList.totalPages
                    mSearchTerm = query
                }

                override fun onError(e: Throwable) {
                    mIsLoading = false
                    Log.e(TAG, "onError: ", e)
                }
            })
    }

    companion object {
        fun newInstance(): MainFragment {
            return MainFragment()
        }

        private const val PAGE_SIZE = 20
    }
}