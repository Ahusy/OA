package code.spxt.cn.adapters;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.ArrayList;

import code.spxt.cn.R;
import code.spxt.cn.common.Global;
import code.spxt.cn.network.entity.WaitMeHandlerItem;
import code.spxt.cn.utils.TimeUtil;
import code.spxt.cn.view.ApprovalFlowView;

/**
 * Android on 2018/12/17.
 */

public class WaitMeHandleAdapter extends BaseAdapter {


    private ArrayList<WaitMeHandlerItem> msiList;
    private View.OnClickListener listener;

    public WaitMeHandleAdapter(ArrayList<WaitMeHandlerItem> msiList, View.OnClickListener listener) {
        this.msiList = msiList;
        this.listener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == Global.RV_EMPTY) {
            return new EmptyHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_empty, parent, false));
        }
        return new MyHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_wait_me_handle_item, parent, false));
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
        WaitMeHandlerItem wm = msiList.get(position);
        h.tvTitle.setText(wm.getFlowName());
        h.tvApplyName.setText(String.valueOf("申请人:" + wm.getApplyName()));
        String applyTime = TimeUtil.formatTime(wm.getApplyTime(), TimeUtil.TIME_FORMAT_FOUR);
        h.tvTime.setText(String.valueOf("申请时间:" + applyTime));

        h.tvNo.setText(String.valueOf("审批单编号:" + wm.getFlowNo()));
        h.tvStatus.setText(String.valueOf("审批状态:" + (0 == wm.getStatus() ? "待审批" : "审批中")));

        ArrayList<ApprovalFlowView.Text> texts = new ArrayList<>();
        String operateName = wm.getOperateName();
        if (!TextUtils.isEmpty(operateName)) {
            ApprovalFlowView.Text text = new ApprovalFlowView.Text();
            text.setName(operateName);
            text.setTag("已同意");
            texts.add(text);
        }
        ApprovalFlowView.Text text = new ApprovalFlowView.Text();
        text.setName("待您审批");
        texts.add(text);
        h.afvView.setData(texts, true);

        h.flLayout.setOnClickListener(listener);
        h.flLayout.setTag(wm);
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
        private final TextView tvTitle;
        private final TextView tvApplyName;
        private final TextView tvTime;
        private final TextView tvNo;
        private final TextView tvStatus;
        private final ApprovalFlowView afvView;

        MyHolder(View itemView) {
            super(itemView);
            flLayout = itemView.findViewById(R.id.fl_layout);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvApplyName = itemView.findViewById(R.id.tv_apply_name);
            tvTime = itemView.findViewById(R.id.tv_time);
            tvNo = itemView.findViewById(R.id.tv_no);
            tvStatus = itemView.findViewById(R.id.tv_status);
            afvView = itemView.findViewById(R.id.afv_view);
        }
    }
}
