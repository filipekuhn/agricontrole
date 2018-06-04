package com.filipe.agricontrole;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.filipe.agricontrole.adapter.FarmAdapter;
import com.filipe.agricontrole.data.model.Farm;
import com.filipe.agricontrole.data.repo.AgronomistRepo;
import com.filipe.agricontrole.data.repo.FarmRepo;
import com.filipe.agricontrole.holder.FarmHolder;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class FarmActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Farm farm;
    AgronomistRepo agronomistHelper;
    FarmRepo farmHelper;
    RecyclerView recyclerView;
    FarmAdapter adapter;
    FarmHolder holder;


    ImageButton btnView, btnDelete, btnEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farm);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setLogo(R.drawable.ic_tractor);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createFarmActivity();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        agronomistHelper = new AgronomistRepo();
        farmHelper = new FarmRepo();
        configureRecycler();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.farm, menu);

        //Get Shared preferences to list name, surename and email of the user.
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String email = preferences.getString("email", null);
        String name = agronomistHelper.getCurrentUserName(email);

        TextView current_user_name = (TextView) findViewById(R.id.current_user_name);
        current_user_name.setText("Bem Vindo, " + name);

        TextView current_user_email = (TextView) findViewById(R.id.current_user_email);
        current_user_email.setText(email);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.app_logout) {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor editor     = sharedPreferences.edit();
            editor.clear();
            editor.commit();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_create_farm) {
            createFarmActivity();
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_edit) {
            startActivity(new Intent(this, AgronomistActivity.class));
        } else if (id == R.id.nav_delete) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void configureRecycler() {
        // Configure the layout manager to be a list
        recyclerView = (RecyclerView)findViewById(R.id.farmrecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // Add the adapter that include the objects to the list.
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String email = preferences.getString("email", null);

        farmHelper = new FarmRepo();
        adapter = new FarmAdapter(farmHelper.findAllByEmail(email), FarmActivity.this);

        recyclerView.setAdapter(adapter);

        adapter.notifyDataSetChanged();
    }

    public void deleteFarm(int position, int index){
        farmHelper = new FarmRepo();

        new SweetAlertDialog(this, SweetAlertDialog.BUTTON_CONFIRM).setConfirmButton("OK", new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                if (farmHelper.delete(index)) {
                    adapter.farmList.remove(position);
                    adapter.notifyItemRemoved(position);
                    sweetAlertDialog.dismissWithAnimation();
                }
            }
        }).setTitleText("Deseja Excluir?").setCancelButton("Cancelar", new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                sweetAlertDialog.cancel();
            }
        }).show();


    }

   public void farmManagement(int index, String name){
        Intent intent = new Intent(this, FarmManagementActivity.class);
        intent.putExtra("farmId", index);
        intent.putExtra("farmName", name);
        startActivity(intent);
    }

    public void createFarmActivity(){
        startActivity(new Intent(this, CreateFarmActivity.class));
    }
}
