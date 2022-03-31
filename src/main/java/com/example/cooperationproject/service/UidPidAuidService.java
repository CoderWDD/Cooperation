package com.example.cooperationproject.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.cooperationproject.pojo.UidPidAuId;

public interface UidPidAuidService extends IService<UidPidAuId> {
    boolean InsertUidPidAuid(UidPidAuId uidPidAuId);

    UidPidAuId FindUidPidAuidByUidPid(Integer userId,Integer projectId);

    boolean DeleteUidPidAuid(Integer userId,Integer projectId);

}
