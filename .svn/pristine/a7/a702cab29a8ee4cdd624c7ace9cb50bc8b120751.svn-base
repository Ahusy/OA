package code.spxt.cn.network.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by dell on 2018/12/20
 */

public class StepsTwoEntity {

    /**
     * itemsList : [{"amount":222,"createDate":1545303095000,"endTime":1544818800000,"flowNo":"20181220182158756853837","itemId":73,"mark":"null","standard":"null","startTime":1544531700000,"travelPlace":"22","type":"1","updateDate":1545303095000,"vehicle":"轮窜"},{"amount":22,"createDate":1545303095000,"endTime":1545336960000,"flowNo":"20181220182158756853837","itemId":74,"mark":"null","standard":"null","startTime":1544610300000,"travelPlace":"21","type":"2","updateDate":1545303095000,"vehicle":"null"},{"amount":222,"createDate":1545303095000,"endTime":1544171220000,"flowNo":"20181220182158756853837","itemId":75,"mark":"没啊啊啊吗","standard":"null","startTime":1544610480000,"travelPlace":"没啊啊啊吗","type":"4","updateDate":1545303095000,"vehicle":"null"},{"amount":111,"createDate":1545303095000,"endTime":1544736240000,"flowNo":"20181220182158756853837","itemId":76,"mark":"null","standard":"null","startTime":1544732100000,"travelPlace":"null","type":"6","updateDate":1545303095000,"vehicle":"null"}]
     * result_code : 0
     * result_desc : 查询成功
     */

    private String result_code;
    private String result_desc;
    private List<ItemsListBean> itemsList;

    public String getResult_code() {
        return result_code;
    }

    public void setResult_code(String result_code) {
        this.result_code = result_code;
    }

    public String getResult_desc() {
        return result_desc;
    }

    public void setResult_desc(String result_desc) {
        this.result_desc = result_desc;
    }

    public List<ItemsListBean> getItemsList() {
        return itemsList;
    }

    public void setItemsList(List<ItemsListBean> itemsList) {
        this.itemsList = itemsList;
    }

    public static class ItemsListBean implements Parcelable{
        private double amount;
        private long createDate;
        private long endTime;
        private String flowNo;
        private int itemId;
        private String mark;
        private String standard;
        private long startTime;
        private String travelPlace;
        private String type;
        private long updateDate;
        private String vehicle;
        private String days;

        protected ItemsListBean(Parcel in) {
            amount = in.readDouble();
            createDate = in.readLong();
            endTime = in.readLong();
            flowNo = in.readString();
            itemId = in.readInt();
            mark = in.readString();
            standard = in.readString();
            startTime = in.readLong();
            travelPlace = in.readString();
            type = in.readString();
            updateDate = in.readLong();
            vehicle = in.readString();
            days = in.readString();
        }

        public static final Creator<ItemsListBean> CREATOR = new Creator<ItemsListBean>() {
            @Override
            public ItemsListBean createFromParcel(Parcel in) {
                return new ItemsListBean(in);
            }

            @Override
            public ItemsListBean[] newArray(int size) {
                return new ItemsListBean[size];
            }
        };

        public String getDays() {
            return days;
        }

        public void setDays(String days) {
            this.days = days;
        }

        public double getAmount() {
            return amount;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }

        public long getCreateDate() {
            return createDate;
        }

        public void setCreateDate(long createDate) {
            this.createDate = createDate;
        }

        public long getEndTime() {
            return endTime;
        }

        public void setEndTime(long endTime) {
            this.endTime = endTime;
        }

        public String getFlowNo() {
            return flowNo;
        }

        public void setFlowNo(String flowNo) {
            this.flowNo = flowNo;
        }

        public int getItemId() {
            return itemId;
        }

        public void setItemId(int itemId) {
            this.itemId = itemId;
        }

        public String getMark() {
            return mark;
        }

        public void setMark(String mark) {
            this.mark = mark;
        }

        public String getStandard() {
            return standard;
        }

        public void setStandard(String standard) {
            this.standard = standard;
        }

        public long getStartTime() {
            return startTime;
        }

        public void setStartTime(long startTime) {
            this.startTime = startTime;
        }

        public String getTravelPlace() {
            return travelPlace;
        }

        public void setTravelPlace(String travelPlace) {
            this.travelPlace = travelPlace;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public long getUpdateDate() {
            return updateDate;
        }

        public void setUpdateDate(long updateDate) {
            this.updateDate = updateDate;
        }

        public String getVehicle() {
            return vehicle;
        }

        public void setVehicle(String vehicle) {
            this.vehicle = vehicle;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeDouble(amount);
            dest.writeLong(createDate);
            dest.writeLong(endTime);
            dest.writeString(flowNo);
            dest.writeInt(itemId);
            dest.writeString(mark);
            dest.writeString(standard);
            dest.writeLong(startTime);
            dest.writeString(travelPlace);
            dest.writeString(type);
            dest.writeLong(updateDate);
            dest.writeString(vehicle);
            dest.writeString(days);
        }
    }
}
