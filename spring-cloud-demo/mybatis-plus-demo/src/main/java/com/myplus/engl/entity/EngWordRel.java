package com.myplus.engl.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 关联单词
 * </p>
 *
 * @date 2025-11-07
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("eng_word_rel")
public class EngWordRel extends Model<EngWordRel> {

                    @TableId(value="id", type= IdType.AUTO)
                            private Integer id;

    @TableField("word1_id")
                    private Integer word1Id;

    @TableField("word2_id")
                    private Integer word2Id;

    @TableField("insert_time")
                    private LocalDateTime insertTime;

    @TableField("last_update_time")
                    private LocalDateTime lastUpdateTime;

    


    public static final String ID = "id";

    public static final String WORD1_ID = "word1_id";

    public static final String WORD2_ID = "word2_id";

    public static final String INSERT_TIME = "insert_time";

    public static final String LAST_UPDATE_TIME = "last_update_time";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
