package com.apa.searchfortickets.ui.currency;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.apa.searchfortickets.R;
import com.apa.searchfortickets.base.BaseActivity;
import com.apa.searchfortickets.dagger.util.ViewModelFactory;
import com.apa.searchfortickets.data.local.model.CurrencyModel;
import com.apa.searchfortickets.util.Helper;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnTextChanged;

/**
 * Created by apavlenco on 8/25/20.
 */
public class CurrencySearchActivity extends BaseActivity implements CurrencyAdapter.CurrencyAdapterListener {

    @Inject
    ViewModelFactory viewModelFactory;

    @BindView(R.id.list)
    RecyclerView listView;

    @BindView(R.id.progress)
    ProgressBar progressBar;

    private CurrencyViewModel currencyViewModel;
    private CurrencyAdapter currencyAdapter;

    public static Intent getNewIntent(Context context) {
        return new Intent(context, CurrencySearchActivity.class);
    }

    @Override
    protected int layoutRes() {
        return R.layout.search_list;
    }

    @OnTextChanged(R.id.search_field)
    public void searchChanged(CharSequence search) {
        currencyAdapter.getFilter().filter(search);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        currencyViewModel = ViewModelProviders.of(this, viewModelFactory).get(CurrencyViewModel.class);

        listView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        currencyAdapter = new CurrencyAdapter(currencyViewModel, this, this);
        listView.setAdapter(currencyAdapter);
        listView.setLayoutManager(new LinearLayoutManager(this));

        observeViewModel();
        currencyViewModel.requestCurrencies();

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onCurrencySelected(CurrencyModel currencyModel) {
        currencyViewModel.setCurrency(currencyModel);
        finish();
    }

    private void observeViewModel() {

        currencyViewModel.getCurrencies().observe(this, repos -> {
            if (repos != null) listView.setVisibility(View.VISIBLE);
        });

        currencyViewModel.getError().observe(this, isError -> {
            if (isError != null) if (isError) {
                Helper.createErrorDialog(CurrencySearchActivity.this, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).show();
            }
        });

        currencyViewModel.getLoading().observe(this, isLoading -> {
            if (isLoading != null) {
                progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
                if (isLoading) {
                    listView.setVisibility(View.GONE);
                }
            }
        });

    }
}
