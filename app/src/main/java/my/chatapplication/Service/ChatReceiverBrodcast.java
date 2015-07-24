package my.chatapplication.Service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

import my.chatapplication.R;

/**
 * Created by glenns on 09/08/13.
 */
public class ChatReceiverBrodcast extends BroadcastReceiver {

    @Override
    public void onReceive(Context ctx, Intent intent){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(ctx)
                .setSmallIcon(R.drawable.firebase_logo)
                .setContentText("Power cable has been plugged in")
                .setContentTitle("Attention");
        NotificationManager manager = (NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }
}
