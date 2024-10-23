package com.example.passwordmanager.models;

import android.content.ContentValues;
import android.database.Cursor;

import java.io.Serializable;
import java.util.Date;

public class Password implements Serializable {

    private int id;
    private String name, login, password;
    private Date updateDate;
    private static final long serialVersionUID = 1L;


    public Password(String name, String login, String password) {
        this.name = name;
        this.login = login;
        this.password = password;
        this.updateDate = new Date();
    }

    public Password() {}

    public String toString() {
        return "Password{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", updateDate=" + updateDate +
                '}';
    }

    public ContentValues getContentValues() {
        ContentValues values = new ContentValues();

        values.put("name",name);
        values.put("password",password);
        values.put("login",login);
        values.put("update_date",updateDate.getTime());

        return values;
    }

    public static Password fromCursor(Cursor cursor) {
        Password password = new Password();

        password.id = cursor.getInt(cursor.getColumnIndex("id"));
        password.name = cursor.getString(cursor.getColumnIndex("name"));
        password.password = cursor.getString(cursor.getColumnIndex("password"));
        password.login = cursor.getString(cursor.getColumnIndex("login"));
        password.updateDate = new Date(Long.parseLong(cursor.getString(cursor.getColumnIndex("update_date"))));

        return password;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() { return this.name; }

    public void setName(String name) { this.name = name; }

    public String getLogin() { return this.login; }
}
