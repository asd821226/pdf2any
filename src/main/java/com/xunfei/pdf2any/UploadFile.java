package com.xunfei.pdf2any;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 * Servlet implementation class UploadFile
 */
@WebServlet("/UploadFile")
@SuppressWarnings("rawtypes")
public class UploadFile extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	public UploadFile() {
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session=request.getSession(true);
		String sessionId=session.getId();
		System.out.println(session.getId());

		String realPath=this.getServletContext().getRealPath("")+"/uploads/"+sessionId;
		new File(realPath).mkdirs();
		DiskFileItemFactory factory = new DiskFileItemFactory();
		// maximum size that will be stored in memory
		factory.setSizeThreshold(4096);
		// the location for saving data that is larger than getSizeThreshold()
		factory.setRepository(new File(realPath));

		ServletFileUpload upload = new ServletFileUpload(factory);
		// maximum size before a FileUploadException will be thrown
		upload.setSizeMax(1000000000);
		String inFileName=null;
		List fileItems;
		try {
			fileItems = upload.parseRequest(request);
			// assume we know there are two files. The first file is a small
			// text file, the second is unknown and is written to a file on
			// the server
			Iterator i = fileItems.iterator();
			i.next();
			FileItem fi = (FileItem) i.next();
			// filename on the client
			String fileName = fi.getName();
			// save comment and filename to database
			// write the file
			inFileName=realPath+"/"+fileName;
			fi.write(new File(inFileName));
			session.setAttribute("fileName", inFileName);
		} catch (Exception e) {
			e.printStackTrace();
		}
//		request.getSession().invalidate(); 
		
	}

}
