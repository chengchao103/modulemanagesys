package com.chinamobile.iot.service.Impl;

import com.chinamobile.iot.dao.RoleDao;
import com.chinamobile.iot.model.business.*;
import com.chinamobile.iot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by buchan on 2016/5/19 0019.
 */
@Service("userService")
public class UserServiceImpl implements UserService {
    @Autowired
    private RoleDao rDao;

    public User addUser(User user){
       rDao.addUser(user);
        return getUserByName(user).get(0);
    }
    public void addRole(Role role){
          rDao.addRole(role);
    }
    public void addUserRole(UserRole userrole){
          rDao.addUserRole(userrole);
    }
    public void addRoleSource(RoleSource rs){
              rDao.addRoleSource(rs);
    }

    public void updateUser(User user){
         rDao.updateUser(user);
    }
    public void updateRole(Role role){
          rDao.updateRole(role);
    }
    public void updatePassword(Password p){
            rDao.updatePassword(p);
    }

    public Role getRole(Role role){
        return rDao.getRole(role);
    }
    public List<RoleSource> getRoleSource(RoleSource rs){
        return rDao.getRoleSource(rs);
    }
    public List<User>  getUser(User u){
        return rDao.getUser(u);
    }
    public List<User> getUserAll(User u){
        return rDao.getUserAll(u);
    }
    public int getuserAllCount(User u){
        return rDao.getuserAllCount(u);
    }
    public List<User> getUserByName(User u){
        return rDao.getUserByName(u);
    }
    public List<Role> getRoleByName(Role role){
        return rDao.getRoleByName(role);
    }

    public void delUser (User u){
        rDao.delUser(u);
    }
    public  void delUserRole(UserRole ur){
        if(!"".equals(ur.getUser_id()) && ur.getUser_id()!=null){
            rDao.delUserRoleByu(ur);
        }
        if(!"".equals(ur.getRole_id())&& ur.getRole_id()!=null){
            rDao.delUserRoleByr(ur);
        }
    }
    public void delRole(Role r){
        rDao.delRole(r);
    }
    public void delRoleSource(RoleSource rs){
        rDao.delRoleSource(rs);
    }

    @Override
    public SingleUser getSingleUserByName(String user_name) {
        return rDao.getSingleUserByName(user_name);
    }
}
