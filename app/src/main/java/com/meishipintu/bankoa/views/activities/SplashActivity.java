package com.meishipintu.bankoa.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;

import com.meishipintu.bankoa.OaApplication;
import com.meishipintu.bankoa.R;
import com.meishipintu.bankoa.models.PreferenceHelper;
import com.meishipintu.bankoa.models.http.HttpApi;

import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.List;

import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class SplashActivity extends AppCompatActivity {

    private HttpApi httpApi;
    private CompositeSubscription subscriptions;
    private MyHandler myHandler = new MyHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        httpApi = HttpApi.getInstance();
        subscriptions = new CompositeSubscription();
        myHandler.sendEmptyMessageDelayed(0, 3500);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myHandler.removeMessages(0);
        subscriptions.clear();
    }

    //handler设为内部静态类，防止handler持有activity对象导致内存泄漏
    private static class MyHandler extends Handler {
        private final WeakReference<SplashActivity> reference;

        MyHandler(SplashActivity activity){
            reference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                SplashActivity activity = reference.get();
                if (PreferenceHelper.isAutoLogin() && PreferenceHelper.getUserInfo()!= null) {
                    activity.startActivity(new Intent(activity, MainActivity.class));
                } else {
                    activity.startActivity(new Intent(activity, LoginActivity.class));
                }
                activity.finish();
            }
        }
    }
}
