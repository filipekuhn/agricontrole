package com.filipe.agricontrole;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.filipe.agricontrole.data.model.Agronomist;
import com.filipe.agricontrole.data.repo.AgronomistRepo;

import java.text.SimpleDateFormat;
import java.util.Date;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class SignUpActivity extends AppCompatActivity {

    private EditText edtName, edtSurename, edtCellphone, edtEmail, edtPassword;
    private AgronomistRepo helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        edtName = (EditText) findViewById(R.id.signUp_edtName);
        edtSurename = (EditText) findViewById(R.id.signUp_edtSurename);
        edtCellphone = (EditText) findViewById(R.id.signUp_edtCellphone1);
        edtEmail = (EditText) findViewById(R.id.signUp_edtEmail);
        edtPassword = (EditText) findViewById(R.id.signUp_edtPassword);

        helper = new AgronomistRepo();
    }

    public void save(View view){
        String name = edtName.getText().toString();
        String surename = edtSurename.getText().toString();
        String cellphone = edtCellphone.getText().toString();
        String email = edtEmail.getText().toString();
        String password   = edtPassword.getText().toString();

        Agronomist agronomist = new Agronomist();

        agronomist.setName(name);
        agronomist.setSurename(surename);
        agronomist.setCellphone(cellphone);
        agronomist.setEmail(email);
        agronomist.setPassword(password);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();

        agronomist.setCreated_at(dateFormat.format(date).toString());


        if(helper.insert(agronomist) > 0){
            new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                    .setTitleText("Cadastro realizado com sucesso")
                    .show();
            callMainAcitivity();
        }
        else{
            new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("Algo deu errado!")
                    .setContentText("Confira os dados preenchidos!")
                    .show();
        }
    }

    private void callMainAcitivity(){
        startActivity(new Intent(this, MainActivity.class));
    }
}
