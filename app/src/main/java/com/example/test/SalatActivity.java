package com.example.test;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.os.HandlerCompat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Scanner;

public class SalatActivity extends AppCompatActivity {

    private String[] salat;
    private TextView result;
    private Button fajer;
    private Button dohor;
    private Button aser;
    private Button maghreb;
    private Button eshaa;
    private String finalRead;
    private ArrayList<String> names;
    private final Handler handler = HandlerCompat.createAsync(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salat);

        result = findViewById(R.id.textView1);
        fajer = findViewById(R.id.button);
        dohor = findViewById(R.id.button2);
        aser = findViewById(R.id.button5);
        maghreb = findViewById(R.id.button3);
        eshaa = findViewById(R.id.button4);

    }

    @Override
    protected void onResume() {
        super.onResume();
        fillSalat();

        checkFile();
    }

    private void checkFile() {
        StringBuilder readString = new StringBuilder();
        try {
            FileInputStream fIn = openFileInput("salat.txt");
            InputStreamReader isr = new InputStreamReader(fIn);
            if (isr.ready()) {
                int i;
                while ((i = isr.read()) != -1)
                    readString.append((char) i);

            }
        } catch (IOException ioe) {
            ioe.printStackTrace();


            String str = "عمر سليمان:\n" +
                    "محمد عادل:\n" +
                    "مهتدي:\n" +
                    "مصعب:\n" +
                    "صلاح:\n" +
                    "انس:\n" +
                    "عمر جمال:\n" +
                    "محمد خالد:\n" +
                    "محمد حسن:\n" +
                    "زياد:\n" +
                    "ابو حلوم:";

            FileOutputStream fOut;
            try {
                fOut = openFileOutput("salat.txt",
                        MODE_PRIVATE);
                OutputStreamWriter osw = new OutputStreamWriter(fOut);
                //---write the string to the file---

                osw.write(str);

                osw.flush();
                osw.close();

                FileInputStream fIn = openFileInput("salat.txt");
                InputStreamReader isr = new InputStreamReader(fIn);

                int i;
                while ((i = isr.read()) != -1)
                    readString.append((char) i);

            } catch (IOException e) {
                e.printStackTrace();
            }


        }


        names = new ArrayList<>();

        finalRead = readString.toString();

        names.clear();
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < finalRead.length(); i++) {
            if (finalRead.charAt(i) == '\n') {
                names.add(str.toString());
                str = new StringBuilder();
            } else
                str.append(finalRead.charAt(i));
        }
        names.add(str.toString());


    }


    private void fillSalat() {
        Thread t = new Thread(() -> {
            try {

                URL url = new URL("https://shobiddak.com/prayers/prayer_today?town_id=3");

                URLConnection con = url.openConnection();

                Scanner scan = new Scanner(con.getInputStream());
                salat = new String[6];
                int j = 0;
                while (scan.hasNextLine()) {
                    String s = scan.nextLine().trim();
                    if (s.startsWith("<table class=\"list_ads\"")) {
                        for (int i = 0; i < 6; i++) {
                            String[] tokens = scan.nextLine().trim().split("</td></tr>");
                            tokens = tokens[tokens.length - 1].split("</td><td>");
                            salat[j++] = tokens[tokens.length - 1];
                        }
                        break;
                    }
                }


                handler.post(() -> {
                    fajer.setEnabled(true);
                    dohor.setEnabled(true);
                    aser.setEnabled(true);
                    maghreb.setEnabled(true);
                    eshaa.setEnabled(true);
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        t.start();

    }

    public void update(View view) {
        try {
            Intent intent = new Intent(this, Class.forName("com.example.test.UpdateActivity"));
            intent.putExtra("read", finalRead);
            intent.putExtra("prev", "salat");

            startActivity(intent);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void fajer(View view) {
        StringBuilder s = new StringBuilder("صلاة الفجر ");
        s.append(salat[0]).append(" ").append(salat[1]).append(":\n");
        for (int i = 0; i < names.size(); i++) {
            s.append(names.get(i)).append(" ");
            s.append("\n");
        }

        result.setText(s.toString());
    }

    public void dohor(View view) {
        StringBuilder s = new StringBuilder("صلاة الظهر ");
        s.append(salat[2]).append(" ").append(salat[3]).append(":\n");
        for (int i = 0; i < names.size(); i++) {
            s.append(names.get(i)).append(" ");
            s.append("\n");
        }

        result.setText(s.toString());
    }


    public void aser(View view) {
        StringBuilder s = new StringBuilder("صلاة العصر ");
        s.append(salat[3]).append(" ").append(salat[4]).append(":\n");
        for (int i = 0; i < names.size(); i++) {
            s.append(names.get(i)).append(" ");
            s.append("\n");
        }

        result.setText(s.toString());
    }

    public void maghrepb(View view) {
        StringBuilder s = new StringBuilder("صلاة المغرب ");
        s.append(salat[4]).append(" ").append(salat[5]).append(":\n");
        for (int i = 0; i < names.size(); i++) {
            s.append(names.get(i)).append(" ");
            s.append("\n");
        }

        result.setText(s.toString());
    }

    public void eshaa(View view) {
        StringBuilder s = new StringBuilder("صلاة العشاء ");
        s.append(salat[5]).append(":\n");
        for (int i = 0; i < names.size(); i++) {
            s.append(names.get(i)).append(" ");
            s.append("\n");
        }

        result.setText(s.toString());
    }

    public void copy(View view) {
        android.content.ClipboardManager clipboard = (android.content.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        android.content.ClipData clip = android.content.ClipData.newPlainText("copy",result.getText().toString());
        clipboard.setPrimaryClip(clip);
        Toast.makeText(this, "Text Copied", Toast.LENGTH_SHORT).show();
    }
}