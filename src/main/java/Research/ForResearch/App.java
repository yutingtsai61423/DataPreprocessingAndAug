/*
 * 20230603 VickyTsai 此為擴增集流程總控制程式  含製作縮小+平移擴增集 分割testingSet validationSet trainingSet
 */
package Research.ForResearch;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class App {

	public final static String ZOOM_OUT = "zoomOutAndPutOnOriginSize";
	public static Mat putOnMat;
	public static Mat zoomOutMat;
	public static Mat zoomedOutImage;
	public static Mat imageMat;
	public static int number;

	public static void main(String[] args) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		//路徑為分類名稱資料夾的上一層
		File classificationParent = new File("D:\\DataSet_VMI\\VMI_DataSet_merge\\16");

		
		/* 擴增看這裡 */
		//1. 資料擴增總控制 資料夾路徑範例D:\DataSet_VMI\VMI_DataSet_merge\10\110000
		//   產生擴增資料夾範例D:\DataSet_VMI\VMI_DataSet_merge\10\augmentation\110000_aug
		//2. 資料切割為正方形 資料路徑範例 D:\DataSet_VMI\VMI_afterClean\11  (包含在內層都資料夾都會切 切完都會在當層生一個cut資料夾)
		//	 產生切割後資料夾範例 D:\DataSet_VMI\VMI_afterClean\11\0\cut 
//		startAugmentAll("", classificationParent);
		
		
		/**/
//		holdOutValidation(classificationParent);//支援直接執行重跑 資料夾要給題號  如 D:\\DataSet_TuBerlin_Sketch\\27
		
		/* 分 trainingSet testingSet 看這裡 */
		kFoldCrossValidationPrepare(classificationParent,4);//數字為輸入要N Fold 且原圖不會特別搬動到trainingSet，所以每個原圖的資料夾都要做augmentation


		
		System.exit(0);
	}
	
	//kFold使用 複製 DrawBymeToTrainingSet  D:\DataSet_VMI\DrawByMe
	public static void copyDrawByMeToTrainingSet(File classificationParent, int k) {
		File drawByMeFile = new File(classificationParent.getParentFile().getParentFile(),"DrawByMeNew" + File.separator + classificationParent.getName());
		
		for(File drawByMeClassificationFile : drawByMeFile.listFiles()) {

			//若 trainingSet 不存在 先建立
			File dest = new File(classificationParent.getAbsolutePath() + File.separator + "augmentation"
									+File.separator + "Fold" + k + File.separator + "trainingSet" + File.separator + drawByMeClassificationFile.getName());
			if(!dest.exists()) {
				dest.mkdirs();
			}
			Arrays.asList(drawByMeClassificationFile.listFiles()).stream()
														.forEach( e -> 
														{
															try {
																Files.copy(e.toPath(),
																		new File(dest, e.getName()).toPath(),
																		StandardCopyOption.REPLACE_EXISTING);
															} catch (IOException e1) {
																e1.printStackTrace();
															}
														});
			System.out.println(String.format("[finish] copy %s files for %s from %s", drawByMeClassificationFile.list().length, drawByMeClassificationFile.getName(), drawByMeFile));
		}
		
	}
	
	//全部copy過去0_aug 1_aug 讓copyDataToTrainingSet() 判斷要撈那些 不撈那些
	public static void copyOriginDataToAug(File classificationParent) {
		for(File classificationFile : classificationParent.listFiles()) {
			if(classificationFile.getName().contains("augmentation")){
				continue;
			}
			//若0_aug 1_aug 不存在 先建立
			File dest = new File(classificationParent.getAbsolutePath()+File.separator+"augmentation"+File.separator+classificationFile.getName()+"_aug");
			if(!dest.exists()) {
				dest.mkdirs();
			}
			Arrays.asList(classificationFile.listFiles()).stream()
														.forEach( e -> 
														{
															try {
																Files.copy(e.toPath(),
																		new File(dest, e.getName()).toPath(),
																		StandardCopyOption.REPLACE_EXISTING);
															} catch (IOException e1) {
																e1.printStackTrace();
															}
														});
			System.out.println(String.format("[finish] copy %s files in %s", classificationFile.list().length, classificationFile.getName()));
		}
	}
	
	//Kfold只有training和testing 循環做k次 不需要ValidationSet (否則會變成nested-KFlod)
	public static void kFoldCrossValidationPrepare(File classificationParent, int kFlod) { // D:\27\
		//適用原圖已經augumentation好的資料 只是因為要分很多折，所以有此程式幫忙
//		int kFlod = 5;
		for(int k = 1; k <= kFlod; k++) {
			// 1. move 1/10 原圖 到 testingSet
			sampleToTestingSet(classificationParent, k);
			// 2. copy _aug資料夾的圖片到 DataToKFold 如果在testingSet有的圖片，就不要從aug搬去TrainingSet 
			copyDataToKFold(classificationParent, k);
		}
		for(int k = 1; k <= kFlod; k++) {
//			// 3. testingSet 複製回原本資料夾
			copyTestingSetBack(classificationParent, k);
			
			//4. 複製 DrawBymeToTrainingSet 到 trainingSet
			copyDrawByMeToTrainingSet(classificationParent,k);
		}
		System.out.println(String.format("[finish] %sFoldValidation Preparation", kFlod));
		
	}
	
	public static void copyDataToTrainingSet(File classificationParent) {
		File augmentationDirFile = new File(classificationParent, "augmentation");
		File[] augDirFiles = augmentationDirFile.listFiles((dir, name) -> name.endsWith("_aug"));
		File trainginSetpath = new File(augmentationDirFile, "\\trainingSet");
		//先清空
		deleteDirectory(trainginSetpath);
		trainginSetpath.mkdirs();
		
		
		//若檔名不在 testingSet 先加入List
		for(File augDirFile :augDirFiles) {			
			List<File> waitToAddToTrainingSet = new ArrayList<>();
			//拿到testingSet/類別裡的檔名
			String[] testingSetNames = new File(augmentationDirFile, 
												String.format("\\testingSet\\%s", augDirFile.getName().substring(0, augDirFile.getName().lastIndexOf("_aug"))))
												.list();
			
			String[] validationSetNames = new File(augmentationDirFile, 
					String.format("\\validationSet\\%s", augDirFile.getName().substring(0, augDirFile.getName().lastIndexOf("_aug"))))
					.list();
			
			String[] testingAndValidationNames= new String[testingSetNames.length+validationSetNames.length];
			System.arraycopy(testingSetNames, 0, testingAndValidationNames, 0, testingSetNames.length);
			System.arraycopy(validationSetNames, 0, testingAndValidationNames, testingSetNames.length, validationSetNames.length);
			
			//若檔名不在 testingSet 先加入waitToAddToTrainingSetList
			File[] augimages = augDirFile.listFiles();
			Arrays.asList(augimages).stream()
								.filter(e -> !findByName(e.getName(),testingAndValidationNames) ) //不是該圖片名稱開頭
								.forEach(e -> waitToAddToTrainingSet.add(e));
			
			System.out.println(waitToAddToTrainingSet.size());
			File classification = new File(trainginSetpath, augDirFile.getName().substring(0, augDirFile.getName().lastIndexOf("_aug")));
			if(!classification.exists()) {
				classification.mkdirs();
			}
			
			//寫入檔案
			waitToAddToTrainingSet.stream()
									.forEach(e->
												{
													try {
														Files.copy(e.toPath(), 
																new File(classification + File.separator + e.getName()).toPath(), StandardCopyOption.REPLACE_EXISTING);
													} catch (IOException e1) {
															e1.printStackTrace();
													}
												}
											);
			System.out.println("[finish] copyDataToTrainingSet" + augDirFile.getName());
		}

	}
	
	public static void copyDataToKFold(File classificationParent, int k) {
		File augmentationDirFile = new File(classificationParent, "augmentation");
		File[] augDirFiles = augmentationDirFile.listFiles((dir, name) -> name.endsWith("_aug"));
		File trainginSetpath = new File(augmentationDirFile, "Fold" + k + "\\trainingSet");
		
		//若檔名不在 testingSet 先加入List  augDirFile=依序取出augmentation\下的OOXX_aug資料夾
		for(File augDirFile :augDirFiles) {			
			List<File> waitToAddToTrainingSet = new ArrayList<>();
			//拿到testingSet/類別裡的檔名
			String[] testingSetNames = new File(augmentationDirFile, 
												String.format("Fold%s\\testingSet\\%s", k, augDirFile.getName().substring(0, augDirFile.getName().lastIndexOf("_aug"))))
												.list();	 //MY(B)241-1_11_F.png, MY(B)382-1_11_F.png....		
		
			//若檔名不在 testingSet 先加入waitToAddToTrainingSetList
			File[] augimages = augDirFile.listFiles(); //D:\DataSet_VMI\VMI_DataSet_merge\11\augmentation\000000_aug\JM(B)050_11_F_z0.80.png
			Arrays.asList(augimages).stream()
								.filter(e -> !findByName(e.getName(),testingSetNames) ) //不是該圖片名稱開頭
								.forEach(e -> waitToAddToTrainingSet.add(e));
			
			File classification = new File(trainginSetpath, augDirFile.getName().substring(0, augDirFile.getName().lastIndexOf("_aug")));//D:\DataSet_VMI\VMI_DataSet_merge\11\augmentation\Fold1\trainingSet\000000
			deleteDirectory(classification);
			if(!classification.exists()) {
				classification.mkdirs();
			}
			
			//寫入檔案
			waitToAddToTrainingSet.stream()
									.forEach(e->
												{
													try {
														Files.copy(e.toPath(), 
																new File(classification + File.separator + e.getName()).toPath(), StandardCopyOption.REPLACE_EXISTING);
													} catch (IOException e1) {
															e1.printStackTrace();
													}
												}
											);
			
			
			System.out.println("[finish] copyDataToFold" + k + " " + augDirFile.getName() +" fileSize= " +waitToAddToTrainingSet.size());
		}

	}
	
	public static boolean findByName(String augName, String[] testingSetNames) {
		for(String testingSetname : testingSetNames) {
			int lastWordIndex = testingSetname.lastIndexOf(".");
			if(augName.startsWith(testingSetname.substring(0, lastWordIndex)))
				return true;
		}
		return false;
	}
	//holdOutValidation 保留 testingSet validationSet 未 aug 含分割前shuffle(隨機) 按照類別比類分割(分層抽樣)
	public static void holdOutValidation(File classificationParent) {

			//holdOutValidation augmentation要先做好
			
			copyOriginDataToAug(classificationParent);

			sampleToTestingSet(classificationParent,"testingSet"); //  random 1/10分割為training //先在VMI原稿資料夾產生
			sampleToTestingSet(classificationParent,"validationSet");// random 1/10分割為validation //先在VMI原稿資料夾產生
			
			copyDataToTrainingSet(classificationParent);
			
			copyTestingSetBack(classificationParent, "testingSet"); // testingSet 複製回原本資料夾
			copyTestingSetBack(classificationParent, "validationSet"); // validationSet 複製回原本資料夾
			
	}	
	
	//holdOutValidation 用
	public static void sampleToTestingSet(File classificationParent, String setName) { // D:\27
		File[] classificationFiles = classificationParent.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return !"augmentation".equals(name);
			}
		});

		File testFile = new File(classificationParent + File.separator + "augmentation" + File.separator+ setName);
		deleteDirectory(testFile);
		for(File dir : classificationFiles) {
			new File(testFile, dir.getName()).mkdirs();
		}
		
		for(File classificationFile : classificationFiles) {
			List<File> imageList = Arrays.asList(classificationFile.listFiles());
			Collections.shuffle(imageList);
			for(int i = 0; i < imageList.size() * 0.1 ; i++) {
				try {
					Files.move(imageList.get(i).toPath()
							,new File(testFile.getAbsolutePath() + File.separator + imageList.get(i).getParentFile().getName() + File.separator + imageList.get(i).getName()).toPath()
							, StandardCopyOption.REPLACE_EXISTING);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			System.out.println("[sample To " + setName + " finish] "+ classificationFile);
		}
	}
	
	public static void deleteDirectory(File file) {
		 if (!file.exists()) {
	            return;
	        }
	        if (file.isFile()) {
	        	file.delete();
	            return;
	        }
	        File[] files = file.listFiles();
	        for (int i = 0; i < files.length; i++) {
	        	deleteDirectory(files[i]);
	        }
	        file.delete();
	        System.out.println("[delete ]" + file + ": " + !file.exists());
	}
	//KfoldBalidation 用
	public static void sampleToTestingSet(File classificationParent, int k) { // D:\27
		File[] classificationFiles = classificationParent.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return !"augmentation".equals(name);
			}
		});

		File testFile = new File(classificationParent + File.separator + "augmentation" + File.separator + "Fold" + k + File.separator+ "testingSet");
		deleteDirectory(testFile);
		for(File dir : classificationFiles) {
			new File(testFile, dir.getName()).mkdirs();
		}
		
		for(File classificationFile : classificationFiles) {
			List<File> imageList = Arrays.asList(classificationFile.listFiles());
			Collections.shuffle(imageList);
			for(int i = 0; i < imageList.size() * 0.1 ; i++) {
				try {
					Files.move(imageList.get(i).toPath()
							,new File(testFile.getAbsolutePath() + File.separator + imageList.get(i).getParentFile().getName() + File.separator + imageList.get(i).getName()).toPath()
							, StandardCopyOption.REPLACE_EXISTING);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			System.out.println("[sample To TestingSet finish] "+ classificationFile);
		}
	}

	// 2. D:\DataSet_VMI\VMI_DataSet_merge\10\110000
	public static void startAugmentAll(String argumentationType, File parentDirFile) {

		// 讀取大資料夾中的檔案名稱
		// 找出裡面有哪些圖案資料夾
		File[] childrenDirs = parentDirFile.listFiles();

		for (File imageFile : childrenDirs) {
			if (imageFile.isDirectory() && !"augmentation".equals(imageFile.getName())) { // 加入判斷如果不是檔案 就再進去一層
				System.out.println("[get into directory] " + imageFile.getAbsolutePath());
				startAugmentAll(argumentationType, imageFile);
				System.out.println("[finish] augmentation  directory " + imageFile.getName());
			}
			if (imageFile.isFile()) {
				System.out.println("[find a file] do augmentation");
				// do something

				imageMat = Imgcodecs.imread(imageFile.getAbsolutePath());
//				traverseCutA4ToSquare(imageMat, imageFile);//A4切割為正方形  
//				traverseMirror(imageMat, imageFile, 0); //鏡像// = 0 图像向下翻转　　　　
//				traverseMirror(imageMat, imageFile, 1); // > 0 图像向右翻转
//				traverseMirror(imageMat, imageFile, -1); //< 0 图像同时向下向右翻转
//				traverseRotate90(imageMat, imageFile);//旋轉90 1倍
//				traverShear(imageMat, imageFile); //水平剪力 圖片數量4倍
//				traverThin(imageMat, imageFile); //圖片數量變8倍
				traverseRatioForZoomOut(imageMat, imageFile, 10); //縮小不同倍率 平移四個角落 藉由調整ij 決定要產生i*j種平移
//				traverseElevateLeftLowerCorner(imageMat, imageFile); //抬升左下角
				imageMat.release();

			}
		}
		System.out.println("[finish] startAugmentAll");
	}

	// 沒在用了 random split to validationSet & trainingSet
	public static void splitTo(File classificationParent) { // D:\27
		// 判斷資料夾名_aug結尾 會複製其80% 20% 到 27/train/XX 27/valid/XX
		File dirAugmentationFile =  new File(classificationParent,"augmentation");
		if(!dirAugmentationFile.exists()) {
			dirAugmentationFile.mkdirs();
		}
//		File dirAugumentationFiles = classificationParent.listFiles((dir, name) -> "augmentation".equals(name))[0];
		
		for (File dirFile : dirAugmentationFile.listFiles()) { // D:\27\augumentation\0_aug
			if (dirFile.getName().endsWith("_aug")) {
				File[] imageFiles = dirFile.listFiles();// image
				File validaPath = new File(dirAugmentationFile.getAbsolutePath() + File.separator + "validationSet"
									+ File.separator + dirFile.getName().replace("_aug", ""));
				File trainingPath = new File(dirAugmentationFile.getAbsolutePath() + File.separator + "trainingSet"
									+ File.separator + dirFile.getName().replace("_aug", ""));
				// 把split資料夾清空 -> 這樣清不掉
				validaPath.deleteOnExit();
				trainingPath.deleteOnExit();
				validaPath.mkdirs();
				trainingPath.mkdirs();

				// 分類
				List<File> imageFilesList = Arrays.asList(imageFiles);
				Collections.shuffle(imageFilesList);
				// 前20%放valid
				for (int i = 0; i < imageFilesList.size(); i++) {
					try {
						if (i < imageFilesList.size() * 0.2) {
							Files.copy(imageFilesList.get(i).toPath(),new File(validaPath + File.separator + imageFilesList.get(i).getName()).toPath(),StandardCopyOption.REPLACE_EXISTING);
						} else {
							Files.copy(imageFilesList.get(i).toPath(),new File(trainingPath + File.separator + imageFilesList.get(i).getName()).toPath(),StandardCopyOption.REPLACE_EXISTING);
						}

					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				System.out.println("[finish] split to validationSet and trainingSet, dir = " + dirFile.getName());
			}
		}
	}
	


	// 4. holdOutValidation用 testingSet validationSet 複製回原本資料夾
	public static void copyTestingSetBack(File classificationParent, String setName) {
		File[] testingOrValidationSetDir = classificationParent.listFiles((dir, name)-> "augmentation".equals(name))[0]
												.listFiles((dir, name)-> setName.equals(name));
		if(testingOrValidationSetDir.length <=0 ) {
			return;
		}
		
		for(File classicifationFDir : testingOrValidationSetDir[0].listFiles()) {
			String path = classicifationFDir.getParentFile().getParentFile().getParentFile().getAbsolutePath() + File.separator + classicifationFDir.getName();
			for(File image : classicifationFDir.listFiles()) {
				try {
					Files.copy(image.toPath(), new File(path + File.separator + image.getName()).toPath(), StandardCopyOption.REPLACE_EXISTING);
				} catch (IOException e) {
					e.printStackTrace();
					
				}
			}
			System.out.println("[finish] copy " + setName +" back, dir = " + classicifationFDir.getName());
		}
		
	}
	//KfoldValidation 用
	public static void copyTestingSetBack(File classificationParent, int k) {
		File[] testingSetDir = classificationParent.listFiles((dir, name)-> "augmentation".equals(name))[0]
												.listFiles((dir, name)-> ("Fold"+k).equals(name))[0]
												.listFiles((dir, name)-> "testingSet".equals(name));
		if(testingSetDir.length <=0 ) {
			return;
		}
		
		for(File classicifationFDir : testingSetDir[0].listFiles()) {
			String path = classicifationFDir.getParentFile().getParentFile().getParentFile().getParentFile().getAbsolutePath() + File.separator + classicifationFDir.getName();
			for(File image : classicifationFDir.listFiles()) {
				try {
					Files.copy(image.toPath(), new File(path + File.separator + image.getName()).toPath(), StandardCopyOption.REPLACE_EXISTING);
				} catch (IOException e) {
					e.printStackTrace();
					
				}
			}
			System.out.println("[finish] copy testingSet back, dir = " + classicifationFDir.getName());
		}
		
	}
	
	// 工具.復原搬去testingSet 搬去原本資料夾 (testingSet清空)
	public static void moveTestingSetBack(File classificationParent) {
		File[] testingSetDir = classificationParent.listFiles((dir, name)-> "augmentation".equals(name))[0]
												.listFiles((dir, name)-> "testingSet".equals(name));
		if(testingSetDir.length <=0 ) {
			return;
		}
		
		for(File classicifationFDir : testingSetDir[0].listFiles()) {
			String path = classicifationFDir.getParentFile().getParentFile().getParentFile().getAbsolutePath() + File.separator + classicifationFDir.getName();
			for(File image : classicifationFDir.listFiles()) {
				try {
					Files.move(image.toPath(), new File(path + File.separator + image.getName()).toPath(), StandardCopyOption.REPLACE_EXISTING);
				} catch (IOException e) {
					e.printStackTrace();
					
				}
			}
		}
		
	}
	

	private static String generateNewFilePath(String argumentationType, File image) {
		String originalImageFileName = image.getName();
		String arguImageFileName = originalImageFileName.substring(0, originalImageFileName.lastIndexOf(".")) + "_"
				+ argumentationType + originalImageFileName.substring(originalImageFileName.lastIndexOf("."));
		File newArguFile = new File(image.getParentFile().getParentFile() + File.separator + "augmentation"
				+ File.separator + image.getParentFile().getName() + "_aug");
		if (!newArguFile.exists()) {
			newArguFile.mkdirs();
		}
		System.out.println("[writing newArguFile]" + newArguFile );
		return newArguFile.getAbsolutePath() + File.separator + arguImageFileName;
	}

	// 整張縮小
	public static Mat zoomOut(Mat image, double ratio) {
		// 定義縮小比例 ratio
		// 取得圖像大小
		int width = image.width();
		int height = image.height();
		// 計算縮小後的圖像大小
		int newWidth = (int) (width * ratio);
		int newHeight = (int) (height * ratio);
		// 建立目標大小的空白圖像
		/*Mat*/ zoomedOutImage = new Mat();
		// 執行圖像縮小
		Imgproc.resize(image, zoomedOutImage, new Size(newWidth, newHeight));
//		image.release();
		return zoomedOutImage;
	}

	// 把縮小的話在原大小本畫布上 用startX(起始X座標 左上角起算) startY(起始Y座標 左上角起算) 控制在大畫布的哪個位置放變形圖
	public static Mat zoomOutAndPutOnOriginSize(Mat image, double ratio, int startX, int startY) {
		// 變形過的圖案
		/*Mat*/ zoomOutMat = zoomOut(image, ratio);
		// 空白的畫布
		/*Mat*/ putOnMat = new Mat(image.size(), CvType.CV_8UC3, new Scalar(255, 255, 255));

		// 指定chooseRegion要框起的局部區域

		// 限制xy最大最小值 超過邊界則換成容許範圍最大值
		if (startX > putOnMat.cols() - zoomOutMat.cols()) {
			startX = putOnMat.cols() - zoomOutMat.cols();
		}

		if (startY > putOnMat.rows() - zoomOutMat.rows()) {
			startY = putOnMat.rows() - zoomOutMat.rows();
		}
		int width = zoomOutMat.cols(); // 變形圖寬度
		int height = zoomOutMat.rows(); // 變形圖高度

		Rect chooseRegion = new Rect(startX, startY, width, height);// 想擷取的範圍&位置 類似選取範圍,遮色片的概念
		zoomOutMat.copyTo(putOnMat.submat(chooseRegion)); // 把變形圖貼在大圖的指定範圍chooseRegion中

//		image.release();
		zoomOutMat.release();
		return putOnMat;
	}

	// 給縮小倍率範圍 + 自動平移產圖
	public static void traverseRatioForZoomOut(Mat image, File imageFile, int minRatio) {
		//0.minRatio 倍做一次自動平移(平疑似個角落變四張圖)  每次0.minRatio+0.3倍 做一次停止
		for (int i = minRatio; i <= minRatio; i += 3) {
			traverseZoomOut(image, i * 0.1, imageFile);
		}
	}

	// 固定縮小倍率 自動平移 4個位置  可藉由調整ij 決定要產生i*j種平移
	public static void traverseZoomOut(Mat image, double ratio, File imageFile) {
		int border = (int) (image.cols() * (1 - ratio));
	 
		number = 0;
		int x = 100;
		for (int i = 0; i < 1; i++) {  
			if(x > border) 
				break;
			int y = 110;
			for (int j = 0; j < 2 ; j++) {
				if(y > border) 
					break;
				Mat resultMat = zoomOutAndPutOnOriginSize(image, ratio, x, y);
				Imgcodecs.imwrite(generateNewFilePath("z" + String.format("%.1f", ratio) + number++ , imageFile),resultMat);
//				highGUIshow(resultMat);
				resultMat.release();
				y += -100;
				x += 10;
			}
		}
	}
	
	public static void traverseMirror(Mat image, File imageFile, int mirrorType) {
//		System.out.println("[start] traverseMirror ");
		// = 0 图像向下翻转　　> 0 图像向右翻转　　< 0 图像同时向下向右翻转
		Imgcodecs.imwrite(generateNewFilePath("M-1" + mirrorType + number++ , imageFile), mirror(image, mirrorType));
	}
	
	public static Mat mirror(Mat img, int mirrorType ) {
//		flipCode：　= 0 图像向下翻转　　> 0 图像向右翻转　　< 0 图像同时向下向右翻转
		Mat dst1 = new Mat(img.rows(), img.cols(), CvType.CV_8UC3);
		Core.flip(img, dst1, mirrorType);
//		highGUIshow(dst1);
		return dst1;
	}
	
	//剪力平移 https://www.jb51.net/article/264965.htm
	public static void traverShear(Mat image, File imageFile) {
      double stepHorizontal = 0.01;
      while(stepHorizontal < 0.8) {
    	  Mat resultMat = shear(image, stepHorizontal);    	  
//    	  highGUIshow(resultMat);
  			Imgcodecs.imwrite(generateNewFilePath("s" + String.format("%.1f", stepHorizontal).substring(2) + number++ , imageFile), resultMat);
  			resultMat.release();
  			stepHorizontal+=0.2;
      }
      System.out.println("[finish] traverShear one of image to 4 " );
		
	}
	
	public static Mat shear(Mat src, double stepHorizontal) {
        MatOfPoint2f point1 = new MatOfPoint2f(new Point(0, 0), new Point(0, src.rows()), new Point(src.cols(), 0));
//        double stepHorizontal = 0.01;
        /*for 向左推*/
        MatOfPoint2f point2 = new MatOfPoint2f(
				        						new Point(0, 0), //左上
				        						new Point(src.cols()*stepHorizontal, src.rows()),		//左下
				        						new Point(src.cols()*(1-stepHorizontal), 0)); //右上
//        /*for 變瘦*/
//        MatOfPoint2f point2 = new MatOfPoint2f(
//				new Point(src.cols()*stepHorizontal, 0), //左上
//				new Point(src.cols()*stepHorizontal, src.rows()),		//左下
//				new Point(src.cols(), 0)); //右上
        
        // 获取 放射变换 矩阵
        Mat dst = Imgproc.getAffineTransform(point1, point2);
        // 进行 仿射变换
        Mat image = new Mat();
        Imgproc.warpAffine(src, image, dst, src.size(), Imgproc.INTER_LINEAR, Core.BORDER_CONSTANT, new Scalar(255, 255, 255));//轉完仍是白背景
//        highGUIshow(image);
        return image;
	}
	
	public static void traverseElevateLeftLowerCorner(Mat image, File imageFile) {
		double stepUpward = 0.1;
		for(int i = 0; i < 1; i ++) {
			Mat resultMat = elevateLeftLowerCorner(image, stepUpward);
//	    	  highGUIshow(resultMat);
  			Imgcodecs.imwrite(generateNewFilePath("e" + String.format("%.1f", stepUpward).substring(2) + number++ , imageFile), resultMat);
  			resultMat.release();
  			stepUpward += 0.05;
		}
	}
	
	public static Mat elevateLeftLowerCorner(Mat src, double stepUpward) {
        MatOfPoint2f point1 = new MatOfPoint2f(new Point(0, 0), new Point(0, src.rows()), new Point(src.cols(), src.cols()));
        /*for 左邊向上推*/
        MatOfPoint2f point2 = new MatOfPoint2f(
				        						new Point(0, 0), //左上
				        						new Point(0, src.rows()*(1-stepUpward)),		//左下
				        						new Point(src.cols(), src.cols())); //右下
        
        // 获取 放射变换 矩阵
        Mat dst = Imgproc.getAffineTransform(point1, point2);
        // 进行 仿射变换
        Mat image = new Mat();
//        Imgproc.warpAffine(src, image, dst, src.size(), Imgproc.INTER_LINEAR, Core.BORDER_CONSTANT);//黑背景測試用
        Imgproc.warpAffine(src, image, dst, src.size(), Imgproc.INTER_LINEAR, Core.BORDER_CONSTANT, new Scalar(255, 255, 255));//轉完仍是白背景
//        highGUIshow(image);
        return image;
	}
	
	
	public static void traverThin(Mat image, File imageFile) {
	      double stepHorizontal = 0.1;
//	      while(stepHorizontal < 0.3) {
	    for(int i = 0; i < 1; i++) {
	    	
	    	  Mat resultMat = shear(image, stepHorizontal);    	  
//	    	  highGUIshow(resultMat);
	  			Imgcodecs.imwrite(generateNewFilePath("t" + String.format("%.1f", stepHorizontal) + number++ , imageFile), resultMat);
	  			resultMat.release();
	  			stepHorizontal+=0.1;
	      }
	      System.out.println("[finish] traverThin one of image to "+ 4 );
			
		}
	
	public static Mat thin(Mat src, double stepHorizontal) {
        MatOfPoint2f point1 = new MatOfPoint2f(new Point(0, 0), new Point(0, src.rows()), new Point(src.cols(), 0));
//        double stepHorizontal = 0.01;

        /*for 變瘦*/
        MatOfPoint2f point2 = new MatOfPoint2f(
				new Point(src.cols()*stepHorizontal, 0), //左上
				new Point(src.cols()*stepHorizontal, src.rows()),		//左下
				new Point(src.cols(), 0)); //右上
        
        // 获取 放射变换 矩阵
        Mat dst = Imgproc.getAffineTransform(point1, point2);
        // 进行 仿射变换
        Mat image = new Mat();
        Imgproc.warpAffine(src, image, dst, src.size(), Imgproc.INTER_LINEAR, Core.BORDER_CONSTANT, new Scalar(255, 255, 255));//轉完仍是白背景
//        highGUIshow(image);
        return image;
	}
	
	public static void traverseRotate90(Mat image, File imageFile) {
		Mat resultMat =	 rotate90(image);
		Imgcodecs.imwrite(generateNewFilePath("r" + number++ , imageFile), resultMat);
		resultMat.release();
		System.out.println("[finish] traverseRotate90 count" + number);
	}
	
	public static Mat rotate90(Mat image) {
		// 獲取圖片的寬度和高度
        int width = image.width();
        int height = image.height();
        
        // 建立旋轉後的圖片
        Mat rotatedImage = new Mat(new Size(555, 555), image.type(), new Scalar(255, 255, 255));
        
        // 進行旋轉操作
        Point center = new Point(500 / 2, 500 / 2);
        Mat rotationMatrix = Imgproc.getRotationMatrix2D(center, 90, 1.0);
        Imgproc.warpAffine(image, rotatedImage, rotationMatrix, rotatedImage.size());
//        highGUIshow(rotatedImage);
		return rotatedImage;
	}
	
	public static void traverseCutA4ToSquare(Mat image, File imageFile) {
		//判斷若是A4才執行
		File cutFile = new File(imageFile.getParentFile().getAbsolutePath() + File.separator +"cut");
		
		if(!cutFile.exists()) {
			cutFile.mkdirs();
		}
		
		if((double)image.rows() / image.cols() > 1.2) {
//			highGUIshow(cutA4ToSquare(image));
			System.out.println("[cut] image ratio = " + (double)image.rows() / image.cols());
			Imgcodecs.imwrite(new File(cutFile, imageFile.getName()).getAbsolutePath(), cutA4ToSquare(image));
		} else {
			System.out.println("[skip] image ratio = " + (double)image.rows() / image.cols());
		}
		
		
		
	}
	

	public static Mat cutA4ToSquare(Mat image) {
		Mat squareImage = new Mat();

        // 取得A4圖片的寬度和高度
        int width = image.width();
        int height = image.height();

        // 計算正方形的尺寸
        int size = Math.min(width, height);

        // 計算裁切的起始點
        int startX = (width - size ) / 2;
        int startY = (height - size) / 2;

        // 裁切圖片為正方形
        Rect roi = new Rect(startX, startY, size, size);
        Mat croppedImage = new Mat(image, roi);

        // 調整圖片大小為指定尺寸（可選）
        int resizedSize = 555; // 調整為512x512的尺寸
        Mat resizedImage = new Mat();
        Size newSize = new Size(resizedSize, resizedSize);
        Imgproc.resize(croppedImage, resizedImage, newSize);

        // 將裁切後的正方形圖片複製到新的Mat物件中
        resizedImage.copyTo(squareImage);
//        highGUIshow(squareImage);
        
        return squareImage;
		
	}	
	
	
	public static void highGUIshow(Mat matToShow) {
		HighGui.imshow("Image", matToShow);
//		HighGui.waitKey();
		HighGui.waitKey(0);
		HighGui.destroyAllWindows();
	}

	// 把圖貼在比較大的白圖正中間
//	public static Mat translationPrepare(Mat image, double rate) {
//		Mat bigWhiteMat	 = new Mat(new Size(image.cols()*rate, image.rows()*rate), CvType.CV_8UC3, new Scalar(255, 255, 255));
//		// 指定chooseRegion要框起的局部區域 
//		int startX = (int)(bigWhiteMat.cols()/2.0 - image.cols()/2.0); // 起始X座標 左上角起算
//		int startY = (int)(bigWhiteMat.rows()/2.0 - image.rows()/2.0); // 起始Y座標 左上角起算
//		int width = image.cols(); // 變形圖寬度
//		int height = image.rows(); // 變形圖高度
//		Rect choosenRegion = new Rect(startX, startY, width, height);
//		image.copyTo(bigWhiteMat.submat(choosenRegion));
//		return bigWhiteMat;
//	}

//	//往右下平移-會縮小
//	public static Mat translationLowerRight(Mat image) {
//		double rate = 1.5;
//		Mat bigWhiteMatWithImage = translationPrepare(image, rate);
//		int startX = 0;
//		int startY = 0;
//		int width = (int)(image.cols() * (1 + (rate - 1) / 2));
//		int height = (int)(image.rows() * (1 + (rate - 1) / 2));
//		Rect choosenRegion = new Rect(startX, startY, width, height);
//		
//		Mat outPutMat = new Mat();
//		Imgproc.resize(bigWhiteMatWithImage.submat(choosenRegion), outPutMat, image.size());
//		return outPutMat;
//	}
//	
//	//往左下平移-會縮小
//	public static Mat translationLowerLeft(Mat image) {
//		double rate = 1.8;
//		Mat bigWhiteMatWithImage = translationPrepare(image, rate);
//		int startX = (int)(image.cols() * (rate - 1.0)/2);
//		int startY = 0;
//		int width = (int)(image.cols() * (1 + (rate - 1) / 2));
//		int height = (int)(image.rows() * (1 + (rate - 1) / 2));
//		
//		Rect choosenRegion = new Rect(startX, startY, width, height);
//		Mat outPutMat = new Mat();
//		Imgproc.resize(bigWhiteMatWithImage.submat(choosenRegion), outPutMat, image.size());
//		return outPutMat;
//	}
//	
//	//往左上平移-會縮小
//	public static Mat translationUpperLeft(Mat image) {
//		double rate = 2.0;
//		Mat bigWhiteMatWithImage = translationPrepare(image, rate);
//		int startX = (int)(image.cols() * (rate - 1.0)/2);
//		int startY = (int)(image.cols() *  (rate - 1.0)/2);;
//		int width = (int)(image.cols() * (1 + (rate - 1) / 2));
//		int height = (int)(image.rows() * (1 + (rate - 1) / 2));
//		
//		Rect choosenRegion = new Rect(startX, startY, width, height);
//		Mat outPutMat = new Mat();
//		Imgproc.resize(bigWhiteMatWithImage.submat(choosenRegion), outPutMat, image.size());
//		return outPutMat;
//	}
//	
//	//往右上平移-會縮小
//	public static Mat translationUpperRight(Mat image) {
//		double rate = 2.0;
//		Mat bigWhiteMatWithImage = translationPrepare(image, rate);
//		int startX = (int)(image.cols() * (rate - 1.0)/2);
//		int startY = (int)(image.cols() *  (rate - 1.0)/2);;
//		int width = (int)(image.cols() * (1 + (rate - 1) / 2));
//		int height = (int)(image.rows() * (1 + (rate - 1) / 2));
//		
//		Rect choosenRegion = new Rect(startX, startY, width, height);
//		Mat outPutMat = new Mat();
//		Imgproc.resize(bigWhiteMatWithImage.submat(choosenRegion), outPutMat, image.size());
//		return outPutMat;
//	}

}
