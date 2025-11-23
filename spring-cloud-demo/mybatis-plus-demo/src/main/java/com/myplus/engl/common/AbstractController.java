package com.myplus.engl.common;

import com.baomidou.mybatisplus.core.metadata.IPage;


/**
 * controller 抽象类
 *
 */
public abstract class AbstractController<T> {

    public QueryResult<T> pageToResult(IPage<T> page) {
        QueryResult<T> result = new QueryResult<T>();
        result.setResultData(page.getRecords());
        result.setTotalRecord(page.getTotal());
        return result;
    }

}