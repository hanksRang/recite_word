package com.myplus.engl.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.myplus.engl.common.*;
import com.myplus.engl.req.EngWordRelReq;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.myplus.engl.service.IEngWordRelService;
import com.myplus.engl.entity.EngWordRel;
import lombok.extern.slf4j.Slf4j;
import java.util.Arrays;
import java.util.List;


/**
 *
 * @date 2025-11-07
 */
 

@Slf4j
@Api(tags = "关联单词")
@CrossOrigin(origins = "*")  // 允许所有来源
@RestController
@RequestMapping("/engWordRel")
public class EngWordRelController extends AbstractController<EngWordRel> {
    

    @Autowired
    public IEngWordRelService iEngWordRelService;

    /**
     * 分页查询数据
     * @param pagePojo 分页信息
     * @param engWordRel 查询条件
     * @return
     */
    @ApiOperation(value = "分页查询关联单词")
    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public Response page(@ModelAttribute final EngWordRel engWordRel,
                         @ModelAttribute final PagePojo pagePojo) {
        return new ResponseCallBack() {
            @Override
            public void execute(ResponseCriteria criteria, Object... objects) {
                IPage<EngWordRel> page = iEngWordRelService.selectPage(pagePojo, engWordRel);
                criteria.addSingleResult(pageToResult(page));
            }
        }.sendRequest();
    }

    /**
     * 保存和修改
     * @param engWordRel  传递的实体
     * @return
     */
    @ApiOperation(value = "保存/修改关联单词")
    @RequestMapping(path = "save", method = RequestMethod.POST)
    public Response add(@RequestBody final EngWordRelReq engWordRel, BindingResult bin) {
        return new ResponseCallBack() {
            @Override
            public void execute(ResponseCriteria criteria, Object... objects) {
                iEngWordRelService.save(engWordRel);
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
				EngWordRel data = iEngWordRelService.getById(id);
				criteria.addSingleResult(data);
			}
		}.sendRequest();
	}

    /**
    * 根据id删除对象
    * @param ids  实体ID
    * @return
    */
    @ApiOperation(value = "根据id删除关联单词")
    @RequestMapping(path = "/{ids}", method = RequestMethod.DELETE)
    public Response delete(@PathVariable final String ids){
        return new ResponseCallBack() {
            @Override
            public void execute(ResponseCriteria criteria, Object... objects) {
                List<String> idList = Arrays.asList(ids.split(","));
                iEngWordRelService.removeByIds(idList);
            }
        }.sendRequest();
    }

}
