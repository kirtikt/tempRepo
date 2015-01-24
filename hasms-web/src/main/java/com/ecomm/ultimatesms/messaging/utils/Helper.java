package com.ecomm.ultimatesms.messaging.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TimeZone;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ecomm.pl4sms.persistence.dbutils.HibernateUtil;
import com.ecomm.pl4sms.persistence.dbutilsCRUD.SmsManager;
import com.ecomm.ultimatesms.messaging.persistence.pojos.Clientmanager;
import com.ecomm.ultimatesms.messaging.persistence.pojos.Sendinginterface;
import com.ecomm.ultimatesms.messaging.persistence.pojos.Sms;
import com.ecomm.ultimatesms.messaging.persistence.pojos.Smscproxyproperty;

public class Helper {

	Logger log=LoggerFactory.getLogger(Helper.class);
	
	/**
	 * This method will return the status display message for last stage of particular sms by providing smsid as parameter. 
	 * @param smsid
	 * @return
	 */
	
	public Helper(){
		
		try {
			props=getProps();
			} catch (Exception e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} 
	}	
	public String getSmsStatus(String smsid){
	 String status="";
	SmsManager sm=new SmsManager();
	
	List<Sms> smsList=sm.getSmsList("smsid", smsid);
	Collections.sort(smsList, new myComparator());
	System.out.println("----");
		Sms sms=(Sms)smsList.get(0);
		status=getStatusMessage(sms.getStatus());
		System.out.println(sms.getSmsid() +" "+sms.getStatus());

	return status;

   }
	
	public String getStatusMessage(String status) {
		
		
		if (status != null) {
			if (status.trim().equals("11")) {
				status = props.getProperty("11");
			} else if (status.trim().equals("22")) {
				status = props.getProperty("22");
			} else if (status.trim().equals("33")) {
				status = props.getProperty("33");
			} else if (status.trim().equals("4")) {
				status = props.getProperty("4");
			} else if (status.trim().equals("1")) {
				status = props.getProperty("1");
			} else if (status.trim().equals("2")) {
				status = props.getProperty("2");
			} else if (status.trim().equals("8")) {
				status = props.getProperty("8");
			}else if (status.trim().equals("m8")) {
				status = props.getProperty("m8");
			}  
			else if (status.trim().equals("16")) {
				status = props.getProperty("16");
			} else if (status.trim().equals("m16")) {
				status = props.getProperty("m16");
			} else if (status.trim().equals("m8")) {
				status = props.getProperty("m8");
			}
		}
		return status;
	}
	
