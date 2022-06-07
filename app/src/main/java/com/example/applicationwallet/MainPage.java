package com.example.applicationwallet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.util.Formatter;

import javax.crypto.Mac;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class MainPage extends AppCompatActivity {

    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;


    //static String phrase="phrase test essai tour premier croyable paco sara guillaume classe ecole nouveau";
    public final String PBKDF2_ALGORITHM = "PBKDF2WithHmacSHA1";

    // The following constants may be changed without breaking existing hashes.
    public final int SALT_BYTES = 64;
    public final int HASH_BYTES = 64;
    public final int PBKDF2_ITERATIONS = 2048;
    public final String CLEENFANT = "6";
    public final int ITERATION_INDEX = 0;
    public final int SALT_INDEX = 1;
    public final int PBKDF2_INDEX = 2;



    String phrase;
    String password1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        /**
        drawerLayout = findViewById(R.id.my_drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);**/

        Intent intent = getIntent();
        phrase= intent.getStringExtra("Phrase");
        password1= intent.getStringExtra("mdp");


        try
        {


            String password = password1;
            String hash =PBKDF2.createHash(password);
            String[] params = hash.split(":");
            String data = params[PBKDF2_INDEX];
            String hmac = calculateHMAC(data, phrase);
            System.out.println(hmac+" "+hmac.length());
            String privatekey = hmac.substring(0, 64);
            String publickey = hmac.substring(64,128);
            String priveetendu = calculateHMAC(privatekey, publickey);
            String publicetendu = calculateHMAC(priveetendu,publickey);
            String hmac2 = calculateHMAC(publicetendu, CLEENFANT);
            String priveenfant= hmac2+priveetendu;
            String clepublique=calculateHMAC(priveenfant, publickey);

            TextView tv1 = (TextView)findViewById(R.id.clé);
            tv1.setMovementMethod(new ScrollingMovementMethod());
            tv1.append(clepublique);

            //System.out.println("Private key: "+priveenfant+"\nPublic key: "+clepublique);
            Toast.makeText(getApplicationContext(), "oui", Toast.LENGTH_SHORT).show();


        }
        catch(Exception ex)
        {
            //System.out.println("ERROR: " + ex);
            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
        }

    }


    public  String createHash(String password)
            throws NoSuchAlgorithmException, InvalidKeySpecException
    {
        return createHash(password.toCharArray());
    }

    public  String createHash(char[] password)
            throws NoSuchAlgorithmException, InvalidKeySpecException
    {

        byte[] salt=fromHex(Salt(phrase));

        byte[] hash = pbkdf2(password, salt, PBKDF2_ITERATIONS, HASH_BYTES);
        System.out.println("Salt:" + toHex(salt) + "Hash:" +  toHex(hash));
        return PBKDF2_ITERATIONS + ":" + toHex(salt) + ":" +  toHex(hash);
    }


    public  boolean validatePassword(String password, String goodHash)
            throws NoSuchAlgorithmException, InvalidKeySpecException
    {
        return validatePassword(password.toCharArray(), goodHash);
    }


    public  boolean validatePassword(char[] password, String goodHash)
            throws NoSuchAlgorithmException, InvalidKeySpecException
    {

        String[] params = goodHash.split(":");
        int iterations = Integer.parseInt(params[ITERATION_INDEX]);
        byte[] salt = fromHex(params[SALT_INDEX]);
        byte[] hash = fromHex(params[PBKDF2_INDEX]);
        byte[] testHash = pbkdf2(password, salt, iterations, hash.length);
        return slowEquals(hash, testHash);
    }


    private  boolean slowEquals(byte[] a, byte[] b)
    {
        int diff = a.length ^ b.length;
        for(int i = 0; i < a.length && i < b.length; i++)
            diff |= a[i] ^ b[i];
        return diff == 0;
    }


    private  byte[] pbkdf2(char[] password, byte[] salt, int iterations, int bytes)
            throws NoSuchAlgorithmException, InvalidKeySpecException
    {
        PBEKeySpec spec = new PBEKeySpec(password, salt, iterations, bytes * 8);
        SecretKeyFactory skf = SecretKeyFactory.getInstance(PBKDF2_ALGORITHM);
        return skf.generateSecret(spec).getEncoded();
    }

    private  byte[] fromHex(String hex)
    {
        byte[] binary = new byte[hex.length() / 2];
        for(int i = 0; i < binary.length; i++)
        {
            binary[i] = (byte)Integer.parseInt(hex.substring(2*i, 2*i+2), 16);
        }
        return binary;
    }


    private  String toHex(byte[] array)
    {
        BigInteger bi = new BigInteger(1, array);
        String hex = bi.toString(16);
        int paddingLength = (array.length * 2) - hex.length();
        if(paddingLength > 0)
            return String.format("%0" + paddingLength + "d", 0) + hex;
        else
            return hex;
    }

    public  void main(String[] args)
    {
        try
        {


            String password = password1;
            String hash =PBKDF2.createHash(password);
            String[] params = hash.split(":");
            String data = params[PBKDF2_INDEX];
            String hmac = calculateHMAC(data, phrase);
            System.out.println(hmac+" "+hmac.length());
            String privatekey = hmac.substring(0, 64);
            String publickey = hmac.substring(64,128);
            String priveetendu = calculateHMAC(privatekey, publickey);
            String publicetendu = calculateHMAC(priveetendu,publickey);
            String hmac2 = calculateHMAC(publicetendu, CLEENFANT);
            String priveenfant= hmac2+priveetendu;
            String clepublique=calculateHMAC(priveenfant, publickey);

            TextView tv1 = (TextView)findViewById(R.id.clé);
            tv1.setMovementMethod(new ScrollingMovementMethod());
            tv1.append(clepublique);

            //System.out.println("Private key: "+priveenfant+"\nPublic key: "+clepublique);
            Toast.makeText(getApplicationContext(), "oui", Toast.LENGTH_SHORT).show();


        }
        catch(Exception ex)
        {
            //System.out.println("ERROR: " + ex);
            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
        }
    }

    public  String Salt(String phrase) {
        String msg="";
        for (int i=0;i<phrase.length();i++) {
            if (phrase.charAt(i)==' ' || i==0) {
                msg+=Integer.toHexString((int) phrase.charAt(i+1));
            }

        }
        return msg;
    }

    private  final String HMAC_SHA512 = "HmacSHA512";

    private static String toHexString(byte[] bytes) {
        Formatter formatter = new Formatter();
        for (byte b : bytes) {
            formatter.format("%02x", b);
        }
        return formatter.toString();
    }

    public  String calculateHMAC(String data, String key)
            throws SignatureException, NoSuchAlgorithmException, InvalidKeyException
    {
        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), HMAC_SHA512);
        Mac mac = Mac.getInstance(HMAC_SHA512);
        mac.init(secretKeySpec);
        return toHexString(mac.doFinal(data.getBytes()));
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
