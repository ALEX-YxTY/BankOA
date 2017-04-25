package com.meishipintu.bankoa.views.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.meishipintu.bankoa.Constans;
import com.meishipintu.bankoa.R;
import com.meishipintu.bankoa.components.DaggerPaymentDetailComponent;
import com.meishipintu.bankoa.contracts.PaymentDetailContract;
import com.meishipintu.bankoa.models.entity.PaymentDetailItem;
import com.meishipintu.bankoa.models.entity.PaymentInfo;
import com.meishipintu.bankoa.modules.PaymentDetailModule;
import com.meishipintu.bankoa.presenters.PaymentDetailImp;
import com.meishipintu.library.util.DateUtil;
import com.meishipintu.library.util.ToastUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/3/21.
 * <p>
 * 查看还款详情页面
 */


public class PaymentDetailActivity extends AppCompatActivity implements PaymentDetailContract.IView{

    private static final String TAG = "BankOA-PaymentDetail";
    @BindView(R.id.tv_title)
    TextView tvTitle;

    @BindView(R.id.et_check_money)
    TextView etCheckMoney;
    @BindView(R.id.et_real_money)
    TextView etRealMoney;
    @BindView(R.id.tv_loan_time)
    TextView tvLoanTime;
    @BindView(R.id.bt_input)
    Button btInput;

    @BindView(R.id.ll_payment_line)
    LinearLayout llPaymentLine;

    @Inject
    PaymentDetailImp mPresenter;

    private String taskId;
    private String repaymentStatus;         //标记是否已完成
    private boolean canItemClick;           //标记当前最新一期还款item

    private String id;                      //标记待还款node的id
    private String paymentMoney;            //标记还款node的money

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_detail);
        ButterKnife.bind(this);

        DaggerPaymentDetailComponent.builder().paymentDetailModule(new PaymentDetailModule(this))
                .build().inject(this);
        initUI();
    }

    private void initUI() {
        canItemClick = true;
        tvTitle.setText(R.string.payment_alert);
        repaymentStatus = getIntent().getStringExtra("repayment_status");
        if ("1".equals(repaymentStatus)) {
            btInput.setText(R.string.finish);
            btInput.setClickable(false);
        }
        btInput.setEnabled(false);
        taskId = getIntent().getStringExtra("task_id");
        mPresenter.getPaymentInfo(taskId);
    }

    @OnClick({R.id.bt_back, R.id.bt_input})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_back:
                setResult(RESULT_OK);
                onBackPressed();
                break;
            case R.id.bt_input:
                if (id != null) {
                    mPresenter.finishPaymen(taskId, id, paymentMoney);
                }
                break;
        }
    }

    //from PaymentDetailContract.IView
    @Override
    public void onPaymentFinish() {
        ToastUtils.show(this, "已确认付款", true);
        //还原状态
        initUI();
    }

    //from PaymentDetailContract.IView
    @Override
    public void showError(String errMsg) {
        ToastUtils.show(this, errMsg, true);
    }

    //from PaymentDetailContract.IView
    @Override
    public void onPaymentInfoGet(PaymentInfo info) {
        etCheckMoney.setText(info.getCheck_money());
        etRealMoney.setText(info.getResult_money());
        tvLoanTime.setText(DateUtil.formart2(info.getLoad_time()));
        llPaymentLine.removeAllViews();
        for (PaymentDetailItem paymentItem : info.getRepayment_json()) {
            addPayment(paymentItem);
        }
    }

    //动态添加还款条目
    private void addPayment(PaymentDetailItem paymentItem) {
        final String ItemId = paymentItem.getId();
        final String paymentMoneyItem = paymentItem.getRepayment_money();
        View itemView = LayoutInflater.from(this).inflate(R.layout.item_payment_detail_wapper, llPaymentLine, false);
        TextView tvPaymentTime = (TextView) itemView.findViewById(R.id.tv_payment_time1_1);
        TextView tvPaymentMoney = (TextView) itemView.findViewById(R.id.tv_payment_money1_1);
        tvPaymentTime.setText(DateUtil.formart2(paymentItem.getRepayment_time()));
        tvPaymentMoney.setText(paymentMoneyItem);
        final ImageView ivIcon1 = (ImageView) itemView.findViewById(R.id.choose1);
        final ImageView ivIcon2 = (ImageView) itemView.findViewById(R.id.choose2);
        if (true) {
            //已付款
            ivIcon1.setSelected(true);
            ivIcon2.setSelected(true);
            ivIcon1.setEnabled(false);
            ivIcon2.setEnabled(false);
        } else if (canItemClick) {
            //未付款可点击
            ivIcon1.setEnabled(true);
            ivIcon1.setSelected(false);
            ivIcon2.setSelected(false);
            ivIcon2.setEnabled(true);
            ivIcon1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean statusNow = v.isSelected();
                    ivIcon1.setSelected(!statusNow);
                    ivIcon2.setSelected(!statusNow);
                    id = !statusNow ? ItemId : null;
                    paymentMoney = !statusNow ? paymentMoneyItem : null;
                    btInput.setEnabled(!statusNow);
                }
            });
            ivIcon2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean statusNow = v.isSelected();
                    ivIcon1.setSelected(!statusNow);
                    ivIcon2.setSelected(!statusNow);
                    id = !statusNow ? ItemId : null;
                    paymentMoney = !statusNow ? paymentMoneyItem : null;
                    btInput.setEnabled(!statusNow);
                }
            });
            //设置之后的按钮不显示
            canItemClick = false;
        } else {
            //未付款不可点击
            ivIcon1.setVisibility(View.INVISIBLE);
            ivIcon2.setVisibility(View.INVISIBLE);
        }
        llPaymentLine.addView(itemView);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.unSubscrib();
    }
}
