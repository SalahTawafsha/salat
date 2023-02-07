package com.example.test;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainInterface();
    }

    private void mainInterface() {

        StringBuilder readString = new StringBuilder();
        try {
            FileInputStream fIn = openFileInput("myFile.txt");
            InputStreamReader isr = new InputStreamReader(fIn);
            if (isr.ready()) {
                int i;
                while ((i = isr.read()) != -1)
                    readString.append((char) i);


                Toast.makeText(getBaseContext(), "File added successfully!",
                        Toast.LENGTH_SHORT).show();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();


            String str = "عمر سليمان:\nعمر جمال:\nمحمد الحج:\nانس ابو عويس:\nمصعب الصوص:\nصلاح ابو العليا:\nحمزة سعيد:";

            FileOutputStream fOut;
            try {
                fOut = openFileOutput("myFile.txt",
                        MODE_PRIVATE);
                OutputStreamWriter osw = new OutputStreamWriter(fOut);
                //---write the string to the file---

                osw.write(str);

                osw.flush();
                osw.close();

                FileInputStream fIn = openFileInput("myFile.txt");
                InputStreamReader isr = new InputStreamReader(fIn);

                int i;
                while ((i = isr.read()) != -1)
                    readString.append((char) i);

            } catch (IOException e) {
                e.printStackTrace();
            }


        }


        ArrayList<String> names = new ArrayList<>();
        setContentView(R.layout.activity_main);
        EditText t = findViewById(R.id.editTextNumber);
        EditText t1 = findViewById(R.id.textView2);
        EditText t3 = findViewById(R.id.t1);

        Button b = findViewById(R.id.tod);
        Button b1 = findViewById(R.id.tom);
        Button addRem = findViewById(R.id.addDel);

        String finalRead = readString.toString();

        addRem.setOnClickListener(e -> secInterface(finalRead));
        b.setOnClickListener(e -> {
            t1.setText("");
            names.clear();
            if (!String.valueOf(t3.getText()).equals("") && !String.valueOf(t.getText()).equals("")) {
                StringBuilder str = new StringBuilder();
                for (int i = 0; i < finalRead.length(); i++) {
                    if (finalRead.charAt(i) == '\n') {
                        names.add(str.toString());
                        str = new StringBuilder();
                    } else
                        str.append(finalRead.charAt(i));
                }
                names.add(str.toString());

                int x = Integer.parseInt(String.valueOf(t3.getText()));

                int start = Integer.parseInt(t.getText().toString());

                StringBuilder s = new StringBuilder("صفحات اليوم🖤\n");
                for (int i = 0; i < names.size(); i++) {
                    s.append(names.get(i)).append(" ");
                    for (int j = 0; j < x; j++)
                        s.append(start++).append(" + ");
                    s = new StringBuilder(s.substring(0, s.length() - 3));
                    s.append("\n");
                }

                t1.setText(s.toString());
            }
        });

        b1.setOnClickListener(e -> {
            t1.setText("");
            names.clear();
            if (!String.valueOf(t3.getText()).equals("") && !String.valueOf(t.getText()).equals("")) {

                StringBuilder str = new StringBuilder();
                for (int i = 0; i < finalRead.length(); i++) {
                    if (finalRead.charAt(i) == '\n') {
                        names.add(str.toString());
                        str = new StringBuilder();
                    } else
                        str.append(finalRead.charAt(i));
                }
                names.add(str.toString());


                int x = Integer.parseInt(String.valueOf(t3.getText()));

                int start = Integer.parseInt(t.getText().toString());

                StringBuilder s = new StringBuilder("صفحات الغد 🖤\n");
                for (int i = 0; i < names.size(); i++) {
                    s.append(names.get(i)).append(" ");
                    for (int j = 0; j < x; j++)
                        s.append(start++).append(" + ");
                    s = new StringBuilder(s.substring(0, s.length() - 3));
                    s.append("\n");
                }

                t1.setText(s.toString());
            }
        });

    }

    private void secInterface(String str) {
        setContentView(R.layout.add_del);
        Button back = findViewById(R.id.back);
        Button save = findViewById(R.id.save);
        TextView textView = findViewById(R.id.textView);
        textView.setText(str);
        back.setOnClickListener(e1 -> mainInterface());

        save.setOnClickListener(e -> {
            String os = String.valueOf(textView.getText());
            FileOutputStream fOut;
            try {
                fOut = openFileOutput("myFile.txt",
                        MODE_PRIVATE);
                OutputStreamWriter osw = new OutputStreamWriter(fOut);
                //---write the string to the file---

                osw.write(os);

                osw.flush();
                osw.close();

            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });
    }
}