package com.example.prova1mateussamuel;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;

public class AnaliseTarefas extends AppCompatActivity {

    private RecyclerView recyclerTarefas;
    private TarefaAdapter adapter;
    private ArrayList<Tarefa> listaTarefas;
    private SQLiteDatabase bancoDados;
    private SimpleDateFormat dateFormatBanco = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
    private SimpleDateFormat dateFormatAnoMesDia = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    private SimpleDateFormat dateFormatExibicao = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
    private Date hoje = new Date();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analise_tarefas);

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

        ArrayList<Tarefa> tarefasParaOrdenar = new ArrayList<>();

        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String titulo = cursor.getString(1);
            String descricao = cursor.getString(2);
            String dataString = cursor.getString(3);
            try {
                Date dataTarefa = dateFormatBanco.parse(dataString);
                if (!dataTarefa.before(hoje)) {
                    tarefasParaOrdenar.add(new Tarefa(id, titulo, descricao, dateFormatAnoMesDia.format(dataTarefa))); // Salva no formato YYYY-MM-DD para ordenação
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        cursor.close();
        bancoDados.close();

        Collections.sort(tarefasParaOrdenar, new Comparator<Tarefa>() {
            @Override
            public int compare(Tarefa t1, Tarefa t2) {
                try {
                    Date d1 = dateFormatAnoMesDia.parse(t1.getData());
                    Date d2 = dateFormatAnoMesDia.parse(t2.getData());
                    return d1.compareTo(d2);
                } catch (ParseException e) {
                    return 0;
                }
            }
        });

        ArrayList<Tarefa> listaFinalTarefas = new ArrayList<>();
        for (Tarefa tarefa : tarefasParaOrdenar) {
            try {
                Date dataOrdenada = dateFormatAnoMesDia.parse(tarefa.getData());
                listaFinalTarefas.add(new Tarefa(tarefa.getId(), tarefa.getTitulo(), tarefa.getDescricao(), dateFormatExibicao.format(dataOrdenada)));
            } catch (ParseException e) {
                e.printStackTrace();
                listaFinalTarefas.add(tarefa);
            }
        }

        listaTarefas = listaFinalTarefas;
        adapter = new TarefaAdapter(listaTarefas);
        recyclerTarefas.setAdapter(adapter);
    }

    public void TelaPrincipal(View v){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}