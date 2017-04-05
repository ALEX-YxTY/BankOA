package com.meishipintu.bankoa.presenters;

import android.media.DrmInitData;
import android.util.Log;

import com.meishipintu.bankoa.contracts.SearchContract;
import com.meishipintu.bankoa.models.entity.Task;
import com.meishipintu.bankoa.models.http.HttpApi;

import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/3/15.
 * <p>
 * 主要功能：
 */

public class SearchPresenterImp implements SearchContract.IPresenter {

    private static final String TAG = "BankOA-searchPresenter";
    private SearchContract.IView iView;
    private HttpApi httpApi;
    private Subscription subscription;

    @Inject
    SearchPresenterImp(SearchContract.IView view) {
        this.iView = view;
        httpApi = HttpApi.getInstance();
    }

    @Override
    public void search(String level, String department_id, String uid, String searchContent) {
        //调用search接口
        subscription = httpApi.getSearchInfo(level, department_id, uid, searchContent).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Task>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        iView.showError(e.getMessage());
                    }

                    @Override
                    public void onNext(List<Task> taskList) {
                        iView.showResult(taskList);
                    }
                });
    }

    //from BasicPresenter
    @Override
    public void unSubscrib() {
        if (subscription != null) {
            subscription.unsubscribe();
        }
    }
}
