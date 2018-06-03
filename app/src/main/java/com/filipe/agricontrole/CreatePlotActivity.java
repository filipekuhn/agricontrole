package com.filipe.agricontrole;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.filipe.agricontrole.data.model.Period;
import com.filipe.agricontrole.data.model.Plot;
import com.filipe.agricontrole.data.repo.PlotRepo;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class CreatePlotActivity extends AppCompatActivity {

    private EditText edtName, edtArea, edtPeriod;
    private Button btnSave;
    private PlotRepo plotHelper;
    private Plot plot;
    private Period period;

    private int periodId, farmId;
    private String periodName, farmName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_plot);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        edtName = (EditText) findViewById(R.id.plot_edtName);
        edtArea = (EditText) findViewById(R.id.plot_edtArea);
        edtPeriod = (EditText) findViewById(R.id.plot_edtPeriod);
        btnSave = (Button) findViewById(R.id.plot_btnSave);

        periodId = getIntent().getExtras().getInt("periodId");
        periodName = getIntent().getExtras().getString("periodName");
        farmId = getIntent().getExtras().getInt("farmId");
        farmName = getIntent().getExtras().getString("farnName");
        edtPeriod.setText(periodName);
    }

    public void createPlot(View view){
        plotHelper = new PlotRepo();

        String name = edtName.getText().toString().trim();
        String area = edtArea.getText().toString().trim();
        area.replace(",", ".");

        Double areaAux = Double.parseDouble(area.toString());

        if((name == null || name.equals("")) || (area == null) || area.equals("")){

            new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Não foi possível concluir o cadastro")
                    .setContentText("É necessário preencher todos os campos")
                    .show();
        }else{
            plot = new Plot();
            period = new Period();



            plot.setName(name);
            plot.setArea(areaAux);
            period.setId(periodId);
            plot.setPeriodId(period);

            int plotId = plotHelper.insert(plot);

            if(plotId > 0) {
                new SweetAlertDialog(this, SweetAlertDialog.BUTTON_CONFIRM).setConfirmButton("OK", new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        plotActivity();
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

    public void plotActivity(){
        Intent intent = new Intent(this, PlotActivity.class);
        intent.putExtra("periodId", periodId);
        intent.putExtra("periodName", periodName);
        intent.putExtra("farmId", farmId);
        intent.putExtra("farmName", farmName);
        startActivity(intent);
        finish();
    }

}
