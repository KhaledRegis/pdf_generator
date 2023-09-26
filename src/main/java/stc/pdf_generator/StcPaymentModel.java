package stc.pdf_generator;

public class StcPaymentModel {
	private String PaymentAmount2;
	private String PaymentNumber;
	private String STCHijriPaymentDate;
	public String getPaymentAmount2() {
		return PaymentAmount2;
	}
	public void setPaymentAmount2(String paymentAmount2) {
		PaymentAmount2 = paymentAmount2;
	}
	public String getPaymentNumber() {
		return PaymentNumber;
	}
	public void setPaymentNumber(String paymentNumber) {
		PaymentNumber = paymentNumber;
	}
	public String getSTCHijriPaymentDate() {
		return STCHijriPaymentDate;
	}
	public void setSTCHijriPaymentDate(String sTCHijriPaymentDate) {
		STCHijriPaymentDate = sTCHijriPaymentDate;
	}
	public StcPaymentModel(String paymentAmount2, String paymentNumber, String sTCHijriPaymentDate) {
		super();
		PaymentAmount2 = paymentAmount2;
		PaymentNumber = paymentNumber;
		STCHijriPaymentDate = sTCHijriPaymentDate;
	}
	
	
	
	
}
