package Research.ForResearch;

import java.io.File;
import java.util.Arrays;
import java.util.Iterator;

import javax.swing.plaf.SliderUI;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class DrawingUtils {

	public static Mat newImage;
	public static Scalar colorBlack = new Scalar(0, 0, 0);
	public static Scalar colorWhite = new Scalar(255, 255, 255);
	public static Size newSize = new Size(555, 555); // 圖像高度、寬度
	public static String path1 = "D:\\DataSet_VMI\\DrawByMe\\%s\\1\\";
	public static String path0 = "D:\\DataSet_VMI\\DrawByMe\\%s\\0\\";
	public static double sidelength = 555.0;

	// VMI作答格子邊長是3.25英寸 //書本題號+3
	public static void main(String[] args) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
//		drawNo4(); //=7
//		drawNo5(); //=8
//		drawNo6(); //=9
//		drawNo10();//十字
//		drawNo11();//右斜線  13就把11的水平鏡像
		drawNo12(); //方形
//		draw14(); //X
//		draw15(); //三角形
//		draw16();// 開口方形與圓形

		System.exit(0);
	}

	public static long getFileCount(String path) {
		File dir = new File(path);
		return Arrays.asList(dir.list()).stream().count();
	}

	public static void drawNo4() {
		path0 = "D:\\DataSet_VMI\\04\\0" + File.separator;
		path1 = "D:\\DataSet_VMI\\04\\1" + File.separator;

		long counter0 = getFileCount(path0);
		long counter1 = getFileCount(path1);

		// 畫直線 1/2以上與垂直線不超過30度
		newImage = new Mat(newSize, CvType.CV_8UC3, colorWhite);

//		1分 直線垂直30度內
//		x1 from 0~255
//		for(int x1 = 0; x1 < newImage.cols(); x1+=50) {
//			double y1 = 0;
//			double x2 = newImage.cols()/Math.sqrt(3);
//			double y2 = newImage.rows();
//			newImage = new Mat(newSize, CvType.CV_8UC3, colorWhite);
//			Imgcodecs.imwrite(path1 + ++counter1 +".png", drawVerticalLines(x1, y1, x2, y2, newImage));
//		}

		// 1分 直線垂直30度內 重複畫線
//		for(int x1 = 0; x1 < newImage.cols(); x1+=50) {
//			double y1 = 0;
//			double x2 = newImage.cols()/Math.sqrt(3);
//			double y2 = newImage.rows();
//			newImage = new Mat(newSize, CvType.CV_8UC3, colorWhite);
//			newImage = drawVerticalLines(x1, y1, x2, y2, newImage);
//			newImage = drawVerticalLines(x1+20, y1, x2+20, y2, newImage);
//			newImage = drawVerticalLines(x1+40, y1, x2+40, y2, newImage);
//			newImage = drawVerticalLines(x1+60, y1, x2+60, y2, newImage);
////			highGUIshow(newImage);
//			Imgcodecs.imwrite(path1 + ++counter1 +".png", newImage);
//		}

		// 0分 直線垂直30度外
//		for(int y2 = 0; y2 < newImage.cols(); y2+=50) {
//			double x1 = 0;
//			double y1 = newImage.rows();;
//			double x2 = newImage.cols();
//			newImage = new Mat(newSize, CvType.CV_8UC3, colorWhite);
////			highGUIshow(drawVerticalLines(x1, y1, x2, y2, newImage));
//			Imgcodecs.imwrite(path0 + ++counter0 +".png", drawVerticalLines(x1, y1, x2, y2, newImage));
//		}

//		//0分 直線垂直30度外 重複畫線
//		for(int y2 = 0; y2 < newImage.cols(); y2+=50) {
//			double x1 = 0;
//			double y1 = newImage.rows();;
//			double x2 = newImage.cols();
//			newImage = new Mat(newSize, CvType.CV_8UC3, colorWhite);
//			newImage = drawVerticalLines(x1, y1, x2, y2, newImage);
//			newImage = drawVerticalLines(x1, y1+20, x2, y2+20, newImage);
//			newImage = drawVerticalLines(x1, y1+40, x2, y2+40, newImage);
////			highGUIshow(newImage);
//			Imgcodecs.imwrite(path0 + ++counter0 +".png", newImage);
//		}

		// 0分 直線30度外 換另一邊畫
//		for(int y1 = 0; y1 < newImage.cols(); y1+=50) {
//			double x1 = 0;
////			double y1 = 0;
//			double x2 = newImage.cols();
//			double y2 = newImage.rows();
//			newImage = new Mat(newSize, CvType.CV_8UC3, colorWhite);
//			newImage = drawVerticalLines(x1, y1, x2, y2, newImage);
//			newImage = drawVerticalLines(x1, y1+20, x2, y2+20, newImage);
//			newImage = drawVerticalLines(x1, y1+40, x2, y2+40, newImage);
////			highGUIshow(newImage);
//			Imgcodecs.imwrite(path0 + ++counter0 +".png", newImage);
//		}

	}

	public static void drawNo5() {
		path0 = "D:\\DataSet_VMI\\05\\0" + File.separator;
		path1 = "D:\\DataSet_VMI\\05\\1" + File.separator;
		long counter0 = getFileCount(path0);
		long counter1 = getFileCount(path1);
		newImage = new Mat(newSize, CvType.CV_8UC3, colorWhite);

//		//1分橫線水平30度內
//		for(int y2 = (int)(newImage.rows()/Math.sqrt(3)- newImage.rows()*0.5); y2 < newImage.rows()*0.5; y2+=40) {
//			double x1 = 0;
//			double y1 = newImage.rows()*0.5;
//			double x2 = newImage.cols();
//			newImage = new Mat(newSize, CvType.CV_8UC3, colorWhite);
////			highGUIshow(drawVerticalLines(x1, y1, x2, y2, newImage));
//			Imgcodecs.imwrite(path1 + ++counter1 +".png", drawVerticalLines(x1, y1, x2, y2, newImage));
//		}

//		//1分橫線水平30度內 換一邊畫
//		for(int y1 = (int)(newImage.rows()/Math.sqrt(3)*0.5); y1 < newImage.rows()*0.5; y1+=30) {
//			double x1 = 0;
//			double x2 = newImage.cols();
//			double y2 = newImage.rows()*0.5;
//			newImage = new Mat(newSize, CvType.CV_8UC3, colorWhite);
////			highGUIshow(drawVerticalLines(x1, y1, x2, y2, newImage));
//			Imgcodecs.imwrite(path1 + ++counter1 +".png", drawVerticalLines(x1, y1, x2, y2, newImage));
//		}

		// 1分 橫線水平30度內 重複畫線
//		for(int y2 = (int)(newImage.rows()/Math.sqrt(3)-newImage.rows()*0.5); y2 < newImage.rows()*0.5; y2+=40) {
//			double x1 = 0;
//			double y1 = newImage.rows()*0.5;
//			double x2 = newImage.cols();
//			newImage = new Mat(newSize, CvType.CV_8UC3, colorWhite);
//			newImage = drawVerticalLines(x1, y1, x2, y2, newImage);
//			newImage = drawVerticalLines(x1, y1+20, x2, y2+20, newImage);
//			newImage = drawVerticalLines(x1, y1+40, x2, y2+40, newImage);
////			highGUIshow(newImage);
//			Imgcodecs.imwrite(path1 + ++counter1 +".png", newImage);
//		}

		// 1分 橫線水平30度內 換另一邊畫
//		for(int y1 = (int)(newImage.rows()/Math.sqrt(3)); y1 < newImage.cols(); y1+=40) {
//			double x1 = 0;
////			double y1 = 0;
//			double x2 = newImage.cols();
//			double y2 = newImage.rows();
//			newImage = new Mat(newSize, CvType.CV_8UC3, colorWhite);
//			newImage = drawVerticalLines(x1, y1, x2, y2, newImage);
//			newImage = drawVerticalLines(x1, y1+20, x2, y2+20, newImage);
//			newImage = drawVerticalLines(x1, y1+40, x2, y2+40, newImage);
////			highGUIshow(newImage);
//			Imgcodecs.imwrite(path1 + ++counter1 +".png", newImage);
//		}

//		//0分橫線水平>30度
//		for(int y2 = (int)(newImage.rows()*0.6); y2 < newImage.rows(); y2+=40) {
//			double x1 = 0;
//			double y1 = 0;
//			double x2 = newImage.cols();
//			newImage = new Mat(newSize, CvType.CV_8UC3, colorWhite);
////			highGUIshow(drawVerticalLines(x1, y1, x2, y2, newImage));
//			Imgcodecs.imwrite(path0 + ++counter0 +".png", drawVerticalLines(x1, y1, x2, y2, newImage));
//		}

//		//0分橫線水平>30度 多畫線
//		for(int y2 = (int)(newImage.rows()*0.6); y2 < newImage.rows(); y2+=40) {
//			double x1 = 0;
//			double y1 = 0;
//			double x2 = newImage.cols();
//			newImage = new Mat(newSize, CvType.CV_8UC3, colorWhite);
//			newImage = drawVerticalLines(x1, y1, x2, y2, newImage);
//			newImage = drawVerticalLines(x1, y1+20, x2, y2+20, newImage);
//			newImage = drawVerticalLines(x1, y1+40, x2, y2+40, newImage);
////			highGUIshow(drawVerticalLines(x1, y1, x2, y2, newImage));
//			Imgcodecs.imwrite(path0 + ++counter0 +".png", drawVerticalLines(x1, y1, x2, y2, newImage));
//		}

		// 0分橫線水平>30度 從另一邊畫
//		for(int y2 = 0; y2 < newImage.rows()*0.4; y2+=40) {
//			double x1 = 0;
//			double y1 = newImage.rows();
//			double x2 = 555;
//			newImage = new Mat(newSize, CvType.CV_8UC3, colorWhite);
////			highGUIshow(drawVerticalLines(x1, y1, x2, y2, newImage));
//			Imgcodecs.imwrite(path0 + "050"+ ++counter0 +".png", drawVerticalLines(x1, y1, x2, y2, newImage));
//		}

//		//0分橫線水平>30度 多畫線 從另一邊畫
//		for(int y2 = 0; y2 < newImage.rows()*0.4; y2+=40) {
//			double x1 = 0;
//			double y1 = newImage.rows();
//			double x2 = 555;
//			newImage = new Mat(newSize, CvType.CV_8UC3, colorWhite);
//			newImage = drawVerticalLines(x1, y1, x2, y2, newImage);
//			newImage = drawVerticalLines(x1, y1+20, x2, y2+20, newImage);
//			newImage = drawVerticalLines(x1, y1+40, x2, y2+40, newImage);
////			highGUIshow(newImage);
//			Imgcodecs.imwrite(path0 + "050"+ ++counter0 +".png", newImage);
//		}
	}

	public static void drawNo6() {
		String questionNo = "06";
		path0 = String.format(path0, questionNo);
		path1 = String.format(path1, questionNo);
		long counter0 = getFileCount(path0);
		long counter1 = getFileCount(path1);
		// 畫一串圓
		newImage = new Mat(newSize, CvType.CV_8UC3, colorWhite);
		int centerX = (int) (newImage.cols() * 0.5);
		int centerY = (int) (newImage.cols() * 0.5);
		int radius = 200;

		newImage = new Mat(newSize, CvType.CV_8UC3, colorWhite);
//		for(int i = 0; i < 3; i++) {
		newImage = drawCircle(newImage, centerX, centerY - 100, radius);
//		}
		Imgcodecs.imwrite(path1 + questionNo + ++counter1 + ".png", newImage);

//		highGUIshow(newImage);
	}

	public static void drawNo10() {

		String questionNo = "10";
		path0 = String.format(path0, questionNo);
		path1 = String.format(path1, questionNo);
		long counter0 = getFileCount(path0);
		long counter1 = getFileCount(path1);
		// 畫一串圓
		newImage = new Mat(newSize, CvType.CV_8UC3, colorWhite);
//		double x1 = 0;
//		double y1 = sideLength/2;
//		double x2 = sideLength;
//		double y2 = sideLength/2;
//		
//		//水平	
//		drawVerticalLines(x1, y1, x2, y2, newImage);
//		
//		//垂直
//		double x3 = sideLength/2;
//		double y3 = 0;
//		double x4 = sideLength/2;
//		double y4 = sideLength;
//		drawVerticalLines(x3, y3, x4, y4, newImage);

		// 控制角度在20度內排列組合
//		for(int i = 0; i < 48*2; i+=6) {
//			for(int j = 0; j< 48*2; j+=6) {
//				newImage = new Mat(newSize, CvType.CV_8UC3, colorWhite);
//				drawVerticalLines(x1, y1-48+i, x2, y2+ 48-i, newImage);//水平
//				drawVerticalLines(x3-48+j, y3, x4+ 48-j, y4, newImage);//垂直
//				Imgcodecs.imwrite(path1 + questionNo+"Draw1_" + ++counter1 +".png", newImage);
//			}
//		}
//			highGUIshow(newImage);

		// 控制中線位置

//		//控制角度在20度外排列組合
//		//歪水平
//		double x1 = 0; 
//		double y1 = 0; //0-200
//		double x2 = sideLength;
//		double y2 = sideLength;//555-355
//		//垂直
//		double x3 = sideLength/2;
//		double y3 = 0;
//		double x4 = sideLength/2;
//		double y4 = sideLength;
//		
//		//歪的水平線
//		for(int i = 0; i < 160; i+=10) {
//			for(int j = 0; j< 48*2; j+=6) {
//				newImage = new Mat(newSize, CvType.CV_8UC3, colorWhite);
//				drawVerticalLines(x1, y1 + i, x2, y2 - i, newImage);//歪的水平線
//				drawVerticalLines(x3-48+j, y3, x4+ 48-j, y4, newImage);
////				highGUIshow(newImage);
//				Imgcodecs.imwrite(path0 + questionNo +"Draw0_"+ + ++counter0 +".png", newImage);
//			}
//		}

//		//控制角度在20度外排列組合
//		//水平
//		double x1 = 0;
//		double y1 = sideLength/2;
//		double x2 = sideLength;
//		double y2 = sideLength/2;
//		//垂直
//		double x3 = 0;
//		double y3 = 0;
//		double x4 = sideLength;
//		double y4 = sideLength;
//		
//		//歪的垂直線
//		for(int i = 0; i < 48*2; i+=10) {
//			for(int j = 0; j< 160; j+=20) {
//				newImage = new Mat(newSize, CvType.CV_8UC3, colorWhite);
//				drawVerticalLines(x1, y1-48+i, x2, y2+ 48-i, newImage);
//				drawVerticalLines(x3 + j, y3 , x4- j, y4 , newImage);//歪的垂直線
////				highGUIshow(newImage);
//				Imgcodecs.imwrite(path0 + questionNo +"Draw0_"+ + ++counter0 +".png", newImage);
//			}
//		}

		// 畫十字 //中心座標當00 = 每個座標作後+correction00
//		//中心
		double x0 = sidelength / 2;
		double y0 = sidelength / 2;
		double x1 = x0 - 70;// - 0
		double y1 = y0;
		// 上
		double x2 = x0;
		double y2 = y0 - 70;// 0
		// 右
		double x3 = x0 + 70;// 555
		double y3 = y0;
		double x4 = x0;
		double y4 = y0 + 70;// 555
		// 左
		for (int i = 0; i < sidelength / 2; i += 50) {
			for (int j = 0; j < sidelength / 2; j += 50) {
				newImage = new Mat(newSize, CvType.CV_8UC3, colorWhite);
				drawVerticalLines(x0, y0, x1 - i, y1, newImage);
				drawVerticalLines(x0, y0, x2, y2 - j, newImage);
				drawVerticalLines(x0, y0, x3 + i, y3, newImage);
				drawVerticalLines(x0, y0, x4, y4 + j, newImage);
				highGUIshow(newImage);
//					Imgcodecs.imwrite(path1 + questionNo+"Draw1_" + ++counter1 +".png", newImage);
			}
		}

		// 沒接起來畫十字 //中心座標當00 = 每個座標作後+correction00
		// 中心
//		double x0 = sidelength/2-20;
//		double y0 = sidelength/2+80;
//		//左
//		double x1 = x0 -100;//- 0
//		double y1 = y0 ;
//		//上
//		double x2 = x0;
//		double y2 = y0 - 100;//0
//		//右
//		double x3 = x0 + 100;// 555
//		double y3 = y0;
//		double x4 = x0 ;
//		double y4 = y0 + 100;// 555
//		
//		for(int i = 0; i < sidelength/2-100; i += 50) {
//			for(int j = 0; j < sidelength/2-100; j += 50) {
//				newImage = new Mat(newSize, CvType.CV_8UC3, colorWhite);
////				drawVerticalLines(x0, y0, x1-i, y1, newImage);
//				drawVerticalLines(x0, y0, x2, y2-j, newImage);
//				drawVerticalLines(x0+10, y0, x3+i, y3, newImage);
//				drawVerticalLines(x0, y0, x4, y4+j, newImage);
////				highGUIshow(newImage);
//				Imgcodecs.imwrite(path0 + questionNo +"Draw0_"+ + ++counter0 +".png", newImage);
//			}
//		}
//		

		/* 1分 */
		// Imgcodecs.imwrite(path1 + questionNo+"Draw1_" + ++counter1 +".png",
		// newImage);
		/* 0分 */
//		Imgcodecs.imwrite(path0 + questionNo +"Draw0_"+ + ++counter0 +".png", newImage);

	}

	public static void drawNo11() {
		String questionNo = "11";
		path0 = String.format(path0, questionNo);
		path1 = String.format(path1, questionNo);
		long counter0 = getFileCount(path0);
		long counter1 = getFileCount(path1);
		// 畫一串圓

		double x1 = 0;
		double y1 = sidelength;
		double x2 = sidelength;

		// 水平 /*1分*/
		for (double i = 0.4; i < 2.5; i += 0.1) {
			double y2 = sidelength - i * sidelength;
			newImage = new Mat(newSize, CvType.CV_8UC3, colorWhite);
			newImage = drawVerticalLines(x1, y1, x2, y2, newImage);
//			highGUIshow(newImage);
			Imgcodecs.imwrite(path1 + questionNo + "_" + ++counter1 + ".png", newImage);
		}

		// 水平多條線 /*0分*/
//		for(double i = 0.4; i < 2.5; i +=0.1) {
//			double y2 = sidelength - i * sidelength;
//			newImage = new Mat(newSize, CvType.CV_8UC3, colorWhite);
//			newImage = drawVerticalLines(x1, y1, x2, y2, newImage);
//			newImage = drawVerticalLines(x1, y1 + 20, x2, y2 + 20, newImage);
//			newImage = drawVerticalLines(x1, y1 + 40, x2, y2 + 40, newImage);
////			highGUIshow(newImage);
//			Imgcodecs.imwrite(path0 + questionNo + ++counter0 +".png", newImage);
//		}

//		//水平 太水平	/*0分*/
//		for(double i = 0.01; i < 0.3 ; i +=0.02) {
//			double y2 = sidelength - i * sidelength;
//			newImage = new Mat(newSize, CvType.CV_8UC3, colorWhite);
//			newImage = drawVerticalLines(x1, y1, x2, y2, newImage);
////			highGUIshow(newImage);
//			Imgcodecs.imwrite(path0 + questionNo + "_0_" + ++counter0 +".png", newImage);
//		}

//		//水平 太垂直	/*0分*/
//		for(double i = 4.7; i < 40 ; i +=1) {
//			double y2 = sidelength - i * sidelength;
//			newImage = new Mat(newSize, CvType.CV_8UC3, colorWhite);
//			newImage = drawVerticalLines(x1, y1, x2, y2, newImage);
////			highGUIshow(newImage);
//			Imgcodecs.imwrite(path0 + questionNo + "_0_" + ++counter0 +".png", newImage);
//		}

		/* 1分 */
//		Imgcodecs.imwrite(path1 + questionNo + "_1_" + ++counter1 +".png", newImage);
		/* 0分 */
//		Imgcodecs.imwrite(path0 + questionNo + "_0_" + ++counter0 +".png", newImage);

	}

	public static void drawNo12() {
		String questionNo = "12";
		path0 = String.format(path0, questionNo);
		path1 = String.format(path1, questionNo);
		long counter0 = getFileCount(path0);
		long counter1 = getFileCount(path1);
		newImage = new Mat(newSize, CvType.CV_8UC3, colorWhite);
		// 定義圓角矩形的左上角和右下角座標
		Point topLeft = new Point(100, 100);
		Point bottomRight = new Point(500, 400);

		// 定義圓角的弧度大小
		int radius = 30;

		// 繪製圓角矩形
		Imgproc.rectangle(newImage, topLeft, bottomRight, new Scalar(0, 255, 0), 2);
		Imgproc.ellipse(newImage, new Point(topLeft.x + radius, topLeft.y + radius), new Size(radius, radius), 180, 0,
				90, new Scalar(0, 255, 0), 2);
		Imgproc.ellipse(newImage, new Point(bottomRight.x - radius, topLeft.y + radius), new Size(radius, radius), 270,
				0, 90, new Scalar(0, 255, 0), 2);
		Imgproc.ellipse(newImage, new Point(bottomRight.x - radius, bottomRight.y - radius), new Size(radius, radius),
				0, 0, 90, new Scalar(0, 255, 0), 2);
		Imgproc.ellipse(newImage, new Point(topLeft.x + radius, bottomRight.y - radius), new Size(radius, radius), 90,
				0, 90, new Scalar(0, 255, 0), 2);

		// 顯示結果
//        Imgcodecs.imwrite("rounded_rectangle.jpg", newImage);
		highGUIshow(newImage);

		/* 1分 */
//		Imgcodecs.imwrite(path1 + questionNo + "_1_" + ++counter1 +".png", newImage);
		/* 0分 */
//		Imgcodecs.imwrite(path0 + questionNo + "_0_" + ++counter0 +".png", newImage);
	}

	public static void draw14() {
		String questionNo = "14";
		path0 = String.format(path0, questionNo);
		path1 = String.format(path1, questionNo);
		long counter0 = getFileCount(path0);
		long counter1 = getFileCount(path1);
		newImage = new Mat(newSize, CvType.CV_8UC3, colorWhite);

		double x1 = 0;
		double y1 = sidelength;
		double x2 = sidelength;

		double x3 = sidelength;
		double y3 = sidelength;
		double x4 = 0;

		// 控制角度在20~70度內排列組合
//		for(double i = 0; i + 0.4 < 2.5; i += 0.05) {
////			for(int j = 0; j< 48*2; j+=6) {
//				double y2 = sidelength-sidelength*(0.4+i);
//				double y4 = sidelength-sidelength*(0.4+i);;
//				newImage = new Mat(newSize, CvType.CV_8UC3, colorWhite);
//				drawVerticalLines(x1, y1, x2, y2, newImage);//右斜
//				drawVerticalLines(x3, y3, x4, y4, newImage);//左斜
////				Imgcodecs.imwrite(path1 + questionNo+"Draw1_" + ++counter1 +".png", newImage);
//				if(y2 < 0) {
//					break;
//				}
////			}
////			highGUIshow(newImage);
//			/*1分*/
////			Imgcodecs.imwrite(path1 + questionNo + "_1_" + ++counter1 +".png", newImage);
//			/*0分*/
////			Imgcodecs.imwrite(path0 + questionNo + "_0_" + ++counter0 +".png", newImage);
//		}

		// 控制角度在0-20度內排列組合
		for (double i = 0; i + 0.1 < 0.30; i += 0.01) {
//			for(int j = 0; j< 48*2; j+=6) {
			double y2 = sidelength - sidelength * (0.1 + i);
			double y4 = sidelength - sidelength * (0.1 + i);
			;
			newImage = new Mat(newSize, CvType.CV_8UC3, colorWhite);
			drawVerticalLines(x1, y1, x2, y2, newImage);// 右斜
			drawVerticalLines(x3, y3, x4, y4, newImage);// 左斜
//				Imgcodecs.imwrite(path1 + questionNo+"Draw1_" + ++counter1 +".png", newImage);
			if (y2 < 0) {
				break;
			}
//			}
//			highGUIshow(newImage);
			/* 1分 */
//			Imgcodecs.imwrite(path1 + questionNo + "_1_" + ++counter1 +".png", newImage);
			/* 0分 */
			Imgcodecs.imwrite(path0 + questionNo + "_0_" + ++counter0 + ".png", newImage);

		}

		/* 1分 */
//		Imgcodecs.imwrite(path1 + questionNo + "_1_" + ++counter1 +".png", newImage);
		/* 0分 */
//		Imgcodecs.imwrite(path0 + questionNo + "_0_" + ++counter0 +".png", newImage);

	}

	public static void draw15() {
		String questionNo = "15";
		path0 = String.format(path0, questionNo);
		path1 = String.format(path1, questionNo);
		long counter0 = getFileCount(path0);
		long counter1 = getFileCount(path1);
		newImage = new Mat(newSize, CvType.CV_8UC3, colorWhite);

		double x1 = 50;
		double y1 = sidelength - 10.0;
		double x2 = sidelength - 50;
		double y2 = sidelength - 10.0;
		double x3 = sidelength * 2 / 5;
		double y3 = 10.0;
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 5; j++) {
//				for(int k = 1; k < 5; k++) {
				newImage = new Mat(newSize, CvType.CV_8UC3, colorWhite);
				drawTriangle(newImage, x1, y1 - 4 * 40, x2, y2, x3 + 70 * j, y3 + 65 * i);
//					highGUIshow(newImage);
				Imgcodecs.imwrite(path1 + questionNo + "_1_" + ++counter1 + ".png", newImage);
//				}
			}
		}

		/* 1分 */
