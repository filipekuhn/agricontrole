package com.filipe.agricontrole;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.filipe.agricontrole.adapter.ProductAdapter;
import com.filipe.agricontrole.data.model.Product;
import com.filipe.agricontrole.data.repo.ProductRepo;

public class ProductActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Product product;
    private ProductAdapter adapter;
    private ProductRepo productHelper;
    int farmId, stockId;
    String farmName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabCreateProduct);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createProductActivity();
            }
        });

        stockId = getIntent().getExtras().getInt("stockId");
        farmId = getIntent().getExtras().getInt("farmId");
        farmName = getIntent().getExtras().getString("farmName");
        configureStockRecycler(stockId);
    }

    private void configureStockRecycler(int index) {
        // Configurando o gerenciador de layout para ser uma lista.
        recyclerView = (RecyclerView)findViewById(R.id.productRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // Adiciona o adapter que irá anexar os objetos à lista.
        productHelper= new ProductRepo();
        adapter = new ProductAdapter(productHelper.findAllByStockId(index), ProductActivity.this);
        recyclerView.setAdapter(adapter);

        adapter.notifyDataSetChanged();
    }

    private void createProductActivity(){
        Intent intent = new Intent(this, CreateProductActivity.class);
        intent.putExtra("stockId", stockId);
        intent.putExtra("farmId", farmId);
        intent.putExtra("farmName", farmName);
        startActivity(intent);
        finish();
    }

}
