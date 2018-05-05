package com.filipe.agricontrole;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.filipe.agricontrole.data.model.Agronomist;
import com.filipe.agricontrole.data.repo.AgronomistRepo;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class AgronomistActivity extends AppCompatActivity {

    private Agronomist agronomist;
    private EditText edtName, edtSurename, edtCellphone, edtEmail, edtPassword;
    private AgronomistRepo helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agronomist);

        edtName = (EditText) findViewById(R.id.agronomist_edtName);
        edtSurename = (EditText) findViewById(R.id.agronomist_edtSurename);
        edtCellphone = (EditText) findViewById(R.id.agronomist_edtCellphone1);
        edtEmail = (EditText) findViewById(R.id.agronomist_edtEmail);
        edtPassword = (EditText) findViewById(R.id.agronomist_edtPassword);

        helper = new AgronomistRepo();

        insertEditText();
    }

    private void insertEditText(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String email = preferences.getString("email", null);
        agronomist = helper.findByEmail(email);

        edtName.setText(agronomist.getName());
        edtSurename.setText(agronomist.getSurename());
        edtCellphone.setText(agronomist.getCellphone());
        edtEmail.setText(agronomist.getEmail());
        edtPassword.setText(agronomist.getPassword());
    }

    public void save(View view){
        String name = edtName.getText().toString();
        String surename = edtSurename.getText().toString();
        String cellphone = edtCellphone.getText().toString();
        String email = edtEmail.getText().toString();
        String password   = edtPassword.getText().toString();

        agronomist.setName(name);
        agronomist.setSurename(surename);
        agronomist.setCellphone(cellphone);
        agronomist.setEmail(email);
        agronomist.setPassword(password);

        if(helper.update(agronomist) > 0){
            new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                    .setTitleText("Alteração realizada com sucesso")
                    .show();
            startActivity(new Intent(this, FarmActivity.class));
        }
        else{
            new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("Algo deu errado!")
                    .setContentText("Confira os dados preenchidos!")
                    .show();
        }
    }

}
