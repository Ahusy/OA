package code.spxt.cn.updata.controller;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.common.utils.NetWorkUtil;
import com.common.utils.StringUtil;
import com.common.utils.ToastUtil;
import com.common.viewinject.annotation.ViewInject;

import code.spxt.cn.R;
import code.spxt.cn.updata.service.UpdateService;
import code.spxt.cn.utils.CommonTools;
import code.spxt.cn.utils.PermissionUtils;
import code.spxt.cn.utils.ViewInjectUtils;

import static code.spxt.cn.base.BaseActivity.REQUEST_PERMISSION_CODE;


public class UpdateActivity extends AppCompatActivity implements View.OnClickListener {

    public final static String KEY_UPDATE_TYPE = "force";
    public final static String KEY_UPDATE_URL = "url";
    public final static String KEY_UPDATE_VERSION_NAME = "version_name";
    public final static String KEY_UPDATE_VERSION_DESC = "version_desc";

    @ViewInject(R.id.txt_update_version)
    private TextView mTxtUpdateVersion;
    @ViewInject(R.id.btn_ignore)
    private TextView mBtnIgnore;
    @ViewInject(R.id.btn_update)
    private TextView mBtnUpdate;
    @ViewInject(R.id.btn_force_update)
    private TextView mBtnForceUpdate;
    @ViewInject(R.id.ll_one)
    LinearLayout llOne;
    @ViewInject(R.id.ll_two)
    LinearLayout llTwo;
    private int type;
    private String ApkUrl;
    private String VersionName;

    public static void startUpdateActiviy(Context context, String newVersion, String downloadUrl, int type) {
        Intent intent = new Intent(context, UpdateActivity.class);
        intent.putExtra(KEY_UPDATE_VERSION_NAME, newVersion);
        intent.putExtra(KEY_UPDATE_URL, downloadUrl);
        intent.putExtra(KEY_UPDATE_TYPE, type);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        this.getWindow().addFlags(Window.FEATURE_NO_TITLE);
        initStyle();
        ViewInjectUtils.inject(this);

        Intent startIntent = getIntent();
        if (startIntent != null) {
            type = startIntent.getIntExtra(KEY_UPDATE_TYPE, 2);
            ApkUrl = startIntent.getStringExtra(KEY_UPDATE_URL);
            VersionName = startIntent.getStringExtra(KEY_UPDATE_VERSION_NAME);
        }
        mTxtUpdateVersion.setText("V" + VersionName);
        if (type == 1) {//1为强制升级
            mBtnIgnore.setVisibility(View.GONE);
            mBtnUpdate.setVisibility(View.GONE);
            mBtnForceUpdate.setVisibility(View.VISIBLE);
        } else {
            mBtnIgnore.setVisibility(View.VISIBLE);
            mBtnUpdate.setVisibility(View.VISIBLE);
            mBtnForceUpdate.setVisibility(View.GONE);
        }

        mBtnIgnore.setOnClickListener(this);
        mBtnUpdate.setOnClickListener(this);
        mBtnForceUpdate.setOnClickListener(this);
    }

    private void initStyle() {
        Window window = getWindow();
        window.setGravity(Gravity.CENTER);
        WindowManager.LayoutParams wl = window.getAttributes();
        // 以下这两句是为了保证按钮可以水平满屏
        wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
        wl.height = CommonTools.dp2px(this, 284);
        this.onWindowAttributesChanged(wl);
    }



    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return KeyEvent.KEYCODE_BACK == keyCode || super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_ignore:
                finish();
                break;
            case R.id.btn_update:
            case R.id.btn_force_update:
                if (StringUtil.isEmpty(ApkUrl)) return;
                if (!NetWorkUtil.isConnect(UpdateActivity.this)) {
                    Toast.makeText(UpdateActivity.this, "无可用网络", Toast.LENGTH_LONG).show();
                    return;
                }

                if (PermissionUtils.isGetPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)&& PermissionUtils.isGetPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    download();
                } else {
                    PermissionUtils.requestPermissions(this, REQUEST_PERMISSION_CODE, Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE);
                }
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (REQUEST_PERMISSION_CODE == requestCode) {
            if (grantResults.length > 0) {
                if ((grantResults[0] == PackageManager.PERMISSION_GRANTED)
                        && (grantResults[1] == PackageManager.PERMISSION_GRANTED)) {
                    download();
                }
            } else {
                ToastUtil.show(this, "获取存储权限失败，请到应用设置中授予权限后再使用！");
            }
        }
    }

    private void download() {
        llOne.setVisibility(View.GONE);
        llTwo.setVisibility(View.VISIBLE);
        String packageName = "com.android.providers.downloads";
        int state = UpdateActivity.this.getPackageManager().getApplicationEnabledSetting(packageName);
        if (state == PackageManager.COMPONENT_ENABLED_STATE_DISABLED ||
                state == PackageManager.COMPONENT_ENABLED_STATE_DISABLED_USER ||
                state == PackageManager.COMPONENT_ENABLED_STATE_DISABLED_UNTIL_USED) {
            //不能使用系统下载管理器
            startActivity(CommonTools.getIntent(UpdateActivity.this));
        } else {
            mBtnUpdate.setEnabled(false);
            mBtnForceUpdate.setEnabled(false);
            startService(new Intent().setClass(getApplicationContext(), UpdateService.class).putExtra(KEY_UPDATE_URL, ApkUrl));
        }
    }
}
