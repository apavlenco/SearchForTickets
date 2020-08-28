package com.apa.searchfortickets.ui.currency;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.apa.searchfortickets.R;
import com.apa.searchfortickets.data.local.model.CurrencyModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by apavlenco on 8/25/20.
 */
public class CurrencyAdapter extends RecyclerView.Adapter<CurrencyAdapter.CurrencyViewHolder>
        implements Filterable {

    private List<CurrencyModel> originalList;
    private List<CurrencyModel> filteredList;
    private CurrencyAdapterListener listener;

    public CurrencyAdapter(CurrencyViewModel viewModel, LifecycleOwner lifecycleOwner, CurrencyAdapterListener listener) {
        this.originalList = new ArrayList<>();
        this.filteredList = new ArrayList<>();
        this.listener = listener;
        viewModel.getCurrencies().observe(lifecycleOwner, currencies -> {
            this.originalList.clear();
            this.filteredList.clear();
            if (currencies != null) {
                this.originalList.addAll(currencies);
                this.filteredList.addAll(currencies);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public CurrencyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.currency_list_item, parent, false);

        return new CurrencyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CurrencyViewHolder holder, final int position) {
        final CurrencyModel currencyModel = filteredList.get(position);
        holder.bind(currencyModel);

    }

    @Override
    public int getItemCount() {
        return filteredList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    filteredList = originalList;
                } else {
                    List<CurrencyModel> filteredList = new ArrayList<>();
                    for (CurrencyModel quoteModel : originalList) {
                        if (quoteModel.getName().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(quoteModel);
                        }
                    }
                    CurrencyAdapter.this.filteredList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filteredList = (ArrayList<CurrencyModel>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public interface CurrencyAdapterListener {
        void onCurrencySelected(CurrencyModel contact);
    }

    public class CurrencyViewHolder extends RecyclerView.ViewHolder {
        public TextView name;

        public CurrencyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.name);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // send selected contact in callback
                    listener.onCurrencySelected(filteredList.get(getAdapterPosition()));
                }
            });
        }

        void bind(CurrencyModel currencyModel) {
            Context context = name.getContext();
            name.setText(context.getString(R.string.currency_list_item_format, currencyModel.getName(), currencyModel.getSymbol()));
        }
    }
}
