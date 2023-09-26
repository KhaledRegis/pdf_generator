package stc.pdf_generator;


import java.util.ArrayList;

public class DataObject {
    private String rootProduct;
    private String productName;
    private String bandWidthSpeed;
    private String qty;
    private String startPriceMRC;
    private String extendedNetPrice;
    private String commitDuration;
    private String startPriceNRC;
    private String devModel;
    private String devMemory;
    private String color;
    private String imei;
    private String discPct ; 
    private String customerAccountName;
    private String commercialRegistrationNumber;
    private String registrationDate;
    private String customerAccountCity;
    private String orderNumber;
    private String deliveryContactFullName;
    private String deliveryContactMobileNumber;
    private String customerENUAccountName;
    private String accountMainPhoneNumber;
    private String created;
    private String deviceCost;
    private String vat;
    //private String rootProduct;
    private String itemName;
    private String pachenName;
    private String pacherName;
    //private String serviceNum;
    
    //khaled add change here for new fields of new XML
    private String STCMasterAccountName;
    private String OutstandingAmount;
    private String NoofInstallment;
    private String AgreementNumber;
    private String STCContractDuration;
    private String DownPayment;
    private String STCCommercialRegistrationNumber;
    private String CSN;
    private String STCAgreementCreated;
    private ArrayList<StcPaymentModel> StcPayment;
    
    private String startPriceMRCVAT;
    private String startPriceNRCVAT;
    private String extendedNetPriceVAT;
    private String VATnetAfDisc;
    
    
    
    
    public String getSTCAgreementCreated() {
		return STCAgreementCreated;
	}
	public void setSTCAgreementCreated(String sTCAgreementCreated) {
		STCAgreementCreated = sTCAgreementCreated;
	}
	public String getAgreementNumber() {
		return AgreementNumber;
	}
	public void setAgreementNumber(String agreementNumber) {
		AgreementNumber = agreementNumber;
	}
	public String getSTCContractDuration() {
		return STCContractDuration;
	}
	public void setSTCContractDuration(String sTCContractDuration) {
		STCContractDuration = sTCContractDuration;
	}
	public String getSTCMasterAccountName() {
		return STCMasterAccountName;
	}
	public void setSTCMasterAccountName(String sTCMasterAccountName) {
		STCMasterAccountName = sTCMasterAccountName;
	}
	public String getOutstandingAmount() {
		return OutstandingAmount;
	}
	public void setOutstandingAmount(String outstandingAmount) {
		OutstandingAmount = outstandingAmount;
	}
	public String getNoofInstallment() {
		return NoofInstallment;
	}
	public void setNoofInstallment(String noofInstallment) {
		NoofInstallment = noofInstallment;
	}
	public String getDownPayment() {
		return DownPayment;
	}
	public void setDownPayment(String downPayment) {
		DownPayment = downPayment;
	}
	public String getSTCCommercialRegistrationNumber() {
		return STCCommercialRegistrationNumber;
	}
	public void setSTCCommercialRegistrationNumber(String sTCCommercialRegistrationNumber) {
		STCCommercialRegistrationNumber = sTCCommercialRegistrationNumber;
	}
	public String getCSN() {
		return CSN;
	}
	public void setCSN(String cSN) {
		CSN = cSN;
	}
	public ArrayList<StcPaymentModel> getStcPayment() {
		return StcPayment;
	}
	public void setStcPayment(ArrayList<StcPaymentModel> stcPayment) {
		StcPayment = stcPayment;
	}
	//end khaled
	public String getitemName() {
		return itemName;
	}
	public void setitemName(String itemName) {
		this.itemName = itemName;
	}
	public String getpachenName() {
		return pachenName;
	}
	public void setpachenName(String pachenName) {
		this.pachenName = pachenName;
	}
	public String getpacherName() {
		return pacherName;
	}
	public void setpacherName(String pacherName) {
		this.pacherName = pacherName;
	}
    
    
    
    
    public String getCustomerAccountName() {
		return customerAccountName;
	}
	public void setCustomerAccountName(String customerAccountName) {
		this.customerAccountName = customerAccountName;
	}
	public String getCommercialRegistrationNumber() {
		return commercialRegistrationNumber;
	}
	public void setCommercialRegistrationNumber(String commercialRegistrationNumber) {
		this.commercialRegistrationNumber = commercialRegistrationNumber;
	}
	public String getRegistrationDate() {
		return registrationDate;
	}
	public void setRegistrationDate(String registrationDate) {
		this.registrationDate = registrationDate;
	}
	public String getCustomerAccountCity() {
		return customerAccountCity;
	}
	public void setCustomerAccountCity(String customerAccountCity) {
		this.customerAccountCity = customerAccountCity;
	}
	public String getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	public String getDeliveryContactFullName() {
		return deliveryContactFullName;
	}
	public void setDeliveryContactFullName(String deliveryContactFullName) {
		this.deliveryContactFullName = deliveryContactFullName;
	}
	public String getDeliveryContactMobileNumber() {
		return deliveryContactMobileNumber;
	}
	public void setDeliveryContactMobileNumber(String deliveryContactMobileNumber) {
		this.deliveryContactMobileNumber = deliveryContactMobileNumber;
	}
	public String getCustomerENUAccountName() {
		return customerENUAccountName;
	}
	public void setCustomerENUAccountName(String customerENUAccountName) {
		this.customerENUAccountName = customerENUAccountName;
	}
	public String getAccountMainPhoneNumber() {
		return accountMainPhoneNumber;
	}
	public void setAccountMainPhoneNumber(String accountMainPhoneNumber) {
		this.accountMainPhoneNumber = accountMainPhoneNumber;
	}
	public String getCreated() {
		return created;
	}
	public void setCreated(String created) {
		this.created = created;
	}
	public String getDeviceCost() {
		return deviceCost;
	}
	public void setDeviceCost(String deviceCost) {
		this.deviceCost = deviceCost;
	}
	public String getVat() {
		return vat;
	}
	public void setVat(String vat) {
		this.vat = vat;
	}
    public String getDiscPct() {
		return discPct;
		
	}
	public void setDiscPct(String discPct) {
		this.discPct = discPct;
	}
	public String getNetAfDisc() {
		return netAfDisc;
	}
	public void setNetAfDisc(String netAfDisc) {
		this.netAfDisc = netAfDisc;
	}
	private String netAfDisc ; 
    public String getServiceNum() {
		return serviceNum;
	}
	public void setServiceNum(String serviceNum) {
		this.serviceNum = serviceNum;
	}
	private String serviceNum ; 
    
