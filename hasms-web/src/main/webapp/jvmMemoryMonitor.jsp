<%@page import="javax.management.MBeanServer"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.lang.management.*" %>
<%@ page import="java.util.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta HTTP-EQUIV="REFRESH" CONTENT="1">
    <title>JVM Memory Monitor</title>
    <style type="text/css">
      td {
        text-align: right;
      }
    </style>
  </head>
<body>
<%
		if (request.getSession(false) == null
				|| request.getSession(false).getAttribute("isActive") == null
				|| "null".equals(request.getSession(false).getAttribute(
						"isActive")) ||  "null".equals(request.getSession(false).getAttribute(
								"isAdmin")) || request.getSession(false).getAttribute("isAdmin")==null) {
		
			response.sendRedirect("/payless4sms/adminLogin.jsp?errorMessage=please login");
			return;
		}
%>
  <%
    MemoryMXBean memoryBean = ManagementFactory.getMemoryMXBean();
    pageContext.setAttribute("memoryBean", memoryBean);
    ClassLoadingMXBean classLoadingMXBean=ManagementFactory.getClassLoadingMXBean();
    pageContext.setAttribute("classLoadingMXBean", classLoadingMXBean);
    CompilationMXBean compilationMXBean= ManagementFactory.getCompilationMXBean();
    pageContext.setAttribute("compilationMXBean", compilationMXBean);
    OperatingSystemMXBean operatingSystemMXBean= ManagementFactory.getOperatingSystemMXBean();
    pageContext.setAttribute("operatingSystemMXBean", operatingSystemMXBean);
    MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
    pageContext.setAttribute("mBeanServer", mBeanServer);
    RuntimeMXBean runtimeMXBean= ManagementFactory.getRuntimeMXBean();
    pageContext.setAttribute("runtimeMXBean", runtimeMXBean);
    ThreadMXBean threadMXBean= ManagementFactory.getThreadMXBean();
    
    pageContext.setAttribute("threadMXBean", threadMXBean);
    List<MemoryPoolMXBean> poolBeans = ManagementFactory.getMemoryPoolMXBeans();
    pageContext.setAttribute("poolBeans", poolBeans);
    List<GarbageCollectorMXBean> garbageCollectorMXBeans= ManagementFactory.getGarbageCollectorMXBeans();
    pageContext.setAttribute("garbageCollectorMXBeans", garbageCollectorMXBeans);
    List<MemoryManagerMXBean> memoryManagerMXBeans= ManagementFactory.getMemoryManagerMXBeans();
    pageContext.setAttribute("memoryManagerMXBeans", memoryManagerMXBeans);
    
  %>
   
