package org.venti.common.worker;

/**
 * @author Xieningjun
 * @date 2025/2/25 12:58
 * @description 定义了一个工作者接口，扩展了Runnable，提供工作状态的获取
 */
public interface Worker extends Runnable {

    /**
     * 获取当前工作者的状态
     *
     * @return 返回工作者的当前状态
     */
    WorkerState getState();
}