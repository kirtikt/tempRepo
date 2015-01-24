package com.ecomm.ultimatesms.messaging.downloadreportAPI;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

import jxl.CellView;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.UnderlineStyle;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import com.ecomm.pl4sms.persistence.dbutilsCRUD.HoldingaccountManager;

public class WriteExcelForMap {

	//Logger log=LoggerFactory.getLogger(WriteExcelForSummaryReport.class);
	private WritableCellFormat timesBoldUnderline;
	private WritableCellFormat times;
	private String inputFile;

	public void setOutputFile(String inputFile) {
		this.inputFile = inputFile;
	}

	public void write(Map map) {
		try{
		File file = new File(inputFile);
		WorkbookSettings wbSettings = new WorkbookSettings();
		wbSettings.setLocale(new Locale("en", "EN"));
		WritableWorkbook workbook = Workbook.createWorkbook(file, wbSettings);
		workbook.createSheet("Report", 0);
		WritableSheet excelSheet = workbook.getSheet(0);

		createLabel(excelSheet,map);
		
	//	if (list.get(0) instanceof Sms) {
		//	log.info("Summary report");
			createContent(excelSheet, map);
	//	} 
		workbook.write();
		workbook.close();}
		catch (WriteException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		catch( IOException e1 ){
			e1.printStackTrace();
		}
	}
	public void write(Map map,String type) {
		try{
		File file = new File(inputFile);
		WorkbookSettings wbSettings = new WorkbookSettings();
		wbSettings.setLocale(new Locale("en", "EN"));
		WritableWorkbook workbook = Workbook.createWorkbook(file, wbSettings);
		workbook.createSheet("Report", 0);
		WritableSheet excelSheet = workbook.getSheet(0);

		createLabel(excelSheet,map);
		
	//	if (list.get(0) instanceof Sms) {
		//	log.info("Summary report");
			createContent(excelSheet, map ,type);
	//	} 
		workbook.write();
		workbook.close();}
		catch (WriteException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		catch( IOException e1 ){
			e1.printStackTrace();
		}
	}
/***
 * 
 * @param sheet
 * @param map
 * @throws WriteException
 */
	private void createLabel(WritableSheet sheet,Map map) throws WriteException {
		// Lets create a times font
		WritableFont times10pt = new WritableFont(WritableFont.TIMES, 10);
		// Define the cell format
		times = new WritableCellFormat(times10pt);
		// Lets automatically wrap the cells
		times.setWrap(true);

		// Create create a bold font with unterlines
		WritableFont times10ptBoldUnderline = new WritableFont(
				WritableFont.TIMES, 10, WritableFont.BOLD, false,
				UnderlineStyle.SINGLE);
		timesBoldUnderline = new WritableCellFormat(times10ptBoldUnderline);
		// Lets automatically wrap the cells
		timesBoldUnderline.setWrap(true);

		CellView cv = new CellView();
		cv.setFormat(times);
		cv.setFormat(timesBoldUnderline);
		// cv.setSize(d)Autosize(true);

		// Write a few headers
		
		System.out.println("Adding lable");
		int count=0;
		 addCaption(sheet, count++, 0, "Record");
        Iterator ithd=map.entrySet().iterator();
		while(ithd.hasNext()){
			Map.Entry pairs=(Map.Entry)ithd.next();
			Map subMap=(Map)pairs.getValue();
			Iterator subIthd = subMap.entrySet().iterator();
			while(subIthd.hasNext()){
			
				Map.Entry subpairs1=(Map.Entry)subIthd.next();
				
				 addCaption(sheet, count++, 0, subpairs1.getKey().toString().trim());
				
	     }
			break;
	     }
	}

//	private void createContent(WritableSheet sheet, List list)
//			throws WriteException, RowsExceededException {
//		// Now a bit of text
//		for (int i = 0; i < list.size(); i++) {
//			// First column
//			addLabel(sheet, 0, i, (String) list.get(i));
//		}
//	}

	private void createContent(WritableSheet sheet, Map map,String type)
			throws WriteException, RowsExceededException {
		// Now a bit of text
		
		
		  Iterator it=map.entrySet().iterator();
		  int outercount=1;
			while(it.hasNext()){
				int count = 0;
				Map.Entry pairs=(Map.Entry)it.next();
				Map subMap=(Map)pairs.getValue();
				if("day".equals(type.trim())){
					addLabel(sheet, count++, outercount, pairs.getKey().toString().trim());
					
					//out.println("day /"+pairs.getKey());
				}
				else{
					addLabel(sheet, count++, outercount, pairs.getKey().toString().trim());
					
				}
				Iterator subIt1 = subMap.entrySet().iterator();
				while(subIt1.hasNext()){
					Map.Entry subpairs1=(Map.Entry)subIt1.next();
               //  out.print(subpairs1.getValue());
                 addLabel(sheet, count++, outercount, subpairs1.getValue().toString().trim());
				}  
				outercount++;
			}
			

	}
	private void createContent(WritableSheet sheet, Map map)
			throws WriteException, RowsExceededException {
		// Now a bit of text
		
		
		  Iterator it=map.entrySet().iterator();
		  int outercount=1;
			while(it.hasNext()){
				int count = 0;
				Map.Entry pairs=(Map.Entry)it.next();
				Map subMap=(Map)pairs.getValue();
				
					addLabel(sheet, count++, outercount, pairs.getKey().toString().trim());
					
					//out.println("day /"+pairs.getKey());
				
				Iterator subIt1 = subMap.entrySet().iterator();
				while(subIt1.hasNext()){
					Map.Entry subpairs1=(Map.Entry)subIt1.next();
               //  out.print(subpairs1.getValue());
                 addLabel(sheet, count++, outercount, subpairs1.getValue().toString().trim());
				}  
				outercount++;
			}
	}

	private void addLabel(WritableSheet sheet, int column, int row, String s)
			throws WriteException, RowsExceededException {
		Label label;
		label = new Label(column, row, s, times);
		sheet.addCell(label);
	}
	private void addCaption(WritableSheet sheet, int column, int row, String s)
			 throws RowsExceededException, WriteException {
			 Label label;
			 label = new Label(column, row, s, timesBoldUnderline);
			 sheet.addCell(label);
			 }
	
//	public static void main(String[] args) throws WriteException, IOException {
//		
//		WriteExcelForMap test=new WriteExcelForMap();
//		
//		test.setOutputFile("/home/varsha/batch.xls");
//	//	test.write(map);
//		System.out.println("Done");
//		
//	}
	
//	
//	public Map createMap(){
//		String month="";
//	 Map map=new LinkedHashMap();
//		for(int i=1;i<=10;i++){
//			 Map  smsCountMap=getMapSmsCountForClientSummaryRep();
//				if(i==1){
//					month="January";
//				}
//				if(i==2){
//					month="February";
//				}
//				if(i==3){
//					month="March";
//				}
//				if(i==4){
//					month="April";
//				}
//				if(i==5){
//					month="May";
//				}
//				if(i==6){
//					month="June";
//				}
//				if(i==7){
//					month="July";
//				}
//				if(i==8){
//					month="August";
//				}
//				if(i==9){
//					month="September";
//				}
//				if(i==10){
//					month="October";
//				}
//				if(i==11){
//					month="November";
//				}
//				if(i==12){
//					month="December";
//				}
//				map.put(month,smsCountMap);
//		}
// return map;
//	}
//	
//	public Map<String, Object> getMapSmsCountForClientSummaryRep() {
//
//		
//		Map<String, Object> smsMapCount = new LinkedHashMap<String, Object>();
//
//		smsMapCount.put("Sms Loaded",10);
//
//		smsMapCount.put("processed",20);
//
//		smsMapCount.put("m8",30);
//
//		smsMapCount.put("m16",40);
//
//		smsMapCount.put("1",50);
//
//		smsMapCount.put("2",60);
//
//	//	log.info("smssent ::" + smssent);
//
//		return smsMapCount;
//	}

}

//	public static void main(String[] args) throws WriteException, IOException {
//		WriteExcel test = new WriteExcel();
//		List<Simplereport> DistinctSmsIdList= new DatabaseHelper().getDistinctSmsIdList();
//		test.setOutputFile("lars.xls");
//		test.write(DistinctSmsIdList);
//		log.info("Please check the result file under lars.xls ");
//	}
//}

 

// private void addNumber(WritableSheet sheet, int column, int row,
// Integer integer) throws WriteException, RowsExceededException {
// Number number;
//
// number = new Number(column, row, integer, times);
// sheet.addCell(number);
// }

