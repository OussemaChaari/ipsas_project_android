package com.ouss.cheque.chequesclients;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Add extends AppCompatActivity {

    Button AddClient,AddCheque;
    Intent addClientForm,addChequeForm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        AddClient= (Button)findViewById(R.id.addClient);
        AddCheque= (Button)findViewById(R.id.addCheque);
        AddClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addClientForm= new Intent(Add.this,AddClientForm.class);
                startActivity(addClientForm);
            }
        });
        AddCheque.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addChequeForm= new Intent(Add.this,AddChequeForm.class);
                startActivity(addChequeForm);
            }
        });
    }
}
