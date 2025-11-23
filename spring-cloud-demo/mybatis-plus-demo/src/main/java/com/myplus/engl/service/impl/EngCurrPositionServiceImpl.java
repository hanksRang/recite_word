package com.myplus.engl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.myplus.engl.entity.EngCurrPosition;
import com.myplus.engl.mapper.EngCurrPositionMapper;
import com.myplus.engl.service.IEngCurrPositionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @date 2025-11-07
 */
@Slf4j
@Service
@Transactional(propagation= Propagation.NESTED,isolation= Isolation.DEFAULT,readOnly = false,rollbackFor=Exception.class)
public class EngCurrPositionServiceImpl extends ServiceImpl<EngCurrPositionMapper, EngCurrPosition> implements IEngCurrPositionService {


    @Autowired
    private EngCurrPositionMapper engCurrPositionMapper;

    /**
     *  分页查询
     * @param page 分页信息
     * @param engCurrPosition
     * @return
     */
    @Override
    public IPage<EngCurrPosition> selectPage(Page<EngCurrPosition> page, EngCurrPosition engCurrPosition) {

        QueryWrapper<EngCurrPosition> queryWrapper = null;
        if (engCurrPosition != null) {
            queryWrapper = getQueryWrapper(engCurrPosition);
        }
        IPage<EngCurrPosition> engCurrPositionIPage = engCurrPositionMapper.selectPage(page, queryWrapper);
        return engCurrPositionIPage;
    }

    /**
     *  公共查询条件
     * @param engCurrPosition
     * @return
     */
    public QueryWrapper<EngCurrPosition> getQueryWrapper(EngCurrPosition engCurrPosition){
        QueryWrapper<EngCurrPosition> queryWrapper = new QueryWrapper<>();
        //条件拼接
            if (engCurrPosition.getCurrPos() != null){
                queryWrapper.eq(EngCurrPosition.CURR_POS,engCurrPosition.getCurrPos());
            }

        return queryWrapper;
    }
}
