package com.filipe.agricontrole;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import com.filipe.agricontrole.data.model.Agronomist;
import com.filipe.agricontrole.data.repo.AgronomistRepo;

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

}
