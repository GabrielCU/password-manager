package com.example.passwordmanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.passwordmanager.models.Password;

import java.util.List;

public class PasswordAdapter extends RecyclerView.Adapter<PasswordAdapter.PasswordViewHolder> {

    private List<Password> passwordList;
    private Context context;

    public PasswordAdapter(List<Password> passwordList, Context context) {
        this.passwordList = passwordList;
        this.context = context;
    }

    @NonNull
    @Override
    public PasswordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View passwordItemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_password, parent, false);
        return new PasswordViewHolder(passwordItemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PasswordViewHolder holder, int position) {
        Password password = passwordList.get(position);
        holder.bind(password);
    }

    @Override
    public int getItemCount() {
        return passwordList.size();
    }

    public class PasswordViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView passwordNameTextView;
        private TextView passwordLoginTextView;
        private Password password;

        public PasswordViewHolder(@NonNull View itemView) {
            super(itemView);
            passwordNameTextView = itemView.findViewById(R.id.passwordNameTextView);
            passwordLoginTextView = itemView.findViewById(R.id.passwordLoginTextView);

            itemView.setOnClickListener(this);
        }

        public void bind(Password password) {
            this.password = password;
            passwordNameTextView.setText(password.getName());
            passwordLoginTextView.setText(password.getLogin());
        }

        @Override
        public void onClick(View v) {
            // Handle item click to show password details
            // For example, navigate to a detail view or show a dialog
            // We'll implement this in the next section
            showPasswordDetails(password);
        }
    }

    private void showPasswordDetails(Password password) {
        // Show a dialog with password details
        PasswordDetailsDialog dialog = PasswordDetailsDialog.newInstance(password);
        dialog.show(((AppCompatActivity) context).getSupportFragmentManager(), "passwordDetails");
    }
}
