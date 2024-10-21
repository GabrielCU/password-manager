package com.example.passwordmanager.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Base64;
import androidx.annotation.Nullable;

import javax.crypto.SecretKey;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "PASSWORDS";
    private static final int DB_VERSION = 1;

    public DatabaseHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(
                "CREATE TABLE PASSWORDS" +
                        "(id INTEGER PRIMARY KEY, name TEXT, password TEXT, iv TEXT, login TEXT, update_date DATE);"
        );
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS PASSWORDS");
        onCreate(sqLiteDatabase);
    }

    public boolean insert(Password password) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = password.getContentValues();
        return db.insert("PASSWORDS", null, values) != -1;
    }

    public boolean secureInsert(Password password, Context context) {
        try {
            SecretKey aesKey = getSecretKey(context);

            // Encrypt the password
            EncryptedData encryptedData = EncryptionUtils.encrypt(password.getPassword(), aesKey);

            // Store the IV and ciphertext as Base64 strings
            String encryptedPassword = Base64.encodeToString(encryptedData.getCiphertext(), Base64.DEFAULT);
            String ivString = Base64.encodeToString(encryptedData.getIv(), Base64.DEFAULT);

            SQLiteDatabase db = getWritableDatabase();
            ContentValues values = password.getContentValues();

            // Replace the plaintext password with the encrypted version
            values.put("password", encryptedPassword);
            values.put("iv", ivString);

            return db.insert("PASSWORDS", null, values) != -1;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private SecretKey getSecretKey(Context context) throws Exception {
        // Try to retrieve the encrypted AES key
        byte[] encryptedKey = EncryptedKeyStorage.getEncryptedAESKey(context);

        if (encryptedKey == null) {
            // Generate a new AES key
            SecretKey aesKey = AESKeyHelper.generateAESKey();

            // Encrypt the AES key with the RSA public key
            PublicKey publicKey = KeyStoreManager.getPublicKey();
            byte[] encryptedAESKey = RSAEncryptionHelper.encryptAESKey(aesKey, publicKey);

            // Store the encrypted AES key
            EncryptedKeyStorage.storeEncryptedAESKey(context, encryptedAESKey);

            return aesKey;
        } else {
            // Decrypt the AES key with the RSA private key
            PrivateKey privateKey = KeyStoreManager.getPrivateKey();
            return RSADecryptionHelper.decryptAESKey(encryptedKey, privateKey);
        }
    }

    public List<Password> getPasswordList() {
        List<Password> passwords = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM PASSWORDS ", null);
        cursor.moveToFirst();

        while(!cursor.isAfterLast()) {
            Password password = Password.fromCursor(cursor);
            passwords.add(password);
            cursor.moveToNext();
        }

        return passwords;
    }

    public List<Password> secureGetPasswordList(Context context) {
        List<Password> passwords = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM PASSWORDS", null);

        if (cursor.moveToFirst()) {
            do {
                Password password = Password.fromCursor(cursor);

                try {
                    // Retrieve the encrypted password and IV
                    String encryptedPassword = cursor.getString(cursor.getColumnIndex("password"));
                    String ivString = cursor.getString(cursor.getColumnIndex("iv"));

                    // Decode from Base64
                    byte[] ciphertext = Base64.decode(encryptedPassword, Base64.DEFAULT);
                    byte[] iv = Base64.decode(ivString, Base64.DEFAULT);

                    // Decrypt the password
                    EncryptedData encryptedData = new EncryptedData(iv, ciphertext);
                    SecretKey aesKey = getSecretKey(context);
                    String decryptedPassword = EncryptionUtils.decrypt(encryptedData, aesKey);

                    // Set the decrypted password
                    password.setPassword(decryptedPassword);
                } catch (Exception e) {
                    e.printStackTrace();
                    // Handle decryption error
                    password.setPassword(null);
                }

                passwords.add(password);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return passwords;
    }

}
