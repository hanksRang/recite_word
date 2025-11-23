package com.myplus.engl.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.myplus.engl.common.*;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.myplus.engl.service.IEngExaSentService;
import com.myplus.engl.entity.EngExaSent;
import lombok.extern.slf4j.Slf4j;
import java.util.Arrays;
import java.util.List;


/**
 *
 * @date 2025-11-08
 */
 

@Slf4j
@Api(tags = "例句")
@CrossOrigin(origins = "*")  // 允许所有来源
@RestController
@RequestMapping("/engExaSent")
public class EngExaSentController extends AbstractController<EngExaSent> {
    

    @Autowired
    public IEngExaSentService iEngExaSentService;

    /**
     * 分页查询数据
     * @param pagePojo 分页信息
     * @param engExaSent 查询条件
     * @return
     */
    @ApiOperation(value = "分页查询例句")
    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public Response page(@RequestBody final EngExaSent engExaSent,
                         @ModelAttribute final PagePojo pagePojo) {
        return new ResponseCallBack() {
            @Override
            public void execute(ResponseCriteria criteria, Object... objects) {
                IPage<EngExaSent> page = iEngExaSentService.selectPage(pagePojo, engExaSent);
                criteria.addSingleResult(pageToResult(page));
            }
        }.sendRequest();
    }

    /**
     * 保存和修改
     * @param engExaSent  传递的实体
     * @return
     */
    @ApiOperation(value = "保存/修改例句")
    @RequestMapping(path = "save", method = RequestMethod.POST)
    public Response add(@RequestBody final  EngExaSent engExaSent, BindingResult bin) {
        return new ResponseCallBack() {
            @Override
            public void execute(ResponseCriteria criteria, Object... objects) {
                iEngExaSentService.saveOrUpdate(engExaSent);
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
				EngExaSent data = iEngExaSentService.getById(id);
				criteria.addSingleResult(data);
			}
		}.sendRequest();
	}

    /**
    * 根据id删除对象
    * @param ids  实体ID
    * @return
    */
    @ApiOperation(value = "根据id删除例句")
    @RequestMapping(path = "/{ids}", method = RequestMethod.DELETE)
    public Response delete(@PathVariable final String ids){
        return new ResponseCallBack() {
            @Override
            public void execute(ResponseCriteria criteria, Object... objects) {
                List<String> idList = Arrays.asList(ids.split(","));
                iEngExaSentService.removeByIds(idList);
            }
        }.sendRequest();
    }

}
