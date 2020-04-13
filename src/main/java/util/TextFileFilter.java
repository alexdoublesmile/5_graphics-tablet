package util;

import javax.swing.filechooser.FileFilter;
import java.io.File;

public class TextFileFilter extends FileFilter {
    private static final String PNG_FORMAT = "png";
    private static final String JPG_FORMAT = "jpg";
    private static final String JPEG_FORMAT = "jpeg";
    private static final String BMP_FORMAT = "bmp";
    private static final String PDF_FORMAT = "pdf";

    private String fileFormat;
    private String fileResolution;
    
    public TextFileFilter(String fileFormat) {
        this.fileFormat = fileFormat;
        this.fileResolution = "." + fileFormat;
    }
    
    public boolean accept(File file) {
        if (file.isDirectory()) {
            return true;
        }
        return (file.getName().endsWith(fileResolution));
    }

    @Override
    public String getDescription() {
        return String.format("*%s", fileResolution);
    }

    public String getFileFormat() {
        return fileFormat;
    }

    public String getFileResolution() {
        return fileResolution;
    }

    public static String getPngFormat() {
        return PNG_FORMAT;
    }

    public static String getJpgFormat() {
        return JPG_FORMAT;
    }

    public static String getJpegFormat() {
        return JPEG_FORMAT;
    }

    public static String getBmpFormat() {
        return BMP_FORMAT;
    }

    public static String getPdfFormat() {
        return PDF_FORMAT;
    }
}
