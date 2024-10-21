package com.example.passwordmanager.models.database;

import android.os.Bundle;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.passwordmanager.R;
import com.example.passwordmanager.models.Password;

public class SavePassword extends AppCompatActivity {

    private EditText editPasswordName, editPasswordValue, editPasswordLogin;
    private Button buttonSave;

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
        buttonSave = findViewById(R.id.button_save);

        String generatedPassword = getIntent().getStringExtra("password");
        editPasswordValue.setText(generatedPassword);

        buttonSave.setOnClickListener(view -> {
            DatabaseHelper databaseHelper = new DatabaseHelper(SavePassword.this);
            Password password = new Password(
                    editPasswordName.getText().toString(),
                    editPasswordLogin.getText().toString(),
                    editPasswordValue.getText().toString());

            // insecure save
            // boolean saved = databaseHelper.insert(password);
            boolean saved = databaseHelper.secureInsert(password, SavePassword.this);
            if (saved) {
                Toast.makeText(this, "Password saved", Toast.LENGTH_SHORT).show();
                buttonSave.setEnabled(false);
            }
        });
    }
}