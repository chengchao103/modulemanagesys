package com.chinamobile.iot.controller;
import com.alibaba.fastjson.JSON;
import com.chinamobile.iot.model.business.*;
import com.chinamobile.iot.model.common.CommonResult;
import com.chinamobile.iot.model.response.*;
import com.chinamobile.iot.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * 用户角色管理
 * Created by w on 2016/5/19 0019.
 */
@RestController
public class UserController  extends BaseController{
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;

    /**
     * 新增登录用户,新增用户角色表
     * @param
     * @return
     */
     @RequestMapping(value = "/user/add", method = RequestMethod.POST)
    public BaseResp addUser(@RequestBody User ur ) {
         SimpleResp resp = new SimpleResp();

        LOGGER.info("=====adduser====:" + ur.toString());

         if(null==ur){
             resp.setErrno(1);
             resp.setError("没有传入用户信息");
             return  resp ;
         }
         List<User> lu=userService.getUserByName(ur);
         if(lu.size()>0){
             resp.setErrno(1);
             resp.setError("该用户名称已经存在，不能重复新增");
             return  resp ;
         }
         User user = userService.addUser(ur);

         if(!"".equals(ur.getRole_id())&& ur.getRole_id()!=null){

             UserRole urole=new UserRole();
             urole.setUser_id(user.getUser_id());
             urole.setRole_id(ur.getRole_id());
              userService.addUserRole(urole);
         }
         List<User> dto=userService.getUserByName(ur);
         User usert=dto.get(0);
         usert.setRole_id(ur.getRole_id());
         resp.setData(usert);
         return  resp ;
    }

    /**
     * 新增角色
     * @param r
     * @return
     */
    /*@RequestMapping(value = "/role/{user_name}", method = RequestMethod.POST)
    public String addRole(@RequestHeader("access_token") String access_token,@PathVariable("user_name") String user_name,@RequestBody Role r) {
        SimpleResp resp = new SimpleResp();
        CommonResult cmrt = super.auth(user_name, access_token);
        if (cmrt.getRet() != 0) {
            resp.setErrno(1);
            resp.setError(cmrt.getMsg());
            return JSON.toJSONString(resp);
        }
        LOGGER.info("=====addRole====:" + r);

        if(null==r){
            resp.setErrno(1);
            resp.setError("没有传入角色信息");
            return JSON.toJSONString(resp);
        }
        List<Role> lr=userService.getRoleByName(r);
        if(lr.size()>0){
            resp.setErrno(1);
            resp.setError("该角色名称已经存在，不能重复新增");
            return JSON.toJSONString(resp);
        }
        r.setId(UUID.randomUUID().toString());
           userService.addRole(r);

        RoleSource rs=new RoleSource();
        for (int i=0;i<r.getResource_id().size();i++){
            rs.setId(UUID.randomUUID().toString());
            rs.setRole_id(r.getId());
            rs.setResource_id(r.getResource_id().get(i));
            userService.addRoleSource(rs);
        }

        return JSON.toJSONString(resp);
    }
*/

