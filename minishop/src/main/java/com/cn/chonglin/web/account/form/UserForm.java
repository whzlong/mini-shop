package com.cn.chonglin.web.account.form;

import com.cn.chonglin.bussiness.base.domain.User;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

public class UserForm {
    private String userId;

    @NotEmpty(message = "Please input the firstname.")
    @Length(max = 40)
    private String firstName;

    private String lastName;

    @NotEmpty(message = "Please input the email.")
    private String email;

    private String password;

    private String phone;

    private String address;

    private String postcode;

    @NotEmpty(message = "Please input the role.")
    private String role;

    @NotEmpty(message = "Please input the state.")
    private String state;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public User toDomain(){
        User user = new User();

        user.setFirstName(this.firstName);
        user.setLastName(this.lastName);
        user.setEmail(this.email);
        user.setPassword(StringUtils.trim(this.password));
        user.setPhone(this.phone);
        user.setAddress(this.address);
        user.setPostcode(this.postcode);
        user.setRole(this.role);
        user.setId(this.userId);
        user.setState(this.state);

        return user;
    }
}
