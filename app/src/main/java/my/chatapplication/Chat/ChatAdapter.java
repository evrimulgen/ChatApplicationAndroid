//package my.chatapplication.Chat;
//
//import android.app.Activity;
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import java.util.List;
//
//import my.chatapplication.DataHolder.ChatItem;
//import my.chatapplication.R;
//
///**
// * Created by nasser on 16/07/15.
// */
//public class ChatAdapter extends BaseAdapter{
//    private Activity activity;
//    private LayoutInflater inflater;
//    private List<ChatItem> chats;
//
//    public ChatAdapter(Activity activity , List<ChatItem> chats){
//        this.activity = activity;
//        this.chats = chats;
//    }
//
//    @Override
//    public int getCount() {
//        return chats.size();
//    }
//
//    @Override
//    public Object getItem(int i) {
//        return chats.get(i);
//    }
//
//    @Override
//    public long getItemId(int i) {
//        return i;
//    }
//
//    @Override
//    public View getView(int i, View view, ViewGroup viewGroup) {
//        ChatItem chat = chats.get(i);
//
//        if(inflater == null)
//            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//
//        if(view == null)
//            view = inflater.inflate(R.layout.chat_item, null);
//
//        ((TextView)view.findViewById(R.id.msg_text)).setText(chat.getMsg());
//        ((ImageView)view.findViewById(R.id.msg_image)).setImageResource(R.drawable.ic_launcher);
//        ((TextView)view.findViewById(R.id.msg_author)).setText(chat.getAuthor());
//
//        return view;
//    }
//}
