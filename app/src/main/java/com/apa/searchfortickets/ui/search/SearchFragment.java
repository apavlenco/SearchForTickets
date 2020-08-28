package com.apa.searchfortickets.ui.search;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.apa.searchfortickets.R;
import com.apa.searchfortickets.base.BaseFragment;
import com.apa.searchfortickets.dagger.util.ViewModelFactory;
import com.apa.searchfortickets.data.remote.response.Place;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class SearchFragment extends BaseFragment implements DatePickerDialog.OnDateSetListener {

    @BindView(R.id.from)
    EditText fromEdit;
    @BindView(R.id.to)
    EditText toEdit;
    @BindView(R.id.date)
    EditText dateEdit;
    @BindView(R.id.tickets)
    RecyclerView listView;
    @BindView(R.id.error_label)
    TextView errorTextView;
    @BindView(R.id.progress)
    ProgressBar progressBar;

    @Inject
    ViewModelFactory viewModelFactory;

    private SearchViewModel searchViewModel;

    @Override
    protected int layoutRes() {
        return R.layout.fragment_search;
    }

    @OnClick(R.id.from)
    public void clickFrom() {
        OutboundLocationDialogFragment departureLocationDialogFragment = OutboundLocationDialogFragment.getInstance();
        departureLocationDialogFragment.show(getBaseActivity().getSupportFragmentManager(), "place_dia");
    }

    @OnClick(R.id.to)
    public void clickTo() {
        InboundLocationDialogFragment inboundLocationDialogFragment = InboundLocationDialogFragment.getInstance();
        inboundLocationDialogFragment.show(getBaseActivity().getSupportFragmentManager(), "place_dia");
    }

    @OnClick(R.id.search_button)
    public void clickSearch() {
        searchViewModel.requestQuotes();
    }

    @OnClick(R.id.date)
    public void onDateClick() {
        final Calendar c = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getBaseActivity(), this, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        searchViewModel = ViewModelProviders.of(getBaseActivity(), viewModelFactory).get(SearchViewModel.class);
        listView.addItemDecoration(new DividerItemDecoration(getBaseActivity(), DividerItemDecoration.VERTICAL));
        listView.setAdapter(new SearchListAdapter(searchViewModel, this));
        listView.setLayoutManager(new LinearLayoutManager(getContext()));

        observeViewModel();
    }

    private void observeViewModel() {

        searchViewModel.getQuotes().observe(this, repos -> {
            if (repos != null) listView.setVisibility(View.VISIBLE);
        });

        searchViewModel.getError().observe(this, isError -> {
            if (isError != null) if (isError) {
                errorTextView.setVisibility(View.VISIBLE);
                listView.setVisibility(View.GONE);
                errorTextView.setText("An Error Occurred While Loading Data!");
            } else {
                errorTextView.setVisibility(View.GONE);
                errorTextView.setText(null);
            }
        });

        searchViewModel.getLoading().observe(this, isLoading -> {
            if (isLoading != null) {
                progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
                if (isLoading) {
                    errorTextView.setVisibility(View.GONE);
                    listView.setVisibility(View.GONE);
                }
            }
        });

        searchViewModel.getDate().observe(this, new Observer<Date>() {
            @Override
            public void onChanged(Date date) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                String formatedDate = sdf.format(date);
                dateEdit.setText(formatedDate);
            }
        });

        searchViewModel.getFromData().observe(this, new Observer<Place>() {
            @Override
            public void onChanged(Place place) {
                fromEdit.setText(place.getPlaceName());
            }
        });

        searchViewModel.getToData().observe(this, new Observer<Place>() {
            @Override
            public void onChanged(Place place) {
                toEdit.setText(place.getPlaceName());
            }
        });
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        searchViewModel.dateSelected(dayOfMonth, month, year);
    }
}