	public String getActionCode() {
		return actionCode;
	}
	public void setActionCode(String actionCode) {
		this.actionCode = actionCode;
	}
	private String actionCode;
	public String getRootProduct() {
		return rootProduct;
	}
	public void setRootProduct(String rootProduct) {
		this.rootProduct = rootProduct;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getBandWidthSpeed() {
		return bandWidthSpeed;
	}
	public void setBandWidthSpeed(String bandWidthSpeed) {
		this.bandWidthSpeed = bandWidthSpeed;
	}
	public String getQty() {
		return qty;
	}
	public void setQty(String qty) {
		this.qty = qty;
	}
	public String getStartPriceMRC() {
		return startPriceMRC;
	}
	public void setStartPriceMRC(String startPriceMRC) {
		this.startPriceMRC = startPriceMRC;
	}
	public String getExtendedNetPrice() {
		return extendedNetPrice;
	}
	public void setExtendedNetPrice(String extendedNetPrice) {
		this.extendedNetPrice = extendedNetPrice;
	}
	public String getCommitDuration() {
		return commitDuration;
	}
	public void setCommitDuration(String commitDuration) {
		this.commitDuration = commitDuration;
	}
	public String getStartPriceNRC() {
		return startPriceNRC;
	}
	public void setStartPriceNRC(String startPriceNRC) {
		this.startPriceNRC = startPriceNRC;
	}
	public String getDevModel() {
		return devModel;
	}
	public void setDevModel(String devModel) {
		this.devModel = devModel;
	}
	public String getDevMemory() {
		return devMemory;
	}
	public void setDevMemory(String devMemory) {
		this.devMemory = devMemory;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getImei() {
		return imei;
	}
	public void setImei(String imei) {
		this.imei = imei;
	}
	
	public String getStartPriceMRCVAT() {
		return startPriceMRCVAT;
	}
	public void setStartPriceMRCVAT(String startPriceMRCVAT) {
		this.startPriceMRCVAT = startPriceMRCVAT;
	}
	public String getExtendedNetPriceVAT() {
		return extendedNetPriceVAT;
	}
	public void setExtendedNetPriceVAT(String extendedNetPriceVAT) {
		this.extendedNetPriceVAT = extendedNetPriceVAT;
	}
	public String getVATNetAfDisc() {
		return VATnetAfDisc;
	}
	public void setVATNetAfDisc(String VATnetAfDisc) {
		this.VATnetAfDisc = VATnetAfDisc;
	}
	public String getStartPriceNRCVAT() {
		return startPriceNRCVAT;
	}
	public void setStartPriceNRCVAT(String startPriceNRCVAT) {
		this.startPriceNRCVAT = startPriceNRCVAT;
	}
  
}