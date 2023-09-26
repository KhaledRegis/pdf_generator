package stc.pdf_generator;

import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.itextpdf.text.Font;
import com.test.pdf.util.CustomInternalException;
import com.test.pdf.util.ErrorMessageEnum;


public class xmlReader {
private final static String[] HEADER_ARRAY = {"S.No.", "CompanyName", "Income", "Year"};
	

	
	public static  HashMap<String , String> xmlData = new HashMap<String, String>() ; 
	
	public static DataObject ECAData;
	
	public static Properties diascowprop;
	
	//slf4J related
	private static final Logger logger = LoggerFactory.getLogger(xmlReader.class);
	final static SimpleDateFormat sdfThreadName = new SimpleDateFormat("ddMMyy.HHmmss.SSSS");
	//static  Logger logger = Logger.getLogger(PDFCreator.class.getName());

	
	long threadId = Thread.currentThread().getId();
	String threadName = "DCTID."+threadId+"."+sdfThreadName.format(new Date());
	
	
	public final static Properties p=new Properties(); 
	public static List<DataObject> dataObjMRCList = new ArrayList<DataObject>();
	public static  List<DataObject> dataObjNRCList = new ArrayList<DataObject>();
	public static  List<DataObject> dataObjVASList = new ArrayList<DataObject>();
	public static List<DataObject> dataObjDEVList = new ArrayList<DataObject>();
	public static List<DataObject> dataObjNewList = new ArrayList<DataObject>();																   
	public static List<String> stcPhoneNumDispList = new ArrayList<String>();
	public static HashMap<String, String> stcSvcMap = new HashMap<String,String>();
	public static HashMap<String, String> stcSvcBandWthMap = new HashMap<String,String>();
	public static HashMap<String, String> invoiceTaxDvcMap= new HashMap<String,String>();
	public double totalVat = 0.0 ; 
	public boolean zeroTaxRec = false ; 
	public boolean bulkDCBoo = false ;
	public BigDecimal discNrcTot = BigDecimal.ZERO , discVasTot = BigDecimal.ZERO ;
	public BigDecimal discMrcTot = BigDecimal.ZERO; 
	public boolean serviceProviderBoo = false ; 

	public  HashMap<String , String> getXMLData(String arg1, String arg2)

