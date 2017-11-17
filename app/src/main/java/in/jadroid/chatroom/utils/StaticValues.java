package in.jadroid.chatroom.utils;

import android.Manifest;

import java.security.Permission;


/**
 * Created by JayPatel on 9/25/2017.
 */

public class StaticValues {

    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 999;
    public static final int RC_SIGN_IN = 9001;
    public static final int REQUEST_ID_ACTIVITES = 1;

    public static final String PASSWORD_PATTERN =
            "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})";

    public static final String[] appPermissions = {Manifest.permission.GET_ACCOUNTS};
}
