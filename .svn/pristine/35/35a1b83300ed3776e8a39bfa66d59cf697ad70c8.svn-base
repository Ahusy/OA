package code.spxt.cn.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.common.network.FProtocol;
import com.common.utils.ToastUtil;
import com.common.viewinject.annotation.ViewInject;

import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;

import code.spxt.cn.R;
import code.spxt.cn.adapters.StepsTwoAdapter;
import code.spxt.cn.base.BaseFragment;
import code.spxt.cn.network.Constants;
import code.spxt.cn.network.Parsers;
import code.spxt.cn.network.entity.ChooseEntity;
import code.spxt.cn.network.entity.Entity;
import code.spxt.cn.network.entity.StepsTwoEntity;
import code.spxt.cn.utils.DateUtils;
import code.spxt.cn.utils.DialogUtils;
import code.spxt.cn.utils.ViewInjectUtils;

import static code.spxt.cn.common.CommonConstant.REQUEST_NET_ONE;
import static code.spxt.cn.common.CommonConstant.REQUEST_NET_SUCCESS;
import static code.spxt.cn.common.CommonConstant.REQUEST_NET_TWO;

/**
 * 出差旅费步骤二
 */

public class StepsTwoFragment extends BaseFragment implements View.OnClickListener {

    @ViewInject(R.id.spes_two_btn)
    private FloatingActionButton btn;
    @ViewInject(R.id.seps_two_recycler)
    private RecyclerView recyclerView;
    private ArrayList<ChooseEntity> ChooseList = new ArrayList<>();
    private StepsTwoAdapter adapter;
    @ViewInject(R.id.text12)
    private TextView textView;
    private String flowId;
    private String flowNo;
    private LocalBroadcastManager instance;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_seps_two, container, false);
        ViewInjectUtils.inject(this, view);
        initView();
        registerReceiver();
        return view;
    }

    private void initView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        btn.setOnClickListener(this);
        adapter = new StepsTwoAdapter(ChooseList, getActivity());
        recyclerView.setAdapter(adapter);
        textView.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        Bundle bundle = getArguments();
