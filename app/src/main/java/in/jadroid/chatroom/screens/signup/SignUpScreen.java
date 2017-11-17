package in.jadroid.chatroom.screens.signup;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TextInputLayout;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.jadroid.chatroom.R;
import in.jadroid.chatroom.progressive_button.iml.ActionProcessButton;
import in.jadroid.chatroom.screens.CommonActivity;
import in.jadroid.chatroom.utils.AppLabels;
import in.jadroid.chatroom.utils.StaticValues;
import in.jadroid.chatroom.utils.Utils;

import static in.jadroid.chatroom.MainApplication.mAuth;
import static in.jadroid.chatroom.MainApplication.toastUtils;

/**
 * Created by JayPatel on 9/12/2017.
 */

public class SignUpScreen extends CommonActivity implements SignUpView {

    public final static String TAG = SignUpScreen.class.getName();

    private Context mContext;

    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;

    @BindView(R.id.input_layout_email)
    TextInputLayout inputLayoutEmail;
    @BindView(R.id.input_layout_password)
    TextInputLayout inputLayoutPassword;
    @BindView(R.id.input_layout_retypePassword)
    TextInputLayout inputLayoutRetypePassword;

    @BindView(R.id.et_email)
    EditText etEmail;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.et_retypePassword)
    EditText etRetypePassword;

    @BindView(R.id.btn_signUp)
    ActionProcessButton btnSignUp;

    private SignUpPresenter signUpPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_screen);
        mContext = this;
        ButterKnife.bind(this);

        signUpPresenter = new SignUpPresenterImpl(this);

        btnSignUp.setMode(ActionProcessButton.Mode.ENDLESS);

        etRetypePassword.setOnEditorActionListener(
                new EditText.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        // Identifier of the action. This will be either the identifier you supplied,
                        // or EditorInfo.IME_NULL if being called due to the enter key being pressed.
                        if (actionId == EditorInfo.IME_ACTION_DONE) {
                            onSignUpClick(btnSignUp);
                            return true;
                        }
                        // Return true if you have consumed the action, else false.
                        return false;
                    }
                });
    }

    public void onSignUpClick(View view) {

        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();
        String retypePassword = etRetypePassword.getText().toString();

        Utils.hideKeyboard(mContext);
        signUpPresenter.validateCredentials(email, password, retypePassword);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
    }

    public void onBackArrowClick(View view) {
        onBackPressed();
    }


    @Override
    public void showEmailError(String error) {
        if (!error.isEmpty())
            inputLayoutEmail.setError(mContext.getResources().getString(R.string.error_msg_email));
        else
            inputLayoutEmail.setError(null);

    }

    @Override
    public void showPasswordError(String error) {
        if (!error.isEmpty())
            inputLayoutPassword.setError(mContext.getResources().getString(R.string.error_msg_password));
        else
            inputLayoutPassword.setError(null);
    }

    @Override
    public void showReTypePasswordError(String error) {
        if (!error.isEmpty())
            inputLayoutRetypePassword.setError(mContext.getResources().getString(R.string.error_msg_retype_password));
        else
            inputLayoutRetypePassword.setError(null);
    }

    @Override
    public void showMismatchPasswordError(String error) {
        if (!error.isEmpty())
            inputLayoutPassword.setError(mContext.getResources().getString(R.string.error_msg_mismatched_password));
        else
            inputLayoutPassword.setError(null);
    }

    @Override
    public void isValidate(boolean isValidate) {
        if (isValidate) {
            if (Utils.isInternetOn(mContext)) {
                progressGenerator.start(btnSignUp);
                //create user
                mAuth.createUserWithEmailAndPassword(etEmail.getText().toString(), etPassword.getText().toString())
                        .addOnCompleteListener(SignUpScreen.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                if (!task.isSuccessful())
                                    toastUtils.showToast("" + task.getException(), false);
                                else {
                                    Utils.showSnackbar(coordinatorLayout, mContext.getResources().getString(R.string.alert_account_creation));
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            setResult(StaticValues.REQUEST_ID_ACTIVITES, new Intent().putExtra(AppLabels.IS_AUTH_FINISHED, true));
                                            finish();//finish activity
                                        }
                                    }, 500);
                                }
                            }
                        });
            } else
                Utils.showSnackbar(coordinatorLayout, mContext.getResources().getString(R.string.alert_no_internet));
        }
    }
}
