package com.example.applicationwallet;

import androidx.appcompat.app.AppCompatActivity;


import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class InscriptionPhrase extends AppCompatActivity {

    private ClipboardManager clipboardManager;
    private TextView listOfWords;

    String word;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription_phrase);

        Intent intent = getIntent();
        String words = intent.getStringExtra("Phrase");

        String word = words.replace(" ", "\n");

        TextView tv1 = (TextView)findViewById(R.id.listOfWords);
        tv1.setMovementMethod(new ScrollingMovementMethod());
        tv1.append(word);



        TextView ListOfWords = (TextView)findViewById(R.id.listOfWords);
        Button copy = (Button)findViewById(R.id.Paste);
        Button valider = (Button)findViewById(R.id.buttonCo);
        this.clipboardManager = (ClipboardManager) this.getSystemService(Context.CLIPBOARD_SERVICE);
        this.listOfWords = (TextView) findViewById(R.id.listOfWords);

        copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doCopy();
            }
        });

        valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(InscriptionPhrase.this, Connexion.class));
            }
        });

    }

    private void doCopy() {
        String txtCopy = this.listOfWords.getText().toString();
        ClipData clipData = ClipData.newPlainText("listOfWords", txtCopy);
        this.clipboardManager.setPrimaryClip(clipData);
        Toast.makeText(getApplicationContext(), "Copier dans le presse-papier", Toast.LENGTH_SHORT).show();
    }
}