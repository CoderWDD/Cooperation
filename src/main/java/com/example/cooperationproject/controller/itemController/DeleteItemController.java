package com.example.cooperationproject.controller.itemController;


import com.example.cooperationproject.service.ItemService;
import com.example.cooperationproject.service.UidTidAuidService;
import com.example.cooperationproject.utils.MyJwtUtil;
import com.example.cooperationproject.utils.ResultUtil;
import com.example.cooperationproject.utils.result.Message;
import com.example.cooperationproject.utils.result.StatusCode;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class DeleteItemController {

    private final UidTidAuidService uidTidAuidService;

    private final HttpServletRequest request;

    private final MyJwtUtil myJwtUtil;

    private final ItemService itemService;

    public DeleteItemController(HttpServletRequest request, MyJwtUtil myJwtUtil, ItemService itemService, UidTidAuidService uidTidAuidService) {
        this.request = request;
        this.myJwtUtil = myJwtUtil;
        this.itemService = itemService;
        this.uidTidAuidService = uidTidAuidService;
    }

    @PostMapping("/item/delete/{itemId}")
    public Message deleteItem(@PathVariable Integer itemId){
        String token = request.getHeader("token");
        int userId = myJwtUtil.getUserIdFromToken(token);
        boolean deleteItemById = itemService.DeleteItemById(userId,itemId);

        if (deleteItemById){
            return ResultUtil.success("任务删除成功！");
        }
        return ResultUtil.error(StatusCode.BadRequest,"任务删除失败！");
    }
}