	public String getSmsStatus(String smsid,String type){
		 String status="";
		SmsManager sm=new SmsManager();
		
		List<Sms> smsList=sm.getSmsList("smsid", smsid);
		Collections.sort(smsList, new myComparator());
		
			Sms sms=(Sms)smsList.get(0);
			if("c".equalsIgnoreCase(type.trim())){
				status=getClientStatusMessage(sms.getStatus().trim(), myTimeIn(sms.getDatetime()).toString());
			}
	       if("a".equalsIgnoreCase(type.trim())){
	    	   status=getAdminStatusMessage(sms.getStatus().trim(),sms.getSendinginterface().getKannelname() ,myTimeIn(sms.getDatetime()).toString());
	    		
			}
	      
		return status;

	}
	public  String myTimeIn(Date date){
		String timezone=props.getProperty("timezone");
		//	log.info("Time Zone from property file::"+timezone);
		String target_time_zone="GMT+2:00";
		String format="yyyy-MM-dd HH:mm:ss";
		
		TimeZone tz = TimeZone.getTimeZone(target_time_zone);
	        
	     //   Date date = Calendar.getInstance().getTime();
	        SimpleDateFormat date_format_gmt = new SimpleDateFormat(format);
	        try {
				date_format_gmt.parse(date.toString());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        date_format_gmt.setTimeZone(tz);
	        
	        return date_format_gmt.format(date);
	    }
	
	
	public String getAdminStatusMessage(String status1,String gateway_name,String date){
		String message="";
		String status=status1.trim();
		//System.out.print("status::"+status);
		//String testMessage=String.format(props.getProperty("a11"),date);
		//System.out.print("format string ::"+testMessage);
//		String.format(props.getProperty("a11"), "ultimatesms","19 oct 3:16");
		if (status.equals("11")) {
		//	status = props.getProperty("a11");
			message="<font color='#0000FF'>"+String.format(props.getProperty("a11"),date)+"</font>";
			
		}else if (status.equals("22")) {
			message="<font color='#0000FF'>"+String.format(props.getProperty("a22"), gateway_name,date)+"</font>";
			
		}else if (status.equals("33")) {
			message="<font color='#0000FF'>"+String.format(props.getProperty("a33"), gateway_name,date)+"</font>";
			
		} else if (status.equals("4")) {
			message="<font color='#0000FF'>"+String.format(props.getProperty("a4"), gateway_name)+"</font>";
			
		}else if (status.equals("1")) {
			message="<font color='#5DAC0E'>"+String.format(props.getProperty("a1"),status, gateway_name,date)+"</font>";
			
		} else if (status.equals("2")) {
			message="<font color='#0000FF'>"+String.format(props.getProperty("a2"),status, gateway_name,date)+"</font>";
			
		}  else if ((status).equals("8")) {
			message="<font color='#0000FF'>"+String.format(props.getProperty("a8"), gateway_name)+"</font>";
			
		} else if ((status).equals("16")) {
			message="<font color='#0000FF'>"+String.format(props.getProperty("a16"), gateway_name,date)+"</font>";
			
		} else if ((status).equals("m16")) {
			message="<font color='#0000FF'>"+String.format(props.getProperty("am16"), gateway_name,date)+"</font>";
		
	    } else if (status.equals("m8")) {
	    	message="<font color='#0000A0'>"+String.format(props.getProperty("am8"), gateway_name,date)+"</font>";
	
	    }
		//log.info("return :: "+message);
		return message;
	}
	
	
	public String getClientStatusMessage(String status1,String date){
		String message="";
		String status=status1.trim();
		//System.out.print("status::"+status);
		//String testMessage=String.format(props.getProperty("a11"),date);
		//System.out.print("format string ::"+testMessage);
		//String.format(props.getProperty("c11"), "ultimatesms","19 oct 3:16");
		if (status.equals("11")) {
			message="<font color='#0000FF'>"+String.format(props.getProperty("a11"),date)+"</font>";
		} else if (status.equals("4")) {
			message="<font color='#0000FF'>"+String.format(props.getProperty("c4"))+"</font>";
			
		}else if (status.equals("1")) {
			message="<font color='#5DAC0E'>"+String.format(props.getProperty("c1"),date)+"</font>";
			
		} else if (status.equals("2")) {
			message="<font color='#0000FF'>"+String.format(props.getProperty("c2"),date)+"</font>";
			
		}  else if (status.equals("8")) {
			message="<font color='#0000FF'>"+String.format(props.getProperty("c8"))+"</font>";
			
		} else if (status.equals("16")) {
			message="<font color='#0000FF'>"+String.format(props.getProperty("c16"))+"</font>";
			
		} else if (status.equals("m16")) {
			message="<font color='#0000FF'>"+String.format(props.getProperty("cm16"))+"</font>";
		
	    } else if (status.equals("m8")) {
	    	message="<font color='#0000A0'>"+String.format(props.getProperty("cm8"),date)+"</font>";

	    }
	    else if(status.equals("88")) {
	    	message="<font color='#0000A0'>Message not processed</font>";

	    }
	    else if(status.equals("99")) {
	    	message="<font color='#0000A0'>Message not processed</font>";

	    }
		//log.info("return :: "+message);
		return message;
	}
	/***
	 * This method will return reference to Properties object. 
	 */
	public static Properties props;
	public static Properties getProps(){
		FileInputStream in=null;
		try {
			/*
			 * For local
			 */
			//props.load(new FileInputStream("/home/varsha/workspacejb/experimental_new/src/main/resources/ultimatesms.properties"));
			if(props==null){
				props=new Properties();

				 in=new FileInputStream("/opt/jboss7/standalone/ultimatesms.properties");
				 
				 //in=new FileInputStream("/home/webwerks/ultimatesms.properties");

			//	 in=new FileInputStream("/usr/local/jboss-as-7.1.2.Final-SNAPSHOT/standalone/configuration/ultimatesms.properties");

			    props.load(in);
			    in.close();
			}
			} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			if(in!=null){
				 try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return props;
	}
	
	public boolean isPreesentSenderId(long clientManagerId){
		boolean isPresent=false;
		SessionFactory sessionFactory=null;
		 Session session=null;
		try{
		  sessionFactory = HibernateUtil.getSessionFactory();
			 session=sessionFactory.openSession();
			Clientmanager cm=(Clientmanager)session.load(Clientmanager.class, clientManagerId);
			Sendinginterface sn=cm.getSendinginterface();
			if("Smscproxyproperty".equals(sn.getInterfacepojoname().trim())){
				Smscproxyproperty spp=sn.getSmscproxyproperty();
				isPresent=spp.isAlphanumericadd();
				
			}
		}catch (Exception e) {
			// TODO: handle exception
			isPresent=false;
			e.printStackTrace();
		}
		finally{
			session.close();
			session=null;
		}
		return isPresent;
	}
	/***
	 * This method will make the list of submap to sort on particular key.
	 * @param map
	 * @param key   On which column i want to sort, that column is key to map
	 * @param type  Is Ascending order or the descending order
	 * @return
	 */
	
	
	public Map SortMap(Map<Integer, Object> map, String key, String type) {
		Map mainMap=new LinkedHashMap();  
		Iterator it = map.entrySet().iterator();
		  List list=new LinkedList();
		  while(it.hasNext()){
			  Map.Entry pairs=(Map.Entry)it.next();
			  list.add(pairs.getValue());
		  }
		  System.out.println("list::"+list);
		 List sortedList= sortByValue(list,key,type);
		 Iterator itr= sortedList.iterator();
		 int count=1;
		 while(itr.hasNext()){
			 Map subMap=(Map)itr.next();
			 mainMap.put(count++, subMap);
		 }
		 return mainMap;
	}
	/***
	 * Sort the map According to specified column.
	 * list-----> list of the map, That i want to sort.
	 * @param list
	 * @param key---> on which column i want to sort,that column is key to map
	 * @param type ---> Is Ascending order or the descending order
	 * @return
	 */
     public List sortByValue(List<Map> list,final String key,String type) {
		
		if(type.equalsIgnoreCase("asc")){
		Collections.sort(list, new Comparator() {
		
		public int compare(Object o1, Object o2) {
		//return ((Comparable) ((Map) (o1)).get(key)).compareTo(((Map) (o2)).get(key));
		return (   ((Long)  (((Map) o1).get(key)) < (Long) (((Map) o2).get(key))  ) ? -1 : ( ((Long)((Map) o1).get(key)) <  (Long)(((Map) o2).get(key)) ? 0 : 1 )   );

		}
		});
		}
		
		if(type.equalsIgnoreCase("desc")){
			Collections.sort(list, new Comparator() {
			
			public int compare(Object o1, Object o2) {
			//return ((Comparable) ((Map) (o1)).get(key)).compareTo(((Map) (o2)).get(key));
			return (   ((Long)  (((Map) o1).get(key)) > (Long) (((Map) o2).get(key))  ) ? -1 : ( ((Long)((Map) o1).get(key)) >  (Long)(((Map) o2).get(key)) ? 0 : 1 )   );

			}
			});
			}
		// logger.info(list);
		List result = new LinkedList();
		for (Iterator it = list.iterator(); it.hasNext();) {
		Map entry = (Map)it.next();
		result.add(entry);
		}
		return result;
		} 
	
}

class myComparator implements Comparator<Sms>{

	@Override
	public int compare(Sms o1, Sms o2) {
		// TODO Auto-generated method stub
		return ( (o1.getPksmsid()>o2.getPksmsid()) ? -1 : ((o1.getPksmsid()>o2.getPksmsid()) ? 0 : 1));
		   		   
	}
	
	
}