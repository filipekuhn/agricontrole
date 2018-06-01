package com.filipe.agricontrole;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.filipe.agricontrole.adapter.PlotAdapter;
import com.filipe.agricontrole.data.repo.PlotRepo;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class PlotActivity extends AppCompatActivity {

    private int periodId;
    private String periodName;
    private RecyclerView recyclerView;
    private PlotRepo plotHelper;
    private PlotAdapter plotAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plot);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.createPlot);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createPlotActivity();
            }
        });

        periodId = getIntent().getExtras().getInt("periodId");
        periodName = getIntent().getExtras().getString("periodName");
        configurePlotRecycler(periodId);

    }

    private void configurePlotRecycler(int index) {
        // Configurando o gerenciador de layout para ser uma lista.
        recyclerView = (RecyclerView)findViewById(R.id.plotRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // Adiciona o adapter que irá anexar os objetos à lista.
        plotHelper = new PlotRepo();
        plotAdapter = new PlotAdapter(plotHelper.findAllByPeriodId(index), PlotActivity.this);
        recyclerView.setAdapter(plotAdapter);

        plotAdapter.notifyDataSetChanged();
    }

    public void createPlotActivity(){
        Intent intent = new Intent(this, CreatePlotActivity.class);
        intent.putExtra("periodId", periodId);
        intent.putExtra("periodName", periodName);
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
