package org.venti.agileform.util;

import java.util.Random;

public class GuaranteeUtil {

    // 生成随机保函编号
    public static String generateGuaranteeNumber() {
        StringBuilder prefix = new StringBuilder();
        Random random = new Random();

        // 生成随机四个大写字母
        for (int i = 0; i < 4; i++) {
            char letter = (char) ('A' + random.nextInt(26));  // 生成 A-Z 字母
            prefix.append(letter);
        }

        // 生成保证唯一性的12位随机数字
        long randomNumber = (long) (random.nextDouble() * 1_000_000_000_000L);  // 生成一个0到999999999999之间的随机数
        String lastTwelveDigits = String.format("%012d", randomNumber);  // 确保12位数，不足的补零

        // 获取当前时间戳的最后四位
        long timestamp = System.currentTimeMillis();
        String timestampStr = String.valueOf(timestamp);
        String lastFourDigits = timestampStr.substring(timestampStr.length() - 4);

        // 组合成保函编号
        return prefix + lastTwelveDigits + lastFourDigits;
    }

    // 生成随机安全码
    public static String generateSecurityCode() {
        StringBuilder code = new StringBuilder();
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();

        for (int i = 0; i < 6; i++) {
            code.append(characters.charAt(random.nextInt(characters.length())));
        }
        return code.toString();
    }

}