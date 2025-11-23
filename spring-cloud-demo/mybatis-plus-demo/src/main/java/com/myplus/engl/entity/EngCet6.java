package com.myplus.engl.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.List;

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
 * @date 2025-11-03
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("eng_cet6")
public class EngCet6 extends Model<EngCet6> {

    @TableId(value="id", type= IdType.AUTO)
    private Integer id;

    @TableField("word")
    private String word;

    @TableField("phonetic")
    private String phonetic;

    @TableField("meaning")
    private String meaning;

    @TableField("related_word")
    private String relatedWord;

    @TableField(exist = false)
    private String example;

    @TableField("has_mastered")
    private Integer hasMastered = 0;

    @TableField("not_recognize")
    private Integer notRecognize = 0;

    @TableField(exist = false)
    private Integer hasPic = 0;

    @TableField(exist = false)
    private List<Integer> picList;

    @TableField("insert_time")
    private LocalDateTime insertTime;

    @TableField("update_time")
    private LocalDateTime updateTime;


    public static final String ID = "id";

    public static final String WORD = "word";

    public static final String PHONETIC = "phonetic";

    public static final String MEANING = "meaning";

    public static final String HAS_MASTERED = "has_mastered";

    public static final String INSERT_TIME = "insert_time";

    public static final String UPDATE_TIME = "update_time";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
