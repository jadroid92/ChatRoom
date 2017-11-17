package in.jadroid.chatroom.utils;

/**
 * Created by JayPatel on 11/3/2017.
 */


import android.os.Handler;

import java.util.Random;

import in.jadroid.chatroom.progressive_button.ProcessButton;

public class ProgressGenerator {

    public interface OnCompleteListener {
        public void onComplete();
    }

    private OnCompleteListener mListener;
    private int mProgress;

    public ProgressGenerator(OnCompleteListener listener) {
        mListener = listener;
    }

    public void start(final ProcessButton button) {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mProgress += 10;
                button.setProgress(mProgress);
                if (mProgress < 100)
                    handler.postDelayed(this, generateDelay());
                 else
                    mListener.onComplete();

            }
        }, generateDelay());
    }

    private Random random = new Random();

    private int generateDelay() {
        return random.nextInt(1000);
    }
}