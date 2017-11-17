package in.jadroid.chatroom.screens.login;

/**
 * Created by JayPatel on 10/9/2017.
 */

public interface LoginInteractor {

    interface OnLoginFinishedListner {

        void onEmailError(String error);

        void onPasswordError(String error);

        void isValidate(boolean valid);
    }

    void onLogin(String email,String password,OnLoginFinishedListner onLoginFinishedListner);
}
