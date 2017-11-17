package in.jadroid.chatroom.screens.forgotpassword;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TextInputLayout;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.jadroid.chatroom.R;
import in.jadroid.chatroom.progressive_button.iml.ActionProcessButton;
import in.jadroid.chatroom.screens.CommonActivity;
import in.jadroid.chatroom.utils.AppLabels;
import in.jadroid.chatroom.utils.StaticValues;
import in.jadroid.chatroom.utils.Utils;

import static in.jadroid.chatroom.MainApplication.mAuth;
import static in.jadroid.chatroom.MainApplication.toastUtils;

/**
 * Created by JayPatel on 11/17/2017.
 */

public class ForgotPasswordScreen extends CommonActivity {

    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;

    @BindView(R.id.input_layout_email)
    TextInputLayout inputLayoutEmail;
    @BindView(R.id.et_email)
    EditText etEmail;
    @BindView(R.id.btn_forgotPassword)
    ActionProcessButton btnforgotPassword;

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_password_screen);
        ButterKnife.bind(this);

        mContext = this;
        btnforgotPassword.setMode(ActionProcessButton.Mode.ENDLESS);

        etEmail.setOnEditorActionListener(
                new EditText.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        // Identifier of the action. This will be either the identifier you supplied,
                        // or EditorInfo.IME_NULL if being called due to the enter key being pressed.
                        if (actionId == EditorInfo.IME_ACTION_DONE) {
                            onForgotPasswordClick(btnforgotPassword);
                            return true;
                        }
                        // Return true if you have consumed the action, else false.
                        return false;
                    }
                });
    }


    public boolean isValidate() {
        boolean isValid = true;

        String email = etEmail.getText().toString();

        Utils.hideKeyboard(mContext);

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            inputLayoutEmail.setError(mContext.getResources().getString(R.string.error_msg_email));
            return false;
        } else
            inputLayoutEmail.setError(null);

        return isValid;
    }

    @OnClick(R.id.btn_forgotPassword)
    public void onForgotPasswordClick(View view) {
        if(isValidate()){
            if (Utils.isInternetOn(mContext)) {
                progressGenerator.start(btnforgotPassword);
                //send request for forgot password...
                mAuth.sendPasswordResetEmail(etEmail.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                         if(task.isSuccessful()) {
                             toastUtils.showToast(mContext.getResources().getString(R.string.alert_forgot_password), false);
                             new Handler().postDelayed(new Runnable() {
                                 @Override
                                 public void run() {
                                     setResult(StaticValues.REQUEST_ID_ACTIVITES, new Intent().putExtra(AppLabels.IS_AUTH_FINISHED, true));
                                     finish();//finish activity
                                 }
                             }, 500);
                         }else toastUtils.showToast("" + task.getException(), false);
                    }
                });
            } else
                Utils.showSnackbar(coordinatorLayout, mContext.getResources().getString(R.string.alert_no_internet));
        }

    }
}
