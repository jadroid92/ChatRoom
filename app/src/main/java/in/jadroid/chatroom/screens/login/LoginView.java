package in.jadroid.chatroom.screens.login;

/**
 * Created by JayPatel on 10/9/2017.
 */

public interface LoginView {


    void showEmailError(String error);

    void showPasswordError(String error);

    void isValidate(boolean isValidate);

}
