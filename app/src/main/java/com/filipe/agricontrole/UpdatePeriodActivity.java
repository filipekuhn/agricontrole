package com.filipe.agricontrole;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.filipe.agricontrole.data.model.Farm;
import com.filipe.agricontrole.data.model.Period;
import com.filipe.agricontrole.data.repo.PeriodRepo;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class UpdatePeriodActivity extends Activity {

    private Period period;
    private Farm farm;
    private PeriodRepo periodHelper;
    private EditText edtName, edtFarm;
    private int farmId, periodId;
    private String farmName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_period);

        edtName = (EditText) findViewById(R.id.period_edtName);
        edtFarm = (EditText) findViewById(R.id.period_edtFarm);

        periodId = getIntent().getExtras().getInt("periodId");
        farmId = getIntent().getExtras().getInt("farmId");
        farmName = getIntent().getExtras().getString("farmName");

        insertText();
    }

    private void insertText(){
        periodHelper = new PeriodRepo();
        period = new Period();

        period = periodHelper.findById(periodId);

        edtName.setText(period.getName());
        edtFarm.setText(farmName);
    }

    public void updatePeriod(View view){
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

            period.setId(periodId);
            period.setName(name);
            farm.setId(farmId);
            period.setFarm(farm);

            int periodId = periodHelper.update(period);

            if(periodId > 0) {
                new SweetAlertDialog(this, SweetAlertDialog.BUTTON_CONFIRM).setConfirmButton("OK", new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        finish();
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
