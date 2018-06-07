package com.filipe.agricontrole;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.filipe.agricontrole.data.model.Planting;
import com.filipe.agricontrole.data.model.Plot;
import com.filipe.agricontrole.data.repo.PlantingRepo;
import com.filipe.agricontrole.regex.DateValidator;

import br.com.sapereaude.maskedEditText.MaskedEditText;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class UpdatePlantingActivity extends Activity {

    private EditText edtType, edtDate, edtPopulation, edtEmergencyDate, edtHarvestDate;
    private Button btnSave;
    private PlantingRepo plantingHelper;
    private Planting planting;
    private PlantingActivity plantingActivity;
    private Plot plot;
    DateValidator dateValidator;

    private int plotId, periodId, farmId, plantingId;
    private String plotName, periodName, farmName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_planting);

        plantingId = getIntent().getExtras().getInt("plantingId");
        plotId = getIntent().getExtras().getInt("plotId");
        plotName = getIntent().getExtras().getString("plotName");
        periodId = getIntent().getExtras().getInt("periodId");
        periodName = getIntent().getExtras().getString("periodName");
        farmId = getIntent().getExtras().getInt("farmId");
        farmName = getIntent().getExtras().getString("farmName");

        edtType = (EditText) findViewById(R.id.planting_edtType);
        edtDate = (MaskedEditText) findViewById(R.id.planting_edtDate);
        edtPopulation = (EditText) findViewById(R.id.planting_edtPopulation);
        edtEmergencyDate = (MaskedEditText) findViewById(R.id.planting_edtEmergencyDate);
        edtHarvestDate = (MaskedEditText) findViewById(R.id.planting_edtHarvestDate);

        insertText();
    }

    private void insertText(){
        plantingHelper = new PlantingRepo();
        planting = new Planting();
        String emergencyDate, harvestDate;
        planting = plantingHelper.findById(plantingId);

        String date = planting.getPlantingDate();

        date.replace("/", ""); //Replace for null because the date mask....
        Double popAux = planting.getPopulation();
        String population = Double.toString(popAux);

        edtType.setText(planting.getType());
        edtDate.setText(date);
        edtPopulation.setText(population);

        if(planting.getEmergencyDate() != null){
            emergencyDate = planting.getEmergencyDate();
            emergencyDate.replace("/", "");
            edtEmergencyDate.setText(emergencyDate);
        }

        if(planting.getHarvestDate() != null){
            harvestDate = planting.getHarvestDate();
            harvestDate.replace("/", "");
            edtHarvestDate.setText(harvestDate);
        }
    }

    public void updatePlanting(View view){
        plantingHelper = new PlantingRepo();
        dateValidator = new DateValidator();

        String type = edtType.getText().toString().trim();
        String date = edtDate.getText().toString().trim();
        String population = edtPopulation.getText().toString().trim();
        population.replace(",", ".");
        String emergencyDate = edtEmergencyDate.getText().toString().trim();
        String harvestDate = edtHarvestDate.getText().toString().trim();
        Double populationAux = null;

        if(!population.equals("")) //Verify if exist some string to convert to Double.
            populationAux = Double.parseDouble(population);

        if((type== null || type.equals("")) ||  date.equals("01/01/2000")){

            new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Não foi possível concluir o cadastro")
                    .setContentText("É necessário preencher todos os campos")
                    .show();
        }else if(!dateValidator.validate(date)){
            new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Não foi possível concluir o cadastro")
                    .setContentText("A data inserida é inválida!")
                    .show();
        }else if(!emergencyDate.equals("") && !dateValidator.validate(emergencyDate)){
            new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Não foi possível concluir o cadastro")
                    .setContentText("A data de emergência inserida é inválida!")
                    .show();
        }else if(!harvestDate.equals("") && !dateValidator.validate(harvestDate)){
            new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Não foi possível concluir o cadastro")
                    .setContentText("A data de colheita inserida é inválida!")
                    .show();
        }else{
            planting = new Planting();
            plot = new Plot();

            planting.setId(plantingId);
            plot.setId(plotId);
            planting.setPlot(plot);
            planting.setType(type);
            planting.setPlantingDate(date);

            if(populationAux != null)//If populationAux is null, don't set the attribute
                planting.setPopulation(populationAux);

            if(!emergencyDate.equals(""))//If emergencyDate is not a empty string set the value
                planting.setEmergencyDate(emergencyDate);

            if(!harvestDate.equals(""))//If harvestDate is not a empty string set the value
                planting.setHarvestDate(harvestDate);

            int plantingId = plantingHelper.update(planting);

            if(plantingId > 0) {
                new SweetAlertDialog(this, SweetAlertDialog.BUTTON_CONFIRM).setConfirmButton("OK", new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        plantingActivity();
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

    public void plantingActivity(){
        Intent intent = new Intent(this, PlantingActivity.class);
        intent.putExtra("plotId", plotId);
        intent.putExtra("plotName", plotName);
        intent.putExtra("periodId", periodId);
        intent.putExtra("periodName", periodName);
        intent.putExtra("farmId", farmId);
        intent.putExtra("farmName", farmName);
        startActivity(intent);
        finish();
    }
}
