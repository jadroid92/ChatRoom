package in.jadroid.chatroom.screens;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import in.jadroid.chatroom.utils.ProgressGenerator;

/**
 * Created by JayPatel on 11/17/2017.
 */

public class CommonActivity extends AppCompatActivity implements ProgressGenerator.OnCompleteListener {
    public ProgressGenerator progressGenerator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        progressGenerator = new ProgressGenerator(CommonActivity.this);
    }

    @Override
    public void onComplete() {
    }
}
