package tuliocota.com.br.primeiroapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;

public class RelogioActivity extends AppCompatActivity {

    private TextView labelRelogio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relogio);

        labelRelogio = findViewById(R.id.labelRelogio);

        final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

        Thread t = new Thread(){
            @Override
            public void run() {
                while(true){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            labelRelogio.setText(sdf.format(new Date()));
                        }
                    });

                    Log.i("relogio", sdf.format(new Date()));
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        t.start();
    }
}
