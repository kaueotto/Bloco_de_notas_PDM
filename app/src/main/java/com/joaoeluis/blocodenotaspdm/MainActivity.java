package com.joaoeluis.blocodenotaspdm;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity {

    ListView listaNotas;
    EditText txtNotas;
    ImageButton btnAdd;
    ArrayList<String> notas = new ArrayList<>();
    ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        carregarNotas();

        txtNotas = (EditText)findViewById(R.id.txtNota);
        arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,notas);

        listaNotas = (ListView)findViewById(R.id.listaNotas);
        listaNotas.setAdapter(arrayAdapter);
        listaNotas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                AlertDialog.Builder adb = new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Excluir")
                        .setMessage("Deseja mesmo excluir?")
                        .setNegativeButton("Não",null)
                        .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                notas.remove(position);
                                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.joaoeluis.blocodenotaspdm", Context.MODE_PRIVATE);
                                HashSet<String> setNotas = new HashSet<String>(notas);
                                sharedPreferences.edit().putStringSet("notas",setNotas).apply();

                                arrayAdapter.notifyDataSetInvalidated();
                            }
                        });

                adb.show();

            }
        });


        btnAdd = (ImageButton)findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(txtNotas.getText().toString().length()>0){
                    notas.add(txtNotas.getText().toString());

                    SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.joaoeluis.blocodenotaspdm", Context.MODE_PRIVATE);
                    HashSet<String> setNotas = new HashSet<String>(notas);
                    sharedPreferences.edit().putStringSet("notas",setNotas).apply();

                    arrayAdapter.notifyDataSetInvalidated();
                }

            }
        });


    }

    public void carregarNotas(){
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.joaoeluis.blocodenotaspdm", Context.MODE_PRIVATE);
        HashSet<String> setNotas = (HashSet<String>) sharedPreferences.getStringSet("notas",null);
        if(setNotas!=null){
            notas= new ArrayList<String>(setNotas);
        }

    }


}
