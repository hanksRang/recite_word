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
 * 例句
 * </p>
 *
 * @date 2025-11-08
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("eng_exa_sent")
public class EngExaSent extends Model<EngExaSent> {

                    @TableId(value="id", type= IdType.AUTO)
                            private Integer id;

    @TableField("word_id")
                    private Integer wordId;

    @TableField("example")
                    private String example;

    @TableField("insert_time")
                    private LocalDateTime insertTime;

    @TableField("last_update_time")
                    private LocalDateTime lastUpdateTime;

    


    public static final String ID = "id";

    public static final String WORD_ID = "word_id";

    public static final String EXAMPLE = "example";

    public static final String INSERT_TIME = "insert_time";

    public static final String LAST_UPDATE_TIME = "last_update_time";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
