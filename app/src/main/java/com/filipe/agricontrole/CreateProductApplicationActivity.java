package com.filipe.agricontrole;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.filipe.agricontrole.data.model.Planting;
import com.filipe.agricontrole.data.model.Product;
import com.filipe.agricontrole.data.model.ProductApplication;
import com.filipe.agricontrole.data.model.Stock;
import com.filipe.agricontrole.data.repo.ProductApplicationRepo;
import com.filipe.agricontrole.data.repo.ProductRepo;
import com.filipe.agricontrole.data.repo.StockRepo;
import com.filipe.agricontrole.regex.DateValidator;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class CreateProductApplicationActivity extends AppCompatActivity {

    private ProductRepo productHelper;
    private ProductApplication productApplication;
    private Planting planting;
    private Stock stock;
    private StockRepo stockHelper;
    private ProductApplicationRepo applicationHelper;
    private DateValidator dateValidator;
    private Spinner productSpinner;
    private EditText edtQuantity, edtDate;

    int plotId, farmId, periodId, plantingId;
    String plotName, farmName, periodName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_product_application);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        plantingId = getIntent().getExtras().getInt("plantingId");
        plotId = getIntent().getExtras().getInt("plotId");
        plotName = getIntent().getExtras().getString("plotName");
        farmId = getIntent().getExtras().getInt("farmId");
        farmName = getIntent().getExtras().getString("farmName");
        periodId = getIntent().getExtras().getInt("periodId");
        periodName = getIntent().getExtras().getString("periodName");

        productSpinner = (Spinner) findViewById(R.id.application_spinnerProduct);
        edtQuantity = (EditText) findViewById(R.id.application_edtQuantity);
        edtDate = (EditText) findViewById(R.id.application_edtDate);

        dateValidator = new DateValidator();

        fillProductSpinner();
    }

    private void fillProductSpinner(){
        productHelper = new ProductRepo();
        productSpinner = (Spinner) findViewById(R.id.application_spinnerProduct);

        stockHelper = new StockRepo();
        stock = new Stock();
        List<Stock> stockList = stockHelper.findAllByFarmId(farmId);

        int stockId = stockList.get(0).getId();
        List<Product> productList = productHelper.findAllByStockId(stockId);

        ArrayAdapter<Product> adapter = new ArrayAdapter<Product>(this, android.R.layout.simple_spinner_item, productList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        productSpinner.setAdapter(adapter);
    }

    public void createProductApplication(View view){

        //Convert product object from spinner to take her ID to set on product_application table
        Product product = (Product) (productSpinner).getSelectedItem();
        String quantity = edtQuantity.getText().toString().trim();
        String date = edtDate.getText().toString().trim();
        Double availableQuantity ,quantityAux, finalQuantity;

        if(quantity.equals("") || date.equals("")){
            new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Não foi possível concluir o cadastro")
                    .setContentText("É necessário preencher todos os campos")
                    .show();
        }else if(!dateValidator.validate(date)){
            new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Não foi possível concluir o cadastro")
                    .setContentText("A data de aplicação inserida é inválida!")
                    .show();
        }else{
            quantityAux = Double.parseDouble(quantity);
            availableQuantity = product.getQuantity();
            String unitType = product.getCategory().getUnitType().getName();

            if(quantityAux > availableQuantity){
                new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Não foi possível concluir o cadastro")
                        .setContentText("A quantidade " + quantity + " " + unitType +
                                " informada na aplicação é maior que a quantidade disponível do produto (" + availableQuantity +" "+ unitType +")")
                        .show();
            }else{
                applicationHelper = new ProductApplicationRepo();
                productApplication = new ProductApplication();
                planting = new Planting();

                planting.setId(plantingId);
                productApplication.setProduct(product);
                productApplication.setPlanting(planting);
                productApplication.setQuantity(quantityAux);
                productApplication.setDate(date);

                finalQuantity = availableQuantity - quantityAux;

                if(applicationHelper.insert(productApplication, finalQuantity) > 0){
                    new SweetAlertDialog(this, SweetAlertDialog.BUTTON_CONFIRM).setConfirmButton("OK", new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            productApplicationActivity();
                        }
                    })
                            .setTitleText("Cadastro realizado com sucesso")
                            .show();
                }else{
                    new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Algo deu errado")
                            .setContentText("Confira os dados preenchidos")
                            .show();
                }
            }

        }
    }

    private void productApplicationActivity(){
        Intent intent = new Intent(this, ProductApplicationActivity.class);
        intent.putExtra("plantingId", plantingId);
        intent.putExtra("plotId", plotId);
        intent.putExtra("plotName", plotName);
        intent.putExtra("farmId", farmId);
        intent.putExtra("farmName", farmName);
        intent.putExtra("periodId", periodId);
        intent.putExtra("periodName", periodName);
        startActivity(intent);
        finish();
    }
}
