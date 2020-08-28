package com.apa.searchfortickets.ui.search;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.apa.searchfortickets.R;
import com.apa.searchfortickets.data.remote.response.Place;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by apavlenco on 8/26/20.
 */
public class PlaceListAdapter extends RecyclerView.Adapter<PlaceListAdapter.PlaceViewHolder> {

    private final List<Place> data = new ArrayList<>();
    private PlaceClick listener;

    public PlaceListAdapter(SearchViewModel viewModel, LifecycleOwner lifecycleOwner, PlaceClick listener) {
        this.listener = listener;
        viewModel.getPlaces().observe(lifecycleOwner, places -> {
            data.clear();
            if (places != null) {
                data.addAll(places);
                notifyDataSetChanged();
            }
        });
    }

    @NonNull
    @Override
    public PlaceListAdapter.PlaceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.place_list_item, parent, false);
        return new PlaceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlaceViewHolder holder, int position) {
        holder.bind(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public interface PlaceClick {
        public void onPlaceSelected(Place place);
    }

    final class PlaceViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.place_name)
        TextView placeName;
        @BindView(R.id.country_name)
        TextView countryName;


        PlaceViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(Place place) {
            placeName.setText(place.getPlaceName());
            countryName.setText(place.getCountryName());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onPlaceSelected(place);
                }
            });

        }
    }
}
