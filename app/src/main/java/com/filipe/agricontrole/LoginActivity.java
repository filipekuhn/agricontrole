package com.filipe.agricontrole;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.filipe.agricontrole.data.repo.AgronomistRepo;

import util.Messages;

public class LoginActivity extends AppCompatActivity {

    private EditText edtEmail, edtPassword;
    private AgronomistRepo helper;
    private CheckBox ckbConected;

    private static final String STAY_CONNECTED = "manter_conectado";
    private static final String PREFERENCE_NAME  = "LoginActivityPreferences";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtEmail   = (EditText) findViewById(R.id.login_edtEmail);
        edtPassword     = (EditText) findViewById(R.id.login_edtPassword);
        ckbConected = (CheckBox) findViewById(R.id.login_ckbConected);

        helper = new AgronomistRepo();

        SharedPreferences preferences = getSharedPreferences(PREFERENCE_NAME, MODE_PRIVATE);
        boolean conected = preferences.getBoolean(STAY_CONNECTED, false);

        if(conected){
            ChamarMainAcitivity();
        }
    }

    public void login(View view){
        String email = edtEmail.getText().toString();
        String password   = edtPassword.getText().toString();

        boolean validation = true;

        if(email == null || email.equals("")){
            validation = false;
            //edtEmail.setError("È necessário informar o email");
            Messages.Message(this, "O login deve ser informado");
        }

        if(password == null || password.equals("")){
            validation = false;
            //edtPassword.setError(getString(R.string.login_valPassword));
            Messages.Message(this, "A senha deve ser informada");
        }

        if(validation)
        {
            //logar
            if(helper.login(email, password))
            {
                /*if(ckbConected.isChecked())
                {
                    SharedPreferences sharedPreferences = getSharedPreferences(PREFERENCE_NAME, MODE_PRIVATE);
                    SharedPreferences.Editor editor     = sharedPreferences.edit();

                    editor.putBoolean(STAY_CONNECTED, true);
                    editor.commit();
                }*/

                ChamarMainAcitivity();
            }
            else {
                Messages.Message(this, getString(R.string.msg_login_incorreto));

                /*new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Algo deu errado?")
                        .setContentText("Usuário ou senha inválido!")
                        .show();*/
            }
        }
    }

    private void ChamarMainAcitivity(){
        startActivity(new Intent(this, MainActivity.class));
        finish();
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
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
