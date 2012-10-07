package mx.meido.downToPic.servlet;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mx.meido.comm.stream.util.FileIOStreamUtil;

/**
 * Servlet implementation class DownloadFileServlet
 */
@WebServlet("/DownloadFile.action")
public class DownloadFileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 用于读取文件并发送
	 */
	private static final FileIOStreamUtil fsu = new FileIOStreamUtil();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DownloadFileServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String fileName = request.getParameter("file");
		if(fileName == null || fileName.length() == 0){
			//设置返回的内容格式
			response.setHeader("Content-Type", "text/html; charset=utf-8");
			response.getOutputStream().print("Need to specify a file! Using 'file' parameter.");
			return;
		}
		String savedFilePath = request.getServletContext().getRealPath("/")+"downloads/"+fileName+"/~main";
		File savedFile = new File(savedFilePath);
		
		//发送
		String rangeStr = request.getHeader("Range");
		//是否是断续请求
		if(rangeStr != null && rangeStr.length() != 0){
			//如果为分段的请求
			//设置响应代码
			response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);
			response.setHeader("Accept-Ranges", "bytes");
			rangeStr = rangeStr.substring(rangeStr.indexOf("=")+1);
			String[] ranges = rangeStr.split("-");
			//获取请求的长度
			long startPos = Long.parseLong(ranges[0]);
			long endPos = savedFile.length();
			//获取文件总大小
			long fileSize = endPos;
			if(ranges.length > 1){
				endPos = Long.parseLong(ranges[1]);
			}
			
			//头
			response.setHeader("Content-Range", "bytes "+startPos+"-"+endPos+"/"+fileSize);
			long sendSize = endPos - startPos + 1;
			response.setHeader("Content-Length", ""+sendSize);
			
			//开始传输
			OutputStream os = response.getOutputStream();
			try(InputStream is = new BufferedInputStream(new FileInputStream(savedFile))){
				//传输
				fsu.streamFile(is, os, startPos, sendSize);
			}catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			//如果为第一次请求
			long fileSize = savedFile.length();
			response.setHeader("Accept-Ranges", "bytes");
			response.setHeader("Content-Type", "text/html; charset=utf-8");
			response.setHeader("Content-Length", ""+fileSize);
			OutputStream os = response.getOutputStream();
			try(InputStream is = new BufferedInputStream(new FileInputStream(savedFile))){
				//传输
				fsu.streamFile(is, os);
			}catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

}
