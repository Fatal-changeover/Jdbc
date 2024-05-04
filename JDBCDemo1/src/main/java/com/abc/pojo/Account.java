package com.abc.pojo;

/**
 * ClassName: Account
 * Package: com.abc.test1
 * Description:
 *
 * @Author R
 * @Create 2024/5/4 12:01
 * @Version 1.0
 */
public class Account {
    private Integer aid;
    private String usernamea;
    private String pwda;
    private Double money;
    public Account(int aid, String usernamea, String pwda, double money) {

    }

    public Integer getAid() {
        return aid;
    }

    public void setAid(Integer aid) {
        this.aid = aid;
    }

    public String getUsernamea() {
        return usernamea;
    }

    public void setUsernamea(String usernamea) {
        this.usernamea = usernamea;
    }

    public String getPwda() {
        return pwda;
    }

    public void setPwda(String pwda) {
        this.pwda = pwda;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }
}
