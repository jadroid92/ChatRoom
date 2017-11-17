package in.jadroid.chatroom.screens;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import in.jadroid.chatroom.R;

/**
 * Created by JayPatel on 11/17/2017.
 */

public class HomeScreen extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen);
    }
}
