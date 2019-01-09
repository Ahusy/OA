package code.spxt.cn.network.entity;

/**
 * Created by dell on 2018/12/22
 */

public class SelectCount {

    /**
     * result_desc : 查询成功
     * count : 0
     * result_code : 0
     */

    private String result_desc;
    private int count;
    private String foodAllowance;
    private String travelAllowance;

    public String getFoodAllowance() {
        return foodAllowance;
    }

    public void setFoodAllowance(String foodAllowance) {
        this.foodAllowance = foodAllowance;
    }

    public String getTravelAllowance() {
        return travelAllowance;
    }

    public void setTravelAllowance(String travelAllowance) {
        this.travelAllowance = travelAllowance;
    }

    private String result_code;

    public String getResult_desc() {
        return result_desc;
    }

    public void setResult_desc(String result_desc) {
        this.result_desc = result_desc;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getResult_code() {
        return result_code;
    }

    public void setResult_code(String result_code) {
        this.result_code = result_code;
    }
}
