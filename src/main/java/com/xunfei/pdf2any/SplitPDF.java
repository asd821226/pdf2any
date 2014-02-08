package com.xunfei.pdf2any;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/SplitPDF")
public class SplitPDF extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session=request.getSession(true);
		String sessionId=session.getId();
		String fileName=(String) session.getAttribute("fileName");
		System.out.println("fileName:"+fileName);
		String inSplit=(String)request.getParameter("inSplit");
		PDFSplit split=PDFSplit.getInstance();
		split.splitToMulti(fileName, inSplit);
//		RequestDispatcher dispatcher = request.getRequestDispatcher(
//				fileName.substring(fileName.indexOf("upload"),fileName.length()-4)+".zip"); 
////		RequestDispatcher dispatcher = request.getRequestDispatcher("http://www.baidu.com/"); 
//		dispatcher.forward(request, response); 
		response.getWriter().print(
				fileName.substring(fileName.indexOf("upload"),fileName.length()-4)+".zip");
	}

}
