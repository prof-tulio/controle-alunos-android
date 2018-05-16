package tuliocota.com.br.primeiroapp.service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import tuliocota.com.br.primeiroapp.ListActivity;
import tuliocota.com.br.primeiroapp.ListSmsActivity;
import tuliocota.com.br.primeiroapp.R;

public class ContadorThread extends Thread {

    private Context context;
    private boolean ativo = true;

    public ContadorThread(Context context) {
        this.context = context;
    }

    public void parar() {
        ativo = false;
    }

    @Override
    public void run() {
        int cont = 0;
        while (cont < 1000 && ativo) {
            cont++;
            Log.i("contador", "Cont: " + cont);

            if (cont % 10 == 0) {

                NotificationCompat.Builder builder =
                        new NotificationCompat.Builder(context, "channel_contador")
                                .setAutoCancel(true)
                                .setSmallIcon(R.drawable.ic_launcher_background)
                                .setContentTitle("Notificação do App")
                                .setContentText("Isto é uma notificação de teste! " + cont);
                NotificationManager notificationManager =
                        (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);


                NotificationChannel notificationChannel = new NotificationChannel("channel_contador", "Notificações do contador", NotificationManager.IMPORTANCE_DEFAULT);
                notificationManager.createNotificationChannel(notificationChannel);


                Intent resultIntent = new Intent(context, ListActivity.class);
                builder.setContentIntent(PendingIntent.getActivity(context, 1, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT));

                notificationManager.notify(1, builder.build());
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
