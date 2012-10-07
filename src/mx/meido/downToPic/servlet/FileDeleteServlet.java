package mx.meido.downToPic.servlet;

import java.io.File;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class FileDeleteServlet
 */
@WebServlet("/FileDelete.action")
public class FileDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FileDeleteServlet() {
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
		String savedFilePath = request.getServletContext().getRealPath("/")+"downloads/"+fileName;
		File savedFile = new File(savedFilePath);
		deleteFile(savedFile);
		response.setHeader("Content-Type", "text/html; charset=utf-8");
		response.getOutputStream().print("Success.");
	}
	
	private void deleteFile(File p){
		if(p.isDirectory()){
			File[] files = p.listFiles();
			for(File f : files){
				deleteFile(f);
			}
		}
		p.delete();
	}

}
