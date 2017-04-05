package com.meishipintu.library.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by Administrator on 2016/9/20.
 */
public class DialogUtils {

    public static void showCustomDialog(Context context, String message
            , DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(context)
                .setMessage(message)
                .setPositiveButton("确定",okListener)
                .setNegativeButton("取消",null)
                .create()
                .show();
    }

    public static void showCustomDialog(Context context, String message
            , DialogInterface.OnClickListener okListener, DialogInterface.OnClickListener cancelListener) {
        new AlertDialog.Builder(context)
                .setMessage(message)
                .setPositiveButton("确定",okListener)
                .setNegativeButton("取消",cancelListener)
                .create()
                .show();
    }
}
