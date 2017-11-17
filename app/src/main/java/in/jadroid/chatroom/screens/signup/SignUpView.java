package in.jadroid.chatroom.screens.signup;

/**
 * Created by JayPatel on 10/9/2017.
 */

public interface SignUpView {

    void showEmailError(String error);

    void showPasswordError(String error);

    void showReTypePasswordError(String error);

    void showMismatchPasswordError(String error);

    void isValidate(boolean isValidate);
}
