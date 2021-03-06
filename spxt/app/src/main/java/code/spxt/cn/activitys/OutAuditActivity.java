package code.spxt.cn.activitys;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.common.network.FProtocol;
import com.common.utils.ToastUtil;
import com.common.viewinject.annotation.ViewInject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.IdentityHashMap;
import java.util.List;

import code.spxt.cn.R;
import code.spxt.cn.base.ToolBarActivity;
import code.spxt.cn.common.UserCenter;
import code.spxt.cn.network.Constants;
import code.spxt.cn.network.Parsers;
import code.spxt.cn.network.entity.BillOutWorkDetail;
import code.spxt.cn.network.entity.Entity;
import code.spxt.cn.utils.DateUtils;
import code.spxt.cn.utils.DialogUtils;
import code.spxt.cn.utils.TimeUtil;
import code.spxt.cn.utils.ViewInjectUtils;

import static code.spxt.cn.common.CommonConstant.EXTRA_OBJECT;
import static code.spxt.cn.common.CommonConstant.REQUEST_NET_ONE;
import static code.spxt.cn.common.CommonConstant.REQUEST_NET_SUCCESS;

/**
 * 出差审批单
 */
public class OutAuditActivity extends ToolBarActivity implements View.OnClickListener {

    @ViewInject(R.id.audit_username)
    private TextView userName;
    @ViewInject(R.id.audit_time)
    private TextView time;
    @ViewInject(R.id.audit_choose_end_time)
    private TextView endTime;
    @ViewInject(R.id.audit_reason)
    private EditText reason;
    @ViewInject(R.id.audit_choose_start_time)
    private TextView startTime;
    @ViewInject(R.id.audit_days)
    private TextView days;
    @ViewInject(R.id.audit_purpose_address)
    private TextView pruposeAddress;
    @ViewInject(R.id.audit_via_address)
    private TextView viaAddress;
    @ViewInject(R.id.audit_distant)
    private TextView distant;
    @ViewInject(R.id.audit_check_train)
    private CheckBox cbTrain;
    @ViewInject(R.id.audit_check_plane)
    private CheckBox cbPlane;
    @ViewInject(R.id.audit_check_ship)
    private CheckBox cbShip;
    @ViewInject(R.id.audit_check_other)
    private CheckBox cbOther;
    @ViewInject(R.id.audit_commit)
    private TextView commit;
    private Date startDate;
    private List<CheckBox> checkBoxList;
    private String flowNo;
    private String flow_id;
    private long startLong;

    public static void startOutAuditActivity(Context context) {
        Intent intent = new Intent(context, OutAuditActivity.class);
        context.startActivity(intent);
    }

