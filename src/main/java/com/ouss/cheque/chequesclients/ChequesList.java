package com.ouss.cheque.chequesclients;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ChequesList extends AppCompatActivity {

    TextView amount,date,state,client;
    Intent intent;
    ChequeAdapter adapter;
    ArrayList<ChequeEntity> cheques;
    ChequeEntity cheque;
    ListView list;
    String nouvétat;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheques_list);
        list = (ListView)findViewById(R.id.listview_with_fab);
        final DbHandler dbHandler= new DbHandler(ChequesList.this);
        addDbEntries(dbHandler);
        FloatingActionButton add = findViewById(R.id.fab);
        cheques= dbHandler.GetCheques();
        adapter= new ChequeAdapter(cheques,getApplicationContext());
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent,View v,int  pos,long id) {
                position= pos;
                cheque= cheques.get(pos);
                nouvétat=cheque.getState().toLowerCase().contains("no")? "Soldé": "Non soldé";
                Snackbar.make(v, "Changer l'état à "+nouvétat+ " ?",Snackbar.LENGTH_LONG).
                        setAction("Oui", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                cheques.set(position,new ChequeEntity(cheque.getId(),cheque.getAmount(),cheque.getDate(),nouvétat,cheque.getUserEmail()));
                                list.invalidateViews();
                                dbHandler.UpdateCheque(cheque.getId(),cheque.getState());
                            }
                        }).show();



            }
        });

        add.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                intent= new Intent(ChequesList.this, Add.class);
                startActivity(intent);
            }


        });
    }

    private void addDbEntries(DbHandler dbHandler) {
        dbHandler.insertUserCheque("3333","30-01-2019","Non soldé","abc@gmail.com");
        dbHandler.insertUserCheque("1234","30-07-2020","soldé","xyz@gmail.com");
        dbHandler.insertUserCheque("9090","01-11-2019","Non soldé","ouss@gmail.com");
        dbHandler.insertUserCheque("3341","30-11-2019","soldé","chaari@gmail.com");
        dbHandler.insertUserCheque("233","30-12-2019","Non soldé","wass@gmail.com");
        dbHandler.insertUserCheque("3333","30-11-2019","soldé","im@gmail.com");
        dbHandler.insertUserCheque("200","30-10-2019","Non soldé","fa@gmail.com");
        dbHandler.insertUserCheque("3333","30-03-2019","Non soldé","eiz@gmail.com");
        dbHandler.insertUserCheque("6598","30-02-2019","soldé","blah@gmail.com");
        dbHandler.insertUserDetails("Hammadi","Black","abc@gmail.com");
        dbHandler.insertUserDetails("Hamid","Black","xyz@gmail.com");
        dbHandler.insertUserDetails("Hammadi","Black","wass@gmail.com");
        dbHandler.insertUserDetails("badi","e","ouss@gmail.com");
        dbHandler.insertUserDetails("Samir","White","blah@gmail.com");

    }
}
