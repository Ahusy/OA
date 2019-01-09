package code.spxt.cn.network.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Android on 2018/12/21.
 */

public class MyReviewAFV {
    @SerializedName("p_status")
    private int pStatus;//1 通过 2驳回
    @SerializedName("operate_user_name")
    private String operateUserName;
    @SerializedName("type")
    private int type;//1.待审批 2,已审批
    @SerializedName("mark")
    private String mark;


    public int getpStatus() {
        return pStatus;
    }

    public void setpStatus(int pStatus) {
        this.pStatus = pStatus;
    }

    public String getOperateUserName() {
        return operateUserName;
    }

    public void setOperateUserName(String operateUserName) {
        this.operateUserName = operateUserName;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }
}
