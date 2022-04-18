package com.partick.forum.user.controller;

import com.partick.forum.common.config.CommonResult;
import com.partick.forum.common.db.pojo.TRole;
import com.partick.forum.common.db.pojo.TUser;
import com.partick.forum.common.db.pojo.TUserRole;
import com.partick.forum.common.dto.LoginDTO;
import com.partick.forum.common.vo.LoginVO;
import com.partick.forum.common.vo.UserVO;
import com.partick.forum.user.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author partick_peng
 */
@RestController
@RequestMapping("/user")
public class UserController  {

    @Autowired
    private UserService userService;

    /**
     * 创建用户api
     *
     * @param user
     * @return
     */
    @PostMapping("/createUser")
    public CommonResult createUser(TUser user) {
        userService.createUser(user);
        return new CommonResult(true,"用户创建成功");
    }

    /**
     * 删除用户
     *
     * @param user
     * @return
     */
    @PostMapping("/delUser")
    public CommonResult delUser(TUser user) {
        userService.delUser(user);
        return new CommonResult(true,"删除用户成功");
    }

    /***
     * 创建角色
     * @param role
     * @return
     */
    @PostMapping("/createRole")
    public CommonResult createRole(TRole role) {
        userService.createRole(role);
        return new CommonResult(true,"创建角色成功");
    }

    /**
     * 删除角色
     *
     * @param role
     * @return
     */
    @PostMapping("/delRole")
    public CommonResult delRole(TRole role) {
        userService.delRole(role);
        return new CommonResult(true,"删除角色成功");
    }

    /**
     * 获取用户角色列表
     *
     * @param userId
     * @return
     */
    @GetMapping("/getUserRoles/{userId}")
    public CommonResult getUserRoles(@PathVariable String userId) {
        List<TUserRole> userRoles = userService.getUserRoles(userId);
        return new CommonResult(true,"获取用户列表成",userRoles);
    }

    /**
     * 登录
     * @param loginVO
     * @return
     */
    @PostMapping("/login")
    public CommonResult Login(@RequestBody @Valid  LoginVO loginVO) {
        LoginDTO loginDTO = new LoginDTO();
        BeanUtils.copyProperties(loginVO, loginDTO);
        loginVO = null;
        UserVO userVO = userService.Login(loginDTO);
        return new CommonResult(true, "登录成功", userVO);
    }
}
