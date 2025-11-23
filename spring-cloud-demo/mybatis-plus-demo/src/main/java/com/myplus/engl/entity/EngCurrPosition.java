package com.myplus.engl.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.IdType;
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
 * @date 2025-11-07
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("eng_curr_position")
public class EngCurrPosition extends Model<EngCurrPosition> {

                    @TableId(value="id", type= IdType.AUTO)
                            private Integer id;

    @TableField("curr_pos")
                    private Integer currPos;

    


    public static final String ID = "id";

    public static final String CURR_POS = "curr_pos";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
