package com.meishipintu.bankoa;

import com.meishipintu.bankoa.presenters.TaskDetailPresenterImp;
import com.meishipintu.bankoa.presenters.TaskPresenterImp;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        TaskDetailPresenterImp presenterImp = new TaskDetailPresenterImp();
        presenterImp.getTaskInfo("7");
        Thread.sleep(2000);
        System.out.println(presenterImp.nodeInfoNow);
    }
}