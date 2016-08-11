package com.chinamobile.iot.dao;

import com.chinamobile.iot.model.business.*;

import java.util.List;

/**
 * Created by buchan on 2016/5/19 0019.
 */
public interface RoleDao {
    public void addUser(User user);
    void  addRole(Role r);
    void addUserRole(UserRole ur);
    void addRoleSource(RoleSource rs);


    void updateUser(User user);
    void updateRole(Role role);
    void updatePassword(Password p);



    Role  getRole(Role r);
    List<RoleSource> getRoleSource(RoleSource rs);
    List<User>  getUser(User u);
    List<User> getUserAll(User role);
    int getuserAllCount(User role);
    List<User> getUserByName(User u);
    List<Role> getRoleByName(Role role);


    void delUser (User u);
    void delUserRoleByu(UserRole ur);
    void delUserRoleByr(UserRole ur);
    void delRole(Role r);
    void delRoleSource(RoleSource rs);

    SingleUser getSingleUserByName(String user_name);
}
