package com.myplus.engl.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cet6ImportExcelDto {

    @ExcelProperty("单词")
    private String word;

    @ExcelProperty("注音")
    private String phonetic;

    @ExcelProperty("释义")
    private String meaning;

}
