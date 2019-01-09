package code.spxt.cn.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;

import java.util.Timer;
import java.util.TimerTask;

import code.spxt.cn.MainActivity;
import code.spxt.cn.R;
import code.spxt.cn.base.BaseActivity;
import code.spxt.cn.common.UserCenter;

/**
 *
 */
public class SplashActivity extends BaseActivity {
    private static final long LAUNCH_MIN_TIME = 2000L;
    private static final int MSG_CITY_INIT_FINISH = 1;
    private long mLaunchTime;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_CITY_INIT_FINISH:
                    gotoActivity();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mLaunchTime = SystemClock.elapsedRealtime();
        initCityDB();

    }

    private void initCityDB() {
        new Thread(() -> handler.sendEmptyMessageDelayed(MSG_CITY_INIT_FINISH, 100)).start();
    }

    private void gotoActivity() {
        long elapsed = SystemClock.elapsedRealtime() - mLaunchTime;
        if (elapsed >= LAUNCH_MIN_TIME) {
            performGotoActivity();
            finish();
        } else {
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    if (SplashActivity.this.isFinishing()) {
                        return;
                    }
                    cancel();
                    performGotoActivity();
                    finish();
                }
            }, LAUNCH_MIN_TIME - elapsed);
        }

    }

    private void performGotoActivity() {
        if (UserCenter.isLogin(this)) {
            startActivity(new Intent(this, MainActivity.class));
        } else {
            //TODO  登录
            LoginActivity.startLoginActivity(this);
        }
    }

}