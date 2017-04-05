package com.meishipintu.bankoa;

import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

/**
 * Created by Administrator on 2017/3/31.
 * 作为消息总线
 *
 * 使用方法：
 *  1. 发送方：RxBus.{@link #getDefault()}.{@link #send(Object)};
 *  2. 订阅方：Subscription subscription = RxBus.{@link #getDefault()}
 *          .{@link #getObservable(Class)}
 *          .subscribe(new Subscriber(){
 *          }
 *  3. 解除订阅：onDestory(){
 *              if(!subscription.isUnSubscribed()){
 *                  subscription.unSubscribe();
 *              }
 *          }
 */

public class RxBus {

    //静态对象，单例模式
    private static RxBus defaultInstance;
    private Subject<Object, Object> bus;

    public RxBus() {
        bus = new SerializedSubject<>(PublishSubject.create());
    }

    //获取单例，因为可能在多个线程启动，因此需要加同步锁
    public static RxBus getDefault() {
        if (defaultInstance == null) {
            synchronized (RxBus.class) {
                if (defaultInstance == null) {
                    defaultInstance = new RxBus();
                }
            }
        }
        return defaultInstance;
    }

    /**
     * 向总线发送消息
     * @param object 发送的数据对象
     */
    public void send(Object object) {
        bus.onNext(object);
    }

    /**
     *  从总线获取Observable对象并订阅
     *  订阅者只会接收到固定格式的数据
     * @param eventType 接受Observable的数据格式
     * @param <T> 泛型
     * @return RxBus的Observable对象
     */
    public <T> Observable<T> getObservable(Class<T> eventType) {
        return bus.ofType(eventType);
    }
}
