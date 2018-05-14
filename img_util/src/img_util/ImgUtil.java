package img_util;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.FileImageInputStream;
import javax.imageio.stream.ImageInputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.monte.media.jpeg.CMYKJPEGImageReader;

public class ImgUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(ImgUtil.class);
	
	public void method01(File file,String savePath) {
		BufferedImage bufferedImage;

		try {

			// read image file
			bufferedImage = ImageIO.read(file);

			// create a blank, RGB, same width and height, and a white
			// background
			BufferedImage newBufferedImage = new BufferedImage(bufferedImage.getWidth(), bufferedImage.getHeight(),
					BufferedImage.TYPE_INT_RGB);
			newBufferedImage.createGraphics().drawImage(bufferedImage, 0, 0, Color.WHITE, null);

			// write to jpeg file
			ImageIO.write(newBufferedImage, "jpg", new File(savePath+"\\"+file.getName().replaceAll("png", "jpg")));

		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	
	public void method02(File file,String savePath){
		if(file.isFile()){
			BufferedImage bufferedImage = null;
			try {
				// read image file
				bufferedImage = ImageIO.read(file);
			}
			catch(IOException e){
				try {
					// CMYK JPEG 읽기
					bufferedImage=this.loadImage(file);
				} catch (IOException e1) {
					logger.info("이미지 읽기 실패 : "+file.getName());
					logger.debug(e1.getMessage());
				}	
			}
			// write to jpeg file
			try {
				Image tmp = bufferedImage.getScaledInstance(354,472, Image.SCALE_SMOOTH);
			    BufferedImage resized = new BufferedImage(354,472, BufferedImage.TYPE_INT_RGB);
			    Graphics2D g2d = resized.createGraphics();
			    g2d.drawImage(tmp, 0, 0, null);
			    g2d.dispose(); 
				ImageIO.write(resized, "jpg", new File(savePath+"\\"+file.getName().replaceAll("png", "jpg")));
			} catch (IOException e) {
				logger.info("이미지 변환 실패 : "+file.getName());
				logger.debug(e.getMessage());
			}
		}
	}
	
	private BufferedImage loadImage(File file) throws IOException {
	    try (ImageInputStream iis = new FileImageInputStream(file)) {
	      long start = System.currentTimeMillis();
	      ImageReader r = new CMYKJPEGImageReader();
	      r.setInput(iis);
	      BufferedImage cmykImage = r.read(0);
	      long end = System.currentTimeMillis();
	      System.out.println("load ms:" + (end - start));
	      return cmykImage;
	    }
	  }
}
