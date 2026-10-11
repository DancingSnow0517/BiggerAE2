package cn.dancingsnow.bigger_ae2.util;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class NumberUtil {
    private static final String[] UNITS = {"", "K", "M", "G", "T", "P", "E", "Y", "Z", "R", "Q"};

    private static final Style NUMBER = Style.EMPTY.withColor(0x886EFF);
    private static final Style UNIT = Style.EMPTY.withColor(0xEE82EE);

    public static String formatUnsignedLongArray(UnsignedLongArray number) {
        int unitIndex = 0;
        BigDecimal temp = new BigDecimal(number.toString());
        while (temp.compareTo(BigDecimal.TEN.pow(3)) >= 0 && unitIndex < UNITS.length) {
            temp = temp.divide(BigDecimal.TEN.pow(3), 3, RoundingMode.HALF_DOWN);
            unitIndex++;
        }

        DecimalFormat df = new DecimalFormat("#.##");
        String formattedNumber = df.format(temp.doubleValue());

        if (unitIndex >= UNITS.length) {
            return String.format("%.2e", new BigDecimal(number.toString()).doubleValue());
        }

        return formattedNumber + UNITS[unitIndex];
    }

    public static Component numberText(UnsignedLongArray number) {
        String text = formatUnsignedLongArray(number);
        if (text.matches(".*[a-zA-Z]$")) {
            return Component.literal(text.substring(0, text.length() - 1))
                    .withStyle(NUMBER)
                    .append(Component.literal(text.substring(text.length() - 1)).withStyle(UNIT));
        } else if (text.contains("e+")) {
            String[] split = text.split("e\\+");
            return Component.literal(split[0])
                    .withStyle(NUMBER)
                    .append(Component.literal("e+").withStyle(UNIT))
                    .append(Component.literal(split[1]).withStyle(NUMBER));
        }
        return Component.literal(text).withStyle(NUMBER);
    }
}
