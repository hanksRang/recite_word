package com.myplus.engl.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 
 * </p>
 *
 * @date 2025-11-15
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("eng_pic")
public class EngPic extends Model<EngPic> {

                    @TableId(value="id", type= IdType.AUTO)
                            private Integer id;

    @TableField("word_id")
                    private Integer wordId;

    @TableField("fileName")
                    private String fileName;

    @TableField("filePath")
                    private String filePath;

    


    public static final String ID = "id";

    public static final String WORD_ID = "word_id";

    public static final String FILENAME = "fileName";

    public static final String FILEPATH = "filePath";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
