package code.spxt.cn.common;

import android.content.Context;
import android.text.TextUtils;

import com.common.utils.PreferencesUtils;
import com.google.gson.Gson;

import code.spxt.cn.network.entity.UserEntity;
import code.spxt.cn.network.json.GsonObjectDeserializer;


/**
 * 用户信息
 */
public class UserCenter {

    private static final String USER_INFO = "user_info";
    private static final Gson gson = GsonObjectDeserializer.produceGson();
    private static UserEntity userEntity;

    public static void saveUserInfo(Context context, UserEntity userEntity) {
        UserCenter.userEntity = userEntity;
        String ue = gson.toJson(userEntity);
        PreferencesUtils.putString(context, USER_INFO, ue);
    }

    public static void cleanLoginInfo(Context context) {
        userEntity = null;
        PreferencesUtils.removeSharedPreferenceByKey(context, USER_INFO);
    }

    public static UserEntity getUserInfo(Context context) {
        if (userEntity != null) {
            return userEntity;
        }
        String userInfo = PreferencesUtils.getString(context, USER_INFO);
        if (!TextUtils.isEmpty(userInfo)) {
            userEntity = gson.fromJson(userInfo, UserEntity.class);
            return userEntity;
        }
        return null;
    }

    public static String getUserId(Context context) {
        if (userEntity != null) {
            return String.valueOf(userEntity.getUser_id());
        }
        String userInfo = PreferencesUtils.getString(context, USER_INFO);
        if (!TextUtils.isEmpty(userInfo)) {
            userEntity = gson.fromJson(userInfo, UserEntity.class);
            return String.valueOf(userEntity.getUser_id());
        }
        return "";
    }

    public static boolean isLogin(Context context) {
        String userInfo = PreferencesUtils.getString(context, USER_INFO);
        return !TextUtils.isEmpty(userInfo);
    }

}
