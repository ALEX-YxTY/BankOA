package com.meishipintu.bankoa.contracts;

import com.meishipintu.bankoa.models.entity.Task;
import com.meishipintu.bankoa.presenters.BasicPresenter;
import com.meishipintu.bankoa.views.BasicView;

import java.util.List;

/**
 * Created by Administrator on 2017/3/15.
 * <p>
 * 主要功能：
 */

public interface SearchContract {

    interface IPresenter extends BasicPresenter{
        void search(String level, String department_id, String uid, String searchContent);
    }

    interface IView extends BasicView{
        void showResult(List<Task> taskList);
    }

}
