package com.test.pdf.util;

/**
 * 
 * @author Jignesh Koshti
 * 
 * Wrapper enum that holds error message and error code together as one entity.
 * 
 * Reuse the error ID 100005 for all technical errors
 * 
 */

public enum ErrorMessageEnum {
	
	MANDATORY_TAG("Mandatory Tag Missing", 100001),
	INVALID_ACCOUNT("Account does not exist", 200001),
	INVALID_CUSTOMER("Customer does not exist", 300001),
	INVALID_SUBSCRIPTION("Subscription does not exist", 600020),
	INVALID_RETENTION_DISCOUNT_VALUE("Invalid retention discount value", 600005),
	MISSING_SUPPLIER_ORDER_NUM_FROM_SRC("Supplier Order Number passed is null. Its a mandatory attribute", 600044),
	MISSING_QUANTITY_FROM_SRC("Product quantity passed is null. Its a mandatory attribute", 600045),
	NULL_CHECK_FALIED("Null value passed to the field:",200009),
	TARIFF_NOT_FOUND("Could not find RBM tariff for input CRM tariff",302111),
	DUPLICATE_SUBSCRIPTION("Subscription already exists with the same unique id",301004),
	SRC_PRODUCT_NOT_FOUND("SRC product not found in the product mapping",301029),
	BILLING_TARIFF_NOT_FOUND("Billing tariff not found", 302111),
	MISSING_CUSTOMER_SEGMENT("Customer segment is mandatory for new customer creation request",100002),
	MISSING_ADDRESS_NAME("Address name cannot be null",600028),
	INVALID_BILL_CYCLE_FREQUENCY_ZERO("Calculated bill frequency is 0, cycle is not valid",600032),
	ZERO_RECORDS_UPDATED("ZERO RECORDS UPDATED, PLEASE CHECK THE ID/ID TYPE",308020),
	NULL_SERIAL_NUMBER ("Enter not null Serial Number",305051),
	INVALID_INPUTS("INVALID INPUT.Customer Reference and Account Number passed as null",308016),
	ACC_TRANSFER_REQUEST_ALREADY_CANCELLED("Account Transfer request for given service request id alreday cancelled",308062),
	ACC_TRANSFER_REQUEST_ALREADY_PROCESSED("Account Transfer request is in process/already processed so request can not be cancelled",308060),
	ACC_TRANSFER_REQUEST_NOT_EXIST("Account Transfer request for given service request id does not exist",308061),
	INVALID_PRIMARY_ACC_NUM("Primary account number cannot be null for enrollment",305026),
	INVALID_CUSTOMER_REF_ARR("No customer reference passed!",305027),
	INVALID_ZEROCOPYBILLS ("Please enter valid number of copies ",405001),
	INVALID_TRANSFER_TYPE("Invalid payment type",203002),
	PAY_TRANSFER_INVALID_ACC_NUM("Can't transfer to another account",203002),
	TRANSFER_SRC_DST_SAME("Same source and destination account specified",203002),
	REALTIME_BALANCE_RETRIEVAL_ERROR("Retrieval of balance details failed",201101),
	INVOKE_INITIATE_STC_REACTIVATION_PROCEDURE_ERROR(" Error during calling procedure stc_pkg_reconnect.stc_reactivatenc ",308083),
	INVOKE_CHECK_REACTIVATION_PROCEDURE_ERROR(" Error during calling procedure stc_pkg_adj.reactivationnc ",308071),
	INSUFFICIENT_ACC_BALANCE("Insufficient account balance",203001),
	DEVICE_DEPOSIT_TRANSFER_ERROR ("Error while updating custom table for device deposit transfer", 308015), 
	TRANSFER_PAYMENT_INSUFFICIENT_DEPOSIT ("Insufficient Account balance or unused deposit", 203005),
	PAYMENT_REFUND_FAILED ("Insufficient deposit(Deposit Refund) or account balance(Account balance refund) or credit limit(Account debit transfer)", 203006),
	INST_TRANSFER_FAILED ("TRANSFER FAILED",308035),
	FAILED_TO_GET_REFUND_PAYMENT_DETAILS ("Failed to get Refund Payment details",305021),
	CANNOT_MODIFY_BILLED_ACCOUNT_PERIOD("Account details cannot be modified for a period for which bill is generated",412006),
	INVOKE_INITIATE_REACTIVATION_PROCEDURE_ERROR(" Error during calling procedure stc_pkg_adj.initiatereactivation", 308072),
	SUBSCRIPTION_ACTIVE ("Subcription is active under this account number, please terminate this first",700008),
	INVALID_CREDIT_LIMIT_INPUT("Validation of credit limit input failed",504012),
	CREDIT_LIMIT_INVALID_DEPOSIT_FLAG("Invalid Input, deposit flag must be F or MNP",504010),
	PAYMENT_DEACTIVATION_FAILED("Payment deactivation failed",211011),
	NO_SUCH_ENTITY_ERROR("No such entity",412003),
	INVALID_BILL_SEQ("Bill information not available for the given account and bill sequence",403005),
	INVALID_CURRENCY_CODE("Currency code provided is invalid", 202005),
	INVALID_PAYMENT_AMOUNT("Amount should be greater than zero", 202003),
	INVALID_BANK_ID("Payment method id not found for the provided bank id", 202004),
	DUPLICATE_PHYSICAL_PAYMENT("Duplicate payment", 202001),
	INCORRECT_BILL_HIERARCHY_FLAG_VALUE("Incorrect bill hierarchy flag value passed",305022),
	BILL_HIERARCHY_CANNOT_BE_CHANGED("Bill Hierarchy cannot be changed",306100),
	INST_ACC_NUM_NOT_PRESENT("Installment account number not exist for the provided agreement number ",207004),
	NO_ACCOUNT_FOR_ACCOUNT_LEVEL_UPDATE("Update Mode is Account Level : No Account number is passed",305054),
	INVALID_AGREEMENT("Invalid agreement number",308031),
	EXPIRED_ECA("ECA has expired",308032),
	NOT_PENDING_ERROR("The ECA is not in pending status",308033),
	SUBSCRIPTION_IS_NOT_ACTIVE("Subscription is inactive",305053),
	INBOX_APPROVAL_PENDING("Cannot enrol into tamayoz, inbox approval pending",501088),
	PRODUCT_SEQ_RETRIEVAL_FAILED("Unable to get product sequence for the supplier order number", 600003),
	ENROL_EBU_TAM_FAILED("Enrolment for EBU Tamayouz failed",307022),
	DUPLCATE_PRODUCT("Duplicate product instance not allowed",600001),
	DO_NOT_BILL_HANDLING_CODE("This account has a Do Not Print Bill Handling Code",600039),
	INVALID_CUSTOMER_DOB("Date of Birth Incorrect format.Correct format:MM/DD/YYYY",308009),
	INVALID_ADJUSTMENT_STATUS("Specified adjustment status is invalid",209002),
	ACCOUNT_HAS_FA_ECA("Adjustment can not be posted because for given Account number either activeted FA ECA account or a request for FA ECA account creation  exist", 308029),
	INVALID_REQUEST_TYPE("Invalid request type",201003),
	INVALID_ACC_STATUS_FOR_FA_ECA("Account Status for which FA-ECA is being created is not in TX status",201103),
	INVALID_ACC_STATUS_FOR_ECA("Account Status for which ECA is being created should not be in TX status",201003),
	INVALID_PAY_REQ_AMOUNT("Amount should not be null or Zero",201003),
	NULL_INSTALLMENT_PERIOD("Installment period should not be null for Down payment request type",201003),
	NULL_THRESHOLD_VALUE("Threshold value should not be null for Down payment request type",201003),
	NULL_MSISDN("MSISDN should not be null",308021),
	INVALID_INPUTS_FOR_DOWNPAYMENT("Mandatory input for down payment request not specified",201003),
	DUPLICATE_PAYMENT_REQ ("Duplicate payment request",201001),
	INVALID_AGREEMENT_NUMBER ("Agreement number is invalid ",207002),
	INVALID_PAYMENT_REQUEST_NUM_FOR_PENALTY_PRODUCT ("Invalid or missing PR Number required for Penalty Product",199999),
	INVALID_PENALTY_PROD_PAY_REASON("The Payment Request Num for Penalty product is invalid",301033),
	NO_ACTIVE_DEVICE("No Active Devices are present for given msisidn", 308068),
	NO_DEVICE_DEPOSIT_FOUND_FOR_GIVEN_PAYREASON("No STC_DEVICE_PROD_DEPOSIT records found for the given pay reason",308013),
	INVALID_TARIFF_FOR_ACCOUNT("Tariff not supported for billing account", 600001),
	REDEEMPTION_PROD_BILLED_CANNOT_TERMINATE("Product cannot be terminated since its already redeemed for the bill period", 602112),
	INVALID_AMAL_INPUT("Please pass proper input details",500000),
	REDEEMPTION_PROD_ACTIVE_CANNOT_TERMINATE("Redemption product cannot be terminated since its active", 602113),
	DUPLICATE_AMAL_GROUPID ("Duplicate AMAL groupId",600029),
	INVALID_AMAL_ORDER_TYPE ("Invalid order type passed to IRB",600030),
	INVALID_AMAL_SUBSCRIPTION_REF ("Subscription reference details are not passed to IRB",600031),
	INVALID_AMAL_ACTION_CODE ("Invalid action code passed to IRB",600032),
	NULL_SRC_PRD_END_DATE("Product end date is mandatory for this product", 308050),
	PRODUCT_END_DATE_BEFORE_START_DATE("Termination date of the product cannot be before start date", 308051),
	DUPLICATE_PRODUCT_INSTANCE("Duplicate product instance not allowed", 600001),
	NO_ACTIVE_ACCOUNTS_FOR_UPDATEMODE_CUSTOMER("No Active Account Present Under the Given Customer Reference",305062),
	NO_ACTIVE_ACCOUNTS_FOR_UPDATEMODE_NATIONAL_ID("No Active Account Present Under the Given National Id",305063),
	NO_ACTIVE_ACCOUNTS_IN_BILLING("No Active Accounts Present in Billing",305064),
	INVALID_INPUT_PARAMETERS_PASSED("Invalid input parameters passed", 209014),
	PAYMENT_IS_CANCELLED_OR_FAILED("Payment is already cancelled/is a failed payment",209015),
	PAYMENT_SEQ_NOT_FOUND("No Payment sequence found",209010),
	MULTIPLE_PAYMENT_SEQ("Multiple Payment sequences found",209011),
	DUPLICATE_RESELLER_ORDER("Given Reseller Order Id alredy exist",3080115),
	DUPLICATE_MSISDN("Given Phone number alredy exist",3080116),
	DUPLICATE_CUG ("CUG group Already Exists",600007),
	DUPLICATE_VOL_DISC_PROD("Volume Discount Product(s) Already Exist",308098),
	DETAILS_NOT_FOUND("Essential details not found for the input number",415018),
	OLD_FOOTBALL_CLUB_NOT_FOUND("No details found for the old football club name ",600021),
	OVERLAP_PRODUCT("Overlap of same product type",600002),
	INVALID_ALIGN_BILLPERIOD_FLAG ("The align to bill period flag should not be null for this product",600033),
	
	
	/* 
	 *                            I       M       P       O       R       T       A       N       T         
	 * ------------------------------------------------------------------------------------------------------------------
	 * Any new error messages/codes introduced as part of refactoring should be written below this, so that later their codes can be reconciled 
	 * with the already defined error codes 
	 * 
	 * */
	ERROR_IN_REMOTE_SYSTEM("Error occurred while communicating with remote system", ErrorMessageEnum.ERROR_CODE_TECHNICAL),
	ERROR_IN_RBM_ECA_CALL("Error occurred during RBM ECA call", ErrorMessageEnum.ERROR_CODE_TECHNICAL),
	UNCATEGORIZED_ERROR("Error while processing", ErrorMessageEnum.ERROR_CODE_TECHNICAL),
	ERROR_IN_STORED_PROC_FUNC("Error occurred while executing store procedure/function",ErrorMessageEnum.ERROR_CODE_TECHNICAL),
	MISSING_SRC_PRODUCT_ATTRIBUTES("One or more product attributes are missing in the input",302150),
	MISSING_SRC_PRODUCT_ATTRIBUTE_VALUE_REQUIRED_FOR_RBM_PRODUCT ("Input SRC product has attributes required for identifying "
			+ "RBM product however attribute values do not map to any RBM product",302151),
	MISSING_CUSTOMER_AND_ACCOUNT_DETAILS("Both customer and account details are missing in the input request",300010),
	MISSING_CUSTOMER_DETAILS("Customer details are mandatory for new customer creation request",300011),
	INVALID_CUSTOMER_TYPE_SUBTYPE("Invalid customer type subtype", 300012),
	MISSING_ACCOUNT_GO_LIVE_DATE("Account go-live date is missing in the input request", 300013),
	STC_CONFIG_NOT_FOUND("Could not find config parameter", ErrorMessageEnum.ERROR_CODE_TECHNICAL),
	DUPLICATE_ACCOUNT("Account already exists with the same unique id",300014),
	INVALID_BUSINESS_UNIT_AND_CYCLE_GROUP_COMBINATION("Business unit and bill cycle group do not match", 300015),
	INVALID_NATIONAL_ID_NATIONAL_ID_TYPE_COMBINATION("Invalid nationalIdentity and nationalIdentityType combination", 300016),
	INT_IMPLEMENT_TAMAYOUZ_REVAMP_NOT_FOUND("Could not find config parameter INT_IMPLEMENT_TAMAYOUZ_REVAMP",300017),
	 NULL_ACCOUNT_NUM_FOR_ACCLEVEL_BILLING("Account number passed as null,eventhough Billing is at Account Level",300018),
	NULL_CONTACT_PRIMARY_KEY("Null value passed to contact primary key",403003),
	ERR_MSG_NODATAFOUND("No data found for account number: ",200028),
	INVALID_INPUTS_CUST_AND_ACC_BOTH_NULL("Invalid inputs,Both Customer reference and account numbers passed as null",300019),
	NULL_BANK_DATE("Bank date cannot be null", 202051),
	INVALID_3G_FLAG("Provide the value of flag as 2G or 3G only",209000),
	ADJUSTMENT_NOT_FOUND("Adjustment seq to be approved not found",209018),
	PRIMARY_EVENT_SOURCE_NOT_FOUND("Could not find primary event source in the request. This is either mobile number for GSM or phone number for LL", 302152),
	ERR_MSG_OLD_EVENT_SRC("Old event source details not found",304005),
	MISSING_TOO_DETAILS("Could not find transfer of ownership details such as source account and/or subscription", 302153),
	MISSING_TRANSFER_PRODUCT_DETAILS("Missing product transfer details", 302154),
	DUPLICATE_CUSTOMER("Customer already exists with the same customer ref", 300020),
	PROCESS_ORDER_ACTION_TYPE_FAILED("Unable to find the process order action type for the given combination of order type,order sub type,action code and reason code", 300021),
	INVALID_PROCESS_ORDER_ACTION_TYPE("Invalid Process Order Action Type Retrieved", 300022),
	ATTRIBUTE_SET_ID_NOT_FOUND("Attribute set id is not found for the provided attributes", 300023),
	MISSING_PRODUCT_MAPPING("Billing product mapping missing for the given source product", 300024),
	RATE_PLAN_PRODUCT_MISSING("Billing product mapping missing for the given source product", 300025),
	FAILURE_IN_CANCEL_ECA("Cancel Installment Agreement Failure", 300026),
	PROMISE_TO_PAY_ERROR("Promise To Pay Cannot Be Done For This Account Number",300027),
	NULL_ATTRIBUTE_VALUE("Attribute Value is null for the action setAttribute",300028),
	INVALID_BILLCYCLE("Invalid Bill Cycle passed", 300029),
	PRODUCT_NOT_FOUND("Given product not found under the given customer reference, subscription reference and supplier order number", 300029),
	INVALID_PATMENT_REF("Payment Ref for the given payment is null",300030),
	ATTRIBUTE_INPUT_NOT_FOUND("Attribute is not passed in input for fetching attribute value",300031),
	INVALID_SUPPLIER_ORDER_NUM("Supplier Order Number does not exist for given customer", 300032),
	LAST_BILL_DTM_NOT_FOUND("Last Bill Date of account not found",300032),
	INVALID_MSISDN("Given MSISDN does not exist",300033),
	INVALID_PHONENUM("Number does not exist in RBM ",300034),
	FAVOURITE_NUMBERS_EXCEED_LIMIT("Number Of Nominated Numbers Exceeded Allowed Limit Of ",300035),
	NEW_COST_BAND_VALUE_IS_NULL("Cost Band Value Passed Is Null",300036),
	CRM_PRODUCT_ATTRIBUTES_NOT_SUPPLIED("Required Product Attributes Not Supplied From Source",300037),
	INVALID_CONTRACT_DURATION("Invalid value of contract duration",300038),
	TERMINATED_PRIMARY_ACCOUNT_NUMBER("Primary account number should not be in termination status",300039),
	CONFIGURED_ATTRIBUTE_NAME_DIFFER("Attribute name from source system differs from attribute name in RBM for processing action",300040),
	INPUT_CUSTOMER_REF_ARR_NOT_FOUND("Customer reference's passed in input are not present in billing ",300041),
	IFRS_DEVICE_NOT_CONFIGURED("IFRS mapping missing for device product:",300042),
	IFRS_PACKAGE_NOT_CONFIGURED("IFRS mapping missing for device package product:",300043),
	INVALID_CONTACT_TYPE("Given Contact Type for the ContactId and AddressId is invalid",300044),
	NO_DATA_IN_CONTACTMAP_TABLE("No data found for given input in STC_BILLING_CONTACT_ADDR_MAP",300045),
	NULL_INVOICELANGUAGEID("IvoiceLanguageId cannot be bull",300047),	
	//for ViewCustomerBillCycleSummary
	NO_ACTIVE_ACCOUNT_UNDER_CUSTOMER("No active account in Billing under the customer:",300046),
	NO_RECORDS_FOUND("No active records found for given input",300053),
	NO_CONTACT_DETAILS("No active contact details found for the given input",300048),
	// Auto Adjustments CR	
	START_DATE_NOT_RCVD("Start Date attribute not received for the discount product:",300050),
	END_DATE_NOT_RCVD("Service End Date attribute not received for the discount product:",300051),
	ORDER_NUM_NOT_RCVD("Order Number not received:",300052),
	INVALID_RANGE("Invalid range product attribute",300053),
	CONTRACT_EXISTS("Valid Contract exists for the given input",300056),
	INVALID_CONTRACT("No Valid Contract exists for the given input",300057),
	CANNOT_RELEASE("Unused Balance cannot be released since Final bill is not generated",300061),
	CANNOT_RESUME("Free service remaining balance is not greater than or equal to the configured value Remaining. Free Balance , Configured Value ",300062),
	CANNOT_UPDATE_FREE_BAL("Transaction is not processed since the requested value of , is not greater than or equal to the existing value ",300059),
	CANNOT_RESERVE_FREE_BAL("Current balance of , is not sufficient to reserve the request order amount of ",300060),
	INVALID_FREE_SRV_ACCOUNT("There is no balance record available for the given billing account ",300063),
	INVALID_ACC_DETAILS_NEW_ECA("Account details not coming for new ECA down paymnet request ",300064),
	INVALID_PRICE("Invalid Price product attribute",300054),
	INVLID_PRODUCT_ATTRIBUTES("Invalid product attributes coming in the request to identify the RBM discount product",300058),
		
