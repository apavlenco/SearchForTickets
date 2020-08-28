package com.apa.searchfortickets.ui.search;

import androidx.lifecycle.Observer;

import com.apa.searchfortickets.R;
import com.apa.searchfortickets.data.remote.response.Place;


/**
 * Created by apavlenco on 8/26/20.
 */
public class InboundLocationDialogFragment extends AbstractLocationDialogFragment {

    public static InboundLocationDialogFragment getInstance() {
        return new InboundLocationDialogFragment();
    }

    @Override
    public void onPlaceSelected(Place place) {
        super.onPlaceSelected(place);
        searchViewModel.toSelected(place);

    }

    @Override
    public void setSearchFieldAppearance() {
        searchEdit.setHint(R.string.place_to);
        searchEdit.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_flight_land_black_24dp, 0, 0, 0);

        searchViewModel.getToData().observe(this, new Observer<Place>() {
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