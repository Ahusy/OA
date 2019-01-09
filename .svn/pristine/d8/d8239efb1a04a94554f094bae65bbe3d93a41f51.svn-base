package code.spxt.cn.activitys;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.common.network.FProtocol;
import com.common.utils.ToastUtil;
import com.common.viewinject.annotation.ViewInject;

import java.util.IdentityHashMap;

import code.spxt.cn.R;
import code.spxt.cn.base.ToolBarActivity;
import code.spxt.cn.common.UserCenter;
import code.spxt.cn.network.Constants;
import code.spxt.cn.network.Parsers;
import code.spxt.cn.network.entity.Entity;
import code.spxt.cn.network.entity.UserEntity;
import code.spxt.cn.utils.DownTimeUtil;
import code.spxt.cn.utils.ViewInjectUtils;
import code.spxt.cn.view.VerifyUtils;

import static code.spxt.cn.common.CommonConstant.REQUEST_NET_ONE;
import static code.spxt.cn.common.CommonConstant.REQUEST_NET_SUCCESS;
import static code.spxt.cn.common.CommonConstant.REQUEST_NET_TWO;

/**
 * 修改密码
 */
public class ChangePasswordActivity extends ToolBarActivity implements View.OnClickListener {

    @ViewInject(R.id.change_pwd_pwd)
    private EditText pwd;
    @ViewInject(R.id.change_pwd_sure_pwd)
    private EditText surePwd;
    @ViewInject(R.id.change_pwd_phone)
    private EditText phone;
    @ViewInject(R.id.change_pwd_get_code)
    private TextView getCode;
    @ViewInject(R.id.change_pwd_code)
    private EditText code;
    @ViewInject(R.id.change_pwd_commit)
    private TextView commit;
    private DownTimeUtil downTimeUtil;

    public static void startChangePwd(Context context) {
        Intent intent = new Intent(context, ChangePasswordActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        initView();
    }

    private void initView() {
        ViewInjectUtils.inject(this);
        setCenterTitle("修改密码");
        getCode.setOnClickListener(this);
        commit.setOnClickListener(this);
        downTimeUtil = new DownTimeUtil(this);
        downTimeUtil.initCountDownTime(getCode);
        UserEntity userInfo = UserCenter.getUserInfo(this);
        if (userInfo != null) {
            String userPhone = userInfo.getUser_phone();
            if (!TextUtils.isEmpty(userPhone)) {
                phone.setText(userPhone);
            }
        }
    }

    @Override
    protected void parseData(int requestCode, String data) {
        super.parseData(requestCode, data);
        switch (requestCode) {
            case REQUEST_NET_ONE:
                downTimeUtil.countDownTimer.start();
                ToastUtil.show(this, "验证码获取成功");
                break;
            case REQUEST_NET_TWO:
                Entity result = Parsers.getResult(data);
                if (result.getResultCode().equals(REQUEST_NET_SUCCESS)) {
                    ToastUtil.show(this, result.getResultMsg());
                    finish();
                } else {
                    ToastUtil.show(this, result.getResultMsg());
                }
                break;
        }
    }

    @Override
    public void onClick(View v) {
        String pwds = pwd.getText().toString().trim();
        String surePwds = surePwd.getText().toString().trim();
        String phones = phone.getText().toString().trim();
        String codes = code.getText().toString().trim();
        switch (v.getId()) {
            case R.id.change_pwd_get_code:
                if (TextUtils.isEmpty(phones)) {
                    ToastUtil.show(this, "请输入手机号");
                } else if (!VerifyUtils.checkPhoneNumber(phones)) {
                    ToastUtil.show(this, "请输入正确的手机号码");
                } else {
                    sendMessage(phones);
                }
                break;
            case R.id.change_pwd_commit:
                if (TextUtils.isEmpty(pwds)) {
                    ToastUtil.show(this, "请输入新密码");
                } else if (TextUtils.isEmpty(surePwds)) {
                    ToastUtil.show(this, "请再次输入新密码");
                } else if (!pwds.equals(surePwds)) {
                    ToastUtil.show(this, "两次密码输入不一致,请重新输入");
                } else if (TextUtils.isEmpty(phones)) {
                    ToastUtil.show(this, "请输入手机号");
                } else if (!VerifyUtils.checkPhoneNumber(phones)) {
                    ToastUtil.show(this, "请输入正确的手机号");
                } else if (TextUtils.isEmpty(codes)) {
                    ToastUtil.show(this, "请输入验证码");
                } else {
                    // commit
                    updatePassword(phones, pwds, codes);
                }
                break;
        }
    }

    private void sendMessage(String phone) {
        showProgressDialog();
        IdentityHashMap<String, String> params = new IdentityHashMap<>();
        params.put("login_name", phone);
        requestHttpData(Constants.Urls.URL_POST_SEND_MESSAGE, REQUEST_NET_ONE, FProtocol.HttpMethod.POST, params);
    }

    private void updatePassword(String loginName, String password, String identifyCode) {
        showProgressDialog();
        IdentityHashMap<String, String> params = new IdentityHashMap<>();
        params.put("login_name", loginName);
        params.put("password", password);
        params.put("identifyCode", identifyCode);
        requestHttpData(Constants.Urls.URL_POST_UPDATE_PASSWORD, REQUEST_NET_TWO, FProtocol.HttpMethod.POST, params);
    }
}
