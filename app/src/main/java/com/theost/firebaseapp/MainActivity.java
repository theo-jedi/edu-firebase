package com.theost.firebaseapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.theost.firebaseapp.databinding.ActivityMainBinding;

import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private static final String COLLECTION_COLORS = "colors";

    private ListenerRegistration colorsListener;

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.addButton.setOnClickListener(view -> {
            addColor(Objects.requireNonNull(binding.editText.getText()).toString().trim());
        });

        binding.getButton.setOnClickListener(view -> getColors());
    }

    @Override
    protected void onStart() {
        super.onStart();
        addColorsListener();
    }

    @Override
    protected void onStop() {
        super.onStop();
        removeColorsListener();
    }

    private void addColor(String code) {
        FirebaseFirestore.getInstance()
                .collection(COLLECTION_COLORS)
                .document()
                .set(new Color(code))
                .addOnSuccessListener(unused -> {
                    binding.dataTextView.setText(getString(R.string.success));
                })
                .addOnFailureListener(e -> {
                    binding.dataTextView.setText(getString(R.string.error));
                });
    }

    private void getColors() {
        FirebaseFirestore.getInstance()
                .collection(COLLECTION_COLORS)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<Color> colors = queryDocumentSnapshots.toObjects(Color.class);
                    binding.dataTextView.setText(colors.toString());
                })
                .addOnFailureListener(e -> {
                    binding.dataTextView.setText(getString(R.string.error));
                });
    }

    private void addColorsListener() {
        colorsListener = FirebaseFirestore.getInstance().collection(COLLECTION_COLORS)
                .addSnapshotListener((value, error) -> {
                    if (error == null && value != null) {
                        List<Color> colors = value.toObjects(Color.class);
                        binding.queryTextView.setText(colors.toString());
                    }
                });
    }

    private void removeColorsListener() {
        if (colorsListener != null) colorsListener.remove();
    }
}