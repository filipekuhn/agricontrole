package com.filipe.agricontrole;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.filipe.agricontrole.data.model.Planting;
import com.filipe.agricontrole.data.model.Product;
import com.filipe.agricontrole.data.model.ProductApplication;
import com.filipe.agricontrole.data.model.Stock;
import com.filipe.agricontrole.data.repo.ProductApplicationRepo;
import com.filipe.agricontrole.data.repo.ProductRepo;
import com.filipe.agricontrole.data.repo.StockRepo;
import com.filipe.agricontrole.regex.DateValidator;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class UpdateProductApplicationActivity extends AppCompatActivity {

    private EditText edtQuantity, edtDate, edtProduct;
    private ProductApplicationRepo helper;
    private ProductApplication productApplication;
    private ProductRepo productHelper;
    private StockRepo stockHelper;
    private Stock stock;
    private Planting planting;
    private Product product;
    private DateValidator dateValidator;
    private int id, plantingId, plotId, farmId, periodId;
    private String plotName, farmName, periodName;
    private Double quantityHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_product_application);

        id = getIntent().getExtras().getInt("applicationId");
        plantingId = getIntent().getExtras().getInt("plantingId");
        plotId = getIntent().getExtras().getInt("plotId");
        plotName = getIntent().getExtras().getString("plotName");
        farmId = getIntent().getExtras().getInt("farmId");
        farmName = getIntent().getExtras().getString("farmName");
        periodId = getIntent().getExtras().getInt("periodId");
        periodName = getIntent().getExtras().getString("periodName");

        edtProduct = (EditText) findViewById(R.id.application_edtProduct);
        edtQuantity = (EditText) findViewById(R.id.application_edtQuantity);
        edtDate = (EditText) findViewById(R.id.application_edtDate);

        dateValidator = new DateValidator();

        insertEditText();
    }

    private void insertEditText(){
        helper = new ProductApplicationRepo();
        productHelper = new ProductRepo();

        productApplication = helper.findById(id);
        product = productHelper.findById(productApplication.getProduct().getId());

        quantityHelper = productApplication.getQuantity(); //Save the quantity applied before update

        String quantity = Double.toString(productApplication.getQuantity());
        String date = productApplication.getDate();
        date.replace("/", "");

        edtProduct.setText(product.getName());
        edtQuantity.setText(quantity);
        edtDate.setText(date);
    }

    public void updateProductApplication(View view){
;
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
            availableQuantity = product.getQuantity() + quantityHelper;

            if(quantityAux > availableQuantity){
                new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Não foi possível concluir o cadastro")
                        .setContentText("A quantidade informada na aplicação é maior que a quantidade disponível do produto")
                        .show();
            }else{
                helper = new ProductApplicationRepo();
                productApplication = new ProductApplication();
                planting = new Planting();

                productApplication.setId(id);
                planting.setId(plantingId);
                productApplication.setProduct(product);
                productApplication.setPlanting(planting);
                productApplication.setQuantity(quantityAux);
                productApplication.setDate(date);

                finalQuantity = availableQuantity - quantityAux;

                if(helper.update(productApplication, finalQuantity) > 0){
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
