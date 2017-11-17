package in.jadroid.chatroom.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by JayPatel on 10/9/2017.
 */

public class PasswordValidator {
    private Pattern pattern;
    private Matcher matcher;


    public PasswordValidator() {
        pattern = Pattern.compile(StaticValues.PASSWORD_PATTERN);
    }

    /**
     * Validate password with regular expression
     *
     * @param password password for validation
     * @return true valid password, false invalid password
     */
    public boolean validate(final String password) {

        matcher = pattern.matcher(password);
        return matcher.matches();

    }
}
