package util;

public class TabUtil {
    private static final String TAB_COPY_LEFT_DELIMITER = "(";
    private static final String TAB_COPY_RIGHT_DELIMITER = ")";
    private static final int LEFT_DELIMITER_MIN_BACK_INDEX = 3;
    private static final String DEFAULT_TAB_NAME = "Panel %d";
    private static final String NEW_TAB_COPY_NAME = "%s" + TAB_COPY_LEFT_DELIMITER + "1" + TAB_COPY_RIGHT_DELIMITER;
    private static final String REGULAR_TAB_COPY_NAME = "%s" + TAB_COPY_LEFT_DELIMITER + "%d" + TAB_COPY_RIGHT_DELIMITER;
    private static int tabCounter;

    private static int copyCounter;
    private static String[] allTitles;
    private static String actionTitle;
    private static String currentTitle;
    private static int titleLength;
    private static int delimiterIndex;
    private static String titleName;

    public static String getDefaultTabName() {
        tabCounter++;
        return defaultTabName();
    }

    public static String getCopyTabName(String actionTitle, String[] allTitles) {
        TabUtil.allTitles = allTitles;
        TabUtil.actionTitle = actionTitle;
        copyCounter = 0;

        try {
            return hasTabCopies() ? regularTabCopyName() : newTabCopyName();

        } catch (NumberFormatException ex) {
            return newTabCopyName();
        }
    }


    private static boolean hasTabCopies() {
        for (String title : allTitles) {
            if (isCopy(title)) {
                try {
                    updateCounter(getCopyNumber());

                } catch (NumberFormatException ex) {
                    return false;
                }
            }
        }
        return copyCounter > 0;
    }

    private static boolean isCopy(String title) {
        return title.endsWith(TAB_COPY_RIGHT_DELIMITER) && equalsActionTitle(title);
    }

    private static int getCopyNumber() {
        String copyNumber = "";
        for (int i = delimiterIndex - 1; i > 1; i--) {
            copyNumber += getSymbolByBackIndex(i);
        }
        return Integer.parseInt(copyNumber);
    }

    private static int getDelimiterBackIndex() {
        for (int i = LEFT_DELIMITER_MIN_BACK_INDEX; i < titleLength; i++) {
            if (getSymbolByBackIndex(i).equals(TAB_COPY_LEFT_DELIMITER)) {
                return i;
            }
        }
        return LEFT_DELIMITER_MIN_BACK_INDEX;
    }

    private static void updateCounter(int copyNumber) {
        if (copyCounter < copyNumber) copyCounter = copyNumber;
    }

    private static boolean equalsActionTitle(String title) {
        initTitleParts(title);
        return titleName.equals(actionTitle);
    }

    private static void initTitleParts(String title) {
        currentTitle = title;
        titleLength = title.length();
        delimiterIndex = getDelimiterBackIndex();
        titleName = currentTitle.substring(0, titleLength - delimiterIndex);
    }

    private static String getSymbolByBackIndex(int index) {
        return String.valueOf(currentTitle.charAt(titleLength - index));
    }


    private static String defaultTabName() {
        return String.format(DEFAULT_TAB_NAME, tabCounter);
    }

    private static String newTabCopyName() {
        return String.format(NEW_TAB_COPY_NAME, actionTitle);
    }

    private static String regularTabCopyName() {
        copyCounter++;
        return String.format(REGULAR_TAB_COPY_NAME, actionTitle, copyCounter);
    }

    public static int getTabCounter() {
        return tabCounter;
    }
}
