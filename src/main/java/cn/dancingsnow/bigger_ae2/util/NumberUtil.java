package cn.dancingsnow.bigger_ae2.util;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class NumberUtil {
    private static final String[] UNITS = {"", "K", "M", "G", "T", "P", "E", "Z", "Y", "R", "Q"};

    private static final Style NUMBER = Style.EMPTY.withColor(0x886EFF);
    private static final Style UNIT = Style.EMPTY.withColor(0xEE82EE);

    public static String formatUnsignedLongArray(UnsignedLongArray number) {
        String decimal = number.toString();
        if (decimal.length() > UNITS.length * 3) {
            return formatScientific(decimal);
        }

        int unitIndex = 0;
        BigDecimal temp = new BigDecimal(decimal);
        while (temp.compareTo(BigDecimal.TEN.pow(3)) >= 0 && unitIndex < UNITS.length) {
            temp = temp.divide(BigDecimal.TEN.pow(3), 3, RoundingMode.HALF_DOWN);
            unitIndex++;
        }

        DecimalFormat df = new DecimalFormat("#.##");
        String formattedNumber = df.format(temp.doubleValue());

        if (unitIndex >= UNITS.length) {
            return formatScientific(decimal);
        }

        return formattedNumber + UNITS[unitIndex];
    }

    private static String formatScientific(String decimal) {
        int exponent = decimal.length() - 1;
        char[] significant = {
            decimal.charAt(0),
            decimal.length() > 1 ? decimal.charAt(1) : '0',
            decimal.length() > 2 ? decimal.charAt(2) : '0'
        };

        if (decimal.length() > 3 && decimal.charAt(3) >= '5') {
            for (int i = significant.length - 1; i >= 0; i--) {
                if (significant[i] != '9') {
                    significant[i]++;
                    break;
                }
                significant[i] = '0';
                if (i == 0) {
                    significant[0] = '1';
                    exponent++;
                }
            }
        }

        return String.valueOf(significant[0]) +
            '.' +
            significant[1] +
            significant[2] +
            "e+" +
            exponent;
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
