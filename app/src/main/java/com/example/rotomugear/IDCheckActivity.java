package com.example.rotomugear;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.rotomugear.logic.IDCheck;

public class IDCheckActivity extends AppCompatActivity {

    private EditText inputText;
    private FloatingActionButton fab;
    private TextView outputText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_idcheck);

        inputText = (EditText)findViewById(R.id.text_input);
        fab = (FloatingActionButton)findViewById(R.id.resolve);
        outputText = (TextView)findViewById(R.id.text_resolve_result);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String inputID = inputText.getText().toString();
                String resolveResult = IDCheck.resolveResult(inputID);
                outputText.setText(resolveResult);
            }
        });

    }
}
