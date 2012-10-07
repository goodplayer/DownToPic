package mx.meido.downToPic.fileToPic;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class AnalysePic {
	public void analyse24bit(File src, File dest){
		try {
			BufferedInputStream bis = new BufferedInputStream(new FileInputStream(src));
			FileOutputStream fos = new FileOutputStream(dest);
			analyse24bit(bis,
					fos);
			bis.close();
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void analyse24bit(InputStream is, OutputStream os){
		byte[] buf = new byte[1024];
		int need = 0;
		int total = 0;
		int fileSize = 0;
		try {
			is.skip(6);
			is.read(buf, 0, 4);
			need = bytesToInt(buf);
			is.skip(24);
			is.read(buf, 0, 4);
			total = bytesToInt(buf);
			is.skip(16);
			fileSize = total - need;
			while(fileSize >= 1024){
				is.read(buf);
				os.write(buf);
				os.flush();
				fileSize -= 1024;
			}
			if(fileSize > 0){
				is.read(buf, 0, fileSize);
				os.write(buf, 0, fileSize);
				os.flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void analyse32bit(File src, File dest){
		try {
			BufferedInputStream bis = new BufferedInputStream(new FileInputStream(src));
			FileOutputStream fos = new FileOutputStream(dest);
			analyse32bit(bis,
					fos);
			bis.close();
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void analyse32bit(InputStream is, OutputStream os){
		byte[] buf = new byte[1024];
		int need = 0;
		int total = 0;
		int fileSize = 0;
		try {
			is.skip(6);
			is.read(buf, 0, 4);
			need = bytesToInt(buf);
			is.skip(24);
			is.read(buf, 0, 4);
			total = bytesToInt(buf);
			is.skip(84);
			fileSize = total - need;
			while(fileSize >= 1024){
				is.read(buf);
				os.write(buf);
				os.flush();
				fileSize -= 1024;
			}
			if(fileSize > 0){
				is.read(buf, 0, fileSize);
				os.write(buf, 0, fileSize);
				os.flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * little endian��byte����ת��Ϊint
	 * @param src
	 * @return
	 */
	public static int bytesToInt(byte[] src){
		int i = 0;
		i |= (src[0]&0x000000FF);
		i |= ((src[1] << 8) & 0x0000FF00);
		i |= ((src[2] << 16) & 0x00FF0000);
		i |= ((src[3] << 24) & 0xFF000000);
		return i;
	}
}
