package in.jadroid.chatroom.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by JayPatel on 11/17/2017.
 */

public class ToastUtils {

    private Context context;
    private boolean isEnable;
    Toast toast;

    public ToastUtils(Context context, boolean isEnable) {
        this.context = context;
        this.isEnable = isEnable;
    }

    public void showToast(String msg,boolean isLongToast){
        if(!isEnable)
            return;

        if(isLongToast)
            toast = Toast.makeText(context,msg,Toast.LENGTH_LONG);
        else
            toast = Toast.makeText(context,msg,Toast.LENGTH_SHORT);

        if(toast != null)
            toast.show();
    }
}
