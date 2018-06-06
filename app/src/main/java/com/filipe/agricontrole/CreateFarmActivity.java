package com.filipe.agricontrole;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.filipe.agricontrole.adapter.FarmAdapter;
import com.filipe.agricontrole.data.model.Agronomist;
import com.filipe.agricontrole.data.model.City;
import com.filipe.agricontrole.data.model.Farm;
import com.filipe.agricontrole.data.model.State;
import com.filipe.agricontrole.data.model.Stock;
import com.filipe.agricontrole.data.repo.AgronomistRepo;
import com.filipe.agricontrole.data.repo.CityRepo;
import com.filipe.agricontrole.data.repo.FarmRepo;
import com.filipe.agricontrole.data.repo.StateRepo;
import com.filipe.agricontrole.data.repo.StockRepo;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class CreateFarmActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Agronomist agronomist;
    Farm farm;
    Stock stock;
    AgronomistRepo agronomistHelper;
    FarmRepo farmHelper;
    StockRepo stockHelper;
    StateRepo stateRepo;
    CityRepo cityRepo;
    RecyclerView recyclerView;
    FarmAdapter adapter;

    private EditText edtName, edtOwner, edtAddress;
    private Spinner spinnerCity, spinnerState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_farm);

        edtName = (EditText) findViewById(R.id.farm_edtName);
        edtOwner = (EditText) findViewById(R.id.farm_edtOwner);
        edtAddress = (EditText) findViewById(R.id.farm_edtAddress);
        spinnerState = (Spinner) findViewById(R.id.farm_spinnerState);
        spinnerCity = (Spinner) findViewById(R.id.farm_spinnerCity);
        spinnerState.setOnItemSelectedListener(this);

        fillStateSpinner();
        agronomistHelper = new AgronomistRepo();
        farmHelper = new FarmRepo();
        stockHelper = new StockRepo();
    }

    @Override
    public void onItemSelected(AdapterView<?> arg0, View view, int position,
                               long id) {
        cityRepo = new CityRepo();
        List<City> cityList = cityRepo.findCityByState(id + 1); //ARRUMAR DEPOIS!

        ArrayAdapter<City> cityAdapter = new ArrayAdapter<City>(this, android.R.layout.simple_spinner_item, cityList);
        cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cityAdapter.notifyDataSetChanged();
        spinnerCity.setAdapter(cityAdapter);

    }
    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

    }

    public void createFarm(View view){
        //take the present agronomist logged on by email saved on the shared preferences
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String agronomistEmail = preferences.getString("email", null);

        agronomist = agronomistHelper.findByEmail(agronomistEmail);

        edtName = (EditText) findViewById(R.id.farm_edtName);
        edtOwner = (EditText) findViewById(R.id.farm_edtOwner);
        edtAddress = (EditText) findViewById(R.id.farm_edtAddress);
        spinnerState = (Spinner) findViewById(R.id.farm_spinnerState);
        spinnerCity = (Spinner) findViewById(R.id.farm_spinnerCity);

        //Cast city object from spinner to take her ID to set on FARM table
        City cityAux = (City) (spinnerCity).getSelectedItem();

        int agronomistId = agronomist.getId();
        String name = edtName.getText().toString().trim();
        String owner = edtOwner.getText().toString().trim();
        String address = edtAddress.getText().toString().trim();

        if((name == null || name.equals("") || (owner == null || owner.equals("")) ||
            address == null || address.equals(""))){

            new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Não foi possível concluir o cadastro")
                    .setContentText("É necessário preencher todos os campos")
                    .show();
        }else{
            farm = new Farm(); //Initiate a new Farm

            farm.setAgronomistId(agronomistId);
            farm.setName(name);
            farm.setOwner(owner);
            farm.setAddress(address);
            farm.setCity(cityAux);

            int farmId = farmHelper.insert(farm);

            if(farmId > 0) {
                stock = new Stock(); //Initiate a new Stock for the farm with the ID created in the farm creation.

                stock.setFarmId(farmId);
                if (stockHelper.insert(stock) > 0) {
                    new SweetAlertDialog(this, SweetAlertDialog.BUTTON_CONFIRM).setConfirmButton("OK", new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            farmActivity();
                        }
                    })
                            .setTitleText("Cadastro realizado com sucesso")
                            .show();
                }
            }
            else{
                new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Algo deu errado")
                        .setContentText("Confira os dados preenchidos")
                        .show();
            }
        }
    }


    private void fillStateSpinner(){
        stateRepo = new StateRepo();
        spinnerState = (Spinner) findViewById(R.id.farm_spinnerState);
        spinnerCity = (Spinner) findViewById(R.id.farm_spinnerCity);

        List<State> stateList = stateRepo.findAllNames();

        ArrayAdapter<State> adapter = new ArrayAdapter<State>(this, android.R.layout.simple_spinner_item, stateList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerState.setAdapter(adapter);

        List<String> cityList = new ArrayList<>();

        ArrayAdapter cityAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, cityList);
        cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCity.setAdapter(cityAdapter);
    }

    public void farmActivity(){
        startActivity(new Intent(this, FarmActivity.class));
        finish();
    }
}
