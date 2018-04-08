package com.filipe.agricontrole.data.model;

/**
 * Created by filipe on 22/03/17.
 */

public class Agronomist {

    public static final String TAG = Agronomist.class.getSimpleName();
    public static final String TABLE = "Agronomist";
    // Labels Table Columns names
    public static final String KEY_AgronomistId = "Id";
    public static final String KEY_Name = "Name";
    public static final String KEY_SureName = "SureName";
    public static final String KEY_CellPhone = "CellPhone";
    public static final String KEY_Email = "Email";
    public static final String KEY_Password = "Password";
    public static final String KEY_CreatedAt = "CreatedAt";

    private Integer id;
    private String name;
    private String surename;
    private String cellphone;
    private String email;
    private String password;
    private String created_at;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurename() {
        return surename;
    }

    public void setSurename(String surename) {
        this.surename = surename;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}