	{
		//logger.info("getXMLData method start");
		ArrayList<String> attri = new ArrayList<String>();
		
		ArrayList<String> parent = new ArrayList<String>();
		//System.out.println("Inside getXMLData");
		try {
			Reader reader = null ; //-
			//Reader reader = new InputStreamReader(new FileInputStream("C:\\Users\\1983845\\Downloads\\AllPDFs&XMLs\\Bulk.xml"), "utf-8");
			//Reader reader = new InputStreamReader(new FileInputStream("C:\\Users\\2000554\\Downloads\\AllPDFs&XMLs\\BulkT1.xml"), "utf-8");
			//Reader reader = new InputStreamReader(new FileInputStream("C:\\Users\\2000554\\Downloads\\AllPDFs&XMLs\\DCBulk.xml"), "utf-8");
			//Reader reader = new InputStreamReader(new FileInputStream("C:\\Users\\2000554\\Downloads\\AllPDFs&XMLs\\BulkGSM.xml"), "utf-8");
			//Reader reader = new InputStreamReader(new FileInputStream("C:\\Users\\2000554\\Downloads\\AllPDFs&XMLs\\quote1.xml"), "utf-8");
			//Reader reader = new InputStreamReader(new FileInputStream("C:\\Users\\2000554\\Downloads\\AllPDFs&XMLs\\Quote XML_Common.xml"), "utf-8");

			//BufferedReader bf = new BufferedReader(reader);//--
			org.w3c.dom.Document doc = null;
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			String xml_data = "";
			int nol = 0;

			/*StringBuilder sb = new StringBuilder();
			while ((xml_data = bf.readLine()) != null) {
				nol = nol + 1;
				sb.append(xml_data);
			}*/
			//String xml = sb.toString(); //-
			String xml = arg1;
			System.out.println("Thread ::"+Thread.currentThread().getName());
			System.out.println("Template / Entity :: "+arg2);
			System.out.println("Input XML :: "+xml);
			logger.info("Template / Entity :: "+arg2);
			logger.info("Input XML :: "+xml);
			
			Reader reader1 = new InputStreamReader(new ByteArrayInputStream(
					xml.getBytes("UTF-8")));
			InputSource is = new InputSource(reader1);

			DocumentBuilder db = dbf.newDocumentBuilder();

			//InputSource is = new InputSource();
			is.setCharacterStream(new StringReader(xml));
			doc = db.parse(is);
			doc.getDocumentElement().normalize();
			org.w3c.dom.Element root = doc.getDocumentElement();
			//System.out.println(root.getNodeName());

			NodeList nList;
			if(arg2.contentEquals("Bulk"))
				nList = doc.getElementsByTagName("*");
			else if(arg2.contentEquals("Physical-Bulk"))
				nList = doc.getElementsByTagName("*");
			else if (arg2.contentEquals("Quote"))
				nList = doc.getElementsByTagName("*");
			else if (arg2.contentEquals("Physical-Quote"))
				nList = doc.getElementsByTagName("*");
			else
				nList = doc.getElementsByTagName("*");
			//System.out.println("============================"+nList.getLength());
			
			NodeList diascow = null;
			if(arg2.contentEquals("Amendment")){
				diascow=	doc.getElementsByTagName("StcAmendementOrderItemNew1");
			}else if(arg2.contentEquals("Quote")) {
				diascow=	doc.getElementsByTagName("STCQuoteItemNew1");
				
			}
			else {
				diascow=	doc.getElementsByTagName("STCOrderItemNew1");
			}
			
			if(diascow!=null) {
				diascowprop = new Properties();
				for(int x=0;x<diascow.getLength();x++) {
					System.out.println(diascow.item(0).getNodeName().equals("STCOrderItemNew1"));
					
					Node node = diascow.item(x);
					//	System.out.println("");    //Just a separator
						if (node.getNodeType() == Node.ELEMENT_NODE)
						{
							org.w3c.dom.Element eElement = (org.w3c.dom.Element) node;
							DataObject obj = new DataObject();
							
							if(	eElement.getElementsByTagName("STCCOWContract").item(0).getTextContent()!=null && !eElement.getElementsByTagName("STCCOWContract").item(0).getTextContent().equalsIgnoreCase("")) {
								diascowprop.setProperty("STCCOWContract", eElement.getElementsByTagName("STCCOWContract").item(0).getTextContent());
							}
							if(	eElement.getElementsByTagName("COWServiceNumber").item(0).getTextContent()!=null  && !eElement.getElementsByTagName("COWServiceNumber").item(0).getTextContent().equalsIgnoreCase("")) {
								diascowprop.setProperty("COWServiceNumber", eElement.getElementsByTagName("COWServiceNumber").item(0).getTextContent());
							}
							if(	eElement.getElementsByTagName("STCDIASDuration").item(0).getTextContent()!=null && !eElement.getElementsByTagName("STCDIASDuration").item(0).getTextContent().equalsIgnoreCase("")) {
								diascowprop.setProperty("STCDIASDuration", eElement.getElementsByTagName("STCDIASDuration").item(0).getTextContent());
							}
							if(	eElement.getElementsByTagName("STCDIASEndDate").item(0).getTextContent()!=null && !eElement.getElementsByTagName("STCDIASEndDate").item(0).getTextContent().equalsIgnoreCase("")) {
								diascowprop.setProperty("STCDIASEndDate", eElement.getElementsByTagName("STCDIASEndDate").item(0).getTextContent());
							}
							if(	eElement.getElementsByTagName("STCDIASStartDate").item(0).getTextContent()!=null && !eElement.getElementsByTagName("STCDIASStartDate").item(0).getTextContent().equalsIgnoreCase("")) {
								diascowprop.setProperty("STCDIASStartDate",eElement.getElementsByTagName("STCDIASStartDate").item(0).getTextContent());
							}
							
							
					
							
						}
					
				}
			}
			
			System.out.println(diascowprop.get("STCCOWContract"));
			System.out.println(diascowprop.get("COWServiceNumber"));
			System.out.println(diascowprop.get("STCDIASDuration"));
			System.out.println(diascowprop.get("STCDIASEndDate"));
			System.out.println(diascowprop.get("STCDIASStartDate"));
			
			for (int temp = 0; temp < nList.getLength(); temp++)
			{
				Node node = nList.item(temp);
				//System.out.println("");    //Just a separator
				if (node.hasChildNodes()) 
				{
					NodeList nList1 = node.getChildNodes();
					for (int temp1 = 0; temp1 < nList1.getLength(); temp1++)
					{
						Node node1 = nList1.item(temp1);
						if (node1.getNodeType() == Node.ELEMENT_NODE)
						{
							xmlData.put(node1.getNodeName(), node1.getTextContent());
							//System.out.println("TAG ==>"+node1.getNodeName());
							if (node1.getNodeName().equals("STCPhoneNumberDisp"))
								stcPhoneNumDispList.add(node1.getTextContent());
							if ( (arg2.contentEquals("Quote") && node1.getNodeName().equals("QuoteNumber"))
									|| (arg2.contentEquals("Bulk") && node1.getNodeName().equals("BulkRequestNumber") )
									||(arg2.contentEquals("Amendment") && node1.getNodeName().equals("OrderNumber"))
									||(arg2.contentEquals("") && node1.getNodeName().equals("OrderNumber")
											||(arg2.contentEquals("Physical-Quote") && node1.getNodeName().equals("QuoteNumber")
													|| (arg2.contentEquals("Physical-Bulk") && node1.getNodeName().equals("BulkRequestNumber"))
									)))
							{   
								if (node1.getTextContent() == null ||node1.getTextContent().length() == 0)
								  throw new CustomInternalException(ErrorMessageEnum.MANDATORY_TAG, "");
							}
							if (node1.getNodeName().equals("AutomatedOrder"))
							{
							if (node1.getTextContent().length() > 0 && ("eSIM Automated".equalsIgnoreCase(node1.getTextContent()) || "AutomatedOrder".equalsIgnoreCase(node1.getTextContent())))
							serviceProviderBoo = true ; 
							}
						}
					}
				}
			}
			if (arg2.contentEquals("Amendment") || arg2.contentEquals("Physical-Amendment"))
				nList = doc.getElementsByTagName("StcOrderItemMain");
			else if(arg2.contentEquals("Bulk"))
			{
				nList = doc.getElementsByTagName("Stcbulkmainsvcmrcdc");
				if (nList.getLength() == 0)
					nList = doc.getElementsByTagName("Stcbulkmainsvcmrc");
			}else if(arg2.contentEquals("Physical-Bulk"))
			{
				nList = doc.getElementsByTagName("Stcbulkmainsvcmrcdc");
				if (nList.getLength() == 0)
					nList = doc.getElementsByTagName("Stcbulkmainsvcmrc");
			}
			else if (arg2.contentEquals("Quote"))
				nList = doc.getElementsByTagName("Stcquotemainsvcmrc");
			else if (arg2.contentEquals("Physical-Quote"))
				nList = doc.getElementsByTagName("Stcquotemainsvcmrc");
			else if (arg2.contentEquals("AllInOne"))
				nList = doc.getElementsByTagName("OrderEntry-LineItems");
			else
				nList = doc.getElementsByTagName("Stcordermainsvcmrc");

			//System.out.println("MRC==" + nList.getLength());
			//logger.info("MRC Records =====" + nList.getLength());
			String value1 = null;

													 
														  
					   
			for (int temp = 0; temp < nList.getLength(); temp++)
			{
				Node node = nList.item(temp);
			//	System.out.println("");    //Just a separator
				if (node.getNodeType() == Node.ELEMENT_NODE)
				{
					//System.out.println("Inside");
					//Print each employee's detail
					org.w3c.dom.Element eElement = (org.w3c.dom.Element) node;
					DataObject obj = new DataObject();
					
					NodeList DIASCOW = eElement.getElementsByTagName("STCOrderItemNew1");;
		
					 
					//System.out.println("Inside");
					if (arg2.equals("AllInOne")) {
						value1 = eElement.getElementsByTagName("STCDeviceModelName").item(0).getTextContent();
						obj.setDevModel(value1 != null ? value1 : "");

						value1 = eElement.getElementsByTagName("STCSCMDeviceMemory").item(0).getTextContent();
						obj.setDevMemory(value1 != null ? value1 : "");

						value1 = eElement.getElementsByTagName("STCSCMDeviceColor").item(0).getTextContent();
						obj.setColor(value1 != null ? value1 : "");

						obj.setQty("1");

						value1 = eElement.getElementsByTagName("STCSCMDeviceIMEI").item(0).getTextContent();
						obj.setImei(value1 != null ? value1 : "");

						value1 = eElement.getElementsByTagName("STCSCMDeviceCost").item(0).getTextContent();
						BigDecimal cost = BigDecimal.ZERO;
						if (value1 != null) {
							cost = new BigDecimal(value1);
						}
						obj.setDeviceCost("" + cost);

						value1 = eElement.getElementsByTagName("STCSCMVAT").item(0).getTextContent();
						BigDecimal vat = BigDecimal.ZERO;
						if (value1 != null) {
							vat = new BigDecimal(value1);
						}

						BigDecimal vatAmt = BigDecimal.ZERO;
						if (cost != BigDecimal.ZERO && vat != BigDecimal.ZERO) {
							vatAmt = cost.multiply(vat).divide(new BigDecimal("100")).setScale(3,RoundingMode.HALF_UP);
						}
						obj.setVat("" + vatAmt);
	  
	  
																				 
										  
						  logger.info(" AllInOne Entity  data ::"+",Devmodel "+obj.getDevModel()
						  +",Devmomery "+obj.getDevMemory()+
						  ",Color "+obj.getColor()+",Qty "+obj.getQty()+",IMEi "+obj.getImei()+
						  " ,cost "+obj.getDeviceCost()+" ,Vat "+obj.getVat());
															 
	   
					}
					else if (arg2.contentEquals("Amendment") || arg2.contentEquals("Physical-Amendment"))
					{
						//System.out.println("Amendment MRC First Name : "  + eElement.getElementsByTagName("AliasNameMain").item(0).getTextContent());
						if(arg2.equalsIgnoreCase("Physical-Amendment"))
							obj.setRootProduct(eElement.getElementsByTagName("MainRootName").item(0).getTextContent());
						else
						obj.setRootProduct(eElement.getElementsByTagName("AliasNameMain").item(0).getTextContent());
						//System.out.println("1");
						obj.setProductName(eElement.getElementsByTagName("AliasNameMain").item(0).getTextContent());
						//System.out.println("2");
						if(eElement.getElementsByTagName("BandwidthMainCalc").item(0).getTextContent() != null)
							obj.setBandWidthSpeed(eElement.getElementsByTagName("BandwidthMainCalc").item(0).getTextContent());
						else
							obj.setBandWidthSpeed("");
						//System.out.println("3");
						if(eElement.getElementsByTagName("SerialNumCalcMain").item(0) != null)
						{
							//System.out.println("3.1");
						obj.setServiceNum(eElement.getElementsByTagName("SerialNumCalcMain").item(0).getTextContent());
						}
						else
						{
						//	System.out.println("3");
						obj.setServiceNum("");
						}
						//System.out.println("4");
						//obj.setServiceNum(eElement.getElementsByTagName("SerialNumCalcMain").item(0).getTextContent());
						
						if(eElement.getElementsByTagName("NetPriceDisplayMain").item(0)!=null)
						obj.setStartPriceMRC(eElement.getElementsByTagName("NetPriceDisplayMain").item(0).getTextContent());
						if(eElement.getElementsByTagName("NetPriceDisplayMainVAT").item(0)!=null)
						obj.setStartPriceMRCVAT(eElement.getElementsByTagName("NetPriceDisplayMainVAT").item(0).getTextContent());
						//System.out.println("5");
						//obj.setExtendedNetrice(eElement.getElementsByTagName("ExtPriceMain").item(0).getTextContent());
						if(eElement.getElementsByTagName("STCContractDurationMain").item(0).getTextContent() != null && eElement.getElementsByTagName("STCContractDurationMain").item(0).getTextContent() .length() > 0)
							obj.setCommitDuration(eElement.getElementsByTagName("STCContractDurationMain").item(0).getTextContent());
						else
							obj.setCommitDuration("");
	  
	  
																		
									   
						  logger.info(" Amendment Entity MRC Data ::"+"Root product "+obj.
						  getRootProduct()+",Prod Name "+
						  obj.getProductName()+",Bandwidth Speed "+obj.getBandWidthSpeed()
						  +",Service Num "+
						  obj.getServiceNum()+",Start Price MRC "+obj.getStartPriceMRC()+",C Duration"
						  +obj.getCommitDuration());
						 
					}
					else if(arg2.contentEquals("Bulk"))
					{
						//System.out.println("BULK MRC First Name : "  + eElement.getElementsByTagName("NameMRC").item(0).getTextContent());
						if(eElement.getElementsByTagName("AliasNameMRC").item(0).getTextContent() != null)
							obj.setRootProduct(eElement.getElementsByTagName("AliasNameMRC").item(0).getTextContent());
						else
							obj.setRootProduct(eElement.getElementsByTagName("").item(0).getTextContent());

						if(eElement.getElementsByTagName("AliasNameMRC").item(0).getTextContent() != null)
							obj.setProductName(eElement.getElementsByTagName("AliasNameMRC").item(0).getTextContent());
						else
							obj.setProductName(eElement.getElementsByTagName("").item(0).getTextContent());

						if(eElement.getElementsByTagName("BandwidthCalcMRC").item(0).getTextContent() != null)
							obj.setBandWidthSpeed(eElement.getElementsByTagName("BandwidthCalcMRC").item(0).getTextContent());
						else
							obj.setBandWidthSpeed("");
						if(eElement.getElementsByTagName("AliasNameMRC").item(0).getTextContent() != null && eElement.getElementsByTagName("BandwidthCalcMRC").item(0).getTextContent() != null)
					//	stcSvcBandWthMap.put(eElement.getElementsByTagName("RootServiceNumber").item(0).getTextContent(), eElement.getElementsByTagName("BandwidthCalcMRC").item(0).getTextContent() );
							
						obj.setQty(eElement.getElementsByTagName("QuantityMRC").item(0).getTextContent());

						if(eElement.getElementsByTagName("NetPriceMRC").item(0)!=null)
						obj.setStartPriceMRC(eElement.getElementsByTagName("NetPriceMRC").item(0).getTextContent());
						if(eElement.getElementsByTagName("NetPriceMRCVAT").item(0)!=null)
						obj.setStartPriceMRCVAT(eElement.getElementsByTagName("NetPriceMRCVAT").item(0).getTextContent());


						if(eElement.getElementsByTagName("ExtPriceMRC").item(0)!=null)
						obj.setExtendedNetPrice(eElement.getElementsByTagName("ExtPriceMRC").item(0).getTextContent());
						if(eElement.getElementsByTagName("ExtPriceMRCVAT").item(0)!=null)
						obj.setExtendedNetPriceVAT(eElement.getElementsByTagName("ExtPriceMRCVAT").item(0).getTextContent());


						if(eElement.getElementsByTagName("ContractDurationMain").getLength() > 0 )
							obj.setCommitDuration(eElement.getElementsByTagName("ContractDurationMain").item(0).getTextContent());
						else if(eElement.getElementsByTagName("ContractDurationMRC").getLength() > 0 )
							obj.setCommitDuration(eElement.getElementsByTagName("ContractDurationMRC").item(0).getTextContent());
						else
							obj.setCommitDuration("");
						
						  logger.info("Bulk Entity MRC Data :: "+"Root product "+obj.getRootProduct()
						  +",Prod Name "+
						  obj.getProductName()+",Bandwidth Speed "+obj.getBandWidthSpeed()
						  +",Service Num "+
						  obj.getServiceNum()+",Start Price MRC "+obj.getStartPriceMRC()
						  +",Extended NetPrice"+
						  obj.getExtendedNetPrice()+",C Duration"+obj.getCommitDuration());
						 

					}
					else if(arg2.contentEquals("Physical-Bulk"))
					{
						//System.out.println("BULK MRC First Name : "  + eElement.getElementsByTagName("NameMRC").item(0).getTextContent());
						if(eElement.getElementsByTagName("AliasNameMRC").item(0).getTextContent() != null)
							obj.setRootProduct(eElement.getElementsByTagName("AliasNameMRC").item(0).getTextContent());
						else
							obj.setRootProduct(eElement.getElementsByTagName("").item(0).getTextContent());

						if(eElement.getElementsByTagName("AliasNameMRC").item(0).getTextContent() != null)
							obj.setProductName(eElement.getElementsByTagName("AliasNameMRC").item(0).getTextContent());
						else
							obj.setProductName(eElement.getElementsByTagName("").item(0).getTextContent());

						if(eElement.getElementsByTagName("BandwidthCalcMRC").item(0).getTextContent() != null)
							obj.setBandWidthSpeed(eElement.getElementsByTagName("BandwidthCalcMRC").item(0).getTextContent());
						else
							obj.setBandWidthSpeed("");
						if(eElement.getElementsByTagName("AliasNameMRC").item(0).getTextContent() != null && eElement.getElementsByTagName("BandwidthCalcMRC").item(0).getTextContent() != null)
						stcSvcBandWthMap.put(eElement.getElementsByTagName("RootServiceNumber").item(0).getTextContent(), eElement.getElementsByTagName("BandwidthCalcMRC").item(0).getTextContent() );
						obj.setQty(eElement.getElementsByTagName("QuantityMRC").item(0).getTextContent());

						obj.setStartPriceMRC(eElement.getElementsByTagName("NetPriceMRC").item(0).getTextContent());

						obj.setExtendedNetPrice(eElement.getElementsByTagName("ExtPriceMRC").item(0).getTextContent());

						if(eElement.getElementsByTagName("ContractDurationMain").getLength() > 0 )
							obj.setCommitDuration(eElement.getElementsByTagName("ContractDurationMain").item(0).getTextContent());
						else if(eElement.getElementsByTagName("ContractDurationMRC").getLength() > 0 )
							obj.setCommitDuration(eElement.getElementsByTagName("ContractDurationMRC").item(0).getTextContent());
						else
							obj.setCommitDuration("");
						
						  logger.info("Bulk Entity MRC Data :: "+"Root product "+obj.getRootProduct()
						  +",Prod Name "+
						  obj.getProductName()+",Bandwidth Speed "+obj.getBandWidthSpeed()
						  +",Service Num "+
						  obj.getServiceNum()+",Start Price MRC "+obj.getStartPriceMRC()
						  +",Extended NetPrice"+
						  obj.getExtendedNetPrice()+",C Duration"+obj.getCommitDuration());
						 

					}
					else if (arg2.contentEquals("Quote"))
					{
						//System.out.println("First Name-- : "  + eElement.getElementsByTagName("AliasNameMain").item(0).getTextContent());
						obj.setRootProduct(eElement.getElementsByTagName("RootProductMain").item(0).getTextContent());
						obj.setProductName(eElement.getElementsByTagName("AliasNameMain").item(0).getTextContent());
						
						if(eElement.getElementsByTagName("BandwidthMain").item(0).getTextContent() != null)
							obj.setBandWidthSpeed(eElement.getElementsByTagName("BandwidthMain").item(0).getTextContent());
						else
							obj.setBandWidthSpeed("");
						
						if(eElement.getElementsByTagName("RootServiceNumber").item(0)!= null && eElement.getElementsByTagName("RootServiceNumber").item(0).getTextContent().length() > 0 && eElement.getElementsByTagName("BandwidthMain").item(0).getTextContent() != null )
						{
						//	System.out.println("32");
							stcSvcBandWthMap.put(eElement.getElementsByTagName("RootServiceNumber").item(0).getTextContent(), eElement.getElementsByTagName("BandwidthMain").item(0).getTextContent() );
						}
						obj.setQty(eElement.getElementsByTagName("QuantityMain").item(0).getTextContent());
						
						
						if(eElement.getElementsByTagName("NetPriceMain").item(0)!=null)
						obj.setStartPriceMRC(eElement.getElementsByTagName("NetPriceMain").item(0).getTextContent());
						if(eElement.getElementsByTagName("NetPriceMainVAT").item(0)!=null)
						obj.setStartPriceMRCVAT(eElement.getElementsByTagName("NetPriceMainVAT").item(0).getTextContent());

						if(eElement.getElementsByTagName("ExtNetPriceMain").item(0)!=null)
						obj.setExtendedNetPrice(eElement.getElementsByTagName("ExtNetPriceMain").item(0).getTextContent());
						if(eElement.getElementsByTagName("ExtNetPriceMainVAT").item(0)!=null)
						obj.setExtendedNetPriceVAT(eElement.getElementsByTagName("ExtNetPriceMainVAT").item(0).getTextContent());

						
						if(eElement.getElementsByTagName("ContractDurationMain").item(0).getTextContent() != null)
						{
						if (!eElement.getElementsByTagName("SystemNameMain").item(0).getTextContent().equalsIgnoreCase("CRMG"))	
							obj.setCommitDuration(eElement.getElementsByTagName("ContractDurationMain").item(0).getTextContent());
						else
							obj.setCommitDuration("");
						}
						else
						{
						obj.setCommitDuration("");
						}
						if(eElement.getElementsByTagName("DiscPercentMain").item(0)!=null)
						obj.setDiscPct(eElement.getElementsByTagName("DiscPercentMain").item(0).getTextContent()+"%");
						if(eElement.getElementsByTagName("DiscNetPriceMain").item(0)!=null)
						obj.setNetAfDisc(eElement.getElementsByTagName("DiscNetPriceMain").item(0).getTextContent());
						if(eElement.getElementsByTagName("DiscNetPriceMainVAT").item(0)!=null)
						obj.setVATNetAfDisc(eElement.getElementsByTagName("DiscNetPriceMainVAT").item(0).getTextContent());

						//System.out.println("34text0");
						if (eElement.getElementsByTagName("DiscPercentMain").item(0).getTextContent()!=null && eElement.getElementsByTagName("DiscPercentMain").item(0).getTextContent().length() > 0) // && eElement.getElementsByTagName("DiscPercentMain").item(0).getTextContent().length() > 0
						{	//System.out.println("34text00");
						discMrcTot = discMrcTot.add(new BigDecimal(eElement.getElementsByTagName("DiscPercentMain").item(0).getTextContent())) ;  
						}
						
						  logger.info("Quote Entity MRC Data :: "+"Root product "+obj.getRootProduct()
						  +",Prod Name "+
						  obj.getProductName()+",Bandwidth Speed "+obj.getBandWidthSpeed()+",Qty "+
						  obj.getQty()+",Service Num "+obj.getServiceNum()+",Start Price MRC "+obj.
						  getStartPriceMRC()+",Extended NetPrice"+
						  obj.getExtendedNetPrice()+",C Duration"+obj.getCommitDuration()+", Disc PCt"
						  +obj.getDiscPct()+ ",NetAF Disc "+obj.getNetAfDisc() );
						 

					}else if (arg2.contentEquals("Physical-Quote"))
					{
						//System.out.println("First Name-- : "  + eElement.getElementsByTagName("AliasNameMain").item(0).getTextContent());
						obj.setRootProduct(eElement.getElementsByTagName("RootProductMain").item(0).getTextContent());
						obj.setProductName(eElement.getElementsByTagName("AliasNameMain").item(0).getTextContent());
						
						if(eElement.getElementsByTagName("BandwidthMain").item(0).getTextContent() != null)
							obj.setBandWidthSpeed(eElement.getElementsByTagName("BandwidthMain").item(0).getTextContent());
						else
							obj.setBandWidthSpeed("");
						
						if(eElement.getElementsByTagName("RootServiceNumber").item(0)!= null && eElement.getElementsByTagName("RootServiceNumber").item(0).getTextContent().length() > 0 && eElement.getElementsByTagName("BandwidthMain").item(0).getTextContent() != null )
						{
						//	System.out.println("32");
							stcSvcBandWthMap.put(eElement.getElementsByTagName("RootServiceNumber").item(0).getTextContent(), eElement.getElementsByTagName("BandwidthMain").item(0).getTextContent() );
						}
						obj.setQty(eElement.getElementsByTagName("QuantityMain").item(0).getTextContent());
						
						obj.setStartPriceMRC(eElement.getElementsByTagName("NetPriceMain").item(0).getTextContent());
						
						obj.setExtendedNetPrice(eElement.getElementsByTagName("ExtNetPriceMain").item(0).getTextContent());
						
						if(eElement.getElementsByTagName("ContractDurationMain").item(0).getTextContent() != null)
						{
						if (!eElement.getElementsByTagName("SystemNameMain").item(0).getTextContent().equalsIgnoreCase("CRMG"))	
							obj.setCommitDuration(eElement.getElementsByTagName("ContractDurationMain").item(0).getTextContent());
						else
							obj.setCommitDuration("");
						}
						else
						{
						obj.setCommitDuration("");
						}
						
						obj.setDiscPct(eElement.getElementsByTagName("DiscPercentMain").item(0).getTextContent()+"%");
						obj.setNetAfDisc(eElement.getElementsByTagName("DiscNetPriceMain").item(0).getTextContent());
						//System.out.println("34text0");
						if (eElement.getElementsByTagName("DiscPercentMain").item(0).getTextContent()!=null && eElement.getElementsByTagName("DiscPercentMain").item(0).getTextContent().length() > 0) // && eElement.getElementsByTagName("DiscPercentMain").item(0).getTextContent().length() > 0
						{	//System.out.println("34text00");
						discMrcTot = discMrcTot.add(new BigDecimal(eElement.getElementsByTagName("DiscPercentMain").item(0).getTextContent())) ;  
						}
						
						  logger.info("Quote Entity MRC Data :: "+"Root product "+obj.getRootProduct()
						  +",Prod Name "+
						  obj.getProductName()+",Bandwidth Speed "+obj.getBandWidthSpeed()+",Qty "+
						  obj.getQty()+",Service Num "+obj.getServiceNum()+",Start Price MRC "+obj.
						  getStartPriceMRC()+",Extended NetPrice"+
						  obj.getExtendedNetPrice()+",C Duration"+obj.getCommitDuration()+", Disc PCt"
						  +obj.getDiscPct()+ ",NetAF Disc "+obj.getNetAfDisc() );
						 

					}
					else if(arg2.equalsIgnoreCase("Physical"))
					{
						NodeList listtest = eElement.getElementsByTagName("RootProductName");
						//System.out.println("First Name-- : "  + eElement.getElementsByTagName("AliasNameMain").item(0).getTextContent());
						if(eElement.getElementsByTagName("RootProductName").item(0)!=null)
						obj.setRootProduct(eElement.getElementsByTagName("RootProductName").item(0).getTextContent());
						
						
						obj.setProductName(eElement.getElementsByTagName("AliasNameMain").item(0).getTextContent());
						if(eElement.getElementsByTagName("BandwidthMain").item(0).getTextContent() != null)
							obj.setBandWidthSpeed(eElement.getElementsByTagName("BandwidthMain").item(0).getTextContent());
						else
							obj.setBandWidthSpeed("");
						if(eElement.getElementsByTagName("AliasNameMain").item(0).getTextContent() != null && eElement.getElementsByTagName("BandwidthMain").item(0).getTextContent() != null)
							stcSvcBandWthMap.put(eElement.getElementsByTagName("RootServiceNumber").item(0).getTextContent(), eElement.getElementsByTagName("BandwidthMain").item(0).getTextContent() );
						obj.setQty(eElement.getElementsByTagName("QuantityMain").item(0).getTextContent());
						
						if(eElement.getElementsByTagName("NetPriceMain").item(0)!=null)
						obj.setStartPriceMRC(eElement.getElementsByTagName("NetPriceMain").item(0).getTextContent());
						
						if(eElement.getElementsByTagName("NetPriceMainVAT").item(0)!=null)
						obj.setStartPriceMRCVAT(eElement.getElementsByTagName("NetPriceMainVAT").item(0).getTextContent());
						if(eElement.getElementsByTagName("ExtPriceMain").item(0)!=null)
						obj.setExtendedNetPrice(eElement.getElementsByTagName("ExtPriceMain").item(0).getTextContent());
						if(eElement.getElementsByTagName("ExtPriceMainVAT").item(0)!=null)
						obj.setExtendedNetPriceVAT(eElement.getElementsByTagName("ExtPriceMainVAT").item(0).getTextContent());

						if(eElement.getElementsByTagName("ContractDurationMain").item(0).getTextContent() != null)
							obj.setCommitDuration(eElement.getElementsByTagName("ContractDurationMain").item(0).getTextContent());
						else
							obj.setCommitDuration("");
					}
					else 
					{
						NodeList listtest = eElement.getElementsByTagName("RootProductName");
						//System.out.println("First Name-- : "  + eElement.getElementsByTagName("AliasNameMain").item(0).getTextContent());
						obj.setRootProduct(eElement.getElementsByTagName("RootProductName").item(0).getTextContent());
						obj.setProductName(eElement.getElementsByTagName("AliasNameMain").item(0).getTextContent());
						if(eElement.getElementsByTagName("BandwidthMain").item(0).getTextContent() != null)
							obj.setBandWidthSpeed(eElement.getElementsByTagName("BandwidthMain").item(0).getTextContent());
						else
							obj.setBandWidthSpeed("");
						if(eElement.getElementsByTagName("AliasNameMain").item(0).getTextContent() != null && eElement.getElementsByTagName("BandwidthMain").item(0).getTextContent() != null)
							stcSvcBandWthMap.put(eElement.getElementsByTagName("RootServiceNumber").item(0).getTextContent(), eElement.getElementsByTagName("BandwidthMain").item(0).getTextContent() );
						obj.setQty(eElement.getElementsByTagName("QuantityMain").item(0).getTextContent());
						
						if(eElement.getElementsByTagName("NetPriceMain").item(0)!=null)
						obj.setStartPriceMRC(eElement.getElementsByTagName("NetPriceMain").item(0).getTextContent());
						
						if(eElement.getElementsByTagName("NetPriceMainVAT").item(0)!=null)
						obj.setStartPriceMRCVAT(eElement.getElementsByTagName("NetPriceMainVAT").item(0).getTextContent());
						if(eElement.getElementsByTagName("ExtPriceMain").item(0)!=null)
						obj.setExtendedNetPrice(eElement.getElementsByTagName("ExtPriceMain").item(0).getTextContent());
						if(eElement.getElementsByTagName("ExtPriceMainVAT").item(0)!=null)
						obj.setExtendedNetPriceVAT(eElement.getElementsByTagName("ExtPriceMainVAT").item(0).getTextContent());

						if(eElement.getElementsByTagName("ContractDurationMain").item(0).getTextContent() != null)
							obj.setCommitDuration(eElement.getElementsByTagName("ContractDurationMain").item(0).getTextContent());
						else
							obj.setCommitDuration("");
					}
					dataObjMRCList.add(obj);
					//System.out.println("dataObjMRCList size :" +dataObjMRCList.size());
					//System.out.println("dataObjMRCList data :" +dataObjMRCList.);
				}
			}
			if (arg2.contentEquals("Amendment") || arg2.contentEquals("Physical-Amendment"))
				nList = doc.getElementsByTagName("StcOrderItemNrc");
			else if(arg2.contentEquals("Bulk"))
			{
				nList = doc.getElementsByTagName("Stcbulknrcvasdc");
				if (nList.getLength() == 0)
					nList = doc.getElementsByTagName("Stcbulknrcvas");
			}else if(arg2.contentEquals("Physical-Bulk"))
			{
				nList = doc.getElementsByTagName("Stcbulknrcvasdc");
				if (nList.getLength() == 0)
					nList = doc.getElementsByTagName("Stcbulknrcvas");
			}
			else if (arg2.contentEquals("Quote"))
				nList = doc.getElementsByTagName("Stcquotenrcvas");
			else if (arg2.contentEquals("Physical-Quote"))
				nList = doc.getElementsByTagName("Stcquotenrcvas");
			else
				nList = doc.getElementsByTagName("Stcordernrcvas");

			//System.out.println("NRC====" + nList.getLength());
			//logger.info("NRC Records ===================" + nList.getLength());

			for (int temp = 0; temp < nList.getLength(); temp++)
			{
				Node node = nList.item(temp);
				//System.out.println("");    //Just a separator
				if (node.getNodeType() == Node.ELEMENT_NODE)
				{
					//System.out.println("Inside");
					//Print each employee's detail
					org.w3c.dom.Element eElement = (org.w3c.dom.Element) node;
					DataObject obj = new DataObject();
					//System.out.println("Inside");
					if (arg2.contentEquals("Amendment") || arg2.contentEquals("Physical-Amendment"))
					{
						//System.out.println("Amendment NRC First Name : "  + eElement.getElementsByTagName("NRCRootName").item(0).getTextContent());
						if(arg2.contentEquals("Physical-Amendment"))
							obj.setRootProduct(eElement.getElementsByTagName("NRCRootName").item(0).getTextContent());
						else
						obj.setRootProduct(eElement.getElementsByTagName("AliasNameNRC").item(0).getTextContent());
						obj.setProductName(eElement.getElementsByTagName("AliasNameNRC").item(0).getTextContent());
						obj.setActionCode(eElement.getElementsByTagName("ActionCodeNRC").item(0).getTextContent());
						if(eElement.getElementsByTagName("BandwidthNRCCalc").item(0).getTextContent() != null)
							obj.setBandWidthSpeed(eElement.getElementsByTagName("BandwidthNRCCalc").item(0).getTextContent());
						else
							obj.setBandWidthSpeed("");
						
						//BandwidthNRC
						
						obj.setServiceNum(eElement.getElementsByTagName("SerialNumCalcNRC").item(0).getTextContent());
						
						
						if(eElement.getElementsByTagName("NetPrice").item(0).getTextContent()!=null) {
						//	if(eElement.getElementsByTagName("NetPrice").item(0).getTextContent().contains("-")) {
						//		obj.setStartPriceNRC("");
						//	}else {
								obj.setStartPriceNRC(eElement.getElementsByTagName("NetPrice").item(0).getTextContent());
						//	}
						}
							
						else {
							obj.setStartPriceNRC("");
						}
						
						if(eElement.getElementsByTagName("NetPriceDisplayNRCVAT").item(0).getTextContent()!=null) {
						//	if(eElement.getElementsByTagName("NetPriceDisplayNRCVAT").item(0).getTextContent().contains("-")) {
						//		obj.setStartPriceNRCVAT("");
						//	}else {
								obj.setStartPriceNRCVAT(eElement.getElementsByTagName("NetPriceDisplayNRCVAT").item(0).getTextContent());

						//	}
						}
							
						else {
							obj.setStartPriceNRCVAT("");
						}
						
						  logger.info(" Amendment Entity NRC Data ::"+"Root product "+obj.
						  getRootProduct()+",Prod Name "+
						  obj.getProductName()+",Action Code"+obj.getActionCode()+",Bandwidth Speed "
						  +obj.getBandWidthSpeed()+",Service Num "+
						  obj.getServiceNum()+",Start Price NRC "+obj.getStartPriceNRC());
						 
							
					}
					else if(arg2.contentEquals("Bulk"))
					{
						//System.out.println("NRC First Name : "  + eElement.getElementsByTagName("SpNumNRC").item(0).getTextContent());
						if(eElement.getElementsByTagName("AliasNameNRC").item(0).getTextContent() != null)
							obj.setRootProduct(eElement.getElementsByTagName("AliasNameNRC").item(0).getTextContent());
						else
							obj.setRootProduct(eElement.getElementsByTagName("").item(0).getTextContent());

						if(eElement.getElementsByTagName("AliasNameNRC").item(0).getTextContent() != null)
							obj.setProductName(eElement.getElementsByTagName("AliasNameNRC").item(0).getTextContent());
						else
							obj.setProductName(eElement.getElementsByTagName("").item(0).getTextContent());
						//obj.setProductName(eElement.getElementsByTagName("AliasNameNRC").item(0).getTextContent());
						obj.setActionCode(eElement.getElementsByTagName("ActionCodeNRC").item(0).getTextContent());
						if(eElement.getElementsByTagName("BandwidthCalcNRC").item(0).getTextContent() != null)
							obj.setBandWidthSpeed(eElement.getElementsByTagName("BandwidthCalcNRC").item(0).getTextContent());
						else
							obj.setBandWidthSpeed("");
						obj.setQty(eElement.getElementsByTagName("QuantityNRC").item(0).getTextContent());
						obj.setStartPriceNRC(eElement.getElementsByTagName("NetPriceNRC").item(0).getTextContent());
						obj.setStartPriceNRCVAT(eElement.getElementsByTagName("NetPriceNRCVAT").item(0).getTextContent());
						obj.setExtendedNetPrice(eElement.getElementsByTagName("ExtPriceNRC").item(0).getTextContent());
						obj.setExtendedNetPriceVAT(eElement.getElementsByTagName("ExtPriceNRCVAT").item(0).getTextContent());

						
						  logger.info(" Bulk Entity NRC Data ::"+"Root product "+obj.getRootProduct()
						  +",Prod Name "+
						  obj.getProductName()+",Action Code"+obj.getActionCode()+",Bandwidth Speed "
						  +obj.getBandWidthSpeed()+",Qty "+
						  obj.getQty()+",Start Price NRC "+obj.getStartPriceNRC()+",ExtNet Price "+obj.
						  getExtendedNetPrice());
						 
					}else if(arg2.contentEquals("Physical-Bulk"))
					{
						//System.out.println("NRC First Name : "  + eElement.getElementsByTagName("SpNumNRC").item(0).getTextContent());
						if(eElement.getElementsByTagName("AliasNameNRC").item(0).getTextContent() != null)
							obj.setRootProduct(eElement.getElementsByTagName("AliasNameNRC").item(0).getTextContent());
						else
							obj.setRootProduct(eElement.getElementsByTagName("").item(0).getTextContent());

						if(eElement.getElementsByTagName("AliasNameNRC").item(0).getTextContent() != null)
							obj.setProductName(eElement.getElementsByTagName("AliasNameNRC").item(0).getTextContent());
						else
							obj.setProductName(eElement.getElementsByTagName("").item(0).getTextContent());
						//obj.setProductName(eElement.getElementsByTagName("AliasNameNRC").item(0).getTextContent());
						obj.setActionCode(eElement.getElementsByTagName("ActionCodeNRC").item(0).getTextContent());
						if(eElement.getElementsByTagName("BandwidthCalcNRC").item(0).getTextContent() != null)
							obj.setBandWidthSpeed(eElement.getElementsByTagName("BandwidthCalcNRC").item(0).getTextContent());
						else
							obj.setBandWidthSpeed("");
						obj.setQty(eElement.getElementsByTagName("QuantityNRC").item(0).getTextContent());
						obj.setStartPriceNRC(eElement.getElementsByTagName("NetPriceNRC").item(0).getTextContent());
						obj.setExtendedNetPrice(eElement.getElementsByTagName("ExtPriceNRC").item(0).getTextContent());
						
						
						  logger.info(" Bulk Entity NRC Data ::"+"Root product "+obj.getRootProduct()
						  +",Prod Name "+
						  obj.getProductName()+",Action Code"+obj.getActionCode()+",Bandwidth Speed "
						  +obj.getBandWidthSpeed()+",Qty "+
						  obj.getQty()+",Start Price NRC "+obj.getStartPriceNRC()+",ExtNet Price "+obj.
						  getExtendedNetPrice());
						 
					}
					else if (arg2.contentEquals("Quote"))
					{
						//System.out.println("First Name : "  + eElement.getElementsByTagName("AliasNameNRC").item(0).getTextContent());
						obj.setRootProduct(eElement.getElementsByTagName("RootProdNameNRC").item(0).getTextContent());
						obj.setProductName(eElement.getElementsByTagName("AliasNameNRC").item(0).getTextContent());
						if(eElement.getElementsByTagName("BandwidthNRC").item(0).getTextContent() != null)
							obj.setBandWidthSpeed(eElement.getElementsByTagName("BandwidthNRC").item(0).getTextContent());
						else
							obj.setBandWidthSpeed("");
						obj.setQty(eElement.getElementsByTagName("QuantityNRC").item(0).getTextContent());
						
						if(eElement.getElementsByTagName("NetPriceNRC").item(0).getTextContent()!=null) {
						//	if(eElement.getElementsByTagName("NetPriceNRC").item(0).getTextContent().contains("-")) {
						//		obj.setStartPriceNRC("");
						//	}else {
								obj.setStartPriceNRC(eElement.getElementsByTagName("NetPriceNRC").item(0).getTextContent());
						//	}
						}
							
						else {
							obj.setStartPriceNRC("");
						}
						
						if(eElement.getElementsByTagName("NetPriceNRCVAT").item(0).getTextContent()!=null) {
						//	if(eElement.getElementsByTagName("NetPriceNRCVAT").item(0).getTextContent().contains("-")) {
						//		obj.setStartPriceNRCVAT("");
						//	}else {
								obj.setStartPriceNRCVAT(eElement.getElementsByTagName("NetPriceNRCVAT").item(0).getTextContent());
						//	}
						}
							
						else {
							obj.setStartPriceNRCVAT("");
						}
						
						if(eElement.getElementsByTagName("ExtNetPriceNRC").item(0).getTextContent()!=null) {
						//	if(eElement.getElementsByTagName("ExtNetPriceNRC").item(0).getTextContent().contains("-")) {
						//		obj.setExtendedNetPrice("");
						//	}else {
								obj.setExtendedNetPrice(eElement.getElementsByTagName("ExtNetPriceNRC").item(0).getTextContent());
						//	}
						}
							
						else {
							obj.setExtendedNetPrice("");
						}
						
						if(eElement.getElementsByTagName("ExtNetPriceNRCVAT").item(0).getTextContent()!=null) {
						//	if(eElement.getElementsByTagName("ExtNetPriceNRCVAT").item(0).getTextContent().contains("-")) {
						//		obj.setExtendedNetPriceVAT("");
						//	}else {
								obj.setExtendedNetPriceVAT(eElement.getElementsByTagName("ExtNetPriceNRCVAT").item(0).getTextContent());
						//	}
						}
							
						else {
							obj.setExtendedNetPriceVAT("");
						}
						
						obj.setDiscPct(eElement.getElementsByTagName("DiscPercentNRC").item(0).getTextContent()+"%");
						obj.setNetAfDisc(eElement.getElementsByTagName("DiscNetPriceNRC").item(0).getTextContent());
						obj.setVATNetAfDisc(eElement.getElementsByTagName("DiscNetPriceNRCVAT").item(0).getTextContent());
						if (eElement.getElementsByTagName("DiscPercentNRC").item(0).getTextContent()!=null && eElement.getElementsByTagName("DiscPercentNRC").item(0).getTextContent().length() > 0)
						{
							discNrcTot = discNrcTot.add(new BigDecimal(eElement.getElementsByTagName("DiscPercentNRC").item(0).getTextContent())) ; 
						}
						
						  logger.info("Quote Entity NRC Data ::"+"Root product "+obj.getRootProduct()
						  +",Prod Name "+
						  obj.getProductName()+",Bandwidth Speed "+obj.getBandWidthSpeed()+",Qty "+
						  obj.getQty()+",Start Price NRC "+obj.getStartPriceNRC()+",ExtNet Price "+obj.
						  getExtendedNetPrice()+", Disc PCt"+obj.getDiscPct()+
						  ",NetAF Disc "+obj.getNetAfDisc() );
						 
					
					}else if (arg2.contentEquals("Physical-Quote"))
					{
						//System.out.println("First Name : "  + eElement.getElementsByTagName("AliasNameNRC").item(0).getTextContent());
						obj.setRootProduct(eElement.getElementsByTagName("RootProdNameNRC").item(0).getTextContent());
						obj.setProductName(eElement.getElementsByTagName("AliasNameNRC").item(0).getTextContent());
						if(eElement.getElementsByTagName("BandwidthNRC").item(0).getTextContent() != null)
							obj.setBandWidthSpeed(eElement.getElementsByTagName("BandwidthNRC").item(0).getTextContent());
						else
							obj.setBandWidthSpeed("");
						
						if(eElement.getElementsByTagName("QuantityNRC").item(0).getTextContent()!=null )
						obj.setQty(eElement.getElementsByTagName("QuantityNRC").item(0).getTextContent());
						
						if(eElement.getElementsByTagName("NetPriceNRC").item(0).getTextContent()!=null) {
						//	if(eElement.getElementsByTagName("NetPriceNRC").item(0).getTextContent().contains("-")) {
						//		obj.setStartPriceNRC("");
						//	}else {
								obj.setStartPriceNRC(eElement.getElementsByTagName("NetPriceNRC").item(0).getTextContent());
						//	}
						}
							
						else {
							obj.setStartPriceNRC("");
						}
						
						
						if(eElement.getElementsByTagName("ExtNetPriceNRC").item(0).getTextContent()!=null) {
						//	if(eElement.getElementsByTagName("ExtNetPriceNRC").item(0).getTextContent().contains("-")) {
						//		obj.setExtendedNetPrice("");
						//	}else {
								obj.setExtendedNetPrice(eElement.getElementsByTagName("ExtNetPriceNRC").item(0).getTextContent());
						//	}
						}
							
						else {
							obj.setExtendedNetPrice("");
						}
						
						obj.setDiscPct(eElement.getElementsByTagName("DiscPercentNRC").item(0).getTextContent()+"%");
						obj.setNetAfDisc(eElement.getElementsByTagName("DiscNetPriceNRC").item(0).getTextContent());
						if (eElement.getElementsByTagName("DiscPercentNRC").item(0).getTextContent()!=null && eElement.getElementsByTagName("DiscPercentNRC").item(0).getTextContent().length() > 0)
						{
							discNrcTot = discNrcTot.add(new BigDecimal(eElement.getElementsByTagName("DiscPercentNRC").item(0).getTextContent())) ; 
						}
						
						  logger.info("Quote Entity NRC Data ::"+"Root product "+obj.getRootProduct()
						  +",Prod Name "+
						  obj.getProductName()+",Bandwidth Speed "+obj.getBandWidthSpeed()+",Qty "+
						  obj.getQty()+",Start Price NRC "+obj.getStartPriceNRC()+",ExtNet Price "+obj.
						  getExtendedNetPrice()+", Disc PCt"+obj.getDiscPct()+
						  ",NetAF Disc "+obj.getNetAfDisc() );
						 
					
					}
					else
					{
						//System.out.println("First Name : "  + eElement.getElementsByTagName("AliasNameNRC").item(0).getTextContent());
						
					
						obj.setRootProduct(eElement.getElementsByTagName("RootProdNameNRC").item(0).getTextContent());
						obj.setProductName(eElement.getElementsByTagName("AliasNameNRC").item(0).getTextContent());
						if(eElement.getElementsByTagName("BandwidthNRC").item(0).getTextContent() != null)
							obj.setBandWidthSpeed(eElement.getElementsByTagName("BandwidthNRC").item(0).getTextContent());
						else
							obj.setBandWidthSpeed("");
						obj.setQty(eElement.getElementsByTagName("QuantityNRC").item(0).getTextContent());
						if(eElement.getElementsByTagName("NetPriceNRC").item(0).getTextContent()!=null) {
							// if (eElement.getElementsByTagName("NetPriceNRC").item(0).getTextContent().contains("-")) {
							//	obj.setStartPriceNRC("");
							//}else {
								obj.setStartPriceNRC(eElement.getElementsByTagName("NetPriceNRC").item(0).getTextContent());
							//}
						}
						else {
							obj.setStartPriceNRC("");
						}
						
						if(eElement.getElementsByTagName("NetPriceNRCVAT").item(0).getTextContent()!=null) {
						//	if(eElement.getElementsByTagName("NetPriceNRCVAT").item(0).getTextContent().contains("-")) {
						//		obj.setStartPriceNRCVAT("");
						//	}else {
								obj.setStartPriceNRCVAT(eElement.getElementsByTagName("NetPriceNRCVAT").item(0).getTextContent());
						//	}
						}
							else {
								obj.setStartPriceNRCVAT("");
						}
						
						if(eElement.getElementsByTagName("ExtPriceNRC").item(0).getTextContent()!=null) {
						//	if(eElement.getElementsByTagName("ExtPriceNRC").item(0).getTextContent().contains("-")) {
						//		obj.setExtendedNetPrice("");
						//	}else {
								obj.setExtendedNetPrice(eElement.getElementsByTagName("ExtPriceNRC").item(0).getTextContent());
						//	}
						}
							
						else {
							obj.setExtendedNetPrice("");
						}
						
						if(eElement.getElementsByTagName("ExtPriceNRCVAT").item(0).getTextContent()!=null ) {
						//	if(eElement.getElementsByTagName("ExtPriceNRCVAT").item(0).getTextContent().contains("-")) {
						//		obj.setExtendedNetPriceVAT("");
						//	}else {
								obj.setExtendedNetPriceVAT(eElement.getElementsByTagName("ExtPriceNRCVAT").item(0).getTextContent());

						//	}
						}
							else {
								obj.setExtendedNetPriceVAT("");
						}
						
						
					}
					if ( !obj.getStartPriceNRC().equals("0"))
						dataObjNRCList.add(obj);
					//System.out.println("Inside");
				}
			}
			if (arg2.contentEquals("Amendment") || arg2.contentEquals("Physical-Amendment"))
				nList = doc.getElementsByTagName("StcOrderItemVas");
			else if(arg2.contentEquals("Bulk"))
			{
				nList = doc.getElementsByTagName("Stcbulkaddvasdc");
				if (nList.getLength() == 0)
					nList = doc.getElementsByTagName("Stcbulkaddvas");
			}else if(arg2.contentEquals("Physical-Bulk"))
			{
				nList = doc.getElementsByTagName("Stcbulkaddvasdc");
				if (nList.getLength() == 0)
					nList = doc.getElementsByTagName("Stcbulkaddvas");
			}
			else if (arg2.contentEquals("Quote"))
				nList = doc.getElementsByTagName("Stcquoteaddvas");
			else if (arg2.contentEquals("Physical-Quote"))
				nList = doc.getElementsByTagName("Stcquoteaddvas");
			else
				nList = doc.getElementsByTagName("Stcorderaddvas");

			//System.out.println("VAS===" + nList.getLength());
			//logger.info("VAS records=========" + nList.getLength());

			for (int temp = 0; temp < nList.getLength(); temp++)
			{
				Node node = nList.item(temp);
			//	System.out.println("");    //Just a separator
				if (node.getNodeType() == Node.ELEMENT_NODE)
				{
					//System.out.println("Inside");
					//Print each employee's detail
					org.w3c.dom.Element eElement = (org.w3c.dom.Element) node;
					DataObject obj = new DataObject();
					//System.out.println("Inside");
					if (arg2.contentEquals("Amendment") || arg2.contentEquals("Physical-Amendment"))
					{
						//System.out.println("VAS First Name : "  + eElement.getElementsByTagName("AliasNameVAS").item(0).getTextContent());
						if(arg2.contentEquals("Physical-Amendment"))
							obj.setRootProduct(eElement.getElementsByTagName("VASRootName").item(0).getTextContent());
						else
						obj.setRootProduct(eElement.getElementsByTagName("AliasNameVAS").item(0).getTextContent());
						obj.setProductName(eElement.getElementsByTagName("AliasNameVAS").item(0).getTextContent());
						//obj.setActionCode(eElement.getElementsByTagName("ActionCodeVAS").item(0).getTextContent());
						if(eElement.getElementsByTagName("ActionCodeVAS").item(0) != null)
							obj.setActionCode(eElement.getElementsByTagName("ActionCodeVAS").item(0).getTextContent());
						else
							obj.setActionCode("");
						if(eElement.getElementsByTagName("BandwidthVASCalc").item(0).getTextContent() != null)
							obj.setBandWidthSpeed(eElement.getElementsByTagName("BandwidthVAS").item(0).getTextContent());
						else
							obj.setBandWidthSpeed("");
						//obj.setServiceNum(eElement.getElementsByTagName("SerialNumCalcVAS").item(0).getTextContent());
						if(eElement.getElementsByTagName("SerialNumCalcVAS").item(0) != null)
							obj.setServiceNum(eElement.getElementsByTagName("SerialNumCalcVAS").item(0).getTextContent());
						else
							obj.setServiceNum("");
						//System.out.println("MRCVAS ::"+eElement.getElementsByTagName("MRCVAS").item(0).getTextContent());
						//System.out.println("NetPriceDisplayVAS ::"+eElement.getElementsByTagName("NetPriceDisplayVAS").item(0).getTextContent());
						//System.out.println("ProductId ::"+eElement.getElementsByTagName("ProductId").item(0).getTextContent());
						//System.out.println("MRCVAS tag empty::"+eElement.getElementsByTagName("MRCVAS").item(0).getTextContent().isEmpty());
						
						/*if(eElement.getElementsByTagName("ProductId").item(0).getTextContent().contains("1-1PZBXUZ")||
								eElement.getElementsByTagName("ProductId").item(0).getTextContent().contains("3-BG4W8K0"))
						{
							   System.out.println("Inside If MRC VAS for Premium SLA & call management product");
							if(eElement.getElementsByTagName("MRCVAS").item(0).getTextContent().isEmpty())
							    obj.setStartPriceMRC(eElement.getElementsByTagName("NetPriceDisplayVAS").item(0).getTextContent());
							else
								obj.setStartPriceMRC(eElement.getElementsByTagName("MRCVAS").item(0).getTextContent());
						}*/
						
						if(eElement.getElementsByTagName("MRCVAS").item(0).getTextContent().isEmpty()) {
							
							if(eElement.getElementsByTagName("NetPriceDisplayVAS").item(0).getTextContent()!=null) {
						//		if(eElement.getElementsByTagName("NetPriceDisplayVAS").item(0).getTextContent().contains("-")) {
						//			obj.setStartPriceMRC("");
						//		}else {
									 obj.setStartPriceMRC(eElement.getElementsByTagName("NetPriceDisplayVAS").item(0).getTextContent());
						//		}
							}
								else {
									obj.setStartPriceMRC("");
							}
							
						   
						    
						}
						else {
							//System.out.println("Inside else MRC VAS");
							
							if(eElement.getElementsByTagName("MRCVAS").item(0).getTextContent()!=null) {
							//	if(eElement.getElementsByTagName("MRCVAS").item(0).getTextContent().contains("-")) {
							//		obj.setStartPriceMRC("");
							//	}else {
									obj.setStartPriceMRC(eElement.getElementsByTagName("MRCVAS").item(0).getTextContent());	
							//	}
							}
								else {
									obj.setStartPriceMRC("");
							}
							
						}
						
						if(eElement.getElementsByTagName("NetPriceDisplayVASVAT").item(0).getTextContent()!=null) {
						//	if(eElement.getElementsByTagName("NetPriceDisplayVASVAT").item(0).getTextContent().contains("-")) {
						//		obj.setStartPriceMRCVAT("");
						//	}else {
								obj.setStartPriceMRCVAT(eElement.getElementsByTagName("NetPriceDisplayVASVAT").item(0).getTextContent());

						//	}
						}
							else {
								obj.setStartPriceMRCVAT("");
						}
						
						
						  logger.info("Amendment Entity VAS Data ::"+"Root product "+obj.getRootProduct
						  ()+",Prod Name "+
						  obj.getProductName()+",Action code"+obj.getActionCode()+",Bandwidth Speed "
						  +obj.getBandWidthSpeed()+
						  ",Service Num "+obj.getServiceNum()+",Start Price MRC "+obj.getStartPriceMRC(
						  ) );
						 
					}
					else if(arg2.contentEquals("Bulk"))
					{
						//System.out.println("VAS First Name : "  + eElement.getElementsByTagName("AliasNameVAS").item(0).getTextContent());
						obj.setRootProduct(eElement.getElementsByTagName("AliasNameVAS").item(0).getTextContent());
						obj.setProductName(eElement.getElementsByTagName("AliasNameVAS").item(0).getTextContent());
						obj.setActionCode(eElement.getElementsByTagName("ActionCodeVAS").item(0).getTextContent());
						if(eElement.getElementsByTagName("BandwidthVAS").item(0).getTextContent() != null)
							obj.setBandWidthSpeed(eElement.getElementsByTagName("BandwidthVAS").item(0).getTextContent());
						else
							obj.setBandWidthSpeed("");
						obj.setQty(eElement.getElementsByTagName("QuantityVAS").item(0).getTextContent());
						//obj.setStartPriceNRC(eElement.getElementsByTagName("NetPriceVAS").item(0).getTextContent());
						obj.setStartPriceMRC(eElement.getElementsByTagName("NetPriceVAS").item(0).getTextContent());
						obj.setStartPriceMRCVAT(eElement.getElementsByTagName("NetPriceVASVAT").item(0).getTextContent());
						obj.setExtendedNetPrice(eElement.getElementsByTagName("ExtPriceVAS").item(0).getTextContent());
						obj.setExtendedNetPriceVAT(eElement.getElementsByTagName("ExtPriceVASVAT").item(0).getTextContent());
						
						  logger.info("Bulk Entity VAS Data ::"+"Root product "+obj.getRootProduct()
						  +",Prod Name "+
						  obj.getProductName()+",Action code"+obj.getActionCode()+",Bandwidth Speed "
						  +obj.getBandWidthSpeed()+ ",Qty "+
						  obj.getQty()+",Start Price NRC "+obj.getStartPriceNRC()+",ExtNet Price "+obj.
						  getExtendedNetPrice());
							   
	   
					}
					else if(arg2.contentEquals("Physical-Bulk"))
					{
						//System.out.println("VAS First Name : "  + eElement.getElementsByTagName("AliasNameVAS").item(0).getTextContent());
						obj.setRootProduct(eElement.getElementsByTagName("AliasNameVAS").item(0).getTextContent());
						obj.setProductName(eElement.getElementsByTagName("AliasNameVAS").item(0).getTextContent());
						obj.setActionCode(eElement.getElementsByTagName("ActionCodeVAS").item(0).getTextContent());
						if(eElement.getElementsByTagName("BandwidthVAS").item(0).getTextContent() != null)
							obj.setBandWidthSpeed(eElement.getElementsByTagName("BandwidthVAS").item(0).getTextContent());
						else
							obj.setBandWidthSpeed("");
						obj.setQty(eElement.getElementsByTagName("QuantityVAS").item(0).getTextContent());
						obj.setStartPriceNRC(eElement.getElementsByTagName("NetPriceVAS").item(0).getTextContent());
						obj.setExtendedNetPrice(eElement.getElementsByTagName("ExtPriceVAS").item(0).getTextContent());
						
						
						  logger.info("Bulk Entity VAS Data ::"+"Root product "+obj.getRootProduct()
						  +",Prod Name "+
						  obj.getProductName()+",Action code"+obj.getActionCode()+",Bandwidth Speed "
						  +obj.getBandWidthSpeed()+ ",Qty "+
						  obj.getQty()+",Start Price NRC "+obj.getStartPriceNRC()+",ExtNet Price "+obj.
						  getExtendedNetPrice());
							   
	   
					}
					else if(arg2.contentEquals("Quote"))
					{
						//System.out.println("First Name : "  + eElement.getElementsByTagName("AliasNameAdd").item(0).getTextContent());
						obj.setRootProduct(eElement.getElementsByTagName("RootProdNameVAS").item(0).getTextContent());
						obj.setProductName(eElement.getElementsByTagName("AliasNameAdd").item(0).getTextContent());
						if(eElement.getElementsByTagName("BandwidthAdd").item(0).getTextContent() != null)
							obj.setBandWidthSpeed(eElement.getElementsByTagName("BandwidthAdd").item(0).getTextContent());
						else
							obj.setBandWidthSpeed("");
						obj.setQty(eElement.getElementsByTagName("QuantityAdd").item(0).getTextContent());
						obj.setStartPriceMRC(eElement.getElementsByTagName("NetPriceAdd").item(0).getTextContent());
						obj.setStartPriceMRCVAT(eElement.getElementsByTagName("NetPriceAddVAT").item(0).getTextContent());
						obj.setExtendedNetPrice(eElement.getElementsByTagName("ExtNetPriceAdd").item(0).getTextContent());
						obj.setExtendedNetPriceVAT(eElement.getElementsByTagName("ExtNetPriceAddVAT").item(0).getTextContent());
						obj.setDiscPct(eElement.getElementsByTagName("DiscPercentAdd").item(0).getTextContent()+"%");
						obj.setNetAfDisc(eElement.getElementsByTagName("DiscNetPriceAdd").item(0).getTextContent());
						obj.setVATNetAfDisc(eElement.getElementsByTagName("DiscNetPriceAddVAT").item(0).getTextContent());

						if (eElement.getElementsByTagName("DiscPercentAdd").item(0).getTextContent()!=null && eElement.getElementsByTagName("DiscPercentAdd").item(0).getTextContent().length() > 0)
						{
							//discNrcTot = discNrcTot + Double.parseDouble(eElement.getElementsByTagName("DiscPercentAdd").item(0).getTextContent()) ;
							discVasTot = discVasTot.add(new BigDecimal(eElement.getElementsByTagName("DiscPercentAdd").item(0).getTextContent())) ; 
						}
						
						
						  logger.info("Quote Entity VAS Data ::"+"Root product "+obj.getRootProduct()
						  +",Prod Name "+
						  obj.getProductName()+",Bandwidth Speed "+obj.getBandWidthSpeed()+
						  ",Qty "+obj.getQty()+",Start Price MRC "+obj.getStartPriceMRC()
						  +",ExtNet Price "+ obj.getExtendedNetPrice()+", Disc PCt"+obj.getDiscPct() );
						 
					}else if(arg2.contentEquals("Physical-Quote"))
					{
						//System.out.println("First Name : "  + eElement.getElementsByTagName("AliasNameAdd").item(0).getTextContent());
						obj.setRootProduct(eElement.getElementsByTagName("RootProdNameVAS").item(0).getTextContent());
						obj.setProductName(eElement.getElementsByTagName("AliasNameAdd").item(0).getTextContent());
						if(eElement.getElementsByTagName("BandwidthAdd").item(0).getTextContent() != null)
							obj.setBandWidthSpeed(eElement.getElementsByTagName("BandwidthAdd").item(0).getTextContent());
						else
							obj.setBandWidthSpeed("");
						obj.setQty(eElement.getElementsByTagName("QuantityAdd").item(0).getTextContent());
						obj.setStartPriceMRC(eElement.getElementsByTagName("NetPriceAdd").item(0).getTextContent());
						obj.setExtendedNetPrice(eElement.getElementsByTagName("ExtNetPriceAdd").item(0).getTextContent());
						obj.setDiscPct(eElement.getElementsByTagName("DiscPercentAdd").item(0).getTextContent()+"%");
						obj.setNetAfDisc(eElement.getElementsByTagName("DiscNetPriceAdd").item(0).getTextContent());
						
						if (eElement.getElementsByTagName("DiscPercentAdd").item(0).getTextContent()!=null && eElement.getElementsByTagName("DiscPercentAdd").item(0).getTextContent().length() > 0)
						{
							//discNrcTot = discNrcTot + Double.parseDouble(eElement.getElementsByTagName("DiscPercentAdd").item(0).getTextContent()) ;
							discVasTot = discVasTot.add(new BigDecimal(eElement.getElementsByTagName("DiscPercentAdd").item(0).getTextContent())) ; 
						}
						
						
						  logger.info("Quote Entity VAS Data ::"+"Root product "+obj.getRootProduct()
						  +",Prod Name "+
						  obj.getProductName()+",Bandwidth Speed "+obj.getBandWidthSpeed()+
						  ",Qty "+obj.getQty()+",Start Price MRC "+obj.getStartPriceMRC()
						  +",ExtNet Price "+ obj.getExtendedNetPrice()+", Disc PCt"+obj.getDiscPct() );
						 
					}
					else
					{
						//System.out.println("First Name : "  + eElement.getElementsByTagName("AliasNameAdd").item(0).getTextContent());
						
						obj.setRootProduct(eElement.getElementsByTagName("RootProdNameVAS").item(0).getTextContent());
						obj.setProductName(eElement.getElementsByTagName("AliasNameAdd").item(0).getTextContent());
						if(eElement.getElementsByTagName("BandwidthAdd").item(0).getTextContent() != null)
							obj.setBandWidthSpeed(eElement.getElementsByTagName("BandwidthAdd").item(0).getTextContent());
						else
							obj.setBandWidthSpeed("");
						obj.setQty(eElement.getElementsByTagName("QuantityAdd").item(0).getTextContent());
						obj.setStartPriceMRC(eElement.getElementsByTagName("NetPriceAdd").item(0).getTextContent());
						obj.setStartPriceMRCVAT(eElement.getElementsByTagName("NetPriceAddVAT").item(0).getTextContent());
						obj.setExtendedNetPrice(eElement.getElementsByTagName("ExtPriceAdd").item(0).getTextContent());
						obj.setExtendedNetPriceVAT(eElement.getElementsByTagName("ExtPriceAddVAT").item(0).getTextContent());

					}
					dataObjVASList.add(obj);
					//System.out.println("VAS size "+dataObjVASList.size() );
					//logger.info("VAS size "+dataObjVASList.size() );
				}
			}

			if (arg2.contentEquals("Amendment") || arg2.contentEquals("Physical-Amendment"))
			{  
				// System.out.println("1" );
			nList = doc.getElementsByTagName("Stcorderdevice");
			}
			else if(arg2.contentEquals("Bulk"))
			{  
				//System.out.println("2" );
			nList = doc.getElementsByTagName("Stcbulkdevicedc");
			if (nList.getLength() == 0)
				nList = doc.getElementsByTagName("Stcbulkdevice");
			}
			else if(arg2.contentEquals("Physical-Bulk"))
			{  
				//System.out.println("2" );
			nList = doc.getElementsByTagName("Stcbulkdevicedc");
			if (nList.getLength() == 0)
				nList = doc.getElementsByTagName("Stcbulkdevice");
			}
			else if (arg2.contentEquals("Quote"))
			{   
				//System.out.println("3");
			nList = doc.getElementsByTagName("Stcquotedevice");
			}
			else if (arg2.contentEquals("Physical-Quote"))
			{   
				//System.out.println("3");
			nList = doc.getElementsByTagName("Stcquotedevice");
			}
			else
			{  
				//System.out.println("4");
			nList = doc.getElementsByTagName("Stcorderdevice");
			}

			//System.out.println("DEVICE============================" + nList.getLength());

			for (int temp = 0; temp < nList.getLength(); temp++)
			{
				Node node = nList.item(temp);
				//System.out.println("");    //Just a separator
				if (node.getNodeType() == Node.ELEMENT_NODE)
				{
					//System.out.println("Inside");
					//Print each employee's detail
					org.w3c.dom.Element eElement = (org.w3c.dom.Element) node;
					DataObject obj = new DataObject();
					//System.out.println("Inside");
					if (arg2.contentEquals("Amendment") ||arg2.contentEquals("Bulk")||arg2.contentEquals("Physical-Bulk")||arg2.contentEquals("Quote") ||arg2.contentEquals("Physical-Quote") || arg2.contentEquals("Physical-Amendment") )
					{
						//System.out.println("DEVICE First Name : "  + eElement.getElementsByTagName("DeviceModel").item(0).getTextContent());
						obj.setDevModel(eElement.getElementsByTagName("DeviceModel").item(0).getTextContent());
						obj.setDevMemory(eElement.getElementsByTagName("DeviceMemory").item(0).getTextContent());
						obj.setColor(eElement.getElementsByTagName("DeviceColor").item(0).getTextContent());
						obj.setQty(eElement.getElementsByTagName("Quantity").item(0).getTextContent());
						obj.setImei(eElement.getElementsByTagName("IMEINumber").item(0).getTextContent());	
						
						
						  logger.info("Dev Model: "+obj.getDevModel()+",dev Memory:"+obj.getDevMemory()
						  +",color:"+ obj.getColor()+",Qty:"+obj.getQty()+",Imei:"+obj.getImei());
						 
					}
					else
					{
						// System.out.println("3");
						//System.out.println("DEVICE First Name : "  + eElement.getElementsByTagName("DeviceModel").item(0).getTextContent());
						obj.setDevModel(eElement.getElementsByTagName("DeviceModel").item(0).getTextContent());
						// System.out.println("3");
						obj.setDevMemory(eElement.getElementsByTagName("DeviceMemory").item(0).getTextContent());
						obj.setColor(eElement.getElementsByTagName("DeviceColor").item(0).getTextContent());
						obj.setQty(eElement.getElementsByTagName("Quantity").item(0).getTextContent());
						obj.setImei(eElement.getElementsByTagName("IMEINumber").item(0).getTextContent());
						
						
						  logger.info("Dev Model: "+obj.getDevModel()+",dev Memory:"+obj.getDevMemory()
						  +",color:"+ obj.getColor()+",Qty:"+obj.getQty()+",Imei:"+obj.getImei());
						 
					}

					dataObjDEVList.add(obj);
					//System.out.println("Inside");
				}
			}
			//dataObjDEVList
			
			if (arg2.contentEquals("Amendment") || arg2.contentEquals("Physical-Amendment"))
			{   System.out.println("1" );
			nList = doc.getElementsByTagName("StcAmendementOrderItemNew");
			}
			else if(arg2.contentEquals("Bulk"))
			{   System.out.println("2" );
			nList = doc.getElementsByTagName("AmendmentOrderItemNew");
			}else if(arg2.contentEquals("Physical-Bulk"))
			{   System.out.println("2" );
			nList = doc.getElementsByTagName("AmendmentOrderItemNew");
			}
			else if (arg2.contentEquals("Quote"))
			{   System.out.println("3");
			nList = doc.getElementsByTagName("STCQuoteItemNew");
			}else if (arg2.contentEquals("Physical-Quote"))
			{   System.out.println("3");
			nList = doc.getElementsByTagName("STCQuoteItemNew");
			}
			else
			{   System.out.println("4");
			nList = doc.getElementsByTagName("STCOrderItemNew");
			}

			System.out.println("SPECIAL ITEMS============================" + nList.getLength());

			for (int temp = 0; temp < nList.getLength(); temp++)
			{
				Node node = nList.item(temp);
				System.out.println("");    //Just a separator
				if (node.getNodeType() == Node.ELEMENT_NODE)
				{
					System.out.println("Inside");
					//Print each employee's detail
					org.w3c.dom.Element eElement = (org.w3c.dom.Element) node;
					DataObject obj = new DataObject();
					System.out.println("Inside");
					if (arg2.contentEquals("Amendment") ||arg2.contentEquals("Bulk") ||arg2.contentEquals("Physical-Bulk") || arg2.contentEquals("Physical-Amendment"))
					{
						System.out.println("DEVICE First Name : "  + eElement.getElementsByTagName("RootAliasName").item(0).getTextContent());
						obj.setRootProduct(eElement.getElementsByTagName("RootAliasName").item(0).getTextContent());
						obj.setitemName(eElement.getElementsByTagName("ProductDisplayName").item(0).getTextContent());
						obj.setpachenName(eElement.getElementsByTagName("STCPromoNameENU").item(0).getTextContent());
						obj.setpacherName(eElement.getElementsByTagName("STCPromoNameARA").item(0).getTextContent());
						obj.setServiceNum(eElement.getElementsByTagName("RootServiceNumber").item(0).getTextContent());
						obj.setActionCode(eElement.getElementsByTagName("ActionCode").item(0).getTextContent());

					}
					else
					{
						 System.out.println("3");
						//System.out.println("DEVICE First Name : "  + eElement.getElementsByTagName("RootAliasName").item(0).getTextContent());
						//RootProdAliasName
						if (arg2.contentEquals("Quote"))
						obj.setRootProduct(eElement.getElementsByTagName("RootProdAliasName").item(0).getTextContent());
						else if (arg2.contentEquals("Physical-Quote"))
							obj.setRootProduct(eElement.getElementsByTagName("RootProdAliasName").item(0).getTextContent());
						else
						obj.setRootProduct(eElement.getElementsByTagName("RootAliasName").item(0).getTextContent());
						obj.setitemName(eElement.getElementsByTagName("ProductDisplayName").item(0).getTextContent());
						if (arg2.contentEquals("Quote"))
						{
							obj.setpachenName(eElement.getElementsByTagName("PromoNameENU").item(0).getTextContent());
							obj.setpacherName(eElement.getElementsByTagName("PromoNameARA").item(0).getTextContent());
						} else if (arg2.contentEquals("Physical-Quote"))
						{
							obj.setpachenName(eElement.getElementsByTagName("PromoNameENU").item(0).getTextContent());
							obj.setpacherName(eElement.getElementsByTagName("PromoNameARA").item(0).getTextContent());
						}
						else
						{
							obj.setpachenName(eElement.getElementsByTagName("STCPromoNameENU").item(0).getTextContent());
							obj.setpacherName(eElement.getElementsByTagName("STCPromoNameARA").item(0).getTextContent());
						}
						obj.setServiceNum(eElement.getElementsByTagName("RootServiceNumber").item(0).getTextContent());
					}

					dataObjNewList.add(obj);
					System.out.println("Inside");
				}
			}						   
   
			 if(arg2.contentEquals("Bulk"))
			{  
					nList = doc.getElementsByTagName("ProductServiceOrderTemplateBc");
				if (nList.getLength() == 0)
					{
					nList = doc.getElementsByTagName("ProductServiceOrderTemplateBcDC");
					bulkDCBoo = true ; 
					//ProductServiceOrderTemplateBcDC
					}
			}
			 else  if(arg2.contentEquals("Physical-Bulk"))
				{  
					nList = doc.getElementsByTagName("ProductServiceOrderTemplateBc");
				if (nList.getLength() == 0)
					{
					nList = doc.getElementsByTagName("ProductServiceOrderTemplateBcDC");
					bulkDCBoo = true ; 
					}
			}
			else if (arg2.contentEquals("Quote"))
			{   
			nList = doc.getElementsByTagName("ProductServiceTemplateBc");
			}else if (arg2.contentEquals("Physical-Quote"))
			{   
			nList = doc.getElementsByTagName("ProductServiceTemplateBc");
			}
			else
			{   
			nList = doc.getElementsByTagName("ProductServiceOrderTemplateBc");
			}

			//System.out.println("Services==" + nList.getLength());
			 NodeList BulkData	= doc.getElementsByTagName("Stcbulkmainsvcmrcdc");
			 NodeList BulkData2	= doc.getElementsByTagName("ProductServiceOrderTemplateBcDC");
			 NodeList BulkData3	= doc.getElementsByTagName("ProductServiceOrderTemplateBc");
			 for(int i=0; i<BulkData2.getLength();i++) {
				 Node node = BulkData2.item(i);
					//System.out.println("");    //Just a separator
					if (node.getNodeType() == Node.ELEMENT_NODE)
					{
						org.w3c.dom.Element eElement = (org.w3c.dom.Element) node;
						DataObject obj = new DataObject();
						
						if(arg2.contentEquals("Bulk")) {
							// Stcbulkmainsvcmrcdc
//							System.out.println(eElement.getElementsByTagName("RootServiceNumber").item(0).getTextContent() );
//							if(eElement.getElementsByTagName("RootServiceNumber").item(0).getTextContent()!=null  && eElement.getElementsByTagName("RootServiceNumber").item(0).getTextContent().length() > 0) {
//								stcSvcMap.put(eElement.getElementsByTagName("RootServiceNumber").item(0).getTextContent(), eElement.getElementsByTagName("Product").item(0).getTextContent());
//							}
//							if (eElement.getElementsByTagName("ServiceID").item(0).getTextContent() != null && eElement.getElementsByTagName("ServiceID").item(0).getTextContent().length() > 0)
//							{
//								//System.out.println("RootServiceNumber");
//								stcSvcMap.put(eElement.getElementsByTagName("ServiceID").item(0).getTextContent(), eElement.getElementsByTagName("Product").item(0).getTextContent());
//							}
							if(eElement.getElementsByTagName("STCBandwidth").item(0)!=null)
							stcSvcBandWthMap.put(eElement.getElementsByTagName("ServiceID").item(0).getTextContent(), eElement.getElementsByTagName("STCBandwidth").item(0).getTextContent() );
							if (bulkDCBoo)
							{
								String ctemp = eElement.getElementsByTagName("Product").item(0).getTextContent() + "#" + eElement.getElementsByTagName("ServiceID").item(0).getTextContent() ; 
								stcSvcMap.put(eElement.getElementsByTagName("FinalServiceCircuitNumber").item(0).getTextContent(), ctemp);
							}else
							{
							stcSvcMap.put(eElement.getElementsByTagName("FinalServiceCircuitNumber").item(0).getTextContent(), eElement.getElementsByTagName("Product").item(0).getTextContent());
							}
						}
					}
			 }
			 
			 if(BulkData2.getLength()==0) {
				 if(BulkData3.getLength()>0)
				 {
				 bulkDCBoo=true;
				 for(int i=0; i<BulkData3.getLength();i++) {
					 Node node = BulkData3.item(i);
						//System.out.println("");    //Just a separator
						if (node.getNodeType() == Node.ELEMENT_NODE)
						{
							org.w3c.dom.Element eElement = (org.w3c.dom.Element) node;
							DataObject obj = new DataObject();
							
							if(arg2.contentEquals("Bulk")) {
								// Stcbulkmainsvcmrcdc
//								System.out.println(eElement.getElementsByTagName("RootServiceNumber").item(0).getTextContent() );
//								if(eElement.getElementsByTagName("RootServiceNumber").item(0).getTextContent()!=null  && eElement.getElementsByTagName("RootServiceNumber").item(0).getTextContent().length() > 0) {
//									stcSvcMap.put(eElement.getElementsByTagName("RootServiceNumber").item(0).getTextContent(), eElement.getElementsByTagName("Product").item(0).getTextContent());
//								}
//								if (eElement.getElementsByTagName("ServiceID").item(0).getTextContent() != null && eElement.getElementsByTagName("ServiceID").item(0).getTextContent().length() > 0)
//								{
//									//System.out.println("RootServiceNumber");
//									stcSvcMap.put(eElement.getElementsByTagName("ServiceID").item(0).getTextContent(), eElement.getElementsByTagName("Product").item(0).getTextContent());
//								}
								if(eElement.getElementsByTagName("STCBandwidth").item(0)!=null)
								stcSvcBandWthMap.put(eElement.getElementsByTagName("ServiceID").item(0).getTextContent(), eElement.getElementsByTagName("STCBandwidth").item(0).getTextContent() );
								if (bulkDCBoo)
								{
									String ctemp = eElement.getElementsByTagName("Product").item(0).getTextContent() + "#" + eElement.getElementsByTagName("RootServiceNumber").item(0).getTextContent() ; 
									stcSvcMap.put(eElement.getElementsByTagName("RootServiceNumber").item(0).getTextContent(), ctemp);
								}
							}
						}
				 }
				 }
			 }
			 
				
			for (int temp = 0; temp < nList.getLength(); temp++)
			{
				Node node = nList.item(temp);
				//System.out.println("");    //Just a separator
				if (node.getNodeType() == Node.ELEMENT_NODE)
				{
					//System.out.println("Inside");
					//Print each employee's detail
					org.w3c.dom.Element eElement = (org.w3c.dom.Element) node;
					DataObject obj = new DataObject();
					//System.out.println("Inside");
				
					if (arg2.contentEquals("Bulk") && BulkData2.getLength()==0)
					{
						//System.out.println("BULK SERVICE First Name : "  + eElement.getElementsByTagName("Product").item(0).getTextContent());
						if (eElement.getElementsByTagName("RootServiceNumber").item(0).getTextContent() != null && eElement.getElementsByTagName("RootServiceNumber").item(0).getTextContent().length() > 0)
						{
							//System.out.println("RootServiceNumber");
							stcSvcMap.put(eElement.getElementsByTagName("RootServiceNumber").item(0).getTextContent(), eElement.getElementsByTagName("Product").item(0).getTextContent());
						}
//						if (eElement.getElementsByTagName("ServiceID").item(0).getTextContent() != null && eElement.getElementsByTagName("ServiceID").item(0).getTextContent().length() > 0)
//						{
//							//System.out.println("RootServiceNumber");
//							stcSvcMap.put(eElement.getElementsByTagName("ServiceID").item(0).getTextContent(), eElement.getElementsByTagName("Product").item(0).getTextContent());
//						}
						else
						{
						//	System.out.println("FinalServiceCircuitNumber");
							if (bulkDCBoo)
							{
								String ctemp = eElement.getElementsByTagName("Product").item(0).getTextContent() + "#" + eElement.getElementsByTagName("ServiceID").item(0).getTextContent() ; 
								stcSvcMap.put(eElement.getElementsByTagName("FinalServiceCircuitNumber").item(0).getTextContent(), ctemp);
							}
							else
							{
							stcSvcMap.put(eElement.getElementsByTagName("FinalServiceCircuitNumber").item(0).getTextContent(), eElement.getElementsByTagName("Product").item(0).getTextContent());
							}
						}
						
							
						
					} else if (arg2.contentEquals("Physical-Bulk"))
					{
						//System.out.println("BULK SERVICE First Name : "  + eElement.getElementsByTagName("Product").item(0).getTextContent());
						if (eElement.getElementsByTagName("RootServiceNumber").item(0).getTextContent() != null && eElement.getElementsByTagName("RootServiceNumber").item(0).getTextContent().length() > 0)
						{
							//System.out.println("RootServiceNumber");
							stcSvcMap.put(eElement.getElementsByTagName("RootServiceNumber").item(0).getTextContent(), eElement.getElementsByTagName("Product").item(0).getTextContent());
						}
						else
						{
						//	System.out.println("FinalServiceCircuitNumber");
							if (bulkDCBoo)
							{
								String ctemp = eElement.getElementsByTagName("Product").item(0).getTextContent() + "#" + eElement.getElementsByTagName("ServiceID").item(0).getTextContent() ; 
								stcSvcMap.put(eElement.getElementsByTagName("FinalServiceCircuitNumber").item(0).getTextContent(), ctemp);
							}
							else
							{
							stcSvcMap.put(eElement.getElementsByTagName("FinalServiceCircuitNumber").item(0).getTextContent(), eElement.getElementsByTagName("Product").item(0).getTextContent());
							}
						}
						
							
						
					}
					else if (arg2.contentEquals("Quote"))
					{
						String ctemp = eElement.getElementsByTagName("AliasName").item(0).getTextContent() + "#" + eElement.getElementsByTagName("Id").item(0).getTextContent() ; 
						stcSvcMap.put(eElement.getElementsByTagName("FinalServiceCircuitNumber").item(0).getTextContent(), ctemp);
					}else if (arg2.contentEquals("Physical-Quote"))
					{
						String ctemp = eElement.getElementsByTagName("AliasName").item(0).getTextContent() + "#" + eElement.getElementsByTagName("Id").item(0).getTextContent() ; 
						stcSvcMap.put(eElement.getElementsByTagName("FinalServiceCircuitNumber").item(0).getTextContent(), ctemp);
					}
					else
					{
						stcSvcMap.put(eElement.getElementsByTagName("FinalServiceCircuitNumber").item(0).getTextContent(), eElement.getElementsByTagName("AliasName").item(0).getTextContent());
					}
					//System.out.println("stcSvcMap -:"+ stcSvcMap.size());
				}
			}
			
			//invoiceTaxDvcMap
			 if(arg2.contentEquals("TaxInvoiceDevice"))
				{  
				 nList = doc.getElementsByTagName("STCDeviceInvoiceDetail");
				

				//System.out.println("Invoice Tax Device Map============================" + nList.getLength());

				for (int temp = 0; temp < nList.getLength(); temp++)
				{
					Node node = nList.item(temp);
					
					if (node.getNodeType() == Node.ELEMENT_NODE)
					{
						//System.out.println("Inside");
						//Print each employee's detail
						org.w3c.dom.Element eElement = (org.w3c.dom.Element) node;
						String mapData = eElement.getElementsByTagName("STCDeviceName").item(0).getTextContent()+"#"+eElement.getElementsByTagName("STCSerialNumber").item(0).getTextContent()+"#"+
								
								eElement.getElementsByTagName("STCDevicePrice").item(0).getTextContent()+"#"+
								eElement.getElementsByTagName("STCQuantity").item(0).getTextContent()+"#"+
								eElement.getElementsByTagName("STCSupplyPeriod").item(0).getTextContent()+"#"+
								eElement.getElementsByTagName("STCTaxableAmount").item(0).getTextContent()+"#"+
								eElement.getElementsByTagName("STCDiscountAmount").item(0).getTextContent()+"#"+
								eElement.getElementsByTagName("STCAmountAfterDisc").item(0).getTextContent()+"#"+
								eElement.getElementsByTagName("STCTaxRate").item(0).getTextContent()+"#"+
								eElement.getElementsByTagName("STCTaxAmount").item(0).getTextContent()+"#"+
								eElement.getElementsByTagName("STCTotalAmount").item(0).getTextContent();
						totalVat = totalVat + Double.parseDouble(eElement.getElementsByTagName("STCTaxAmount").item(0).getTextContent());
						if (Double.parseDouble(eElement.getElementsByTagName("STCTaxAmount").item(0).getTextContent()) == 0 )
							zeroTaxRec = true ; 
						invoiceTaxDvcMap.put(eElement.getElementsByTagName("STCOrderLineId").item(0).getTextContent(), mapData);	
					}
				}
				//System.out.println("Invoice Tax Device Map============================" + invoiceTaxDvcMap.size() + " "+totalVat);
				}
			// logger.info("getXMLData method end");

		} catch(CustomInternalException ex) {
			System.out.println("CustomInternalException is :" + ex.toString());
			logger.info(ex.getMessage());
			throw ex;
		} catch (Exception e) {
			System.out.println("Error is :" + e.toString());
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		

	//	for (String s :xmlData.keySet())
		//	System.out.println(s + "= " + xmlData.get(s)); 
	//	System.out.println("xmlData size is :" + xmlData.size());
		return xmlData;
		
		

	}
}
