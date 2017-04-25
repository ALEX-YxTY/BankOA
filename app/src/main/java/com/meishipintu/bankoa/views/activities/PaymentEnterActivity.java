package com.meishipintu.bankoa.views.activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.meishipintu.bankoa.R;
import com.meishipintu.bankoa.components.DaggerPaymentEnterComponent;
import com.meishipintu.bankoa.contracts.PaymentEnterContract;
import com.meishipintu.bankoa.models.entity.PaymentDetailItem;
import com.meishipintu.bankoa.models.entity.PaymentInfo;
import com.meishipintu.bankoa.modules.PaymentEnterModule;
import com.meishipintu.bankoa.presenters.PaymentEnterPresenterImp;
import com.meishipintu.library.util.DateUtil;
import com.meishipintu.library.util.StringUtils;
import com.meishipintu.library.util.ToastUtils;
import com.meishipintu.library.view.CustomDatePickeDialog;
import com.meishipintu.library.view.CustomEditText;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/3/21.
 * <p>
 * 录入还款信息页面
 */


public class PaymentEnterActivity extends AppCompatActivity implements PaymentEnterContract.IView{

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.cet_real_money)
    CustomEditText cetMoney;
    @BindView(R.id.cet_check_money)
    CustomEditText cetCheckMoney;
    @BindView(R.id.tv_loan_time)
    TextView tvLoanTime;
    @BindView(R.id.ll_payment_line)
    LinearLayout llPaymentLine;
    @BindView(R.id.scroll)
    ScrollView scrollView;
    @BindView(R.id.bt_input)
    Button btInput;

    @Inject
    PaymentEnterPresenterImp mPresenter;

    private int paymentNum = 0;
    private List<View> paymentList;
    private String taskId;
    private Dialog dialog;


    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_enter);
        ButterKnife.bind(this);
        tvTitle.setText(R.string.payment_info);
        paymentList = new ArrayList<>();
        taskId = getIntent().getStringExtra("task_id");
        DaggerPaymentEnterComponent.builder().paymentEnterModule(new PaymentEnterModule(this))
                .build().inject(this);
        initPayment();
    }

    //初始化paymentInfo
    private void initPayment() {
        mPresenter.getPaymentInfo(taskId);
    }

    @OnClick({R.id.bt_back,R.id.rl_add, R.id.rl_remove, R.id.bt_input,R.id.ll_loan_time})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_back:
                onBackPressed();
                break;
            case R.id.rl_add:
                addPayment(null);
                break;
            case R.id.rl_remove:
                removePayment();
                break;
            case R.id.ll_loan_time:
                showDatePicker(tvLoanTime);
                break;
            case R.id.bt_input:
                String checkMoney = cetCheckMoney.getContent();
                String realMoney = cetMoney.getContent();
                String loanTime = DateUtil.deformar2(tvLoanTime.getText().toString());
                if (StringUtils.isNullOrEmpty(new String[]{checkMoney, realMoney, loanTime})) {
                    ToastUtils.show(this, R.string.err_empty_input, true);
                    break;
                }
                PaymentInfo info = new PaymentInfo();
                info.setCheck_money(checkMoney);
                info.setResult_money(realMoney);
                info.setLoad_time(loanTime);
                info.setTask_id(taskId);

                List<PaymentDetailItem> items = new ArrayList<>();
                for(int i=0;i<paymentNum;i++) {
                    String timeI = ((TextView)paymentList.get(2 * i)).getText().toString();
                    String moneyI = ((CustomEditText)paymentList.get(2 * i + 1)).getContent();
                    if (StringUtils.isNullOrEmpty(new String[]{timeI, moneyI})) {
                        ToastUtils.show(this, R.string.err_empty_input, true);
                        return;
                    }
                    items.add(new PaymentDetailItem(DateUtil.deformar2(timeI), moneyI));
                }
                info.setRepayment_json(items);
                mPresenter.enterPayment(info);
                break;
        }
    }

    //添加还款条目
    private void addPayment(@Nullable PaymentDetailItem item) {
        paymentNum += 1;
        View newItem = View.inflate(this, R.layout.item_payment_wapper, null);
        LinearLayout llpaymentTime = (LinearLayout) newItem.findViewById(R.id.ll_payment_time1);
        final TextView tvPaymentTime = (TextView) newItem.findViewById(R.id.tv_payment_time);
        CustomEditText cetPayMoney = (CustomEditText) newItem.findViewById(R.id.cet_payment_money);
        if (item != null) {
            if (!item.getRepayment_time().equals("0")) {
                tvPaymentTime.setText(DateUtil.formart2(item.getRepayment_time()));
            }
            cetPayMoney.setContent(item.getRepayment_money());
        }
        TextView tvTimeName = (TextView) newItem.findViewById(R.id.tv_timeNum);
        tvTimeName.setText("还款时间" + paymentNum + "*");
        cetPayMoney.setTvTitle("还款金额" + paymentNum + "*");
        llpaymentTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker(tvPaymentTime);
            }
        });
        llPaymentLine.addView(newItem);
        paymentList.add(tvPaymentTime);
        paymentList.add(cetPayMoney);

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                scrollView.fullScroll(ScrollView.FOCUS_DOWN);
                //按钮获取焦点，隐藏光标
                btInput.requestFocus();
            }
        },200);
    }

