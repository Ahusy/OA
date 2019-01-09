package code.spxt.cn.activitys;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.common.network.FProtocol;
import com.common.utils.ToastUtil;
import com.common.viewinject.annotation.ViewInject;

import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;

import code.spxt.cn.R;
import code.spxt.cn.adapters.StepsOneCopyAdapter;
import code.spxt.cn.adapters.StepsThreeCopyAdapter;
import code.spxt.cn.adapters.StepsTwoCopyAdapter;
import code.spxt.cn.base.ToolBarActivity;
import code.spxt.cn.common.UserCenter;
import code.spxt.cn.network.Constants;
import code.spxt.cn.network.Parsers;
import code.spxt.cn.network.entity.AddressEntity;
import code.spxt.cn.network.entity.ApprovalFlowItem;
import code.spxt.cn.network.entity.BillTourismMoneyDetail;
import code.spxt.cn.network.entity.ChooseEntity;
import code.spxt.cn.network.entity.EditTourism;
import code.spxt.cn.network.entity.PhotoCopyEntity;
import code.spxt.cn.network.entity.StepsTwoEntity;
import code.spxt.cn.utils.DateUtils;
import code.spxt.cn.utils.DialogUtils;
import code.spxt.cn.utils.TimeUtil;
import code.spxt.cn.utils.ViewInjectUtils;
import code.spxt.cn.view.ApprovalFlowView;

import static code.spxt.cn.common.CommonConstant.REQUEST_NET_ONE;
import static code.spxt.cn.common.CommonConstant.REQUEST_NET_THREE;
import static code.spxt.cn.common.CommonConstant.REQUEST_NET_TWO;
import static code.spxt.cn.common.CommonConstant.STATUS_ONE;
import static code.spxt.cn.common.CommonConstant.STATUS_THREE;
import static code.spxt.cn.common.CommonConstant.STATUS_TWO;

public class ApprovalBillTourismMoneyActivity extends ToolBarActivity implements View.OnClickListener {
    public static final String FLOW_ID = "flowID";
    public static final String STATUS = "status";
    public static final String TYPE = "type";
    public static final String NAME = "name";
    public static final String FLOWNO = "flowNo";
    @ViewInject(R.id.fl_layout)
    FrameLayout flLayout;
    @ViewInject(R.id.tv_no)
    TextView tvNo;
    @ViewInject(R.id.tv_name)
    TextView tvName;
    @ViewInject(R.id.tv_position)
    TextView tvPositon;
    @ViewInject(R.id.rv_address)
    RecyclerView rv_address;
    @ViewInject(R.id.tv_out_desc)
    TextView tvOutDesc;
    @ViewInject(R.id.rv_money)
    RecyclerView rv_money;
    @ViewInject(R.id.tv_min_money)
    TextView tvMinMoney;

    @ViewInject(R.id.tv_max_money)
    TextView tvMaxMoney;
    @ViewInject(R.id.rv_invoice)
    RecyclerView rv_invoice;
    @ViewInject(R.id.afv_view)
    ApprovalFlowView afvView;
    @ViewInject(R.id.tv_start_time)
    TextView tvStartTime;
    @ViewInject(R.id.tv_end_time)
    TextView tvEndTime;
    @ViewInject(R.id.tv_status)
    TextView tvStatus;

    private String type;
    private int flowID;
    private String flowNo;
    private int status;
    private BillTourismMoneyDetail bpcDetail;
    private int processId;
    private TextView tvAdopt;
    private TextView tvReject;
    private String toast;

    private StepsTwoCopyAdapter adapter;
    private StepsOneCopyAdapter adapterOne;
    private StepsThreeCopyAdapter adapterThree;
    private ArrayList<ChooseEntity> ChooseList = new ArrayList<>();
    private ArrayList<AddressEntity> listAddress = new ArrayList<>();
    private ArrayList<PhotoCopyEntity> entities = new ArrayList<>();

    public static void startAct(Context context, int flowID, int status, String flowNo, String Type) {
        Intent intent = new Intent(context, ApprovalBillTourismMoneyActivity.class);
        intent.putExtra(TYPE, Type);
        intent.putExtra(FLOWNO, flowNo);
        intent.putExtra(FLOW_ID, flowID);
        intent.putExtra(STATUS, status);
        context.startActivity(intent);
    }

