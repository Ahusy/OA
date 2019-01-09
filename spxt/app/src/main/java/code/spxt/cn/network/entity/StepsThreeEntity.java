package code.spxt.cn.network.entity;

import java.util.List;

/**
 * Created by dell on 2018/12/20
 */

public class StepsThreeEntity {

    /**
     * picList : [{"createDate":1545310100000,"flowNo":"20181220203522744024199","id":512,"picUrl":"http://sk.antke.cn/f7e23ad1-8cf5-42a3-ac49-3abbf1049369","type":"餐饮","updateDate":1545310100000},{"createDate":1545310100000,"flowNo":"20181220203522744024199","id":513,"picUrl":"http://sk.antke.cn/25394d89-a702-4b8b-92e9-feeceaaaebd1","type":"住宿","updateDate":1545310100000}]
     * result_code : 0
     * result_desc : 查询成功
     */

    private String result_code;
    private String result_desc;
    private List<PicListBean> picList;

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

    public List<PicListBean> getPicList() {
        return picList;
    }

    public void setPicList(List<PicListBean> picList) {
        this.picList = picList;
    }

    public static class PicListBean {
        /**
         * createDate : 1545310100000
         * flowNo : 20181220203522744024199
         * id : 512
         * picUrl : http://sk.antke.cn/f7e23ad1-8cf5-42a3-ac49-3abbf1049369
         * type : 餐饮
         * updateDate : 1545310100000
         */

        private long createDate;
        private String flowNo;
        private int id;
        private String picUrl;
        private String type;
        private long updateDate;

        public long getCreateDate() {
            return createDate;
        }

        public void setCreateDate(long createDate) {
            this.createDate = createDate;
        }

        public String getFlowNo() {
            return flowNo;
        }

        public void setFlowNo(String flowNo) {
            this.flowNo = flowNo;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getPicUrl() {
            return picUrl;
        }

        public void setPicUrl(String picUrl) {
            this.picUrl = picUrl;
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
    }
}
