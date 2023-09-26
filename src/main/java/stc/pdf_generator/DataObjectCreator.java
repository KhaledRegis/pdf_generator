package stc.pdf_generator;

import java.io.ByteArrayInputStream;
import java.io.Reader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DataObjectCreator {
	private static final Logger logger = LoggerFactory.getLogger(DataObjectCreator.class);
	final static SimpleDateFormat sdfThreadName = new SimpleDateFormat("ddMMyy.HHmmss.SSSS");
	
	

	public DataObject getDataObjectECA(String arg1, String arg2){
		DataObject obj = new DataObject();

		// logger.info("getXMLData method start");
		ArrayList<String> attri = new ArrayList<String>();

		ArrayList<String> parent = new ArrayList<String>();
		// System.out.println("Inside getXMLData");
		try {
			Reader reader = null; // -
			// Reader reader = new InputStreamReader(new
			// FileInputStream("C:\\Users\\2000554\\Downloads\\AllPDFs&XMLs\\Amendment.xml"),
			// "utf-8");
			// Reader reader = new InputStreamReader(new
			// FileInputStream("C:\\Users\\2000554\\Downloads\\AllPDFs&XMLs\\BulkT1.xml"),
			// "utf-8");
			// Reader reader = new InputStreamReader(new
			// FileInputStream("C:\\Users\\2000554\\Downloads\\AllPDFs&XMLs\\DCBulk.xml"),
			// "utf-8");
			// Reader reader = new InputStreamReader(new
			// FileInputStream("C:\\Users\\2000554\\Downloads\\AllPDFs&XMLs\\BulkGSM.xml"),
			// "utf-8");
			// Reader reader = new InputStreamReader(new
			// FileInputStream("C:\\Users\\2000554\\Downloads\\AllPDFs&XMLs\\quote1.xml"),
			// "utf-8");
			// Reader reader = new InputStreamReader(new
			// FileInputStream("C:\\Users\\2000554\\Downloads\\AllPDFs&XMLs\\Quote
			// XML_Common.xml"), "utf-8");

			// BufferedReader bf = new BufferedReader(reader);//--
			org.w3c.dom.Document doc = null;
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			String xml_data = "";
			int nol = 0;

//			String xml_data = "";
//			int nol = 0;

			/*
			 * StringBuilder sb = new StringBuilder(); while ((xml_data = bf.readLine()) !=
			 * null) { nol = nol + 1; sb.append(xml_data); }
			 */
			// String xml = sb.toString(); //-
			String xml = arg1;
//			System.out.println("Thread ::" + Thread.currentThread().getName());
//			System.out.println("Template / Entity :: " + arg2);
//			System.out.println("Input XML :: " + xml);
//			logger.info("Template / Entity :: " + arg2);
//			logger.info("Input XML :: " + xml);

//			Reader reader1 = new InputStreamReader(new ByteArrayInputStream(xml.getBytes("UTF-8")));
//			InputSource is = new InputSource(reader1);
//			
//			InputStream templateInputStream = classLoader.getResourceAsStream("pdf_template.html");
//            BufferedReader templateReader = new BufferedReader(new InputStreamReader(templateInputStream));
//            String template = templateReader.lines().collect(Collectors.joining("\n"));

//			DocumentBuilder db = dbf.newDocumentBuilder();

			// InputSource is = new InputSource();
//			is.setCharacterStream(new StringReader(xml));

			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();

			logger.info(xml);
			ByteArrayInputStream input = new ByteArrayInputStream(xml.getBytes());

			doc = builder.parse(input);

//			doc = db.parse(is);
			doc.getDocumentElement().normalize();
			org.w3c.dom.Element root = doc.getDocumentElement();
			// System.out.println(root.getNodeName());

			NodeList nList;

			nList = doc.getElementsByTagName("*");
			// here added khaled
			if (arg2.contentEquals("ECA"))
				nList = doc.getElementsByTagName("*");

			// System.out.println("============================"+nList.getLength());

			// for (int temp = 0; temp < nList.getLength(); temp++)
			// {
			// Node node = nList.item(temp);
			// //System.out.println(""); //Just a separator
			// if (node.hasChildNodes())
			// {
			// NodeList nList1 = node.getChildNodes();
			// for (int temp1 = 0; temp1 < nList1.getLength(); temp1++)
			// {
			// Node node1 = nList1.item(temp1);
			// if (node1.getNodeType() == Node.ELEMENT_NODE)
			// {
			// xmlData.put(node1.getNodeName(), node1.getTextContent());
			//
			// if ( (arg2.contentEquals("ECA") ))
			// {
			// if (node1.getTextContent() == null ||node1.getTextContent().length() == 0)
			// throw new CustomInternalException(ErrorMessageEnum.MANDATORY_TAG, "");
			// }
			//
			// }
			// }
			// }
			// }

			if (arg2.contentEquals("ECA"))
				nList = doc.getElementsByTagName("ListOfSTCECAAgreementBIPIO");

			// System.out.println("MRC==" + nList.getLength());
			// logger.info("MRC Records =====" + nList.getLength());
			String value1 = null;

			for (int temp = 0; temp < nList.getLength(); temp++) {
				logger.info(String.valueOf(temp));
				Node node = nList.item(temp);
				// System.out.println(""); //Just a separator
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					// System.out.println("Inside");
					// Print each employee's detail
					org.w3c.dom.Element eElement = (org.w3c.dom.Element) node;

					// System.out.println("Inside");

					// add new type here khaled
					if (arg2.contentEquals("ECA")) {
						// System.out.println("First Name-- : " +
						// eElement.getElementsByTagName("AliasNameMain").item(0).getTextContent());
						obj.setSTCMasterAccountName(
								(eElement.getElementsByTagName("STCMasterAccountName").item(0).getTextContent()));
						obj.setOutstandingAmount(
								(eElement.getElementsByTagName("OutstandingAmount").item(0).getTextContent()));
						obj.setDownPayment((eElement.getElementsByTagName("DownPayment").item(0).getTextContent()));
						obj.setCommercialRegistrationNumber((eElement
								.getElementsByTagName("STCCommercialRegistrationNumber").item(0).getTextContent()));
						obj.setNoofInstallment(
								eElement.getElementsByTagName("NoofInstallment").item(0).getTextContent());
						obj.setAgreementNumber(
								(eElement.getElementsByTagName("AgreementNumber").item(0).getTextContent()));
						if (eElement.getElementsByTagName("STCContractDuration").item(0) != null) {
							obj.setSTCContractDuration(
									(eElement.getElementsByTagName("STCContractDuration").item(0).getTextContent()));
						}
						if (eElement.getElementsByTagName("STCAgreementCreated").item(0) != null) {
							obj.setSTCAgreementCreated(
									(eElement.getElementsByTagName("STCAgreementCreated").item(0).getTextContent()));
						}

						int itemsize = eElement.getElementsByTagName("ListOfStcPayment").getLength();
						NodeList nListpayment;
						nListpayment = eElement.getElementsByTagName("ListOfStcPayment");
						ArrayList<StcPaymentModel> payments = new ArrayList<StcPaymentModel>();
						for (int tempint = 0; tempint < nListpayment.getLength(); tempint++) {
							Node nodepayment = nListpayment.item(tempint);

							// System.out.println(""); //Just a separator
							if (nodepayment.getNodeType() == Node.ELEMENT_NODE) {
								org.w3c.dom.Element eElementtmp = (org.w3c.dom.Element) nodepayment;
								System.out.println(eElementtmp.getElementsByTagName("PaymentAmount2").getLength());
								for (int i = 0; i < eElementtmp.getElementsByTagName("PaymentAmount2")
										.getLength(); i++) {
									payments.add(new StcPaymentModel(
											eElementtmp.getElementsByTagName("PaymentAmount2").item(i).getTextContent(),
											eElementtmp.getElementsByTagName("PaymentNumber").item(i).getTextContent(),
											eElementtmp.getElementsByTagName("STCHijriPaymentDate").item(i)
													.getTextContent()));
								}

								// System.out.println(eElementtmp.getElementsByTagName("PaymentAmount2").item(0).getTextContent());

							}
						}

						// eElementtemp.getElementsByTagName("StcPayment");

						obj.setStcPayment(payments);
						//
						// obj.setStcPayment(ArrayList<>new
						// StcPaymentModel().setPaymentAmount2((eElement.getElementsByTagName("STCCommercialRegistrationNumber").item(0).getTextContent())));
						// obj.setSTCMasterAccountName((eElement.getElementsByTagName("CSN").item(0).getTextContent()));
						// obj.setSTCMasterAccountName((eElement.getElementsByTagName("PaymentAmount2").item(0).getTextContent()));

					}

					// System.out.println("dataObjMRCList size :" +dataObjMRCList.size());
					// System.out.println("dataObjMRCList data :" +dataObjMRCList.);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return obj;

	}

}
