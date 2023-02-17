package com.example.test;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {
    private final ArrayList<String> names = new ArrayList<>();
    private EditText start;
    private EditText result;
    private EditText numOfPages;
    private Button today;
    private Button tomorrow;
    private Button update;
    private StringBuilder readString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        start = findViewById(R.id.editTextNumber);
        result = findViewById(R.id.textView2);
        numOfPages = findViewById(R.id.t1);

        today = findViewById(R.id.tod);
        tomorrow = findViewById(R.id.tom);
        update = findViewById(R.id.addDel);

    }

    @Override
    protected void onResume() {
        super.onResume();
        mainInterface();
    }

    private void mainInterface() {
        names.clear();
        readString = new StringBuilder();
        try {
            FileInputStream fIn = openFileInput("myFile.txt");
            InputStreamReader isr = new InputStreamReader(fIn);
            Scanner scan = new Scanner(isr);

            if (isr.ready())
                while (scan.hasNextLine()) {
                    String s = scan.nextLine();
                    names.add(s);
                    readString.append(s).append("\n");
                }


        } catch (IOException ioe) {
            ioe.printStackTrace();


            String str = "عمر سليمان:\n" +
                    "عمر جمال:\n" +
                    "محمد الحج:\n" +
                    "انس ابو عويس:\n" +
                    "مصعب الصوص:\n" +
                    "صلاح ابو العليا:\n" +
                    "مهتدي درابي:\n" +
                    "نور فقهاء:\n" +
                    "محمد خالد:";

            readString = new StringBuilder(str);
            names.addAll(Arrays.asList("عمر سليمان", "عمر جمال", "محمد الحج", "انس ابو عويس", "مصعب الصوص", "صلاح ابو العليا", "مهتدي درابي", "نور الفقهاء", "محمد خالد"));

            FileOutputStream fOut;
            try {
                fOut = openFileOutput("myFile.txt",
                        MODE_PRIVATE);
                OutputStreamWriter osw = new OutputStreamWriter(fOut);
                //---write the string to the file---

                osw.write(str);

                osw.flush();
                osw.close();


            } catch (IOException e) {
                e.printStackTrace();
            }


        }

        update.setOnClickListener(e -> secInterface(readString.toString()));

        today.setOnClickListener(e -> {
            result.setText("");
            if (!String.valueOf(numOfPages.getText()).equals("") && !String.valueOf(start.getText()).equals("")) {

                int x = Integer.parseInt(String.valueOf(numOfPages.getText()));
                int start = Integer.parseInt(this.start.getText().toString());

                StringBuilder s = new StringBuilder("صفحات اليوم🖤\n");
                for (int i = 0; i < names.size(); i++) {
                    s.append(names.get(i)).append(" ");
                    for (int j = 0; j < x; j++)
                        s.append(start++).append(" + ");
                    s = new StringBuilder(s.substring(0, s.length() - 3));
                    s.append("\n");
                }

                result.setText(s.toString());
            }
        });

        tomorrow.setOnClickListener(e -> {
            result.setText("");
            if (!String.valueOf(numOfPages.getText()).equals("") && !String.valueOf(start.getText()).equals("")) {

                int x = Integer.parseInt(String.valueOf(numOfPages.getText()));
                int start = Integer.parseInt(this.start.getText().toString());

                StringBuilder s = new StringBuilder("صفحات الغد 🖤\n");
                for (int i = 0; i < names.size(); i++) {
                    s.append(names.get(i)).append(" ");
                    for (int j = 0; j < x; j++)
                        s.append(start++).append(" + ");
                    s = new StringBuilder(s.substring(0, s.length() - 3));
                    s.append("\n");
                }

                result.setText(s.toString());
            }
        });

    }

    private void secInterface(String str) {

        try {
            Intent intent = new Intent(this, Class.forName("com.example.test.UpdateActivity"));
            intent.putExtra("read", str);
            intent.putExtra("prev", "main");


            startActivity(intent);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void copy(View view) {
        android.content.ClipboardManager clipboard = (android.content.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        android.content.ClipData clip = android.content.ClipData.newPlainText("copy", result.getText().toString());
        clipboard.setPrimaryClip(clip);
        Toast.makeText(this, "Text Copied", Toast.LENGTH_SHORT).show();
    }
}