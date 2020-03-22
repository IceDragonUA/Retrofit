package com.evaluation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.bumptech.glide.Glide
import com.evaluation.adapter.CustomListAdapter.ListAdapterHolder
import com.evaluation.command.ICommand
import com.evaluation.model.search.SearchResult
import com.evaluation.retrofit.R

class CustomListAdapter(private val context: FragmentActivity?, private val searchResultList: MutableList<SearchResult>, private val clickCommand: ICommand<SearchResult>) :
    RecyclerView.Adapter<ListAdapterHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): ListAdapterHolder =
        ListAdapterHolder(context, LayoutInflater.from(viewGroup.context).inflate(R.layout.list_item, viewGroup, false))

    override fun onBindViewHolder(searchListAdapterHolder: ListAdapterHolder, position: Int) =
        searchListAdapterHolder.bind(getItem(position), clickCommand)

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

    class ListAdapterHolder(private val mContext: FragmentActivity?, view: View) : RecyclerView.ViewHolder(view) {

        companion object {
            private const val BASE_IMAGE_URL = "https://image.tmdb.org/t/p/w185"
        }

        @BindView(R.id.title)
        lateinit var titleView: TextView

        @BindView(R.id.thumbnail)
        lateinit var thumbnailView: ImageView

        init {
            ButterKnife.bind(this, view)
        }

        fun bind(selectedSearchResult: SearchResult, assetClickCommand: ICommand<SearchResult>) {

            itemView.setOnClickListener { assetClickCommand.execute(selectedSearchResult) }

            titleView.text = selectedSearchResult.title

            mContext?.let {
                Glide.with(it)
                    .load(BASE_IMAGE_URL + selectedSearchResult.posterPath)
                    .into(thumbnailView)
            }
        }
    }
}