package com.example.prova1mateussamuel;
import android.os.Parcel;
import android.os.Parcelable;


public class Tarefa implements Parcelable {
    private int id;
    private String titulo;
    private String descricao;
    private String data;

    public Tarefa(int id, String titulo, String descricao, String data) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.data = data;
    }

    protected Tarefa(Parcel in) {
        id = in.readInt();
        titulo = in.readString();
        descricao = in.readString();
        data = in.readString();
    }

    public static final Creator<Tarefa> CREATOR = new Creator<Tarefa>() {
        @Override
        public Tarefa createFromParcel(Parcel in) {
            return new Tarefa(in);
        }

        @Override
        public Tarefa[] newArray(int size) {
            return new Tarefa[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(titulo);
        dest.writeString(descricao);
        dest.writeString(data);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    // Getters
    public int getId() { return id; }
    public String getTitulo() { return titulo; }
    public String getDescricao() { return descricao; }
    public String getData() { return data; }
}
