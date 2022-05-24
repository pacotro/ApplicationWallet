package com.example.applicationwallet;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class ConnexionPhrase extends AppCompatActivity {

    private EditText listOfWords;
    private Button Coller;
    private Button Connexion;
    private ClipboardManager clipboardManager;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    StorageReference storageReference;
    String userID;
    String phrase;
    String Phrase;
    String phraseUtilisateur;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connexion_phrase);

        this.clipboardManager = (ClipboardManager) this.getSystemService(Context.CLIPBOARD_SERVICE);
        this.listOfWords = (EditText) findViewById(R.id.listOfWords);
        this.Coller = (Button) findViewById(R.id.Paste);
        this.Connexion = (Button) findViewById(R.id.buttonCo);

        phraseUtilisateur = listOfWords.getText().toString().trim();

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();

        userID = fAuth.getCurrentUser().getUid();



        DocumentReference documentReference = fStore.collection("users").document(userID);

        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if (documentSnapshot.exists()) {
                    phrase = documentSnapshot.getString("Phrase_secrete");
                    Phrase = phrase.replace(" ", "\n");
                }else {
                    Log.d("tag", "onEvent: Le document n'existe pas");
                }
            }
       });

        this.Coller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doPaste();
            }
        });

        this.Connexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phraseUtilisateur = listOfWords.getText().toString().trim();
                if (phraseUtilisateur.isEmpty()){
                    Toast.makeText(getApplicationContext(), "La phrase est vide", Toast.LENGTH_SHORT).show();
                }else{
                    if(phraseUtilisateur.equals(Phrase)){
                        startActivity(new Intent(ConnexionPhrase.this, MainPage.class));
                    }else {
                        Toast.makeText(getApplicationContext(), "La phrase est incorrecte", Toast.LENGTH_SHORT).show();
                    } }
            }
        });

    }

    private void doPaste() {
        ClipData primaryClipData = this.clipboardManager.getPrimaryClip();
        ClipData.Item item = primaryClipData.getItemAt(0);
        String txtPaste = item.getText().toString();
        this.listOfWords.setText(txtPaste);
    }

}