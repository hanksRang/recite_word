package com.myplus.engl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.myplus.engl.common.Response;
import com.myplus.engl.entity.EngWordRel;
import com.myplus.engl.mapper.EngWordRelMapper;
import com.myplus.engl.req.EngWordRelReq;
import com.myplus.engl.service.IEngWordRelService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * <p>
 * 关联单词服务实现类
 * </p>
 *
 * @date 2025-11-07
 */
@Slf4j
@Service
@Transactional(propagation= Propagation.NESTED,isolation= Isolation.DEFAULT,readOnly = false,rollbackFor=Exception.class)
public class EngWordRelServiceImpl extends ServiceImpl<EngWordRelMapper, EngWordRel> implements IEngWordRelService {


    @Autowired
    private EngWordRelMapper engWordRelMapper;

    /**
     *  分页查询
     * @param page 分页信息
     * @param engWordRel
     * @return
     */
    @Override
    public IPage<EngWordRel> selectPage(Page<EngWordRel> page, EngWordRel engWordRel) {

        QueryWrapper<EngWordRel> queryWrapper = null;
        if (engWordRel != null) {
            queryWrapper = getQueryWrapper(engWordRel);
        }
        IPage<EngWordRel> engWordRelIPage = engWordRelMapper.selectPage(page, queryWrapper);
        return engWordRelIPage;
    }

    @Override
    public Response save(EngWordRelReq engWordRelReq) {
        Integer mainId = engWordRelReq.getMainId();
        List<Integer> ids = engWordRelReq.getIds();
        if (ids == null || ids.size() < 1) {
            return Response.error(500, "未选择任何关联单词");
        }
        for (Integer id : ids) {
            if (mainId == id) {
                continue;
            }
            EngWordRel engWordRel = new EngWordRel();
            engWordRel.setWord1Id(mainId);
            engWordRel.setWord2Id(id);
            engWordRelMapper.insert(engWordRel);
            // 调转位置
            engWordRel.setWord1Id(id);
            engWordRel.setWord2Id(mainId);
            engWordRelMapper.insert(engWordRel);
        }
        return Response.ok("插入完成");
    }

    /**
     *  公共查询条件
     * @param engWordRel
     * @return
     */
    public QueryWrapper<EngWordRel> getQueryWrapper(EngWordRel engWordRel){
        QueryWrapper<EngWordRel> queryWrapper = new QueryWrapper<>();
        //条件拼接
            if (engWordRel.getWord1Id() != null){
                queryWrapper.eq(EngWordRel.WORD1_ID,engWordRel.getWord1Id());
            }

            if (engWordRel.getWord2Id() != null){
                queryWrapper.eq(EngWordRel.WORD2_ID,engWordRel.getWord2Id());
            }

            if (engWordRel.getInsertTime() != null){
                queryWrapper.eq(EngWordRel.INSERT_TIME,engWordRel.getInsertTime());
            }

            if (engWordRel.getLastUpdateTime() != null){
                queryWrapper.eq(EngWordRel.LAST_UPDATE_TIME,engWordRel.getLastUpdateTime());
            }

        return queryWrapper;
    }
}
