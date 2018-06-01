package com.filipe.agricontrole;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.filipe.agricontrole.adapter.PeriodAdapter;
import com.filipe.agricontrole.adapter.StockAdapter;
import com.filipe.agricontrole.data.repo.PeriodRepo;
import com.filipe.agricontrole.data.repo.StockRepo;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class FarmManagementActivity extends AppCompatActivity {

    private TextView mTextMessage;
    RecyclerView recyclerView;
    PeriodAdapter periodAdapter;
    StockAdapter stockAdapter;
    PeriodRepo periodHelper;
    StockRepo stockHelper;

    FloatingActionButton fab;

    int farmId;
    String farmName;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    //mTextMessage.setText(R.string.title_home);
                    configureStockRecycler(farmId);
                    return true;
                case R.id.navigation_dashboard:
                    //mTextMessage.setText(R.string.title_dashboard);
                    configurePeriodRecycler(farmId);

                    fab.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            createPeriodActivity();
                        }
                    });

                    return true;
                /*case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;*/
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farm_management);

        //mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        farmId = getIntent().getExtras().getInt("farmId");
        farmName = getIntent().getExtras().getString("farmName");
        configureStockRecycler(farmId);

        fab = (FloatingActionButton) findViewById(R.id.createPeriod);
    }

    private void configurePeriodRecycler(int index) {
        // Configurando o gerenciador de layout para ser uma lista.
        recyclerView = (RecyclerView)findViewById(R.id.periodRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // Adiciona o adapter que irá anexar os objetos à lista.
        periodHelper = new PeriodRepo();
        periodAdapter = new PeriodAdapter(periodHelper.findAllByFarmId(index), FarmManagementActivity.this);
        recyclerView.setAdapter(periodAdapter);

        periodAdapter.notifyDataSetChanged();
    }

    private void configureStockRecycler(int index) {
        // Configurando o gerenciador de layout para ser uma lista.
        recyclerView = (RecyclerView)findViewById(R.id.periodRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // Adiciona o adapter que irá anexar os objetos à lista.
        stockHelper = new StockRepo();
        stockAdapter = new StockAdapter(stockHelper.findAllByFarmId(index));
        recyclerView.setAdapter(stockAdapter);

        stockAdapter.notifyDataSetChanged();
    }

    public void createPeriodActivity(){
        Intent intent = new Intent(this, CreatePeriodActivity.class);
        intent.putExtra("farmId", farmId);
        intent.putExtra("farmName", farmName);
        startActivity(intent);
    }

    public void deletePeriod(int position, int index){
        periodHelper = new PeriodRepo();

        new SweetAlertDialog(this, SweetAlertDialog.BUTTON_CONFIRM).setConfirmButton("OK", new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                if (periodHelper.delete(index)) {
                    periodAdapter.periodList.remove(position);
                    periodAdapter.notifyItemRemoved(position);
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

    public void updatePeriod(int position, int index){
        periodHelper = new PeriodRepo();
    }

    public void plotActivity(int index, String periodName){
        Intent intent = new Intent(this, PlotActivity.class);
        intent.putExtra("periodId", index);
        intent.putExtra("periodName", periodName);
        startActivity(intent);
    }
}
