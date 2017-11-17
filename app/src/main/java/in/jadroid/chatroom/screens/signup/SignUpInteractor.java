package in.jadroid.chatroom.screens.signup;

/**
 * Created by JayPatel on 10/9/2017.
 */

public interface SignUpInteractor {

    interface OnSignUpFinishedListener {

        void onEmailError(String error);

        void onPasswordError(String error);

        void onRetypePasswordError(String error);

        void onMismatchPasswordError(String error);

        void isValidate(boolean isValid);
    }

    void signUp(String email, String password, String re_password, OnSignUpFinishedListener onSignUpFinishedListner);
}
