package com.cn.chonglin.web.client.account.form;

import com.cn.chonglin.bussiness.base.domain.User;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * 用户注册表单
 *
 * @author wu
 */
public class RegisterForm {
    @NotEmpty(message = "Please input the email address!")
    private String email;
    @NotEmpty(message = "Please input the password!")
    @Length(min=6, message = "The password must be more than 6 and less than 30 characters long")
    private String password;
    private String confirmPassword;
    @NotEmpty(message = "Please input the first name!")
    private String firstName;
    private String lastName;

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

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
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

    public User toDomain(){
        User user = new User();

        user.setEmail(this.email);
        user.setPassword(this.password);
        user.setFirstName(this.firstName);
        user.setLastName(this.lastName);

        return user;
    }
}
