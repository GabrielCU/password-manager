package com.example.passwordmanager;

import android.app.Dialog;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.passwordmanager.models.Password;

public class PasswordDetailsDialog extends DialogFragment {

    private static final String ARG_PASSWORD = "password";

    private Password password;
    private TextView passwordTextView;
    private boolean isPasswordVisible = false;

    public static PasswordDetailsDialog newInstance(Password password) {
        PasswordDetailsDialog fragment = new PasswordDetailsDialog();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PASSWORD, password);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_password_details, container, false);

        if (getArguments() != null) {
            password = (Password) getArguments().getSerializable(ARG_PASSWORD);
        }

        TextView nameTextView = view.findViewById(R.id.detailNameTextView);
        TextView loginTextView = view.findViewById(R.id.detailLoginTextView);
        passwordTextView = view.findViewById(R.id.detailPasswordTextView);
        ImageButton togglePasswordButton = view.findViewById(R.id.togglePasswordButton);

        nameTextView.setText(password.getName());
        loginTextView.setText(password.getLogin());
        passwordTextView.setText(password.getPassword());
        passwordTextView.setTransformationMethod(new PasswordTransformationMethod());

        togglePasswordButton.setOnClickListener(v -> togglePasswordVisibility());

        return view;
    }

    private void togglePasswordVisibility() {
        if (isPasswordVisible) {
            passwordTextView.setTransformationMethod(new PasswordTransformationMethod());
            isPasswordVisible = false;
        } else {
            passwordTextView.setTransformationMethod(null);
            isPasswordVisible = true;
        }
    }
}
