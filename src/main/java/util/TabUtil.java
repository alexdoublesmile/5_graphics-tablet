package util;

public class TabUtil {

    private static int tabCounter;
    private static int copyCounter;


    public static String getDefaultTabName() {
        tabCounter++;
        return String.format("Panel %d", tabCounter);
    }

    public static String getCopyTabName(String previousTitle) {

        if (previousTitle.endsWith(String.format(")"))) {
            try {
                copyCounter = Integer.parseInt(String.valueOf(previousTitle.charAt(previousTitle.length() - 2)));
                copyCounter++;
                StringBuilder newTitle = new StringBuilder(previousTitle);
                newTitle.setCharAt(previousTitle.length() - 2, Character.forDigit(copyCounter, 10));
                return String.valueOf(newTitle);

            } catch (NumberFormatException ex) {
                return previousTitle + "(1)";
            }
        } else {
            return previousTitle + "(1)";
        }
    }
}
