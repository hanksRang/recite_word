package com.myplus.engl.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.myplus.engl.common.Response;
import com.myplus.engl.entity.EngWordRel;
import com.baomidou.mybatisplus.extension.service.IService;
import com.myplus.engl.req.EngWordRelReq;

/**
 * <p>
 * 关联单词服务类
 * </p>
 *
 * @date 2025-11-07
 */
public interface IEngWordRelService extends IService<EngWordRel> {

    /**
     *  分页查询
     * @param page 页面信息
     * @param engWordRel
     * @return
     */
    IPage<EngWordRel> selectPage(Page<EngWordRel> page, EngWordRel engWordRel);

    Response save(EngWordRelReq engWordRelReq);

}
