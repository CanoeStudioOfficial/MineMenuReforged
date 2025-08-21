package dmillerw.minemenu.helper;

import net.minecraft.client.resources.I18n;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LocalizationHelper {
    private static final Pattern LOCALIZATION_PATTERN = Pattern.compile("\\{([^}]+)\\}");

    public static String getLocalizedString(String text) {
        if (text == null || text.isEmpty()) {
            return text;
        }
        Matcher matcher = LOCALIZATION_PATTERN.matcher(text);
        if (matcher.find()) {
            return I18n.format(matcher.group(1));
        }
        return text;
    }
}