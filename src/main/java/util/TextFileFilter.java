package util;

import javax.swing.filechooser.FileFilter;
import java.io.File;

public class TextFileFilter extends FileFilter {
    
    private String pattern;
    
    public TextFileFilter(String pattern) {
        this.pattern = pattern;
    }
    
    public boolean accept(File file) {
        if (file.isDirectory()) {
            return true;
        }
        
        return (file.getName().endsWith(pattern));
    }
    
    public String getDescription()
    {
        return "*" + pattern;
    }
}
