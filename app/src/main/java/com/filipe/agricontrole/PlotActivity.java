package com.filipe.agricontrole;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.filipe.agricontrole.adapter.PlotAdapter;
import com.filipe.agricontrole.data.repo.PlotRepo;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class PlotActivity extends AppCompatActivity {

    private int periodId, farmId;
    private String periodName, farmName;
    private RecyclerView recyclerView;
    private PlotRepo plotHelper;
    private PlotAdapter plotAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plot);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setLogo(R.drawable.ic_plot);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.createPlot);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createPlotActivity();
            }
        });

        periodId = getIntent().getExtras().getInt("periodId");
        periodName = getIntent().getExtras().getString("periodName");
        farmId = getIntent().getExtras().getInt("farmId");
        farmName = getIntent().getExtras().getString("farmName");
        configurePlotRecycler(periodId);

    }

    private void configurePlotRecycler(int index) {
        // Configure the layout manager to be a list
        recyclerView = (RecyclerView)findViewById(R.id.plotRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // Add the adapter that include the objects to the list.
        plotHelper = new PlotRepo();
        plotAdapter = new PlotAdapter(plotHelper.findAllByPeriodId(index), PlotActivity.this);
        recyclerView.setAdapter(plotAdapter);

        plotAdapter.notifyDataSetChanged();
    }

    private void createPlotActivity(){
        Intent intent = new Intent(this, CreatePlotActivity.class);
        intent.putExtra("periodId", periodId);
        intent.putExtra("periodName", periodName);
        intent.putExtra("farmId", farmId);
        intent.putExtra("farmName", farmName);
        startActivity(intent);
        finish();
    }

    public void plantingActivity(int index, String plotName){
        Intent intent = new Intent(this, PlantingActivity.class);
        intent.putExtra("plotId", index);
        intent.putExtra("plotName", plotName);
        intent.putExtra("periodId", periodId);
        intent.putExtra("periodName", periodName);
        intent.putExtra("farmId", farmId);
        intent.putExtra("farmName", farmName);
        startActivity(intent);
    }

    public void deletePlot(int position, int index){
        plotHelper = new PlotRepo();

        new SweetAlertDialog(this, SweetAlertDialog.BUTTON_CONFIRM).setConfirmButton("OK", new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                if (plotHelper.delete(index)) {
                    plotAdapter.plotList.remove(position);
                    plotAdapter.notifyItemRemoved(position);
                    sweetAlertDialog.dismissWithAnimation();
                }
            }
        }).setTitleText("Deseja Excluir?").setCancelButton("Cancelar", new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                sweetAlertDialog.cancel();
            }
        }).show();
    }
}
