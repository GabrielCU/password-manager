package com.example.passwordmanager;

import android.content.Intent;
import android.os.Bundle;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.passwordmanager.models.Password;
import com.example.passwordmanager.models.database.DatabaseHelper;

public class SavePasswordActivity extends AppCompatActivity {

    private EditText editPasswordName, editPasswordValue, editPasswordLogin;
    private Button btnSave, btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_password);

        initViews();
    }

    private void initViews() {
        editPasswordLogin = findViewById(R.id.edit_password_login);
        editPasswordValue = findViewById(R.id.edit_password_value);
        editPasswordName = findViewById(R.id.edit_password_name);
        btnSave = findViewById(R.id.button_save);
        btnBack = findViewById(R.id.button_back);

        String generatedPassword = getIntent().getStringExtra("password");
        editPasswordValue.setText(generatedPassword);

        btnSave.setOnClickListener(view -> {
            DatabaseHelper databaseHelper = new DatabaseHelper(SavePasswordActivity.this);
            Password password = new Password(
                    editPasswordName.getText().toString(),
                    editPasswordLogin.getText().toString(),
                    editPasswordValue.getText().toString());

            // insecure save
            // boolean saved = databaseHelper.insert(password);
            boolean saved = databaseHelper.secureInsert(password, SavePasswordActivity.this);
            if (saved) {
                Toast.makeText(this, "Password saved", Toast.LENGTH_SHORT).show();
                btnSave.setEnabled(false);
            }
        });

        btnBack.setOnClickListener(view -> {
            Intent intent = new Intent(SavePasswordActivity.this, MainActivity.class);
            startActivity(intent);
        });
    }
}
