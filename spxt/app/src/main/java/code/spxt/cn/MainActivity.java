package code.spxt.cn;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.RadioGroup;

import com.common.utils.LogUtil;
import com.common.utils.ToastUtil;

import code.spxt.cn.base.BaseFragment;
import code.spxt.cn.base.ToolBarActivity;
import code.spxt.cn.fragments.CenterFragment;
import code.spxt.cn.fragments.CommitFragment;
import code.spxt.cn.fragments.HomeFragment;
import code.spxt.cn.network.Parsers;
import code.spxt.cn.network.entity.UpdateEntity;
import code.spxt.cn.updata.controller.UpdateActivity;
import code.spxt.cn.utils.ExitManager;

import static code.spxt.cn.common.CommonConstant.PERMISSIONS_REQUEST_CODE;
import static code.spxt.cn.common.CommonConstant.REQUEST_NET_SUCCESS;

public class MainActivity extends ToolBarActivity implements RadioGroup.OnCheckedChangeListener{

    public static final String TAG = "MainActivity";
    public static final long DIFF_DEFAULT_BACK_TIME = 2000;

    private long mBackTime = -1;

    private FragmentManager fragmentManager;
    private RadioGroup rgView;
    private BaseFragment oneF, twoF, threeF;

    public static final String JUMP_TYPE = "jump_type";

    public static void startMainActivity(Context context){
        Intent intent = new Intent(context,MainActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.spxt_main);

        fragmentManager = getSupportFragmentManager();
        initView();
        initData();
        checkUpdateVersion();
        requestPermission();
    }
    /**
     * 初始化View
     */
    private void initView() {
        setCenterTitle("吉城资管综合管理系统");
        rgView = (RadioGroup) findViewById(R.id.rg);
        rgView.setOnCheckedChangeListener(this);
        mBtnTitleLeft.setVisibility(View.GONE);
    }

    /**
     * 处理初始化数据
     */
    private void initData() {
        Intent intent = getIntent();
        int type = intent.getIntExtra(JUMP_TYPE, 1);
        if (type == 1) {
            //默认跳转首页
            jumpTo(TabType.ONE);
        } else if (type == 2) {
            jumpTo(TabType.TWO);
        } else if (type == 3) {
            jumpTo(TabType.THREE);
        }
    }


    @Override
    public void success(int requestCode, String data) {
        super.success(requestCode, data);
        if (REQUEST_UPDATE_VERSION_CODE == requestCode) {
            UpdateEntity update = Parsers.getUpdate(data);
            if (update != null) {
                if (REQUEST_NET_SUCCESS.equals(update.getResultCode())) {
                    String version = update.getNewVersion() == null ? "" : update.getNewVersion();
                    String url = update.getDownloadUrl() == null ? "" : update.getDownloadUrl();
                    update.setType(update.getType());
                    UpdateActivity.startUpdateActiviy(this, version, url, update.getType());
                }
            } else {
                ToastUtil.show(this, "检查更新失败");
            }
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        //添加或者展示
        switch (checkedId) {
            case R.id.rb_one:
                if (oneF == null) {
                    LogUtil.d(TAG, "one");
                    oneF = new HomeFragment();
                }
                ft.replace(R.id.fl_content, oneF);
                break;
            case R.id.rb_two:
                if (twoF == null) {
                    LogUtil.d(TAG, "two");
                    twoF = new CommitFragment();
                }
                ft.replace(R.id.fl_content, twoF);
                break;
            case R.id.rb_three:

                if (threeF == null) {
                    LogUtil.d(TAG, "three");
                    threeF = new CenterFragment();
                }
                ft.replace(R.id.fl_content, threeF);
                break;
        }
        ft.commit();
    }

    /**
     * 判断跳转到哪个fragment
     */
    public void jumpTo(TabType tt) {
        switch (tt) {
            case ONE:
                rgView.check(R.id.rb_one);
                break;
            case TWO:
                rgView.check(R.id.rb_two);
                break;
            case THREE:
                rgView.check(R.id.rb_three);
                break;
        }
    }

    private enum TabType {
        ONE(), TWO(), THREE();

        TabType() {
        }
    }


    @Override
    public void onBackPressed() {
        long nowTime = System.currentTimeMillis();
        long diff = nowTime - mBackTime;
        if (diff >= DIFF_DEFAULT_BACK_TIME) {
            mBackTime = nowTime;
            ToastUtil.show(getApplicationContext(), "再按一次退出");
        } else {
            ExitManager.instance.exit();
        }
    }

    private void requestPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{
                            Manifest.permission.READ_PHONE_STATE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                    }, PERMISSIONS_REQUEST_CODE);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_CODE: {
                // 用户取消了权限弹窗
                if (grantResults.length == 0) {
                    ToastUtil.show(getApplicationContext(), "无法获取所需的权限, 请到系统设置开启");
                    return;
                }
                // 用户拒绝了某些权限
                for (int x : grantResults) {
                    if (x == PackageManager.PERMISSION_DENIED) {
                        ToastUtil.show(getApplicationContext(), "无法获取程序所需的权限, 请到系统设置开启");
                        return;
                    }
                }
            }
        }
    }

}
