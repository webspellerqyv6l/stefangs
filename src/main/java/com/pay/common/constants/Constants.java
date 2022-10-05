package com.pay.common.constants;

import org.springframework.util.ClassUtils;

public class Constants {
	
	public static final String SF_FILE_SEPARATOR = System.getProperty("file.separator");//文件分隔符

	public static final String QRCODE_PATH = ClassUtils.getDefaultClassLoader().getResource("static").getPath()+SF_FILE_SEPARATOR+"qrcode"; 
	
	public static final String SUCCESS = "success";
	
	public static final String FAIL = "fail";
	
}
