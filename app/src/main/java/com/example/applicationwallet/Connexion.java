package com.example.applicationwallet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;


public class Connexion extends AppCompatActivity {

    private EditText editTextMail;
    private EditText editTextMdp;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connexion);

        this.editTextMail = (EditText) findViewById(R.id.editTextEmail);
        this.editTextMdp = (EditText) findViewById(R.id.editTextPassword);

        mAuth=FirebaseAuth.getInstance();


        Button Connexion = (Button)findViewById(R.id.buttonCo);

        Connexion.setOnClickListener(v -> {
            String phrase = editTextMail.getText().toString().trim();
            String password = editTextMdp.getText().toString().trim();
            String password1 = editTextMdp.getText().toString().trim();

            String Encrypted = "";
            try {
                Encrypted = AESUtils.encrypt(password);
                Log.d("TEST", "encrypted:" + Encrypted);
            } catch (Exception e) {
                e.printStackTrace();
            }

            password=Encrypted;

            if(phrase.isEmpty())
            {
                editTextMail.setError("La phrase secrete est vide");
                editTextMail.requestFocus();
                return;
            }
            if(!Patterns.EMAIL_ADDRESS.matcher(phrase).matches())
            {
                editTextMail.setError("Enter la phrase secrete valide");
                editTextMail.requestFocus();
                return;
            }
            if(password.isEmpty())
            {
                editTextMdp.setError("Le mot de passe est vide");
                editTextMdp.requestFocus();
                return;
            }

            mAuth.signInWithEmailAndPassword(phrase,password).addOnCompleteListener(task -> {
                if(task.isSuccessful())
                {
                    Intent i = new Intent(Connexion.this, ConnexionPhrase.class);
                    i.putExtra("mdp",password1);
                    startActivity(i);
                    //startActivity(new Intent(Connexion.this, ConnexionPhrase.class));
                }
                else
                {
                    Toast.makeText(Connexion.this,
                            "Mauvais identifiants",
                            Toast.LENGTH_SHORT).show();
                }
        });});
    }


}