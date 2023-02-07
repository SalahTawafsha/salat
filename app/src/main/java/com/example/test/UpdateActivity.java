package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class UpdateActivity extends AppCompatActivity {
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_del);

        String s = String.valueOf(getIntent().getExtras().get("read"));
        textView = findViewById(R.id.textView);
        textView.setText(s);

    }

    public void save(View view) {
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
    }

    public void back(View view) throws ClassNotFoundException {
        Intent intent = new Intent(this, Class.forName("com.example.test.MainActivity"));
        startActivity(intent);
    }
}