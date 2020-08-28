package com.apa.searchfortickets.ui.search;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.apa.searchfortickets.R;
import com.apa.searchfortickets.base.BaseDialogFragment;
import com.apa.searchfortickets.dagger.util.ViewModelFactory;
import com.apa.searchfortickets.data.remote.response.Place;
import com.apa.searchfortickets.util.Helper;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnTextChanged;

/**
 * Created by apavlenco on 8/25/20.
 */
public abstract class AbstractLocationDialogFragment extends BaseDialogFragment implements View.OnClickListener, PlaceListAdapter.PlaceClick {

    @Inject
    ViewModelFactory viewModelFactory;

    @BindView(R.id.progress)
    ProgressBar progressBar;

    @BindView(R.id.locations)
    RecyclerView recyclerView;

    @BindView(R.id.dialog_toolbar)
    Toolbar dialogToolbar;

    @BindView(R.id.search)
    EditText searchEdit;

    SearchViewModel searchViewModel;

    @Override
    protected int layoutRes() {
        return R.layout.location_autocomplete_dialog;
    }

    @OnTextChanged(R.id.search)
    public void fromTextChanged(CharSequence charSequence) {
        searchViewModel.requestLocations(charSequence.toString());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        searchViewModel = ViewModelProviders.of(getBaseActivity(), viewModelFactory).get(SearchViewModel.class);
        recyclerView.addItemDecoration(new DividerItemDecoration(getBaseActivity(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(new PlaceListAdapter(searchViewModel, this, this));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        dialogToolbar.setNavigationIcon(R.drawable.ic_clear_white_24dp);
        dialogToolbar.setNavigationOnClickListener(this);

        setSearchFieldAppearance();
        observeViewModel();

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getContext(), R.style.AppDialogTheme);

        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(true);

        return dialog;
    }

    private void observeViewModel() {

        searchViewModel.getPlaceError().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isError) {
                if (isError != null) if (isError) {
                    Helper.createErrorDialog(getBaseActivity(), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            AbstractLocationDialogFragment.this.dismiss();
                        }
                    }).show();
                    searchViewModel.getPlaceError().removeObserver(this);
                }
            }
        });


        searchViewModel.getPlaceLoading().observe(this, isLoading -> {
            if (isLoading != null) {
                progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
            }
        });

    }

    /*
    Navigation item click - white cross icon
     */
    @Override
    public void onClick(View v) {
        dismiss();
    }

    @Override
    public void onPlaceSelected(Place place) {
        dismiss();
    }

    public abstract void setSearchFieldAppearance();
}
