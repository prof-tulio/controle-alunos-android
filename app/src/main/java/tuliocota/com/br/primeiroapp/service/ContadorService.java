package tuliocota.com.br.primeiroapp.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class ContadorService extends Service {
    private ContadorThread thread;

    public ContadorService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (thread == null) {
            thread = new ContadorThread(this);
            thread.start();
        }

        return super.onStartCommand(intent, flags, startId);
        //START_STICKY
        //START_NOT_STICKY
        //START_REDELIVER_INTENT
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        thread.parar();
    }
}
