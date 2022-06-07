package com.example.applicationwallet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Keys extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keys);

        Button buttonVoir = (Button)findViewById(R.id.buttonVoir);
        EditText editpassword = findViewById(R.id.editTextTextPassword);

        Intent intent = getIntent();
        String password1 = intent.getStringExtra("mdp");
        String publique = intent.getStringExtra("clé1");
        String prive = intent.getStringExtra("clé2");

        TextView tv1 = (TextView)findViewById(R.id.clé);
        tv1.append(publique);


        buttonVoir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pwdUtilisateur = editpassword.getText().toString().trim();
                if (pwdUtilisateur.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Le mot de passe est vide", Toast.LENGTH_SHORT).show();
                }else{
                    if(pwdUtilisateur.equals(password1)){
                        TextView tv2 = (TextView)findViewById(R.id.clé2);
                        tv2.append(prive);
                    }else {
                        Toast.makeText(getApplicationContext(), "Le mot de passe est incorrecte", Toast.LENGTH_SHORT).show();
                    } }
            }
        });

    }
}