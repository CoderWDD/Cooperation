package com.example.cooperationproject.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.cooperationproject.pojo.UidPid;
import com.example.cooperationproject.mapper.UidPidMapper;
import com.example.cooperationproject.service.UidPidService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;


@Service
public class UidPidServiceImpl extends ServiceImpl<UidPidMapper, UidPid> implements UidPidService {
    @Override
    public boolean InsertByUidPid(Integer Uid, Integer Pid) {
        UidPid uidPid = new UidPid(Uid,Pid);

        // 判断当前表中是否已经有uid-pid这对权限

        QueryWrapper<UidPid> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",Uid);
        queryWrapper.eq("project_id",Pid);

        UidPid one = getOne(queryWrapper);

        if (!Objects.isNull(one)){
            // 如果已经存在，就直接返回
            return false;
        }

        boolean saveSuccessful = save(uidPid);
        return saveSuccessful;
    }

    @Override
    public boolean DeleteByUidPid(Integer userId, Integer projectId) {
        QueryWrapper<UidPid> uidPidQueryWrapper = new QueryWrapper<>();
        uidPidQueryWrapper.eq("user_id",userId);
        uidPidQueryWrapper.eq("project_id",projectId);

        boolean removeSuccessful = remove(uidPidQueryWrapper);
        return removeSuccessful;
    }

    @Override
    public List<UidPid> GetPidListByUid(Integer Uid) {
        QueryWrapper<UidPid> wrapper = new QueryWrapper<>();

        wrapper.eq("user_id",Uid);

        List<UidPid> list = list(wrapper);

        return list;
    }

    @Override
    public List<UidPid> GetUidListByPid(Integer Pid) {
        QueryWrapper<UidPid> wrapper = new QueryWrapper<>();

        wrapper.eq("project_id",Pid);

        List<UidPid> list = list(wrapper);

        return list;
    }


}
