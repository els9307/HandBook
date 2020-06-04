package com.handbook.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.springframework.util.FileCopyUtils;

import com.handbook.vo.S_ImgPathName;

public class UploadFileUtils {

	static final int THUMB_WIDTH = 300;
	static final int THUMB_HEIGHT = 300;
	
	
	// 생성할 디렉토리 주소 받아와서 생성
	// 회원가입시 디렉토리 생성
	public static void mkDir(File subUserDir,File realUserDir) {
		if(!subUserDir.exists() && !realUserDir.exists()) {
			System.out.println("디렉토리 생성");
			subUserDir.mkdirs();
		}
	}
	//

	//임시파일저장
	//subName 실행 
	public static S_ImgPathName subFileUpload(String imgPath,byte[] fileData,S_ImgPathName img) throws IOException {
		//임시폴더 저장 경로
		File SubName = new File(imgPath,img.getSubAddress()+img.getSubName());
		File SubAddress = new File(imgPath,img.getSubAddress());
			if(!SubAddress.exists()) SubAddress.mkdirs();
			// 임시폴더에 파일저장
				FileCopyUtils.copy(fileData, SubName);
	      return img;
	}
	
	//파일 move
	//s폴더(임시폴더에 있는 파일을 ) 본 폴더로 옮기는 작업
	public static void copyFile(File subAddress,File realAddress) throws IOException {
		System.out.println("copyFile 진입");
		System.out.println("업로드 유틸 subAddress = "+ subAddress);
		System.out.println("업로드 유틸 realAddress = " + realAddress);
		// sub 폴더에 있는 파일을 배열로 담음
		File[] target_file = subAddress.listFiles();
		//sub폴더에 있는 파일을 file에 넘겨줌 
		for (File file : target_file) { 
			//real 폴더의 절대경로 및 sub폴더의 파일 이름을 념거줌 
			File temp = new File(realAddress.getAbsoluteFile() + File.separator + file.getName());
			//해당 디렉토리 존재 확인
			if(file.isDirectory()) {
				temp.mkdir();
			} else {
				InputStream inStream = null;
				OutputStream outputStream = null;
				
				try {
					// 바이트 단위로 파일 읽고
					inStream = new FileInputStream(file);
					// 바이트 단위로 파일 출력
					outputStream = new FileOutputStream(temp);
					byte[] buffer = new byte[1024];
					
					int length;
					while((length = inStream.read(buffer)) >0) {
						outputStream.write(buffer,0,length);
					}
					
				}catch (FileNotFoundException e) {
					e.printStackTrace();
				} finally {
					inStream.close();
					outputStream.close();
				}
			}
		}
			System.out.println("파일등록");
		
	}
	public static void subDireCtoryDelete(File folder) {
		while(folder.exists()) {//폴더가 존재할때 
			File[] folder_list = folder.listFiles();
			
			for (int i = 0; i < folder_list.length; i++) {

				System.out.println("파일삭제"+folder_list[i]);
				folder_list[i].delete();
				System.out.println("파일삭제");
			}
			
			if(folder_list.length == 0 && folder.isDirectory()) {
				folder.delete();
				System.out.println("폴더가 삭제");
			}
		}
	}
	
	//Get RealName
	//subName, realName을 경로 주소 값 생성
	public static S_ImgPathName  fileUpload( String fileName,String ymdPath,String UploadFlag,String b_user_id)
			throws Exception {
		UUID uid = UUID.randomUUID();
		String newFileName = uid + "_" + fileName;
		S_ImgPathName img = new S_ImgPathName();
		/*
		 * 	태영수정
		 *  Address - 폴더 경로
		 *  Name 	- UUID생성 , 파일이름
		 *  
		 */
		if(UploadFlag.equals("1")) {
			img.setSubAddress(File.separator + "imgUpload\\"+ b_user_id + "\\board" + ymdPath + "\\s" + "\\sub" + File.separator);
			img.setRealAddress(File.separator + "imgUpload\\"+ b_user_id + "\\board" + ymdPath + "\\s"+  File.separator);
			img.setSubName( newFileName);
			img.setRealName( newFileName );
			System.out.println("유틸" +img.getSubAddress());
			System.out.println(img.getRealAddress());
		}else if(UploadFlag.equals("2")) {
			img.setSubAddress(File.separator + "imgUpload\\" + b_user_id + "\\profile" + "\\sub" + File.separator);
			img.setRealAddress(File.separator + "imgUpload\\" +  b_user_id + "\\profile" + File.separator);
			img.setSubName( newFileName);
			img.setRealName( newFileName );
		}

		return img;
	}
	
	public static String calcPath(String b_user_id,String uploadPath) {
		Calendar cal = Calendar.getInstance();
		String yearPath = File.separator + cal.get(Calendar.YEAR);
		String monthPath = yearPath + File.separator + new DecimalFormat("00").format(cal.get(Calendar.MONTH) + 1);
		String datePath = monthPath + File.separator + new DecimalFormat("00").format(cal.get(Calendar.DATE));

		makeDir(uploadPath,b_user_id + "\\board" + yearPath, monthPath, datePath);
		makeDir(uploadPath,b_user_id + "\\board" + yearPath, monthPath, datePath + "\\s");

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