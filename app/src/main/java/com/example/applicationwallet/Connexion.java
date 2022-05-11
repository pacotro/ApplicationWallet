package com.example.applicationwallet;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Connexion extends AppCompatActivity {

    private EditText editTextMail;
    private Button Coller;
    private ClipboardManager clipboardManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connexion);

        this.clipboardManager = (ClipboardManager) this.getSystemService(Context.CLIPBOARD_SERVICE);
        this.editTextMail = (EditText) findViewById(R.id.editTextMail);
        this.Coller = (Button) findViewById(R.id.Coller);

        this.Coller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doPaste();
            }
        });

        Button Connexion = (Button)findViewById(R.id.buttonConnexion);

        Connexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Connexion.this, MainPage.class));
            }
        });
    }

    private void doPaste() {
        ClipData primaryClipData = this.clipboardManager.getPrimaryClip();
        ClipData.Item item = primaryClipData.getItemAt(0);
        String txtPaste = item.getText().toString();
        this.editTextMail.setText(txtPaste);
    }



    /**
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connexion);

        mAuth = FirebaseAuth.getInstance();

        EditText editTextMail = (EditText) this.findViewById(R.id.editTextMail);
        EditText editTextPassword = (EditText) this.findViewById(R.id.editTextPassword);

        String Mail = editTextMail.getText().toString();
        String Password = editTextPassword.getText().toString();

        Button btnConnexion = (Button)findViewById(R.id.buttonConnexion);

        btnConnexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn(Mail,Password );
                Toast.makeText(Connexion.this, "Connexion réussite",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }



    //méthode qui prend une adresse e - mail et mot de passe, les valide et signe alors
    // un utilisateur avec la signInWithEmailAndPassword méthode.
    private void signIn(String email, String password) {

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(Connexion.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });

    }

    private void reload() { }

    private void updateUI(FirebaseUser user) {

    }

**/

}