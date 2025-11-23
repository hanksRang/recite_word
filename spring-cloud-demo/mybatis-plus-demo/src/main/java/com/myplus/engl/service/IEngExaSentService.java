package com.myplus.engl.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.myplus.engl.entity.EngExaSent;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 例句服务类
 * </p>
 *
 * @date 2025-11-08
 */
public interface IEngExaSentService extends IService<EngExaSent> {

    /**
     *  分页查询
     * @param page 页面信息
     * @param engExaSent
     * @return
     */
    IPage<EngExaSent> selectPage(Page<EngExaSent> page, EngExaSent engExaSent);

}
