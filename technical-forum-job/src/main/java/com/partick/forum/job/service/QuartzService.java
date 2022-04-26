package com.partick.forum.job.service;

import com.partick.forum.common.vo.QuartzJobVO;

import java.util.List;

/**
 * @author partick_peng
 */
public interface QuartzService {

    /**
     * 修改一个任务job
     * @param jobName
     * @param jobGroupName
     * @param corn
     */
    void updateJob(String jobName, String jobGroupName, String corn);

    /**
     * 获取所有任务列表
     * @return
     */
    List<QuartzJobVO> queryAllJob();

    /**
     * 暂停任务
     * @param jobName
     * @param jobGroupName
     */
    void pauseJob(String jobName, String jobGroupName);

    /**
     * 启动任务
     * @param jobName
     * @param jobGroupName
     */
    void startJob(String jobName, String jobGroupName);


}
