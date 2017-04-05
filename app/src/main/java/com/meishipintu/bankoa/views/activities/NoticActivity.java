package com.meishipintu.bankoa.views.activities;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import com.meishipintu.bankoa.Constans;
import com.meishipintu.bankoa.OaApplication;
import com.meishipintu.bankoa.R;
import com.meishipintu.bankoa.components.DaggerNoticeComponent;
import com.meishipintu.bankoa.contracts.NoticContract;
import com.meishipintu.bankoa.models.entity.SysNotic;
import com.meishipintu.bankoa.models.entity.Task;
import com.meishipintu.bankoa.models.entity.UpClassRemind;
import com.meishipintu.bankoa.modules.NoticeModule;
import com.meishipintu.bankoa.presenters.NoticePresenterImp;
import com.meishipintu.bankoa.views.adapter.NoticeListAdapter;
import com.meishipintu.bankoa.views.adapter.RemindListAdapter;
import com.meishipintu.bankoa.views.adapter.TaskListAdapter;
import com.meishipintu.library.util.ToastUtils;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/3/1.
 * <p>
 * 提醒和消息页面
 */

public class NoticActivity extends BasicActivity implements NoticContract.IView{

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.left)
    RadioButton left;
    @BindView(R.id.right)
    RadioButton right;
    @BindView(R.id.vp)
    RecyclerView vp;
    @BindView(R.id.tv_empty)
    TextView tvEmpty;

    @Inject
    NoticePresenterImp mPresenter;

    private NoticeListAdapter noticeAdapter;
    private RemindListAdapter remindAdapter;
    private List<SysNotic> noticeList;
    private List<UpClassRemind> remindList;

    private int checkNow;

    private String uid;                 //当前显示对象的uid
    private String supervisorId;        //当前监管者的uid
    private String supervisorLevel;     //当前监管者的level

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        ButterKnife.bind(this);

        DaggerNoticeComponent.builder().noticeModule(new NoticeModule(this)).build().inject(this);

        initUI();

    }

    private void initUI() {
        uid = getIntent().getStringExtra("uid");
        if (uid == null) {
            uid = OaApplication.getUser().getUid();
        }
        supervisorId = getIntent().getStringExtra("supervisor_id");
        supervisorLevel = getIntent().getStringExtra("supervisor_level");

        tvTitle.setText(R.string.message);
        vp.setLayoutManager(new LinearLayoutManager(this));
        vp.setItemAnimator(new DefaultItemAnimator());

        checkNow = R.id.left;
        mPresenter.getRemind(uid);
    }

    @OnClick({R.id.bt_back, R.id.left, R.id.right})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_back:
                onBackPressed();
                break;
            case R.id.left:
                if (checkNow != R.id.left) {
                    checkNow = R.id.left;
                    tvEmpty.setVisibility(View.GONE);
                    vp.removeAllViews();
                    mPresenter.getRemind(uid);
                }
                break;
            case R.id.right:
                if (checkNow != R.id.right) {
                    checkNow = R.id.right;
                    tvEmpty.setVisibility(View.GONE);
                    vp.removeAllViews();
                    mPresenter.getSysNotic();
                }break;
        }
    }

    //from BasicView
    @Override
    public void showError(String errMsg) {
        ToastUtils.show(this, errMsg, true);
    }

    //from NoticeContract.IView
    @Override
    public void showRemind(List<UpClassRemind> remindList) {
        if (remindAdapter == null) {
            this.remindList = remindList;
            remindAdapter = new RemindListAdapter(this, this.remindList, supervisorId, supervisorLevel);
        } else {
            this.remindList.clear();
            this.remindList.addAll(remindList);
        }
        vp.setAdapter(remindAdapter);
        if (remindList.size() == 0) {
            tvEmpty.setVisibility(View.VISIBLE);
        }
    }

    //from NoticeContract.IView
    @Override
    public void showSysNotic(List<SysNotic> noticList) {
        if (noticeAdapter == null) {
            noticeList = noticList;
            noticeAdapter = new NoticeListAdapter(this, noticeList);
        } else {
            noticeList.clear();
            noticeList.addAll(noticList);
        }
        vp.setAdapter(noticeAdapter);
        if (noticList.size() == 0) {
            tvEmpty.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onDestroy() {
        mPresenter.unSubscrib();
        super.onDestroy();
    }
}
