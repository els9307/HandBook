package com.handbook.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.UUID;

import org.springframework.util.FileCopyUtils;

import com.handbook.vo.S_ImgPathName;

public class UploadFileUtils {

	static final int THUMB_WIDTH = 300;
	static final int THUMB_HEIGHT = 300;

	//실제파일저장
	

	//파일저장
	public static S_ImgPathName subFileUpload(String imgPath,byte[] fileData,S_ImgPathName img) throws IOException {
		//임시폴더 저장 경로
		File SubName = new File(imgPath,img.getSubName());
			System.out.println(SubName);
			//파일저장
			FileCopyUtils.copy(fileData, SubName);
			
			
			if(SubName.exists()) {//파일이 존재하면
				System.out.println("파일존재");
				File copyTest = new File("C:\\Users\\els78\\Documents\\HandBook\\HandBook\\src\\main\\webapp\\resources\\imgUpload\\2020\\05\\26\\s\\real");
				System.out.println("이 경로는 ?" + copyTest);
				//real 폴더 생성
				if(!copyTest.exists()) copyTest.mkdirs();
				File a = new File (copyTest,img.getRealName());
				copyFile(SubName,a);
			}
		System.out.println("RealName의 경로" + img.getRealName());
		
	      return img;
	}
	//파일 move
	//s폴더(임시폴더에 있는 파일을 ) 본 폴더로 옮기는 작업
	public static void copyFile(File SubName,File a) throws IOException {
	
			System.out.println("copyFile 진입");
			System.out.println(SubName);
			System.out.println(a);
			InputStream inStream = null;
			OutputStream outputStream = null;
			
			try {
				inStream = new FileInputStream(SubName);
				System.out.println("1");
				outputStream = new FileOutputStream(a);
				System.out.println("2");
				byte[] buffer = new byte[1024];
				
				int length;
				
				while((length = inStream.read(buffer)) >0) {
					outputStream.write(buffer,0,length);
				}
				System.out.println("끝");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} finally {
				inStream.close();
				outputStream.close();
			}
	}
	//Get RealName


	//subName, realName을 경로 주소 값 생성
	public static S_ImgPathName  fileUpload( String fileName,String ymdPath)
			throws Exception {
		UUID uid = UUID.randomUUID();
		String newFileName = uid + "_" + fileName;
		S_ImgPathName img = new S_ImgPathName();
		img.setSubName(File.separator + "imgUpload" + ymdPath + "\\s"+ File.separator + newFileName);
		img.setRealName(newFileName);
		
		return img;
	}
	
	public static String calcPath(String uploadPath) {
		Calendar cal = Calendar.getInstance();
		String yearPath = File.separator + cal.get(Calendar.YEAR);
		String monthPath = yearPath + File.separator + new DecimalFormat("00").format(cal.get(Calendar.MONTH) + 1);
		String datePath = monthPath + File.separator + new DecimalFormat("00").format(cal.get(Calendar.DATE));

		makeDir(uploadPath, yearPath, monthPath, datePath);
		makeDir(uploadPath, yearPath, monthPath, datePath + "\\s");

		return datePath;
	}

	private static void makeDir(String uploadPath, String... paths) {

		if (new File(paths[paths.length - 1]).exists()) {
			return;
		}

		for (String path : paths) {
			File dirPath = new File(uploadPath + path);

			if (!dirPath.exists()) {
				dirPath.mkdir();
			}
		}
	}
}