//    //动态添加还款条目
//    private void addPayment(PaymentDetailItem paymentItem) {
//        final String ItemId = paymentItem.getId();
//        final String paymentMoneyItem = paymentItem.getRepayment_money();
//        View itemView = LayoutInflater.from(this).inflate(R.layout.item_payment_detail_wapper, llPaymentLine, false);
//        TextView tvPaymentTime = (TextView) itemView.findViewById(R.id.tv_payment_time1_1);
//        TextView tvPaymentMoney = (TextView) itemView.findViewById(R.id.tv_payment_money1_1);
//        tvPaymentTime.setText(DateUtil.formart2(paymentItem.getRepayment_time()));
//        tvPaymentMoney.setText(paymentMoneyItem);
//        final ImageView ivIcon1 = (ImageView) itemView.findViewById(R.id.choose1);
//        final ImageView ivIcon2 = (ImageView) itemView.findViewById(R.id.choose2);
//        if (paymentItem.is_finish()) {
//            //已付款
//            ivIcon1.setSelected(true);
//            ivIcon2.setSelected(true);
//            ivIcon1.setEnabled(false);
//            ivIcon2.setEnabled(false);
//        } else if (canItemClick) {
//            //未付款可点击
//            ivIcon1.setEnabled(true);
//            ivIcon1.setSelected(false);
//            ivIcon2.setSelected(false);
//            ivIcon2.setEnabled(true);
//            ivIcon1.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    boolean statusNow = v.isSelected();
//                    ivIcon1.setSelected(!statusNow);
//                    ivIcon2.setSelected(!statusNow);
//                    id = !statusNow ? ItemId : null;
//                    paymentMoney = !statusNow ? paymentMoneyItem : null;
//                    btInput.setEnabled(!statusNow);
//                }
//            });
//            ivIcon2.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    boolean statusNow = v.isSelected();
//                    ivIcon1.setSelected(!statusNow);
//                    ivIcon2.setSelected(!statusNow);
//                    id = !statusNow ? ItemId : null;
//                    paymentMoney = !statusNow ? paymentMoneyItem : null;
//                    btInput.setEnabled(!statusNow);
//                }
//            });
//            //设置之后的按钮不显示
//            canItemClick = false;
//        } else {
//            //未付款不可点击
//            ivIcon1.setVisibility(View.INVISIBLE);
//            ivIcon2.setVisibility(View.INVISIBLE);
//        }
//        llPaymentLine.addView(itemView);
//    }

    //显示日期选择器
    private void showDatePicker(final TextView tvPaymentTime) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            DatePickerDialog pickerDialog = new DatePickerDialog(this);
            pickerDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    tvPaymentTime.setText(String.format("%4d年%2d月%2d日", year, (month+1), dayOfMonth));
                }
            });
            dialog = pickerDialog;
        } else {
            dialog = new CustomDatePickeDialog(this,R.style.DialogNoAction,new CustomDatePickeDialog.onDateSelectListener() {
                @Override
                public void onDateSelect(Calendar calendar) {
                    tvPaymentTime.setText(String.format("%4d年%2d月%2d日", calendar.get(Calendar.YEAR)
                            , (calendar.get(Calendar.MONTH)+1), calendar.get(Calendar.DAY_OF_MONTH)));
                }
            });

        }
        dialog.show();
    }

    //删除还款条目
    private void removePayment() {
        if (paymentNum > 1) {
            //移去最后一组
            llPaymentLine.removeViewAt(llPaymentLine.getChildCount() - 1);
            paymentList.remove(paymentList.size() - 1);
            paymentList.remove(paymentList.size() - 1);
            paymentNum -= 1;
        }
    }

    //from PaymentEnterContract.IView
    @Override
    public void onPaymentInfoGet(PaymentInfo info) {
        cetCheckMoney.setContent(info.getCheck_money());
        cetMoney.setContent(info.getResult_money());
        tvLoanTime.setText(DateUtil.formart2(info.getLoad_time()));
        llPaymentLine.removeAllViews();
        for (PaymentDetailItem paymentItem : info.getRepayment_json()) {
            addPayment(paymentItem);
        }
    }

    //from PaymentEnterContract.IView
    @Override
    public void onEnterSuccess() {
        setResult(RESULT_OK);
        this.finish();
    }

    //from PaymentEnterContract.IView
    @Override
    public void showError(String errMsg) {
        ToastUtils.show(this, errMsg, true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
        mPresenter.unSubscrib();
    }
}
