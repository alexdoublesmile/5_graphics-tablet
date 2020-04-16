package view.swing;

import config.Config;
import model.DrawMode;
import model.Model;
import util.IconBuilder;
import view.View;
import view.swing.buttons.ColorButton;
import view.swing.buttons.FunctionButton;
import view.swing.buttons.ToolButton;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SwingViewImpl extends JFrame implements View {
    private String MAIN_FRAME_NAME = "Графический планшетик";
    private static final String FILE_MENU_NAME = "Файл";
    private static final String DISCOLOR_BUTTON_NAME = "Обесцветить";
    private static final String CLEAN_BUTTON_NAME = "Очистить планшетик";
    private static final Color CONTROL_PANEL_COLOR = new Color(0xE9D6BF);
    private static final String COLOR_DIALOG_TITLE = "Выбор цвета";
    private static final ArrayList<String> closingElements;
    static {
        closingElements = new ArrayList<>();
        closingElements.add("RAG");
        closingElements.add("HAND");
        closingElements.add("POLYGON");
        closingElements.add("SPHERE");
    }
    private final int MAIN_FRAME_WIDTH = Integer.parseInt(Config.getProperty(Config.MAIN_FRAME_WIDTH));
    private final int MAIN_FRAME_HEIGHT = Integer.parseInt(Config.getProperty(Config.MAIN_FRAME_HEIGHT));
    private final int COLOR_DIALOG_WIDTH = Integer.parseInt(Config.getProperty(Config.COLOR_DIALOG_WIDTH));
    private final int COLOR_DIALOG_HEIGHT = Integer.parseInt(Config.getProperty(Config.COLOR_DIALOG_HEIGHT));
    private final Map<String, ToolButton> toolButtons;
    private Model model;
    private JFrame mainFrame;

    private JMenuBar mainMenu;
    private JMenu fileMenu;
    private JMenuItem loadMenu;
    private JMenuItem saveMenu;
    private JMenuItem saveAsMenu;

    private JPanel mainPanel;
    private JToolBar toolBar;
    private JToolBar colorBar;

    private JButton colorButton;
    private ColorButton redButton;
    private ColorButton blackButton;
    private ColorButton blueButton;
    private ColorButton greenButton;
    private ColorButton whiteButton;

    private JButton undoButton;
    private JButton redoButton;

    private JColorChooser colorChooser;
    private JFileChooser fileChooser;

    private JButton discolorButton;
    private JButton cleanButton;
    private JButton calculatorButton;

    private BufferedImage mainImage;
    private BufferedImage previousImage;
    private Color mainColor;

    public SwingViewImpl(Model model) {
        this.model = model;
        toolButtons = new HashMap<>();
        mainColor = Color.black;

        compareCanvas();
    }

    @Override
    public void compareCanvas() {
        initMainWindow();
        initMenu();
        initToolBar();
        initColorBar();
        initButtons();
        initDrawingPanel();
        collectAllElements();
    }

    private void initMainWindow() {
        this.mainFrame = this;
        this.setTitle(MAIN_FRAME_NAME);
        this.setSize(MAIN_FRAME_WIDTH, MAIN_FRAME_HEIGHT);
//        this.setVisible(true);
        this.setExtendedState(MAXIMIZED_BOTH);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBackground(Color.yellow);
    }

    private void initMenu() {
        mainMenu = new JMenuBar();
        mainMenu.setBackground(CONTROL_PANEL_COLOR);
//        mainMenu.setBounds(0,0,350,20);
        fileMenu = new JMenu(FILE_MENU_NAME);
        loadMenu = new JMenuItem();
        saveMenu = new JMenuItem();
        saveAsMenu = new JMenuItem();
        fileChooser = new JFileChooser();

    }

    private void initToolBar() {
        toolBar = new JToolBar(JToolBar.HORIZONTAL);
        toolBar.setBackground(CONTROL_PANEL_COLOR);
        toolBar.setBorderPainted(false);
//        toolBar.setBounds(0, 0, 300, 30);

        for (DrawMode drawMode : DrawMode.values()) {
            toolButtons.put(
                    drawMode.name(),
                    new ToolButton(IconBuilder.buildIconByDrawMode(drawMode)));
        }
    }

    private void initColorBar() {
        colorBar = new  JToolBar(JToolBar.HORIZONTAL);
        colorBar.setBackground(CONTROL_PANEL_COLOR);
        colorBar.setBorderPainted(false);

//        colorBar.setBounds(30, 0, 160, 20);
        colorBar.setLayout(null);

        colorButton = new ColorButton(mainColor, 25);
        colorButton.setIcon(IconBuilder.buildIconByPath(Config.getProperty(Config.PALETTE_ICON_PATH)));
        redButton = new  ColorButton(Color.red, true, 15);
        blackButton = new  ColorButton(Color.black);
        blueButton = new ColorButton(Color.blue);
        greenButton = new  ColorButton(new Color(0x12A612));
        whiteButton = new  ColorButton(Color.white);
        colorChooser = new  JColorChooser(mainColor);

    }

    private void initButtons() {
        undoButton = new  ToolButton(IconBuilder.buildIconByPath(
                Config.getProperty(Config.UNDO_ICON_PATH)));
        redoButton = new  ToolButton(IconBuilder.buildIconByPath(
                Config.getProperty(Config.REDO_ICON_PATH)));
        calculatorButton = new  ToolButton(IconBuilder.buildIconByPath(
                Config.getProperty(Config.CALCULATOR_ICON_PATH)
        ));

        discolorButton = new FunctionButton(DISCOLOR_BUTTON_NAME);
        cleanButton = new FunctionButton(CLEAN_BUTTON_NAME);
    }

    private void initDrawingPanel() {
        mainPanel = new MyPanel();
        mainPanel.setFocusable(true);
        mainPanel.setBounds(0,0,mainFrame.getWidth(),mainFrame.getHeight());
        mainPanel.setBackground(Color.white);
        mainPanel.setOpaque(true);
    }

    private void collectAllElements() {
        this.setJMenuBar(mainMenu);
        this.add(mainPanel);

        mainMenu.add(fileMenu);
        mainMenu.add(toolBar);
        mainMenu.add(colorBar);
        mainMenu.add(new JToolBar.Separator());
        fileMenu.add(loadMenu);
        fileMenu.add(saveMenu);
        fileMenu.add(saveAsMenu);

        for (DrawMode drawMode : DrawMode.values()) {
            if (closingElements.contains(drawMode.name())) {
                toolBar.add(toolButtons.get(drawMode.name()));
                toolBar.addSeparator();
            } else {
                toolBar.add(toolButtons.get(drawMode.name()));
            }
        }

        toolBar.addSeparator();
        toolBar.add(undoButton);
        toolBar.add(redoButton);
        colorBar.add(colorButton);
        colorBar.add(blackButton);
        colorBar.add(redButton);
        colorBar.add(blueButton);
        colorBar.add(greenButton);
        colorBar.add(whiteButton);

        mainMenu.add(discolorButton);
        mainMenu.add(new JToolBar.Separator());
        mainMenu.add(cleanButton);
        mainMenu.add(new JToolBar.Separator());
        mainMenu.add(calculatorButton);
        mainMenu.add(new JToolBar.Separator());
    }

    public void saveCurrentImage() {
        previousImage = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics g = previousImage.getGraphics();
        g.drawImage(mainImage, 0, 0, null);
    }

    public void loadSavedImage() {
        Graphics g = mainImage.getGraphics();
        if (previousImage != null) {
            g.drawImage(previousImage, 0, 0, null);
        }
    }

    public void resetToolButtonBorders() {
        for (Map.Entry<String, ToolButton> pair : toolButtons.entrySet()) {
            pair.getValue().setBorderPainted(false);
        }
    }

    class MyPanel extends JPanel {
        public MyPanel() {
            if (mainImage == null) {
                mainImage = new BufferedImage(mainFrame.getWidth(), mainFrame.getHeight(), BufferedImage.TYPE_INT_RGB);
                Graphics2D g2 = mainImage.createGraphics();
                g2.setColor(Color.white);
                g2.fillRect(0, 0, mainFrame.getWidth(), mainFrame.getHeight());
            }
        }

        public void paintComponent (Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint (RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON );
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            g2.setRenderingHint (RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_NORMALIZE);
            g2.setRenderingHint (RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR );

            g2.drawImage(mainImage, 0, 0,this);
        }
    }

    public class ColorDialog extends JDialog {

        public ColorDialog (JFrame owner) {
            super(owner, COLOR_DIALOG_TITLE, true);
            add(colorChooser);
            setSize(COLOR_DIALOG_WIDTH, COLOR_DIALOG_HEIGHT);
        }
    }


    public BufferedImage getPreviousImage() {
        return previousImage;
    }

    public void setPreviousImage(BufferedImage previousImage) {
        this.previousImage = previousImage;
    }

    public Map<String, ToolButton> getToolButtons() {
        return toolButtons;
    }




    public JFrame getMainFrame() {
        return mainFrame;
    }

    public JMenuBar getMainMenu() {
        return mainMenu;
    }

    public JMenu getFileMenu() {
        return fileMenu;
    }

    public JMenuItem getLoadMenu() {
        return loadMenu;
    }

    public JMenuItem getSaveMenu() {
        return saveMenu;
    }

    public JMenuItem getSaveAsMenu() {
        return saveAsMenu;
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public JToolBar getToolBar() {
        return toolBar;
    }

    public JToolBar getColorBar() {
        return colorBar;
    }

    public JButton getUndoButton() {
        return undoButton;
    }

    public JButton getRedoButton() {
        return redoButton;
    }

    public JButton getColorButton() {
        return colorButton;
    }

    public ColorButton getRedButton() {
        return redButton;
    }

    public ColorButton getBlackButton() {
        return blackButton;
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

    public JColorChooser getColorChooser() {
        return colorChooser;
    }

    public JFileChooser getFileChooser() {
        return fileChooser;
    }

    public JButton getCleanButton() {
        return cleanButton;
    }

    public BufferedImage getMainImage() {
        return mainImage;
    }

    public Color getMainColor() {

        return mainColor;
    }

    public JButton getDiscolorButton() {
        return discolorButton;
    }

    public void setMainImage(BufferedImage mainImage) {
        this.mainImage = mainImage;
    }

    public void setMainColor(Color mainColor) {
        this.mainColor = mainColor;
    }

    public JButton getCalculatorButton() {
        return calculatorButton;
    }
}
