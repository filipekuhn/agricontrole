package com.filipe.agricontrole;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.filipe.agricontrole.data.model.Farm;
import com.filipe.agricontrole.data.model.Period;
import com.filipe.agricontrole.data.repo.PeriodRepo;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class CreatePeriodActivity extends AppCompatActivity {

    private Period period;
    private Farm farm;
    private PeriodRepo periodHelper;
    private EditText edtName, edtFarm;
    private int farmId;
    private String farmName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_period);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);


        edtName = (EditText) findViewById(R.id.period_edtName);
        edtFarm = (EditText) findViewById(R.id.period_edtFarm);

        farmId = getIntent().getExtras().getInt("farmId");
        farmName = getIntent().getExtras().getString("farmName");
        edtFarm.setText(farmName);
    }

    public void createPeriod(View view){
        periodHelper = new PeriodRepo();
        edtName = (EditText) findViewById(R.id.period_edtName);
        String name = edtName.getText().toString().trim();



        if(name == null || name.equals("")){

            new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Não foi possível concluir o cadastro")
                    .setContentText("É necessário preencher todos os campos")
                    .show();
        }else{
            period = new Period();
            farm = new Farm();

            period.setName(name);
            farm.setId(farmId);
            period.setFarm(farm);

            int periodId = periodHelper.insert(period);

            if(periodId > 0) {
                new SweetAlertDialog(this, SweetAlertDialog.BUTTON_CONFIRM).setConfirmButton("OK", new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
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
}
