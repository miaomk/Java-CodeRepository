package com.miao.service.impl;

import com.miao.mapper.UsersMapper;
import com.miao.pojo.Users;
import com.miao.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;

/**
 * 用户业务实现类
 *
 * @author miao
 */
@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UsersMapper usersMapper;

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
    public Users getUserByOpenId(String openId) {
        Example example = new Example(Users.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("mpWxOpenId", openId);
        return usersMapper.selectOneByExample(example);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int insertUsers(Users users) {

        return usersMapper.insertSelective(users);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
    public Users getUserByUserName(String userName) {
        Example example = new Example(Users.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("username", userName);
        return usersMapper.selectOneByExample(example);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
    public Users getUserByUserId(String userId) {

        Example example = new Example(Users.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("id", userId);

        return usersMapper.selectOneByExample(example);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int modifyUsers(Users users) {
        //更新用户信息
        return usersMapper.updateByPrimaryKeySelective(users);
    }
}
