/* 
 * 20230603 VickyTsai 把一個資料夾的所有圖片壓縮成指定尺寸
 */
package Research.ForResearch;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ImageCompression {
    public static void main(String[] args) {
    	String parentDir = "D:\\DataSet_TuBerlin_Sketch\\png\\";
    	
    	String[] dirNames = new File(parentDir).list();
    	
    	for(String dirName : dirNames) {
    		doCompress(dirName);
    	}		
        
    }
    
    
    public static void doCompress(String dirName) {
    	String imageDir = "D:\\DataSet_TuBerlin_Sketch\\png\\"+dirName;  // 圖片所在的目錄
        String outputDir = "D:\\DataSet_TuBerlin_Sketch\\png_compressed\\"+dirName;  // 壓縮後的圖片保存目錄
        float compressionRatio = 0.5f;  // 壓縮比例

        File dir = new File(imageDir);
        File[] files = dir.listFiles();

        if (files != null) {
        	System.out.println("get file success");
            for (File file : files) {

                if (file.isFile()) {
                    String filename = file.getName();
                    String extension = filename.substring(filename.lastIndexOf(".") + 1).toLowerCase();
                    System.out.println(filename);
                    // 檢查是否為圖片檔案
                    if (extension.equals("jpg") || extension.equals("jpeg") || extension.equals("png")) {
                        try {
                            // 讀取原始圖片
                            BufferedImage originalImage = ImageIO.read(file);

                            // 計算壓縮後的寬度和高度
                            int width = (int) (originalImage.getWidth() * compressionRatio);
                            int height = (int) (originalImage.getHeight() * compressionRatio);

                            // 建立壓縮後的圖片
                            BufferedImage compressedImage = new BufferedImage(width, height, originalImage.getType());

                            // 壓縮圖片
                            compressedImage.getGraphics().drawImage(originalImage, 0, 0, width, height, null);

                            // 構建壓縮後的圖片的保存路徑
                            String outputFilePath = outputDir + File.separator + filename;
                            File outputFileDir = new File(outputDir);
                            if(!outputFileDir.exists()) {
                            	new File(outputDir).mkdirs();
                            }
                            	ImageIO.write(compressedImage, extension, new File(outputFilePath));
                            // 保存壓縮後的圖片

                            System.out.println("圖片壓縮完成: " + outputFilePath);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
	}
}
