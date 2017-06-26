package com.example.joaos.virtualhelper.util;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.joaos.virtualhelper.activity.MainActivity;

/**
 * Created by Aluno on 12/06/2017.
 */
public class AlarmReceiver extends BroadcastReceiver {
    public static String NOTIFICATION_ID = "notification-id";
    public static int NOTIFICATION_ID_VALUE = 199;
    public static String NOTIFICATION = "notification";

    @Override
    public void onReceive(Context context, Intent intent) {
        int acao = intent.getExtras().getInt("action");

        NotificationManager notifManager = (NotificationManager)
                    context.getSystemService(Context.NOTIFICATION_SERVICE);

        switch (acao){

            case Constantes.BROADCAST_NOTIFICAR:

                Notification notif = intent.getParcelableExtra(NOTIFICATION);
                int id = intent.getIntExtra(NOTIFICATION_ID, 0);
                notifManager.notify(id, notif);

                break;
            case Constantes.BROADCAST_FECHAR:
                notifManager.cancel(NOTIFICATION_ID_VALUE);

                break;
            case Constantes.BROADCAST_INICIAR_APP:
                Intent it=new Intent(context, MainActivity.class);
                it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(it);

                break;
        }

    }
}
