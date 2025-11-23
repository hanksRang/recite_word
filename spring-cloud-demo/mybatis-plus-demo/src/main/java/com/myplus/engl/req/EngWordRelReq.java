package com.myplus.engl.req;

import lombok.Data;

import java.util.List;

@Data
public class EngWordRelReq {

    private int mainId;

    private List<Integer> ids;

}