    /**
     * 新增用户角色关系表
     * @param ur
     * @return
     */
   /* @RequestMapping(value = "/userRole", method = RequestMethod.POST)
    public String addUserRole(@RequestBody UserRole ur) {
        LOGGER.info("=====addUserRole====:" + ur);
        SimpleResp resp = new SimpleResp();
        if(null==ur){
            resp.setErrno(1);
            resp.setError("没有传入用户角色信息");
            return JSON.toJSONString(resp);
        }
        ur.setId(UUID.randomUUID().toString());
        userService.addUserRole(ur);
        return JSON.toJSONString(resp);
    }
*/
    /**
     * 新增角色菜单表
     * @param rs 角色权限
     * @return
     */
    /*@RequestMapping(value = "/roleSource", method = RequestMethod.POST)
    public String addRoleSource(@RequestBody RoleSource rs) {
        LOGGER.info("=====addRoleSource====:" + rs);
        SimpleResp resp = new SimpleResp();
        if(null==rs){
            resp.setErrno(1);
            resp.setError("没有传入角色信息");
            return JSON.toJSONString(resp);
        }
        rs.setId(UUID.randomUUID().toString());
         userService.addRoleSource(rs);
        return JSON.toJSONString(resp);
    }
    */
    /**
     * 修改登录用户
     * @param
     * @return
     */
    @RequestMapping(value = "/user/update", method = RequestMethod.PUT)
    public BaseResp updateUser(@RequestBody User ur ) {
        SimpleResp resp = new SimpleResp();

        LOGGER.info("=====adduser====:" + ur);

        if(null==ur ||"".equals( ur.getUser_name())){
            resp.setErrno(1);
            resp.setError("没有传入用户信息");
            return resp ;
        }
        List<User> lu=userService.getUserByName(ur);
        if(lu.size()>0){
            ur.setUser_id(lu.get(0).getUser_id());
        }else{
            resp.setErrno(1);
            resp.setError("系统没有此用户");
            return resp ;
        }


        userService.updateUser(ur);



        if(!"".equals(ur.getRole_id()) && ur.getRole_id()!=null){
            UserRole urole=new UserRole();
            urole.setUser_id(ur.getUser_id());
            userService.delUserRole(urole);

            urole.setRole_id(ur.getRole_id());
            userService.addUserRole(urole);
        }
        if(!"".equals(ur.getPassword()) && ur.getPassword()!=null){
            Password p=new  Password ();
            p.setUser_id(ur.getUser_id());
            p.setNewPwd(ur.getPassword());
            userService.updatePassword(p);
        }


        return resp ;
    }
    /**
     * 修改角色
     * @param r
     * @return
     */
   /* @RequestMapping(value = "/role/{user_name}/{role_id}", method = RequestMethod.PUT)
    public String updateRole(@RequestHeader("access_token") String access_token,@PathVariable("user_name") String user_name,@PathVariable("role_id") String role_id,@RequestBody Role r) {
        SimpleResp resp = new SimpleResp();
        CommonResult cmrt = super.auth(user_name, access_token);
        if (cmrt.getRet() != 0) {
            resp.setErrno(1);
            resp.setError(cmrt.getMsg());
            return JSON.toJSONString(resp);
        }

        LOGGER.info("=====addRole====:" + r);

        if(null==r){
            resp.setErrno(1);
            resp.setError("没有传入角色信息");
            return JSON.toJSONString(resp);
        }
        r.setId(role_id);
        userService.updateRole(r);

        RoleSource rs=new RoleSource();
        rs.setRole_id(r.getId());
        userService.delRoleSource(rs);

        for (int i=0;i<r.getResource_id().size();i++){

            rs.setResource_id(r.getResource_id().get(i));
            rs.setId(UUID.randomUUID().toString());
            userService.addRoleSource(rs);
        }

        return JSON.toJSONString(resp);
    }
    */
    /**
     * 修改密码
     * @param
     * @return
     */
    @RequestMapping(value = "/user/updatePwd", method = RequestMethod.PUT)
    public BaseResp updatePassword(@RequestBody Password p) {
        SimpleResp resp = new SimpleResp();


        LOGGER.info("=====updatePassword====:" + p);

        if(null==p || (p.getUser_id()==null&& p.getUser_name()==null)){
            resp.setErrno(1);
            resp.setError("没有传入用户信息");
            return resp;
        }
        User u=new User();
        u.setUser_name(p.getUser_name());
        List<User> lu=userService.getUserByName(u);
        if(lu.size()>0){
          if(lu.get(0).getPassword().equals(p.getOldPwd())){
              userService.updatePassword(p);
          }else{
              resp.setErrno(1);
              resp.setError("用户原密码输入错误");
              return resp ;
          }
        }else{
            resp.setErrno(1);
            resp.setError("系统没有此用户");
            return resp ;
        }




        return resp;
    }


    /**
     * 查询用户分页
     * @param role 角色ID
     * @param userName 用户名
     * @param pageNo
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public BaseResp getUserAll(@RequestParam(value ="role",required =false) String role,
                               @RequestParam(value ="userName",required =false) String userName,
                               @RequestParam(value ="page",required =false) Integer pageNo,
                               @RequestParam(value = "pagesize",required =false ) Integer pageSize) {
        SimpleResp rsp = new SimpleResp();
        Integer pageStart;
        if (pageNo != null && pageSize != null) {
            pageStart = (pageNo - 1) * pageSize;
        } else {
            // 如果没有设置，就默认为10行
            pageNo = 1;
            pageSize = 10;
            pageStart = 0;
        }
        User u=new User();
        if(!"".equals(role) ){
            u.setRole_id(role);
        }
        if(!"".equals(userName) ){
            u.setUser_name(userName);
        }
        u.setPageStart(pageStart);
        u.setPageSize(pageSize);

        List<User> lu = userService.getUserAll(u);
        int count = userService.getuserAllCount(u);


        Map dataMap = new HashMap<>();
        dataMap.put("total_size",count);
        dataMap.put("list",lu);
        rsp.setData(dataMap);

        return rsp;
    }
    /**
     * 获取用户信息
     * @param
     * @return
     */
    @RequestMapping(value = "/userinfo", method = RequestMethod.GET)
    public BaseResp  getUser(@RequestParam(value ="username",required =false) String username) {
        SimpleResp resp = new SimpleResp();

        User u=new User();
        u.setUser_name(username);

        List<User>  user= userService.getUser(u);

        resp.setData(user);
        if(user!=null)
        LOGGER.info("===getRole.data==" + resp.getData().toString());
        return resp;
    }
    /**
     * 根据roleid获取角色信息
     * @param
     * @return
     */
    /*@RequestMapping(value = "/role/{user_name}/{role_id}", method = RequestMethod.GET)
    public String getRole(@RequestHeader("access_token") String access_token,@PathVariable("user_name") String user_name,@PathVariable("role_id") String role_id) {
        SimpleResp resp = new SimpleResp();
        CommonResult cmrt = super.auth(user_name, access_token);
        if (cmrt.getRet() != 0) {
            resp.setErrno(1);
            resp.setError(cmrt.getMsg());
            return JSON.toJSONString(resp);
        }
        Role r=new Role();
        r.setId(role_id);
        LOGGER.info("=====getRole====:" + r.toString());

        if (null == r||"".equals(r.getId())) {
            resp.setErrno(1);
            resp.setError("没有传入查询信息");
            return JSON.toJSONString(resp);
        }

        Role role = userService.getRole(r);
        RoleSource rs=new RoleSource();
        rs.setRole_id(role_id);
        List<String > resource_id=new ArrayList<>();
        List<RoleSource> rstmp=userService.getRoleSource(rs);
        for (int i=0;i<rstmp.size();i++){
            resource_id.add(rstmp.get(i).getResource_id());
        }
        if(role!=null)

        resp.setData(role);
        if(role!=null)
        LOGGER.info("===getRole.data==" + resp.getData().toString());
        return JSON.toJSONString(resp);
    }
    */
    /**
     * 查询角色权限表
     * @param roleid 角色ID
     * @return
     */

