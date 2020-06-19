package com.yc.projects.yc74ibike.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yc.projects.yc74ibike.service.LogService;
import com.yc.projects.yc74ibike.web.model.JsonModel;

@Controller
public class LogController {

    @Autowired
    private LogService logService;

    @PostMapping("/log/savelog")   //只支持  POST请求
    @ResponseBody   //回送给客户端的是一个json数据
    public JsonModel ready( JsonModel jsonModel,@RequestBody String log) {
        logService.save(log);
        jsonModel.setCode(1);
        return jsonModel;
    }
}