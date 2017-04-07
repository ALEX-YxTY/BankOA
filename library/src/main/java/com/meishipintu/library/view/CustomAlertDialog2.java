package com.meishipintu.library.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.meishipintu.library.R;


/**
 * Created by Administrator on 2016/9/28.
 */

public class CustomAlertDialog2 extends Dialog implements View.OnClickListener{

    OnItemClickListener listener;

    public CustomAlertDialog2(Context context, OnItemClickListener listener) {
        super(context);
        this.listener = listener;
    }

    public CustomAlertDialog2(Context context, int themeResId, OnItemClickListener listener) {
        super(context, themeResId);
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_sure);
        getWindow().setBackgroundDrawableResource(R.color.transparent);
        findViewById(R.id.bt_ok).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if  (v.getId() == R.id.bt_ok) {
            listener.onPositiveClick(this);
        }
    }


    public interface OnItemClickListener {
        void onPositiveClick(Dialog dialog);

        void onNegativeClick(Dialog dialog);
    }

}
