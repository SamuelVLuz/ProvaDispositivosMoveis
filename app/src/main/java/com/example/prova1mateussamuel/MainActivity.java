package com.example.prova1mateussamuel;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.net.InetSocketAddress;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private SQLiteDatabase bancoDados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        criarBancoDados();
    }
    @Override
    protected void onResume(){
        super.onResume();
    }
    public void criarBancoDados(){
        //ABRE OU CRIA O BANCO DE DADOS ESTOQUE
        bancoDados = openOrCreateDatabase("ListaTarefas", MODE_PRIVATE, null);
        try {
            //CRIA A TABELA SE ELA NÃO EXISTIR
            bancoDados.execSQL("CREATE TABLE IF NOT EXISTS tarefa(" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT" +
                    ", titulo VARCHAR" +
                    ", descricao VARCHAR" +
                    ", data DATE)");
            bancoDados.close();
        } catch (Exception e) {
        }
    }

    public void telaCadastro(View v){
        Intent i = new Intent(this, CadastrarTarefa.class);
        startActivity(i);
    }

    public void telaSobreDupla(View v){
        Intent i = new Intent(this, SobreDupla.class);
        startActivity(i);
    }

    public void telaListagem(View v){
        Intent i = new Intent(this, ListagemTarefas.class);
        startActivity(i);
    }

    public void siteCurso(View v){
        Uri uri = Uri.parse("https://presencial.ifrs.edu.br");
        Intent intent = new Intent(Intent. ACTION_VIEW, uri);
        startActivity(intent);
    }

    public void telaAnalise(View v){
        Intent i = new Intent(this, AnaliseTarefas.class);
        startActivity(i);
    }


}