package com.meishipintu.bankoa.contracts;

import com.meishipintu.bankoa.models.entity.ClerkInfo;
import com.meishipintu.bankoa.models.entity.UserInfo;
import com.meishipintu.bankoa.presenters.BasicPresenter;
import com.meishipintu.bankoa.views.BasicView;

import java.util.List;

/**
 * Created by Administrator on 2017/3/13.
 * <p>
 * 主要功能：
 */

public interface ClerkContract {

    interface IPresenter extends BasicPresenter {

        void getClerk(String uid);
    }

    interface IView extends BasicView {

        void showCLerk(List<ClerkInfo> clerkInfos);
    }
}
