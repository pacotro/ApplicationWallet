package com.example.applicationwallet;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Inscription extends AppCompatActivity {

    private FirebaseAuth mAuth;
    EditText pseudo,nom,email,password,verifpassword;
    String Pseudo,Fname,Email,Mdp,Verifmdp;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    String iChars = "~`!#$%^&*+=-[]\\\';,/{}|\":<>?";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);


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

                if (TextUtils.isEmpty(Pseudo)) {
                    pseudo.setError("Le nom d'utilisateur est requis");
                    return;
                }

                if (TextUtils.isEmpty(Fname)) {
                    email.setError("L'email est requis");
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


                if (!Mdp.equals(Verifmdp) && Mdp.length() != 0) {
                    verifpassword.setError("Les mots de passe sont différents !");
                    return;
                }

                String encrypted = "";
                try {
                    encrypted = AESUtils.encrypt(Mdp);
                    Log.d("TEST", "encrypted:" + encrypted);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                //Toast.makeText(getApplicationContext(), encrypted , Toast.LENGTH_SHORT).show();

                startActivity(new Intent(Inscription.this, InscriptionPhrase.class));

                /*
                dans la bdd il faut mettre le mdp crypter, le surnom, les clés (?) et creer une
                variable qui compte le  nombre de jour depuis la derniere fois que le mdp a ete changé
                on doit ajouter la phrase apres (ou sinon la phrase doit permettre de retrouver les
                clés ce qui parait plus simple
                */
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
