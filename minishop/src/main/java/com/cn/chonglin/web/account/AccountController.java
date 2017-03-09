package com.cn.chonglin.web.account;

import com.cn.chonglin.bussiness.base.service.UserService;
import com.cn.chonglin.bussiness.base.vo.UserVo;
import com.cn.chonglin.common.PaginationResult;
import com.cn.chonglin.common.ResponseResult;
import com.cn.chonglin.constants.DropdownListContants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * 账号
 *
 */
@RestController
public class AccountController {
    @Autowired
    private UserService userService;

    @GetMapping(value = "admin/user-list", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public PaginationResult<UserVo> query(@RequestParam(required = false, defaultValue = "") String email,
                                          @RequestParam(required = false, defaultValue = "") String firstName,
                                          @RequestParam(required = false, defaultValue = DropdownListContants.USER_STATE_ACTIVE) String state,
                                          @RequestParam(required = false, defaultValue = "0") @Min(0) int currentPage,
                                          @RequestParam(required = false, defaultValue = "15") @Min(0) @Max(200) int size){

        currentPage -= 1;

        return PaginationResult.success(userService.query(email, firstName, state, size, currentPage));
    }

    @PostMapping(value = "admin/users", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseResult<Object> save(@Valid com.cn.chonglin.web.account.form.UserForm userForm, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return ResponseResult.error(bindingResult.getFieldErrors());
        }

        userService.save(userForm.toDomain());

        return ResponseResult.success(null);
    }
}
