package com.apa.searchfortickets.ui.search;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.apa.searchfortickets.R;
import com.apa.searchfortickets.data.local.model.QuoteModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by apavlenco on 8/24/20.
 */
public class SearchListAdapter extends RecyclerView.Adapter<SearchListAdapter.QuoteViewHolder> {

    private final List<QuoteModel> data = new ArrayList<>();

    public SearchListAdapter(SearchViewModel viewModel, LifecycleOwner lifecycleOwner) {
        viewModel.getQuotes().observe(lifecycleOwner, quotes -> {
            data.clear();
            if (quotes != null) {
                data.addAll(quotes);
                notifyDataSetChanged();
            }
        });
    }

    @NonNull
    @Override
    public QuoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.quote_list_item, parent, false);
        return new QuoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuoteViewHolder holder, int position) {
        holder.bind(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public long getItemId(int position) {
        return data.get(position).getId();
    }

    static final class QuoteViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.departure_arrival)
        TextView depArrival;
        @BindView(R.id.airline)
        TextView airline;
        @BindView(R.id.price)
        TextView price;

        QuoteViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(QuoteModel quote) {
            final Context context = itemView.getContext();
            depArrival.setText(context.getString(R.string.from_to_format, quote.getOrigin(), quote.getDestination()));
            airline.setText(quote.getCarrierName());
            price.setText(String.valueOf(quote.getPrice()));

        }
    }
}

