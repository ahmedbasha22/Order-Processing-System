package application.jasper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import application.model.Driver;
import application.model.DriverImp;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

public class JasperReports {
	private Driver driver;
	
	public JasperReports() {
		super();
		try {
			driver = DriverImp.getInstance();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void getBookSalesPreviousMonth() throws SQLException{
		createReport(new ArrayList<Object>(driver.getBookSalesPreviousMonth()),"C:\\Users\\Ahmed\\git\\Order Processing System\\src\\application\\jasper/res/Books Sales.jrxml" ,"Books Sales");
	}

	public void getTop5Users() throws SQLException{
		createReport(new ArrayList<Object>(driver.getTop5Users()),"C:\\Users\\Ahmed\\git\\Order Processing System\\src\\application\\jasper/res/Top 5 Users.jrxml" ,"Top 5 Users");
	}	
	
	public void getTop10SoldBooks() throws SQLException{
		createReport(new ArrayList<Object>(driver.getTop10SoldBooks()),"C:\\Users\\Ahmed\\git\\Order Processing System\\src\\application\\jasper/res/Top 10 Selling Books.jrxml" ,"Top 10 Selling Books");
	}
	
	private void createReport(List<Object> data, String jasperDesignPath, String output) {
		
		/* Output file location to create report in pdf form */
		Date currentDate = new Date(Calendar.getInstance().getTimeInMillis());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH.mm.ss");

		String outputFile = output + " " + sdf.format(currentDate) + ".pdf";

		/* Convert List to JRBeanCollectionDataSource */
		JRBeanCollectionDataSource itemsJRBean = new JRBeanCollectionDataSource(data);

		/* Map to hold Jasper report Parameters */
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("ds", itemsJRBean);

		JasperDesign jasperDesign;
		try {

			jasperDesign = JRXmlLoader.load(new FileInputStream(new File(jasperDesignPath).getCanonicalPath()));

			/* compiling jrxml with help of JasperReport class */
			JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);

			/* Using jasperReport object to generate PDF */
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());

			/* outputStream to create PDF */
			OutputStream outputStream = new FileOutputStream(new File(outputFile));

			/* Write content to PDF file */
			JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);

		} catch (JRException | IOException e) {
			e.printStackTrace();
		}
	}
}
