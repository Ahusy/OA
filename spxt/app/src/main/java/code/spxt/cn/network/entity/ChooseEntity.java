package code.spxt.cn.network.entity;

import java.io.Serializable;

/**
 * Created by dell on 2018/12/13
 */

public class ChooseEntity implements Serializable {

    public ChooseEntity() {
    }

    private int type;
    private String peopleNum;
    private String startTime;
    private String endTime;
    private String bars;
    private String money;
    private String costsThat;
    private String days;
    private String standard;

    public String getPeopleNum() {
        return peopleNum;
    }

    public void setPeopleNum(String peopleNum) {
        this.peopleNum = peopleNum;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getBars() {
        return bars;
    }

    public void setBars(String bars) {
        this.bars = bars;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getCostsThat() {
        return costsThat;
    }

    public void setCostsThat(String costsThat) {
        this.costsThat = costsThat;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getStandard() {
        return standard;
    }

    public void setStandard(String standard) {
        this.standard = standard;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public ChooseEntity(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "ChooseEntity{" +
                "type=" + type +
                ", peopleNum='" + peopleNum + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", bars='" + bars + '\'' +
                ", money='" + money + '\'' +
                ", costsThat='" + costsThat + '\'' +
                ", days='" + days + '\'' +
                ", standard='" + standard + '\'' +
                '}';
    }
}