    public static void startAct(Context context, int flowID, int status, String flowNo, String name, String Type) {
        Intent intent = new Intent(context, ApprovalBillTourismMoneyActivity.class);
        intent.putExtra(TYPE, Type);
        intent.putExtra(FLOW_ID, flowID);
        intent.putExtra(STATUS, status);
        intent.putExtra(FLOWNO, flowNo);
        intent.putExtra(NAME, name);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approval_bill_tourism_money);
        ViewInjectUtils.inject(this);
        initView();
    }

    private void initView() {
        setCenterTitle("差旅报销单");
        type = getIntent().getStringExtra(TYPE);
        flowID = getIntent().getIntExtra(FLOW_ID, 0);
        status = getIntent().getIntExtra(STATUS, 0);
        flowNo = getIntent().getStringExtra(FLOWNO);
        if (STATUS_ONE.equals(type)) {
            View view = LayoutInflater.from(getApplication()).inflate(R.layout.layout_approval_bill_one, null);
            tvAdopt = view.findViewById(R.id.tv_adopt);
            tvReject = view.findViewById(R.id.tv_reject);
            tvAdopt.setOnClickListener(this);
            tvReject.setOnClickListener(this);
            flLayout.addView(view);
        } else if (STATUS_TWO.equals(type)) {
            View view = LayoutInflater.from(getApplication()).inflate(R.layout.layout_approval_bill_two, null);
            TextView tvCancel = view.findViewById(R.id.tv_cancel);
            TextView tvEdit = view.findViewById(R.id.tv_edit);
            if (status == 0) {
                //待审核
                flLayout.setVisibility(View.VISIBLE);
                tvCancel.setVisibility(View.VISIBLE);
            } else if (status == 1 || status == 2 || 4 == status) {
                flLayout.setVisibility(View.GONE);
            } else if (status == 3) {
                flLayout.setVisibility(View.VISIBLE);
            }
            tvCancel.setOnClickListener(this);
            tvEdit.setOnClickListener(this);
            flLayout.addView(view);
        } else if (STATUS_THREE.equals(type)) {
            flLayout.setVisibility(View.GONE);
        }

        if (0 == status) {
            //待审批
            tvStatus.setText("待审批");
        } else if (1 == status) {
            //审批中
            tvStatus.setText("审批中");
        } else if (2 == status) {
            //完成
            tvStatus.setText("已完成");
        } else if (3 == status) {
            //已驳回
            tvStatus.setText("已驳回");
        } else if (4 == status) {
            //已取消
            tvStatus.setText("已撤回");
        }

        adapter = new StepsTwoCopyAdapter(ChooseList, this);
        rv_money.setLayoutManager(new LinearLayoutManager(this));
        rv_money.setAdapter(adapter);


        adapterOne = new StepsOneCopyAdapter(listAddress);
        rv_address.setLayoutManager(new LinearLayoutManager(this));
        rv_address.setAdapter(adapterOne);

        adapterThree = new StepsThreeCopyAdapter(this, entities);
        rv_invoice.setLayoutManager(new LinearLayoutManager(this));
        rv_invoice.setAdapter(adapterThree);

        requestData(flowID, flowNo);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_adopt:
                //审批通过
                toast = "审批通过";
                DialogUtils.showApprovalDialog(this, 0, value -> {
                    requestApproval(value, "1");
                });
                break;
            case R.id.tv_reject:
                //驳回
                toast = "审批驳回";
                DialogUtils.showApprovalDialog(this, 1, value -> {
                    requestApproval(value, "2");
                });
                break;
            case R.id.tv_cancel:
                requestCancel(1, flowID);
                break;
            case R.id.tv_edit:
                EditTourism et = new EditTourism();
                et.setFlowId(flowID);
                et.setFlowNo(flowNo);
                et.setPhotos(bpcDetail.getPicList());
                et.setAddressList(listAddress);
                String timeOne = TimeUtil.formatTime(bpcDetail.getStartTime(), TimeUtil.TIME_FORMAT_TWO);
                et.setStartTime(timeOne);
                String timeTwo = TimeUtil.formatTime(bpcDetail.getStartTime(), TimeUtil.TIME_FORMAT_TWO);
                et.setEndTime(timeTwo);
                et.setDesc(bpcDetail.getTravel_reason());
                et.setTotal(bpcDetail.getTotal());
                et.setCapitalTotal(bpcDetail.getCapital_total());
                TourismActivity.startAct(this, et);
                finish();
                break;
        }

    }

    @Override
    protected void parseData(int requestCode, String data) {
        super.parseData(requestCode, data);
        switch (requestCode) {
            case REQUEST_NET_ONE:
                bpcDetail = Parsers.getBillTourismDetail(data);
                ArrayList<ApprovalFlowItem> afiList = Parsers.getAFIList(data);
                for (ApprovalFlowItem afi : afiList) {
                    if (afi.getType() == 1) {
                        processId = afi.getProcessId();
                    }
                }
                updataUI(bpcDetail, afiList);
                break;
            case REQUEST_NET_THREE:
                ToastUtil.show(getApplication(), "取消成功");
                finish();
                break;
            case REQUEST_NET_TWO:
                ToastUtil.show(getApplication(), toast);
                finish();
                break;
        }
    }

    private void requestApproval(String value, String status) {
        showProgressDialog();
        IdentityHashMap<String, String> map = new IdentityHashMap<>();
        map.put("flow_id", String.valueOf(flowID));
        map.put("wf_id", "1");
        map.put("status", status);
        map.put("mark", value);
        map.put("process_id", String.valueOf(processId));
        requestHttpData(Constants.Urls.URL_POST_AUDIT, REQUEST_NET_TWO, FProtocol.HttpMethod.POST, map);
    }

    private void requestCancel(int wfID, int id) {
        showProgressDialog();
        IdentityHashMap<String, String> map = new IdentityHashMap<>();
        map.put("wf_id", String.valueOf(wfID));
        map.put("flow_id", String.valueOf(id));
        requestHttpData(Constants.Urls.URL_POST_GOBACKFORAPPROVAL, REQUEST_NET_THREE, FProtocol.HttpMethod.POST, map);
    }


    private void requestData(int flowID, String flowNo) {
        showProgressDialog();
        IdentityHashMap<String, String> map = new IdentityHashMap<>();
        map.put("flow_no", String.valueOf(flowNo));
        map.put("flow_id", String.valueOf(flowID));
        requestHttpData(Constants.Urls.URL_POST_TRAVELCOSTAPPROVALDETAIL, REQUEST_NET_ONE, FProtocol.HttpMethod.POST, map);
    }

    private void updataUI(BillTourismMoneyDetail bd, ArrayList<ApprovalFlowItem> afiList) {
        if (STATUS_TWO.equals(type)) {
            String userName = UserCenter.getUserInfo(this).getUser_name();
            tvName.setText(userName);
        } else {
            String name = getIntent().getStringExtra(NAME);
            tvName.setText(name);
        }
        tvNo.setText(flowNo);
        tvMaxMoney.setText(bd.getCapital_total());
        tvMinMoney.setText(String.valueOf(bd.getTotal()));
        tvOutDesc.setText(bd.getTravel_reason());
        tvPositon.setText(bd.getRole());
        String timeOne = TimeUtil.formatTime(bd.getStartTime(), TimeUtil.TIME_FORMAT_TWO);
        tvStartTime.setText(timeOne);
        String timeTwo = TimeUtil.formatTime(bd.getEndTime(), TimeUtil.TIME_FORMAT_TWO);
        tvEndTime.setText(timeTwo);

        List<StepsTwoEntity.ItemsListBean> itemsList = bd.getMoneyList();
        if (itemsList != null && itemsList.size() > 0) {
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

        String travel_place = bd.getTravel_place();
        String[] split = travel_place.split(",");
        for (String address : split) {
            if (!TextUtils.isEmpty(address)) {
                AddressEntity ae = new AddressEntity();
                ae.setAddress(address);
                listAddress.add(ae);
            }
        }
        adapterOne.notifyDataSetChanged();

        ArrayList<PhotoCopyEntity> picList = bd.getPicList();
        entities.addAll(picList);
        adapterThree.notifyDataSetChanged();


        ArrayList<ApprovalFlowView.Text> texts = new ArrayList<>();
        for (ApprovalFlowItem afi : afiList) {
            ApprovalFlowView.Text text = new ApprovalFlowView.Text();
            int type = afi.getType();
            if (type == 2) {
                long createDate = afi.getCreateDate();
                String time = TimeUtil.formatTime(createDate, TimeUtil.TIME_FORMAT_FOUR);
                text.setTime(time);
                int status = afi.getStatus();
                text.setTag(1 == status ? "已通过" : "已驳回");
                text.setName(afi.getOperateUserName());
            } else if (type == 1) {
                text.setName(afi.getOperateUserName());
                text.setTag("待审批");
            } else if (type == 0) {
                text.setName(afi.getOperateUserName());
            }
            texts.add(text);
        }
        afvView.setData(texts, false);
    }
}
