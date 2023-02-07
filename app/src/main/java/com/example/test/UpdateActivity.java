package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
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
            if (String.valueOf(getIntent().getExtras().get("prev")).equals("main"))
                fOut = openFileOutput("myFile.txt",
                        MODE_PRIVATE);
            else
                fOut = openFileOutput("salat.txt",
                        MODE_PRIVATE);
            OutputStreamWriter osw = new OutputStreamWriter(fOut);
            //---write the string to the file---

            osw.write(os);

            osw.flush();
            osw.close();

            Toast.makeText(this, "File Updated = )", Toast.LENGTH_SHORT).show();

        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}