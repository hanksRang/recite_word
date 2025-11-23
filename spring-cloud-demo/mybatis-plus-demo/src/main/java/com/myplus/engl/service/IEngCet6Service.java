package com.myplus.engl.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.myplus.engl.entity.EngCet6;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @date 2025-11-03
 */
public interface IEngCet6Service extends IService<EngCet6> {

    /**
     *  分页查询
     * @param page 页面信息
     * @param engCet6
     * @return
     */
    IPage<EngCet6> selectPage(Page<EngCet6> page, EngCet6 engCet6);

    IPage<EngCet6> selectPageBlur(Page<EngCet6> page, EngCet6 engCet6);

    Integer selectCount(EngCet6 engCet6);

}
