package com.myplus.engl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.myplus.engl.entity.EngCet6;
import com.myplus.engl.entity.EngExaSent;
import com.myplus.engl.entity.EngPic;
import com.myplus.engl.entity.EngWordRel;
import com.myplus.engl.mapper.EngCet6Mapper;
import com.myplus.engl.mapper.EngExaSentMapper;
import com.myplus.engl.mapper.EngPicMapper;
import com.myplus.engl.mapper.EngWordRelMapper;
import com.myplus.engl.service.IEngCet6Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.myplus.engl.service.IEngPicService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.apache.commons.lang.StringUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @date 2025-11-03
 */
@Slf4j
@Service
@Transactional(propagation= Propagation.NESTED,isolation= Isolation.DEFAULT,readOnly = false,rollbackFor=Exception.class)
public class EngCet6ServiceImpl extends ServiceImpl<EngCet6Mapper, EngCet6> implements IEngCet6Service {


    @Autowired
    private EngCet6Mapper engCet6Mapper;
    @Autowired
    private EngWordRelMapper engWordRelMapper;
    @Autowired
    private EngExaSentMapper engExaSentMapper;
    @Autowired
    private EngPicMapper engPicMapper;
    @Autowired
    private IEngPicService engPicService;

    /**
     *  分页查询
     * @param page 分页信息
     * @param engCet6
     * @return
     */
    @Override
    public IPage<EngCet6> selectPage(Page<EngCet6> page, EngCet6 engCet6) {

        QueryWrapper<EngCet6> queryWrapper = null;
        if (engCet6 != null) {
            queryWrapper = getQueryWrapper(engCet6);
        }
        // 强制乱序返回
        // queryWrapper.last("ORDER BY RAND()");
        IPage<EngCet6> engCet6IPage = engCet6Mapper.selectPage(page, queryWrapper);
        // 显示关联单词
        List<EngCet6> list = engCet6IPage.getRecords();
        for(int i = 0; i < list.size(); i++) {
            Integer id = list.get(i).getId();
            QueryWrapper<EngWordRel> queryWrapperRel = new QueryWrapper<>();
            queryWrapperRel.eq("word1_id", id);
            List<EngWordRel> engWordRelList = engWordRelMapper.selectList(queryWrapperRel);
            StringBuilder sb = new StringBuilder();
            int j = 0;
            for(EngWordRel engWordRel : engWordRelList) {
                int word2Id = engWordRel.getWord2Id();
                EngCet6 engCet6Rec = engCet6Mapper.selectById(word2Id);
                sb.append(" ( " + ++ j + " ) ");
                sb.append(engCet6Rec.getWord());
                sb.append(" ");
                sb.append(engCet6Rec.getMeaning());
                sb.append(" ");
                list.get(i).setRelatedWord(sb.toString());
            }
            QueryWrapper<EngExaSent> exaSentQueryWrapper = new QueryWrapper<>();
            exaSentQueryWrapper.eq("word_id", id);
            List<EngExaSent> engExaSentList = engExaSentMapper.selectList(exaSentQueryWrapper);
            sb = new StringBuilder();
            j = 0;
            for (EngExaSent engExaSent : engExaSentList) {
                sb.append(" ( " + ++ j + " ) ");
                sb.append(engExaSent.getExample());
            }
            list.get(i).setExample(sb.toString());
            // 有无图片展示
            QueryWrapper<EngPic> engPicQueryWrapper = new QueryWrapper<>();
            engPicQueryWrapper.eq("word_id", id);
            Integer count = engPicMapper.selectCount(engPicQueryWrapper);
            if (count > 0) {
                list.get(i).setHasPic(1);
                List<Integer> engPicServiceImageList = engPicService.getImageList(id);
                list.get(i).setPicList(engPicServiceImageList);
            }
        }
        return engCet6IPage;
    }

    @Override
    public IPage<EngCet6> selectPageBlur(Page<EngCet6> page, EngCet6 engCet6) {

        QueryWrapper<EngCet6> queryWrapper = null;
        if (engCet6 != null) {
            queryWrapper = new QueryWrapper<>();
            queryWrapper.like("word", engCet6.getWord());
        }
        // 强制乱序返回
        IPage<EngCet6> engCet6IPage = engCet6Mapper.selectPage(page, queryWrapper);
        return engCet6IPage;
    }

    @Override
    public Integer selectCount(EngCet6 engCet6) {
        QueryWrapper<EngCet6> queryWrapper = null;
        if (engCet6 != null) {
            queryWrapper = getQueryWrapper(engCet6);
        }
        Integer count = engCet6Mapper.selectCount(queryWrapper);
        return count;
    }

    /**
     *  公共查询条件
     * @param engCet6
     * @return
     */
    public QueryWrapper<EngCet6> getQueryWrapper(EngCet6 engCet6){
        QueryWrapper<EngCet6> queryWrapper = new QueryWrapper<>();
        //条件拼接
            if (StringUtils.isNotBlank(engCet6.getWord())){
                // queryWrapper.eq(EngCet6.WORD,engCet6.getWord());
                queryWrapper.like("word", engCet6.getWord());
            }

            if (StringUtils.isNotBlank(engCet6.getPhonetic())){
                queryWrapper.eq(EngCet6.PHONETIC,engCet6.getPhonetic());
            }

            if (StringUtils.isNotBlank(engCet6.getMeaning())){
                queryWrapper.eq(EngCet6.MEANING,engCet6.getMeaning());
            }

            if (engCet6.getHasMastered() != null){
                queryWrapper.eq(EngCet6.HAS_MASTERED,engCet6.getHasMastered());
            }

            if (engCet6.getInsertTime() != null){
                queryWrapper.eq(EngCet6.INSERT_TIME,engCet6.getInsertTime());
            }

            if (engCet6.getUpdateTime() != null){
                queryWrapper.eq(EngCet6.UPDATE_TIME,engCet6.getUpdateTime());
            }

        return queryWrapper;
    }
}
