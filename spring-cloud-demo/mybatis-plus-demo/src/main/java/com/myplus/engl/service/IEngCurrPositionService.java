package com.myplus.engl.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.myplus.engl.entity.EngCurrPosition;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @date 2025-11-07
 */
public interface IEngCurrPositionService extends IService<EngCurrPosition> {

    /**
     *  分页查询
     * @param page 页面信息
     * @param engCurrPosition
     * @return
     */
    IPage<EngCurrPosition> selectPage(Page<EngCurrPosition> page, EngCurrPosition engCurrPosition);

}
