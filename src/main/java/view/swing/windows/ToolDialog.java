package view.swing.windows;

import controller.swing.listeners.buttonListeners.ColorButtonListener;
import view.swing.SwingViewImpl;
import view.swing.buttons.ColorButton;

import javax.swing.*;
import java.awt.*;

public  class ToolDialog extends JDialog {
    private final JButton defaultButton;
    private final JPanel buttonPanel;
    private ToolDialog toolWindow;
    private JPanel optionPanel;
    private JLabel widthLabel;
    private JSlider widthSlider;
    private JToolBar colorBar;
    private JLabel lineType;
    private JRadioButton solid;
    private JRadioButton dashed;
    private JRadioButton dotted;
    private ButtonGroup lineTypeGroup;
    private JButton closeButton;
    private ColorButton redButton;
    private ColorButton blackButton;
    private ColorButton greyButton;
    private ColorButton blueButton;
    private ColorButton greenButton;
    private ColorButton whiteButton;
    private ColorButton orangeButton;


    public ToolDialog (JFrame owner, String title) {
        super(owner, title);
        toolWindow = this;
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());


        redButton = new  ColorButton(Color.red, true, 15);
        blackButton = new  ColorButton(Color.black);
        greyButton = new  ColorButton(SwingViewImpl.DEFAULT_COLOR);
        blueButton = new ColorButton(Color.blue);
        greenButton = new  ColorButton(new Color(0x12A612));
        orangeButton = new  ColorButton(new Color(250, 125, 0));
        whiteButton = new  ColorButton(Color.white);

        optionPanel = new JPanel();
        optionPanel.setLayout(new GridLayout(4, 0));
        widthLabel = new JLabel();
        widthLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
        widthSlider = new JSlider();
        widthSlider.setMinimum(10);
        widthSlider.setMaximum(100);
        widthSlider.setExtent(10);
//            widthSlider.setPaintTrack(true);
//            widthSlider.setPaintLabels(true);
//            widthSlider.setSnapToTicks(true);
//            widthSlider.setPaintTicks(true);

        colorBar = new JToolBar();
        colorBar.setFloatable(false);
        colorBar.setRollover(false);
        colorBar.setBorderPainted(false);
        colorBar.setLayout(new FlowLayout());
        colorBar.add(blackButton);
        colorBar.add(greyButton);
        colorBar.add(redButton);
        colorBar.add(blueButton);
        colorBar.add(greenButton);
        colorBar.add(orangeButton);
        colorBar.add(whiteButton);
        lineType = new JLabel("lineType");
        lineTypeGroup = new ButtonGroup();
        solid = new JRadioButton();
        dashed = new JRadioButton();
        dotted = new JRadioButton();
        lineTypeGroup.add(solid);
        lineTypeGroup.add(dashed);
        lineTypeGroup.add(dotted);
        optionPanel.add(widthLabel);
        optionPanel.add(widthSlider);
        optionPanel.add(colorBar);

//            optionPanel.add(lineType);
//            optionPanel.add(solid);
//            optionPanel.add(dashed);
//            optionPanel.add(dotted);

        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2, 0));
        defaultButton = new JButton("Load Defaults");
        defaultButton.setMnemonic('d');
        buttonPanel.add(defaultButton);

        add(optionPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        setUndecorated(true);
        pack();
    }

    public ToolDialog getToolWindow() {
        return toolWindow;
    }

    public void setToolWindow(ToolDialog toolWindow) {
        this.toolWindow = toolWindow;
    }

    public JPanel getOptionPanel() {
        return optionPanel;
    }

    public void setOptionPanel(JPanel optionPanel) {
        this.optionPanel = optionPanel;
    }

    public JLabel getWidthLabel() {
        return widthLabel;
    }

    public void setWidthLabel(JLabel widthLabel) {
        this.widthLabel = widthLabel;
    }

    public JSlider getWidthSlider() {
        return widthSlider;
    }

    public void setWidthSlider(JSlider widthSlider) {
        this.widthSlider = widthSlider;
    }

    public JToolBar getColorBar() {
        return colorBar;
    }

    public void setColorBar(JToolBar colorBar) {
        this.colorBar = colorBar;
    }

    public JLabel getLineType() {
        return lineType;
    }

    public void setLineType(JLabel lineType) {
        this.lineType = lineType;
    }

    public JRadioButton getSolid() {
        return solid;
    }

    public void setSolid(JRadioButton solid) {
        this.solid = solid;
    }

    public JRadioButton getDashed() {
        return dashed;
    }

    public void setDashed(JRadioButton dashed) {
        this.dashed = dashed;
    }

    public JRadioButton getDotted() {
        return dotted;
    }

    public void setDotted(JRadioButton dotted) {
        this.dotted = dotted;
    }

    public ButtonGroup getLineTypeGroup() {
        return lineTypeGroup;
    }

    public void setLineTypeGroup(ButtonGroup lineTypeGroup) {
        this.lineTypeGroup = lineTypeGroup;
    }

    public JButton getCloseButton() {
        return closeButton;
    }

    public void setCloseButton(JButton closeButton) {
        this.closeButton = closeButton;
    }

    public JButton getDefaultButton() {
        return defaultButton;
    }

    public JPanel getButtonPanel() {
        return buttonPanel;
    }

    public ColorButton getRedButton() {
        return redButton;
    }

    public ColorButton getBlackButton() {
        return blackButton;
    }

    public ColorButton getGreyButton() {
        return greyButton;
    }

    public ColorButton getBlueButton() {
        return blueButton;
    }

    public ColorButton getGreenButton() {
        return greenButton;
    }

    public ColorButton getWhiteButton() {
        return whiteButton;
    }

    public ColorButton getOrangeButton() {
        return orangeButton;
    }
}
