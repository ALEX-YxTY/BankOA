package com.meishipintu.bankoa.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.meishipintu.bankoa.Constans;
import com.meishipintu.bankoa.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/3/1.
 * <p>
 * 功能介绍：注册申请页/任务全流程一览（大）
 */

public class WebViewActivity extends BasicActivity {

    @BindView(R.id.wb)
    WebView wb;
    @BindView(R.id.pb)
    ProgressBar pb;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_subTitle)
    TextView tvSubTitle;

    private int type;          //Constans.APPLY_TYPE 注册申请， Constants.PROCESS_TYPE 查看全程
    private Intent intent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        ButterKnife.bind(this);
        intent = getIntent();
        type = intent.getIntExtra("type", Constans.PROCESS_TYPE);
        initWebView();
    }

    private void initWebView() {
        wb.setWebViewClient(new WebViewClient());
        wb.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress == 100) {
                    pb.setVisibility(View.GONE);
                } else {
                    pb.setProgress(newProgress);
                }
            }
        });
        wb.getSettings().setJavaScriptEnabled(true);
        wb.getSettings().setDomStorageEnabled(true);
        if (type == Constans.APPLY_TYPE) {
            wb.loadUrl(Constans.APPLY_URL);
            tvTitle.setText(R.string.apply);
            tvSubTitle.setText(R.string.close);
            tvSubTitle.setVisibility(View.VISIBLE);
        } else {
            tvTitle.setText(R.string.whole_process);
            wb.loadUrl(Constans.PROCESS_URL + "?task_id=" + intent.getStringExtra("task_id") + "&uid="
                    + intent.getStringExtra("uid"));
            tvTitle.setText(R.string.whole_process);
        }


    }

    @OnClick({R.id.bt_back, R.id.tv_subTitle})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_back:
                onBackPressed();
                break;
            case R.id.tv_subTitle:
                this.finish();
                break;
        }
    }

    @Override
    protected void onPause() {
        //onPause时重载，可避免webView页消失后声音继续播放
        wb.reload();
        wb.onPause();
        super.onPause();
    }
}
