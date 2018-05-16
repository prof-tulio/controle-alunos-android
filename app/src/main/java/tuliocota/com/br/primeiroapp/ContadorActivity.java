package tuliocota.com.br.primeiroapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import tuliocota.com.br.primeiroapp.service.ContadorService;

public class ContadorActivity extends AppCompatActivity {

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contador);

        intent = new Intent(this, ContadorService.class);
    }

    public void iniciarService(View view){
        startService(intent);
    }

    public void pararService(View view){
        stopService(intent);
    }
}
