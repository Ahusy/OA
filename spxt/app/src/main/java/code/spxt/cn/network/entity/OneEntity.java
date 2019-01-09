package code.spxt.cn.network.entity;

/**
 * Created by dell on 2018/12/18
 */

public class OneEntity{
    private String startTime;
    private String endTime;
    private String address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public OneEntity(String startTime, String endTime, String reason) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.reason = reason;
    }

    private String reason;

    public OneEntity() {
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

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    public String toString() {
        return "OneEntity{" +
                "startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", address='" + address + '\'' +
                ", reason='" + reason + '\'' +
                '}';
    }
}
