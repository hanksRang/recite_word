package com.myplus.engl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.myplus.engl.common.Response;
import com.myplus.engl.entity.EngPic;
import com.myplus.engl.enums.ImageFormat;
import com.myplus.engl.mapper.EngPicMapper;
import com.myplus.engl.service.IEngPicService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.apache.commons.lang.StringUtils;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @date 2025-11-15
 */
@Slf4j
@Service
@Transactional(propagation= Propagation.NESTED,isolation= Isolation.DEFAULT,readOnly = false,rollbackFor=Exception.class)
public class EngPicServiceImpl extends ServiceImpl<EngPicMapper, EngPic> implements IEngPicService {


    @Autowired
    private EngPicMapper engPicMapper;
    @Value("${pic.uploaddir.base}")
    private String fileBasePath;
    /**
     *  分页查询
     * @param page 分页信息
     * @param engPic
     * @return
     */
    @Override
    public IPage<EngPic> selectPage(Page<EngPic> page, EngPic engPic) {

        QueryWrapper<EngPic> queryWrapper = null;
        if (engPic != null) {
            queryWrapper = getQueryWrapper(engPic);
        }
        IPage<EngPic> engPicIPage = engPicMapper.selectPage(page, queryWrapper);
        return engPicIPage;
    }

    @Override
    public Response writeLocalFile(InputStream inputStream, String fileName, Integer wordId) {
        log.info("项目路径：{}",fileBasePath);
        String suffix = fileName.substring(fileName.indexOf(".") + 1);
        // 只允许指定类型上传
        if ( !ImageFormat.isSupportedFormat(suffix)) {
            return Response.error(500, "");
        }
        // 数据库存储的fileName字段
        fileName = fileName.substring(0, fileName.indexOf("."));
        Long storeFileName = System.currentTimeMillis();
        LocalDate today = LocalDate.now();
        int year = today.getYear();
        int month = today.getMonthValue();
        String subDir = year + "-" + month;
        // 数据库存储的filePath字段
        String filePath = "/" + subDir + "/" + storeFileName + "." + suffix;
        String storeFilePath = fileBasePath + filePath;
        FileOutputStream fos = null;
        try {
            Path path = Paths.get(storeFilePath);
            if(!Files.exists(path.getParent())){
                Files.createDirectories(path.getParent());
            }
            fos = new FileOutputStream(storeFilePath);
            byte[] buf = new byte[1024];
            while((inputStream.read(buf))>0){
                fos.write(buf,0,buf.length);
            }
            fos.close();
            log.info("写入成功！");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(fos != null){
                try {
                    fos.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        EngPic engPic = new EngPic();
        engPic.setFileName(fileName + "." + suffix);
        engPic.setFilePath(filePath);
        engPic.setWordId(wordId);
        engPicMapper.insert(engPic);
        return Response.ok("成功");
    }

    @Override
    public void flushImage(Integer id, HttpServletResponse response) {
        EngPic engPic = engPicMapper.selectById(id);
        String relativePath = engPic.getFilePath();
        String path = fileBasePath + relativePath;
        File imageFile = new File(path);
        // 输出图片流
        try (FileInputStream fis = new FileInputStream(imageFile);
             OutputStream os = response.getOutputStream()) {

            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List getImageList(Integer wordId) {
        QueryWrapper<EngPic> engPicQueryWrapper = new QueryWrapper<>();
        engPicQueryWrapper.eq("word_id", wordId);
        List<EngPic> engPicList = engPicMapper.selectList(engPicQueryWrapper);
        List<Integer> resList = new ArrayList<>();
        for(EngPic engPic : engPicList) {
            resList.add(engPic.getId());
        }
        return resList;
    }

    /**
     *  公共查询条件
     * @param engPic
     * @return
     */
    public QueryWrapper<EngPic> getQueryWrapper(EngPic engPic){
        QueryWrapper<EngPic> queryWrapper = new QueryWrapper<>();
        //条件拼接
            if (engPic.getWordId() != null){
                queryWrapper.eq(EngPic.WORD_ID,engPic.getWordId());
            }

            if (StringUtils.isNotBlank(engPic.getFileName())){
                queryWrapper.eq(EngPic.FILENAME,engPic.getFileName());
            }

            if (StringUtils.isNotBlank(engPic.getFilePath())){
                queryWrapper.eq(EngPic.FILEPATH,engPic.getFilePath());
            }

        return queryWrapper;
    }
}
