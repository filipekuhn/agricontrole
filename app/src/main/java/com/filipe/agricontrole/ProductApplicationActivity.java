package com.filipe.agricontrole;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.filipe.agricontrole.adapter.ProductApplicationAdapter;
import com.filipe.agricontrole.data.model.ProductApplication;
import com.filipe.agricontrole.data.repo.ProductApplicationRepo;

public class ProductApplicationActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProductApplicationAdapter adapter;
    private ProductApplicationRepo productApplicationHelper;
    private ProductApplication productApplication;

    int plotId, farmId, periodId, plantingId;
    String plotName, farmName, periodName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_application);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setLogo(R.drawable.ic_product_application);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createProductApplicationActivity();
            }
        });
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        plantingId = getIntent().getExtras().getInt("plantingId");
        plotId = getIntent().getExtras().getInt("plotId");
        plotName = getIntent().getExtras().getString("plotName");
        farmId = getIntent().getExtras().getInt("farmId");
        farmName = getIntent().getExtras().getString("farmName");
        periodId = getIntent().getExtras().getInt("periodId");
        periodName = getIntent().getExtras().getString("periodName");

        configureProductApplicationRecycler(plantingId);
    }

    private void configureProductApplicationRecycler(int index) {
        // Configurando o gerenciador de layout para ser uma lista.
        recyclerView = (RecyclerView)findViewById(R.id.productApplicationRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // Adiciona o adapter que irá anexar os objetos à lista.
        productApplicationHelper= new ProductApplicationRepo();
        adapter = new ProductApplicationAdapter(productApplicationHelper.findAllByPlantingId(index), ProductApplicationActivity.this);
        recyclerView.setAdapter(adapter);

        adapter.notifyDataSetChanged();
    }

    private void createProductApplicationActivity(){
        Intent intent = new Intent(this, CreateProductApplicationActivity.class);
        intent.putExtra("plantingId", plantingId);
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
