package mx.meido.downToPic.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.bouncycastle.util.encoders.Hex;

public class MessageDigestUtil {
	private MessageDigest md;
	public MessageDigestUtil(String algorithm) {
		try {
			md = MessageDigest.getInstance(algorithm);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}
	public String digest(File src){
		return MessageDigestUtil.digest(src, md);
	}
	public static String digest(File src, MessageDigest wp){
		try {
			BufferedInputStream bis = new BufferedInputStream(new FileInputStream(src));
			byte[] b = new byte[4096*4];
			int r = 0;
			while((r = bis.read(b))!=-1){
				wp.update(b, 0, r);
			}
			byte[] digest = wp.digest();

			return new String(Hex.encode(digest));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
