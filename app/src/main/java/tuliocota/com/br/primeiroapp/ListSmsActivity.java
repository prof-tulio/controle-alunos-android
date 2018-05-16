package tuliocota.com.br.primeiroapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import tuliocota.com.br.primeiroapp.dao.AlunoDao;
import tuliocota.com.br.primeiroapp.entidade.MensagemSms;

public class ListSmsActivity extends AppCompatActivity {

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_sms);

        listView = findViewById(R.id.listView);
        carregarLista();
    }

    private void carregarLista(){
        AlunoDao dao = new AlunoDao(this);
        List<MensagemSms> msgs = dao.listarUltimosSms();
        dao.close();

        ArrayAdapter<MensagemSms> adapter = new ArrayAdapter<MensagemSms>(this,
                android.R.layout.simple_list_item_1, msgs);
        listView.setAdapter(adapter);
    }

}
