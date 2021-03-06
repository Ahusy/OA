package code.spxt.cn.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.ArrayList;

import code.spxt.cn.R;
import code.spxt.cn.common.Global;
import code.spxt.cn.network.entity.MySubmitItem;
import code.spxt.cn.utils.TimeUtil;

/**
 * Android on 2018/12/17.
 */

public class MySubmitItemAdapter extends BaseAdapter {


    private ArrayList<MySubmitItem> msiList;
    private View.OnClickListener listener;

    public MySubmitItemAdapter(ArrayList<MySubmitItem> msiList, View.OnClickListener listener) {
        this.msiList = msiList;
        this.listener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == Global.RV_EMPTY) {
            return new EmptyHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_empty_wfqd, parent, false));
        }
        return new MyHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_my_submit_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == Global.RV_EMPTY) {
            if (!isInit) {
                EmptyHolder eH = (EmptyHolder) holder;
                eH.llEmpty.setVisibility(View.VISIBLE);
            }
            return;
        }
        MyHolder h = (MyHolder) holder;
        MySubmitItem msi = msiList.get(position);
        String time = TimeUtil.formatTime(msi.getApplyTime(), TimeUtil.TIME_FORMAT_FOUR);
        h.tvTime.setText(String.valueOf("申请时间:" + time));
        h.tvNo.setText(String.valueOf("审批单编号:" + msi.getFlowNo()));
        h.tvName.setText(String.valueOf(msi.getFlowName()));
        h.tvGetMoney.setVisibility(View.GONE);
        if (0 == msi.getStatus()) {
            //待审批
            h.tvStatus.setText("审批状态:待审批");
            h.tvStatusDesc.setText("等待审批");
        } else if (1 == msi.getStatus()) {
            //审批中
            h.tvStatus.setText("审批状态:审批中");
            h.tvStatusDesc.setText("正在审批中");
        } else if (2 == msi.getStatus()) {
            //完成
            h.tvStatus.setText("审批状态:已完成");
            h.tvStatusDesc.setText("审批流程已通过");
            int financeStatus = msi.getFinanceStatus();
            h.tvGetMoney.setVisibility(View.VISIBLE);
            if (financeStatus == 1) {
                h.tvGetMoney.setText("财务状态:待验票");
            } else if (financeStatus == 2) {
                h.tvGetMoney.setText("财务状态:验票失败");
            } else if (financeStatus == 3) {
                h.tvGetMoney.setText("财务状态:待打款");
            } else if (financeStatus == 4) {
                h.tvGetMoney.setText("财务状态:已打款");
            } else {
                h.tvGetMoney.setVisibility(View.GONE);
            }

        } else if (3 == msi.getStatus()) {
            //已驳回
            h.tvStatus.setText("审批状态:已驳回");
            h.tvStatusDesc.setText("已驳回");
        } else if (4 == msi.getStatus()) {
            //已取消
            h.tvStatus.setText("审批状态:已撤回");
            h.tvStatusDesc.setText("已撤回");
        }
        h.tvCancel.setVisibility(0 == msi.getStatus() ? View.VISIBLE : View.GONE);

        h.tvCancel.setOnClickListener(listener);
        h.tvCancel.setTag(msi);
        h.flLayout.setOnClickListener(listener);
        h.flLayout.setTag(msi);


    }

    @Override
    public int getItemCount() {
        return msiList == null || msiList.size() == 0 ? 1 : msiList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0 && (msiList == null || msiList.size() == 0)) {
            return Global.RV_EMPTY;
        } else {
            return Global.RV_DEFAULT;
        }
    }

    private class MyHolder extends RecyclerView.ViewHolder {

        private final FrameLayout flLayout;
        private final TextView tvName;
        private final TextView tvTime;
        private final TextView tvNo;
        private final TextView tvStatus;
        private final TextView tvStatusDesc;
        private final TextView tvCancel;
        private final TextView tvGetMoney;

        MyHolder(View itemView) {
            super(itemView);
            flLayout = itemView.findViewById(R.id.fl_layout);
            tvName = itemView.findViewById(R.id.tv_name);
            tvTime = itemView.findViewById(R.id.tv_time);
            tvNo = itemView.findViewById(R.id.tv_no);
            tvStatus = itemView.findViewById(R.id.tv_status);
            tvStatusDesc = itemView.findViewById(R.id.tv_status_desc);
            tvCancel = itemView.findViewById(R.id.tv_cancel);
            tvGetMoney = itemView.findViewById(R.id.tv_get_money);
        }
    }
}
