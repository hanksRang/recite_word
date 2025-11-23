package com.myplus.engl.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.myplus.engl.common.*;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import org.springframework.beans.factory.annotation.Autowired;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import com.myplus.engl.service.IEngCurrPositionService;
import com.myplus.engl.entity.EngCurrPosition;
import lombok.extern.slf4j.Slf4j;
import java.util.Arrays;
import java.util.List;



/**
 *
 * @date 2025-11-07
 */
 

@Slf4j
@Api(tags = "")
@CrossOrigin(origins = "*")  // 允许所有来源
@RestController
@RequestMapping("/engCurrPosition")
public class EngCurrPositionController extends AbstractController<EngCurrPosition> {
    

    @Autowired
    public IEngCurrPositionService iEngCurrPositionService;

    /**
     * 分页查询数据
     * @param pagePojo 分页信息
     * @param engCurrPosition 查询条件
     * @return
     */
    @ApiOperation(value = "分页查询")
    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public Response page(@ModelAttribute final EngCurrPosition engCurrPosition,
                         @ModelAttribute final PagePojo pagePojo) {
        return new ResponseCallBack() {
            @Override
            public void execute(ResponseCriteria criteria, Object... objects) {
                IPage<EngCurrPosition> page = iEngCurrPositionService.selectPage(pagePojo, engCurrPosition);
                criteria.addSingleResult(pageToResult(page));
            }
        }.sendRequest();
    }

    /**
     * 保存和修改
     * @param engCurrPosition  传递的实体
     * @return
     */
    @ApiOperation(value = "保存/修改")
    @RequestMapping(path = "", method = RequestMethod.PUT)
    public Response add(@RequestBody final  EngCurrPosition engCurrPosition, BindingResult bin) {
        return new ResponseCallBack() {
            @Override
            public void execute(ResponseCriteria criteria, Object... objects) {
                iEngCurrPositionService.saveOrUpdate(engCurrPosition);
            }
        }.sendRequest();
    }
    
    /**
	 * 获取单条记录
	 * 
	 * @return
	 */
	@ApiOperation(value = "获取单条记录")
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public Response one(@PathVariable final String id) {
		return new ResponseCallBack() {
			@Override
			public void execute(ResponseCriteria criteria, Object... objects) {
				EngCurrPosition data = iEngCurrPositionService.getById(id);
				criteria.addSingleResult(data);
			}
		}.sendRequest();
	}

    /**
    * 根据id删除对象
    * @param ids  实体ID
    * @return
    */
    @ApiOperation(value = "根据id删除")
    @RequestMapping(path = "/{ids}", method = RequestMethod.DELETE)
    public Response delete(@PathVariable final String ids){
        return new ResponseCallBack() {
            @Override
            public void execute(ResponseCriteria criteria, Object... objects) {
                List<String> idList = Arrays.asList(ids.split(","));
                iEngCurrPositionService.removeByIds(idList);
            }
        }.sendRequest();
    }

}
