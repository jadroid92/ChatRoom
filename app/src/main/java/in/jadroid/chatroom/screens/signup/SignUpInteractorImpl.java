package in.jadroid.chatroom.screens.signup;

import in.jadroid.chatroom.MainApplication;
import in.jadroid.chatroom.R;
import in.jadroid.chatroom.utils.PasswordValidator;

/**
 * Created by JayPatel on 10/9/2017.
 */

public class SignUpInteractorImpl implements SignUpInteractor {

    private PasswordValidator passwordValidator;

    @Override
    public void signUp(String email, String password, String re_password, OnSignUpFinishedListener onSignUpFinishedListner) {

        passwordValidator = new PasswordValidator();

        boolean isValid = true;

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            isValid = false;
            onSignUpFinishedListner.onEmailError(MainApplication.mContext.getResources().getString(R.string.error_msg_email));
        } else
            onSignUpFinishedListner.onEmailError("");


        if (password.isEmpty() || !passwordValidator.validate(password)) {
            isValid = false;
            onSignUpFinishedListner.onPasswordError(MainApplication.mContext.getResources().getString(R.string.error_msg_password));
        } else
            onSignUpFinishedListner.onPasswordError("");


        if (re_password.isEmpty() || !passwordValidator.validate(re_password)) {
            isValid = false;
            onSignUpFinishedListner.onRetypePasswordError(MainApplication.mContext.getResources().getString(R.string.error_msg_retype_password));
        } else
            onSignUpFinishedListner.onRetypePasswordError("");

        if (!password.isEmpty() && !re_password.isEmpty()) {
            if (!password.equals(re_password)) {
                isValid = false;
                onSignUpFinishedListner.onMismatchPasswordError(MainApplication.mContext.getResources().getString(R.string.error_msg_mismatched_password));
            } else
                onSignUpFinishedListner.onMismatchPasswordError("");
        }


        onSignUpFinishedListner.isValidate(isValid);
    }
}
