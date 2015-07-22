package my.chatapplication.View;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import my.chatapplication.Model.UMSModule;
import my.chatapplication.R;

public class UserProfile extends ActionBarActivity {
    private UMSModule umsModule;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

//        context = this;
//        if(messageHandler != null)
//            umsModule = new UMSModule(this , messageHandler);
//        else
//            showToastMessage("mesage hundler is null");
//
//        try {
//            umsModule.getUser("ahmed@ahmed.com");
//        }catch (Exception e) {
//            showToastMessage(e.toString());
//        }
    }

    private void showToastMessage(String s) {
        Toast.makeText(this , s , Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private Handler messageHandler = new Handler() {

        @Override
        public void handleMessage(Message msg){
            if(msg.arg1 == 1){
                showToastMessage(msg.obj.toString());
            }else if(msg.arg1 == -1){
                showToastMessage("Server unavailable");
            }
        }
    };
}
