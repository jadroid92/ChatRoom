package in.jadroid.chatroom.screens;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.jadroid.chatroom.R;
import in.jadroid.chatroom.permission.PermissionResultCallback;
import in.jadroid.chatroom.permission.PermissionUtils;
import in.jadroid.chatroom.screens.forgotpassword.ForgotPasswordScreen;
import in.jadroid.chatroom.screens.login.LoginScreen;
import in.jadroid.chatroom.screens.signup.SignUpScreen;
import in.jadroid.chatroom.utils.AppLabels;
import in.jadroid.chatroom.utils.StaticValues;
import in.jadroid.chatroom.utils.Utils;

import static in.jadroid.chatroom.MainApplication.mAuth;
import static in.jadroid.chatroom.MainApplication.mContext;
import static in.jadroid.chatroom.MainApplication.spHelper;
import static in.jadroid.chatroom.MainApplication.toastUtils;

/**
 * Created by JayPatel on 9/12/2017.
 */

public class SplashScreen extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback, PermissionResultCallback {


    @BindView(R.id.contentLayout)
    RelativeLayout contentLayout;
    @BindView(R.id.txt_forgotPassword)
    TextView txtforgotPassword;
    @BindView(R.id.btn_fbLogin)
    LoginButton loginFBLogin;
    @BindView(R.id.btn_gSignIn)
    SignInButton signInButton;
    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;

    private GoogleSignInClient mGoogleSignInClient;

    private CallbackManager mCallbackManager;
    private PermissionUtils permissionUtils;

    private static String TAG = SplashScreen.class.getName();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        ButterKnife.bind(this);

        permissionUtils = new PermissionUtils(this);

        permissionUtils.check_permission(StaticValues.appPermissions, StaticValues.REQUEST_ID_MULTIPLE_PERMISSIONS);

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mCallbackManager = CallbackManager.Factory.create();
        loginFBLogin.setReadPermissions("email", "public_profile");


        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


    }

    public void signUpClick(View view) {
        startActivityForResult(new Intent(SplashScreen.this, SignUpScreen.class), StaticValues.REQUEST_ID_ACTIVITES);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);

    }

    public void loginClick(View view) {
        startActivityForResult(new Intent(SplashScreen.this, LoginScreen.class), StaticValues.REQUEST_ID_ACTIVITES);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
    }

    @OnClick(R.id.txt_forgotPassword)
    public void forgotPasswordClick(View view) {
        startActivityForResult(new Intent(SplashScreen.this, ForgotPasswordScreen.class), StaticValues.REQUEST_ID_ACTIVITES);
    }

    @OnClick(R.id.btn_gSignIn)
    public void gLoginClick(View view) {
        if (Utils.isInternetOn(mContext)) {
            Intent signInIntent = mGoogleSignInClient.getSignInIntent();
            startActivityForResult(signInIntent, StaticValues.RC_SIGN_IN);
        } else
            Utils.showSnackbar(coordinatorLayout, mContext.getResources().getString(R.string.alert_no_internet));
    }

    @OnClick(R.id.btn_fbLogin)
    public void fbLoginClick(View view) {
        if (Utils.isInternetOn(mContext)) {
            loginFBLogin.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(LoginResult loginResult) {
                    handleFacebookAccessToken(loginResult.getAccessToken());
                }

                @Override
                public void onCancel() {
                }

                @Override
                public void onError(FacebookException error) {
                }
            });
        } else
            Utils.showSnackbar(coordinatorLayout, mContext.getResources().getString(R.string.alert_no_internet));

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Pass the activity result back to the Facebook SDK
        mCallbackManager.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == StaticValues.RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                fireBaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
            }
        } else if (resultCode == StaticValues.REQUEST_ID_ACTIVITES) {
            if (data.hasExtra(AppLabels.IS_AUTH_FINISHED))
                if (!data.getBooleanExtra(AppLabels.IS_AUTH_FINISHED, false) && spHelper.readBooleanValue(AppLabels.HAS_USER, false))
                    //go to HomeScreen
                    startActivity(new Intent(SplashScreen.this, HomeScreen.class));
            finish();
        }
    }


    private void handleFacebookAccessToken(AccessToken token) {
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            if (user != null)
                                Utils.showSnackbar(coordinatorLayout, "Welcome " + mAuth.getCurrentUser().getDisplayName());

                            spHelper.saveBooleanValue(AppLabels.HAS_USER, true);

                            //go to HomeScreen
                            startActivity(new Intent(SplashScreen.this, HomeScreen.class));
                            finish();
                        } else
                            // If sign in fails, display a message to the user.
                            toastUtils.showToast("" + task.getException(), false);
                    }
                });
    }

    private void fireBaseAuthWithGoogle(GoogleSignInAccount acct) {
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            if (user != null)
                                Utils.showSnackbar(coordinatorLayout, "Welcome " + mAuth.getCurrentUser().getDisplayName());

                            spHelper.saveBooleanValue(AppLabels.HAS_USER, true);

                            startActivity(new Intent(SplashScreen.this, HomeScreen.class));
                            finish();
                        } else
                            // If sign in fails, display a message to the user.
                            toastUtils.showToast("" + task.getException(), false);
                    }
                });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        // redirects to utils
        permissionUtils.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void PermissionGranted(int request_code) {
        if (spHelper.readBooleanValue(AppLabels.HAS_USER, false)) {
            //go to HomeScreen
            startActivity(new Intent(SplashScreen.this, HomeScreen.class));
            finish();
        } else {
            contentLayout.setVisibility(View.VISIBLE);
            //Load animation
            Animation slide_up = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up);
            // Start animation
            contentLayout.startAnimation(slide_up);
        }
    }

    @Override
    public void PartialPermissionGranted(int requestCode, String[] granted_permissions) {
        // redirects to utils
    }

    @Override
    public void PermissionDenied(int request_code) {
    }

    @Override
    public void NeverAskAgain(int request_code) {
        new AlertDialog.Builder(SplashScreen.this)
                .setMessage("All Permissions is required to run this application.")
                .setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                })
                .create()
                .show();
    }
}
