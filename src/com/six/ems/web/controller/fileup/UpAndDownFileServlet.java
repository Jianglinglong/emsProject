package com.six.ems.web.controller.fileup;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.six.ems.web.controller.base.BaseServlet;
/**
 * 文件上传
 */
public class UpAndDownFileServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpAndDownFileServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    /**
     * 转发到上传页面
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public String initIndex(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
    	return "/WEB-INF/jsp/fileup/upload.jsp";
    	//request.getRequestDispatcher("/WEB-INF/jsp/fileup/upload.jsp").forward(request, response);
    }
    
    /**
     * 定义上传的方法
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void uploadFile(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 创建一个存储对象的容器
		Map<String,FileItem> context = new HashMap<String,FileItem>();
		// 获取文件上传的真实路径
		String realPath = getServletContext().getRealPath("/WEB-INF/files/upload");
		// 创建上传文件目录
		File savePath = new File(realPath);
		// 判断上传目录是否存在
		if (!savePath.exists()) {
			savePath.mkdirs();
		}
		// 创建一个文件项对象
		DiskFileItemFactory fileItemFactory = new DiskFileItemFactory();
		// 设置单个文件上传的大小
		fileItemFactory.setSizeThreshold(100*1024*1024);
		// 设置上传存储临时目录
		fileItemFactory.setRepository(new File("E:/temp"));
		// 创建一个文件上传解析器
		ServletFileUpload upload = new ServletFileUpload(fileItemFactory);
		try {
			List<FileItem> itemList = upload.parseRequest(request);
			if (itemList != null && itemList.size() > 0) {
				for(FileItem item : itemList) {
					String fileName = item.getName();
					String name = item.getName();
					// 处理文件名称避免覆盖文件
					name = handlerFileName(fileName, name);
					// 创建一个需要上传都目标目录下面的空白文件
					File saveFile = new File(savePath,name);
					// 把源文件的内容写入目标文件
					item.write(saveFile);
				}
			}


		} catch (Exception e) {
			e.printStackTrace();
		}
		response.getWriter().print("OK");
		// 转发
		// return "/WEB-INF/jsp/fileup/upload.jsp";
	}
    
    /**
     * 定义去重的方法
     * @param alinName
     * @param name
     * @return
     */
	private String handlerFileName(String alinName, String name) {
		StringBuffer fileName = new StringBuffer();
        int lastIndexOf = name.lastIndexOf('.');
        String extension = name.substring(lastIndexOf);
        fileName.append(name.substring(0,lastIndexOf));
        Date date = new Date();
        fileName.append((date.getYear()+1900)+"-" +date.getMonth()+"-"+date.getDate()+" "+date.getHours()+"-"+date.getMinutes()+"-"+date.getSeconds() );
        fileName.append(extension);
//		// 判断
//		if(alinName != null && !"".equals(alinName)) {
//			fileName = alinName + extension;
//		}else {
//			String uuid = UUID.randomUUID().toString().replaceAll("-", "");
//			fileName = uuid+extension;
//		}
		return fileName.toString();
	}
    
	
	/**
	 * 定义初始化的方法
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void initFile(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 设置编码格式
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		/*读取下载目录下面的文件*/
		// 获取文件上传的真实路径
		String realPath = getServletContext().getRealPath("/WEB-INF/files/upload");
		// 创建文件下载目录
		File downloadPath = new File(realPath);
		// 获取所有文件
		File[] files = downloadPath.listFiles();
		// 存储到request对象中
		request.setAttribute("files", files);
		// 转发到download下载页面
		request.getRequestDispatcher("/WEB-INF/jsp/fileup/download.jsp").forward(request, response);
	}
	
	
	/**
	 * 定义下载的方法
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void downloadFile(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 获取请求的fileName
		String fileName = request.getParameter("fileName");
		// 转码处理
		fileName = new String(fileName.getBytes("ISO-8859-1"),"UTF-8");
		// 获取输入流的路径
		InputStream in = getServletContext().getResourceAsStream("/WEB-INF/files/upload/"+fileName);
		// 设置响应头
		if (request.getHeader("USER-AGENT").toLowerCase().indexOf("msie") > 0) { // IE浏览器
			// 转码
			fileName = URLEncoder.encode(fileName,"UTF-8");
			fileName = fileName.replace("+", "%20");// 处理空格变"+"的问题
		}else {
			fileName = new String(fileName.getBytes("UTF-8"),"ISO-8859-1");
		}
		// 设置响应的类型
		response.setContentType("application/octet-stream;");
		response.setHeader("Content-Disposition", "attachment;filename=\""+fileName+"\"");
		response.setHeader("Content-Length", String.valueOf(in.available()));
		
		// 获取响应的输出流
		ServletOutputStream out = response.getOutputStream();
		int len = 0;// 定义实际长度
		// 定义读取的字节数
		byte[] sb = new byte[1024];
		// 循环读取文件
		while ((len=in.read(sb)) != -1) {
			// 输出
			out.write(sb, 0, len);
		}
		// 关闭流
		out.flush();
		out.close();
		in.close();
	}

}
