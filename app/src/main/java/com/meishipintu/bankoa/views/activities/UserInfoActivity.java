package com.meishipintu.bankoa.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.meishipintu.bankoa.OaApplication;
import com.meishipintu.bankoa.R;
import com.meishipintu.bankoa.models.entity.HttpResult;
import com.meishipintu.bankoa.models.entity.UserInfo;
import com.meishipintu.bankoa.models.http.HttpApi;
import com.meishipintu.library.util.ToastUtils;

import org.json.JSONException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class UserInfoActivity extends AppCompatActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.loan_no)
    TextView tvLoanNo;
    @BindView(R.id.tv_department)
    TextView tvDepartment;
    @BindView(R.id.tv_work_no)
    TextView tvWorkNo;
    @BindView(R.id.tv_tel)
    TextView tvTel;

    private CompositeSubscription subscriptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        ButterKnife.bind(this);

        subscriptions = new CompositeSubscription();
        initUI();
    }

    private void initUI() {
        tvTitle.setText(R.string.userinfo);
        subscriptions.add(HttpApi.getInstance().getUserInfoById(OaApplication.getUser().getUid())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<UserInfo>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtils.show(UserInfoActivity.this, e.getMessage(), true);
                    }

                    @Override
                    public void onNext(UserInfo userInfo) {
                        tvName.setText(userInfo.getUser_name());
                        tvTel.setText(userInfo.getMobile());
                        tvWorkNo.setText(userInfo.getJob_number());
                        tvLoanNo.setText(userInfo.getCredit_number());
                        try {
                            tvDepartment.setText(OaApplication.departmentList.getString(userInfo.getDepartment_id()));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }));
    }

    @OnClick({R.id.bt_back, R.id.rl_change_tel})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_back:
                onBackPressed();
                break;
            case R.id.rl_change_tel:
                Intent intent = new Intent(UserInfoActivity.this, BindTelActivity.class);
                intent.putExtra("tel", tvTel.getText().toString());
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        subscriptions.clear();
        super.onDestroy();
    }
}
