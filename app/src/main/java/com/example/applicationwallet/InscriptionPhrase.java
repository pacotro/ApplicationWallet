package com.example.applicationwallet;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;


import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class InscriptionPhrase extends AppCompatActivity {

    private ClipboardManager clipboardManager;
    private TextView listOfWords;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription_phrase);
        generateSecretPhrase();

        TextView ListOfWords = (TextView)findViewById(R.id.listOfWords);
        Button copy = (Button)findViewById(R.id.CopyButton);
        Button valider = (Button)findViewById(R.id.Valider);
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

    public void generateSecretPhrase(){

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
                    reader.readLine();
                }
                String lineIWant = reader.readLine();
                listeOfWords.add(lineIWant);

            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }


        String[] word = new String[12];
        TextView tv1 = (TextView)findViewById(R.id.listOfWords);
        tv1.setMovementMethod(new ScrollingMovementMethod());
        int a = listeOfWords.size();
        for(int i=0; i<a ;i++){
            word[i]=listeOfWords.get(i);
        }

        for (int i=0; i<word.length;i++){
            tv1.append(word[i]);
            tv1.append("\n");
        }

    }



}