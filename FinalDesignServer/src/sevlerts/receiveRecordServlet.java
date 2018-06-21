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
			log("目录不存在");
            //创建目录  
            receiveFile.mkdir();  
        }
		try {
			DiskFileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			upload.setHeaderEncoding("UTF-8");
			if(!ServletFileUpload.isMultipartContent(request)){  
                //按照传统方式获取数据  
                System.out.println("没有文件上传"); 
                info = "nothing received";
			}
			
			List<FileItem> list = upload.parseRequest(request);
			for(FileItem item : list){
	               //如果fileitem中封装的是普通输入项的数据  
	               if(item.isFormField()){
	                   String name = item.getFieldName();
	                   
	                   //解决普通输入项的数据的中文乱码问题  
	                  String value = item.getString("UTF-8");  
	                  //value = new String(value.getBytes("iso8859-1"),"UTF-8");  
	                   map.put(name, value);
	                  System.out.println(name + "=" + value);
	              }else{//如果fileitem中封装的是上传文件  
	                   //得到上传的文件名称，  
	                   String filename = item.getName();  
	                   System.out.println(filename);  
	                   if(filename==null || filename.trim().equals("")){  
	                       continue;}
	                   
	                   filename = filename.substring(filename.lastIndexOf("\\")+1); 
	                   map.put("fileNameReal", filename);
	                   //获取item中的上传文件的输入流  
	                   InputStream in = item.getInputStream();  
	                    //创建一个文件输出流  
	                    FileOutputStream out = new FileOutputStream(savePath + "\\" + 
	                    filename);
	      //              map.put("filePath", savePath);
	                    //创建一个缓冲区  
	                    byte buffer[] = new byte[1024];  
	                    //判断输入流中的数据是否已经读完的标识  
	                   int len = 0;  
	                   //循环将输入流读入到缓冲区当中
	                   //(len=in.read(buffer))>0就表示in里面还有数据  
	                   while((len=in.read(buffer))>0){  
	                       //使用FileOutputStream输出流将缓冲区的数据写入到指定的目录
	                	   //(savePath + "\\" + filename)当中  
	                       out.write(buffer, 0, len);  
	                   }  
	                    //关闭输入流  
	                    in.close();  
	                    //关闭输出流  
	                    out.close();  
	                   //删除处理文件上传时生成的临时文件  
	                   item.delete();  
	                   info = "received!";
	              }
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		//将参数传给vector里所有的socket
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
