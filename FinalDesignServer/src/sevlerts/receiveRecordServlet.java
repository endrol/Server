package sevlerts;

import java.util.HashMap;
import java.util.List;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;  
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import serviceS.serverListener;
import serviceS.socketManager;
import serviceS.transferRecordSocket;



public class receiveRecordServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public receiveRecordServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @return 
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	
	HashMap<String, String> map = new HashMap<String, String>();
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String info = "";
		String savePath = "E:/recordFile";
		File receiveFile = new File(savePath);
		if (!receiveFile.exists() && !receiveFile.isDirectory()) { 
			log("Ŀ¼������");
            //����Ŀ¼  
            receiveFile.mkdir();  
        }
		try {
			DiskFileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			upload.setHeaderEncoding("UTF-8");
			if(!ServletFileUpload.isMultipartContent(request)){  
                //���մ�ͳ��ʽ��ȡ����  
                System.out.println("û���ļ��ϴ�"); 
                info = "nothing received";
			}
			
			List<FileItem> list = upload.parseRequest(request);
			for(FileItem item : list){
	               //���fileitem�з�װ������ͨ�����������  
	               if(item.isFormField()){
	                   String name = item.getFieldName();
	                   
	                   //�����ͨ����������ݵ�������������  
	                  String value = item.getString("UTF-8");  
	                  //value = new String(value.getBytes("iso8859-1"),"UTF-8");  
	                   map.put(name, value);
	                  System.out.println(name + "=" + value);
	              }else{//���fileitem�з�װ�����ϴ��ļ�  
	                   //�õ��ϴ����ļ����ƣ�  
	                   String filename = item.getName();  
	                   System.out.println(filename);  
	                   if(filename==null || filename.trim().equals("")){  
	                       continue;}
	                   
	                   filename = filename.substring(filename.lastIndexOf("\\")+1); 
	                   map.put("fileNameReal", filename);
	                   //��ȡitem�е��ϴ��ļ���������  
	                   InputStream in = item.getInputStream();  
	                    //����һ���ļ������  
	                    FileOutputStream out = new FileOutputStream(savePath + "\\" + 
	                    filename);
	      //              map.put("filePath", savePath);
	                    //����һ��������  
	                    byte buffer[] = new byte[1024];  
	                    //�ж��������е������Ƿ��Ѿ�����ı�ʶ  
	                   int len = 0;  
	                   //ѭ�������������뵽����������
	                   //(len=in.read(buffer))>0�ͱ�ʾin���滹������  
	                   while((len=in.read(buffer))>0){  
	                       //ʹ��FileOutputStream�������������������д�뵽ָ����Ŀ¼
	                	   //(savePath + "\\" + filename)����  
	                       out.write(buffer, 0, len);  
	                   }  
	                    //�ر�������  
	                    in.close();  
	                    //�ر������  
	                    out.close();  
	                   //ɾ�������ļ��ϴ�ʱ���ɵ���ʱ�ļ�  
	                   item.delete();  
	                   info = "received!";
	              }
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		//����������vector�����е�socket
		socketManager.getSocketManager().addParameter(map);
		System.out.println("send to the socketManager the map info");
		System.out.println(map.entrySet());
		response.getWriter().append(info);
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
