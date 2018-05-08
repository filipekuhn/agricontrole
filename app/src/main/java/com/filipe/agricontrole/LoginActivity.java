package com.filipe.agricontrole;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.filipe.agricontrole.data.repo.AgronomistRepo;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class LoginActivity extends AppCompatActivity {

    EditText edtEmail, edtPassword;
    AgronomistRepo helper;
    CheckBox ckbConected;

    private static final String PREFERENCE_NAME  = "LoginActivityPreferences";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtEmail   = (EditText) findViewById(R.id.login_edtEmail);
        edtPassword     = (EditText) findViewById(R.id.login_edtPassword);
        ckbConected = (CheckBox) findViewById(R.id.login_ckbConected);

        helper = new AgronomistRepo();

        if(isLogged()){
            startActivity(new Intent(this, FarmActivity.class));
            finish();
        }

    }

    public void login(View view){
        String email = edtEmail.getText().toString();
        String password   = edtPassword.getText().toString();

        boolean validation = true;

        if(email == null || email.equals("")){
            validation = false;
            //edtEmail.setError("È necessário informar o email");
            //Messages.Message(this, "O login deve ser informado");
            new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("O login deve ser informado")
                    .show();
        }

        if(password == null || password.equals("")){
            validation = false;
            //edtPassword.setError(getString(R.string.login_valPassword));
            //Messages.Message(this, "A senha deve ser informada");
            new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("A senha deve ser informada")
                    .show();
        }

        if(validation)
        {
            //logar
            if(helper.login(email, password))
            {
                if(ckbConected.isChecked())
                {
                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
                    SharedPreferences.Editor editor     = sharedPreferences.edit();

                    editor.putBoolean("remember", true);
                    editor.putString("email", email);
                    editor.putString("password", password);
                    editor.commit();
                }
                else{
                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
                    SharedPreferences.Editor editor     = sharedPreferences.edit();

                    editor.putString("email", email);
                    editor.commit();
                }
                startActivity(new Intent(this, FarmActivity.class));
                finish();
            }
            else {
              //  Messages.Message(this, getString(R.string.msg_login_incorreto));

                new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Algo deu errado")
                        .setContentText("Usuário ou senha inválido")
                        .show();
            }
        }
    }

    private void ChamarMainAcitivity(){
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    public void signUp(View view){
        startActivity(new Intent(this, SignUpActivity.class));
    }

    private boolean isLogged(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String email = preferences.getString("email", null);
        String password = preferences.getString("password", null);
        System.out.println(email + " " + password );
        if(email != null && password != null){
            if(helper.login(email, password))
                return true;
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.login_help) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
