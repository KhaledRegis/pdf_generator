package stc.pdf_generator;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.jhonnymertz.wkhtmltopdf.wrapper.exceptions.PDFExportException;

public class Runner {

	final static Logger logger = LoggerFactory.getLogger(Runner.class);
	static DataObject obj = null;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ClassLoader classLoader = Runner.class.getClassLoader();
		try {
			//

			Reader reader =
					// new InputStreamReader(new FileInputStream("C:\\Users\\Khali\\Documents\\test
					// data\\PDF-ECA-Test2.xml"), "utf-8");
					new InputStreamReader(new FileInputStream("C:\\Users\\Khali\\Documents\\test data\\Amendment5.xml"),
							"utf-8");
			// InputStreamReader(new
			// FileInputStream("C:\\Users\\2000554\\Downloads\\AllPDFs&XMLs\\IO
			// xml\\BIPNewSaleOrderFinal.xml"), "utf-8");
			// InputStreamReader(new FileInputStream("C:\\Users\\Khali\\Documents\\test
			// data\\testfileamendment.xml"), "utf-8");
			// InputStreamReader(new FileInputStream("C:\\Users\\Khali\\Documents\\test
			// data\\PDF-ECA-Test2.xml"), "utf-8");
			// InputStreamReader(new
			// FileInputStream("/siebelapp/serverlogs/catilina_out_logs/DCPdf/Data/Test.xml"),
			// "utf-8");
			// 3-45211739431_DC
			// InputStreamReader(new
			// FileInputStream("C:\\Users\\2000554\\Downloads\\AllPDFs&XMLs\\TAXINVOICE\\3-47890446499.xml"),
			// "utf-8");
			BufferedReader bf = new BufferedReader(reader);
			StringBuilder sb = new StringBuilder();
			String xml_data = "";
			int nol = 0;
			while ((xml_data = bf.readLine()) != null) {
				nol = nol + 1;
				sb.append(xml_data);
			}
			String arg1 = sb.toString();
//        	//
			InputStream templateInputStream = classLoader.getResourceAsStream("pdf_template.html");
			BufferedReader templateReader = new BufferedReader(
					new InputStreamReader(templateInputStream, StandardCharsets.UTF_8));
			String template = templateReader.lines().collect(Collectors.joining("\n"));
//            
//           InputStream xmlInputStream = classLoader.getResourceAsStream("pdfXml.txt");
//         BufferedReader xmlReader = new BufferedReader(new InputStreamReader(xmlInputStream,StandardCharsets.UTF_8));
//         String xml = xmlReader.lines().collect(Collectors.joining("\n"));

			new xmlReader().getXMLData(arg1, "Amendment");
//            System.out.println(template);

			obj = new DataObjectCreator().getDataObjectECA(arg1, "");

			System.out.println(xmlReader.xmlData.get("STCCustomerAccountName"));
			template = template.replace("{{CustomerName}}", xmlReader.xmlData.get("STCCustomerAccountName"));
			template = template.replace("{{CustomerID}}", xmlReader.xmlData.get("STCCommercialRegisterationNumber")!=null ? xmlReader.xmlData.get("STCCommercialRegisterationNumber") : "");
    template = template.replace("{{CRDate}}", xmlReader.xmlData.get("STCCRRegistrationDate")!=null ? xmlReader.xmlData.get("STCCRRegistrationDate") : "");
    template = template.replace("{{CRCity}}", xmlReader.xmlData.get("STCCustomerAccountCity")!=null ? xmlReader.xmlData.get("STCCustomerAccountCity") : "");

			template = template.replace("{{BuildingNumber}}", xmlReader.xmlData.get("Building"));
			template = template.replace("{{Street}}", xmlReader.xmlData.get("StreetNum"));
			   template = template.replace("{{Neighboorhood}}", xmlReader.xmlData.get("Neighbourhood")!=null ? xmlReader.xmlData.get("Neighbourhood") : "");
			   template = template.replace("{{City}}", xmlReader.xmlData.get("AccCity")!=null ? xmlReader.xmlData.get("AccCity") : "");
			   template = template.replace("{{PostalCode}}", xmlReader.xmlData.get("AccZipCode")!=null ? xmlReader.xmlData.get("AccZipCode") : "");
			   template = template.replace("{{AdditionalNo}}", xmlReader.xmlData.get("AlternateNum")!=null ? xmlReader.xmlData.get("AlternateNum") : "");

//			template = template.replace("{{Neighboorhood}}", xmlReader.xmlData.get("Neighbourhood"));
//			template = template.replace("{{PostalCode}}", xmlReader.xmlData.get("AccZipCode"));
//			template = template.replace("{{AdditionalNo}}", xmlReader.xmlData.get("AlternateNum"));
			
//            logger.info();

//            OutputStream OutStream = new FileOutputStream("pdf.pdf");

			logger.info("Creating PDF File...");
			// OutStream.write(new PDFGenerator().getPDF(template));
			logger.info("Size is {} Bytes", new PDFGenerator().getPDF(template).length);
			logger.info("Done!");
		} catch (NullPointerException e) {
			e.printStackTrace();
		} catch (PDFExportException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
