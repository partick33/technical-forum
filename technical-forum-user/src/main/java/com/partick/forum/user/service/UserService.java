package com.partick.forum.user.service;


import com.partick.forum.common.db.pojo.TRole;
import com.partick.forum.common.db.pojo.TUser;
import com.partick.forum.common.db.pojo.TUserRole;
import com.partick.forum.common.dto.LoginDTO;
import com.partick.forum.common.vo.UserVO;

import java.util.List;

/**
 * @author partick_peng
 */
public interface UserService {

    /**
     * 创建用户
     * @param tUser
     */
    void createUser(TUser tUser);

    /**
     * 删除用户
     * @param tUser
     */
    void delUser(TUser tUser);

    /**
     * 创建角色
     * @param tRole
     */
    void createRole(TRole tRole);

    /**
     * 删除角色
     * @param tRole
     */
    void delRole(TRole tRole);

    /**
     * 添加用户角色
     * @param userId
     * @param tRoles
     */
    void addRoleByUser(String userId, List<TRole> tRoles);

    /**
     * 删除用户角色
     * @param tUserRole
     */
    void delRoleByUser(TUserRole tUserRole);

    /**
     * 获取用户角色列表
     * @param userId
     * @return
     */
    List<TUserRole> getUserRoles(String userId);

    /**
     * 用户登录
     * @param loginDTO
     * @return
     */
    UserVO Login(LoginDTO loginDTO);

}
