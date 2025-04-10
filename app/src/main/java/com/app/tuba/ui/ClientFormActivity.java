package com.app.tuba.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.app.tuba.R;
import com.app.tuba.model.Cliente;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

public class ClientFormActivity extends AppCompatActivity {

    private TextInputEditText nameEditText, addressEditText, cpfCnpjEditText, emailEditText,
            phoneEditText, paymentMethodEditText;
    private TextView carPlatesPreviewTextView, formTitleTextView;
    private CheckBox requestNfCheckBox, blockedCheckBox;
    private Button cancelButton, saveButton, managePlatesButton;

    private final List<String> carPlates = new ArrayList<>();
    private FirebaseFirestore firestore;

    // Regex para validação de CPF e CNPJ
    private static final Pattern CPF_PATTERN = Pattern.compile("\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}");
    private static final Pattern CNPJ_PATTERN = Pattern.compile("\\d{2}\\.\\d{3}\\.\\d{3}/\\d{4}-\\d{2}");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_form);

        initializeUI();
        setupActionBar();

        firestore = FirebaseFirestore.getInstance();

        managePlatesButton.setOnClickListener(v -> showPlatesDialog());

        saveButton.setOnClickListener(v -> salvarClienteNoFirebase());

        cancelButton.setOnClickListener(v -> finish());
    }

    private void initializeUI() {
        formTitleTextView = findViewById(R.id.formTitleTextView);
        nameEditText = findViewById(R.id.nameEditText);
        addressEditText = findViewById(R.id.addressEditText);
        cpfCnpjEditText = findViewById(R.id.cpfCnpjEditText);
        emailEditText = findViewById(R.id.emailEditText);
        phoneEditText = findViewById(R.id.phoneEditText);
        paymentMethodEditText = findViewById(R.id.paymentMethodEditText);
        managePlatesButton = findViewById(R.id.managePlatesButton);
        carPlatesPreviewTextView = findViewById(R.id.carPlatesPreviewTextView);
        requestNfCheckBox = findViewById(R.id.requestNfCheckBox);
        blockedCheckBox = findViewById(R.id.blockedCheckBox);
        cancelButton = findViewById(R.id.cancelButton);
        saveButton = findViewById(R.id.saveButton);
    }

    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showPlatesDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Gerenciar Placas");

        final TextInputEditText input = new TextInputEditText(this);
        input.setHint("Digite uma nova placa");

        builder.setView(input);
        builder.setPositiveButton("Adicionar", (dialog, which) -> {
            String newPlate = input.getText().toString().trim().toUpperCase();
            if (!TextUtils.isEmpty(newPlate) && !carPlates.contains(newPlate)) {
                carPlates.add(0, newPlate);
                updateCarPlatesPreview();
                Toast.makeText(this, "Placa adicionada", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Placa inválida ou duplicada", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Cancelar", (dialog, which) -> dialog.cancel());

        builder.show();
    }

    private void updateCarPlatesPreview() {
        if (carPlates.isEmpty()) {
            carPlatesPreviewTextView.setText("Nenhuma placa cadastrada");
        } else {
            List<String> reversed = new ArrayList<>(carPlates);
            Collections.reverse(reversed);
            carPlatesPreviewTextView.setText("Placas: " + TextUtils.join(", ", reversed));
        }
    }

    private boolean validateFields() {
        boolean isValid = true;

        if (isEmpty(nameEditText)) {
            nameEditText.setError("Preencha o nome");
            isValid = false;
        }

        if (isEmpty(addressEditText)) {
            addressEditText.setError("Preencha o endereço");
            isValid = false;
        }

        if (isEmpty(cpfCnpjEditText)) {
            cpfCnpjEditText.setError("Informe CPF ou CNPJ");
            isValid = false;
        } else if (!validateCpfCnpjFormat(cpfCnpjEditText.getText().toString().trim())) {
            cpfCnpjEditText.setError("Formato de CPF/CNPJ inválido");
            isValid = false;
        }

        if (isEmpty(emailEditText)) {
            emailEditText.setError("Informe o e-mail");
            isValid = false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailEditText.getText().toString().trim()).matches()) {
            emailEditText.setError("E-mail inválido");
            isValid = false;
        }

        if (isEmpty(phoneEditText)) {
            phoneEditText.setError("Informe o telefone");
            isValid = false;
        }

        if (isEmpty(paymentMethodEditText)) {
            paymentMethodEditText.setError("Informe o método de pagamento");
            isValid = false;
        }

        if (carPlates.isEmpty()) {
            Toast.makeText(this, "Adicione pelo menos uma placa", Toast.LENGTH_SHORT).show();
            isValid = false;
        }

        return isValid;
    }

    private boolean isEmpty(TextInputEditText editText) {
        return editText.getText() == null || editText.getText().toString().trim().isEmpty();
    }

    private boolean validateCpfCnpjFormat(String cpfCnpj) {
        return CPF_PATTERN.matcher(cpfCnpj).matches() || CNPJ_PATTERN.matcher(cpfCnpj).matches();
    }

    private void salvarClienteNoFirebase() {
        String id = firestore.collection("cliente").document().getId();

        Cliente cliente = new Cliente(
                id,
                nameEditText.getText().toString().trim(),
                addressEditText.getText().toString().trim(),
                emailEditText.getText().toString().trim(),
                phoneEditText.getText().toString().trim(),
                cpfCnpjEditText.getText().toString().trim(),
                paymentMethodEditText.getText().toString().trim(),
                requestNfCheckBox.isChecked(),
                blockedCheckBox.isChecked(),
                new ArrayList<>(carPlates)
        );

        firestore.collection("cliente")
                .document(id)
                .set(cliente)
                .addOnSuccessListener(unused -> {
                    Toast.makeText(this, "Cliente salvo com sucesso no Firestore!", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Erro ao salvar no Firestore: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
}
