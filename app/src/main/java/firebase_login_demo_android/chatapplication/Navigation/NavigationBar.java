package firebase_login_demo_android.chatapplication.Navigation;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.Contacts;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import firebase_login_demo_android.chatapplication.ChatActivity;
import firebase_login_demo_android.chatapplication.R;
import it.neokree.materialnavigationdrawer.MaterialNavigationDrawer;

/**
 * Created by nasser on 16/07/15.
 */
public class NavigationBar extends MaterialNavigationDrawer {
        @Override
        public void init(Bundle savedInstanceState) {

            // create and set the header
            View view = LayoutInflater.from(this).inflate(R.layout.custom_drawer,null);
            setDrawerHeaderCustom(view);

            this.addSection(newSection("listView", new FragmentIndex()));
            this.addSection(newSection("Chat" , new ChatActivity()));
            // create sections
//            this.addSection(newSection("Section 1", new FragmentIndex()));
//            this.addSection(newSection("Section 2",new FragmentIndex()));
//            this.addSection(newSection("Section 3", R.drawable.ic_mic_white_24dp,new FragmentIndex()).setSectionColor(Color.parseColor("#9c27b0")));
//            this.addSection(newSection("Section",R.drawable.ic_hotel_grey600_24dp,new FragmentIndex()).setSectionColor(Color.parseColor("#03a9f4")));

            // create bottom section

        }

}
