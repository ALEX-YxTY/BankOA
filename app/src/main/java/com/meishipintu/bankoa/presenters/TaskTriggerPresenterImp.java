package com.meishipintu.bankoa.presenters;

import android.content.Context;

import com.meishipintu.bankoa.contracts.TaskTriggerContract;
import com.meishipintu.bankoa.models.PreferenceHelper;
import com.meishipintu.bankoa.models.entity.TaskTriggerInfo;
import com.meishipintu.bankoa.models.http.HttpApi;

import javax.inject.Inject;

import rx.subscriptions.CompositeSubscription;

/**
 * Created by Administrator on 2017/3/2.
 * <p>
 *
 * 主要功能：TaskTrigger页的依赖Module
 */

public class TaskTriggerPresenterImp implements TaskTriggerContract.IPresenter {

    private TaskTriggerContract.IView view;
    private CompositeSubscription subscriptions;
    private HttpApi httpApi;
    private PreferenceHelper preferenceHelper;

    @Inject
    TaskTriggerPresenterImp(Context context, TaskTriggerContract.IView view) {
        this.view = view;
        httpApi = HttpApi.getInstance();
        this.subscriptions = new CompositeSubscription();
    }

    @Override
    public void getDistrcts() {

    }

    @Override
    public void getBranches() {

    }

    @Override
    public void triggerTask(TaskTriggerInfo info) {

    }

    //from BasicPresenter
    @Override
    public void unSubscrib() {

    }
}
