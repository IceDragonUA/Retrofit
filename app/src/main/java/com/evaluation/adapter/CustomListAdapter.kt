package com.evaluation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.evaluation.adapter.CustomListAdapter.ListAdapterHolder
import com.evaluation.model.search.SearchResult
import com.evaluation.retrofit.R
import kotlinx.android.synthetic.main.list_item.view.*

class CustomListAdapter(
    private val context: FragmentActivity?,
    private val searchResultList: MutableList<SearchResult>,
    private val clickCommand: (SearchResult) -> Unit
) :
    RecyclerView.Adapter<ListAdapterHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): ListAdapterHolder =
        ListAdapterHolder(
            LayoutInflater.from(viewGroup.context).inflate(R.layout.list_item, viewGroup, false),
            context,
            clickCommand
        )

    override fun onBindViewHolder(searchListAdapterHolder: ListAdapterHolder, position: Int) =
        searchListAdapterHolder.bind(getItem(position))

    private fun getItem(position: Int): SearchResult {
        return searchResultList[position]
    }

    override fun getItemCount(): Int {
        return searchResultList.size
    }

    fun insertList(position: Int, list: List<SearchResult>) {
        searchResultList.addAll(list)
        notifyItemRangeChanged(position, itemCount)
    }

    class ListAdapterHolder(
        view: View,
        private val mContext: FragmentActivity?,
        private val assetClickCommand: (SearchResult) -> Unit
    ) : RecyclerView.ViewHolder(view) {

        companion object {
            private const val BASE_IMAGE_URL = "https://image.tmdb.org/t/p/w185"
        }

        fun bind(selectedSearchResult: SearchResult) {

            itemView.setOnClickListener { assetClickCommand(selectedSearchResult) }

            itemView.title.text = selectedSearchResult.title

            mContext?.let {
                Glide.with(it)
                    .load(BASE_IMAGE_URL + selectedSearchResult.posterPath)
                    .into(itemView.thumbnail)
            }
        }
    }
}