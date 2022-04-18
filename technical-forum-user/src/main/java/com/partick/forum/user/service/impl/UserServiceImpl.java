package com.partick.forum.user.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.crypto.digest.MD5;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.partick.forum.common.db.mapper.TRoleMapper;
import com.partick.forum.common.db.mapper.TUserMapper;
import com.partick.forum.common.db.mapper.TUserRoleMapper;
import com.partick.forum.common.db.pojo.TRole;
import com.partick.forum.common.db.pojo.TUser;
import com.partick.forum.common.db.pojo.TUserRole;
import com.partick.forum.common.dto.LoginDTO;
import com.partick.forum.common.vo.UserVO;
import com.partick.forum.user.service.UserService;
import com.partick.forum.common.utils.DataBaseDefaultFieldUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author partick_peng
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private TUserMapper tUserMapper;

    @Resource
    private TRoleMapper tRoleMapper;

    @Resource
    private TUserRoleMapper tUserRoleMapper;

    @Resource
    private RedisTemplate redisTemplate;

    /**
     * 创建用户
     *
     * @param tUser
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createUser(TUser tUser) {
        Integer count = tUserMapper.selectCount(
                new QueryWrapper<TUser>().eq("user_name", tUser.getUserName()));
        if (count == 1) {
            throw new RuntimeException("用户已存在");
        }
        try {
            DataBaseDefaultFieldUtil.defaultCreateField(tUser);
            tUser.setUserId(IdUtil.simpleUUID());
            String md5Password = MD5.create().digestHex16(tUser.getPassword());
            tUser.setPassword(md5Password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        tUserMapper.insert(tUser);
    }

    /**
     * 删除用户
     *
     * @param tUser
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delUser(TUser tUser) {
        try {
            DataBaseDefaultFieldUtil.defaultUpdateField(tUser);
            tUser.setIsDeleted(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        tUserMapper.update(tUser,
                new UpdateWrapper<TUser>().eq("id", tUser.getId()));
    }

    /**
     * 创建角色
     *
     * @param tRole
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createRole(TRole tRole) {
        Integer count = tRoleMapper.selectCount(
                new QueryWrapper<TRole>().eq("role_name", tRole.getRoleName()));
        if (count == 1) {
            throw new RuntimeException("角色已存在");
        }
        try {
            DataBaseDefaultFieldUtil.defaultCreateField(tRole);
            tRole.setRoleId(IdUtil.simpleUUID());
        } catch (Exception e) {
            e.printStackTrace();
        }
        tRoleMapper.insert(tRole);
    }

    /**
     * 删除角色
     *
     * @param tRole
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delRole(TRole tRole) {
        try {
            DataBaseDefaultFieldUtil.defaultUpdateField(tRole);
            tRole.setIsDeleted(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        tRoleMapper.update(tRole,
                new UpdateWrapper<TRole>().eq("id", tRole.getId()));
    }

    /**
     * 添加用户角色
     *
     * @param userId
     * @param tRoles
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addRoleByUser(String userId, List<TRole> tRoles) {
        try {
            for (TRole tRole : tRoles) {
                Integer count = tUserRoleMapper.selectCount(
                        new QueryWrapper<TUserRole>()
                                .eq("user_id", userId)
                                .eq("role_id", tRole.getRoleId()));
                if (count == 1) {
                    continue;
                }
                TUserRole userRole = new TUserRole();
                userRole.setRoleName(tRole.getRoleName());
                userRole.setRoleId(tRole.getRoleId());
                userRole.setUserId(userId);
                DataBaseDefaultFieldUtil.defaultCreateField(userRole);
                tUserRoleMapper.insert(userRole);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除用户角色
     *
     * @param tUserRole
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delRoleByUser(TUserRole tUserRole) {
        try {
            DataBaseDefaultFieldUtil.defaultUpdateField(tUserRole);
            tUserRole.setIsDeleted(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        tUserRoleMapper.update(tUserRole,
                new UpdateWrapper<TUserRole>().eq("id", tUserRole.getId()));
    }

    /**
     * 获取用户角色列表
     *
     * @param userId
     * @return
     */
    @Override
    public List<TUserRole> getUserRoles(String userId) {
        List<TUserRole> userRoles = tUserRoleMapper
                .selectList(new QueryWrapper<TUserRole>().eq("user_id", userId).eq("is_deleted", 0));
        return userRoles;
    }

    /**
     * 用户登录
     *
     * @param loginDTO
     * @return
     */
    @Override
    public UserVO Login(LoginDTO loginDTO) {
        String md5Password = MD5.create().digestHex16(loginDTO.getPassword());
        TUser user = tUserMapper.selectOne(new QueryWrapper<TUser>()
                .eq("user_name", loginDTO.getUserName())
                .eq("password", md5Password)
        );
        loginDTO = null;
        if (ObjectUtils.isEmpty(user)) {
            throw new RuntimeException("用户名或密码错误");
        }
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        user = null;
        if (redisTemplate.getExpire(userVO.getUserId()).compareTo(0L) < 0) {
            List<TUserRole> roleList = this.getUserRoles(userVO.getUserId());
            redisTemplate.opsForValue().set(userVO.getUserId(), roleList, 60 * 60 * 8, TimeUnit.SECONDS);
        }
        return userVO;
    }

}
