
package Logic;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.scene.image.Image;



public class ImageLoader {
    private List<Image> images;
    
    public ImageLoader(String folderPath) {
        this.images = new ArrayList();
       if(fileExists(folderPath)) {
            loadImages(folderPath);
       } else {
           throw new IllegalArgumentException();
       }
    }
    
    public List getImages() {
        return this.images;
    }
    
    private void loadImages(String folderPath) {
        File folder = new File(folderPath);
        File[] listOfFiles = folder.listFiles();
        
        for(File file : listOfFiles) {
            if(isImage(file.getAbsolutePath())) {
                Image image = new Image("file:"+file);
                this.images.add(image);
            }
        }
    }
    
    private boolean isImage(String path) {
        if(path.endsWith(".png")) {
            return true;
        }
        
        if(path.endsWith(".PNG")) {
            return true;
        }
        
        if(path.endsWith(".jpg")) {
            return true;
        }
        
        if(path.endsWith(".JPG")) {
            return true;
        }
        
        if(path.endsWith(".jpeg")) {
            return true;
        }
        
        if(path.endsWith((".JPEG"))) {
            return true;
        }
        
        if(path.endsWith(".gif")) {
            return true;
        }
        
        if(path.endsWith(".bmp")) {
            return true;
        }
        
        return false;
    }
    
    private boolean fileExists(String path) {
        return Files.exists(Paths.get(path));
    }
}
