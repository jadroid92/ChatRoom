package in.jadroid.chatroom.permission;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import in.jadroid.chatroom.utils.StaticValues;

/**
 * Created by JayPatel on 27/12/16.
 */


public class PermissionUtils {

    Context context;
    Activity current_activity;

    PermissionResultCallback permissionResultCallback;

    String[] permission_list;
    ArrayList<String> listPermissionsNeeded = new ArrayList<>();
    String dialog_content = "";
    int req_code;

    public PermissionUtils(Context context) {
        this.context = context;
        this.current_activity = (Activity) context;

        permissionResultCallback = (PermissionResultCallback) context;
    }


    /**
     * Check the API Level & Permission
     *
     * @param permissions
     * @param request_code
     */

    public void check_permission(String[] permissions, int request_code) {
        this.permission_list = permissions;
        this.req_code = request_code;

        if (Build.VERSION.SDK_INT >= 23) {
            if (checkAndRequestPermissions(permissions, request_code))
                permissionResultCallback.PermissionGranted(request_code);
        } else
            permissionResultCallback.PermissionGranted(request_code);

    }


    /**
     * Check and request the Permissions
     *
     * @param permissions
     * @param request_code
     * @return
     */

    private boolean checkAndRequestPermissions(String[] permissions, int request_code) {

        if (permissions.length > 0) {
            listPermissionsNeeded = new ArrayList<>();

            for (int i = 0; i < permissions.length; i++) {
                int hasPermission = ContextCompat.checkSelfPermission(current_activity, permissions[i]);

                if (hasPermission != PackageManager.PERMISSION_GRANTED)
                    listPermissionsNeeded.add(permissions[i]);
            }

            if (!listPermissionsNeeded.isEmpty()) {
                ActivityCompat.requestPermissions(current_activity, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), request_code);
                return false;
            }
        }
        return true;
    }

    /**
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case StaticValues.REQUEST_ID_MULTIPLE_PERMISSIONS:
                if (grantResults.length > 0) {
                    Map<String, Integer> perms = new HashMap<>();

                    for (int i = 0; i < permissions.length; i++) {
                        perms.put(permissions[i], grantResults[i]);
                    }

                    final ArrayList<String> pending_permissions = new ArrayList<>();

                    for (int i = 0; i < listPermissionsNeeded.size(); i++) {
                        if (perms.get(listPermissionsNeeded.get(i)) != PackageManager.PERMISSION_GRANTED) {
                            if (ActivityCompat.shouldShowRequestPermissionRationale(current_activity, listPermissionsNeeded.get(i)))
                                pending_permissions.add(listPermissionsNeeded.get(i));
                            else {
                                permissionResultCallback.NeverAskAgain(req_code);
                                return;
                            }
                        }
                    }

                    if (pending_permissions.size() > 0)
                        check_permission(permission_list, req_code);
                    else
                        permissionResultCallback.PermissionGranted(req_code);
                }
                break;
        }
    }
}