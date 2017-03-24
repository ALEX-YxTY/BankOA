package com.meishipintu.bankoa.views.activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.ListMenuItemView;
import android.text.format.DateUtils;
import android.util.Log;
import android.util.StringBuilderPrinter;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.meishipintu.bankoa.Constans;
import com.meishipintu.bankoa.R;
import com.meishipintu.bankoa.components.DaggerPaymentEnterComponent;
import com.meishipintu.bankoa.contracts.PaymentEnterContract;
import com.meishipintu.bankoa.models.entity.PaymentDetailItem;
import com.meishipintu.bankoa.models.entity.PaymentInfo;
import com.meishipintu.bankoa.models.entity.paymentItem;
import com.meishipintu.bankoa.modules.PaymentEnterModule;
import com.meishipintu.bankoa.presenters.PaymentEnterPresenterImp;
import com.meishipintu.library.util.DateUtil;
import com.meishipintu.library.util.StringUtils;
import com.meishipintu.library.util.ToastUtils;
import com.meishipintu.library.view.CustomDatePickeDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;

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

    @BindView(R.id.et_check_money)
    EditText etCheckMoney;
    @BindView(R.id.et_real_money)
    EditText etRealMoney;
    @BindView(R.id.tv_loan_time)
    TextView tvLoanTime;
    @BindView(R.id.ll_payment_line)
    LinearLayout llPaymentLine;
    @BindView(R.id.scroll)
    ScrollView scrollView;

    @Inject
    PaymentEnterPresenterImp mPresenter;

    private int paymentNum = 0;
    private List<TextView> paymentList;
    private String taskId;

    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_enter);
        ButterKnife.bind(this);
        tvTitle.setText(R.string.payment_info);
        paymentList = new ArrayList<>();
        taskId = getIntent().getStringExtra("task_id");
        addPayment();
        DaggerPaymentEnterComponent.builder().paymentEnterModule(new PaymentEnterModule(this))
                .build().inject(this);
    }

    @OnClick({R.id.bt_back,R.id.rl_add, R.id.rl_remove, R.id.bt_input,R.id.tv_loan_time})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_back:
                onBackPressed();
                break;
            case R.id.rl_add:
                addPayment();
                break;
            case R.id.rl_remove:
                removePayment();
                break;
            case R.id.tv_loan_time:
                showDatePicker(tvLoanTime);
                break;
            case R.id.bt_input:
                String checkMoney = etCheckMoney.getText().toString();
                String realMoney = etRealMoney.getText().toString();
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
                    String timeI = paymentList.get(2 * i).getText().toString();
                    String moneyI = paymentList.get(2 * i + 1).getText().toString();
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
    private void addPayment() {
        paymentNum += 1;
        View newItem = View.inflate(this, R.layout.item_payment_wapper, null);
        LinearLayout llpaymentTime = (LinearLayout) newItem.findViewById(R.id.ll_payment_time1);
        final TextView tvPaymentTime = (TextView) newItem.findViewById(R.id.tv_payment_time);
        EditText etPayMoney = (EditText) newItem.findViewById(R.id.et_payment_money);

        TextView tvTimeName = (TextView) newItem.findViewById(R.id.tv_timeNum);
        TextView tvMoneyName = (TextView) newItem.findViewById(R.id.tv_moneyNum);
        tvTimeName.setText("还款时间" + paymentNum + "*");
        tvMoneyName.setText("还款金额" + paymentNum + "*");
        llpaymentTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker(tvPaymentTime);
            }
        });
        llPaymentLine.addView(newItem);
        paymentList.add(tvPaymentTime);
        paymentList.add(etPayMoney);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                scrollView.fullScroll(ScrollView.FOCUS_DOWN);
            }
        },200);
    }

    //显示日期选择器
    private void showDatePicker(final TextView tvPaymentTime) {
        Dialog dialog;
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
    public void onEnterSuccess() {
        setResult(RESULT_OK);
        this.finish();
    }

    //from PaymentEnterContract.IView
    @Override
    public void showError(String errMsg) {
        ToastUtils.show(this, errMsg, true);
    }
}
