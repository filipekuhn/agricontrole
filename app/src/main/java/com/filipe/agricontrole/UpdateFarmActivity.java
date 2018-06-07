package com.filipe.agricontrole;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.filipe.agricontrole.data.model.City;
import com.filipe.agricontrole.data.model.Farm;
import com.filipe.agricontrole.data.model.State;
import com.filipe.agricontrole.data.repo.CityRepo;
import com.filipe.agricontrole.data.repo.FarmRepo;
import com.filipe.agricontrole.data.repo.StateRepo;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class UpdateFarmActivity extends Activity {

    private EditText edtName, edtOwner, edtAddress;
    private Spinner spinnerState, spinnerCity;
    private Farm farm;
    private FarmRepo farmHelper;
    private State state;
    private City city;
    private StateRepo stateHelper;
    private CityRepo cityHelper;
    private int farmId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_farm);

        farmId = getIntent().getExtras().getInt("farmId");
        edtName = (EditText) findViewById(R.id.farm_edtName);
        edtOwner = (EditText) findViewById(R.id.farm_edtOwner);
        edtAddress = (EditText) findViewById(R.id.farm_edtAddress);
        spinnerState = (Spinner) findViewById(R.id.farm_spinnerState);
        spinnerCity = (Spinner) findViewById(R.id.farm_spinnerCity);

        insertText();
    }

    private void insertText() {
        farm = new Farm();
        farmHelper = new FarmRepo();

        farm = farmHelper.findById(farmId);

        edtName.setText(farm.getName());
        edtOwner.setText(farm.getOwner());
        edtAddress.setText(farm.getAddress());
        fillStateSpinner();
    }

    private void fillStateSpinner() {
        stateHelper = new StateRepo();
        spinnerState = (Spinner) findViewById(R.id.farm_spinnerState);
        spinnerCity = (Spinner) findViewById(R.id.farm_spinnerCity);

        List<State> stateList = stateHelper.findAllNames();

        ArrayAdapter<State> adapter = new ArrayAdapter<State>(this, android.R.layout.simple_spinner_item, stateList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerState.setAdapter(adapter);

        List<String> cityList = new ArrayList<>();

        ArrayAdapter cityAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, cityList);
        cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCity.setAdapter(cityAdapter);

        spinnerState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cityHelper = new CityRepo();
                List<City> cityList = cityHelper.findCityByState(id + 1); //ARRUMAR DEPOIS!

                ArrayAdapter<City> cityAdapter = new ArrayAdapter<City>(UpdateFarmActivity.this, android.R.layout.simple_spinner_item, cityList);
                cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                cityAdapter.notifyDataSetChanged();
                spinnerCity.setAdapter(cityAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void updateFarm(View view){

        edtName = (EditText) findViewById(R.id.farm_edtName);
        edtOwner = (EditText) findViewById(R.id.farm_edtOwner);
        edtAddress = (EditText) findViewById(R.id.farm_edtAddress);
        spinnerState = (Spinner) findViewById(R.id.farm_spinnerState);
        spinnerCity = (Spinner) findViewById(R.id.farm_spinnerCity);

        //Cast city object from spinner to take her ID to set on FARM table
        City cityAux = (City) (spinnerCity).getSelectedItem();

        int id = farm.getId();
        int agronomistId = farm.getAgronomistId();
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

            farm.setId(id);
            farm.setAgronomistId(agronomistId);
            farm.setName(name);
            farm.setOwner(owner);
            farm.setAddress(address);
            farm.setCity(cityAux);

            int farmId = farmHelper.update(farm);

            if(farmId > 0) {
                new SweetAlertDialog(this, SweetAlertDialog.BUTTON_CONFIRM).setConfirmButton("OK", new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        farmActivity();
                    }
                })
                        .setTitleText("Cadastro realizado com sucesso")
                        .show();

            }
            else{
                new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Algo deu errado")
                        .setContentText("Confira os dados preenchidos")
                        .show();
            }
        }
    }

    private void farmActivity(){
        startActivity(new Intent(this, FarmActivity.class));
        finish();
    }




}
