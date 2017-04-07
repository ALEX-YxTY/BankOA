package com.meishipintu.library.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.meishipintu.library.R;

import org.w3c.dom.Text;


/**
 * Created by Administrator on 2016/9/28.
 */

public class CustomAlertDialog extends Dialog implements View.OnClickListener{

    OnItemClickListener listener;

    public CustomAlertDialog(Context context, OnItemClickListener listener) {
        super(context);
        this.listener = listener;
    }

    public CustomAlertDialog(Context context, int themeResId, OnItemClickListener listener) {
        super(context, themeResId);
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_task_del);
        getWindow().setBackgroundDrawableResource(R.color.transparent);
        findViewById(R.id.bt_cancel).setOnClickListener(this);
        findViewById(R.id.bt_ok).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.bt_cancel) {
            listener.onNegativeClick(this);
        } else if (v.getId() == R.id.bt_ok) {
            listener.onPositiveClick(this);
        }
    }


    public interface OnItemClickListener {
        void onPositiveClick(Dialog dialog);

        void onNegativeClick(Dialog dialog);
    }

}
