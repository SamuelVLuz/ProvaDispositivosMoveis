package com.example.prova1mateussamuel;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class CadastrarTarefa extends AppCompatActivity {

    EditText editTitulo, editDesc, editData;
    Calendar dataAtual = Calendar.getInstance();
    SQLiteDatabase bancoDados;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cadastrar_tarefa);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        editTitulo = findViewById(R.id.editTextTitulo);
        editDesc = findViewById(R.id.editTextDescricao);
        editData = findViewById(R.id.editTextData);

        editData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirJanela();
            }
        });
    }

    public void abrirJanela(){
        int ano = dataAtual.get(Calendar.YEAR);
        int mes = dataAtual.get(Calendar.MONTH);
        int dia = dataAtual.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int anoEscolhido, int mesEscolhido, int diaEscolhido) {
                dataAtual.set(Calendar.YEAR, anoEscolhido);
                dataAtual.set(Calendar.MONTH, mesEscolhido);
                dataAtual.set(Calendar.DAY_OF_MONTH, diaEscolhido);
                updateLabel();
            }
        }, ano, mes, dia);
        datePickerDialog.show();
    }

    private void updateLabel(){
        String myformat = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myformat, new Locale("pt","BR"));

        editData.setText(sdf.format(dataAtual.getTime()));
    }

    public void cadastrarTarefa(View v){
        //SE ESSE TIVER ALGUMA COISA ESCRITA ELE PASSA
        if(!TextUtils.isEmpty(editTitulo.getText().toString())){
            try {
                bancoDados = openOrCreateDatabase("ListaTarefas", MODE_PRIVATE, null);
                String sql = "INSERT INTO tarefa (titulo, descricao, data) VALUES (?, ?, ?)";
                SQLiteStatement stmt = bancoDados.compileStatement(sql);
                stmt.bindString(1, editTitulo.getText().toString());
                stmt.bindString(2, editDesc.getText().toString());
                stmt.bindString(3, editData.getText().toString());
                stmt.executeInsert();
                finish();
            }catch (Exception e) {
                Log.d("teste2", "passou aqui");
            }
        }
    }
    public void TelaPrincipal(View v){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }


}