<h1 style="text-decoration: underline;">JVM Memory Monitor</h1>
  <h3>Total Memory</h3>
  <table border="1" width="100%">
    <tr>
      <th>usage</th>
      <th>init</th>
      <th>used</th>
      <th>committed</th>
      <th>max</th>
    </tr>
    <tr>
      <td style="text-align: left">Heap Memory Usage</td>
      <td><fmt:formatNumber value="${memoryBean.heapMemoryUsage.init / (1024 * 1024)}" maxFractionDigits="1"/> Mb</td>
      <td><fmt:formatNumber value="${memoryBean.heapMemoryUsage.used / (1024 * 1024)}" maxFractionDigits="1"/> Mb</td>
      <td><fmt:formatNumber value="${memoryBean.heapMemoryUsage.committed / (1024 * 1024)}" maxFractionDigits="1"/> Mb</td>
      <td><fmt:formatNumber value="${memoryBean.heapMemoryUsage.max / (1024 * 1024)}" maxFractionDigits="1"/> Mb</td>
    </tr>
    <tr>
      <td style="text-align: left">Non-heap Memory Usage</td>
      <td><fmt:formatNumber value="${memoryBean.nonHeapMemoryUsage.init / (1024 * 1024)}" maxFractionDigits="1"/> Mb</td>
      <td><fmt:formatNumber value="${memoryBean.nonHeapMemoryUsage.used / (1024 * 1024)}" maxFractionDigits="1"/> Mb</td>
      <td><fmt:formatNumber value="${memoryBean.nonHeapMemoryUsage.committed / (1024 * 1024)}" maxFractionDigits="1"/> Mb</td>
      <td><fmt:formatNumber value="${memoryBean.nonHeapMemoryUsage.max / (1024 * 1024)}" maxFractionDigits="1"/> Mb</td>
    </tr>
  </table>
  <hr>
  <h3>Class Loading</h3>
  <table border="1" width="40%">
    <tr>
      <th>LoadedClassCount</th>
      <td><fmt:formatNumber value="${classLoadingMXBean.loadedClassCount}"/></td></tr>
     <tr> <th>TotalLoadedClassCount</th>
      <td><fmt:formatNumber value="${classLoadingMXBean.totalLoadedClassCount}"/></td></tr>
      <tr><th>UnloadedClassCount</th>
      <td><fmt:formatNumber value="${classLoadingMXBean.unloadedClassCount}"/></td></tr>
  </table>
  <hr>
  <h3>Compilation Details</h3>
  <table border="1" width="50%">
    <tr>
      <th>Name</th>
      <td>${compilationMXBean.name}</td></tr>
     <tr> <th>Total Compilation Time</th>
      <td><fmt:formatNumber value="${compilationMXBean.totalCompilationTime/1000}"/> sec.</td></tr>
      <tr><th>compilation Time Monitoring Supported</th>
      <td>${compilationMXBean.compilationTimeMonitoringSupported}</td></tr>
  </table>
  <hr>
  <h3>Operating System</h3>
  <table border="1" width="50%">
    <tr>
      <th>Name</th>
      <td>${operatingSystemMXBean.name}</td></tr>
      <tr> <th>architecture</th>
      <td>${operatingSystemMXBean.arch}</td></tr>
     <tr> <th>Version</th>
      <td>${operatingSystemMXBean.version}</td></tr>
      <tr><th>available processor</th>
      <td>${operatingSystemMXBean.availableProcessors}</td></tr>
      <tr><th>System Load Average</th>
      <td>${operatingSystemMXBean.systemLoadAverage}</td></tr>
  </table><hr>
  <h3>Runtime Details</h3>
  	<h4>Boot Class Path:-</h4>${runtimeMXBean.bootClassPath}
	<h4>Class Path:-</h4>${runtimeMXBean.classPath}
	<h4>Input Arguments:-</h4>${runtimeMXBean.inputArguments}
	<h4>Library Path:-</h4>${runtimeMXBean.libraryPath}
	<h4>Management Spec Version:-</h4>${runtimeMXBean.managementSpecVersion}
	<h4>Name:-</h4>${runtimeMXBean.name}
	<h4>Spec Name:-</h4>${runtimeMXBean.specName}
	<h4>Spec Vendor:-</h4>${runtimeMXBean.specVendor}
   
  <hr>
		
  <h3>Memory Pools</h3>
  <table border="1" width="100%">
    <tr>
      <th>name</th>
      <th>usage</th>
      <th>init</th>
      <th>used</th>
      <th>committed</th>
      <th>max</th>
    </tr>
    <c:forEach var="bean" items="${poolBeans}">
      <tr>
        <td style="text-align: left">${bean.name}</td>
        <td style="text-align: left">Memory Usage</td>
        <td><fmt:formatNumber value="${bean.usage.init / (1024 * 1024)}" maxFractionDigits="1"/> Mb</td>
        <td><fmt:formatNumber value="${bean.usage.used / (1024 * 1024)}" maxFractionDigits="1"/> Mb</td>
        <td><fmt:formatNumber value="${bean.usage.committed / (1024 * 1024)}" maxFractionDigits="1"/> Mb</td>
        <td><fmt:formatNumber value="${bean.usage.max / (1024 * 1024)}" maxFractionDigits="1"/> Mb</td>
      </tr>
      <tr>
        <td></td>
        <td style="text-align: left">Peak Usage</td>
        <td><fmt:formatNumber value="${bean.peakUsage.init / (1024 * 1024)}" maxFractionDigits="1"/> Mb</td>
        <td><fmt:formatNumber value="${bean.peakUsage.used / (1024 * 1024)}" maxFractionDigits="1"/> Mb</td>
        <td><fmt:formatNumber value="${bean.peakUsage.committed / (1024 * 1024)}" maxFractionDigits="1"/> Mb</td>
        <td><fmt:formatNumber value="${bean.peakUsage.max / (1024 * 1024)}" maxFractionDigits="1"/> Mb</td>
      </tr>
    </c:forEach>
    </table>
    <hr>
    <h3>Garbage Collection </h3>
    <table border="1">
    <tr>
		<th>Name</th>
       	<th>Garbage Collection Count</th>
       	<th>Collection Time</th>
       	<th>Memory Pools</th>
      </tr>
    <c:forEach var="garbageCollectorMXBean" items="${garbageCollectorMXBeans}">
      <tr>
	    <td>${garbageCollectorMXBean.name}</td>
    	<td><fmt:formatNumber value="${garbageCollectorMXBean.collectionCount}"/></td>
        <td><fmt:formatNumber value="${garbageCollectorMXBean.collectionTime}"/>ms.</td>
         <td><c:forEach var="name" items="${garbageCollectorMXBean.memoryPoolNames}">
         ${name},
         </c:forEach></td>
      </tr>
    </c:forEach>   
  </table>
	    <%-- <%= threadMXBean.findDeadlockedThreads()%>;
	    <%= threadMXBean.findMonitorDeadlockedThreads()%>; --%>
	    <hr>
  <h3>Thread Details</h3>
  <table border="1">
  <tr>
  	<th>Current Thread CPU Time</th>
  	<td><fmt:formatNumber value="${threadMXBean.currentThreadCpuTime/1000}"/> sec</td>
  </tr>
  <tr>
  	<th>Current Thread User Time</th>
  	<td><fmt:formatNumber value="${threadMXBean.currentThreadUserTime/1000}"/> sec</td>
  </tr>
  <tr>
  	<th>Daemon Thread Count</th>
  	<td>${threadMXBean.daemonThreadCount}</td>
  </tr>
  <tr>
  	<th>Peak Thread Count</th>
  	<td>${threadMXBean.peakThreadCount}</td>
  </tr>
  <tr>
  	<th>Thread Count</th>
  	<td>${threadMXBean.threadCount}</td>
  </tr>
  <tr>
  	<th>Total Started Thread Count</th>
  	<td>${threadMXBean.totalStartedThreadCount}</td>
  </tr>
  
</table>
<h3>Thread Details</h3>
  <table border="1">
  <tr>
  <th>All Threads</th>
  </tr>
  <%
  long[] threadIDs=threadMXBean.getAllThreadIds();
  ThreadInfo[] threadDataset = threadMXBean.getThreadInfo(threadIDs);
  for (ThreadInfo threadData : threadDataset) {
      if (threadData != null) {
    	  out.print("<tr><td>"+threadData.getThreadName()+"</td></tr>");
      }
  }
  %>
  </table>
</body>