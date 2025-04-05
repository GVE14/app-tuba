package com.app.tuba;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.app.tuba.ui.ClientFormActivity;


public class MainActivity extends AppCompatActivity {

    private static final String EXTRA_MODE = "MODE";
    private static final String MODE_CREATE = "CREATE";
    private static final String MODE_UPDATE = "UPDATE";

    private Button createClientButton, searchClientButton, updateClientButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeUI();

        setupButtonListeners();
    }

    private void initializeUI() {
        // Componentes da UI
        TextView mainTitleTextView = findViewById(R.id.mainTitleTextView);
        TextView clientSectionTitleTextView = findViewById(R.id.clientSectionTitleTextView);
        createClientButton = findViewById(R.id.createClientButton);
        updateClientButton = findViewById(R.id.updateClientButton);
        searchClientButton = findViewById(R.id.searchClientButton);
    }

    private void setupButtonListeners() {
        // Botão para criar cliente
        createClientButton.setOnClickListener(v -> navigateToClientForm(MODE_CREATE));

        // Botão para atualizar cliente
        updateClientButton.setOnClickListener(v -> navigateToClientForm(MODE_UPDATE));

        // Botão para buscar cliente
        searchClientButton.setOnClickListener(v -> navigateToClientSearch());
    }

    private void navigateToClientForm(@NonNull String mode) {
        Intent intent = new Intent(this, ClientFormActivity.class);
        intent.putExtra(EXTRA_MODE, mode);
        startActivity(intent);
    }

    private void navigateToClientSearch() {
//        Intent intent = new Intent(this, ClientSearchActivity.class);
//        startActivity(intent);
    }
}