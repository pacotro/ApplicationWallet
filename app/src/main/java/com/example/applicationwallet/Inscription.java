package com.example.applicationwallet;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class Inscription extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);

        Button btn = (Button) findViewById(R.id.Benregistrer);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Inscription.this, InscriptionPhrase.class));
            }
        });
/*
        //Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        Button btnEnregistrer = (Button)findViewById(R.id.Benregistrer);

        EditText editTextNewUserName = (EditText) this.findViewById(R.id.editTextNewUserName);
        EditText editTextNewFullName = (EditText) this.findViewById(R.id.editTextNewFullName);
        EditText editTextNewMail = (EditText) this.findViewById(R.id.editTextNewMail);
        EditText editTextNewPassword = (EditText) this.findViewById(R.id.editTextNewPassword);
        EditText editTextNewCPassword = (EditText) this.findViewById(R.id.editTextNewCPassword);

        String NewUserName = editTextNewUserName.getText().toString();
        String NewFullName = editTextNewFullName.getText().toString();
        String NewMail = editTextNewMail.getText().toString();
        String NewPassword = editTextNewPassword.getText().toString();
        String NewCPassword = editTextNewCPassword.getText().toString();


        btnEnregistrer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                createAccount(NewMail,NewPassword );
                Toast.makeText(Inscription.this, "Inscription réussite",
                        Toast.LENGTH_SHORT).show();
                /*
                Toast.makeText(Inscription.this, NewPassword,
                        Toast.LENGTH_SHORT).show();
                if (NewPassword==NewCPassword){
                    Toast.makeText(Inscription.this, "OK",
                            Toast.LENGTH_SHORT).show();
                    createAccount(NewMail,NewPassword );
                    Toast.makeText(Inscription.this, "Inscription réussite",
                            Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(Inscription.this, "Les mots de passes ne sont pas identique",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });


    }



    //Lors de l'initialisation de votre activité, vérifiez si l'utilisateur est actuellement connecté.
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            reload();
        }
    }

    //méthode qui prend une adresse e - mail et mot de passe, les valide, et crée ensuite
    //un nouvel utilisateur avec la createUserWithEmailAndPassword méthode.
    private void createAccount(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(Inscription.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(Inscription.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }});}

        /*
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(Inscription.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });*/





    /*
    private void sendEmailVerification() {
        // Send verification email
        // [START send_email_verification]
        final FirebaseUser user = mAuth.getCurrentUser();
        user.sendEmailVerification()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // Email sent
                    }
                });
        // [END send_email_verification]
    }

    private void reload() { }

    private void updateUI(FirebaseUser user) {

    }*/
    }
}