//		Imgcodecs.imwrite(path1 + questionNo + "_1_" + ++counter1 +".png", newImage);
		/* 0分 */
//		Imgcodecs.imwrite(path0 + questionNo + "_0_" + ++counter0 +".png", newImage);		
	}

	public static void draw16() {
		String questionNo = "16";
		path0 = String.format(path0, questionNo);
		path1 = String.format(path1, questionNo);
		long counter0 = getFileCount(path0);
		long counter1 = getFileCount(path1);
		newImage = new Mat(newSize, CvType.CV_8UC3, colorWhite);

		// 畫出固定方形
		double linex1 = 10;// r變大 用10
		double liney1 = 10;// r變大 用10
		double linex2 = linex1;
		double liney2 = liney1 + sidelength * 2 / 7;
		double linex3 = linex2 + sidelength * 2 / 7;
		double liney3 = liney2;
		double linex4 = linex1 + sidelength * 2 / 7;
		double liney4 = liney1;
//        drawVerticalLines(linex1, liney1, linex2, liney2, newImage);
//        drawVerticalLines(linex2, liney2, linex3, liney3, newImage);
//        drawVerticalLines(linex3, liney3, linex4, liney4, newImage);

//        //畫圓形 1 分
//        double squareSide = liney2 - liney1;
//        double circlex = linex3;
//        double circley = liney3;
//        for(int i = 0; i < 20; i++) {
//        	circlex += 10 ;
//        	circley = liney3 + 10;
//        	for(int k = 0; k < 20; k++) {
////        		System.out.println("circlex="+ circlex+ " circley= " + circley);
//            	int radius = (int)(Math.sqrt(Math.pow(circlex - linex3, 2) + Math.pow(circley - liney3, 2)));
//            	
//	        	//rule4
//	        	if(circlex > linex3 && circley > liney3 && (squareSide * 2 > radius * 2 && radius * 2 > squareSide / 2 + 5)) {
//	        		double diff = - 0.012;
//	        		for(int j = 0; j < 3 ; j++) { //rule4 微調和方形距離
//	        			System.out.println("radius = "+radius + " diff = "+ diff);
//	            		newImage = new Mat(newSize, CvType.CV_8UC3, colorWhite); //畫正方形
//	                    drawVerticalLines(linex1, liney1, linex2, liney2, newImage);
//	                    drawVerticalLines(linex2, liney2, linex3, liney3, newImage);
//	                    drawVerticalLines(linex3, liney3, linex4, liney4, newImage);
//	        			drawCircle(newImage, circlex, circley, (int)(radius + sidelength * diff)); //畫圓形
//	        			highGUIshow(newImage);
////	        			Imgcodecs.imwrite(path1 + questionNo + "_1_" + ++counter1 +".png", newImage);
//	        			diff += 0.012;
//	        		}
//	        	}
//	        	circley += 10 ;
//        	}
//        }

		// 畫圓形 0 分 比例不對
		double squareSide = liney2 - liney1; // 158
		double circlex = linex3;
		double circley = liney3;
		int radius = 40;
		for (int i = 0; i < 10; i++) {

//			for (int k = 0; k < 4; k++) { // for 控circlex
				// 2L>R>L/2
				if (squareSide < 4 * radius && radius < squareSide) {
					// 固定R的情況下 AO距離過遠
					double moreThan0025 = 0.04;
					double lessThan0025 = 0.028;
					double ratio = 0.9;
//					for (int m = 0; m < 2; m++) {// 控制兩近兩遠
//						double aoFar = radius + sidelength * moreThan0025;
//						circlex = -20*k + 10 + sidelength * 2 / 7 + (aoFar * ratio);
//						circley =  10 + sidelength * 2 / 7 + Math.sqrt(Math.abs(Math.pow(aoFar, 2) - Math.pow((circlex - linex3), 2)));
//
//						newImage = new Mat(newSize, CvType.CV_8UC3, colorWhite); // 畫正方形
//						drawVerticalLines(linex1, liney1, linex2, liney2, newImage);
//						drawVerticalLines(linex2, liney2, linex3, liney3, newImage);
//						drawVerticalLines(linex3, liney3, linex4, liney4, newImage);
//						drawCircle(newImage, circlex, circley, (int) (radius)); // 畫圓形
//						System.out.println("too Far " + moreThan0025);
////						highGUIshow(newImage);
//			Imgcodecs.imwrite(path0 + questionNo + "_0_" + ++counter0 +".png", newImage);
//						moreThan0025 *= 3;

						// 固定R的情況下 AO距離過近
//						double aoNear = radius - sidelength * lessThan0025;
//						circlex = -10*k + 10 + sidelength * 2 / 7 + (aoNear * ratio);
//						circley = 10 + sidelength * 2 / 7 + Math.sqrt(Math.abs(Math.pow(aoNear, 2) - Math.pow((circlex - linex3), 2)));
//
//						newImage = new Mat(newSize, CvType.CV_8UC3, colorWhite); // 畫正方形
//						drawVerticalLines(linex1, liney1, linex2, liney2, newImage);
//						drawVerticalLines(linex2, liney2, linex3, liney3, newImage);
//						drawVerticalLines(linex3, liney3, linex4, liney4, newImage);
//						drawCircle(newImage, circlex, circley, (int) (radius)); // 畫圓形
//						System.out.println("too near " + lessThan0025);
////						highGUIshow(newImage);
//						Imgcodecs.imwrite(path0 + questionNo + "_0_" + ++counter0 +".png", newImage);
//						lessThan0025 *= 3;
//					}
//				ratio -= 0.2;

//				} 
//				else if (squareSide > 4 * radius || radius > squareSide) { // 因為半徑太大或太小所以距離剛好也0分
//					double diff = -0.012;
					circlex = 10 + sidelength * 2 / 7 + radius ;
					int count = 0;
					for(int jj = 0; jj < 4; jj++) {
						for (int j = 0; j < 8; j++) { // 微調和方形合格距離
							if(jj>0) {
								circley =  10 + sidelength * 2 / 7 + radius ;
								newImage = new Mat(newSize, CvType.CV_8UC3, colorWhite); // 畫正方形
								drawVerticalLines(linex1, liney1, linex2, liney2, newImage);
								drawVerticalLines(linex2, liney2, linex3, liney3, newImage);
								drawVerticalLines(linex3, liney3, linex4, liney4, newImage);
								drawCircle(newImage, circlex , circley, (int) (radius )); // 畫圓形
//								highGUIshow(newImage);
								if(circley >  liney3) 
									circley = liney3;
								Imgcodecs.imwrite(path0 + questionNo + "_0_" + ++counter0 + ".png", newImage);
								break;
							} else {
								circley =   radius + 20 * j;
								if(circley >  liney3) 
									circley = liney3;
							}
							
//						circley = 10 + sidelength * 2 / 7 + Math.sqrt(Math.abs(Math.pow(radius, 2) - Math.pow((circlex - linex3), 2)));
							newImage = new Mat(newSize, CvType.CV_8UC3, colorWhite); // 畫正方形
							drawVerticalLines(linex1, liney1, linex2, liney2, newImage);
							drawVerticalLines(linex2, liney2, linex3, liney3, newImage);
							drawVerticalLines(linex3, liney3, linex4, liney4, newImage);
							drawCircle(newImage, circlex , circley, (int) (radius )); // 畫圓形
//							highGUIshow(newImage);
							Imgcodecs.imwrite(path0 + questionNo + "_0_" + ++counter0 + ".png", newImage);
//						diff += 0.024;
							count++;
						}
						circlex-=radius;
						if(circlex< radius) 
							circlex = radius;
					}
					System.out.println(count);
//				}

			}
			radius += 10;
			System.out.println("radius = " + radius);
			if(radius*2 > 555- 10 + sidelength * 2 / 7)
				break;
		}

//        //畫圓形 0 分
//        double squareSide = liney2 - liney1;
//        double circlex = linex3;
//        double circley = liney3;
//        for(int i = 0; i < 20; i++) {
//        	circlex += 10 ;
//        	circley = liney3 + 10;
//        	for(int k = 0; k < 20; k++) {
////        		System.out.println("circlex="+ circlex+ " circley= " + circley);
//            	int radius = (int)(Math.sqrt(Math.pow(circlex - linex3, 2) + Math.pow(circley - liney3, 2)));
//            	
//	        	//rule4
//	        	if(circlex > linex3 && circley > liney3 && (squareSide * 2 > radius * 2 && radius * 2 > squareSide / 2 + 5)) {
//	        		for(int j = 0; j < 3 ; j++) { //rule4 微調和方形距離
//	        			double diff = - 0.060;
//	        			System.out.println("radius = "+radius + " diff = "+ diff);
//	            		newImage = new Mat(newSize, CvType.CV_8UC3, colorWhite); //畫正方形
//	                    drawVerticalLines(linex1, liney1, linex2, liney2, newImage);
//	                    drawVerticalLines(linex2, liney2, linex3, liney3, newImage);
//	                    drawVerticalLines(linex3, liney3, linex4, liney4, newImage);
//	        			drawCircle(newImage, circlex, circley, (int)(radius + sidelength * diff)); //畫圓形
//	        			highGUIshow(newImage);
////	        			Imgcodecs.imwrite(path0 + questionNo + "_0_" + ++counter0 +".png", newImage);
//	        			
//	        			diff = - 0.040;
//	        			newImage = new Mat(newSize, CvType.CV_8UC3, colorWhite); //畫正方形
//	                    drawVerticalLines(linex1, liney1, linex2, liney2, newImage);
//	                    drawVerticalLines(linex2, liney2, linex3, liney3, newImage);
//	                    drawVerticalLines(linex3, liney3, linex4, liney4, newImage);
//	        			drawCircle(newImage, circlex, circley, (int)(radius + sidelength * diff)); //畫圓形
//	        			highGUIshow(newImage);
////	        			Imgcodecs.imwrite(path0 + questionNo + "_0_" + ++counter0 +".png", newImage);
//	        			
//	        			diff = 0.030;
//	        			newImage = new Mat(newSize, CvType.CV_8UC3, colorWhite); //畫正方形
//	                    drawVerticalLines(linex1, liney1, linex2, liney2, newImage);
//	                    drawVerticalLines(linex2, liney2, linex3, liney3, newImage);
//	                    drawVerticalLines(linex3, liney3, linex4, liney4, newImage);
//	        			drawCircle(newImage, circlex, circley, (int)(radius + sidelength * diff)); //畫圓形
//	        			highGUIshow(newImage);
////	        			Imgcodecs.imwrite(path0 + questionNo + "_0_" + ++counter0 +".png", newImage);
//	        			
//	        			diff = 0.060;
//	        			newImage = new Mat(newSize, CvType.CV_8UC3, colorWhite); //畫正方形
//	                    drawVerticalLines(linex1, liney1, linex2, liney2, newImage);
//	                    drawVerticalLines(linex2, liney2, linex3, liney3, newImage);
//	                    drawVerticalLines(linex3, liney3, linex4, liney4, newImage);
//	        			drawCircle(newImage, circlex, circley, (int)(radius + sidelength * diff)); //畫圓形
//	        			highGUIshow(newImage);
////	        			Imgcodecs.imwrite(path0 + questionNo + "_0_" + ++counter0 +".png", newImage);
//	        		}
//	        	}
//	        	circley += 10 ;
//        	}
//        }

		/* 1分 */
//		Imgcodecs.imwrite(path1 + questionNo + "_1_" + ++counter1 +".png", newImage);
		/* 0分 */
//		Imgcodecs.imwrite(path0 + questionNo + "_0_" + ++counter0 +".png", newImage);

	}

	public static void drawTemplate() {
		String questionNo = "XX";
		path0 = String.format(path0, questionNo);
		path1 = String.format(path1, questionNo);
		long counter0 = getFileCount(path0);
		long counter1 = getFileCount(path1);
		newImage = new Mat(newSize, CvType.CV_8UC3, colorWhite);

//		highGUIshow(newImage);

		/* 1分 */
//		Imgcodecs.imwrite(path1 + questionNo + "_1_" + ++counter1 +".png", newImage);
		/* 0分 */
//		Imgcodecs.imwrite(path0 + questionNo + "_0_" + ++counter0 +".png", newImage);
	}

	// 畫直線
	public static Mat drawVerticalLines(double x1, double y1, double x2, double y2, Mat matToDraw) {
		// 定義起點終點座標
		Point start = new Point(x1, y1);
		Point end = new Point(x2, y2);
		int thickness = 2; // 线段的粗细
		Imgproc.line(matToDraw, start, end, colorBlack, thickness);
		return matToDraw;
	}

	// 畫圓圈 // 圆心 x 坐标 // 圆心 y 坐标 // 圆的半径
	public static Mat drawCircle(Mat matToDraw, int centerX, int centerY, int radius) {

		int thickness = 2; // 圆的线条粗细
		Imgproc.circle(matToDraw, new Point(centerX, centerY), radius, colorBlack, thickness);
//		Imgproc.circle(matToDraw, new Point(centerX, centerY), radius, colorWhite, -1);
//		highGUIshow(matToDraw);
		return matToDraw;
	}

	// 畫圓圈 // 圆心 x 坐标 // 圆心 y 坐标 // 圆的半径
	public static Mat drawCircle(Mat matToDraw, double centerX, double centerY, int radius) {

		int thickness = 2; // 圆的线条粗细
		Imgproc.circle(matToDraw, new Point(centerX, centerY), radius, colorBlack, thickness);
//		Imgproc.circle(matToDraw, new Point(centerX, centerY), radius, colorWhite, -1);
//		highGUIshow(matToDraw);
		return matToDraw;
	}

	// 畫三角形
	public static Mat drawTriangle(Mat matToDraw, double x1, double y1, double x2, double y2, double x3, double y3) {

		Point pt1 = new Point(x1, y1);
		Point pt2 = new Point(x2, y2);
		Point pt3 = new Point(x3, y3);

		int thickness = 2; // 定义线条宽度
		Imgproc.line(matToDraw, pt1, pt2, colorBlack, thickness); // 画出三角形的第一条边
		Imgproc.line(matToDraw, pt2, pt3, colorBlack, thickness); // 画出三角形的第二条边
		Imgproc.line(matToDraw, pt3, pt1, colorBlack, thickness); // 画出三角形的第三条边

		return matToDraw;
	}

	// 顯示在畫面上
	public static void highGUIshow(Mat matToShow) {
		HighGui.imshow("Image", matToShow);
		HighGui.waitKey(0);
		HighGui.destroyAllWindows();
	}

}
