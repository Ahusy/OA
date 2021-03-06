package code.spxt.cn.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.common.network.FProtocol;
import com.common.viewinject.annotation.ViewInject;

import code.spxt.cn.R;
import code.spxt.cn.activitys.ChangePasswordActivity;
import code.spxt.cn.activitys.LoginActivity;
import code.spxt.cn.activitys.MyReviewActivity;
import code.spxt.cn.activitys.MySubmitActivity;
import code.spxt.cn.activitys.WaitMeHandleActivity;
import code.spxt.cn.base.BaseFragment;
import code.spxt.cn.common.UserCenter;
import code.spxt.cn.network.Constants;
import code.spxt.cn.network.Parsers;
import code.spxt.cn.network.entity.SelectCount;
import code.spxt.cn.network.entity.UserEntity;
import code.spxt.cn.utils.DialogUtils;
import code.spxt.cn.utils.ExitManager;
import code.spxt.cn.utils.ViewInjectUtils;
import code.spxt.cn.view.BadgeView;

import static code.spxt.cn.common.CommonConstant.REQUEST_NET_TWO;

/**
 * Created by dell on 2018/12/13
 */

public class CenterFragment extends BaseFragment implements View.OnClickListener {

    @ViewInject(R.id.center_name)
    private TextView name;
    @ViewInject(R.id.center_phone)
    private TextView phone;
    @ViewInject(R.id.center_department)
    private TextView department;
    @ViewInject(R.id.center_position)
    private TextView centerPosition;
    @ViewInject(R.id.center_dwsp)
    private LinearLayout dwsp;
    @ViewInject(R.id.center_wfqd)
    private LinearLayout wfqd;
    @ViewInject(R.id.center_wspd)
    private LinearLayout wspd;
    @ViewInject(R.id.center_gzrz)
    private LinearLayout gzrz;
    @ViewInject(R.id.center_change)
    private LinearLayout change;
    @ViewInject(R.id.center_img_dwsp)
    private ImageView imgDwsp;
    private BadgeView badgeView;
    @ViewInject(R.id.center_exit)
    private LinearLayout exit;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_center,container,false);
        ViewInjectUtils.inject(this,view);
        initView();
        selectCount();
        return view;
    }

    @SuppressLint("SetTextI18n")
    private void initView() {
        UserEntity userInfo = UserCenter.getUserInfo(getContext());
        if (userInfo != null){
            name.setText("姓名:"+userInfo.getUser_name());
            phone.setText("手机号:"+userInfo.getUser_phone());
            department.setText("部门:"+userInfo.getDept_name());
            centerPosition.setText("职位:"+userInfo.getRole());
        }

        badgeView = new BadgeView(getActivity(),imgDwsp);
        badgeView.setBadgePosition(BadgeView.POSITION_TOP_RIGHT);
        badgeView.setShadowLayer(0,0,0, Color.WHITE);

        dwsp.setOnClickListener(this);
        wfqd.setOnClickListener(this);
        wspd.setOnClickListener(this);
        gzrz.setOnClickListener(this);
        change.setOnClickListener(this);
        exit.setOnClickListener(this);
    }

    @Override
    protected void parseData(int requestCode, String data) {
        super.parseData(requestCode, data);
        switch (requestCode){
            case REQUEST_NET_TWO:
                SelectCount selectCount = Parsers.getSelectCount(data);
                if (selectCount.getCount() != 0){
                    badgeView.setText(String.valueOf(selectCount.getCount()));
                    badgeView.show();
                }else{
                    badgeView.hide();
                }
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.center_dwsp: // 待我审批
                startActivity(new Intent(getActivity(), WaitMeHandleActivity.class));
                break;
            case R.id.center_wfqd: // 我发起的
                MySubmitActivity.startMySubmitActivity(getActivity());
                break;
            case R.id.center_wspd: // 我审批的
                startActivity(new Intent(getActivity(), MyReviewActivity.class));
                break;
            case R.id.center_gzrz: // 工作日志
                break;
            case R.id.center_change: // 修改密码
                ChangePasswordActivity.startChangePwd(getContext());
                break;
            case R.id.center_exit:
                DialogUtils.showTwosBtnDialog(getContext(),"提示","确定退出登录吗?","确定","取消",v1 -> {
                    UserCenter.cleanLoginInfo(getContext());
                    DialogUtils.closeDialog();
                    startActivity(new Intent(getContext(), LoginActivity.class));
                    ExitManager.instance.exit();
                },v1 -> {
                    DialogUtils.closeDialog();
                });
                break;
        }
    }

    public void selectCount(){
        showProgressDialog();
        requestHttpData(Constants.Urls.URL_POST_SELECT_COUNT,REQUEST_NET_TWO, FProtocol.HttpMethod.POST,null);
    }
}
