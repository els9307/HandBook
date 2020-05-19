package com.handbook.util;

import java.io.File;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.UUID;

import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import net.coobird.thumbnailator.Thumbnails;

public class UploadFileUtils {

	static final int THUMB_WIDTH = 300;
	static final int THUMB_HEIGHT = 300;

	public static String fileUpload(String imgPath,byte[] fileData,String realName
			)
			throws Exception {
		System.out.println("uploadfile in==============");
		System.out.println(imgPath);
		System.out.println(realName);

		File target = new File(imgPath, realName);
		FileCopyUtils.copy(fileData, target);

		String thumbFileName = "s_" + realName;
		File image = new File(imgPath + File.separator + realName);

		File thumbnail = new File(imgPath + File.separator + "s" + File.separator + thumbFileName);
		if (image.exists()) {
			thumbnail.getParentFile().mkdirs();
			Thumbnails.of(image).size(THUMB_WIDTH, THUMB_HEIGHT).toFile(thumbnail);
		}
		return realName;
	}
	public static String fileUpload( String fileName, String ymdPath)
			throws Exception {

		UUID uid = UUID.randomUUID();
		String newFileName = uid + "_" + fileName;
		String realName = File.separator + "imgUpload" + ymdPath + File.separator + newFileName;
		return realName;
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