package com.jx.tennis.vo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WxUserVo implements Serializable {

    /**
     * 序列化ID
     */
    private static final long serialVersionUID = -4L;

    

    private String openId;
    private String name;
    private String headUrl;
    private List<Map<String, String>> list;



}