    /*public String getRoleSource(@RequestBody String  roleid) {
        SimpleResp resp = new SimpleResp();
        LOGGER.info("=====roleid====:" + roleid);
        RoleSource rs=new RoleSource();
        rs.setRole_id(roleid);

        if(null==rs||"".equals( rs.getRole_id())){
            resp.setErrno(1);
            resp.setError("没有传入角色信息");
            return JSON.toJSONString(resp);
        }
        List<RoleSource> roleSource = userService.getRoleSource(rs);
        resp.setData(roleSource);

        LOGGER.info("===getRoleSource.data==" + resp.getData().toString());
        return JSON.toJSONString(resp);
    }
*/
    /**
     * 删除用户信息,同时删除角色信息
     * @param
     * @return
     */
    @RequestMapping(value = "/user/del", method = RequestMethod.DELETE)
    public BaseResp delUser(@RequestParam(value ="username",required =true) String username) {
        SimpleResp resp = new SimpleResp();

        User u=new User();
        u.setUser_name(username);


        List<User> lu=userService.getUserByName(u);
        if(lu.size()==0){
            resp.setErrno(1);
            resp.setError("系统没有此用户");
            return  resp ;
        }
        u.setUser_id(lu.get(0).getUser_id());

         userService.delUser(u);
        UserRole ur=new UserRole();
        ur.setUser_id(u.getUser_id());
        userService.delUserRole(ur);
        return resp;
    }
    /**
     * 删除用户角色信息
     * @param ，角色ID
     * @return
     */
   /* private String delUserRole( String userid,String roleid ) {
        LOGGER.info("=====delUserRole====userid:" + userid+",roleid:"+roleid);
        UserRole ur=new UserRole();
        if(!"".equals(userid)){
            ur.setUser_id(userid);
        }
        if(!"".equals(roleid)){
            ur.setRole_id(roleid);
        }

        SimpleResp resp = new SimpleResp();
        if(null==ur||("".equals(userid)&& "".equals(roleid))){
            resp.setErrno(1);
            resp.setError("没有要删除的角色信息");
            return JSON.toJSONString(resp);
        }
        userService.delUserRole(ur);

        return JSON.toJSONString(resp);
    }
    */
    /**
     * 删除角色信息,同时删除角色权限信息
     * @param
     * @return
     */
    /*@RequestMapping(value = "/role/{user_name}/{role_id}", method = RequestMethod.DELETE)
    public String delRole(@RequestHeader("access_token") String access_token,@PathVariable("user_name") String user_name,@PathVariable("role_id") String role_id ) {
        SimpleResp resp = new SimpleResp();
        CommonResult cmrt = super.auth(user_name, access_token);
        if (cmrt.getRet() != 0) {
            resp.setErrno(1);
            resp.setError(cmrt.getMsg());
            return JSON.toJSONString(resp);
        }
        LOGGER.info("=====delRole====:" + role_id);
        Role r=new Role();
        r.setId(role_id);

        if(null==r||"".equals(r.getId())){
            resp.setErrno(1);
            resp.setError("没有要删除的角色信息");
            return JSON.toJSONString(resp);
        }
        userService.delRole(r);
        delRoleSource(role_id);
        return JSON.toJSONString(resp);
    }
*/
    /**
     * 删除角色权限信息
     * @param roleid
     * @return
     */
   /* private String delRoleSource( String roleid ) {
        LOGGER.info("=====delRoleSource====:" + roleid);
        RoleSource r=new RoleSource();
        r.setRole_id(roleid);
        SimpleResp resp = new SimpleResp();
        if(null==roleid||"".equals(roleid)){
            resp.setErrno(1);
            resp.setError("没有要删除的角色权限信息");
            return JSON.toJSONString(resp);
        }
        userService.delRoleSource(r);
        return JSON.toJSONString(resp);
    }
    */
}