    public static void startAct(Activity activity, BillOutWorkDetail ba, int requestCode) {
        Intent intent = new Intent(activity, OutAuditActivity.class);
        intent.putExtra(EXTRA_OBJECT, ba);
        activity.startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_out_audit);
        initView();
    }

    private void initView() {
        ViewInjectUtils.inject(this);
        setCenterTitle("出差审批单");
        checkBoxList = new ArrayList<>();
        checkBoxList.add(cbTrain);
        checkBoxList.add(cbPlane);
        checkBoxList.add(cbShip);
        checkBoxList.add(cbOther);
        BillOutWorkDetail ab = getIntent().getParcelableExtra(EXTRA_OBJECT);
        if (ab != null) {
            startTime.setText(TimeUtil.formatTime(ab.getSetOffTime(), "yyyy-MM-dd HH:mm"));
            pruposeAddress.setText(ab.getDestination());
            viaAddress.setText(ab.getWay());
            reason.setText(ab.getTravelReason());
            int flowId = ab.getFlowId();
            flow_id = String.valueOf(flowId);
            flowNo = ab.getFlowNo();
            endTime.setText(TimeUtil.formatTime(ab.getReturnTime(), "yyyy-MM-dd HH:mm"));
            for (CheckBox cb : checkBoxList) {
                String transportation = ab.getTransportation();
                cb.setChecked(transportation.contains(cb.getText()));
            }

        }

        startTime.setOnClickListener(this);
        pruposeAddress.setOnClickListener(this);
        viaAddress.setOnClickListener(this);
        commit.setOnClickListener(this);
        endTime.setOnClickListener(this);

        String currentTimeToday = DateUtils.getCurrentTime();
        time.setText(currentTimeToday);
        userName.setText(UserCenter.getUserInfo(this).getUser_name());


    }

    @Override
    protected void parseData(int requestCode, String data) {
        super.parseData(requestCode, data);
        switch (requestCode) {
            case REQUEST_NET_ONE:
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
        switch (v.getId()) {
            case R.id.audit_choose_start_time: // 出差时间
                DialogUtils.showOnlyDatePicker(this, date -> {
                    startLong = DateUtils.getLong(date);
                    startTime.setText(date);
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    try {
                        startDate = format.parse(date);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
                break;
            case R.id.audit_choose_end_time: // 截止时间
                if (!startTime.getText().toString().equals("")) {
                    DialogUtils.showOnlyDatePicker(this, date -> {
                        long endLong = DateUtils.getLong(date);
                        if (endLong < startLong) {
                            ToastUtil.show(this, "结束时间不能小于开始时间");
                            DialogUtils.closeDialog();
                        } else {
                            endTime.setText(date);
                            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                            try {
                                Date endDate = format.parse(date);
                                int day = DateUtils.differentDaysByMillisecond(startDate, endDate);
                                days.setText("共计 " + day + " 天");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                } else {
                    ToastUtil.show(this, "请先选择开始日期");
                }

                break;
            case R.id.audit_purpose_address: // 目的地
                DialogUtils.showAddressDialog(this, 0, (addressName, position) -> {
                    pruposeAddress.setText(addressName);
                });
                break;
            case R.id.audit_via_address: // 途经地
                DialogUtils.showAddressDialog(this, 0, (addressName, position) -> {
                    viaAddress.setText(addressName);
                });
                break;
            case R.id.audit_commit:
                String reasons = reason.getText().toString().trim();
                String chooseTimes = startTime.getText().toString().trim();
                String endTime = this.endTime.getText().toString().trim();
                String prupsAddress = pruposeAddress.getText().toString().trim();
                String viaAddres = viaAddress.getText().toString().trim();
                if (TextUtils.isEmpty(reasons)) {
                    ToastUtil.show(this, "请输入出差事由");
                } else if (TextUtils.isEmpty(chooseTimes)) {
                    ToastUtil.show(this, "请选开始日期");
                } else if (TextUtils.isEmpty(endTime)) {
                    ToastUtil.show(this, "请选择截止日期");
                } else if (TextUtils.isEmpty(prupsAddress)) {
                    ToastUtil.show(this, "请选择出差目的地");
//                } else if (TextUtils.isEmpty(viaAddres)) {
//                    ToastUtil.show(this, "请选择出差途经地");
                } else {
                    StringBuffer buffer = new StringBuffer();
                    for (int i = 0; i < checkBoxList.size(); i++) {
                        if (checkBoxList.get(i).isChecked()) {
                            if (i == checkBoxList.size() - 1) {
                                buffer.append(checkBoxList.get(i).getText().toString()).append("");
                            } else {
                                buffer.append(checkBoxList.get(i).getText().toString()).append(",");
                            }
                        }

                    }
                    if ("".equals(buffer.toString())) {
                        ToastUtil.show(this, "请选择交通工具");
                    } else {
                        submitTravelApproval(reasons, chooseTimes, endTime, days.getText().toString(), prupsAddress, viaAddres, buffer.toString());
                    }
                }
                break;
        }
    }

    public void submitTravelApproval(String reason, String startTime, String endTime, String days, String destination, String way, String bus) {
        showProgressDialog();
        IdentityHashMap<String, String> params = new IdentityHashMap<>();
        params.put("travel_reason", reason);
        params.put("set_off_time", startTime);
        params.put("return_time", endTime);
        params.put("travel_day", days);
        params.put("destination", destination);
        params.put("way", way);
        params.put("flow_id", TextUtils.isEmpty(flow_id) ? "" : flow_id);
        params.put("flow_no", TextUtils.isEmpty(flowNo) ? "" : flowNo);
        params.put("transportation", bus);
        requestHttpData(Constants.Urls.URL_POST_SUBMIT_TRAVEL, REQUEST_NET_ONE, FProtocol.HttpMethod.POST, params);
    }

}
