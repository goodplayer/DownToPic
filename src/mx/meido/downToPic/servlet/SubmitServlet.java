package mx.meido.downToPic.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.client.methods.HttpGet;

import mx.meido.downToPic.thread.DownThread;

/**
 * Servlet implementation class SubmitServlet
 */
@WebServlet("/Submit.action")
public class SubmitServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public static List<DownThread> l = Collections.synchronizedList(new ArrayList<DownThread>());
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SubmitServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String url = request.getParameter("downurl");
		String name = request.getParameter("savename");
		if(url == null || name == null
				|| url.trim().length() == 0 || name.trim().length() == 0){
			response.setHeader("Content-Type", "text/html; charset=utf-8");
			response.getOutputStream().print("Nothing happened");
			return;
		}
		
		String partSizeStr = request.getParameter("partSize");
		int partSize = 2140000000;
		try{
			partSize = Integer.valueOf(partSizeStr);
		} catch (Exception e) {
		}
		
		url = new String(url.getBytes("ISO-8859-1"), "UTF-8");
		name = new String(name.getBytes("ISO-8859-1"), "UTF-8");
		
//		System.out.println(request.getServletContext().getRealPath("/")+"downloads/");
		
		HttpGet httpGet = new HttpGet(url);
		String cookie = request.getParameter("cookie");
		if(cookie != null && cookie.trim().length() != 0)
			httpGet.setHeader("Cookie", cookie.trim());
		new DownThread(url, name, request.getServletContext().getRealPath("/")+"downloads/", httpGet).start();
		
		response.setHeader("Content-Type", "text/html; charset=utf-8");
		response.getOutputStream().print("Task added!");
	}

}
