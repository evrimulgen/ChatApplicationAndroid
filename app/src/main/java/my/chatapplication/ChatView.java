package my.chatapplication;

import android.os.Handler;
import android.os.Message;

/**
 * Created by nasser on 22/07/15.
 */
public interface ChatView {
    public void handleMessage(Message msg);
}
