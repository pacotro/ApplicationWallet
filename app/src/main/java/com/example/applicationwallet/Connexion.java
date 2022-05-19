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


}