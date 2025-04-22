package com.example.prova1mateussamuel;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.prova1mateussamuel.databinding.ActivityListagemTarefasBinding;

public class ListagemTarefas extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityListagemTarefasBinding binding;

    private RecyclerView recyclerTarefas;
    private TarefaAdapter adapter;
    private ArrayList<Tarefa> listaTarefas;
    private SQLiteDatabase bancoDados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listagem_tarefas);

        recyclerTarefas = findViewById(R.id.recyclerTarefas);
        recyclerTarefas.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregarTarefas();
    }

    private void carregarTarefas() {
        listaTarefas = new ArrayList<>();
        bancoDados = openOrCreateDatabase("ListaTarefas", MODE_PRIVATE, null);
        bancoDados.execSQL("CREATE TABLE IF NOT EXISTS tarefa (id INTEGER PRIMARY KEY AUTOINCREMENT, titulo VARCHAR, descricao TEXT, data DATE)");

        Cursor cursor = bancoDados.rawQuery("SELECT id, titulo, descricao, data FROM tarefa", null);

        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String titulo = cursor.getString(1);
            String descricao = cursor.getString(2);
            String data = cursor.getString(3);
            listaTarefas.add(new Tarefa(id, titulo, descricao, data));
        }

        cursor.close();
        bancoDados.close();

        adapter = new TarefaAdapter(listaTarefas);
        recyclerTarefas.setAdapter(adapter);
    }
}