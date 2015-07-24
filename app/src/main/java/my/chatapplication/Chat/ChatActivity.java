//package my.chatapplication.Chat;
//
//import android.support.v7.app.ActionBarActivity;
//import android.os.Bundle;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.view.View;
//import android.widget.EditText;
//import android.widget.ImageButton;
//import android.widget.ListView;
//import android.widget.Toast;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import my.chatapplication.DataHolder.ChatItem;
//import my.chatapplication.R;
//
//
//public class ChatActivity extends ActionBarActivity {
//    private ListView list;
//    private List<ChatItem> listArray;
//    private ChatAdapter chatAdapter;
//
//    private EditText editText;
//    private ImageButton send;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_chat);
//        intListAdapter();
//        initFromXmlAndInitActionLister();
//
//    }
//
//
//    private void initFromXmlAndInitActionLister() {
//        editText = (EditText) findViewById(R.id.messageInput);
//
//        send = (ImageButton) findViewById(R.id.sendButton);
//
//        send.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String msg = editText.getText().toString();
//                if (!msg.equals("")) {
//                    addToList(msg);
//                    editText.setText("");
//                    editText.setHint("type your message");
//                }
//            }
//        });
//    }
//
//    public void intListAdapter(){
//        list = (ListView) findViewById(R.id.list);
//        listArray = new ArrayList<ChatItem>();
//        chatAdapter = new ChatAdapter(this  , listArray);
//        addToList("hello sherif");
//        addToList("hello ahmed");
//        try {
//            list.setAdapter(chatAdapter);
//        }catch (Exception e){
//            Toast.makeText(this , "setAdapter exception " , Toast.LENGTH_LONG).show();
//            e.printStackTrace(
//
//            );
//        }
//    }
//
//
//    public void addToList(String msg){
//            listArray.add(new ChatItem(msg , "ahmed" , 0));
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_chat, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
//}
