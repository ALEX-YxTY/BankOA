package com.meishipintu.bankoa.views.activities;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.meishipintu.bankoa.R;
import com.meishipintu.bankoa.components.DaggerSearchComponent;
import com.meishipintu.bankoa.contracts.SearchContract;
import com.meishipintu.bankoa.models.entity.Task;
import com.meishipintu.bankoa.modules.SearchMoudule;
import com.meishipintu.bankoa.presenters.SearchPresenterImp;
import com.meishipintu.library.util.ToastUtils;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created 2017-3-2
 * <p>
 * 主要功能：搜索页面
 */

public class SearchActivity extends BasicActivity implements SearchContract.IView {

    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.rv)
    RecyclerView rv;

    @Inject
    SearchPresenterImp mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

        DaggerSearchComponent.builder().searchMoudule(new SearchMoudule(this))
                .build().inject(this);
        setListener();
    }

    private void setListener() {
        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if ("".equals(etSearch.getText().toString())) {
                        ToastUtils.show(SearchActivity.this, R.string.err_search_empty, true);
                    } else {
                        mPresenter.search(etSearch.getText().toString());
                    }
                    return true;
                }
                return false;
            }
        });
    }

    @OnClick(R.id.cancel)
    public void onClick() {
    }

    //from SearchContract.IView
    @Override
    public void showResult(List<Task> taskList) {
        //TODO 刷新界面
    }
}
