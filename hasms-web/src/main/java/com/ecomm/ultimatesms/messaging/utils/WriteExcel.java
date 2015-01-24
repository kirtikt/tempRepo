package com.ecomm.ultimatesms.messaging.utils;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

import com.ecomm.ultimatesms.messaging.persistence.pojos.Sms;

public class WriteExcel {

	Logger log=LoggerFactory.getLogger(WriteExcel.class);
	private WritableCellFormat timesBoldUnderline;
	private WritableCellFormat times;
	private String inputFile;

	public void setOutputFile(String inputFile) {
		this.inputFile = inputFile;
	}

	public void write(List list) {
		try{
		File file = new File(inputFile);
		WorkbookSettings wbSettings = new WorkbookSettings();
		wbSettings.setLocale(new Locale("en", "EN"));
		WritableWorkbook workbook = Workbook.createWorkbook(file, wbSettings);
		workbook.createSheet("Report", 0);
		WritableSheet excelSheet = workbook.getSheet(0);

		createLabel(excelSheet);
		
	//	if (list.get(0) instanceof Sms) {
			log.info("Simple report");
			createContentSimpleReport(excelSheet, list);
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

	private void createLabel(WritableSheet sheet) throws WriteException {
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
		 addCaption(sheet, 0, 0, "UserId");
		 addCaption(sheet, 1, 0, "Destination");
		 addCaption(sheet, 2, 0, "Source");
		 addCaption(sheet, 3, 0, "TimeDate");
		 addCaption(sheet, 4, 0, "SmsId");
		 addCaption(sheet, 5, 0, "Message");
		 addCaption(sheet, 6, 0, "status");

	}

//	private void createContent(WritableSheet sheet, List list)
//			throws WriteException, RowsExceededException {
//		// Now a bit of text
//		for (int i = 0; i < list.size(); i++) {
//			// First column
//			addLabel(sheet, 0, i, (String) list.get(i));
//		}
//	}

	private void createContentSimpleReport(WritableSheet sheet, List<Sms> list)
			throws WriteException, RowsExceededException {
		// Now a bit of text
		for (int i = 0; i < list.size(); i++) {
			// First column
			Sms sm = (Sms) list.get(i);
			int a=i+1;
			addLabel(sheet, 0, a, (String) sm.getClientmanager().getSysuser().getUserid());
			addLabel(sheet, 1, a, (String) sm.getDestination());
			addLabel(sheet, 2, a, (String) sm.getSource());
			addLabel(sheet, 3, a, (String) (sm.getDatetime()).toString());
			addLabel(sheet, 4, a, (String) sm.getSmsid());
			addLabel(sheet, 5, a, (String) sm.getMessage());
			//new Helper().getStatusMessage(sm.getStatus())
			addLabel(sheet, 6, a, (String) new Helper().getStatusMessage(sm.getStatus()));	
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

