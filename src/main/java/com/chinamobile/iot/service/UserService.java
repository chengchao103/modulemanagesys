package com.chinamobile.iot.service;

import com.chinamobile.iot.model.business.*;

import java.util.List;

/**
 * Created by buchan on 2016/5/19 0019.
 */
public interface UserService {

     User addUser(User user);
    void addRole(Role role);
    void addUserRole(UserRole userrole);
    void addRoleSource(RoleSource rs);

    void updateUser(User user);
    void updateRole(Role role);
    void updatePassword(Password role);


    List<RoleSource> getRoleSource(RoleSource rs);
    Role getRole(Role role);
    List<User>  getUser(User role);
    List<User> getUserAll(User role);
    int getuserAllCount(User role);
    List<User> getUserByName(User u);
    List<Role> getRoleByName(Role role);


    void delUser (User user);
    void delUserRole(UserRole ur);
    void delRole(Role r);
    void delRoleSource(RoleSource rs);

    SingleUser getSingleUserByName(String user_name);
}
