package com.meishipintu.bankoa;

import com.meishipintu.bankoa.presenters.TaskDetailPresenterImp;
import com.meishipintu.bankoa.presenters.TaskPresenterImp;
import com.meishipintu.library.util.DateUtil;

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
        String timeStamp = 1490088248 + "";
        String format = DateUtil.formart2(timeStamp);
        System.out.println(DateUtil.deformar2(format));
    }
}