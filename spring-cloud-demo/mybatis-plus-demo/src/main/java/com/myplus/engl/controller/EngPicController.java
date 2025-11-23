package com.myplus.engl.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.myplus.engl.common.*;
import com.myplus.engl.entity.EngCet6;
import com.myplus.engl.entity.EngPic;
import com.myplus.engl.service.IEngPicService;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @date 2025-11-15
 */
 

@Slf4j
@Api(tags = "")
@CrossOrigin(origins = "*")  // 允许所有来源
@RestController
@RequestMapping("/engPic")
public class EngPicController extends AbstractController<EngPic> {
    

    @Autowired
    public IEngPicService iEngPicService;

    @ApiOperation("上传一个文件到本地")
    @PostMapping("/upload")
    public Response upload(EngCet6 engCet6, MultipartFile file){
        InputStream is = null;
        try {
            is = file.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String fileName = file.getOriginalFilename();
        Response response = iEngPicService.writeLocalFile(is,fileName, engCet6.getId());
        return response;
    }

    @GetMapping("/image/{id}")
    public void getImage(@PathVariable Integer id,
                         HttpServletResponse response) throws IOException {
        iEngPicService.flushImage(id, response);
    }



    /**
     * 分页查询数据
     * @param pagePojo 分页信息
     * @param engPic 查询条件
     * @return
     */
    @ApiOperation(value = "分页查询")
    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public Response page(@ModelAttribute final EngPic engPic,
                         @ModelAttribute final PagePojo pagePojo) {
        return new ResponseCallBack() {
            @Override
            public void execute(ResponseCriteria criteria, Object... objects) {
                IPage<EngPic> page = iEngPicService.selectPage(pagePojo, engPic);
                criteria.addSingleResult(pageToResult(page));
            }
        }.sendRequest();
    }

    /**
     * 保存和修改
     * @param engPic  传递的实体
     * @return
     */
    @ApiOperation(value = "保存/修改")
    @RequestMapping(path = "", method = RequestMethod.PUT)
    public Response add(@RequestBody final  EngPic engPic, BindingResult bin) {
        return new ResponseCallBack() {
            @Override
            public void execute(ResponseCriteria criteria, Object... objects) {
                iEngPicService.saveOrUpdate(engPic);
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
				EngPic data = iEngPicService.getById(id);
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
                iEngPicService.removeByIds(idList);
            }
        }.sendRequest();
    }

}
