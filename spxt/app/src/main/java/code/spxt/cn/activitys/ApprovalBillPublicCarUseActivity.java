package code.spxt.cn.activitys;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.common.network.FProtocol;
import com.common.utils.ToastUtil;
import com.common.viewinject.annotation.ViewInject;

import java.util.ArrayList;
import java.util.IdentityHashMap;

import code.spxt.cn.R;
import code.spxt.cn.base.ToolBarActivity;
import code.spxt.cn.common.UserCenter;
import code.spxt.cn.network.Constants;
import code.spxt.cn.network.Parsers;
import code.spxt.cn.network.entity.ApprovalFlowItem;
import code.spxt.cn.network.entity.BillPublicCarDetail;
import code.spxt.cn.utils.DialogUtils;
import code.spxt.cn.utils.TimeUtil;
import code.spxt.cn.utils.ViewInjectUtils;
import code.spxt.cn.view.ApprovalFlowView;

import static code.spxt.cn.common.CommonConstant.REQUEST_ACT_ONE;
import static code.spxt.cn.common.CommonConstant.REQUEST_NET_ONE;
import static code.spxt.cn.common.CommonConstant.REQUEST_NET_THREE;
import static code.spxt.cn.common.CommonConstant.REQUEST_NET_TWO;
import static code.spxt.cn.common.CommonConstant.STATUS_ONE;
import static code.spxt.cn.common.CommonConstant.STATUS_THREE;
import static code.spxt.cn.common.CommonConstant.STATUS_TWO;

public class ApprovalBillPublicCarUseActivity extends ToolBarActivity implements View.OnClickListener {
    public static final String FLOW_ID = "flowID";
    public static final String STATUS = "status";
    public static final String TYPE = "type";
    public static final String NAME = "name";
    @ViewInject(R.id.fl_layout)
    FrameLayout flLayout;

    @ViewInject(R.id.tv_name)
    TextView tvName;
    @ViewInject(R.id.tv_time)
    TextView tvTime;
    @ViewInject(R.id.tv_car_type)
    TextView tvCarType;
    @ViewInject(R.id.tv_card_number)
    TextView tvCardNumber;
    @ViewInject(R.id.tv_out_desc)
    TextView tvOutDesc;
    @ViewInject(R.id.tv_end_address)
    TextView tvEndAddress;
    @ViewInject(R.id.tv_other_p)
    TextView tvOtherP;
    @ViewInject(R.id.tv_car_p)
    TextView tvCarP;
    @ViewInject(R.id.afv_view)
    ApprovalFlowView afvView;

    private String type;
    private int flowID;
    private int status;
    private int processId;
    private TextView tvAdopt;
    private TextView tvReject;
    private BillPublicCarDetail bpcDetail;

    private String toast;

    public static void startAct(Context context, int flowID, int status, String Type) {
        Intent intent = new Intent(context, ApprovalBillPublicCarUseActivity.class);
        intent.putExtra(TYPE, Type);
        intent.putExtra(FLOW_ID, flowID);
        intent.putExtra(STATUS, status);
        context.startActivity(intent);
    }


    public static void startAct(Context context, int flowID, int status, String name, String Type) {
        Intent intent = new Intent(context, ApprovalBillPublicCarUseActivity.class);
        intent.putExtra(TYPE, Type);
        intent.putExtra(FLOW_ID, flowID);
        intent.putExtra(STATUS, status);
        intent.putExtra(NAME, name);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approval_bill_public_car_use);
        ViewInjectUtils.inject(this);
        initView();
    }

    private void initView() {
        setCenterTitle("公车使用审批单");
        type = getIntent().getStringExtra(TYPE);
        flowID = getIntent().getIntExtra(FLOW_ID, 0);
        status = getIntent().getIntExtra(STATUS, 0);
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
        requestData(flowID);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_cancel:
                requestCancel(bpcDetail.getWfId(), bpcDetail.getFlowId());
                break;
            case R.id.tv_edit:
                CardUseActivity.startAct(this, bpcDetail, REQUEST_ACT_ONE);
                finish();
                break;
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
        }
    }

    @Override
    protected void parseData(int requestCode, String data) {
        super.parseData(requestCode, data);
        switch (requestCode) {
            case REQUEST_NET_ONE:
                bpcDetail = Parsers.getBillPublicCarUser(data);
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


    private void requestData(int flowID) {
        showProgressDialog();
        IdentityHashMap<String, String> map = new IdentityHashMap<>();
        map.put("flow_id", String.valueOf(flowID));
        requestHttpData(Constants.Urls.URL_POST_BUSAPPROVALDETAILS, REQUEST_NET_ONE, FProtocol.HttpMethod.POST, map);
    }

    private void requestApproval(String value, String status) {
        showProgressDialog();
        IdentityHashMap<String, String> map = new IdentityHashMap<>();
        map.put("flow_id", String.valueOf(flowID));
        map.put("wf_id", String.valueOf(bpcDetail.getWfId()));
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

    private void updataUI(BillPublicCarDetail bd, ArrayList<ApprovalFlowItem> afiList) {
        if (STATUS_TWO.equals(type)) {
            String userName = UserCenter.getUserInfo(this).getUser_name();
            tvName.setText(userName);
        } else {
            String name = getIntent().getStringExtra(NAME);
            tvName.setText(name);
        }
        String applyTime = TimeUtil.formatTime(bd.getApplyTime(), TimeUtil.TIME_FORMAT_ONE);
        tvTime.setText(applyTime);
        tvCarType.setText(bd.getCarType());
        tvCardNumber.setText(bd.getPlateNumber());
        tvOutDesc.setText(bd.getReason());
        tvEndAddress.setText(bd.getDestination());
        tvOtherP.setText(bd.getTogether());
        tvCarP.setText(bd.getDriver());
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
