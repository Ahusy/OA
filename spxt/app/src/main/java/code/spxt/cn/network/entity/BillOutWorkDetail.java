package code.spxt.cn.network.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Android on 2018/12/20.
 */

public class BillOutWorkDetail implements Parcelable{
    @SerializedName("flowId")
    private int flowId;
    @SerializedName("flowNo")
    private String flowNo;
    @SerializedName("flowName")
    private String flowName;
    @SerializedName("status")
    private int status;
    @SerializedName("createDate")
    private long createDate;
    @SerializedName("updateDate")
    private long updateDate;
    @SerializedName("applyId")
    private String applyId;
    @SerializedName("applyTime")
    private long applyTime;
    @SerializedName("travelReason")
    private String travelReason;
    @SerializedName("setOffTime")
    private long setOffTime;
    @SerializedName("returnTime")
    private long returnTime;
    @SerializedName("travelDay")
    private String travelDay;
    @SerializedName("destination")
    private String destination;
    @SerializedName("way")
    private String way;
    @SerializedName("mileage")
    private String mileage;
    @SerializedName("transportation")
    private String transportation;
    @SerializedName("auditTime")
    private long auditTime;
    @SerializedName("wfId")
    private int wfId;

    protected BillOutWorkDetail(Parcel in) {
        flowId = in.readInt();
        flowNo = in.readString();
        flowName = in.readString();
        status = in.readInt();
        createDate = in.readLong();
        updateDate = in.readLong();
        applyId = in.readString();
        applyTime = in.readLong();
        travelReason = in.readString();
        setOffTime = in.readLong();
        returnTime = in.readLong();
        travelDay = in.readString();
        destination = in.readString();
        way = in.readString();
        mileage = in.readString();
        transportation = in.readString();
        auditTime = in.readLong();
        wfId = in.readInt();
    }

    public static final Creator<BillOutWorkDetail> CREATOR = new Creator<BillOutWorkDetail>() {
        @Override
        public BillOutWorkDetail createFromParcel(Parcel in) {
            return new BillOutWorkDetail(in);
        }

        @Override
        public BillOutWorkDetail[] newArray(int size) {
            return new BillOutWorkDetail[size];
        }
    };

    public int getFlowId() {
        return flowId;
    }

    public void setFlowId(int flowId) {
        this.flowId = flowId;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    public long getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(long updateDate) {
        this.updateDate = updateDate;
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

    public String getTravelReason() {
        return travelReason;
    }

    public void setTravelReason(String travelReason) {
        this.travelReason = travelReason;
    }

    public long getSetOffTime() {
        return setOffTime;
    }

    public void setSetOffTime(long setOffTime) {
        this.setOffTime = setOffTime;
    }

    public long getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(long returnTime) {
        this.returnTime = returnTime;
    }

    public String getTravelDay() {
        return travelDay;
    }

    public void setTravelDay(String travelDay) {
        this.travelDay = travelDay;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getWay() {
        return way;
    }

    public void setWay(String way) {
        this.way = way;
    }

    public String getMileage() {
        return mileage;
    }

    public void setMileage(String mileage) {
        this.mileage = mileage;
    }

    public String getTransportation() {
        return transportation;
    }

    public void setTransportation(String transportation) {
        this.transportation = transportation;
    }

    public long getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(long auditTime) {
        this.auditTime = auditTime;
    }

    public int getWfId() {
        return wfId;
    }

    public void setWfId(int wfId) {
        this.wfId = wfId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(flowId);
        dest.writeString(flowNo);
        dest.writeString(flowName);
        dest.writeInt(status);
        dest.writeLong(createDate);
        dest.writeLong(updateDate);
        dest.writeString(applyId);
        dest.writeLong(applyTime);
        dest.writeString(travelReason);
        dest.writeLong(setOffTime);
        dest.writeLong(returnTime);
        dest.writeString(travelDay);
        dest.writeString(destination);
        dest.writeString(way);
        dest.writeString(mileage);
        dest.writeString(transportation);
        dest.writeLong(auditTime);
        dest.writeInt(wfId);
    }
}
