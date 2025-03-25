package org.venti.common.util;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Xieningjun
 * @date 2025/2/25 12:12
 * @description 提供List相关的工具方法
 */
public class ListUtil {

    /**
     * 将一个列表拆分为多个子列表，每个子列表的大小不超过指定的chunkSize
     *
     * @param tList 要拆分的原始列表
     * @param chunkSize 每个子列表的最大大小
     * @param <T> 列表中元素的类型
     * @return 返回拆分后的子列表列表
     * @throws IllegalArgumentException 如果传入的列表为null或chunkSize小于等于0，抛出异常
     */
    public static <T> List<List<T>> chunk(List<T> tList, int chunkSize) {
        // 检查参数是否合法
        if (tList == null || chunkSize <= 0) {
            throw new IllegalArgumentException("List must not be null and chunkSize must be greater than 0.");
        }

        // 存储拆分结果
        var result = new ArrayList<List<T>>();
        var size = tList.size();

        // 遍历原始列表，按指定的chunkSize拆分
        for (var i = 0; i < size; i += chunkSize) {
            var end = Math.min(size, i + chunkSize); // 计算子列表的结束位置
            var subList = tList.subList(i, end);    // 获取当前子列表
            result.add(subList);                    // 添加到结果列表中
        }

        // 返回拆分后的子列表
        return result;
    }
}
