package com.myplus.engl.common;

import java.util.List;

/**
 * 类名称：QueryResult
 * @version 1.0.0      
 */
public class QueryResult<T> {
    
    /**返回的结果集*/
    private List<T> resultData;
    
    /**总记录数*/
    private long totalRecord;

    /**    
     * resultData    
     * @return  the resultData       
     */
    
    public List<T> getResultData() {
        return resultData;
    }

    /**    
     * @param resultData the resultData to set    
     */
    public void setResultData(List<T> resultData) {
        this.resultData = resultData;
    }

    /**    
     * totalRecord    
     * @return  the totalRecord       
     */
    
    public long getTotalRecord() {
        return totalRecord;
    }

    /**    
     * @param totalRecord the totalRecord to set    
     */
    public void setTotalRecord(long totalRecord) {
        this.totalRecord = totalRecord;
    }
}
