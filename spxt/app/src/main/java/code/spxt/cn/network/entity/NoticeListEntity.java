package code.spxt.cn.network.entity;

import java.util.List;

/**
 * Created by dell on 2018/12/21
 */

public class NoticeListEntity {

    /**
     * notice_list : [{"content":"万集卡七夕优惠多又多！！！","createDate":1541491604000,"createUserId":"超级管理员","id":9,"name":"预付消费卡管理规定明年施行 市人大常委会会议表决通过","status":1,"updateDate":1545383004000},{"content":"万集卡七夕优惠多又多！！！万集卡七夕优惠多又多！！！","createDate":1541491574000,"createUserId":"超级管理员","id":8,"name":"万集卡七夕优惠多又多！！！","status":1,"updateDate":1544775382000},{"content":"犯得上犯得上发射点发射点v大","createDate":1541491407000,"createUserId":"超级管理员","id":7,"name":"关于万集卡账户安全信息补填公告（重要）","status":1,"updateDate":1544773972000}]
     * result_code : 0
     * result_desc : 获取通告列表成功
     */

    private String result_code;
    private String result_desc;
    private List<NoticeListBean> notice_list;

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

    public List<NoticeListBean> getNotice_list() {
        return notice_list;
    }

    public void setNotice_list(List<NoticeListBean> notice_list) {
        this.notice_list = notice_list;
    }

    public static class NoticeListBean {
        /**
         * content : 万集卡七夕优惠多又多！！！
         * createDate : 1541491604000
         * createUserId : 超级管理员
         * id : 9
         * name : 预付消费卡管理规定明年施行 市人大常委会会议表决通过
         * status : 1
         * updateDate : 1545383004000
         */

        private String content;
        private long createDate;
        private String createUserId;
        private int id;
        private String name;
        private int status;
        private long updateDate;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public long getCreateDate() {
            return createDate;
        }

        public void setCreateDate(long createDate) {
            this.createDate = createDate;
        }

        public String getCreateUserId() {
            return createUserId;
        }

        public void setCreateUserId(String createUserId) {
            this.createUserId = createUserId;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public long getUpdateDate() {
            return updateDate;
        }

        public void setUpdateDate(long updateDate) {
            this.updateDate = updateDate;
        }
    }
}
