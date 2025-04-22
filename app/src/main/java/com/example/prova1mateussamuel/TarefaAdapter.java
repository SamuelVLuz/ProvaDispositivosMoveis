package com.example.prova1mateussamuel;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TarefaAdapter extends RecyclerView.Adapter<TarefaAdapter.TarefaViewHolder> {

    private List<Tarefa> tarefas;

    public TarefaAdapter(List<Tarefa> tarefas) {
        this.tarefas = tarefas;
    }

    @NonNull
    @Override
    public TarefaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_tarefa, parent, false);
        return new TarefaViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TarefaViewHolder holder, int position) {
        Tarefa tarefa = tarefas.get(position);
        holder.textTitulo.setText(tarefa.getTitulo());

        holder.btnDetalhes.setOnClickListener(v -> {
            Context context = v.getContext();
            Intent intent = new Intent(context, DetalhesTarefa.class);
            intent.putExtra("tarefa", tarefa);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return tarefas.size();
    }

    public static class TarefaViewHolder extends RecyclerView.ViewHolder {
        TextView textTitulo;
        Button btnDetalhes;

        public TarefaViewHolder(View itemView) {
            super(itemView);
            textTitulo = itemView.findViewById(R.id.textTitulo);
            btnDetalhes = itemView.findViewById(R.id.btnDetalhes);
        }
    }
}