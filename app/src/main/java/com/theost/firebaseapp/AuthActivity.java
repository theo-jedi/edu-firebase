package com.theost.firebaseapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.theost.firebaseapp.databinding.ActivityAuthBinding;

public class AuthActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;

    private ActivityAuthBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseAuth = FirebaseAuth.getInstance();
        binding = ActivityAuthBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.auth.setOnClickListener(view -> {
            authWithEmail(binding.email.getText().toString(), binding.password.getText().toString());
        });

        binding.forgotPassword.setOnClickListener(view -> {
            firebaseAuth.sendPasswordResetEmail(binding.email.getText().toString());
        });
    }

    private void authWithEmail(String email, String password) {
        // firebaseAuth.signInWithEmailAndPassword()
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        if (user != null) {
                            showUserInfoToast(user.getUid() + ": " + user.getEmail());
                            startMainActivity();
                        }
                    } else {
                        showErrorToast();
                    }
                })
                .addOnFailureListener(e -> showErrorToast());
    }

    private void startMainActivity() {
        startActivity(new Intent(this, MainActivity.class));
    }

    private void showErrorToast() {
        Toast.makeText(this, R.string.error, Toast.LENGTH_SHORT).show();
    }

    private void showUserInfoToast(String userInfo) {
        Toast.makeText(this, userInfo, Toast.LENGTH_SHORT).show();
    }
}
