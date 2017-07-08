/*
 *
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 */
package com.zds.scf.web.common.util;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

/**
 *
 *
 *
 */
public class MD5Util {
	
	/**
	 * 比较一个未加密字符串和使用MD5加密过的字符串是否相等
	 * @return
	 */
	public static boolean compareMD5String(String unencryptedStr, String encryptedStr) {
		if (StringUtils.isEmpty(unencryptedStr)) {
			if (StringUtils.isEmpty(encryptedStr)) {
				return true;
			}
			return false;
		} else {
			if (StringUtils.isEmpty(unencryptedStr)) {
				return false;
			}
		}
		String str = DigestUtils.md5Hex(unencryptedStr);
		if (StringUtils.equals(str, encryptedStr)) {
			return true;
		}
		return false;
	}
	
	/**
	 * 传入一个未加密的字符串，将其加密为32字节MD5字符串
	 * @param unencryptedStr
	 * @return
	 */
	public static String md5Digest(String unencryptedStr) {
		if (StringUtils.isEmpty(unencryptedStr)) {
			return "";
		}
		return DigestUtils.md5Hex(unencryptedStr);
	}
}
