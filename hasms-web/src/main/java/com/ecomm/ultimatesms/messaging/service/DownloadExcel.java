package com.ecomm.ultimatesms.messaging.service;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Servlet implementation class DownloadExcel
 */
public class DownloadExcel extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DownloadExcel() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		processRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		processRequest(request, response);
	}

	void processRequest(HttpServletRequest request, HttpServletResponse response) {
		try {
			if(request.getSession(false)==null || request.getSession(false).getAttribute("isActive")==null || "null".equals(request.getSession(false).getAttribute("isActive")) ){
				try {
					response.sendRedirect("/payless4sms/adminLogin.jsp?errorMessage=please login");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return;
			}
			//String path = "/home/varsha/jboss-6.0.0.Final/bin/report.xls";
			Logger log=LoggerFactory.getLogger(DownloadExcel.class);
			String path = request.getParameter("hdfreportpath");
			File f = new File(path);
			if (f.exists()) {
				
				response.setHeader("Content-Disposition","attachment;filename=report.xls");
				response.setContentType("application/excel");
				response.setContentLength((int) f.length());
				
				byte[] bbuf = new byte[1024];
				DataInputStream in;

				in = new DataInputStream(new FileInputStream(f));

				int z = 0;
				while (z != (int) f.length()) {
					try {
						in.read(bbuf);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					try {
						response.getOutputStream().write(bbuf);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					z++;
				}
				try {
					response.getOutputStream().flush();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					response.getOutputStream().close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
                 log.info("file download finished");
               //  boolean isdeleted=f.delete();
               //  log.info("Is deleted::"+isdeleted);
			}
           
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
		}
	}
}
