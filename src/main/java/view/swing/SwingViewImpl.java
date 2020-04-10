package view.swing;

import model.PaintModel;
import util.CursorBuilder;
import util.IconBuilder;
import view.View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

public class SwingViewImpl extends JFrame implements View {

    private String MAIN_FRAME_NAME = "Графический планшетик";
    private static final String FILE_MENU_NAME = "Файл";
    private static final String TRANSFORM_IMAGE_BUTTON_NAME = "Обесцветить";
    private static final String CLEAN_BUTTON_NAME = "Очистить планшетик";

    private PaintModel model;

    private JFrame mainFrame;

    private JMenuBar mainMenu;
    private JMenu fileMenu;
    private JMenuItem loadMenu;
    private JMenuItem saveMenu;
    private JMenuItem saveAsMenu;

    private JPanel mainPanel;
    private JToolBar toolBar;
    private JToolBar colorBar;

    private JButton pencilButton;
    private JButton markerButton;
    private JButton brushButton;
    private JButton eraserButton;
    private JButton lineButton;
    private JButton dottedLineButton;
    private JButton ellipseButton;
    private JButton rectButton;
    private JButton pyramidButton;
    private JButton prismButton;
    private JButton fillButton;

    private JButton undoButton;
    private JButton redoButton;
    private JButton textButton;
    private JButton calculatorButton;

    private JButton colorButton;
    private JButton redButton;
    private JButton blackButton;
    private JButton blueButton;
    private JButton greenButton;
    private JButton whiteButton;
    private JColorChooser colorChooser;
    private JFileChooser fileChooser;

    private JButton transformImageButton;
    private JButton cleanButton;

    private BufferedImage mainImage;
    private Color mainColor;
    private int drawNumber = 2;
    private boolean isNotRepainting = true;

    public SwingViewImpl(PaintModel model) {
        this.model = model;
        createView();
    }

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
        mainPanel.setCursor(new CursorBuilder().getPENCIL_CURSOR());

        toolBar = new JToolBar(JToolBar.HORIZONTAL);
//        toolBar.setBounds(0, 0, 300, 30);
        pencilButton = new ToolButton(new IconBuilder().getPENCIL_ICON());
        markerButton = new ToolButton(new IconBuilder().getMARKER_ICON());
        brushButton = new ToolButton(new IconBuilder().getBRUSH_ICON());
        eraserButton = new ToolButton(new IconBuilder().getERASER_ICON());
        lineButton = new ToolButton(new IconBuilder().getLINE_ICON());
        dottedLineButton = new ToolButton(new IconBuilder().getDOTTED_LINE_ICON());
        ellipseButton = new ToolButton(new IconBuilder().getELLIPSE_ICON());
        rectButton = new ToolButton(new IconBuilder().getRECT_ICON());
        pyramidButton = new ToolButton(new IconBuilder().getPYRAMID_ICON());
        prismButton = new ToolButton(new IconBuilder().getPRISM_ICON());

        undoButton = new  ToolButton(new IconBuilder().getUNDO_ICON());
        redoButton = new  ToolButton(new IconBuilder().getREDO_ICON());
        textButton = new  ToolButton(new IconBuilder().getTEXT_ICON());
        fillButton = new ToolButton(new IconBuilder().getFILL_ICON());


        colorBar = new  JToolBar(JToolBar.HORIZONTAL);
//        colorBar.setBounds(30, 0, 160, 20);

        colorBar.setLayout(null);
        colorButton = new ColorButton(mainColor, 25);
        redButton = new  ColorButton(Color.red, true, 15);
        blackButton = new  ColorButton(Color.black);
        blueButton = new ColorButton(Color.blue);
        greenButton = new  ColorButton(new Color(0x128D12));
        whiteButton = new  ColorButton(Color.white);

        colorChooser = new  JColorChooser(mainColor);
        colorChooser.getSelectionModel().addChangeListener(e -> {
            mainColor = colorChooser.getColor();
            colorButton.setBackground(mainColor);
        });

        fileChooser = new JFileChooser();

        transformImageButton = new FunctionButton(TRANSFORM_IMAGE_BUTTON_NAME);
        cleanButton = new FunctionButton(CLEAN_BUTTON_NAME);
        calculatorButton = new  ToolButton(new IconBuilder().getCALCULATOR_ICON());


        fileMenu.add(loadMenu);
        fileMenu.add(saveMenu);
        fileMenu.add(saveAsMenu);

        toolBar.add(pencilButton);
        toolBar.add(markerButton);
        toolBar.add(brushButton);
        toolBar.add(eraserButton);
        toolBar.addSeparator();
        toolBar.add(lineButton);
        toolBar.add(dottedLineButton);
        toolBar.add(ellipseButton);
        toolBar.add(rectButton);
        toolBar.add(pyramidButton);
        toolBar.add(prismButton);
        toolBar.addSeparator();
        toolBar.add(undoButton);
        toolBar.add(redoButton);
        toolBar.addSeparator();
        toolBar.add(textButton);
        toolBar.add(fillButton);
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

        mainMenu.add(transformImageButton);
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



    public class ColorDialog extends JDialog {
        public ColorDialog(JFrame owner, String title) {
            super(owner, title, true);
            add(colorChooser);
            setSize(200, 200);
        }
    }

    class MyPanel extends JPanel {

        public MyPanel() {
            if (mainImage == null) {

                mainImage = new BufferedImage(mainFrame.getWidth(), mainFrame.getHeight(), BufferedImage.TYPE_INT_RGB);
                Graphics2D d2 = (Graphics2D) mainImage.createGraphics();
                d2.setColor(Color.white);
                d2.fillRect(0, 0, mainFrame.getWidth(), mainFrame.getHeight());

            }
        }

        public void paintComponent (Graphics g) {

            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint ( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON );
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            g2.setRenderingHint ( RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_NORMALIZE);
            g2.setRenderingHint ( RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR );

            g2.drawImage(mainImage, 0, 0,this);



        }
    }






    public static String getFileMenuName() {
        return FILE_MENU_NAME;
    }

    public static String getCleanButtonName() {
        return CLEAN_BUTTON_NAME;
    }

    public PaintModel getModel() {
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

    public JButton getPencilButton() {
        return pencilButton;
    }

    public JButton getMarkerButton() {
        return markerButton;
    }

    public JButton getEraserButton() {
        return eraserButton;
    }

    public JButton getLineButton() {
        return lineButton;
    }

    public JButton getEllipseButton() {
        return ellipseButton;
    }

    public JButton getRectButton() {
        return rectButton;
    }

    public JButton getTextButton() {
        return textButton;
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

    public JButton getRedButton() {
        return redButton;
    }

    public JButton getBlackButton() {
        return blackButton;
    }

    public JButton getBlueButton() {
        return blueButton;
    }

    public JButton getGreenButton() {
        return greenButton;
    }

    public JButton getWhiteButton() {
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

    public JButton getTransformImageButton() {
        return transformImageButton;
    }

    public int getDrawNumber() {
        return drawNumber;
    }

    public void setDrawNumber(int drawNumber) {
        this.drawNumber = drawNumber;
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

    public JButton getBrushButton() {
        return brushButton;
    }

    public JButton getDottedLineButton() {
        return dottedLineButton;
    }

    public JButton getPyramidButton() {
        return pyramidButton;
    }

    public JButton getPrismButton() {
        return prismButton;
    }

    public JButton getFillButton() {
        return fillButton;
    }

    public JButton getCalculatorButton() {
        return calculatorButton;
    }
}
