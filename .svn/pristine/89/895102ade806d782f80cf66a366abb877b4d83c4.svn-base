package code.spxt.cn.base;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.common.interfaces.IActivityHelper;
import com.common.network.FProtocol;
import com.common.ui.FBaseActivity;
import com.common.utils.ToastUtil;
import com.common.utils.VersionInfoUtils;

import java.util.IdentityHashMap;

import code.spxt.cn.activitys.LoginActivity;
import code.spxt.cn.common.UserCenter;
import code.spxt.cn.network.Constants;
import code.spxt.cn.network.Parsers;
import code.spxt.cn.network.entity.Entity;
import code.spxt.cn.utils.ConfigUtils;
import code.spxt.cn.utils.DialogUtils;
import code.spxt.cn.utils.ExitManager;
import code.spxt.cn.utils.TextUntil;

import static code.spxt.cn.common.CommonConstant.REQUEST_NET_SUCCESS;


/**
 */
public class BaseActivity extends FBaseActivity implements IActivityHelper {

    public static final int REQUEST_UPDATE_VERSION_CODE = -3;
    public static final int REQUEST_PERMISSION_CODE = 0x1;

    protected Resources res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //建议在Base中写，解决在系统设置中修改了语言之后，切回应用语言会变成刚修改的语言的问题
        //两种处理方法，一种是在BaseActivity的onCreate方法的super.onCreate()方法之前设置语言
        //第二种方法是重写BaseActivity的getResources()方法，返回新的Resource
//        MainApplication.configLanguage(this);
        super.onCreate(savedInstanceState);
        ExitManager.instance.addActivity(this);
        res = getResources();
    }

    /**
     * 检查更新
     */
    protected void checkUpdateVersion() {
        IdentityHashMap<String, String> params = new IdentityHashMap<>();
        params.put("version_code", String.valueOf(VersionInfoUtils.getVersionCode(this)));
        params.put("terminal", "10");
        requestHttpData(Constants.Urls.URL_POST_CHECK_UPDATE, REQUEST_UPDATE_VERSION_CODE, FProtocol.HttpMethod.POST, params);
    }


    @Override
    public void requestHttpData(String path, int requestCode) {
        super.requestHttpData(path, requestCode);
    }

    @Override
    public void requestHttpData(String path, int requestCode, FProtocol.HttpMethod method, IdentityHashMap<String, String> postParameters) {
        if (postParameters == null) {
            postParameters = new IdentityHashMap<>();
        }
        postParameters.put("user_id", UserCenter.getUserId(this));
        postParameters.put("language", ConfigUtils.getPreLanguage(this));
        super.requestHttpData(path, requestCode, method, postParameters);
    }

    @Override
    public void success(int requestCode, String data) {
        super.success(requestCode, data);
        closeProgressDialog();
        Entity entity = Parsers.getResult(data);
        if (REQUEST_NET_SUCCESS.equals(entity.getResultCode())) {
            parseData(requestCode, data);
        } else if ("999999".equals(entity.getResultCode())) {
            //强退
            DialogUtils.showOneBtnDialog(this, "提示", entity.getResultMsg(), "确定", v -> {
                DialogUtils.closeDialog();
                if (!(this instanceof LoginActivity)) {
                    UserCenter.cleanLoginInfo(getApplication());
                    Intent intent = new Intent(BaseActivity.this, LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                }
            }, false);

        }else {
            ToastUtil.show(this, entity.getResultMsg());
        }
    }

    /**
     * 请求成功后实际处理数据的方法
     */
    protected void parseData(int requestCode, String data) {
        closeProgressDialog();
    }

    @Override
    public void mistake(int requestCode, FProtocol.NetDataProtocol.ResponseStatus status, String errorMessage) {
        if (REQUEST_UPDATE_VERSION_CODE == requestCode) {
            closeProgressDialog();
        }
        ToastUtil.show(this, errorMessage);
    }

    @Override
    public void onDestroy() {
        ExitManager.instance.remove(this);
        super.onDestroy();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                View view = getCurrentFocus();
                TextUntil.hideKeyboard(ev, view, this);//调用方法判断是否需要隐藏键盘
                break;

            default:
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    public String getEText(EditText et) {
        return et.getText().toString().trim();
    }

}
