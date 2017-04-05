package com.meishipintu.bankoa.views.activities;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.meishipintu.bankoa.Constans;
import com.meishipintu.bankoa.R;
import com.meishipintu.bankoa.RxBus;
import com.meishipintu.bankoa.components.DaggerSettingComponent;
import com.meishipintu.bankoa.contracts.SettingContract;
import com.meishipintu.bankoa.models.entity.BusMessage;
import com.meishipintu.bankoa.models.entity.UserInfo;
import com.meishipintu.bankoa.modules.SettingModule;
import com.meishipintu.bankoa.presenters.SettingPresenterImp;
import com.meishipintu.library.util.ToastUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;

/**
 * Created by Administrator on 2017/3/2.
 * <p>
 * 主要功能：设置页面
 */

public class SettingActivity extends BasicActivity implements SettingContract.IView{

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.version_code)
    TextView versionCode;

    @Inject
    SettingPresenterImp mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        tvTitle.setText(R.string.setting);
        try {
            versionCode.setText(getPackageManager().getPackageInfo(this.getPackageName(),0).versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        DaggerSettingComponent.builder().settingModule(new SettingModule(this))
                .build().inject(this);
    }

    @OnClick({R.id.bt_back, R.id.rl_userInfo, R.id.rl_clear_cache, R.id.bt_logout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_back:
                onBackPressed();
                break;
            case R.id.rl_userInfo:
                startActivity(new Intent(SettingActivity.this, UserInfoActivity.class));
                break;
            case R.id.rl_clear_cache:
                mPresenter.clearCache();
                break;
            case R.id.bt_logout:
                mPresenter.logout();
                break;
        }
    }

    //from SettingContract.IView
    @Override
    public void onLogoutSuccess() {
        // 取消JPush的别名注册
        JPushInterface.setAlias(this, "", null);
        RxBus.getDefault().send(new BusMessage(Constans.LOGOUT, null));
        this.finish();
    }

    //from BasicView
    @Override
    public void showError(String errMsg) {
        ToastUtils.show(this, errMsg, true);
    }
}
