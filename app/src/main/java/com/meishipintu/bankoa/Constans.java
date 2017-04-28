package com.meishipintu.bankoa;

/**
 * Created by Administrator on 2017/3/2.
 * <p>
 * 主要功能：
 */

public class Constans {

    public static final String APP = "BankOA";

    public static final int REGISTER_TYPE_NORMAL = 100;
    public static final int REGISTER_TYPE_FORGET_PSW = 200;
    public static final int REGISTER_TYPE_REBIND = 300;

    public static final int LOGOUT = 400;                           //BusType 退出登录成功

    public static String BaseURL = "http://njbank.domobile.net/";
    public static String APPLY_URL = "http://njbank.domobile.net/Home/Index/index";         //申请页面地址
    public static String PROCESS_URL = "http://njbank.domobile.net/Home/Index/jump";      //浏览全程地址
//    public static String PROCESS_URL = "http://njbank.domobile.net/Home/Index/remark";      //浏览全程地址

    public static final int APPLY_TYPE = 1;                           //webview类型，1.申请 2.主流程页面
    public static final int PROCESS_TYPE = 2;                         //webview类型，1.申请 2.主流程页面

    public static final int START_REGISTER = 10001;                  //requestCode 从主页启动注册
    public static final int START_FORGET_PSW = 10002;                //requestCode 从主页启动重设密码

    public static final int FINISH_AND_INPUT = 10003;                //requestCode 从任务详情跳转信息录入界面
    public static final int PAYMENT = 10004;                         //requestCode 从任务列表页面跳转进入录入或付款
    public static final int SEE_ALL = 10005;                         //requestCode 从任务详情跳转入浏览全程页面

}
