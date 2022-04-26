package com.partick.forum.common.vo;

import lombok.Data;

/**
 * @author partick_peng
 */
@Data
public class QuartzJobVO{
    private String key;

    private String jobName;

    private String jobGroupName;

    private String cron;

    private String jobStatus;

    private String description;
}
