package model;

import config.Config;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class UndoRedoService {

    private static final int FORWARD_DIRECTION = 1;
    private static final int BACK_DIRECTION = 2;
    private static int UNDO_QUANTITY = Integer.parseInt(Config.getProperty(Config.UNDO_ACTIONS_QUANTITY));

    private ArrayList<BufferedImage> actionList;
    private int actionCounter;
    private boolean iterated;
    private BufferedImage selectedAction;

    public UndoRedoService() {
        actionList = new ArrayList<>(UNDO_QUANTITY);
    }

    public void saveAction(BufferedImage action) {
        if (iterated) {
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

    private BufferedImage iterate(int direction) {
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
        iterated = true;

        selectedAction = getNewImage(actionList.get(actionCounter - 1));
        return selectedAction;
    }

    private BufferedImage getNewImage(BufferedImage oldImage) {
//        return oldImage;

        BufferedImage newImage = new BufferedImage(
                oldImage.getColorModel(),
                oldImage.copyData(oldImage.getRaster().createCompatibleWritableRaster()),
                oldImage.isAlphaPremultiplied(),
                null
        );
        return newImage;
    }

    private void cropList() {
        ArrayList<BufferedImage> newList = actionList.stream()
                .limit(actionCounter)
                .collect(Collectors.toCollection(ArrayList::new));
        actionList = newList;

        iterated = false;
    }

    public boolean isIterated() {
        return iterated;
    }

    public void setIterated(boolean iterated) {
        this.iterated = iterated;
    }

    public void reSaveAction(BufferedImage action) {
        actionList.set(actionCounter - 1, getNewImage(action));
    }

    public void removeLastAction() {
        actionList.remove(actionCounter - 1);
        actionCounter--;
    }

    public static void setUndoQuantity(int undoQuantity) {
        UNDO_QUANTITY = undoQuantity;
    }

    public static int getUndoQuantity() {
        return UNDO_QUANTITY;
    }
}
