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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TarefaAdapter extends RecyclerView.Adapter<TarefaAdapter.TarefaViewHolder> {

    private List<Tarefa> tarefas;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
   private Date hoje = new Date();

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

        try {
            Date dataTarefa = dateFormat.parse(tarefa.getData());
            holder.textDataInfo.setText(formatarDataInfo(dataTarefa));
        } catch (ParseException e) {
            holder.textDataInfo.setText("Erro ao formatar data");
            e.printStackTrace();
        }

        holder.btnDetalhes.setOnClickListener(v -> {
            Context context = v.getContext();
            Intent intent = new Intent(context, DetalhesTarefa.class);
            intent.putExtra("tarefa", tarefa);
            context.startActivity(intent);
        });
    }

    private String formatarDataInfo(Date dataTarefa) {
        Calendar calendarHoje = Calendar.getInstance();
        Calendar calendarAmanha = Calendar.getInstance();
        calendarAmanha.add(Calendar.DAY_OF_YEAR, 1);
        Calendar calendarUmaSemana = Calendar.getInstance();
        calendarUmaSemana.add(Calendar.DAY_OF_YEAR, 7);
        Calendar calendarUmMes = Calendar.getInstance();
        calendarUmMes.add(Calendar.MONTH, 1);

        if (dataTarefa.before(hoje)) {
            return "Data Excedida";
        } else if (isSameDay(dataTarefa, calendarHoje.getTime())) {
            return "Hoje";
        } else if (isSameDay(dataTarefa, calendarAmanha.getTime())) {
            return "Amanhã";
        } else if (dataTarefa.before(calendarUmaSemana.getTime())) {
            return "Esta Semana";
        } else if (dataTarefa.before(calendarUmMes.getTime())) {
            return "Este Mês";
        } else {
            return dateFormat.format(dataTarefa);
        }
    }

    private boolean isSameDay(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH) &&
                cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);
    }

    @Override
    public int getItemCount() {
        return tarefas.size();

    }
    public static class TarefaViewHolder extends RecyclerView.ViewHolder {
        TextView textTitulo;
        TextView textDataInfo;
        Button btnDetalhes;

        public TarefaViewHolder(View itemView) {
            super(itemView);
            textTitulo = itemView.findViewById(R.id.textTitulo);
            textDataInfo = itemView.findViewById(R.id.textDataInfo);
            btnDetalhes = itemView.findViewById(R.id.btnDetalhes);
        }
    }
}