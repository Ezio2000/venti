package org.venti.common.worker;

/**
 * @author Xieningjun
 * @date 2025/2/25 13:56
 * @description 工作者状态枚举，表示工作者的生命周期状态
 */
public enum WorkerState {

    /**
     * 初始化状态，表示工作者尚未开始执行
     */
    INITIAL,

    /**
     * 运行中状态，表示工作者正在执行任务
     */
    RUNNING,

    /**
     * 关闭状态，表示工作者已停止或被关闭
     */
    SHUTDOWN
}