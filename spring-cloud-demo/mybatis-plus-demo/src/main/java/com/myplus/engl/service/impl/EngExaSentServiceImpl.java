package com.myplus.engl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.myplus.engl.entity.EngExaSent;
import com.myplus.engl.mapper.EngExaSentMapper;
import com.myplus.engl.service.IEngExaSentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.apache.commons.lang.StringUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 例句服务实现类
 * </p>
 *
 * @date 2025-11-08
 */
@Slf4j
@Service
@Transactional(propagation= Propagation.NESTED,isolation= Isolation.DEFAULT,readOnly = false,rollbackFor=Exception.class)
public class EngExaSentServiceImpl extends ServiceImpl<EngExaSentMapper, EngExaSent> implements IEngExaSentService {


    @Autowired
    private EngExaSentMapper engExaSentMapper;

    /**
     *  分页查询
     * @param page 分页信息
     * @param engExaSent
     * @return
     */
    @Override
    public IPage<EngExaSent> selectPage(Page<EngExaSent> page, EngExaSent engExaSent) {

        QueryWrapper<EngExaSent> queryWrapper = null;
        if (engExaSent != null) {
            queryWrapper = getQueryWrapper(engExaSent);
        }
        IPage<EngExaSent> engExaSentIPage = engExaSentMapper.selectPage(page, queryWrapper);
        return engExaSentIPage;
    }

    /**
     *  公共查询条件
     * @param engExaSent
     * @return
     */
    public QueryWrapper<EngExaSent> getQueryWrapper(EngExaSent engExaSent){
        QueryWrapper<EngExaSent> queryWrapper = new QueryWrapper<>();
        //条件拼接
            if (engExaSent.getWordId() != null){
                queryWrapper.eq(EngExaSent.WORD_ID,engExaSent.getWordId());
            }

            if (StringUtils.isNotBlank(engExaSent.getExample())){
                queryWrapper.eq(EngExaSent.EXAMPLE,engExaSent.getExample());
            }

            if (engExaSent.getInsertTime() != null){
                queryWrapper.eq(EngExaSent.INSERT_TIME,engExaSent.getInsertTime());
            }

            if (engExaSent.getLastUpdateTime() != null){
                queryWrapper.eq(EngExaSent.LAST_UPDATE_TIME,engExaSent.getLastUpdateTime());
            }

        return queryWrapper;
    }
}
