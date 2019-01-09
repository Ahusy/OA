package code.spxt.cn.network.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Android on 2018/12/20.
 */

public class ApprovalFlowItem {

    @SerializedName("process_id")
    private int processId;
    @SerializedName("operate_user_name")
    private String operateUserName;
    @SerializedName("operate_user_id")
    private String operateUserId;
    @SerializedName("update_date")
    private long createDate;
    @SerializedName("status")
    private int status;//1通过2 驳回

    @SerializedName("type")
    private int type;//0未到审批 1 待审批 2已完成

    public int getProcessId() {
        return processId;
    }

    public void setProcessId(int processId) {
        this.processId = processId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getOperateUserName() {
        return operateUserName;
    }

    public void setOperateUserName(String operateUserName) {
        this.operateUserName = operateUserName;
    }

    public String getOperateUserId() {
        return operateUserId;
    }

    public void setOperateUserId(String operateUserId) {
        this.operateUserId = operateUserId;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
