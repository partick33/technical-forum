package com.partick.forum.common.elasticsearch.pojo;

import lombok.Data;

/**
 * @author partick_peng
 */
@Data
public class EsKeyWordCount {

    private String keyWord;
    private Long docCount;
}
