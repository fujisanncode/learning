package ink.fujisann.learning.vo.mybatis;

import java.util.Date;

public class Person {

    private Integer id;

    private String firstName;

    private String lastName;

    private String addressLv1;

    private String addressLv1Code;

    private String addressLv2;

    private String addressLv2Code;

    private String addressLv3;

    private String addressLv3Code;

    private String addressLv4;

    private Date createTime;

    private String createBy;

    private Date lastUpdateTime;

    private String lastUpdateBy;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName == null ? null : firstName.trim();
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName == null ? null : lastName.trim();
    }

    public String getAddressLv1() {
        return addressLv1;
    }

    public void setAddressLv1(String addressLv1) {
        this.addressLv1 = addressLv1 == null ? null : addressLv1.trim();
    }

    public String getAddressLv1Code() {
        return addressLv1Code;
    }

    public void setAddressLv1Code(String addressLv1Code) {
        this.addressLv1Code = addressLv1Code == null ? null : addressLv1Code.trim();
    }

    public String getAddressLv2() {
        return addressLv2;
    }

    public void setAddressLv2(String addressLv2) {
        this.addressLv2 = addressLv2 == null ? null : addressLv2.trim();
    }

    public String getAddressLv2Code() {
        return addressLv2Code;
    }

    public void setAddressLv2Code(String addressLv2Code) {
        this.addressLv2Code = addressLv2Code == null ? null : addressLv2Code.trim();
    }

    public String getAddressLv3() {
        return addressLv3;
    }

    public void setAddressLv3(String addressLv3) {
        this.addressLv3 = addressLv3 == null ? null : addressLv3.trim();
    }

    public String getAddressLv3Code() {
        return addressLv3Code;
    }

    public void setAddressLv3Code(String addressLv3Code) {
        this.addressLv3Code = addressLv3Code == null ? null : addressLv3Code.trim();
    }

    public String getAddressLv4() {
        return addressLv4;
    }

    public void setAddressLv4(String addressLv4) {
        this.addressLv4 = addressLv4 == null ? null : addressLv4.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy == null ? null : createBy.trim();
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public String getLastUpdateBy() {
        return lastUpdateBy;
    }

    public void setLastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy == null ? null : lastUpdateBy.trim();
    }
}