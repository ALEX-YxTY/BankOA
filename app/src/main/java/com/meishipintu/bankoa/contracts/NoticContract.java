package com.meishipintu.bankoa.contracts;

import com.meishipintu.bankoa.models.entity.SysNotic;
import com.meishipintu.bankoa.models.entity.Task;
import com.meishipintu.bankoa.models.entity.UpClassRemind;
import com.meishipintu.bankoa.presenters.BasicPresenter;
import com.meishipintu.bankoa.views.BasicView;

import java.util.List;
import java.util.Set;

/**
 * Created by Administrator on 2017/3/27.
 * <p>
 * 主要功能：
 */

public interface NoticContract {

    interface IPresenter extends BasicPresenter {

        void getRemind(boolean reload, String uid, int page);

        void getSysNotic();

        Set<String> getReadSet(String uid);

        void saveReadSet(String uid, Set<String> readSet);
    }

    interface IView extends BasicView {

        void showRemind(boolean reload, List<UpClassRemind> remindList);

        void showSysNotic(List<SysNotic> noticList);

    }
}
