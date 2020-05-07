package view.swing;

import config.Config;
import controller.swing.listeners.KeyboardListener;
import controller.swing.listeners.MouseDrawListener;
import model.DrawMode;
import model.Model;
import model.UndoRedoService;
import org.imgscalr.Scalr;
import util.IconBuilder;
import view.View;
import view.swing.buttons.ButtonTabComponent;
import view.swing.buttons.ColorButton;
import view.swing.buttons.FunctionButton;
import view.swing.buttons.ToolButton;

import javax.swing.*;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.TabbedPaneUI;
import javax.swing.plaf.basic.BasicTabbedPaneUI;
import javax.swing.plaf.basic.BasicTabbedPaneUI.MouseHandler;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SwingViewImpl extends JFrame implements View {
    private String MAIN_FRAME_NAME = "Графический планшетик";
    private static final String FILE_MENU_NAME = "File";
    private static final String DISCOLOR_BUTTON_NAME = "Discolor";
    private static final String CLEAN_BUTTON_NAME = "Clean";
    private static final Color CONTROL_PANEL_COLOR = new Color(0xE9D6BF);
    private static final String COLOR_DIALOG_TITLE = "Choose color";
    private static final ArrayList<String> closingElements;

    static {
        closingElements = new ArrayList<>();
        closingElements.add("RAG");
        closingElements.add("MINUS");
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
    private JTabbedPane tabbedPane;
    private JLayeredPane layeredPane;

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
    private ColorButton greyButton;
    private ColorButton blueButton;
    private ColorButton greenButton;
    private ColorButton whiteButton;
    private ColorButton orangeButton;

    private JButton undoButton;
    private JButton redoButton;
    private JButton plusButton;
    private JButton minusButton;
    private JButton refreshButton;
    private JButton expandButton;

    private JColorChooser colorChooser;
    private JFileChooser fileChooser;

    private JButton discolorButton;
    private JButton cleanButton;
    private JButton calculatorButton;

    private ArrayList<JPanel> panelList;
    private ArrayList<BufferedImage> imageList;
    private BufferedImage mainImage;
    private BufferedImage pictureImage;
    private BufferedImage previousImage;
    private Color mainColor;
    private double imageScale;
    private int scaledX;
    private int scaledY;
    private int scaledWidth;
    private int scaledHeight;
    private boolean extended;
    private int tabCounter;

    public SwingViewImpl(Model model) {
        this.model = model;
        toolButtons = new HashMap<>();
        mainColor = new Color(100, 100, 100);
        imageScale = 1;
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
        mainFrame = this;
        setTitle(MAIN_FRAME_NAME);
        setSize(MAIN_FRAME_WIDTH, MAIN_FRAME_HEIGHT);
        setExtendedState(MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        tabbedPane = new JTabbedPane(JTabbedPane.BOTTOM, JTabbedPane.WRAP_TAB_LAYOUT);
        tabbedPane.addChangeListener(e -> previousImage = null);
    }

    private void initMenu() {
        mainMenu = new JMenuBar();
        mainMenu.setBackground(CONTROL_PANEL_COLOR);
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

        for (DrawMode drawMode : DrawMode.values()) {
            if (!model.getSpecialModeList().contains(drawMode)) {
                ToolButton toolButton = new ToolButton(IconBuilder.buildIconByDrawMode(drawMode));
                toolButtons.put(drawMode.name(), toolButton);
                toolButton.setToolTipText(new StringBuilder(drawMode.name())
                        .toString().toLowerCase());
            }
        }
    }

    private void initColorBar() {
        colorBar = new JToolBar(JToolBar.HORIZONTAL);
        colorBar.setBackground(CONTROL_PANEL_COLOR);
        colorBar.setBorderPainted(false);
        colorBar.setLayout(null);

        colorButton = new ColorButton(mainColor, 25);
        colorButton.setIcon(IconBuilder.buildIconByPath(Config.getProperty(Config.PALETTE_ICON_PATH)));
        redButton = new  ColorButton(Color.red, true, 15);
        blackButton = new  ColorButton(Color.black);
        greyButton = new  ColorButton(new Color(100, 100, 100));
        blueButton = new ColorButton(Color.blue);
        greenButton = new  ColorButton(new Color(0x12A612));
        orangeButton = new  ColorButton(new Color(250, 125, 0));
        whiteButton = new  ColorButton(Color.white);
        colorChooser = new  JColorChooser(mainColor);

    }

    private void initButtons() {
        undoButton = new  ToolButton(IconBuilder.buildIconByPath(
                Config.getProperty(Config.UNDO_ICON_PATH)));
        redoButton = new  ToolButton(IconBuilder.buildIconByPath(
                Config.getProperty(Config.REDO_ICON_PATH)));
        plusButton = new  ToolButton(IconBuilder.buildIconByPath(
                Config.getProperty(Config.PLUS_ICON_PATH)));
        minusButton = new  ToolButton(IconBuilder.buildIconByPath(
                Config.getProperty(Config.MINUS_ICON_PATH)));
        refreshButton = new  ToolButton(IconBuilder.buildIconByPath(
                Config.getProperty(Config.REFRESH_ICON_PATH)));
        expandButton = new  ToolButton(IconBuilder.buildIconByPath(
                Config.getProperty(Config.EXPAND_ICON_PATH)));
        calculatorButton = new  ToolButton(IconBuilder.buildIconByPath(
                Config.getProperty(Config.CALCULATOR_ICON_PATH)
        ));

        discolorButton = new FunctionButton(DISCOLOR_BUTTON_NAME);
        cleanButton = new FunctionButton(CLEAN_BUTTON_NAME);
    }

    private void initDrawingPanel() {
        panelList = new ArrayList<>();
        imageList = new ArrayList<>();
        mainPanel = new MyPanel(true);
        mainPanel.setBounds(0,0,mainFrame.getWidth(),mainFrame.getHeight());

        panelList.add(mainPanel);
        addTab(getDefaultTabName(), 0);

    }

    private void collectAllElements() {
        setJMenuBar(mainMenu);
        add(tabbedPane);

        mainMenu.add(fileMenu);
        mainMenu.add(toolBar);
        mainMenu.add(colorBar);
        mainMenu.add(new JToolBar.Separator());
        fileMenu.add(loadMenu);
        fileMenu.add(saveMenu);
        fileMenu.add(saveAsMenu);

        for (DrawMode drawMode : DrawMode.values()) {
            if (!model.getSpecialModeList().contains(drawMode)) {
                if (closingElements.contains(drawMode.name())) {
                    toolBar.add(toolButtons.get(drawMode.name()));
                    toolBar.addSeparator();
                } else {
                    toolBar.add(toolButtons.get(drawMode.name()));
                }
            }
        }

        toolBar.addSeparator();
        toolBar.add(undoButton);
        toolBar.add(redoButton);
        toolBar.addSeparator();

        toolBar.add(plusButton);
        toolBar.add(minusButton);
        toolBar.add(refreshButton);
//        toolBar.add(expandButton);
        colorBar.add(colorButton);
        colorBar.add(blackButton);
        colorBar.add(greyButton);
        colorBar.add(redButton);
        colorBar.add(blueButton);
        colorBar.add(greenButton);
        colorBar.add(whiteButton);
        colorBar.add(orangeButton);

        mainMenu.add(discolorButton);
        discolorButton.setMnemonic('d');
        mainMenu.add(new JToolBar.Separator());
        mainMenu.add(cleanButton);
        cleanButton.setMnemonic('c');
        mainMenu.add(new JToolBar.Separator());
        mainMenu.add(calculatorButton);
        mainMenu.add(new JToolBar.Separator());
//        undoButton.setEnabled(false);
//        redoButton.setEnabled(false);
        refreshButton.setEnabled(false);

        setUndecorated(true);
    }

    public void saveCurrentImage() {
        previousImage = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics g = previousImage.getGraphics();
        g.drawImage(imageList.get(tabbedPane.getSelectedIndex()), 0, 0, null);
//        if (previousImageList.size() > 0) {
//            previousImageList.set(0, previousImage);
//        } else {
//            previousImageList.add(previousImage);
//        }
    }

    public void loadSavedImage() {
        Graphics g = imageList.get(tabbedPane.getSelectedIndex()).getGraphics();
        if (previousImage != null) {
            g.drawImage(previousImage, 0, 0, null);
        }
    }

    public void resetToolButtonBorders() {
        for (Map.Entry<String, ToolButton> pair : toolButtons.entrySet()) {
            pair.getValue().setBorderPainted(false);
        }
    }

    public BufferedImage getPictureImage() {
        return pictureImage;
    }

    public void setPictureImage(BufferedImage pictureImage) {
        this.pictureImage = pictureImage;
    }

    public class MyPanel extends JPanel {

        public MyPanel(boolean buffer) {
            super(buffer);

            mainImage = new BufferedImage(mainFrame.getWidth(), mainFrame.getHeight(), BufferedImage.TYPE_INT_RGB);
            Graphics2D g2 = (Graphics2D) mainImage.getGraphics();
            g2.setColor(Color.white);
            g2.fillRect(0, 0, mainFrame.getWidth(), mainFrame.getHeight());

            if (tabbedPane.getTabCount() < 1) {
                imageList.add(mainImage);
            } else {
                imageList.add(tabbedPane.getSelectedIndex() + 1, mainImage);
            }
        }

        public void paintComponent (Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
//            g2.translate(scaledX, scaledY);
//            g2.scale(imageScale, imageScale);

            g2.drawImage(getCurrentImage(), 0, 0,null);
//            if (pictureImage != null) {
//                g2.drawImage(pictureImage, 0, 0,null);
//
//            }

        }
    }

    public BufferedImage getCurrentImage() {

        BufferedImage image = Scalr.resize(
                imageList.get(tabbedPane.getSelectedIndex()),
                imageList.get(tabbedPane.getSelectedIndex()).getWidth());

        scaledWidth = image.getWidth();
        scaledHeight = image.getHeight();

//        scaledX = (newWidth - scaledWidth) / 2;
//        scaledY = (newWidth - scaledHeight) / 2;

//        image.flush();
        return image;
    }

    public class ColorDialog extends JDialog {

        private ColorDialog window;

        public ColorDialog (JFrame owner) {
            super(owner, COLOR_DIALOG_TITLE, true);
            window = this;
            setLayout(new BorderLayout());
//            JPanel panel = new JPanel();
//            panel.setLayout(new GridLayout(3, 1));
//            JSlider slider = new JSlider();
//            JLabel label = new JLabel("Touch the Slider! ;)");
//            slider.setMajorTickSpacing(10);
//            slider.setMinorTickSpacing(1);
//            slider.setLabelTable(slider.createStandardLabels(10));
//            slider.setPaintTicks(true);
//            slider.setPaintLabels(true);
//            slider.setSnapToTicks(true);
//
//            slider.addChangeListener(e -> {
//                label.setText(String.format("Slider value is %d", slider.getValue()));
//            });
            JButton closeButton = new JButton("Close");
            closeButton.setMnemonic('c');
            closeButton.addActionListener((e) -> window.setVisible(false));
            add(colorChooser, BorderLayout.NORTH);
            add(closeButton, BorderLayout.SOUTH);
//            add(panel, BorderLayout.EAST);
//            panel.add(label);
//            panel.add(slider);
//            panel.add(closeButton);

            setSize(COLOR_DIALOG_WIDTH, COLOR_DIALOG_HEIGHT);
            pack();
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
        return panelList.get(tabbedPane.getSelectedIndex());
    }

    public BufferedImage getMainImage() {
        return imageList.get(tabbedPane.getSelectedIndex());
    }

    public void setMainImage(BufferedImage mainImage) {
        imageList.set(tabbedPane.getSelectedIndex(), mainImage);
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

    public Color getMainColor() {

        return mainColor;
    }

    public JButton getDiscolorButton() {
        return discolorButton;
    }


    public void setMainColor(Color mainColor) {
        this.mainColor = mainColor;
    }

    public JButton getCalculatorButton() {
        return calculatorButton;
    }

    public JButton getPlusButton() {
        return plusButton;
    }

    public JButton getMinusButton() {
        return minusButton;
    }

    public JButton getRefreshButton() {
        return refreshButton;
    }

    public double getImageScale() {
        return imageScale;
    }

    public void setImageScale(double imageScale) {
        this.imageScale = imageScale;
    }

    public ColorButton getOrangeButton() {
        return orangeButton;
    }

    public int getScaledX() {
        return scaledX;
    }

    public void setScaledX(int scaledX) {
        this.scaledX = scaledX;
    }

    public int getScaledY() {
        return scaledY;
    }

    public void setScaledY(int scaledY) {
        this.scaledY = scaledY;
    }

    public void setTranslateImage(int x, int y) {
        this.scaledX = x;
        this.scaledY = y;
    }

    public JButton getExpandButton() {
        return expandButton;
    }

    public boolean isExtended() {
        return extended;
    }

    public void setExtended(boolean extended) {
        this.extended = extended;
    }

    public ColorButton getGreyButton() {
        return greyButton;
    }

    public JTabbedPane getTabbedPane() {
        return tabbedPane;
    }

    public ArrayList<JPanel> getPanelList() {
        return panelList;
    }

    public ArrayList<BufferedImage> getImageList() {
        return imageList;
    }

    public void setMainPanel(JPanel mainPanel) {

        panelList.set(tabbedPane.getSelectedIndex(), mainPanel);
    }

    public String getDefaultTabName() {
        tabCounter++;
        return String.format("Panel %d", tabCounter);
    }

    public void addTab(String tabName, int index) {

//        JPanel tabHeader = new JPanel();
//
//        tabHeader.setLayout(new GridBagLayout());
//
//        GridBagConstraints labelConst = new GridBagConstraints(
//                0,0,
//                1,1,
//                0.0,0.9,
//                GridBagConstraints.NORTH,
//                GridBagConstraints.HORIZONTAL,
//                new Insets(0,0,0,0),
//                0,0);
//
//        GridBagConstraints buttonConst = new GridBagConstraints(
//                1,0,
//                1,1,
//                0.0,0.9,
//                GridBagConstraints.NORTH,
//                GridBagConstraints.HORIZONTAL,
//                new Insets(0,5,0,0),
//                0,0);
//
//        JButton tabCloseButton = new JButton(IconBuilder.buildIconByPath(Config.getProperty(Config.TAB_CLOSE_ICON_PATH)));
//        tabCloseButton.setPreferredSize(new Dimension(10, 10));
//        tabCloseButton.setBorder(null);
//        tabCloseButton.addActionListener(e -> {
//            if (getTabbedPane().getTabCount() > 0) {
//                getPanelList().remove(getTabbedPane().getSelectedIndex());
//                getImageList().remove(getTabbedPane().getSelectedIndex());
//                model.getUndoList().remove(getTabbedPane().getSelectedIndex());
//
//                getTabbedPane().remove(getTabbedPane().getSelectedIndex());
//                JPanel currentPanel = (JPanel) getTabbedPane().getTabComponentAt(getTabbedPane().getSelectedIndex());
//                currentPanel.getComponent(1).setEnabled(true);
////                removedTabs++;
//            }
//        });
//
//        JLabel tabLabel = new JLabel();
//        tabLabel.setFont(new Font("Comic Sans", Font.BOLD, 11));
//        if (tabName == null) {
//            tabLabel.setText(String.format("panel №%d", panelList.size()));
//        } else {
//            tabLabel.setText(tabName);
//        }
//
//        tabHeader.add(tabLabel, labelConst);
//        tabHeader.add(tabCloseButton, buttonConst);


        if (tabbedPane.getTabCount() < 1) {
            tabbedPane.add(panelList.get(panelList.size() - 1));
            tabbedPane.setTitleAt(0, tabName);
        } else {
            tabbedPane.add(panelList.get(index + 1), index + 1);
            tabbedPane.setTitleAt(index + 1, tabName);
        }

        JPanel tabHeader = new ButtonTabComponent(tabbedPane, this, model);

//        JPopupMenu tabPopupMenu = new JPopupMenu();
//        JMenuItem renameTab = new JMenuItem("Rename");
//        JMenuItem copyTab = new JMenuItem("Duplicate");
////        renameTab.setMnemonic(KeyEvent.VK_R);
////        copyTab.setMnemonic(KeyEvent.VK_D);
////        renameTab.addActionListener(e -> {
////            String answer = (String) JOptionPane.showInputDialog(
////                    this,
////                    null,
////                    String.format("Rename %s Tab", tabLabel.getText()),
////                    JOptionPane.PLAIN_MESSAGE,
////                    null,
////                    null,
////                    tabLabel.getText());
////            if (answer != null && !answer.isEmpty())
////                tabLabel.setText(answer);
////        });
//
//        copyTab.addActionListener(e -> {
//            MouseDrawListener mouseDrawListeners = new MouseDrawListener(this, model);
//
//            JPanel panel = new MyPanel(true);
//
//            panel.addMouseMotionListener(mouseDrawListeners.getMOUSE_MOTION_ADAPTER());
//            panel.addMouseListener(mouseDrawListeners.getMOUSE_ADAPTER());
//
//            getPanelList().add(getTabbedPane().getSelectedIndex() + 1, panel);
//            addTab(getDefaultTabName());
//            Graphics g2 = imageList.get(tabbedPane.getSelectedIndex() + 1).getGraphics();
//            g2.drawImage(imageList.get(tabbedPane.getSelectedIndex()), 0 , 0, null);
//
//            getTabbedPane().setSelectedIndex(getTabbedPane().getSelectedIndex() + 1);
//            if (model.getUndoList().size() <= (getTabbedPane().getSelectedIndex())) {
//                model.getUndoList().add(new UndoRedoService());
//            } else {
//                model.getUndoList().set(getTabbedPane().getSelectedIndex(), new UndoRedoService());
//            }
//
//            panel.addKeyListener(new KeyboardListener(this, model).getKEY_ADAPTER());
//            model.saveAction(getMainImage(), getTabbedPane().getSelectedIndex());
//        });
//
//        tabPopupMenu.add(renameTab);
//        tabPopupMenu.add(copyTab);
//        this.getContentPane().addMouseListener(new MouseAdapter() {
//            @Override
//            public void mousePressed(MouseEvent e) {
//                if (e.isPopupTrigger()) {
//                    tabPopupMenu.show(e.getComponent(), e.getX(), e.getY());
//                }
//            }
//
//            @Override
//            public void mouseReleased(MouseEvent e) {
//                if (e.isPopupTrigger()) {
//                    tabPopupMenu.show(e.getComponent(), e.getX(), e.getY());
//                }
//            }
//        });
//        tabHeader.setComponentPopupMenu(tabPopupMenu);

        if (tabbedPane.getTabCount() <= 1) {
            tabbedPane.setTabComponentAt(
                    tabbedPane.getTabCount() - 1,
                    tabHeader);
        } else {

            tabbedPane.setTabComponentAt(
                    index + 1,
                    tabHeader);
        }



//        tabHeader.getComponent(0).addMouseListener(new MouseAdapter() {
//            @Override
//            public void mousePressed(MouseEvent e) {
//                super.mousePressed(e);
//
//            }
//        });

//        tabbedPane.getModel().addChangeListener(e -> {
//            for (int i = 0; i < tabbedPane.getTabCount(); i++) {
//                JPanel tabPanel = (JPanel) tabbedPane.getTabComponentAt(i);
//                tabPanel.getComponent(1).setEnabled(false);
//            }
//            JPanel tabPanel = (JPanel) tabbedPane.getTabComponentAt(tabbedPane.getSelectedIndex());
//            tabPanel.getComponent(1).setEnabled(true);
//         });

    }


}
