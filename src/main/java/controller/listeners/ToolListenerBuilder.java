package controller.listeners;

import model.DrawMode;
import model.PaintModel;
import util.CursorBuilder;
import view.swing.SwingViewImpl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import static java.awt.Frame.MAXIMIZED_BOTH;

public class ToolListenerBuilder {
    private final PencilButtonListener PENCIL_BUTTON_LISTENER;
    private final MarkerButtonListener MARKER_BUTTON_LISTENER;
    private final BrushButtonListener BRUSH_BUTTON_LISTENER;
    private final EraserButtonListener ERASER_BUTTON_LISTENER;
    private final LineButtonListener LINE_BUTTON_LISTENER;
    private final DottedLineButtonListener DOTTED_LINE_BUTTON_LISTENER;
    private final EllipseButtonListener ELLIPSE_BUTTON_LISTENER;
    private final RectButtonListener RECT_BUTTON_LISTENER;
    private final PyramidButtonListener PYRAMID_BUTTON_LISTENER;
    private final PrismButtonListener PRISM_BUTTON_LISTENER;
    private final FillButtonListener FILL_BUTTON_LISTENER;

    private final UndoButtonListener UNDO_BUTTON_LISTENER;
    private final RedoButtonListener REDO_BUTTON_LISTENER;
    private final TextButtonListener TEXT_BUTTON_LISTENER;

    private final TrnsformImageButtonListener TRANSFORM_IMAGE_BUTTON_LISTENER;
    private final CleanButtonListener CLEAN_BUTTON_LISTENER;
    private final CalculatorButtonListener CALCULATOR_BUTTON_LISTENER;

    private SwingViewImpl view;
    private PaintModel model;
    private JPanel mainPanel;

    public ToolListenerBuilder(SwingViewImpl view, PaintModel model) {
        this.view = view;
        this.model = model;

        mainPanel = view.getMainPanel();
        PENCIL_BUTTON_LISTENER = new PencilButtonListener();
        MARKER_BUTTON_LISTENER = new MarkerButtonListener();
        BRUSH_BUTTON_LISTENER = new BrushButtonListener();
        ERASER_BUTTON_LISTENER = new EraserButtonListener();
        LINE_BUTTON_LISTENER = new LineButtonListener();
        DOTTED_LINE_BUTTON_LISTENER = new DottedLineButtonListener();
        ELLIPSE_BUTTON_LISTENER = new EllipseButtonListener();
        RECT_BUTTON_LISTENER = new RectButtonListener();
        PYRAMID_BUTTON_LISTENER = new PyramidButtonListener();
        PRISM_BUTTON_LISTENER = new PrismButtonListener();
        FILL_BUTTON_LISTENER = new FillButtonListener();

        UNDO_BUTTON_LISTENER = new UndoButtonListener();
        REDO_BUTTON_LISTENER = new RedoButtonListener();
        TEXT_BUTTON_LISTENER = new TextButtonListener();

        TRANSFORM_IMAGE_BUTTON_LISTENER = new TrnsformImageButtonListener();
        CLEAN_BUTTON_LISTENER = new CleanButtonListener();
        CALCULATOR_BUTTON_LISTENER = new CalculatorButtonListener();
    }

