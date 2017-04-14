package com.meishipintu.bankoa.views.activities;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.meishipintu.bankoa.Constans;
import com.meishipintu.bankoa.OaApplication;
import com.meishipintu.bankoa.R;
import com.meishipintu.bankoa.RxBus;
import com.meishipintu.bankoa.components.DaggerMainComponent;
import com.meishipintu.bankoa.contracts.MainContract;
import com.meishipintu.bankoa.models.PreferenceHelper;
import com.meishipintu.bankoa.models.entity.BusMessage;
import com.meishipintu.bankoa.models.entity.UserInfo;
import com.meishipintu.bankoa.modules.MainModule;
import com.meishipintu.bankoa.presenters.MainPresenterImp;
import com.meishipintu.library.util.DialogUtils;
import com.meishipintu.library.util.ToastUtils;
import com.meishipintu.library.view.CircleImageView;

import org.json.JSONException;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import rx.Subscription;
import rx.functions.Action1;

/**
 * Created 2017-3-1
 * <p>
 * 主界面，功能模块展示
 */

public class MainActivity extends BasicActivity implements MainContract.IView {

    private static final int REQUEST_STORAGE_PERMISSION = 300;
    private static final int REQUEST_PHONE_PERMISSION = 200;

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.civ_head)
    CircleImageView civHead;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_job_title)
    TextView tvJobTitle;
    @BindView(R.id.tv_number)
    TextView tvNumber;
    @BindView(R.id.red_point)
    View redPoint;
    @BindView(R.id.check)
    RelativeLayout rlCheck;

    @Inject
    MainPresenterImp mPresenter;

    private int newestNotice = -1;          //记录最新通知条目
    private int newestRemind = -1;          //记录最新提醒条目
    private long exitTime = 0;              //记录退出时间

    private Subscription subscription;      //RxBus的连接

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        DaggerMainComponent.builder().mainModule(new MainModule(this))
                .build().inject(this);

        checkVersion();
        //申请权限
        storagePermissionWapper();
        phonePermissionWapper();
        //Jpush 注册alias
        JPushInterface.setAlias(this, OaApplication.getUser().getUid(), null);
        //注册RxBus
        subscription = RxBus.getDefault().getObservable(BusMessage.class)
                .subscribe(new Action1<BusMessage>() {
                    @Override
                    public void call(BusMessage busMessage) {
                        if (busMessage.getMessageType() == Constans.LOGOUT) {
                            backToLogin();
                        }
                    }
                });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        if (Constans.LOGOUT == intent.getIntExtra("result", 0)) {
            backToLogin();
            return;
        }
        super.onNewIntent(intent);
    }

    private void backToLogin() {
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
        MainActivity.this.finish();
    }

    //申请读取手机状态权限
    private void phonePermissionWapper() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {        //未授权
            if (!ActivityCompat.shouldShowRequestPermissionRationale(this
                    , android.Manifest.permission.READ_PHONE_STATE)) {    //系统申请权限框不再弹出
                DialogUtils.showCustomDialog(this, "本应用需要获取读写外部存储的权限"
                        , new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions(MainActivity.this, new String[]{android
                                        .Manifest.permission.READ_PHONE_STATE}, REQUEST_PHONE_PERMISSION);
                                dialog.dismiss();
                            }
                        }, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                return;
            }
            //系统框弹出时直接申请
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{android
                    .Manifest.permission.READ_PHONE_STATE},REQUEST_PHONE_PERMISSION);
            return;
        }
    }

    //申请读写存储权限
    private void storagePermissionWapper() {
        int hasStoragePermission = ContextCompat.checkSelfPermission(this
                , android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (hasStoragePermission != PackageManager.PERMISSION_GRANTED) {        //未授权
            if (!ActivityCompat.shouldShowRequestPermissionRationale(this
                    , android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {    //系统申请权限框不再弹出
                DialogUtils.showCustomDialog(this, "本应用需要获取读写外部存储的权限"
                        , new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions(MainActivity.this, new String[]{android
                                        .Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_STORAGE_PERMISSION);
                                dialog.dismiss();
                            }
                        }, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                return;
            }
            //系统框弹出时直接申请
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{android
                    .Manifest.permission.WRITE_EXTERNAL_STORAGE},REQUEST_STORAGE_PERMISSION);
            return;
        }
    }



    //检查系统版本
    private void checkVersion() {
        mPresenter.getVersionInfo();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initUI();
    }

    private void initUI() {
        tvTitle.setText(R.string.homePage);
        mPresenter.getUserInfo();
        //获取最新系统消息
        mPresenter.getNewestNotice();
    }


    @OnClick({R.id.message, R.id.task, R.id.task_trigger, R.id.check, R.id.bt_setting, R.id.bt_search})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.message:
                //进入通知界面即视为消息已阅
                if (newestNotice > 0) {
                    PreferenceHelper.saveNewestNotice(newestNotice);
                }
                if (newestRemind > 0) {
                    PreferenceHelper.saveNewestRemind(newestRemind);
                }
                startActivity(new Intent(MainActivity.this, NoticActivity.class));
                break;
            case R.id.task:
                startActivity(new Intent(MainActivity.this, TaskActivity.class));
                break;
            case R.id.task_trigger:
                startActivity(new Intent(MainActivity.this, TaskTriggerActivity.class));
                break;
            case R.id.check:
                startActivity(new Intent(MainActivity.this, ClerkListActivity.class));
                break;
            case R.id.bt_setting:
                startActivity(new Intent(MainActivity.this, SettingActivity.class));
                break;
            case R.id.bt_search:
                startActivity(new Intent(MainActivity.this, SearchActivity.class));
                break;
        }
    }

    //from MainContract.IView
    @Override
    public void refreshUI(UserInfo userInfo) {
        //获取最新的提醒
        mPresenter.getNewestRemind(userInfo.getUid());
        tvName.setText(userInfo.getUser_name());
        tvNumber.setText("工号：" + userInfo.getJob_number());
        String department,title;
        if (userInfo.getLevel().equals("1")) {
            department = "";
        } else {
            try {
                department = OaApplication.departmentList.getString(userInfo.getDepartment_id());
            } catch (Exception e) {
                e.printStackTrace();
                department = "";
            }
        }
        switch (Integer.parseInt(userInfo.getLevel())) {
            case 1:
                title = "行长";
                break;
            case 2:
                title = "经理";
                break;
            default:
                title = "业务员";
                break;
        }
        tvJobTitle.setText(department + " " + title);
        if (Integer.parseInt(userInfo.getLevel()) < 3) {
            rlCheck.setVisibility(View.VISIBLE);
        }
    }


    //from MainContract.IView
    @Override
    public void dealNewestNotice(int number) {
        if (PreferenceHelper.getNewestNotice() < number) {
            showRedPoint(true);
            newestNotice = number;
        } else {
            showRedPoint(false);
        }
    }

    //from MainContract.IView
    @Override
    public void dealNewestRemind(int number) {
        if (PreferenceHelper.getNewestRemind() < number) {
            showRedPoint(true);
            newestRemind = number;
        } else {
            showRedPoint(false);
        }
    }

    //from BasicView
    @Override
    public void showError(String errMsg) {
        ToastUtils.show(this, errMsg, true);
        startActivity(new Intent(this, LoginActivity.class));
        this.finish();
    }


    private void showRedPoint(boolean b) {
        redPoint.setVisibility(b ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    protected void onDestroy() {
        mPresenter.unSubscrib();
        //解绑RxBus
        subscription.unsubscribe();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        long clickTime = System.currentTimeMillis();
        if ((clickTime - exitTime) > 1000) {
            //两次点击超过0.5秒则不视为退出
            ToastUtils.show(this, R.string.exit, true);
            exitTime = clickTime;
        } else {
            super.onBackPressed();
        }
    }

    //申请权限回调
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions
            , @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PHONE_PERMISSION:
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    //拒绝授权
                    Toast.makeText(this, "没有读取手机状态权限，将有部分功能无法使用，请在系统设置中增加应用的相应授权", Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            case REQUEST_STORAGE_PERMISSION:
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    //拒绝授权
                    Toast.makeText(this, "没有读写内存卡权限，将有部分功能无法使用，请在系统设置中增加应用的相应授权", Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
                break;
        }

    }
}