//        flowId = bundle.getString("flow_id");
        assert bundle != null;
        flowNo = bundle.getString("flow_no");
        selectItemsTwo();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.spes_two_btn:
                DialogUtils.showChooseDialog(getActivity(), list -> {
                    ChooseList.addAll(list);
                    adapter.notifyDataSetChanged();
                });
                break;
        }
    }

    @Override
    protected void parseData(int requestCode, String data) {
        super.parseData(requestCode, data);
        switch (requestCode){
            case REQUEST_NET_ONE:
                StepsTwoEntity stepsTwo = Parsers.getStepsTwo(data);
                if (stepsTwo != null){
                    ChooseList.clear();
                    List<StepsTwoEntity.ItemsListBean> itemsList = stepsTwo.getItemsList();
                    if (itemsList != null && itemsList.size() > 0){
                        for (int i = 0; i < itemsList.size(); i++) {
                            StepsTwoEntity.ItemsListBean listBean = itemsList.get(i);
                            String type = listBean.getType();
                            ChooseEntity chooseEntity = new ChooseEntity(Integer.parseInt(type));
                            String startTime = DateUtils.getDateToString(listBean.getStartTime());
                            String endTime = DateUtils.getDateToString(listBean.getEndTime());
                            chooseEntity.setStartTime(startTime);
                            chooseEntity.setEndTime(endTime);
                            chooseEntity.setPeopleNum(listBean.getTravelPlace());
                            chooseEntity.setMoney(String.valueOf(listBean.getAmount()));
                            chooseEntity.setDays(listBean.getDays());
                            chooseEntity.setStandard(listBean.getStandard());
                            chooseEntity.setBars(listBean.getVehicle());
                            chooseEntity.setCostsThat(listBean.getMark());
                            ChooseList.add(chooseEntity);
                            adapter.notifyDataSetChanged();

                        }
                    }
                }
                break;
            case REQUEST_NET_TWO:
                Entity result = Parsers.getResult(data);
                if (result.getResultCode().equals(REQUEST_NET_SUCCESS)){
                    ChooseList.clear();
                    adapter.notifyDataSetChanged();
                    ToastUtil.show(getContext(),result.getResultMsg());
                }else{
                    ToastUtil.show(getContext(),result.getResultMsg());
                }
                break;
        }
    }

    // 注册广播接收器
    private void registerReceiver() {
        instance = LocalBroadcastManager.getInstance(getActivity());
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("clear");
        instance.registerReceiver(mAdDownLoadReceiver, intentFilter);
    }

    private BroadcastReceiver mAdDownLoadReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String change = intent.getStringExtra("change");
            if ("two".equals(change)) {
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        deleteTravelCostItems(flowNo);
                    }
                });
            }
        }
    };

    // 注销
    @Override
    public void onDetach() {
        super.onDetach();
        instance.unregisterReceiver(mAdDownLoadReceiver);
    }


    private void selectItemsTwo() {
        showProgressDialog();
        IdentityHashMap<String,String> params = new IdentityHashMap<>();
        params.put("flow_no",flowNo);
        requestHttpData(Constants.Urls.URL_POST_SELECT_TWO, REQUEST_NET_ONE, FProtocol.HttpMethod.POST, params);
    }

    private void deleteTravelCostItems(String flowNo) {
        showProgressDialog();
        IdentityHashMap<String,String> params = new IdentityHashMap<>();
        params.put("flow_no",flowNo);
        requestHttpData(Constants.Urls.URL_POST_CLEAR_TWO, REQUEST_NET_TWO, FProtocol.HttpMethod.POST, params);
    }

    public ArrayList<ChooseEntity> twoList() {
        if (ChooseList.size() == 0){
            ToastUtil.show(getContext(),"请添加费用明细");
            return null;
        }
        for (int i = 0; i < ChooseList.size(); i++) {
            ChooseEntity list = ChooseList.get(i);
            int type = list.getType();
            switch (type) {
                case 1:
                    if (TextUtils.isEmpty(list.getPeopleNum())) {
                        ToastUtil.show(getContext(), "请输入车船费所需的人数");
                        return null;
                    }
                    if (TextUtils.isEmpty(list.getStartTime())) {
                        ToastUtil.show(getContext(), "请输入车船费下的开始时间");
                        return null;
                    }
                    if (TextUtils.isEmpty(list.getEndTime())) {
                        ToastUtil.show(getContext(), "请输入车船费下的结束时间");
                        return null;
                    }
                    if (TextUtils.isEmpty(list.getBars())) {
                        ToastUtil.show(getContext(), "请输入车船费下的交通工具");
                        return null;
                    }
                    if (TextUtils.isEmpty(list.getMoney())) {
                        ToastUtil.show(getContext(), "请输入车船费下的金额");
                        return null;
                    }
                    break;
                case 2:
                    if (TextUtils.isEmpty(list.getPeopleNum())) {
                        ToastUtil.show(getContext(), "请输入住宿费所需的人数");
                        return null;
                    }
                    if (TextUtils.isEmpty(list.getStartTime())) {
                        ToastUtil.show(getContext(), "请输入住宿费下的开始时间");
                        return null;
                    }
                    if (TextUtils.isEmpty(list.getEndTime())) {
                        ToastUtil.show(getContext(), "请输入住宿费下的结束时间");
                        return null;
                    }
                    if (TextUtils.isEmpty(list.getMoney())) {
                        ToastUtil.show(getContext(), "请输入住宿费下的金额");
                        return null;
                    }
                    break;
                case 3:
                    if (TextUtils.isEmpty(list.getPeopleNum())) {
                        ToastUtil.show(getContext(), "请输入培训费所需的人数");
                        return null;
                    }
                    if (TextUtils.isEmpty(list.getStartTime())) {
                        ToastUtil.show(getContext(), "请输入培训费下的开始时间");
                        return null;
                    }
                    if (TextUtils.isEmpty(list.getEndTime())) {
                        ToastUtil.show(getContext(), "请输入培训费下的结束时间");
                        return null;
                    }
                    if (TextUtils.isEmpty(list.getMoney())) {
                        ToastUtil.show(getContext(), "请输入培训费下的金额");
                        return null;
                    }
                    break;
                case 5:
                    if (TextUtils.isEmpty(list.getPeopleNum())) {
                        ToastUtil.show(getContext(), "请输入会议费所需的人数");
                        return null;
                    }
                    if (TextUtils.isEmpty(list.getStartTime())) {
                        ToastUtil.show(getContext(), "请输入会议费下的开始时间");
                        return null;
                    }
                    if (TextUtils.isEmpty(list.getEndTime())) {
                        ToastUtil.show(getContext(), "请输入会议费下的结束时间");
                        return null;
                    }
                    if (TextUtils.isEmpty(list.getMoney())) {
                        ToastUtil.show(getContext(), "请输入会议费下的金额");
                        return null;
                    }
                    break;
                case 4:
                    if (TextUtils.isEmpty(list.getPeopleNum())) {
                        ToastUtil.show(getContext(), "请输入其他费用所需的人数");
                        return null;
                    }
                    if (TextUtils.isEmpty(list.getStartTime())) {
                        ToastUtil.show(getContext(), "请输入其他费用下的开始时间");
                        return null;
                    }
                    if (TextUtils.isEmpty(list.getEndTime())) {
                        ToastUtil.show(getContext(), "请输入其他费用下的结束时间");
                        return null;
                    }
                    if (TextUtils.isEmpty(list.getMoney())) {
                        ToastUtil.show(getContext(), "请输入其他费用下的金额");
                        return null;
                    }
                    if (TextUtils.isEmpty(list.getCostsThat())) {
                        ToastUtil.show(getContext(), "请输入其他费用下的费用说明");
                        return null;
                    }
                    break;
                case 6:
                    if (TextUtils.isEmpty(list.getStartTime())) {
                        ToastUtil.show(getContext(), "请输入出差补助下的开始时间");
                        return null;
                    }
                    if (TextUtils.isEmpty(list.getEndTime())) {
                        ToastUtil.show(getContext(), "请输入出差补助下的开始时间");
                        return null;
                    }
                    if (TextUtils.isEmpty(list.getDays())) {
                        ToastUtil.show(getContext(), "请输入出差补助下的出差天数");
                        return null;
                    }
                    if (TextUtils.isEmpty(list.getMoney())) {
                        ToastUtil.show(getContext(), "请输入出差补助下的金额");
                        return null;
                    }
                case 7:
                    if (TextUtils.isEmpty(list.getStartTime())) {
                        ToastUtil.show(getContext(), "请输入伙食补助下的开始时间");
                        return null;
                    }
                    if (TextUtils.isEmpty(list.getEndTime())) {
                        ToastUtil.show(getContext(), "请输入伙食补助下的开始时间");
                        return null;
                    }
                    if (TextUtils.isEmpty(list.getDays())) {
                        ToastUtil.show(getContext(), "请输入伙食补助下的出差天数");
                        return null;
                    }
                    if (TextUtils.isEmpty(list.getMoney())) {
                        ToastUtil.show(getContext(), "请输入伙食补助下的金额");
                        return null;
                    }
                    break;
            }

        }
        return ChooseList;
    }

}
