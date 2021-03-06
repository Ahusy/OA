package code.spxt.cn.network;

/**
 * Created by dell on 2018/12/12
 */

public class Constants {

    public static class Urls {
        private static String URL_BASE_DOMAIN = "http://rqrj.antke.cn";
        //        private static String URL_BASE_DOMAIN = "http://39.96.9.108:8083";
        // 版本更新
        public static String URL_POST_CHECK_UPDATE = URL_BASE_DOMAIN + "/versionUpdate";
        // 手机密码登录
        public static String URL_POST_LOGIN = URL_BASE_DOMAIN + "/login";
        // 验证码登录
        public static String URL_POST_MESSAGE_LOGIN = URL_BASE_DOMAIN + "/messageLogin";
        // 发送验证码
        public static String URL_POST_SEND_MESSAGE = URL_BASE_DOMAIN + "/sendSMSCode";
        // 步骤一
        public static String URL_POST_SUBMIT_ONE = URL_BASE_DOMAIN + "/submitTravelCostOne";
        // 步骤二
        public static String URL_POST_SUBMIT_TWO = URL_BASE_DOMAIN + "/submitTravelCostTwo";
        // 步骤三
        public static String URL_POST_SUBMIT_THREE = URL_BASE_DOMAIN + "/submitTravelCostThree";
        //上传图片
        public static String URL_PICTUREUPLOAD = URL_BASE_DOMAIN + "/pictureUpload";
        // 查询步骤一
        public static String URL_POST_SELECT_ONE = URL_BASE_DOMAIN + "/selectTravelCostOne";
        // 查询步骤二
        public static String URL_POST_SELECT_TWO = URL_BASE_DOMAIN + "/selectItemsTwo";
        // 查询步骤三
        public static String URL_POST_SELECT_THREE = URL_BASE_DOMAIN + "/selectPicListThree";
        //我的申请列表
        public static String URL_POST_MYAPPROVALLIST = URL_BASE_DOMAIN + "/myApprovalList";
        //待我审批
        public static String URL_POST_PENDINGAPPROVALLIST = URL_BASE_DOMAIN + "/pendingApprovalList";
        //我审批的
        public static String URL_POST_HASAPPROVALLIST = URL_BASE_DOMAIN + "/hasApprovalList";
        //撤回我的申请
        public static String URL_POST_GOBACKFORAPPROVAL = URL_BASE_DOMAIN + "/goBackForApproval";
        //出差审批单详情
        public static String URL_POST_TRAVELAPPROVALDETAILS = URL_BASE_DOMAIN + "/travelApprovalDetails";
        //公车审批单详情
        public static String URL_POST_BUSAPPROVALDETAILS = URL_BASE_DOMAIN + "/busApprovalDetails";
        //合同审批单详情
        public static String URL_POST_CONTRACTAPPROVALDETAILS = URL_BASE_DOMAIN + "/contractApprovalDetails";
        //业务招待费审批单详情
        public static String URL_POST_ENTERTAINAPPROVALDETAILS = URL_BASE_DOMAIN + "/entertainApprovalDetails";
        //审核
        public static String URL_POST_AUDIT = URL_BASE_DOMAIN + "/audit";
        // 清空一
        public static String URL_POST_CLEAR_ONE = URL_BASE_DOMAIN + "/deleteTravelCostApproval";
        // 清空二
        public static String URL_POST_CLEAR_TWO = URL_BASE_DOMAIN + "/deleteTravelCostItems";
        // 出差审批单
        public static String URL_POST_SUBMIT_TRAVEL = URL_BASE_DOMAIN + "/submitTravelApproval";
        // 业务招待审批单
        public static String URL_POST_SUBMIT_ENTERTAIN = URL_BASE_DOMAIN + "/submitEntertainApproval";
        // 公车使用申请单
        public static String URL_POST_SUBMIT_BUS_APPRIVAL = URL_BASE_DOMAIN + "/submitBusApproval";
        // 合同审批
        public static String URL_POST_SUBMIT_CONTRACT = URL_BASE_DOMAIN + "/submitContractApproval";
        // 修改密码
        public static String URL_POST_UPDATE_PASSWORD = URL_BASE_DOMAIN + "/updatePassword";
        // 公告
        public static String URL_POST_NOTICELIST = URL_BASE_DOMAIN + "/noticeList";
        // 首页
        public static String URL_POST_SELECT_COUNT = URL_BASE_DOMAIN + "/selectCountApproval";
        //差旅报销单详情
        public static String URL_POST_TRAVELCOSTAPPROVALDETAIL = URL_BASE_DOMAIN + "/travelCostApprovalDetail";
    }
}
