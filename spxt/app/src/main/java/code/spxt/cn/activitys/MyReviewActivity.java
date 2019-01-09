package code.spxt.cn.activitys;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.common.network.FProtocol;
import com.common.viewinject.annotation.ViewInject;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Set;

import code.spxt.cn.R;
import code.spxt.cn.adapters.MyReviewAdapter;
import code.spxt.cn.base.ToolBarActivity;
import code.spxt.cn.common.CommonConstant;
import code.spxt.cn.network.Constants;
import code.spxt.cn.network.Parsers;
import code.spxt.cn.network.entity.MyReviewItem;
import code.spxt.cn.utils.ViewInjectUtils;

import static code.spxt.cn.common.CommonConstant.REQUEST_NET_ONE;

public class MyReviewActivity extends ToolBarActivity implements View.OnClickListener {
    @ViewInject(R.id.rv_view)
    private XRecyclerView rvView;
    @ViewInject(R.id.submit_time)
    private LinearLayout timeClick;
    @ViewInject(R.id.submit_time_img)
    private ImageView imgTime;
    @ViewInject(R.id.submit_status)
    private LinearLayout statusClick;
    @ViewInject(R.id.submit_status_text)
    private TextView statusName;
    @ViewInject(R.id.rl)
    private RelativeLayout rl;
    private PopupWindow popupWindow;
    private ArrayList<MyReviewItem> mrList = new ArrayList<>();
    private MyReviewAdapter mrAdapter;
    private int currentPage = 1;
    private Map<CheckBox, String> map;
    private String type = "2";
    private String status = "-1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_review);
        ViewInjectUtils.inject(this);
        initView();
        initData();
    }

    private void initView() {
        setCenterTitle("我审批的");
        timeClick.setOnClickListener(this);
        statusClick.setOnClickListener(this);
        rvView.setLayoutManager(new LinearLayoutManager(this));
        mrAdapter = new MyReviewAdapter(mrList, this);
        rvView.setAdapter(mrAdapter);
        initListener();
        imgTime.setBackground(ContextCompat.getDrawable(this, R.drawable.icon_s));

    }

    private void initData() {
        requestData(true, status, type);
    }

    private void initListener() {
        rvView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                requestData(true, status, type);
            }

            @Override
            public void onLoadMore() {
                requestData(false, status, type);
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submit_time:
                if (type.equals("2")) {
                    imgTime.setBackground(ContextCompat.getDrawable(this, R.drawable.icon_j));
                    type = "1";
                } else {
                    type = "2";
                    imgTime.setBackground(ContextCompat.getDrawable(this, R.drawable.icon_s));
                }
                String status = "";
                switch (statusName.getText().toString()) {
                    case "全部":
                        status = "-1";
                        break;
                    case "已通过":
                        status = "2";
                        break;
                    case "审批中":
                        status = "1";
                        break;
                    case "已驳回":
                        status = "3";
                        break;
                }
                requestData(true, status, type);
                break;
            case R.id.submit_status:
                View contentView = LayoutInflater.from(this).inflate(R.layout.popuwindow_choose_status_copy, null, false);
                View view = contentView.findViewById(R.id.v_view);
                CheckBox all = contentView.findViewById(R.id.status_all);
                CheckBox ytg = contentView.findViewById(R.id.status_ytg);
                CheckBox shz = contentView.findViewById(R.id.status_shz);
                CheckBox ybh = contentView.findViewById(R.id.status_ybh);
                all.setOnCheckedChangeListener(cb);
                ytg.setOnCheckedChangeListener(cb);
                shz.setOnCheckedChangeListener(cb);
                ybh.setOnCheckedChangeListener(cb);
                map = new HashMap<>();
                map.put(all, "-1");
                map.put(ytg, "2");
                map.put(shz, "1");
                map.put(ybh, "3");

                view.setOnClickListener(v1 -> {
                    if (popupWindow != null) {
                        popupWindow.dismiss();
                    }
                });
                popupWindow = new PopupWindow(contentView, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, true);
                popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                popupWindow.setOutsideTouchable(true);
                popupWindow.setTouchable(true);
                popupWindow.showAsDropDown(rl, 0, 0);
                break;
            case R.id.fl_layout:
                //case R.id.tv_reset:
                MyReviewItem mr = (MyReviewItem) v.getTag();
                int wfId = mr.getWfId();
                if (wfId == 2) {
                    ApprovalBillOutWorkActivity.startAct(this, mr.getFlowId(), mr.getStatus(), mr.getWorkerName(), /*mr.getStatus() == 3 ? CommonConstant.STATUS_ONE : */CommonConstant.STATUS_THREE);
                } else if (wfId == 4) {
                    ApprovalBillPublicCarUseActivity.startAct(this, mr.getFlowId(), mr.getStatus(), mr.getWorkerName(), /*mr.getStatus() == 3 ? CommonConstant.STATUS_ONE :*/ CommonConstant.STATUS_THREE);
                } else if (wfId == 5) {
                    ApprovalBillAgreementActivity.startAct(this, mr.getFlowId(), mr.getStatus(), mr.getWorkerName(),/* mr.getStatus() == 3 ? CommonConstant.STATUS_ONE :*/ CommonConstant.STATUS_THREE);
                } else if (wfId == 3) {
                    ApprovalBillBusinessActivity.startAct(this, mr.getFlowId(), mr.getStatus(), mr.getWorkerName(),/* mr.getStatus() == 3 ? CommonConstant.STATUS_ONE :*/ CommonConstant.STATUS_THREE);
                } else if (wfId == 1) {
                    ApprovalBillTourismMoneyActivity.startAct(this, mr.getFlowId(), mr.getStatus(), mr.getFlowNo(), mr.getWorkerName(), /*mr.getStatus() == 3 ? CommonConstant.STATUS_ONE :*/ CommonConstant.STATUS_THREE);
                }
                break;
        }
    }

    private CompoundButton.OnCheckedChangeListener cb = new CompoundButton.OnCheckedChangeListener() { //实例化一个cb
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            Set<Map.Entry<CheckBox, String>> entries = map.entrySet();
            for (Map.Entry<CheckBox, String> set : entries) {
                if (set.getKey().isChecked()) {
                    popupWindow.dismiss();
                    requestData(true, set.getValue(), type);
                    return;
                }
            }
        }
    };

    @Override
    protected void parseData(int requestCode, String data) {
        super.parseData(requestCode, data);
        switch (requestCode) {
            case CommonConstant.REQUEST_NET_ONE:
                ArrayList<MyReviewItem> sList = Parsers.getReviewList(data);
                if (currentPage == 1) {
                    mrList.clear();
                }
                if (sList != null && sList.size() > 0) {
                    ++currentPage;
                    mrList.addAll(sList);
                }
                mrAdapter.notifyChanged();
                rvView.reset();
                break;
        }
    }

    private void requestData(boolean isFirst, String status, String type) {
        if (isFirst) {
            currentPage = 1;
        }
        switch (status) {
            case "-1":
                statusName.setText("全部");
                break;
            case "1":
                statusName.setText("审批中");
                break;
            case "2":
                statusName.setText("已通过");
                break;
            case "3":
                statusName.setText("已驳回");
                break;
        }
        IdentityHashMap<String, String> map = new IdentityHashMap<>();
        map.put(CommonConstant.PAGESIZE, CommonConstant.PAGE_SIZE_10);
        map.put(CommonConstant.PAGENUM, String.valueOf(currentPage));
        map.put("status", status);
        map.put("type", type);
        requestHttpData(Constants.Urls.URL_POST_HASAPPROVALLIST, REQUEST_NET_ONE, FProtocol.HttpMethod.POST, map);
    }
}
