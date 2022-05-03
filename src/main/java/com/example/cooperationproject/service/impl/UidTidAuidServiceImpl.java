package com.example.cooperationproject.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.cooperationproject.pojo.UidTidAuid;
import com.example.cooperationproject.mapper.UidTidAuidMapper;
import com.example.cooperationproject.service.UidTidAuidService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class UidTidAuidServiceImpl extends ServiceImpl<UidTidAuidMapper, UidTidAuid> implements UidTidAuidService {

    @Override
    public boolean InsertUidTidAuid(UidTidAuid uidTidAuid) {
        QueryWrapper<UidTidAuid> wrapper = new QueryWrapper<>();

        wrapper.eq("user_id",uidTidAuid.getUserId());
        wrapper.eq("item_id",uidTidAuid.getItemId());

        UidTidAuid one = getOne(wrapper);

        if (!Objects.isNull(one)){
            return false;
        }

        return save(uidTidAuid);
    }

    @Override
    public UidTidAuid FindAuidByUidTid(Integer userId, Integer itemId) {

        QueryWrapper<UidTidAuid> wrapper = new QueryWrapper<>();

        wrapper.eq("user_id",userId);
        wrapper.eq("item_id",itemId);

        UidTidAuid uidTidAuid = getOne(wrapper);

        return uidTidAuid;
    }

    @Override
    public List<UidTidAuid> FindAuidListByProjectId(Integer projectId) {
        QueryWrapper<UidTidAuid> wrapper = new QueryWrapper<>();
        wrapper.eq("project_id",projectId);
        return list(wrapper);
    }

    @Override
    public boolean DeleteByUIDTID(Integer userId, Integer itemId) {
        QueryWrapper<UidTidAuid> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",userId);
        wrapper.eq("item_id",itemId);
        return remove(wrapper);
    }

    @Override
    public boolean DeleteByTID(Integer itemId) {
        QueryWrapper<UidTidAuid> wrapper = new QueryWrapper<>();
        wrapper.eq("item_id",itemId);
        return remove(wrapper);
    }
}
