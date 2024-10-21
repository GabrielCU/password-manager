package com.example.passwordmanager;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.example.passwordmanager.models.*;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView textGeneratedPassword;
    private Button btnGeneratePassword, btnCopyPassword, btnSavePassword;

    private final int passwordLength = 12;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            KeyStoreManager.generateKeyPair(MainActivity.this);
        } catch (Exception e) {
            e.printStackTrace();
        }

        initViews();

        clickListeners();

        showSavedPasswords();
    }

    private void showSavedPasswords() {
        DatabaseHelper databaseHelper = new DatabaseHelper(MainActivity.this);

        // Insecure method
        //List<Password> passwordList = databaseHelper.getPasswordList();
        List<Password> passwordList = databaseHelper.secureGetPasswordList(MainActivity.this);

        Log.e("PASSWORD_LIST", passwordList.toString());
    }

    private void initViews() {
        textGeneratedPassword = findViewById(R.id.text_generated_password);
        btnGeneratePassword = findViewById(R.id.button_generate_password);
        btnCopyPassword = findViewById(R.id.button_password_copy);
        btnSavePassword = findViewById(R.id.button_password_save);
        btnSavePassword.setEnabled(false);
    }

    private void clickListeners() {
        btnGeneratePassword.setOnClickListener(view -> {

            PasswordGenerator securePasswordGenerator = new SecurePasswordGenerator(passwordLength);
            PasswordGenerator insecurePasswordGenerator = new InsecurePasswordGenerator(passwordLength);

            String secureGeneratedPassword = securePasswordGenerator.generatePassword();
            String insecureGeneratedPassword = insecurePasswordGenerator.generatePassword();

            textGeneratedPassword.setText(secureGeneratedPassword);

            btnSavePassword.setEnabled(true);
        });

        btnCopyPassword.setOnClickListener(view -> {
            ClipboardManager manager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            manager.setPrimaryClip(ClipData.newPlainText("password", textGeneratedPassword.getText().toString()));
            Toast.makeText(this, "Password copied to clipboard", Toast.LENGTH_SHORT).show();
        });

        btnSavePassword.setOnClickListener(view -> {
            String generatedPassword = textGeneratedPassword.getText().toString();
            Intent intent = new Intent(MainActivity.this, SavePassword.class);
            intent.putExtra("password", generatedPassword);
            startActivity(intent);
        });
    }
}