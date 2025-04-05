package com.app.tuba.data;

import com.app.tuba.model.Cliente;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FirebaseHelper {

    private final FirebaseDatabase database;
    private final DatabaseReference reference;

    public FirebaseHelper() {
        database = FirebaseDatabase.getInstance();
        reference = database.getReference();
        database.setPersistenceEnabled(true);
    }

    public DatabaseReference getDatabaseReference() {
        return reference;
    }

    // Exemplos de métodos:

    public void writeData(String path, Object data) {
        reference.child(path).setValue(data);
    }

    public void readData(String path, ValueEventListener listener) {
        reference.child(path).addValueEventListener(listener);
    }

    public void insertCliente(Cliente cliente, DatabaseReference.CompletionListener listener) {

        String clienteId = reference.child("cliente").push().getKey();

        // Define o ID do cliente no objeto
        cliente.setId(clienteId);

        // Define o caminho de inserção do cliente, usando o ID como chave
        reference.child("cliente").child(clienteId)
                .setValue(cliente, listener);
    }

}
