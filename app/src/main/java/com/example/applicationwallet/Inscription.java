package com.example.applicationwallet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Inscription extends AppCompatActivity {

    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    EditText pseudo,nom,email,password,verifpassword;
    String Pseudo,Fname,Email,Mdp,Verifmdp;
    String userID;
    Date date;
    String DateDuJour;
    String[] Phrase;
    String PhraseSecrete;

    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    String iChars = "~`!#$%^&*+=-[]\\\';,/{}|\":<>?";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        email = findViewById(R.id.editTextNewMail);
        password = findViewById(R.id.editTextNewPassword);
        verifpassword = findViewById(R.id.editTextNewCPassword);
        pseudo = findViewById(R.id.editTextNewUserName);
        nom = findViewById(R.id.editTextNewFullName);

        Button btn = (Button) findViewById(R.id.Benregistrer);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Pseudo = pseudo.getText().toString().trim();
                Fname = nom.getText().toString().trim();
                Email = email.getText().toString().trim();
                Mdp = password.getText().toString().trim();
                Verifmdp = verifpassword.getText().toString().trim();
                date = new Date();
                DateDuJour = date.toString();

                if (TextUtils.isEmpty(Pseudo)) {
                    pseudo.setError("Le nom d'utilisateur est requis");
                    return;
                }

                if (TextUtils.isEmpty(Fname)) {
                    nom.setError("Le nom complet est requis");
                    return;
                }

                if (TextUtils.isEmpty(Email)) {
                    email.setError("L'email est requis");
                    return;
                }

                if (!Email.matches(emailPattern)) {
                    email.setError("L'email n'est pas correct");
                    return;
                }

                if (TextUtils.isEmpty(Mdp)) {
                    password.setError("Le mot de passe est requis");
                    return;
                }

                if (Mdp.length() < 8 ) {
                    password.setError("Le mot de passe doit contenir au moins 6 caractères");
                    return;
                }

                if (!StrongPassword(Mdp)){
                    password.setError("Le mot de passe doit contenir au moins 1 minuscule 1 majuscule 1 caractere spécial et 1 chiffre");
                    //return;
                    //a reactiver plus tard
                }


                if (!Mdp.equals(Verifmdp) && Mdp.length() != 0) {
                    verifpassword.setError("Les mots de passe sont différents !");
                    return;
                }

                String Encrypted = "";
                try {
                    Encrypted = AESUtils.encrypt(Mdp);
                    Log.d("TEST", "encrypted:" + Encrypted);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                Phrase = generateSecretPhrase();
                PhraseSecrete = String.join(" ", Phrase);

                fAuth.createUserWithEmailAndPassword(Email, Encrypted).addOnCompleteListener (new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            userID = fAuth.getCurrentUser().getUid();
                            DocumentReference documentReference = fStore.collection("users").document(userID);
                            Map<String,Object> user = new HashMap<>();
                            user.put("nom", Fname);
                            user.put("Pseudo", Pseudo);
                            user.put("Phrase secrete", PhraseSecrete);
                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d("Tag", "onSuccess: Profil bien créé pour "+ userID);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d("Tag", "onFailure: " + e.toString());
                                }
                            });
                            Intent i = new Intent(Inscription.this, InscriptionPhrase.class);
                            i.putExtra("Phrase",PhraseSecrete);
                            startActivity(i);

                        } else {
                            Log.i("Tag",task.getException().getMessage());
                            Toast.makeText(Inscription.this, "L'inscription a échoué", Toast.LENGTH_SHORT).show();
                        }
                    }

                });

            }
        });
    }

    boolean Digital (String texte) {
        for (int i =0 ;  i < texte.length(); i++) {
            if (Character.isDigit(texte.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    boolean Special (String texte) {
        for (int i =0 ;  i < texte.length(); i++) {
            if (!Character.isLetterOrDigit(texte.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    boolean Upper (String texte) {
        for (int i =0 ;  i < texte.length(); i++) {
            if (Character.isUpperCase(texte.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    boolean Lower (String texte) {
        for (int i =0 ;  i < texte.length(); i++) {
            if (Character.isLowerCase(texte.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    boolean StrongPassword(String texte) {
        if (!Digital(texte) || !Lower(texte) || !Upper(texte) || !Special(texte)) {
            return false;
        }
        return true;
    }

    public void ShowHidePass1(View view){
        if(view.getId()==R.id.VoirMdp1){
            if(password.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())){
                password.setTransformationMethod(HideReturnsTransformationMethod.getInstance()); }
            else{ password.setTransformationMethod(PasswordTransformationMethod.getInstance()); }
        }  }

    public void ShowHidePass2(View view){
        if(view.getId()==R.id.VoirMdp2){
            if(verifpassword.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())){
                verifpassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance()); }
            else{ verifpassword.setTransformationMethod(PasswordTransformationMethod.getInstance()); }
        }  }

    String[] generateSecretPhrase(){

        int min = 0;
        int max = 78855;

        ArrayList listeOfNumber = new ArrayList();
        ArrayList<String> listeOfWords = new ArrayList<String>();
        Random random = new Random();

        for(int i = 0; i < 12; ++i){
            int value = random.nextInt(max + min) + min;
            listeOfNumber.add(value);
        }

        for(int j = 0; j < listeOfNumber.size(); ++j){
            int a= (int) listeOfNumber.get(j);

            try
            {
                InputStream is = this.getResources().openRawResource(R.raw.words);
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));

                for(int i = 0; i < a-1 ; ++i){
                    reader.readLine();}

                String lineIWant = reader.readLine();
                listeOfWords.add(lineIWant); }

            catch(Exception e) { e.printStackTrace(); }

        }
        String[] word = new String[12];
        int a = listeOfWords.size();
        for(int i=0; i<a ;i++){
            word[i]=listeOfWords.get(i);
        }
        return word ;
    }

    }
