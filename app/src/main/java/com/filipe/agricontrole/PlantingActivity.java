package com.filipe.agricontrole;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.filipe.agricontrole.adapter.PlantingAdapter;
import com.filipe.agricontrole.data.repo.PlantingRepo;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class PlantingActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PlantingRepo plantingHelper;
    private PlantingAdapter adapter;

    int plotId, farmId, periodId;
    String plotName, farmName, periodName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planting);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setLogo(R.drawable.ic_corn);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabCreatePlanting);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createPlantingActivity();
            }
        });

        plotId = getIntent().getExtras().getInt("plotId");
        plotName = getIntent().getExtras().getString("plotName");
        farmId = getIntent().getExtras().getInt("farmId");
        farmName = getIntent().getExtras().getString("farmName");
        periodId = getIntent().getExtras().getInt("periodId");
        periodName = getIntent().getExtras().getString("periodName");
        configurePlantingRecycler(plotId);

    }

    public void configurePlantingRecycler(int index) {
        // Configurando o gerenciador de layout para ser uma lista.
        recyclerView = (RecyclerView)findViewById(R.id.plantingRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // Adiciona o adapter que irá anexar os objetos à lista.
        plantingHelper = new PlantingRepo();
        adapter = new PlantingAdapter(plantingHelper.findAllByPlotId(index), PlantingActivity.this);
        recyclerView.setAdapter(adapter);

        adapter.notifyDataSetChanged();
    }

    private void createPlantingActivity(){
        Intent intent = new Intent(this, CreatePlantingActivity.class);
        intent.putExtra("plotId", plotId);
        intent.putExtra("plotName", plotName);
        intent.putExtra("farmId", farmId);
        intent.putExtra("farmName", farmName);
        intent.putExtra("periodId", periodId);
        intent.putExtra("periodName", periodName);
        startActivity(intent);
        finish();
    }

    public void productApplicationActivity(int index){
        Intent intent = new Intent(this, ProductApplicationActivity.class);
        intent.putExtra("plantingId", index);
        intent.putExtra("plotId", plotId);
        intent.putExtra("plotName", plotName);
        intent.putExtra("periodId", periodId);
        intent.putExtra("periodName", periodName);
        intent.putExtra("farmId", farmId);
        intent.putExtra("farmName", farmName);
        startActivity(intent);
    }

    public void deletePlanting(int position, int index){
        plantingHelper = new PlantingRepo();

        new cn.pedant.SweetAlert.SweetAlertDialog(this, SweetAlertDialog.BUTTON_CONFIRM).setConfirmButton("OK", new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                if (plantingHelper.delete(index)) {
                    adapter.plantingList.remove(position);
                    adapter.notifyItemRemoved(position);
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

    public void updatePlanting(int id){
        Intent intent = new Intent(this, UpdatePlantingActivity.class);
        intent.putExtra("plantingId", id);
        intent.putExtra("plotId", plotId);
        intent.putExtra("plotName", plotName);
        intent.putExtra("farmId", farmId);
        intent.putExtra("farmName", farmName);
        intent.putExtra("periodId", periodId);
        intent.putExtra("periodName", periodName);
        startActivity(intent);
        finish();
    }

}
