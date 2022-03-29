package com.example.cooperationproject.controller.itemController;

import com.example.cooperationproject.pojo.TaskItem;
import com.example.cooperationproject.service.ItemService;
import com.example.cooperationproject.utils.ResultUtil;
import com.example.cooperationproject.utils.result.Message;
import com.example.cooperationproject.utils.result.StatusCode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
public class GetItemInfoController {

    private final ItemService itemService;

    public GetItemInfoController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/item/getItemInfo")
    public Message getItemInfo(@RequestParam int itemId){
        TaskItem taskItem = itemService.FindItemById(itemId);

        if (Objects.isNull(taskItem)){
            return ResultUtil.error(StatusCode.BadRequest,"ItemId出错！");
        }

        return ResultUtil.success(taskItem);
    }
}
