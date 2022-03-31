package com.example.cooperationproject.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.cooperationproject.pojo.UidPidAuId;
import com.example.cooperationproject.mapper.UidPidAuidMapper;
import com.example.cooperationproject.service.UidPidAuidService;
import org.springframework.stereotype.Service;

@Service
public class UidPidAuidServiceImpl extends ServiceImpl<UidPidAuidMapper, UidPidAuId> implements UidPidAuidService {


    @Override
    public boolean InsertUidPidAuid(UidPidAuId uidPidAuId) {
        UpdateWrapper<UidPidAuId> wrapper = new UpdateWrapper<>();

        wrapper.eq("au_id",uidPidAuId.getAuId());
        wrapper.eq("user_id",uidPidAuId.getUserId());
        wrapper.eq("project_id",uidPidAuId.getProjectId());

        boolean res = saveOrUpdate(uidPidAuId, wrapper);

        return res;
    }

    @Override
    public UidPidAuId FindUidPidAuidByUidPid(Integer userId, Integer projectId) {
        QueryWrapper<UidPidAuId> wrapper = new QueryWrapper<>();

        wrapper.eq("user_id",userId);
        wrapper.eq("project_id",projectId);

        UidPidAuId uidPidAuId = getOne(wrapper);

        return uidPidAuId;
    }

    @Override
    public boolean DeleteUidPidAuid(Integer userId, Integer projectId) {
        QueryWrapper<UidPidAuId> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",userId);
        wrapper.eq("project_id",projectId);
        return remove(wrapper);
    }

}
