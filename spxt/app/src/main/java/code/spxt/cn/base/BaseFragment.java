package code.spxt.cn.base;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import com.common.interfaces.IActivityHelper;
import com.common.network.FProtocol;
import com.common.ui.FBaseFragment;
import com.common.utils.LogUtil;
import com.common.utils.ToastUtil;
import com.common.utils.VersionInfoUtils;

import java.util.IdentityHashMap;

import code.spxt.cn.activitys.LoginActivity;
import code.spxt.cn.common.CommonConstant;
import code.spxt.cn.common.UserCenter;
import code.spxt.cn.network.Constants;
import code.spxt.cn.network.Parsers;
import code.spxt.cn.network.entity.Entity;
import code.spxt.cn.utils.ConfigUtils;
import code.spxt.cn.utils.DialogUtils;

import static code.spxt.cn.base.BaseActivity.REQUEST_UPDATE_VERSION_CODE;


public class BaseFragment extends FBaseFragment implements IActivityHelper {

    public static final String TAG = "BaseFragment";
    private String baseTitle = "";

    public String getTitle() {
        return baseTitle;
    }

    public void setTitle(int titleId) {
        baseTitle = getString(titleId);
    }

    public void setTitle(String title) {
        baseTitle = title;
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
        postParameters.put("user_id", UserCenter.getUserId(getActivity()));
        postParameters.put("language", ConfigUtils.getPreLanguage(getActivity()));
        super.requestHttpData(path, requestCode, method, postParameters);
    }


    @Override
    public void success(int requestCode, String data) {
        closeProgressDialog();
        Entity entity = Parsers.getResult(data);
        if (CommonConstant.REQUEST_NET_SUCCESS.equals(entity.getResultCode())) {
            parseData(requestCode, data);
        } else if ("999999".equals(entity.getResultCode())) {
            DialogUtils.showOneBtnDialog(getActivity(), "提示", entity.getResultMsg(), "确定", v -> {
                DialogUtils.closeDialog();
                if (!(getActivity() instanceof LoginActivity)) {
                    UserCenter.cleanLoginInfo(getContext());
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    getActivity().finish();
                }
            }, false);
        } else {
            ToastUtil.show(getContext(), entity.getResultMsg());
        }
    }

    /**
     * 检查更新
     */
    protected void checkUpdateVersion() {
        IdentityHashMap<String, String> params = new IdentityHashMap<>();
        params.put("version_code", String.valueOf(VersionInfoUtils.getVersionCode(getActivity())));
        params.put("terminal", "10");
        requestHttpData(Constants.Urls.URL_POST_CHECK_UPDATE, REQUEST_UPDATE_VERSION_CODE, FProtocol.HttpMethod.POST, params);
    }


    /**
     * 请求成功后实际处理数据的方法
     */
    protected void parseData(int requestCode, String data) {
        closeProgressDialog();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("baseTitle", baseTitle);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            baseTitle = savedInstanceState.getString("baseTitle", getTitle());
        }
    }

    public String getEText(EditText et) {
        return et.getText().toString().trim();
    }

    public void initData() {
        LogUtil.d(TAG, "initData");
    }
}
