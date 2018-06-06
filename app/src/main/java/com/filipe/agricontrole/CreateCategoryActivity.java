package com.filipe.agricontrole;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.filipe.agricontrole.data.model.Category;
import com.filipe.agricontrole.data.model.UnitType;
import com.filipe.agricontrole.data.repo.CategoryRepo;
import com.filipe.agricontrole.data.repo.UnitTypeRepo;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class CreateCategoryActivity extends AppCompatActivity {
    private UnitType unitType;
    private UnitTypeRepo unitTypeHelper;
    private Category category;
    private CategoryRepo categoryHelper;

    private Spinner spinner;
    private EditText edtName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_category);

        spinner = (Spinner) findViewById(R.id.category_spinnerUnitType);
        edtName = (EditText) findViewById(R.id.category_edtName);

        fillUnitTypeSpinner();
    }

    private void fillUnitTypeSpinner(){
        unitTypeHelper = new UnitTypeRepo();
        spinner = (Spinner) findViewById(R.id.category_spinnerUnitType);

        unitType = new UnitType();
        List<UnitType> unitTypeList = unitTypeHelper.findAll();

        ArrayAdapter<UnitType> adapter = new ArrayAdapter<UnitType>(this, android.R.layout.simple_spinner_item, unitTypeList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    public void createCategory(View view){
        categoryHelper = new CategoryRepo();

        String name = edtName.getText().toString().trim();
        UnitType unitType = (UnitType) (spinner).getSelectedItem();


        if(name.equals("")){

            new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Não foi possível concluir o cadastro")
                    .setContentText("É necessário preencher o nome da categoria")
                    .show();
        }else{
            category = new Category();

            category.setName(name);
            category.setUnitType(unitType);

            int categoryId = categoryHelper.insert(category);

            if(categoryId > 0) {
                new SweetAlertDialog(this, SweetAlertDialog.BUTTON_CONFIRM).setConfirmButton("OK", new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        farmActivity();
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

    private void farmActivity(){
        Intent intent = new Intent(this, FarmActivity.class);
        startActivity(intent);
        finish();
    }

}

