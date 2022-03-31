package com.example.cooperationproject.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.cooperationproject.pojo.UidTidAuid;

public interface UidTidAuidService extends IService<UidTidAuid> {

    boolean InsertUidTidAuid(UidTidAuid uidTidAuid);

    UidTidAuid FindAuidByUidTid(Integer userId,Integer itemId);

    boolean DeleteByUIDTID(Integer userId,Integer itemId);
}