    private class PencilButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            model.setDrawMode(DrawMode.PENCIL);
            mainPanel.setCursor(new CursorBuilder().getPENCIL_CURSOR());
        }
    }

    private class MarkerButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            model.setDrawMode(DrawMode.MARKER);
            mainPanel.setCursor(new CursorBuilder().getMARKER_CURSOR());
        }
    }

    private class BrushButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            model.setDrawMode(DrawMode.BRUSH);
            mainPanel.setCursor(new CursorBuilder().getBRUSH_CURSOR());
        }
    }

    private class EraserButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            model.setDrawMode(DrawMode.ERASER);
            mainPanel.setCursor(new CursorBuilder().getERASER_CURSOR());
        }
    }

    private class LineButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            model.setDrawMode(DrawMode.LINE);
            mainPanel.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
        }
    }

    private class DottedLineButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO: 10.04.2020

            JOptionPane.showMessageDialog(view.getMainFrame(),
                    "Этот фукционал еще в разработке.");
            model.setDrawMode(DrawMode.DOTTEDLINE);
            mainPanel.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
        }
    }

    private class EllipseButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            model.setDrawMode(DrawMode.ELLIPSE);
            mainPanel.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
        }
    }

    private class RectButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            model.setDrawMode(DrawMode.RECTANGLE);
            mainPanel.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
        }
    }

    private class PyramidButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO: 10.04.2020
            JOptionPane.showMessageDialog(view.getMainFrame(),
                    "Этот фукционал еще в разработке");
            model.setDrawMode(DrawMode.PYRAMID);
            mainPanel.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
        }
    }

    private class PrismButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO: 10.04.2020
            JOptionPane.showMessageDialog(view.getMainFrame(),
                    "Этот фукционал еще в разработке");
            model.setDrawMode(DrawMode.PRISM);
            mainPanel.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
        }
    }

    private class FillButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            model.setDrawMode(DrawMode.FILL);
            mainPanel.setCursor(new CursorBuilder().getFILL_CURSOR());
        }
    }

    private class UndoButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            view.setMainImage(model.getPreviousAction());
            mainPanel.repaint();
        }
    }

    private class RedoButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            view.setMainImage(model.getNextAction());
            mainPanel.repaint();
        }
    }

    private class TextButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            model.setDrawMode(DrawMode.TEXT);
            mainPanel.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
        }
    }

    private class CalculatorButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO: 10.04.2020
            JOptionPane.showMessageDialog(view.getMainFrame(),
                    "Этот фукционал еще в нескорой разработке, Катюша :)");
        }
    }


    private class TrnsformImageButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
                BufferedImage source = view.getMainImage();
                BufferedImage result = new BufferedImage(source.getWidth(), source.getHeight(), source.getType());

                for (int x = 0; x < source.getWidth(); x++) {
                    for (int y = 0; y < source.getHeight(); y++) {

                        Color color = new Color(source.getRGB(x, y));
                        int blue = color.getBlue();
                        int red = color.getRed();
                        int green = color.getGreen();

                        int grey = (int) (red * 0.299 + green * 0.587 + blue * 0.114);
                        int newRed = grey;
                        int newGreen = grey;
                        int newBlue = grey;
                        Color newColor = new Color(newRed, newGreen, newBlue);
                        result.setRGB(x, y, newColor.getRGB());
                    }
                }

                view.setMainImage(result);
                mainPanel.repaint();
        }
    }

    private class CleanButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            view.setExtendedState(MAXIMIZED_BOTH);
            view.getMainPanel().setSize(view.getWidth(), view.getHeight());
            view.setMainImage(new BufferedImage(view.getWidth(), view.getHeight(), BufferedImage.TYPE_INT_RGB));

            Graphics g = view.getMainImage().getGraphics();
            Graphics2D g2 = (Graphics2D)g;
            g2.setColor(Color.white);
            g2.fillRect(0, 0, mainPanel.getSize().width, mainPanel.getSize().height);
            g2.setColor(Color.black);
            mainPanel.repaint();
        }
    }

    public PencilButtonListener getPENCIL_BUTTON_LISTENER() {
        return PENCIL_BUTTON_LISTENER;
    }

    public MarkerButtonListener getMARKER_BUTTON_LISTENER() {
        return MARKER_BUTTON_LISTENER;
    }

    public EraserButtonListener getERASER_BUTTON_LISTENER() {
        return ERASER_BUTTON_LISTENER;
    }

    public LineButtonListener getLINE_BUTTON_LISTENER() {
        return LINE_BUTTON_LISTENER;
    }

    public EllipseButtonListener getELLIPSE_BUTTON_LISTENER() {
        return ELLIPSE_BUTTON_LISTENER;
    }

    public RectButtonListener getRECT_BUTTON_LISTENER() {
        return RECT_BUTTON_LISTENER;
    }

    public TextButtonListener getTEXT_BUTTON_LISTENER() {
        return TEXT_BUTTON_LISTENER;
    }

    public UndoButtonListener getUNDO_BUTTON_LISTENER() {
        return UNDO_BUTTON_LISTENER;
    }

    public RedoButtonListener getREDO_BUTTON_LISTENER() {
        return REDO_BUTTON_LISTENER;
    }

    public CleanButtonListener getCLEAN_BUTTON_LISTENER() {
        return CLEAN_BUTTON_LISTENER;
    }

    public TrnsformImageButtonListener getTRANSFORM_IMAGE_BUTTON_LISTENER() {
        return TRANSFORM_IMAGE_BUTTON_LISTENER;
    }

    public BrushButtonListener getBRUSH_BUTTON_LISTENER() {
        return BRUSH_BUTTON_LISTENER;
    }

    public DottedLineButtonListener getDOTTED_LINE_BUTTON_LISTENER() {
        return DOTTED_LINE_BUTTON_LISTENER;
    }

    public PyramidButtonListener getPYRAMID_BUTTON_LISTENER() {
        return PYRAMID_BUTTON_LISTENER;
    }

    public PrismButtonListener getPRISM_BUTTON_LISTENER() {
        return PRISM_BUTTON_LISTENER;
    }

    public FillButtonListener getFILL_BUTTON_LISTENER() {
        return FILL_BUTTON_LISTENER;
    }

    public CalculatorButtonListener getCALCULATOR_BUTTON_LISTENER() {
        return CALCULATOR_BUTTON_LISTENER;
    }
}
