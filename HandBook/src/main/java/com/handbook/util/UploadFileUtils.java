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

	//������������
	

	//��������
	public static S_ImgPathName subFileUpload(String imgPath,byte[] fileData,S_ImgPathName img) throws IOException {
		//�ӽ����� ���� ���
		File SubName = new File(imgPath,img.getSubAddress()+img.getSubName());
			
			//��������
			FileCopyUtils.copy(fileData, SubName);
			
			if(SubName.exists()) {//������ �����ϸ�
				System.out.println("��������");
				File copyTest = new File(imgPath + img.getRealAddress());
				System.out.println("�� ��δ� ?" + copyTest);
				//real ���� ����
				if(!copyTest.exists()) copyTest.mkdirs();
				File a = new File (copyTest,img.getRealName());
				copyFile(SubName,a);
			}
		System.out.println("RealName�� ���" + img.getRealName());
		
	      return img;
	}
	//���� move
	//s����(�ӽ������� �ִ� ������ ) �� ������ �ű�� �۾�
	public static void copyFile(File SubName,File a) throws IOException {
	
			System.out.println("copyFile ����");
			InputStream inStream = null;
			OutputStream outputStream = null;
			
			try {
				inStream = new FileInputStream(SubName);
				outputStream = new FileOutputStream(a);
				byte[] buffer = new byte[1024];
				
				int length;
				
				while((length = inStream.read(buffer)) >0) {
					outputStream.write(buffer,0,length);
				}
				

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} finally {
				inStream.close();
				outputStream.close();
				
			}
	}
	public static void fileDelete(File Name) {
		System.out.println("����");
		if(Name.exists()) {
			if(Name.delete()) {
				System.out.println("����?");
			}else {
				System.out.println("����");
			}
		}
	}
	//Get RealName


	//subName, realName�� ��� �ּ� �� ����
	public static S_ImgPathName  fileUpload( String fileName,String ymdPath)
			throws Exception {
		UUID uid = UUID.randomUUID();
		String newFileName = uid + "_" + fileName;
		S_ImgPathName img = new S_ImgPathName();
		/*
		 * 	�¿�����
		 *  Address - ���� ���
		 *  Name 	- UUID���� , �����̸�
		 *  
		 */
		img.setSubAddress(File.separator + "imgUpload" + ymdPath + "\\s"+ File.separator );
		img.setRealAddress(File.separator + "imgUpload" + ymdPath + "\\s"+ "\\real" + File.separator);
		img.setSubName( newFileName);
		img.setRealName( newFileName );
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