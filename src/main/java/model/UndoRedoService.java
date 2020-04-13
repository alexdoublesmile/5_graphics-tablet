package model;

import config.Config;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UndoRedoService {

    private static final String FORWARD_DIRECTION = "Forward";
    private static final String BACK_DIRECTION = "Back";
    private static final int UNDO_QUANTITY = Integer.parseInt(Config.getProperty(Config.UNDO_ACTIONS_QUANTITY));

    private List<BufferedImage> actionList;
    private int actionCounter;
    private boolean wasIterated;
    private BufferedImage selectedAction;



    public UndoRedoService() {
        actionList = new ArrayList<>(UNDO_QUANTITY);
    }

    public void saveAction(BufferedImage action) {
        if (wasIterated) {
            cropList();
            // change the rewritten BufferedImage object to the previously selected
            BufferedImage rewrittenImage = actionList.get(actionCounter - 1);
            rewrittenImage = selectedAction;
        }
        if (actionList.size() < UNDO_QUANTITY) {
            actionList.add(getNewImage(action));
        } else {
            for (int i = 0; i < actionList.size(); i++) {
                if (i < UNDO_QUANTITY - 1) {
                    actionList.set(i, actionList.get(i + 1));
                } else {
                    actionList.set(i, getNewImage(action));
                    actionCounter--;
                }
            }
        }
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
        selectedAction = getNewImage(actionList.get(actionCounter - 1));
        return selectedAction;
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

    public boolean isWasIterated() {
        return wasIterated;
    }

    public void setWasIterated(boolean wasIterated) {
        this.wasIterated = wasIterated;
    }
}
