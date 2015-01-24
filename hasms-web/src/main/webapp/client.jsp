<%@page import="net.sf.json.JSONObject"%>
<%@page import="java.util.List"%>
<%@page import="com.ecomm.ultimatesms.messaging.com.ecomm.pl4sms.persistence.entities.Clientmanager"%>
<%@page import="java.util.Iterator"%>
<%@page import="com.ecomm.pl4sms.persistence.dao.ClientManagersManagerDAO"%>

<%
								ClientmanagersManager manager = new ClientmanagersManager();
								Long mid = null;
								if (request.getAttribute("managerid") != null) {
									mid = (Long) request.getAttribute("managerid");
								}
								System.out.println("managerid:" + mid);
								List<Clientmanager> cltList = manager.getList();
								Iterator<Clientmanager> it1 = cltList.iterator();
								 JSONObject object=new JSONObject();
								 
								 
								while (it1.hasNext()) {
									Clientmanager sr = (Clientmanager) it1.next();
									 
									 object.put(sr.getPkclientmanagerid(),sr.getSysuser().getUserid());
								}
								response.setContentType("application/json");
								response.getWriter().write(object.toString());
							%>
