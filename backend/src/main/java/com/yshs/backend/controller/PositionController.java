package com.yshs.backend.controller;

import com.yshs.backend.entity.Position;
import com.yshs.backend.entity.RestBean;
import com.yshs.backend.service.IPositionService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author baomidou
 * @since 2023-09-26
 */
@RestController
@RequestMapping("/api/position")
public class PositionController {

    @Resource
    IPositionService positionService;

    @GetMapping("/getList")
    public RestBean<List<String>> getList() {
        List<String> PositionNameList = positionService.list()
                .stream()
                .map(Position::getPosName)
                .toList();
        return RestBean.success(PositionNameList);
    }
}
