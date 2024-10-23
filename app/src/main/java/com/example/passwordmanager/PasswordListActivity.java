package com.example.passwordmanager;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.passwordmanager.models.database.DatabaseHelper;
import com.example.passwordmanager.models.Password;
import com.example.passwordmanager.models.generators.InsecurePasswordGenerator;
import com.example.passwordmanager.models.generators.PasswordGenerator;
import com.example.passwordmanager.models.generators.SecurePasswordGenerator;

import java.util.List;

public class PasswordListActivity extends AppCompatActivity {

    private RecyclerView passwordRecyclerView;
    private PasswordAdapter passwordAdapter;
    private DatabaseHelper databaseHelper;
    private List<Password> passwordList;
    private Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_list);

        passwordRecyclerView = findViewById(R.id.passwordRecyclerView);
        passwordRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        databaseHelper = new DatabaseHelper(this);

        btnBack = findViewById(R.id.button_back);

        loadPasswords();

        clickListeners();
    }

    private void loadPasswords() {
        try {
            passwordList = databaseHelper.getPasswordList();
            passwordAdapter = new PasswordAdapter(passwordList, this);
            passwordRecyclerView.setAdapter(passwordAdapter);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error loading passwords", Toast.LENGTH_SHORT).show();
        }
    }

    private void clickListeners() {
        btnBack.setOnClickListener(view -> {
            Intent intent = new Intent(PasswordListActivity.this, MainActivity.class);
            startActivity(intent);
        });

    }
}
