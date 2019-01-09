package code.spxt.cn.network.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by dell on 2018/12/17
 */

public class AddressEntity implements Parcelable{
    public AddressEntity() {
    }

    private String address;

    protected AddressEntity(Parcel in) {
        address = in.readString();
    }

    public static final Creator<AddressEntity> CREATOR = new Creator<AddressEntity>() {
        @Override
        public AddressEntity createFromParcel(Parcel in) {
            return new AddressEntity(in);
        }

        @Override
        public AddressEntity[] newArray(int size) {
            return new AddressEntity[size];
        }
    };

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(address);
    }
}
