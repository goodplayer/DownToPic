package mx.meido.downToPic.thread;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.Security;

import mx.meido.downToPic.fileToPic.GenPic;
import mx.meido.downToPic.servlet.SubmitServlet;
import mx.meido.downToPic.util.MessageDigestUtil;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.log4j.Logger;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class DownThread extends Thread {
	private static Logger logger = Logger.getLogger(DownThread.class);
	
	public long total = 0;
	public long done = 0;
	
	public String downUrl;
	public String saveName;
	private String parentFolder;
	
	private HttpGet httpGet;
	
	public DownThread(String url, String name, String parentFolder, HttpGet httpGet) {
		this.downUrl = url;
		this.saveName = name;
		this.parentFolder = parentFolder;
		this.httpGet = httpGet;
		SubmitServlet.l.add(this);
	}
	
	@Override
	public void run() {
		//新建文件和文件夹
		File saveFolder = new File(parentFolder+saveName);
		if(!saveFolder.exists())
			saveFolder.mkdir();
		File saveMainFile = new File(saveFolder, "~main");
		if(!saveMainFile.exists())
			try {
				saveMainFile.createNewFile();
			} catch (IOException e1) {
				e1.printStackTrace();
			}

		//处理下载和生成图片
		HttpClient httpClient = new DefaultHttpClient();
//		HttpGet httpGet = new HttpGet(downUrl);
		try {
			//处理下载
			HttpResponse response = httpClient.execute(httpGet);
			
			logger.info(downUrl+" has started,save to:"+saveName);
			
			if(HttpStatus.SC_OK == response.getStatusLine().getStatusCode()){
				HttpEntity entity = response.getEntity();
				
				logger.info(saveName + " content-length:"+entity.getContentLength());
				
				//下载
				total = entity.getContentLength();
				InputStream input = entity.getContent();
				byte[] b = new byte[1024];
				int j = 0;
				FileOutputStream fos = new FileOutputStream(saveMainFile);
				while((j = input.read(b))!=-1){
					fos.write(b, 0, j);
					done += j;
				}
				fos.flush();
				fos.close();
				
				logger.info(saveName + " has completed,downloaded size:"+done);
				
				//保证大于2G的文件不进行MD5
				//TODO 本段仅限于在加入分卷之前使用.当加入分卷之后,需对本段代码进行重构
				if(saveMainFile.length() <= Integer.MAX_VALUE){
					//以下为处理
					//源文件摘要
					MessageDigest md = MessageDigest.getInstance("whirlpool");
					String r_wp = MessageDigestUtil.digest(saveMainFile, md);
					md = MessageDigest.getInstance("md5");
					String r_md5 = MessageDigestUtil.digest(saveMainFile, md);
					logger.info(saveName + " MD5:"+r_md5);
					logger.info(saveName + " whirlpool:"+r_wp);
					
					//生成图片
					File dest = new File(saveMainFile.getParentFile(), "~main.bmp");
					if(saveMainFile.exists()&&saveFolder.exists()){
						GenPic g = new GenPic();
						g.generate32bit(saveMainFile, dest);
					}

					//图片摘要
					md = MessageDigest.getInstance("md5");
					String rp_md5 = MessageDigestUtil.digest(dest, md);
					logger.info(saveName + " pic file MD5:"+rp_md5);
					
				}
			}else{
				saveMainFile.delete();
				saveFolder.delete();
				logger.info(saveName + " downloading has stoped,error:"+response.getStatusLine().getStatusCode());
			}

		} catch(Exception e){
			e.printStackTrace();
			logger.info(saveName + " exception:"+e.getMessage());
		} finally{
			httpGet.releaseConnection();
		}
		
		
		SubmitServlet.l.remove(this);
	}
	
	static{
		Security.addProvider(new BouncyCastleProvider());
	}
}
