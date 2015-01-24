package com.ecomm.ultimatesms.messaging.service;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.ecomm.pl4sms.persistence.dbutils.HibernateUtil;
import com.ecomm.pl4sms.persistence.dbutilsCRUD.SmsManager;
import com.ecomm.ultimatesms.messaging.persistence.pojos.Sms;
import com.ecomm.ultimatesms.messaging.utils.WriteExcel;


/**
 * Servlet implementation class WriteTOExcel
 */
public class WriteTOExcel extends HttpServlet  {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public WriteTOExcel() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		processRequest( request, response) ;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		 processRequest( request, response) ;
	}
	String basicpath;
	public void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException{
		if(request.getSession(false)==null || request.getSession(false).getAttribute("isActive")==null || "null".equals(request.getSession(false).getAttribute("isActive")) ){
			response.sendRedirect("/payless4sms/adminLogin.jsp?errorMessage=please login");
			return;
		}
		boolean isadmin=false;
		RequestDispatcher rd = null;
		String reportType=request.getParameter("reportType");
		long clientmanagerId=(Long)request.getSession(false).getAttribute("clientmanagerId");
		Properties props=new Properties();
		try {
			props.load(SmsManager.class.getClassLoader().getResourceAsStream("ultimatesms.properties"));
			} catch (Exception e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		basicpath=props.getProperty("uploaddirectory");
		String reportpath="";
		if("simplereport".equalsIgnoreCase(reportType)){
			reportpath=simpleReport(clientmanagerId);
		}
		else if("failedreport".equalsIgnoreCase(reportType))
		{
			reportpath=failedReport(clientmanagerId);
		}
		else if("adminsimplereport".equalsIgnoreCase(reportType))
		{
			String from=request.getParameter("reportFromDate");
			String to=request.getParameter("reportToDate");
			String clientid=request.getParameter("clientid");
			String from1=from + " 00:00:00";
			String to1=to + " 23:59:59";
			System.out.println("from::"+from1+"To::"+to1);
			reportpath=simpleReportForAdmin(from1,to1,clientid);
			isadmin=true;
		}
		else if("failedreportforadmin".equalsIgnoreCase(reportType))
		{
			reportpath=failedReportForAdmin();
		}
		else if("adminreportforadmin".equalsIgnoreCase(reportType))
		{
			reportpath=adminReportforAdmin();
			isadmin=true;
		}
		if(isadmin){
			rd = request.getRequestDispatcher("adminreports/landpage.jsp");
		}else{
		rd = request.getRequestDispatcher("reports/landpage.jsp");
		}
		request.setAttribute("msg","Excel File created. To download click on Download Now");
		request.setAttribute("reportpath", reportpath);
		reportpath=null;
		try {
			rd.forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public String adminReportforAdmin()
	{
			WriteExcel test = new WriteExcel();
			
			String reportpath=basicpath+"/report.xls";
			test.setOutputFile(reportpath);
			//System.out.println("Please check the result file under lars.xls ");
				List<Sms> smsList= new SmsManager().getSmsList("status","11");
				test.write(smsList);
				return reportpath;
		
	}
	
	public String simpleReport(long clientmanagerId)
	{
			WriteExcel test = new WriteExcel();
			String reportpath=basicpath+"/report.xls";
			test.setOutputFile(reportpath);
//			System.out.println("Please check the result file under lars.xls ");
//			
//			System.out.println("clientmanagerId: in jsp::"+clientmanagerId);
//			
				List<Sms> smsList= new SmsManager().getSmsList("status","11",clientmanagerId);
				test.write(smsList);
				return reportpath;
		
	}
	public String failedReport(long clientmanagerId)
	{
			WriteExcel test = new WriteExcel();
			String reportpath=basicpath+"/failedreport.xls";
			test.setOutputFile(reportpath);
//			System.out.println("Please check the result file under lars.xls ");
//			
//			System.out.println("clientmanagerId: in jsp::"+clientmanagerId);
//			
				List<Sms> smsList= new SmsManager().getSmsList("status","16",clientmanagerId);
				test.write(smsList);
				return reportpath;
		
	}
	public String simpleReportForAdmin(String from,String to,String clienid)
	{
			WriteExcel test = new WriteExcel();
			Long id=null;
			List<Sms> smsList=null;
			String reportpath=basicpath+"/simplereportforadmin.xls";
			test.setOutputFile(reportpath);
			//System.out.println("Please check the result file under lars.xls ");
			if(clienid!=null){
				id=Long.parseLong(clienid);
			
			 smsList= new SmsManager().getSmsList("status","11",id,from,to);
			}
				test.write(smsList);
				return reportpath;
		
	}
	public String failedReportForAdmin()
	{
			WriteExcel test = new WriteExcel();
			String reportpath=basicpath+"/failedreportforadmin.xls";
			test.setOutputFile(reportpath);
			//System.out.println("Please check the result file under lars.xls ");
				List<Sms> smsList= new SmsManager().getSmsList("status","16");
				test.write(smsList);
				return reportpath;
		
	}
	
}
