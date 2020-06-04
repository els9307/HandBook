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
	
	
	// ������ ���丮 �ּ� �޾ƿͼ� ����
	// ȸ�����Խ� ���丮 ����
	public static void mkDir(File subUserDir,File realUserDir) {
		if(!subUserDir.exists() && !realUserDir.exists()) {
			System.out.println("���丮 ����");
			subUserDir.mkdirs();
		}
	}
	//

	//�ӽ���������
	//subName ���� 
	public static S_ImgPathName subFileUpload(String imgPath,byte[] fileData,S_ImgPathName img) throws IOException {
		//�ӽ����� ���� ���
		File SubName = new File(imgPath,img.getSubAddress()+img.getSubName());
		File SubAddress = new File(imgPath,img.getSubAddress());
			if(!SubAddress.exists()) SubAddress.mkdirs();
			// �ӽ������� ��������
				FileCopyUtils.copy(fileData, SubName);
	      return img;
	}
	
	//���� move
	//s����(�ӽ������� �ִ� ������ ) �� ������ �ű�� �۾�
	public static void copyFile(File subAddress,File realAddress) throws IOException {
		System.out.println("copyFile ����");
		System.out.println("���ε� ��ƿ subAddress = "+ subAddress);
		System.out.println("���ε� ��ƿ realAddress = " + realAddress);
		// sub ������ �ִ� ������ �迭�� ����
		File[] target_file = subAddress.listFiles();
		//sub������ �ִ� ������ file�� �Ѱ��� 
		for (File file : target_file) { 
			//real ������ ������ �� sub������ ���� �̸��� ����� 
			File temp = new File(realAddress.getAbsoluteFile() + File.separator + file.getName());
			//�ش� ���丮 ���� Ȯ��
			if(file.isDirectory()) {
				temp.mkdir();
			} else {
				InputStream inStream = null;
				OutputStream outputStream = null;
				
				try {
					// ����Ʈ ������ ���� �а�
					inStream = new FileInputStream(file);
					// ����Ʈ ������ ���� ���
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
			System.out.println("���ϵ��");
		
	}
	public static void subDireCtoryDelete(File folder) {
		while(folder.exists()) {//������ �����Ҷ� 
			File[] folder_list = folder.listFiles();
			
			for (int i = 0; i < folder_list.length; i++) {

				System.out.println("���ϻ���"+folder_list[i]);
				folder_list[i].delete();
				System.out.println("���ϻ���");
			}
			
			if(folder_list.length == 0 && folder.isDirectory()) {
				folder.delete();
				System.out.println("������ ����");
			}
		}
	}
	
	//Get RealName
	//subName, realName�� ��� �ּ� �� ����
	public static S_ImgPathName  fileUpload( String fileName,String ymdPath,String UploadFlag,String b_user_id)
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
		if(UploadFlag.equals("1")) {
			img.setSubAddress(File.separator + "imgUpload\\"+ b_user_id + "\\board" + ymdPath + "\\s" + "\\sub" + File.separator);
			img.setRealAddress(File.separator + "imgUpload\\"+ b_user_id + "\\board" + ymdPath + "\\s"+  File.separator);
			img.setSubName( newFileName);
			img.setRealName( newFileName );
			System.out.println("��ƿ" +img.getSubAddress());
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