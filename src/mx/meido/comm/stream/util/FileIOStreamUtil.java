package mx.meido.comm.stream.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import mx.meido.comm.stream.StreamException;

/**
 * 文件IO流工具
 * @author sun hao
 */
public class FileIOStreamUtil {
	/**
	 * @param fileInput 文件输入流
	 * @param output 输出流
	 * @throws StreamException
	 */
	public void streamFile(InputStream fileInput, OutputStream output) throws StreamException{
		this.streamFile(fileInput, output, 0);
	}
	
	/**
	 * @param fileInput 文件输入流
	 * @param output 输出流
	 * @param escape 跳过的字节数
	 * @throws StreamException
	 */
	public void streamFile(InputStream fileInput, OutputStream output, long escape) throws StreamException{
		try {
			this.streamFile(fileInput, output, escape, fileInput.available() - escape);
		} catch (StreamException | IOException e) {
			e.printStackTrace();
			throw new StreamException(e);
		}
	}
	
	/**
	 * @param fileInput 文件输入流
	 * @param output 输出流
	 * @param escape 跳过的字节数
	 * @param length 要读取的字节数
	 * @throws StreamException
	 */
	public void streamFile(InputStream fileInput, OutputStream output, long escape, long length) throws StreamException{
		this.streamFile(fileInput, output, escape, length, new byte[4096]);
	}
	
	/**
	 * @param fileInput 文件输入流
	 * @param output 输出流
	 * @param escape 跳过的字节数
	 * @param length 要读取的字节数
	 * @param buf 缓冲区
	 * @throws StreamException
	 */
	public void streamFile(InputStream fileInput, OutputStream output, long escape, long length, byte[] buf) throws StreamException{
		try {
			fileInput.skip(escape);
			while(length >= buf.length){
				fileInput.read(buf);
				output.write(buf);
				output.flush();
				length -= buf.length;
			}
			if(length > 0){
				fileInput.read(buf, 0, (int) length);
				output.write(buf, 0, (int) length);
				output.flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new StreamException(e);
		}
	}
}
