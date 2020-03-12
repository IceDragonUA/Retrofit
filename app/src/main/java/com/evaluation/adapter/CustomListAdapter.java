package com.evaluation.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.evaluation.command.ICommand;
import com.evaluation.model.search.SearchResult;
import com.evaluation.retrofit.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CustomListAdapter extends RecyclerView.Adapter<CustomListAdapter.ListAdapterHolder> {

    private static String BASE_IMAGE_URL = "https://image.tmdb.org/t/p/w185";

    private final Context context;
    private final List<SearchResult> searchResultList;
    private final ICommand<SearchResult> clickCommand;


    public CustomListAdapter(Context context, List<SearchResult> searchResultList, ICommand<SearchResult> clickCommand) {
        this.context = context;
        this.searchResultList = searchResultList;
        this.clickCommand = clickCommand;
    }

    @NotNull
    @Override
    public ListAdapterHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        View rootView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item, viewGroup, false);
        return new ListAdapterHolder(context, rootView);
    }

    @Override
    public void onBindViewHolder(ListAdapterHolder searchListAdapterHolder, int position) {
        searchListAdapterHolder.bind(getItem(position), clickCommand);
    }

    private SearchResult getItem(int position) {
        return searchResultList.get(position);
    }

    @Override
    public int getItemCount() {
        return (null != searchResultList ? searchResultList.size() : 0);
    }

    static class ListAdapterHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.title)
        TextView titleView;

        @BindView(R.id.thumbnail)
        ImageView thumbnailView;

        private Context mContext;

        ListAdapterHolder(Context context, View view) {
            super(view);
            mContext = context;
            ButterKnife.bind(this, view);
        }

        void bind(final SearchResult selectedSearchResult, final ICommand<SearchResult> assetClickCommand) {

            titleView.setText(selectedSearchResult.getTitle());

            Glide.with(mContext)
                    .load(BASE_IMAGE_URL + selectedSearchResult.getPosterPath())
                    .into(thumbnailView);

            itemView.setOnClickListener(v -> assetClickCommand.execute(selectedSearchResult));
        }
    }
}
