package com.jerry.passwordsafemeasure;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;

public class MainActivity extends AppCompatActivity {
    TextView show;
    EditText password;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        password = findViewById(R.id.paswwrd);
        show = findViewById(R.id.show);
        button = findViewById(R.id.button);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s = password.getText().toString();

                if (!Python.isStarted())
                    Python.start(new AndroidPlatform(MainActivity.this));

                Python py = Python.getInstance();
                PyObject pyobj = py.getModule("script");
                PyObject obj = pyobj.callAttr("main",s);
                show.setText(obj.toString());

                Log.d("manik",obj.toString());
            }
        });


    }
}