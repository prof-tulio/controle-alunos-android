package tuliocota.com.br.primeiroapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    private EditText editUsuario;
    private EditText editSenha;

    private SharedPreferences getSharedPreferences(){
        return PreferenceManager.getDefaultSharedPreferences(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences preferences = getSharedPreferences();
        if (preferences.getString("login", null) != null){
            abrirListActivity();
            finish();
            return;
        }

        setContentView(R.layout.activity_login);

        editUsuario = findViewById(R.id.editUsuario);
        editSenha = findViewById(R.id.editSenha);

    }

    public void acessar(View view){
        String login = editUsuario.getText().toString();
        String senha = editSenha.getText().toString();
        if (login.equals("admin") && senha.equals("1234")){
            finish();
            SharedPreferences preferences = getSharedPreferences();
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("login", login);
            editor.commit();
            abrirListActivity();
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Usuário e/ou senha inválido(s).")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
            builder.create();
            builder.show();
        }
    }

    private void abrirListActivity() {
        Intent intent = new Intent(this, ListActivity.class);
        startActivity(intent);
    }
}
