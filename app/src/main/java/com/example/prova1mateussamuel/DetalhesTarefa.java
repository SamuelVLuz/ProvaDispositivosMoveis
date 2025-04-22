package com.example.prova1mateussamuel;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.prova1mateussamuel.databinding.ActivityDetalhesTarefaBinding;

// DetalhesTarefa.java
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class DetalhesTarefa extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_tarefa);

        Tarefa tarefa = getIntent().getParcelableExtra("tarefa");

        TextView textTitulo = findViewById(R.id.textTituloDetalhe);
        TextView textDescricao = findViewById(R.id.textDescricaoDetalhe);
        TextView textData = findViewById(R.id.textDataDetalhe);

        if (tarefa != null) {
            textTitulo.setText(tarefa.getTitulo());
            textDescricao.setText(tarefa.getDescricao());
            textData.setText(tarefa.getData());
        }
    }

    public void retornar(View v){
        finish();
    }
}
