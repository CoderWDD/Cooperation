package com.example.cooperationproject.controller.itemController;

import com.example.cooperationproject.pojo.TaskItem;
import com.example.cooperationproject.service.ItemService;
import com.example.cooperationproject.utils.ResultUtil;
import com.example.cooperationproject.utils.result.Message;
import com.example.cooperationproject.utils.result.StatusCode;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
public class GetItemInfoController {

    private final ItemService itemService;

    public GetItemInfoController(ItemService itemService) {
        this.itemService = itemService;
    }

    /**
     * 根据itemId返回Item信息
     * @param itemId
     * @return
     */
    @ResponseBody
    @GetMapping("/item/get/{itemId}")
    public Message getItemInfo(@PathVariable(value = "itemId") int itemId){
        TaskItem taskItem = itemService.FindItemById(itemId);

        if (Objects.isNull(taskItem)){
            return ResultUtil.error(StatusCode.BadRequest,"ItemId出错！");
        }

        return ResultUtil.success(taskItem);
    }
}
