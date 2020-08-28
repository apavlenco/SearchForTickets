package com.apa.searchfortickets.ui.search;

import androidx.lifecycle.Observer;

import com.apa.searchfortickets.R;
import com.apa.searchfortickets.data.remote.response.Place;

/**
 * Created by apavlenco on 8/26/20.
 */
public class OutboundLocationDialogFragment extends AbstractLocationDialogFragment {

    public static OutboundLocationDialogFragment getInstance() {
        return new OutboundLocationDialogFragment();
    }

    @Override
    public void onPlaceSelected(Place place) {
        searchViewModel.fromSelected(place);
        super.onPlaceSelected(place);
    }

    @Override
    public void setSearchFieldAppearance() {
        searchEdit.setHint(R.string.place_from);
        searchEdit.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_flight_takeoff_black_24dp, 0, 0, 0);

        searchViewModel.getFromData().observe(this, new Observer<Place>() {
            @Override
            public void onChanged(Place place) {
                if (place != null) {
                    searchEdit.setText(place.getPlaceName());
                    searchViewModel.getToData().removeObserver(this);
                }
            }
        });
    }

}
