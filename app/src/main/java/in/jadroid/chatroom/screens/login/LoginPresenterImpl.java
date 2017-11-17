package in.jadroid.chatroom.screens.login;

/**
 * Created by JayPatel on 10/9/2017.
 */

public class LoginPresenterImpl implements LoginPresenter, LoginInteractor.OnLoginFinishedListner {

    private LoginView loginView;
    private LoginInteractor loginInteractor;

    public LoginPresenterImpl(LoginView loginView) {
        this.loginView = loginView;
        this.loginInteractor = new LoginInteractorImpl();
    }

    @Override
    public void validateCredentials(String email, String password) {
        loginInteractor.onLogin(email, password, this);
    }

    @Override
    public void onEmailError(String error) {
        loginView.showEmailError(error);
    }

    @Override
    public void onPasswordError(String error) {
        loginView.showPasswordError(error);
    }

    @Override
    public void isValidate(boolean valid) {
        loginView.isValidate(valid);
    }

}
