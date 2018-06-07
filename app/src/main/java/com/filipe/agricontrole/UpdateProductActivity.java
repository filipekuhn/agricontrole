package com.filipe.agricontrole;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.filipe.agricontrole.data.model.Category;
import com.filipe.agricontrole.data.model.Product;
import com.filipe.agricontrole.data.model.Stock;
import com.filipe.agricontrole.data.repo.CategoryRepo;
import com.filipe.agricontrole.data.repo.ProductRepo;
import com.filipe.agricontrole.regex.DateValidator;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class UpdateProductActivity extends Activity {

    private CategoryRepo categoryHelper;
    private Spinner categorySpinner;
    private ProductRepo productHelper;
    private Product product;
    private Stock stock;
    private DateValidator dateValidator;
    private EditText edtName, edtQuantity, edtExpirationDate;

    int farmId, stockId, productId;
    String farmName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_product);



        productId = getIntent().getExtras().getInt("productId");
        stockId = getIntent().getExtras().getInt("stockId");
        farmId = getIntent().getExtras().getInt("farmId");
        farmName = getIntent().getExtras().getString("farmName");

        edtName = (EditText) findViewById(R.id.product_edtName);
        edtQuantity = (EditText) findViewById(R.id.product_edtQuantity);
        edtExpirationDate = (EditText) findViewById(R.id.product_edtExpirationDate);
        categorySpinner = (Spinner) findViewById(R.id.product_spinnerCategory);

        fillCategorySpinner();
        insertText();
    }

    private void insertText(){
        productHelper = new ProductRepo();
        product = new Product();

        product = productHelper.findById(productId);

        edtName.setText(product.getName());

        String sQuantity = Double.toString(product.getQuantity());
        sQuantity.replace(".", ",");
        String date = product.getExpiration_date();
        date.replace("/", "");

        edtQuantity.setText(sQuantity);
        edtExpirationDate.setText(date);
    }

    private void fillCategorySpinner(){
        categoryHelper = new CategoryRepo();
        categorySpinner = (Spinner) findViewById(R.id.product_spinnerCategory);

        List<Category> categoryList = categoryHelper.findAll();

        ArrayAdapter<Category> adapter = new ArrayAdapter<Category>(this, android.R.layout.simple_spinner_item, categoryList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(adapter);
    }

    public void updateProduct(View view){
        productHelper = new ProductRepo();
        dateValidator = new DateValidator();

        String name = edtName.getText().toString().trim();
        String quantity = edtQuantity.getText().toString().trim();
        String expirationDate = edtExpirationDate.getText().toString().trim();
        quantity.replace(",", ".");

        Category category = (Category) (categorySpinner).getSelectedItem();
        Double quantityAux = null;

        if(!quantity.equals("")) //Verify if exist some string to convert to Double.
            quantityAux = Double.parseDouble(quantity);

        if(name.equals("") || quantity.equals("") || expirationDate.equals("")){

            new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Não foi possível concluir o cadastro")
                    .setContentText("É necessário preencher todos os campos")
                    .show();
        }else if(!dateValidator.validate(expirationDate)) {
            new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Não foi possível concluir o cadastro")
                    .setContentText("A data de vencimento inserida é inválida!")
                    .show();
        }else{
            product = new Product();
            stock = new Stock();

            stock.setId(stockId);
            stock.setFarmId(farmId);

            product.setId(productId);
            product.setStock(stock);
            product.setCategory(category);
            product.setName(name);
            product.setQuantity(quantityAux);
            product.setExpiration_date(expirationDate);

            int productId = productHelper.update(product);

            if(productId > 0) {
                new SweetAlertDialog(this, SweetAlertDialog.BUTTON_CONFIRM).setConfirmButton("OK", new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        productActivity();
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

    private void productActivity(){
        Intent intent = new Intent(this, ProductActivity.class);
        intent.putExtra("stockId", stockId);
        intent.putExtra("farmId", farmId);
        intent.putExtra("farmName", farmName);
        startActivity(intent);
        finish();
    }
}
