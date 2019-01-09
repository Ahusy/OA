package code.spxt.cn.network.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.ArrayList;

/**
 * Created by dell on 2018/12/18
 */

public class PhotoCopyEntity implements Parcelable{

    @SerializedName("type")
    private String typeName;

    @SerializedName("pic_list")
    private ArrayList<String> urlList;

    protected PhotoCopyEntity(Parcel in) {
        typeName = in.readString();
        urlList = in.createStringArrayList();
    }

    public static final Creator<PhotoCopyEntity> CREATOR = new Creator<PhotoCopyEntity>() {
        @Override
        public PhotoCopyEntity createFromParcel(Parcel in) {
            return new PhotoCopyEntity(in);
        }

        @Override
        public PhotoCopyEntity[] newArray(int size) {
            return new PhotoCopyEntity[size];
        }
    };

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public ArrayList<String> getUrlList() {
        return urlList;
    }

    public void setUrlList(ArrayList<String> urlList) {
        this.urlList = urlList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(typeName);
        dest.writeStringList(urlList);
    }
}
