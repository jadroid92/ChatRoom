package in.jadroid.chatroom.permission;

import java.util.ArrayList;

/**
 * Created by JayPatel on 27/12/16.
 */


public interface PermissionResultCallback {
    void PermissionGranted(int request_code);

    void PartialPermissionGranted(int request_code, String[] granted_permissions);

    void PermissionDenied(int request_code);

    void NeverAskAgain(int request_code);
}