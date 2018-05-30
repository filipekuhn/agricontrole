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

        String name = edtName.getText().toString().trim();
        String surename = edtSurename.getText().toString().trim();
        String cellphone = edtCellphone.getText().toString().trim();
        String email = edtEmail.getText().toString().trim();
        String password   = edtPassword.getText().toString().trim();

        if((name == null || name.equals("")) || (surename == null || surename.equals(""))
                || (cellphone == null || cellphone.equals("")) || (email == null || email.equals(""))
                || (password == null || password.equals(""))){

            new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Não foi possível concluir o cadastro!")
                    .setContentText("É necessário preencher todos os campos!")
                    .show();
        }else if(!isValidEmail(email)){
            new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("O campo de email está incorreto")
                    .setContentText("É necessário incluir um e-mail válido")
                    .show();
        }
        else{
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
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
                SharedPreferences.Editor editor     = sharedPreferences.edit();

                editor.putBoolean("remember", true);
                editor.putString("email", email);
                editor.putString("password", password);
                editor.commit();

                new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText("Cadastro realizado com sucesso")
                        .show();
                loginAcitivity();
            }
            else{
                new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Algo deu errado!")
                        .setContentText("Confira os dados preenchidos!")
                        .show();
            }
        }


    }

    private void loginAcitivity(){
        startActivity(new Intent(this, LoginActivity.class));
    }

    public final static boolean isValidEmail(CharSequence target) {
        if (target == null)
            return false;

        return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }
}
