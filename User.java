package cn.edu.gdmec.s07150611.mycontacts;

/**
 * Created by Administrator on 2016/10/22.
 */
public class User {
    public final static String NAME="name";
    public final static String TEL="TEL";
    public final static String COMPANY="comany";
    public final static String QQ="qq";
    public final static String ADDRESS="address";


    private String name;
    private String tel;
    private String company;
    private String qq;
    private String address;
    private int id_DB=-1;

    public String getName() {
        return name;
    }

    public String getTel() {
        return tel;
    }

    public String getCompany() {
        return company;
    }

    public String getQq() {
        return qq;
    }

    public String getAddress() {
        return address;
    }

    public int getId_DB() {
        return id_DB;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setId_DB(int id_DB) {
        this.id_DB = id_DB;
    }
}
