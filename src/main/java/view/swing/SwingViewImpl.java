package view.swing;

import config.Config;
import model.DrawMode;
import model.Model;
import util.CursorBuilder;
import util.IconBuilder;
import view.View;
import view.swing.buttons.ColorButton;
import view.swing.buttons.FunctionButton;
import view.swing.buttons.ToolButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class SwingViewImpl extends JFrame implements View {

    private String MAIN_FRAME_NAME = "Графический планшетик";
    private static final String FILE_MENU_NAME = "Файл";
    private static final String TRANSFORM_IMAGE_BUTTON_NAME = "Обесцветить";
    private static final String CLEAN_BUTTON_NAME = "Очистить планшетик";
    private static final Color CONTROL_PANEL_COLOR = new Color(0xE9D6BF);

    private Model model;

    private final Map<String, ToolButton> toolButtons;

    private JFrame mainFrame;

    private JMenuBar mainMenu;
    private JMenu fileMenu;
    private JMenuItem loadMenu;
    private JMenuItem saveMenu;
    private JMenuItem saveAsMenu;

    private JPanel mainPanel;
    private JToolBar toolBar;
    private JToolBar colorBar;

    private JButton undoButton;
    private JButton redoButton;

    private JButton colorButton;
    private ColorButton redButton;
    private ColorButton blackButton;
    private ColorButton blueButton;
    private ColorButton greenButton;
    private ColorButton whiteButton;
    private JColorChooser colorChooser;
    private JFileChooser fileChooser;

    private JButton discolorButton;
    private JButton cleanButton;
    private JButton calculatorButton;

    private BufferedImage mainImage;
    private BufferedImage previousImage;
    private Color mainColor;
    private boolean isNotRepainting = true;

    public SwingViewImpl(Model model) {
        this.model = model;
        toolButtons = new HashMap<>();
        initToolButtons();
        createView();
    }

    private void initToolButtons() {
        for (DrawMode drawMode : DrawMode.values()) {
            toolButtons.put(
                    drawMode.name(),
                    new ToolButton(IconBuilder.buildIconByDrawMode(drawMode)));
        }
    }

//    private void initColorButtons() {
//        for (DrawMode drawMode : DrawMode.values()) {
//            toolButtons.put(
//                    drawMode.name(),
//                    new ToolButton(IconBuilder.buildIconByDrawMode(drawMode)));
//        }
//    }

    private void createView() {
        this.mainFrame = this;
        this.setTitle(MAIN_FRAME_NAME);
        this.setSize(6400, 4800);
        this.setVisible(true);
        this.setExtendedState(MAXIMIZED_BOTH);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setBackground(Color.yellow);
        this.mainColor = Color.black;

        mainMenu = new JMenuBar();
        mainMenu.setBackground(CONTROL_PANEL_COLOR);
//        mainMenu.setBounds(0,0,350,20);

        fileMenu = new JMenu(FILE_MENU_NAME);

        loadMenu = new JMenuItem();
        saveMenu = new JMenuItem();
        saveAsMenu = new JMenuItem();

        mainPanel = new MyPanel();
        mainPanel.setFocusable(true);
        mainPanel.setBounds(0,0,mainFrame.getWidth(),mainFrame.getHeight());
        mainPanel.setBackground(Color.white);
        mainPanel.setOpaque(true);
        try {
            mainPanel.setCursor(CursorBuilder.buildCursorByDrawMode(DrawMode.PENCIL));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        toolBar = new JToolBar(JToolBar.HORIZONTAL);
        toolBar.setBackground(CONTROL_PANEL_COLOR);
//        toolBar.setBounds(0, 0, 300, 30);

        undoButton = new  ToolButton(IconBuilder.buildIconByPath(
                Config.getProperty(Config.UNDO_ICON_PATH)));
        redoButton = new  ToolButton(IconBuilder.buildIconByPath(
                Config.getProperty(Config.REDO_ICON_PATH)));
        calculatorButton = new  ToolButton(IconBuilder.buildIconByPath(
                Config.getProperty(Config.CALCULATOR_ICON_PATH)
        ));

        colorBar = new  JToolBar(JToolBar.HORIZONTAL);
        colorBar.setBackground(CONTROL_PANEL_COLOR);

//        colorBar.setBounds(30, 0, 160, 20);

        colorBar.setLayout(null);
        colorButton = new ColorButton(mainColor, 25);
        redButton = new  ColorButton(Color.red, true, 15);
        blackButton = new  ColorButton(Color.black);
        blueButton = new ColorButton(Color.blue);
        greenButton = new  ColorButton(new Color(0x12A612));
        whiteButton = new  ColorButton(Color.white);
//        whiteButton.setBorderPainted(true);

        colorChooser = new  JColorChooser(mainColor);
        colorChooser.getSelectionModel().addChangeListener(e -> {
            mainColor = colorChooser.getColor();
            colorButton.setBackground(mainColor);
        });

        fileChooser = new JFileChooser();

        discolorButton = new FunctionButton(TRANSFORM_IMAGE_BUTTON_NAME);
        cleanButton = new FunctionButton(CLEAN_BUTTON_NAME);


        fileMenu.add(loadMenu);
        fileMenu.add(saveMenu);
        fileMenu.add(saveAsMenu);

        toolBar.add(toolButtons.get(DrawMode.PENCIL.name()));
        toolBar.add(toolButtons.get(DrawMode.MARKER.name()));
        toolBar.add(toolButtons.get(DrawMode.BRUSH.name()));
        toolBar.add(toolButtons.get(DrawMode.ERASER.name()));
        toolBar.add(toolButtons.get(DrawMode.RAG.name()));
        toolBar.addSeparator();
        toolBar.add(toolButtons.get(DrawMode.LINE.name()));
        toolBar.add(toolButtons.get(DrawMode.DOTTEDLINE.name()));
        toolBar.add(toolButtons.get(DrawMode.CIRCLE.name()));
        toolBar.add(toolButtons.get(DrawMode.RECT.name()));
        toolBar.add(toolButtons.get(DrawMode.PYRAMID.name()));
        toolBar.add(toolButtons.get(DrawMode.PRISM.name()));
        toolBar.addSeparator();
        toolBar.add(undoButton);
        toolBar.add(redoButton);
        toolBar.addSeparator();
//        toolBar.add(toolButtons.get(DrawMode.TEXT.name()));
        toolBar.add(toolButtons.get(DrawMode.FILL.name()));
        toolBar.addSeparator();

        colorBar.add(colorButton);
        colorBar.add(redButton);
        colorBar.add(blackButton);
        colorBar.add(blueButton);
        colorBar.add(greenButton);
        colorBar.add(whiteButton);

        mainMenu.add(fileMenu);
        mainMenu.add(toolBar);
        mainMenu.add(colorBar);
        mainMenu.add(new JToolBar.Separator());

        mainMenu.add(discolorButton);
        mainMenu.add(new JToolBar.Separator());
        mainMenu.add(cleanButton);
        mainMenu.add(new JToolBar.Separator());
        mainMenu.add(calculatorButton);
        mainMenu.add(new JToolBar.Separator());

        this.setJMenuBar(mainMenu);
        this.add(mainPanel);
    }

    @Override
    public void drawTablet() {

        // если делаем загрузку, то изменение размеров формы отрабатываем в коде загрузки
        mainFrame.addComponentListener(new  ComponentAdapter() {
            public void componentResized(ComponentEvent evt) {
                if(model.isLoading() == false) {
//
//                    JOptionPane.showMessageDialog(mainFrame,
//                            "Такого файла не существует");
                    mainPanel.setSize(mainImage.getWidth(), mainImage.getHeight());
                    BufferedImage tempImage = new  BufferedImage(mainPanel.getWidth(), mainPanel.getHeight(), BufferedImage.TYPE_INT_RGB);
                    Graphics2D d2 = (Graphics2D) tempImage.createGraphics();
                    d2.setColor(Color.white);
                    d2.fillRect(0, 0, mainPanel.getWidth(), mainPanel.getHeight());
                    tempImage.setData(mainImage.getRaster());
                    mainImage=tempImage;
                    mainPanel.repaint();
                }
                model.setLoading(false);
            }
        });
    }

    public void resetToolButtonBorders() {
        for (Map.Entry<String, ToolButton> pair : toolButtons.entrySet()) {
            pair.getValue().setBorderPainted(false);
        }
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

        public ColorDialog (JFrame owner, String title, int width, int height) {
            super(owner, title, true);
            add(colorChooser);
            setSize(width, height);
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








    public static String getFileMenuName() {
        return FILE_MENU_NAME;
    }

    public static String getCleanButtonName() {
        return CLEAN_BUTTON_NAME;
    }

    public Model getModel() {
        return model;
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

    public boolean isNotRepainting() {
        return isNotRepainting;
    }

    public void setNotRepainting(boolean notRepainting) {
        isNotRepainting = notRepainting;
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
