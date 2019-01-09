package code.spxt.cn.network.entity;

import java.util.ArrayList;

/**
 * Created by dell on 2018/12/18
 */

public class PhotoEntity {
    public PhotoEntity() {
    }

    private String typeName;

    private ArrayList<String> urlList = new ArrayList<>();

    public ArrayList<String> getUrlList() {
        return urlList;
    }

    public void setUrlList(ArrayList<String> urlList) {
        this.urlList = urlList;
    }


    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }


    @Override
    public String toString() {
        return "PhotoEntity{" +
                "typeName='" + typeName + '\'' +
                ", url=" + urlList +
                '}';
    }
}
