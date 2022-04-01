package com.example.cooperationproject.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.cooperationproject.constant.ConstantFiledUtil;
import com.example.cooperationproject.pojo.*;
import com.example.cooperationproject.pojo.authentication.LoginUser;
import com.example.cooperationproject.mapper.UserMapper;
import com.example.cooperationproject.service.*;
import com.mysql.cj.util.StringUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    public UserServiceImpl(AuthenticationService authenticationService, UidPidService uidPidService, UidPidAuidService uidPidAuidService, UidTidAuidService uidTidAuidService, TaskItemService taskItemService) {
        this.authenticationService = authenticationService;
        this.uidPidService = uidPidService;
        this.uidPidAuidService = uidPidAuidService;
        this.uidTidAuidService = uidTidAuidService;
        this.taskItemService = taskItemService;
    }

    @Override
    public boolean Register(User user) {
        // 不允许用户重名
        QueryWrapper<User> wrapper = new QueryWrapper();
        wrapper.eq("user_name",user.getUserName());
        User user1 = getOne(wrapper);

        if (Objects.isNull(user1)){
            // 如果用户不存在，则允许注册
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String encodePassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(encodePassword);
            boolean res = save(user);
            return res;
        }
        return false;
    }

    @Override
    public boolean Login(String username, String password) {
        QueryWrapper<User> wrapper = new QueryWrapper();
        wrapper.eq("user_name",username);
        User user = getOne(wrapper);
        if (Objects.isNull(user)){
            // 如果用户不存在
            return false;
        }
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        boolean matches = passwordEncoder.matches(password, user.getPassword());
        return matches;
    }

    @Override
    public boolean ModifyInfo(User user) {
        String username = user.getUserName();

        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",user.getUserId());
        User dbUser = getOne(wrapper);
        if (Objects.isNull(dbUser)){
            return false;
        }
        try {
            // 有可能传入的数据是无效的
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String newEncodePassword = passwordEncoder.encode(user.getPassword());
            dbUser.setPassword(newEncodePassword);
            dbUser.setAvatar(user.getAvatar());
            dbUser.setDepartment(user.getDepartment());
            dbUser.setDeleteFlag(user.getDeleteFlag());
            dbUser.setDescription(user.getDescription());
            dbUser.setFirstName(user.getFirstName());
            dbUser.setLastName(user.getLastName());
            dbUser.setNickName(user.getNickName());
            dbUser.setPhone(user.getPhone());
            dbUser.setSex(user.getSex());
            dbUser.setUserName(user.getUserName());
        }catch (Exception e){
            return false;
        }

        return updateById(dbUser);
    }

    @Override
    public boolean DeleteUserByUsername(String username) {

        // TODO : 将对应userid下的project和item删除
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("user_name",username);
        return remove(wrapper);
    }

    @Override
    public boolean DeleteUserByUserId(Integer userId) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("user_Id",userId);
        return remove(wrapper);
    }

    @Override
    public User FindUserByUsername(String username) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("user_name",username);
        User user = getOne(wrapper);
        return user;
    }

    @Override
    public String FindUsernameByUserId(Integer userId) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",userId);
        User user = getOne(wrapper);
        return user.getUserName();
    }

    private final AuthenticationService authenticationService;

    private final UidPidService uidPidService;

    private final UidPidAuidService uidPidAuidService;

    private final UidTidAuidService uidTidAuidService;

    private final TaskItemService taskItemService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 每次有访问，都会调用到这里

        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("user_name",username);
        User user = getOne(wrapper);

        System.out.println("wqeqweqweqweqweqweqweqweqweqweqweqwe");

        // 如果用户不在数据库，则抛出异常
        if (Objects.isNull(user) || StringUtils.isNullOrEmpty(user.getUserName())){
//            throw new UsernameNotFoundException("User not found with username: " + username);
            return null;
        }

        // 能跑到这里，说明token一定是合法的，即用户一定存在
        User curUser = FindUserByUsername(username);

        Integer userId = curUser.getUserId();

        // 获取userId对应的所有的project的权限

        // 设定每个项目中，每个人都只有一种权限，但是范围可以覆盖

//        HashMap<String,String> auMap = new HashMap<>();

        ArrayList<String> auList = new ArrayList<>();

        List<UidPid> uidPidList = uidPidService.GetPidListByUid(userId);

        for (UidPid e : uidPidList){

            int projectId = e.getProjectId();
            UidPidAuId uidPidAuId = uidPidAuidService.FindUidPidAuidByUidPid(userId, projectId);
            if (!Objects.isNull(uidPidAuId)){
                Authentication authentication = authenticationService.GetAuthenticationNameByAuId(uidPidAuId.getAuId());
                auList.add(ConstantFiledUtil.PROJECT + ":" + projectId + ":" + authentication.getAnName());
            }
        }

        // 获取userId对应的所有的item的权限

        List<TaskItem> taskItemList = taskItemService.GetTaskItemListByUsername(username);

        for (TaskItem e : taskItemList){
            int itemId = e.getItemId();
            UidTidAuid uidTidAuid = uidTidAuidService.FindAuidByUidTid(userId, itemId);

            if (!Objects.isNull(uidTidAuid)){
                int auId = uidTidAuid.getAuId();
                Authentication authentication = authenticationService.GetAuthenticationNameByAuId(auId);
//            eg: item:2:user
//            auMap.put(ConstantFiledUtil.ITEM + ":" + itemId,authentication.getAnName());

                auList.add(ConstantFiledUtil.ITEM + ":" + itemId + ":"+ authentication.getAnName());
            }

        }

        System.out.println(auList);

        // 将用户的权限信息封装到一个实体类中，然后放进这里
        // 就可以利用SpringSecurity的授权鉴权机制了
        LoginUser loginUser = new LoginUser(user,auList);
        return loginUser;
    }

}
