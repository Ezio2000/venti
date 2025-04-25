package org.venti.common.struc.dform.cell;

public enum CellType {
    // 基础类型
    TEXT,       // 纯文本（原STRING）
    NUMBER,     // 数字（整数/小数）
    BOOLEAN,    // 布尔值（true/false）

    // 日期时间类型
    DATE,       // 日期（如 2023-08-20）
    TIME,       // 时间（如 14:30:00）
    DATETIME,   // 日期时间（如 2023-08-20 14:30:00）

    // 特殊格式
    CURRENCY,   // 货币（自动格式如 ¥1,000.00）
    PERCENTAGE, // 百分比（自动转换 0.85 → 85%）
    FRACTION,   // 分数（如 1/4）
    SCIENTIFIC, // 科学计数法（如 1.23E+8）

    // 复合类型
    HYPERLINK,  // 超链接（可点击）
    RICH_TEXT,  // 富文本（带格式的文字）

    // 预留扩展
    CUSTOM      // 自定义类型（需配合元数据使用）
}
