package com.meishipintu.library.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Administrator on 2017/3/15.
 * <p>
 * 主要功能：
 */

public class ToastUtils {

    public static void show(Context context, String content, boolean isShort) {
        Toast.makeText(context, content, isShort ? Toast.LENGTH_SHORT : Toast.LENGTH_LONG).show();
    }

    public static void show(Context context, int stringResId, boolean isShort) {
        Toast.makeText(context, context.getResources().getString(stringResId)
                , isShort ? Toast.LENGTH_SHORT : Toast.LENGTH_LONG).show();
    }
}
