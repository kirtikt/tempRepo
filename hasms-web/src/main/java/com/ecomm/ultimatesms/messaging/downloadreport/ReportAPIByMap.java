package com.ecomm.ultimatesms.messaging.downloadreportAPI;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ecomm.pl4sms.persistence.dbutilsCRUD.SmsManager;
import com.ecomm.ultimatesms.messaging.utils.Helper;

/**
 * Servlet implementation class CreditPerClient
 */
public class ReportAPIByMap extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ReportAPIByMap() {
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

	public void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		if (request.getSession(false) == null
				|| request.getSession(false).getAttribute("isActive") == null
				|| "null".equals(request.getSession(false).getAttribute(
						"isActive"))) {
			response.sendRedirect("/payless4sms/adminLogin.jsp?errorMessage=please login");
			return;
		}
		Logger log = LoggerFactory.getLogger(ReportAPIByMap.class);
		String displayStatus = request.getParameter("day");
		HttpSession httpSession = request.getSession(false);

		WriteExcelForMap test = new WriteExcelForMap();
		Properties props = Helper.getProps();
		Map map = (Map) httpSession.getAttribute("downloadedMap");
		log.info("Downloaded map::" + map);
		String basicpath = props.getProperty("uploaddirectory");
		String reportpath = basicpath + "/report.xls";
		test.setOutputFile(reportpath);
		if(displayStatus!=null){
		test.write(map, displayStatus);
		}
		else{
			test.write(map);
		}

		File f = new File(reportpath);
		if (f.exists()) {

			response.setHeader("Content-Disposition",
					"attachment;filename=report.xls");
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
			boolean isdeleted = f.delete();

		}
	}
}