package code.spxt.cn.network.entity;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Android on 2018/12/17.
 */

public class MyReviewItem {
    @SerializedName("process_id")
    private int processId;
    @SerializedName("operate_user_name")
    private String operateUserName;
    @SerializedName("operate_name")
    private String operateName;
    @SerializedName("operate_user_id")
    private String operateUserId;
    @SerializedName("flow_id")
    private int flowId;
    @SerializedName("wf_id")
    private int wfId;
    @SerializedName("flow_no")
    private String flowNo;
    @SerializedName("flow_name")
    private String flowName;
    @SerializedName("apply_id")
    private String applyId;
    @SerializedName("apply_time")
    private long applyTime;
    @SerializedName("next_node_id")
    private int nextNodeId;
    @SerializedName("status")
    private int status;
    @SerializedName("worker_name")
    private String workerName;

    @SerializedName("p_list")
    private ArrayList<MyReviewAFV> pList;

    public ArrayList<MyReviewAFV> getpList() {
        return pList;
    }

    public void setpList(ArrayList<MyReviewAFV> pList) {
        this.pList = pList;
    }

    public String getWorkerName() {
        return workerName;
    }

    public void setWorkerName(String workerName) {
        this.workerName = workerName;
    }

    public int getProcessId() {
        return processId;
    }

    public void setProcessId(int processId) {
        this.processId = processId;
    }

    public String getOperateUserName() {
        return operateUserName;
    }

    public void setOperateUserName(String operateUserName) {
        this.operateUserName = operateUserName;
    }

    public String getOperateName() {
        return operateName;
    }

    public void setOperateName(String operateName) {
        this.operateName = operateName;
    }

    public String getOperateUserId() {
        return operateUserId;
    }

    public void setOperateUserId(String operateUserId) {
        this.operateUserId = operateUserId;
    }

    public int getFlowId() {
        return flowId;
    }

    public void setFlowId(int flowId) {
        this.flowId = flowId;
    }

    public int getWfId() {
        return wfId;
    }

    public void setWfId(int wfId) {
        this.wfId = wfId;
    }

    public String getFlowNo() {
        return flowNo;
    }

    public void setFlowNo(String flowNo) {
        this.flowNo = flowNo;
    }

    public String getFlowName() {
        return flowName;
    }

    public void setFlowName(String flowName) {
        this.flowName = flowName;
    }

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }

    public long getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(long applyTime) {
        this.applyTime = applyTime;
    }

    public int getNextNodeId() {
        return nextNodeId;
    }

    public void setNextNodeId(int nextNodeId) {
        this.nextNodeId = nextNodeId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
