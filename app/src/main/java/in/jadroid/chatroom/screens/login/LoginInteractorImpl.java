package in.jadroid.chatroom.screens.login;

import in.jadroid.chatroom.MainApplication;
import in.jadroid.chatroom.R;
import in.jadroid.chatroom.utils.PasswordValidator;

/**
 * Created by JayPatel on 10/9/2017.
 */

public class LoginInteractorImpl implements LoginInteractor {

    private PasswordValidator passwordValidator;

    @Override
    public void onLogin(final String email, final String password, final OnLoginFinishedListner onLoginFinishedListner) {
        boolean isValid = true;
        passwordValidator = new PasswordValidator();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            onLoginFinishedListner.onEmailError(MainApplication.mContext.getResources().getString(R.string.error_msg_email));
            isValid = false;
        }else
            onLoginFinishedListner.onEmailError("");

        if (password.isEmpty() || !passwordValidator.validate(password)) {
            onLoginFinishedListner.onPasswordError(MainApplication.mContext.getResources().getString(R.string.error_msg_password));
            isValid = false;
        }else
            onLoginFinishedListner.onPasswordError("");

        onLoginFinishedListner.isValidate(isValid);

    }
}
