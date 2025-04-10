package com.app.tuba.ui;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.app.tuba.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class ClientSearchActivity extends AppCompatActivity {

    private TextInputEditText searchEditText;
    private Button floatingActionButton;

    private FirebaseFirestore firestore;

    private static final String TAG = "SearchActivityError:";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_search);

        initializeUI();
        searchClient();

    }

    private void initializeUI() {
        searchEditText = findViewById(R.id.searchEditText);
        floatingActionButton = findViewById(R.id.floatingActionButton);
    }

    private void searchClient() {
        String searchQuery = searchEditText.getText().toString().trim();

        searchClientBy(searchQuery);

    }

    private void searchClientBy(String searchQuery) {
        CollectionReference clientesRef = firestore.collection("cliente");


        clientesRef.whereEqualTo("name", searchQuery)
                .get()
                .addOnSuccessListener(result -> {
                    if (!result.isEmpty()) {
                        mostrarClientesEncontrados(result.getDocuments());
                    } else {
                        clientesRef.whereEqualTo("cpfCnpj", searchQuery)
                                .get()
                                .addOnSuccessListener(resultCpf -> {
                                    if (!resultCpf.isEmpty()) {
                                        mostrarClientesEncontrados(resultCpf.getDocuments());
                                    } else {
                                        clientesRef.whereArrayContains("carPlate", searchQuery)
                                                .get()
                                                .addOnSuccessListener(resultPlacas -> {
                                                    if (!resultPlacas.isEmpty()) {
                                                        mostrarClientesEncontrados(resultPlacas.getDocuments());
                                                    } else {
                                                        Log.d(TAG, "Nenhum cliente encontrado com esse dado.");
                                                    }
                                                })
                                                .addOnFailureListener(e -> Log.e(TAG, "Erro ao buscar por placas: ", e));
                                    }
                                })
                                .addOnFailureListener(e -> Log.e(TAG, "Erro ao buscar por cpf-Cnpj: ", e));
                    }
                })
                .addOnFailureListener(e -> Log.e(TAG, "Erro ao buscar por name: ", e));
    }

    private void mostrarClientesEncontrados(List<DocumentSnapshot> documentos) {
        for (DocumentSnapshot doc : documentos) {

            String id = doc.getId();
            String name = doc.getString("name");
            String phone = doc.getString("phone");
            String email = doc.getString("email");
            String adress = doc.getString("adress");
            String cpfCnpj = doc.getString("cpfCnpj");
            String paymentMethod = doc.getString("paymentMethod");
            Boolean requestNf = doc.getBoolean("requestNf");
            Boolean blocked = doc.getBoolean("blocked");
            List<String> carPlate = (List<String>) doc.get("carPlate");

        }
    }

}
