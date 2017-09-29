package com.example.rotomugear;

import android.content.Context;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rotomugear.logic.Cryption.Caesar;
import com.example.rotomugear.logic.Cryption.Cryption;
import com.example.rotomugear.logic.Cryption.Polybius;
import com.example.rotomugear.logic.Cryption.Substitution;

import java.util.ArrayList;
import java.util.List;

public class CryptionActivity extends AppCompatActivity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_son, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private Spinner spinner;
    private List<String> spinnerList;
    private ArrayAdapter<String> spinnerAdapter;
    private Toolbar toolbar;
    private ActionBar actionBar;
    private Cryption cryption;
    private Button encryp;
    private Button decryp;
    private EditText input;
    private TextView output;
    private EditText key;
    private InputMethodManager imm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cryption);

        imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        input = (EditText)findViewById(R.id.text_input);
        output = (TextView)findViewById(R.id.text_result);
        key = (EditText)findViewById(R.id.input_key);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        actionBar.setTitle(getResources().getString(R.string.cryption_title).toString());

        spinner = (Spinner)findViewById(R.id.spinner_type);
        spinnerList = new ArrayList<>();
        spinnerList.add("Caesar");
        spinnerList.add("Substitution");
        spinnerList.add("Polybius");
        spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerList);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (spinnerAdapter.getItem(i)){
                    case "Caesar":
                        cryption = new Caesar();
                        break;
                    case "Substitution":
                        cryption = new Substitution();
                        break;
                    case "Polybius":
                        cryption = new Polybius();
                        break;
                    default:
                        cryption = null;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        encryp = (Button)findViewById(R.id.encryption);
        encryp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String plaintext = input.getText().toString();
                String k = key.getText().toString();

                if (plaintext.isEmpty() || k.isEmpty()){
                    Toast.makeText(CryptionActivity.this, "Empty input or key", Toast.LENGTH_SHORT).show();
                }else{
                    String r = cryption.encryption(plaintext, k);
                    output.setText(r);
                }
                imm.hideSoftInputFromWindow(key.getWindowToken(), 0);
            }
        });

        decryp = (Button)findViewById(R.id.decryption);
        decryp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String plaintext = input.getText().toString();
                String k = key.getText().toString();

                if (plaintext.isEmpty() || k.isEmpty()){
                    Toast.makeText(CryptionActivity.this, "Empty input or key", Toast.LENGTH_SHORT).show();
                }else{
                    String r = cryption.decryption(plaintext, k);
                    output.setText(r);
                }
                imm.hideSoftInputFromWindow(key.getWindowToken(), 0);
            }
        });
    }


}
