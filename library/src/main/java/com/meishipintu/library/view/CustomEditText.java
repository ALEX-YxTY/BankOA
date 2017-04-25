package com.meishipintu.library.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.meishipintu.library.R;

/**
 * Created by Administrator on 2017/4/25.
 * <p>
 * 主要功能：自定义view 包裹
 */

public class CustomEditText extends LinearLayout{

    private TextView tvTitle;
    private EditText etInput;


    public CustomEditText(Context context) {
        super(context);
        init(context);
    }

    public CustomEditText(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.CustomEditText);
        if (attributes != null) {
            String title = attributes.getString(R.styleable.CustomEditText_title);
            if (title != null) {
                tvTitle.setText(title);
            }
            String hint = attributes.getString(R.styleable.CustomEditText_hint);
            if (hint != null) {
                etInput.setHint(hint);
            }
            //最后记得要回收
            attributes.recycle();
        }
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.custom_edit_text, this, true);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        etInput = (EditText) findViewById(R.id.et_input);
        etInput.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    setBackgroundResource(R.drawable.shape_bt_border_blue_3dp);
                } else {
                    setBackgroundResource(R.drawable.shape_bt_border_blue_3dp_grey);
                }
            }
        });
    }

    public void setTvTitle(String title) {
        tvTitle.setText(title);
    }

    public void setHint(String hint) {
        etInput.setHint(hint);
    }

    public String getContent() {
        return etInput.getText().toString();
    }

    public void setContent(String string) {
        etInput.setText(string);
    }
}
