package my.chatapplication.Navigation;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import it.neokree.materialnavigationdrawer.MaterialNavigationDrawer;
import my.chatapplication.Chat.ChatActivity;
import my.chatapplication.View.Login;
import my.chatapplication.View.SignUpEmailAndPassowrd;
import my.chatapplication.View.UserProfile;
import my.chatapplication.R;


/**
 * Created by nasser on 16/07/15.
 */
public class NavigationBar extends MaterialNavigationDrawer {
        @Override
        public void init(Bundle savedInstanceState) {

            // create and set the header
            View view = LayoutInflater.from(this).inflate(R.layout.custom_drawer,null);
            setDrawerHeaderCustom(view);

            this.addSection(newSection("Home", new FragmentIndex()));
            this.addSection(newSection("Chat", new Intent(this, ChatActivity.class)));
            this.addSection(newSection("Login", new Intent(this, Login.class)));
            this.addSection(newSection("SignUp", new Intent(this, SignUpEmailAndPassowrd.class)));
            Intent intent = new Intent(this, UserProfile.class);
            intent.putExtra("email" , "ahmed@ahmed.com");
            this.addSection(newSection("Profile", intent));

            //this.addBottomSection(newSection("Bottom Section",R.drawable.ic_settings_black_24dp,new Intent(this,Settings.class)));

        }

}
