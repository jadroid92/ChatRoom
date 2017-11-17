package in.jadroid.chatroom.screens.signup;

/**
 * Created by JayPatel on 10/9/2017.
 */

public class SignUpPresenterImpl implements SignUpPresenter, SignUpInteractor.OnSignUpFinishedListener {

    private SignUpView signUpView;
    private SignUpInteractor signUpInteractor;

    public SignUpPresenterImpl(SignUpView signUpView) {
        this.signUpView = signUpView;
        signUpInteractor = new SignUpInteractorImpl();
    }

    @Override
    public void validateCredentials(String email, String password, String re_password) {

        signUpInteractor.signUp(email, password, re_password, this);
    }

    @Override
    public void onEmailError(String error) {
        signUpView.showEmailError(error);
    }

    @Override
    public void onPasswordError(String error) {
        signUpView.showPasswordError(error);
    }

    @Override
    public void onRetypePasswordError(String error) {
        signUpView.showReTypePasswordError(error);
    }

    @Override
    public void onMismatchPasswordError(String error) {
        signUpView.showMismatchPasswordError(error);
    }

    @Override
    public void isValidate(boolean isValid) {
        signUpView.isValidate(isValid);
    }
}
