package com.myplus.engl.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.myplus.engl.common.Response;
import com.myplus.engl.entity.EngPic;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @date 2025-11-15
 */
public interface IEngPicService extends IService<EngPic> {

    /**
     *  分页查询
     * @param page 页面信息
     * @param engPic
     * @return
     */
    IPage<EngPic> selectPage(Page<EngPic> page, EngPic engPic);

    Response writeLocalFile(InputStream inputStream, String fileName, Integer wordId);

    void flushImage(Integer id, HttpServletResponse response);

    List<Integer> getImageList(Integer wordId);

}
