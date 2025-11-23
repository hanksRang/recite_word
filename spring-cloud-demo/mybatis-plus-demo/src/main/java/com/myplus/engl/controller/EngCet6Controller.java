package com.myplus.engl.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.myplus.engl.common.*;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.myplus.engl.service.IEngCet6Service;
import com.myplus.engl.entity.EngCet6;
import lombok.extern.slf4j.Slf4j;
import java.util.Arrays;
import java.util.List;


/**
 *
 * @date 2025-11-03
 */
 

@Slf4j
@Api(tags = "")
@CrossOrigin(origins = "*")  // 允许所有来源
@RestController
@RequestMapping("/engCet6")
public class EngCet6Controller extends AbstractController<EngCet6> {
    

    @Autowired
    public IEngCet6Service iEngCet6Service;

    /**
     * 分页查询数据
     * @param pagePojo 分页信息
     * @param engCet6 查询条件
     * @return
     */
    @ApiOperation(value = "分页查询")
    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public Response page(@RequestBody final EngCet6 engCet6,
                         @ModelAttribute final PagePojo pagePojo) {
        return new ResponseCallBack() {
            @Override
            public void execute(ResponseCriteria criteria, Object... objects) {
                pagePojo.setSize(100);
                IPage<EngCet6> page = iEngCet6Service.selectPage(pagePojo, engCet6);
                criteria.addSingleResult(pageToResult(page));
            }
        }.sendRequest();
    }

    /**
     * 分页查询数据
     * @param pagePojo 分页信息
     * @param engCet6 查询条件
     * @return
     */
    @ApiOperation(value = "分页查询")
    @RequestMapping(value = "/pageFirst10", method = RequestMethod.POST)
    public Response pageFirst10(@RequestBody final EngCet6 engCet6,
                                @ModelAttribute final PagePojo pagePojo) {
        return new ResponseCallBack() {
            @Override
            public void execute(ResponseCriteria criteria, Object... objects) {
                pagePojo.setSize(10);
                IPage<EngCet6> page = iEngCet6Service.selectPageBlur(pagePojo, engCet6);
                criteria.addSingleResult(pageToResult(page));
            }
        }.sendRequest();
    }

    @ApiOperation(value = "分页查询")
    @RequestMapping(value = "/selCount", method = RequestMethod.POST)
    public Response selCount(@RequestBody final EngCet6 engCet6,
                         @ModelAttribute final PagePojo pagePojo) {
        return new ResponseCallBack() {
            @Override
            public void execute(ResponseCriteria criteria, Object... objects) {
                Integer count = iEngCet6Service.selectCount(engCet6);
                criteria.addSingleResult(count);
            }
        }.sendRequest();
    }

    /**
     * 保存和修改
     * @param engCet6  传递的实体
     * @return
     */
    @ApiOperation(value = "保存/修改")
    @RequestMapping(path = "/save", method = RequestMethod.POST)
    public Response add(@RequestBody final  EngCet6 engCet6, BindingResult bin) {
        return new ResponseCallBack() {
            @Override
            public void execute(ResponseCriteria criteria, Object... objects) {
                iEngCet6Service.saveOrUpdate(engCet6);
            }
        }.sendRequest();
    }
    
    /**
	 * 获取单条记录
	 * 
	 * @return
	 */
	@ApiOperation(value = "获取单条记录")
	@RequestMapping(value = "/query/{id}", method = RequestMethod.GET)
	public Response one(@PathVariable final String id) {
		return new ResponseCallBack() {
			@Override
			public void execute(ResponseCriteria criteria, Object... objects) {
				EngCet6 data = iEngCet6Service.getById(id);
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
                iEngCet6Service.removeByIds(idList);
            }
        }.sendRequest();
    }

}
