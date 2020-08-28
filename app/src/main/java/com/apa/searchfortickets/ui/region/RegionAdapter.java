package com.apa.searchfortickets.ui.region;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.apa.searchfortickets.R;
import com.apa.searchfortickets.data.local.model.RegionModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by apavlenco on 8/25/20.
 */
public class RegionAdapter extends RecyclerView.Adapter<RegionAdapter.RegionViewHolder>
        implements Filterable {

    private List<RegionModel> originalList;
    private List<RegionModel> filteredList;
    private RegionAdapterListener listener;

    public RegionAdapter(RegionViewModel viewModel, LifecycleOwner lifecycleOwner, RegionAdapterListener listener) {
        this.originalList = new ArrayList<>();
        this.filteredList = new ArrayList<>();
        this.listener = listener;
        viewModel.getRegions().observe(lifecycleOwner, currencies -> {
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
    public RegionAdapter.RegionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.currency_list_item, parent, false);

        return new RegionViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RegionAdapter.RegionViewHolder holder, final int position) {
        final RegionModel currencyModel = filteredList.get(position);
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
                    List<RegionModel> filteredList = new ArrayList<>();
                    for (RegionModel quoteModel : originalList) {
                        if (quoteModel.getName().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(quoteModel);
                        }
                    }
                    RegionAdapter.this.filteredList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filteredList = (ArrayList<RegionModel>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public interface RegionAdapterListener {
        void onRegionSelected(RegionModel contact);
    }

    public class RegionViewHolder extends RecyclerView.ViewHolder {
        public TextView name;

        public RegionViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.name);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // send selected contact in callback
                    listener.onRegionSelected(filteredList.get(getAdapterPosition()));
                }
            });
        }

        void bind(RegionModel regionModel) {
            name.setText(regionModel.getName());
        }
    }
}
