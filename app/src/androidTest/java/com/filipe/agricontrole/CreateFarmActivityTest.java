package com.filipe.agricontrole;

import android.support.test.annotation.UiThreadTest;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.EditText;

public class CreateFarmActivityTest extends ActivityInstrumentationTestCase2 {

    private CreateFarmActivity createFarmActivity;
    private Button btnSave;
    private EditText edtName, edtOwner, edtAddress;

    public CreateFarmActivityTest(){
        super(CreateFarmActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        createFarmActivity = (CreateFarmActivity) getActivity();
        btnSave = (Button) createFarmActivity.findViewById(R.id.farm_btnSave);
    }
    // Boa prática, verifica se os componentes foram incializados corretamente antes de continuar
    public void testPreconditions() {
        assertNotNull("mainActivity is null", createFarmActivity);
        assertNotNull("button is null",btnSave);
    }
    // Testa o clique no botão, essa anotação é necessária porque o componente foi criado pela UIThread
    @UiThreadTest
    public void testButtonClick() {
        btnSave.performClick();
        btnSave.performClick();
        assertEquals("Clicado 2 vezes!",btnSave.getText());
    }
}
