package tuliocota.com.br.primeiroapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class PessoaActivity extends AppCompatActivity {

    private EditText textNome;
    private TextView labelSaudacao;
    private Button buttonOK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pessoa);

        textNome = findViewById(R.id.editNome);
        labelSaudacao = findViewById(R.id.labelSaudacao);
        buttonOK = findViewById(R.id.buttonOK);

        buttonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PessoaActivity.this, "Teste", Toast.LENGTH_LONG).show();
            }
        });

    }

    public void processar(View v){
        String nome = textNome.getText().toString();
        String saudacao = getString(R.string.msg_saudacao, nome);
        labelSaudacao.setText(saudacao);
        Log.i("appPessoa", saudacao);
        Toast.makeText(this, saudacao, Toast.LENGTH_LONG).show();
    }
}
