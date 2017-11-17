package in.jadroid.chatroom;

import android.app.Application;
import android.content.Context;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import in.jadroid.chatroom.utils.SpHelper;
import in.jadroid.chatroom.utils.ToastUtils;

/**
 * Created by JayPatel on 9/12/2017.
 */

public class MainApplication extends Application {

    public static Context mContext;
    public static SpHelper spHelper;

    public static FirebaseAuth mAuth;
    public static FirebaseDatabase mDatabase;
    public static ToastUtils toastUtils;

    @Override
    public void onCreate() {
        super.onCreate();

        mContext = this;

        //Facebook SDk initialization...
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

        //FireBase SDK initialization...
        FirebaseApp.initializeApp(mContext);

        //Enabling disk persistence stores the data offline even though app restarts.
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        mAuth = FirebaseAuth.getInstance();

        mDatabase = FirebaseDatabase.getInstance();

        spHelper = SpHelper.getInstance(mContext);

        toastUtils = new ToastUtils(getApplicationContext(),true);
    }
}
