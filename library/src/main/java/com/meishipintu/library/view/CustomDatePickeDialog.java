package com.meishipintu.library.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.DatePicker;

import com.meishipintu.library.R;

import java.util.Calendar;

/**
 * Created by Administrator on 2017/3/21.
 * <p>
 * 主要功能：
 */

public class CustomDatePickeDialog extends AlertDialog {

    private onDateSelectListener listener;

    public CustomDatePickeDialog(Context context, @StyleRes int themeResId, onDateSelectListener mListener) {
        super(context, themeResId);
        this.listener = mListener;
    }

    public CustomDatePickeDialog(@NonNull Context context) {
        super(context);
    }

    public CustomDatePickeDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_date_picker);
        DatePicker dp = (DatePicker) findViewById(R.id.dp);
        findViewById(R.id.ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomDatePickeDialog.this.dismiss();
            }
        });
        final Calendar calendar = Calendar.getInstance();
        dp.setMinDate(calendar.getTimeInMillis() - 10000);
        dp.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)
                , new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                if (listener != null) {
                    listener.onDateSelect(calendar);
                }
            }
        });
    }

    public interface onDateSelectListener {

        void onDateSelect(Calendar calendar);
    }
}
