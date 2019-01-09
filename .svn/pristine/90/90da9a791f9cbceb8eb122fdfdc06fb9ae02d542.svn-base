package code.spxt.cn.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.common.viewinject.annotation.ViewInject;

import code.spxt.cn.R;
import code.spxt.cn.activitys.BusinessActivity;
import code.spxt.cn.activitys.CardUseActivity;
import code.spxt.cn.activitys.ContractActivity;
import code.spxt.cn.activitys.OutAuditActivity;
import code.spxt.cn.activitys.TourismActivity;
import code.spxt.cn.base.BaseFragment;
import code.spxt.cn.utils.ViewInjectUtils;

/**
 * Created by dell on 2018/12/13
 */

public class CommitFragment extends BaseFragment implements View.OnClickListener {

    @ViewInject(R.id.commit_cclvf)
    private TextView cclvf;
    @ViewInject(R.id.commit_gcxmsp)
    private TextView gcxmsp;
    @ViewInject(R.id.commit_jksqd)
    private TextView jksqd;
    @ViewInject(R.id.commit_fyzfspd)
    private TextView fyzfspd;
    @ViewInject(R.id.commit_ccspd)
    private TextView ccspd;
    @ViewInject(R.id.commit_ywzdspd)
    private TextView ywzdspd;
    @ViewInject(R.id.commit_htsp)
    private TextView htsp;
    @ViewInject(R.id.commit_rkd)
    private TextView rkd;
    @ViewInject(R.id.commit_ckd)
    private TextView ckd;
    @ViewInject(R.id.commit_gdzcysd)
    private TextView gdzcysd;
    @ViewInject(R.id.commit_dzyhpysd)
    private TextView dzyhpysd;
    @ViewInject(R.id.commit_gcsysqd)
    private TextView gcsysqd;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_commit,container,false);
        ViewInjectUtils.inject(this,view);
        initView();
        return view;
    }

    private void initView() {
        ywzdspd.setOnClickListener(this);
        htsp.setOnClickListener(this);
        ccspd.setOnClickListener(this);
        gcsysqd.setOnClickListener(this);
        cclvf.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.commit_ywzdspd: // 业务招待审批单
                BusinessActivity.startBusinessActivity(getActivity());
                break;
            case R.id.commit_htsp:  // 合同审批
                ContractActivity.startContractActivity(getActivity());
                break;
            case R.id.commit_ccspd: // 出差审批单
                OutAuditActivity.startOutAuditActivity(getActivity());
                break;
            case R.id.commit_gcsysqd: // 公车使用申请单
                CardUseActivity.startCardUseActivity(getActivity());
                break;
            case R.id.commit_cclvf:  // 出差旅游费报销单
                TourismActivity.startTourismActivity(getActivity());
                break;
        }
    }
}
