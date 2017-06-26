package com.example.joaos.virtualhelper.util;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.SystemClock;

import com.example.joaos.virtualhelper.R;

/**
 * Created by joaos on 26/06/2017.
 */

public class GerenciadorDeNotificacoes {

    private Context context;
    private Intent notificationIntent;
    private String nomeContainer;
    private int idContainer;

    public GerenciadorDeNotificacoes(Context context, Intent notificationIntent, String nomeContainer){
        this.context=context;
        this.notificationIntent=notificationIntent;
        this.nomeContainer=nomeContainer;
    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private Notification getNotification(String conteudo){
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        Notification.Builder notificacao = new Notification.Builder(context);
        notificacao.setContentTitle(nomeContainer);
        notificacao.setContentText(conteudo);
        notificacao.setSmallIcon(R.mipmap.icone_logo_nova);
        notificacao.setVibrate(new long[]{1000});
        notificacao.setSound(alarmSound);

        //Adiconar a opção Fechar
        notificationIntent.putExtra("action", Constantes.BROADCAST_FECHAR);

        PendingIntent pendent1 = PendingIntent.getBroadcast(context,
                Constantes.BROADCAST_FECHAR,
                notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        notificacao.addAction(android.R.drawable.ic_menu_add,
                "Ok", pendent1);

        //Adicionar opção abrir app
        notificationIntent.putExtra("action", Constantes.BROADCAST_INICIAR_APP);

        PendingIntent pendent2 = PendingIntent.getBroadcast(context,
                Constantes.BROADCAST_INICIAR_APP,
                notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        notificacao.addAction(android.R.drawable.ic_menu_info_details,
                "Abrir aplicativo", pendent2);

        notificacao.setAutoCancel(true);
        return notificacao.build();
    }

    private void scheduleNotification(Notification notification, long delay, long intervalo){

        notificationIntent.putExtra(AlarmReceiver.NOTIFICATION_ID, idContainer);
        notificationIntent.putExtra(AlarmReceiver.NOTIFICATION, notification);
        notificationIntent.putExtra("action", Constantes.BROADCAST_NOTIFICAR);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
                Constantes.BROADCAST_NOTIFICAR,
                notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        long futureMillis = SystemClock.elapsedRealtime()+delay;
        AlarmManager alarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarm.setRepeating(AlarmManager.ELAPSED_REALTIME, futureMillis, intervalo, pendingIntent);
    }

    public void criarNotification(int idContainer){

        this.idContainer=idContainer;
        String texto = context.getString(R.string.notificacao_manutencao);

        //2592000000 milisegundos - 30dias
        notificationIntent = new Intent(context, AlarmReceiver.class);
        scheduleNotification(getNotification(texto), 2592000000L, 2592000000L);
    }

    //id do notification é igual ao o id do container
    public static void cancelNotification(int idNotification, Context context){
        NotificationManager notifManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);

        notifManager.cancel(idNotification);
    }

}
