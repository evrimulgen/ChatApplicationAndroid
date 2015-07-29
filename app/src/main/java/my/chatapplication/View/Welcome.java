package my.chatapplication.View;

import android.content.Context;
import android.content.Intent;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import my.chatapplication.Controller.UserController;
import my.chatapplication.DataHolder.CLASSES;
import my.chatapplication.DataHolder.Chat;
import my.chatapplication.DataHolder.User;
import my.chatapplication.R;

public class Welcome extends ActionBarActivity  implements ChatView{
    private UserController userController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        final Context context = this;
        ((Button) findViewById(R.id.welcome_login)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Login.class);
                startActivity(intent);
            }
        });

        ((Button)findViewById(R.id.welcome_register)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context , SignUpEmailAndPassowrd.class);
                startActivity(intent);
            }
        });

        userController = new UserController(this , CLASSES.WELCOME , this);
        User user = userController.getUserFromSQL();
        if(user != null && !user.getEmail().equals("") && !user.getPassword().equals("")){
            Intent intent = new Intent(this, Home.class);
            intent.putExtra("user" , user);
            startActivity(intent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_welcome, menu);
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

    @Override
    public void handleMessage(Message msg) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        System.exit(0);
    }
}
