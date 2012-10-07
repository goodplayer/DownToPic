package mx.meido.downToPic.fileToPic;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class GenPic {
	public void generate24bit(File src, File dest){
		try {
			BufferedInputStream bis = new BufferedInputStream(new FileInputStream(src));
			FileOutputStream fos = new FileOutputStream(dest);
			generate24bit(src.length(),
					bis,
					fos);
			bis.close();
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void generate24bit(long fileSize, InputStream is, OutputStream os){
		if(fileSize <= 0 || (fileSize+14+40) > Integer.MAX_VALUE)
			throw new NumberFormatException("Should greater than 0 and less than Integer.MAX_VALUE");
		
		//�����ļ���С,���ͼƬ���
		PicCal.PicData pd = PicCal.calPic(fileSize);
		
		try {
			//BMP Header
			os.write(new byte[]{0x42,0x4D});
			os.write(intToBytes((int) (pd.getSrcSize()+14+40)));
			os.write(intToBytes((int) pd.getNeed()));
			os.write(new byte[]{0x36, 0x00, 0x00, 0x00});
			
			//DIB Header
			os.write(new byte[]{0x28, 0x00, 0x00, 0x00});
			os.write(intToBytes((int) pd.getPixel_x()));
			os.write(intToBytes((int) pd.getPixel_y()));
			os.write(new byte[]{0x01, 0x00, 0x18, 0x00});
			os.write(new byte[]{0x00, 0x00, 0x00, 0x00});
			os.write(intToBytes((int) pd.getTotalSize()));
			os.write(new byte[]{0x00, 0x00, 0x00, 0x00});
			os.write(new byte[]{0x00, 0x00, 0x00, 0x00});
			os.write(new byte[]{0x00, 0x00, 0x00, 0x00});
			os.write(new byte[]{0x00, 0x00, 0x00, 0x00});
			
			//Start of pixel array (bitmap data)
			byte[] buf = new byte[1024];
			int readLenght = 0;
			while((readLenght = is.read(buf)) != -1){
				os.write(buf, 0, readLenght);
			}
			byte[] zero = new byte[]{0x00};
			for(int i = 0; i < pd.getNeed(); i++){
				os.write(zero);
			}
			os.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void generate32bit(File src, File dest){
		try {
			BufferedInputStream bis = new BufferedInputStream(new FileInputStream(src));
			FileOutputStream fos = new FileOutputStream(dest);
			generate32bit(src.length(),
					bis,
					fos);
			bis.close();
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void generate32bit(long fileSize, InputStream is, OutputStream os){
		if(fileSize <= 0 || (fileSize+14+108) > Integer.MAX_VALUE)
			throw new NumberFormatException("Should greater than 0 and less than Integer.MAX_VALUE");
		
		//�����ļ���С,���ͼƬ���
		PicCal.PicData pd = PicCal.calPic(fileSize);
		
		try {
			//BMP Header
			os.write(new byte[]{0x42,0x4D});
			os.write(intToBytes((int) (pd.getSrcSize()+14+108)));
			os.write(intToBytes((int) pd.getNeed()));
			os.write(new byte[]{0x7A, 0x00, 0x00, 0x00});
			
			//DIB Header
			os.write(new byte[]{0x6C, 0x00, 0x00, 0x00});
			os.write(intToBytes((int) pd.getPixel_x()));
			os.write(intToBytes((int) pd.getPixel_y()));
			os.write(new byte[]{0x01, 0x00, 0x20, 0x00});
			os.write(new byte[]{0x03, 0x00, 0x00, 0x00});
			os.write(intToBytes((int) pd.getTotalSize()));
			os.write(new byte[]{0x00, 0x00, 0x00, 0x00});
			os.write(new byte[]{0x00, 0x00, 0x00, 0x00});
			os.write(new byte[]{0x00, 0x00, 0x00, 0x00});
			os.write(new byte[]{0x00, 0x00, 0x00, 0x00});
			//
			os.write(new byte[]{0x00, 0x00, (byte) 0xFF, 0x00});
			os.write(new byte[]{0x00, (byte) 0xFF, 0x00, 0x00});
			os.write(new byte[]{(byte) 0xFF, 0x00, 0x00, 0x00});
			os.write(new byte[]{0x00, 0x00, 0x00, (byte) 0xFF});
			os.write(new byte[]{0x20, 0x6E, 0x69, 0x57});
			os.write(new byte[]{0x00, 0x00, 0x00, 0x00});
			os.write(new byte[]{0x00, 0x00, 0x00, 0x00});
			os.write(new byte[]{0x00, 0x00, 0x00, 0x00});
			os.write(new byte[]{0x00, 0x00, 0x00, 0x00});
			os.write(new byte[]{0x00, 0x00, 0x00, 0x00});
			os.write(new byte[]{0x00, 0x00, 0x00, 0x00});
			os.write(new byte[]{0x00, 0x00, 0x00, 0x00});
			os.write(new byte[]{0x00, 0x00, 0x00, 0x00});
			os.write(new byte[]{0x00, 0x00, 0x00, 0x00});
			os.write(new byte[]{0x00, 0x00, 0x00, 0x00});
			os.write(new byte[]{0x00, 0x00, 0x00, 0x00});
			os.write(new byte[]{0x00, 0x00, 0x00, 0x00});
			
			//Start of pixel array (bitmap data)
			byte[] buf = new byte[1024];
			int readLenght = 0;
			while((readLenght = is.read(buf)) != -1){
				os.write(buf, 0, readLenght);
			}
			byte[] zero = new byte[]{0x00};
			for(int i = 0; i < pd.getNeed(); i++){
				os.write(zero);
			}
			os.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * int����ת��Ϊlittle endian��byte����
	 * @param src
	 * @return
	 */
	public static byte[] intToBytes(int src){
		byte[] b = new byte[4];
		b[0] = (byte) ((src >>> 0) & 0xFF);
		b[1] = (byte) ((src >>> 8) & 0xFF);
		b[2] = (byte) ((src >>> 16) & 0xFF);
		b[3] = (byte) ((src >>> 24) & 0xFF);
		return b;
	}
}
