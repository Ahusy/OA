package code.spxt.cn.network.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Android on 2018/12/20.
 */

public class BillBusinessDetail implements Parcelable{

    private int flowId;
    private String flowNo;
    private String flowName;
    private int status;
    private long createDate;
    private long updateDate;
    private String applyId;
    private long applyTime;
    private String visitUnit;
    private String visitReason;
    private String eatPlace;
    private String sleepPlace;
    private String visitSum;
    private String visiter;
    private String escortSum;
    private String escorter;
    private String auditTime;
    private int wfId;

    protected BillBusinessDetail(Parcel in) {
        flowId = in.readInt();
        flowNo = in.readString();
        flowName = in.readString();
        status = in.readInt();
        createDate = in.readLong();
        updateDate = in.readLong();
        applyId = in.readString();
        applyTime = in.readLong();
        visitUnit = in.readString();
        visitReason = in.readString();
        eatPlace = in.readString();
        sleepPlace = in.readString();
        visitSum = in.readString();
        visiter = in.readString();
        escortSum = in.readString();
        escorter = in.readString();
        auditTime = in.readString();
        wfId = in.readInt();
    }

    public static final Creator<BillBusinessDetail> CREATOR = new Creator<BillBusinessDetail>() {
        @Override
        public BillBusinessDetail createFromParcel(Parcel in) {
            return new BillBusinessDetail(in);
        }

        @Override
        public BillBusinessDetail[] newArray(int size) {
            return new BillBusinessDetail[size];
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

    public String getVisitUnit() {
        return visitUnit;
    }

    public void setVisitUnit(String visitUnit) {
        this.visitUnit = visitUnit;
    }

    public String getVisitReason() {
        return visitReason;
    }

    public void setVisitReason(String visitReason) {
        this.visitReason = visitReason;
    }

    public String getEatPlace() {
        return eatPlace;
    }

    public void setEatPlace(String eatPlace) {
        this.eatPlace = eatPlace;
    }

    public String getSleepPlace() {
        return sleepPlace;
    }

    public void setSleepPlace(String sleepPlace) {
        this.sleepPlace = sleepPlace;
    }

    public String getVisitSum() {
        return visitSum;
    }

    public void setVisitSum(String visitSum) {
        this.visitSum = visitSum;
    }

    public String getVisiter() {
        return visiter;
    }

    public void setVisiter(String visiter) {
        this.visiter = visiter;
    }

    public String getEscortSum() {
        return escortSum;
    }

    public void setEscortSum(String escortSum) {
        this.escortSum = escortSum;
    }

    public String getEscorter() {
        return escorter;
    }

    public void setEscorter(String escorter) {
        this.escorter = escorter;
    }

    public String getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(String auditTime) {
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
        dest.writeString(visitUnit);
        dest.writeString(visitReason);
        dest.writeString(eatPlace);
        dest.writeString(sleepPlace);
        dest.writeString(visitSum);
        dest.writeString(visiter);
        dest.writeString(escortSum);
        dest.writeString(escorter);
        dest.writeString(auditTime);
        dest.writeInt(wfId);
    }
}
