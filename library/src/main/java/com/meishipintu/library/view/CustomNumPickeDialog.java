package com.meishipintu.library.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.DatePicker;
import android.widget.NumberPicker;

import com.meishipintu.library.R;

import java.util.Calendar;

/**
 * Created by Administrator on 2017/3/21.
 * <p>
 * 主要功能：
 */

public class CustomNumPickeDialog extends AlertDialog {

    private String[] show;
    private OnOkClickListener okListener;

    public CustomNumPickeDialog(Context context, @StyleRes int themeResId
            , String[] show,OnOkClickListener listener) {
        super(context, themeResId);
        this.show = show;
        this.okListener = listener;
    }

    public CustomNumPickeDialog(@NonNull Context context) {
        super(context);
    }

    public CustomNumPickeDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_num_picker);
        final NumberPicker np = (NumberPicker) findViewById(R.id.np);
        findViewById(R.id.ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                okListener.onOkClick(np.getValue());
            }
        });
        np.setDisplayedValues(show);
        np.setMinValue(0);
        np.setMaxValue(show.length-1);
    }

    public interface OnOkClickListener {

        void onOkClick(int vlueChoose);
    }
}
