package model;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UndoRedoService {

    private static final String FORWARD_DIRECTION = "Forward";
    private static final String BACK_DIRECTION = "Back";

    private List<BufferedImage> actionList;
    private int actionCounter;
    private boolean wasIterated;
    private BufferedImage selectedImage;

    public UndoRedoService() {
        actionList = new ArrayList<>(1000);
    }

    public void saveImage(BufferedImage action) {
        if (wasIterated) {
            cropList();
            // change the rewritten BufferedImage object to the previously selected
            BufferedImage rewrittenImage = actionList.get(actionCounter - 1);
            rewrittenImage = selectedImage;
        }
        actionList.add(getNewImage(action));
        actionCounter++;
    }

    public BufferedImage getNextAction() {
        return iterate(FORWARD_DIRECTION);
    }

    public BufferedImage getPreviousAction() {
        return iterate(BACK_DIRECTION);
    }

    private BufferedImage iterate(String direction) {
        switch (direction) {
            case FORWARD_DIRECTION:
                if (actionCounter < actionList.size())
                    actionCounter++;
                break;
            case BACK_DIRECTION:
                if (actionCounter > 1)
                    actionCounter--;
                break;
        }
        wasIterated = true;
        selectedImage = getNewImage(actionList.get(actionCounter - 1));
        return selectedImage;
    }

    private BufferedImage getNewImage(BufferedImage oldImage) {
        BufferedImage newImage = new BufferedImage(
                oldImage.getColorModel(),
                oldImage.copyData(null),
                oldImage.isAlphaPremultiplied(),
                null
        );
        return newImage;
    }

    private void cropList() {
        List<BufferedImage> newList = actionList.stream()
                .limit(actionCounter)
                .collect(Collectors.toList());
        actionList = newList;

        wasIterated = false;
    }
}
