package util;

public class TabUtil {
    private static int tabCounter;
    private static int copyCounter;
    private static String[] allTitles;
    private static String previousTitle;
    private static String currentTitle;

    public static String getDefaultTabName() {
        tabCounter++;
        return String.format("Panel %d", tabCounter);
    }

    public static String getCopyTabName(String previousTitle, String[] allTitles) {
        TabUtil.allTitles = allTitles;
        TabUtil.previousTitle = previousTitle;
        if (tabHasCopies()) {
            try {
                copyCounter++;
                currentTitle = previousTitle + "(" + copyCounter + ")";
            } catch (NumberFormatException ex) {
                currentTitle = previousTitle + "(1)";
            }

        } else {
            currentTitle = previousTitle + "(1)";
        }
        copyCounter = 0;
        return currentTitle;
    }

    private static boolean tabHasCopies() {
        int numberInTitle = 0;
        for (String title : allTitles) {
            if (title.endsWith(")") && tabsAreSame(title)) {
                try {
                    if (getBackSymbol(title, 3).equals("(")) {
                        numberInTitle = Integer.parseInt(getBackSymbol(title, 2));
                    } else {
                        numberInTitle = Integer.parseInt(getBackSymbol(title, 3) + getBackSymbol(title, 2));
                    }
                    if (copyCounter < numberInTitle) {
                        copyCounter = numberInTitle;
                    }

                    return true;
                } catch (NumberFormatException ex) {
                    return false;
                }
            }
        }
        return false;
    }

    private static boolean tabsAreSame(String title) {
        if (getBackSymbol(title, 3).equals("(")) {
            String titleName = title.substring(0, title.length() - 3);
            return titleName.equals(previousTitle);

        } else if (getBackSymbol(title, 4).equals("(")) {

            String titleName = title.substring(0, title.length() - 4);
            return titleName.equals(previousTitle);
        } else {
            return false;
        }
    }

    private static String getBackSymbol(String word, int index) {
        return String.valueOf(word.charAt(word.length() - index));
    }
}
