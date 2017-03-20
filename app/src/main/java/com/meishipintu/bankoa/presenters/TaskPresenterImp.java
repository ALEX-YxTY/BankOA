package com.meishipintu.bankoa.presenters;

import com.meishipintu.bankoa.OaApplication;
import com.meishipintu.bankoa.contracts.TaskContract;
import com.meishipintu.bankoa.models.entity.Task;
import com.meishipintu.bankoa.models.http.HttpApi;

import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Administrator on 2017/3/17.
 * <p>
 * 主要功能：
 */

public class TaskPresenterImp implements TaskContract.IPresenter {

    private TaskContract.IView iView;
    private HttpApi httpApi;
    private CompositeSubscription subsriptions;

    @Inject
    TaskPresenterImp(TaskContract.IView view) {
        this.iView = view;
        this.httpApi = HttpApi.getInstance();
        subsriptions = new CompositeSubscription();
    }

    @Override
    public void getTask(final int type) {
        subsriptions.add(httpApi.getTaskList(OaApplication.getUser().getId(),type)
        .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
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
                iView.showTask(taskList,type);
            }
        }));

    }

    //from BasicPresenter.IPresenter
    @Override
    public void unSubscrib() {
        subsriptions.clear();
    }

}
