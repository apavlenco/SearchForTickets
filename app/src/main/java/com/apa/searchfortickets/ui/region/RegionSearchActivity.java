package com.apa.searchfortickets.ui.region;

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
import com.apa.searchfortickets.data.local.model.RegionModel;
import com.apa.searchfortickets.util.Helper;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnTextChanged;

/**
 * Created by apavlenco on 8/25/20.
 */
public class RegionSearchActivity extends BaseActivity implements RegionAdapter.RegionAdapterListener {

    @Inject
    ViewModelFactory viewModelFactory;

    @BindView(R.id.list)
    RecyclerView listView;

    @BindView(R.id.progress)
    ProgressBar progressBar;

    private RegionViewModel regionViewModel;
    private RegionAdapter regionAdapter;

    public static Intent getNewIntent(Context context) {
        return new Intent(context, RegionSearchActivity.class);
    }

    @Override
    protected int layoutRes() {
        return R.layout.search_list;
    }

    @OnTextChanged(R.id.search_field)
    public void searchChanged(CharSequence search) {
        regionAdapter.getFilter().filter(search);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        regionViewModel = ViewModelProviders.of(this, viewModelFactory).get(RegionViewModel.class);

        listView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        regionAdapter = new RegionAdapter(regionViewModel, this, this);
        listView.setAdapter(regionAdapter);
        listView.setLayoutManager(new LinearLayoutManager(this));

        observeViewModel();
        regionViewModel.requestMarkets();

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onRegionSelected(RegionModel regionModel) {
        regionViewModel.setRegion(regionModel);
        finish();
    }

    private void observeViewModel() {

        regionViewModel.getRegions().observe(this, repos -> {
            if (repos != null) listView.setVisibility(View.VISIBLE);
        });

        regionViewModel.getError().observe(this, isError -> {
            if (isError != null) if (isError) {
                Helper.createErrorDialog(RegionSearchActivity.this, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).show();
            }
        });

        regionViewModel.getLoading().observe(this, isLoading -> {
            if (isLoading != null) {
                progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
                if (isLoading) {
                    listView.setVisibility(View.GONE);
                }
            }
        });

    }
}