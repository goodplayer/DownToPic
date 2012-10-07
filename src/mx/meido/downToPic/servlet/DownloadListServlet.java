package mx.meido.downToPic.servlet;

import java.io.File;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mx.meido.downToPic.thread.DownThread;

/**
 * Servlet implementation class DownloadListServlet
 */
@WebServlet("/DownloadList.action")
public class DownloadListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DownloadListServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//存储位置的Url
		String purl = request.getRequestURL().substring(0, request.getRequestURL().lastIndexOf("/"))+"/downloads";
		//存储位置
		File parentFolder = new File(request.getServletContext().getRealPath("/")+"downloads/");
		//设置返回的内容格式
		response.setHeader("Content-Type", "text/html; charset=utf-8");
		//处理下载的文件
		ServletOutputStream sos = response.getOutputStream();
		sos.print("Downloaded:<br />");
		printPath(sos, parentFolder, purl);
		
		sos.print("<br /><br />");
		//处理正在下载的文件
		sos.print("Downloading:<br />");
		for(DownThread dt : SubmitServlet.l){
			sos.print(dt.done+"/"+dt.total+"&nbsp;"+dt.saveName+"&nbsp;"+dt.downUrl+"<br />");
		}
	}
	
	protected void printPath(ServletOutputStream sos, File p) throws IOException{
		sos.print(p.getAbsolutePath()+"&nbsp;"+p.length()+"<br />");

		if(p.isDirectory()){
			File[] files = p.listFiles();
			for(File f : files){
				printPath(sos, f);
			}
		}
	}

	protected void printPath(ServletOutputStream sos, File p , String purl) throws IOException{
		String path = p.getAbsolutePath();
		path = path.replace('\\', '/');
		try {
			int index = path.indexOf("/", path.lastIndexOf("downloads"));
			if(index != -1)
				path = path.substring(index);
			else
				path = "";
		} catch (Exception e) {
			e.printStackTrace();
		}
		sos.print("<a href='"+purl+path+"'>");
		sos.print(purl+path);
		sos.print("</a>&nbsp;"+p.length()+"<br />");

		if(p.isDirectory()){
			File[] files = p.listFiles();
			for(File f : files){
				printPath(sos, f, purl);
			}
		}
	}

}
