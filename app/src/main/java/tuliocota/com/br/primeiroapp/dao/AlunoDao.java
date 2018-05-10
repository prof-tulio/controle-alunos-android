package tuliocota.com.br.primeiroapp.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import tuliocota.com.br.primeiroapp.entidade.Aluno;

/**
 * Created by tulio on 09/03/2018.
 */

public class AlunoDao extends SQLiteOpenHelper {

    public AlunoDao(Context context) {
        super(context, "db_alunos", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE alunos (" +
                "id INTEGER PRIMARY KEY, " +
                "nome TEXT NOT NULL, " +
                "endereco TEXT, " +
                "telefone TEXT, " +
                "site TEXT, " +
                "email TEXT, " +
                "nota REAL, " +
                "faltas INTEGER );";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS alunos";
        db.execSQL(sql);
        onCreate(db);
    }

    public void salvar(Aluno aluno) {
        ContentValues values = new ContentValues();
        values.put("nome", aluno.getNome());
        values.put("endereco", aluno.getEndereco());
        values.put("telefone", aluno.getTelefone());
        values.put("site", aluno.getSite());
        values.put("nota", aluno.getNota());
        values.put("faltas", aluno.getFaltas());
        values.put("email", aluno.getEmail());

        SQLiteDatabase db = getWritableDatabase();
        if (aluno.getId() == null) {
            db.insert("alunos", null, values);
        } else {
            db.update("alunos", values, "id = ?",
                    new String[]{aluno.getId().toString()});
        }
        db.close();
    }

    public List<Aluno> listar() {
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT * FROM alunos ORDER BY nome";
        Cursor cursor = db.rawQuery(sql, null);

        List<Aluno> alunos = new ArrayList<>();
        while (cursor.moveToNext()) {
            Aluno aluno = getAluno(cursor);
            alunos.add(aluno);
        }

        return alunos;
    }

    @NonNull
    private Aluno getAluno(Cursor cursor) {
        Aluno aluno = new Aluno();
        aluno.setId(cursor.getLong(cursor.getColumnIndex("id")));
        aluno.setNome(cursor.getString(cursor.getColumnIndex("nome")));
        aluno.setSite(cursor.getString(cursor.getColumnIndex("site")));
        aluno.setFaltas(cursor.getInt(cursor.getColumnIndex("faltas")));
        aluno.setNota(cursor.getDouble(cursor.getColumnIndex("nota")));
        aluno.setEmail(cursor.getString(cursor.getColumnIndex("email")));
        aluno.setEndereco(cursor.getString(cursor.getColumnIndex("endereco")));
        aluno.setTelefone(cursor.getString(cursor.getColumnIndex("telefone")));
        return aluno;
    }

    public void excluir(Aluno aluno) {
        String[] vet = new String[1];
        vet[0] = aluno.getId().toString();
        SQLiteDatabase db = getWritableDatabase();
        db.delete("alunos", "id = ?", vet);
        db.close();
    }

    public Aluno buscarPorTelefone(String telefone) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from alunos " +
                "where telefone = ?", new String[]{telefone});
        try {
            if (cursor.moveToNext()) {
                return getAluno(cursor);
            }
        } finally {
            cursor.close();
        }
        return null;
    }
}