	// SharedPool Start
	SHAREDPOOL_DISC_NOTCONFIGURED("Discount not configured against the Shared Pool Subgroup",300055),
	SHAREDPOOL_DISC_CAP_NOTRECEIVED("End Subscriber Discount Cap not received",300056),
	// SharedPool End
	
	/*11-Jun-2018: 20180603_7 Customized Postpaid plans CP1-CP3*/
	MISSING_CURRENTPLAN_MRC("Current Plan MRC Missing for contract product",300057),
	MISSING_SADAD_DATE("SADAD date cannot be null",300065),
	
	UNCATEGORIZED_ERROR_LENGTH_ISSUE("Error while processing", ErrorMessageEnum.ERROR_CODE_TECHNICAL),
	ERROR_IN_HIBERNATE_CALL("Error during Hibernate call", ErrorMessageEnum.ERROR_CODE_TECHNICAL),
	ERROR_IN_DATAACCESS_CALL("Error during proc/Func call", ErrorMessageEnum.ERROR_CODE_TECHNICAL),
	ERROR_MESSAGE_DEFAULT_PART1("Exception at ", ErrorMessageEnum.ERROR_CODE_TECHNICAL),
	ERROR_MESSAGE_DEFAULT_PART2(". This is technical issue. Please contact technical team.", ErrorMessageEnum.ERROR_CODE_TECHNICAL),
	ERROR_MESSAGE_TIMEOUT("Transaction is not active anymore", ErrorMessageEnum.ERROR_CODE_TECHNICAL),
	NO_PENALTY("No penalties/suspension charges applicable for the given criteria", 300067),
	NO_ACTIVE_PRODUCTS("No active products found the given criteria", 300068),
	NO_PRODUCTS_EXISTS("No product/service available for the given input",300072),
	
	INVALID_DEVICE_PRODUCT_MAPPING("Device product mapping not present for the product", 300069),
	DEVICE_DETAILS_ALREADY_EXISTS("Device already exists", 300070),
	DEVICE_DETAILS_NOT_EXISTS("Device does not exists", 300071),
	
	MISSING_EPAYMENT_DATE("EPAYMENT date cannot be null",300066),
	INVALID_OVERRIDE_END_DAT("Invalid OVERRIDE END_DAT",300067);
	
	/* Following code is the basic infrastructure required for this enum, this should not be touched */
	private static final int ERROR_CODE_TECHNICAL =100005;
	private String errorMessage;
	private int errorCode;
	private ErrorMessageEnum(String errorMessage, int errorCode) {
		this.errorMessage = errorMessage;
		this.errorCode = errorCode;
	}
	public String getErrorMessage() {return this.errorMessage;}
	public int getErrorCode() {return this.errorCode;}
	
	
}
