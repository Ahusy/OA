package code.spxt.cn;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

import code.spxt.cn.utils.SafeSharePreferenceUtil;

/**
 * Created by dell on 2018/12/12
 */

public class MyApplication extends Application {
    private static MyApplication context;
    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        Fresco.initialize(this);
        SafeSharePreferenceUtil.init(this);
    }


    public synchronized static MyApplication getInstance() {
        return context;
    }

}