/**
 * 
 */
package com.omg.XmlBean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/** 
* @author cyb
* @data 2018年8月30日 下午4:20:14
*/
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="Request")
@XmlType
public class Request {
	
	@XmlElement(name="policyList")
	private PolicyList policyList;
	
	@XmlElement(name="transactionid")
	private String transactionId;
	
	@XmlElement(name="telephone")
	private String telephone;
	
	@XmlElement(name="taxpayertype")
	private String taxpayerType;
	
	@XmlElement(name="billopenenum")
	private String billopenEnum;
	
	@XmlElement(name="buymailbox")
	private String buymailBox;
	
	@XmlElement(name="cusType")
	private String cusType;
	
	@XmlElement(name="custtaxno")
	private String custTaxNo;
	
	@XmlElement(name="cusName")
	private String cusName;
	
	@XmlElement(name="custadd")
	private String custadd;
	
	@XmlElement(name="account")
	private String account;
	
	@XmlElement(name="bankName")
	private String bankName;


	/**
	 * 获取  transactionId
	 * @return the transactionId
	 */
	public String getTransactionId() {
		return transactionId;
	}

	/**
	 * @param transactionId the transactionId to set
	 */
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	/**
	 * 获取  telephone
	 * @return the telephone
	 */
	public String getTelephone() {
		return telephone;
	}

	/**
	 * @param telephone the telephone to set
	 */
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	/**
	 * 获取  taxpayerType
	 * @return the taxpayerType
	 */
	public String getTaxpayerType() {
		return taxpayerType;
	}

	/**
	 * @param taxpayerType the taxpayerType to set
	 */
	public void setTaxpayerType(String taxpayerType) {
		this.taxpayerType = taxpayerType;
	}

	/**
	 * 获取  billopenEnum
	 * @return the billopenEnum
	 */
	public String getBillopenEnum() {
		return billopenEnum;
	}

	/**
	 * @param billopenEnum the billopenEnum to set
	 */
	public void setBillopenEnum(String billopenEnum) {
		this.billopenEnum = billopenEnum;
	}

	/**
	 * 获取  buymailBox
	 * @return the buymailBox
	 */
	public String getBuymailBox() {
		return buymailBox;
	}

	/**
	 * @param buymailBox the buymailBox to set
	 */
	public void setBuymailBox(String buymailBox) {
		this.buymailBox = buymailBox;
	}

	/**
	 * 获取  cusType
	 * @return the cusType
	 */
	public String getCusType() {
		return cusType;
	}

	/**
	 * @param cusType the cusType to set
	 */
	public void setCusType(String cusType) {
		this.cusType = cusType;
	}

	/**
	 * 获取  custTaxNo
	 * @return the custTaxNo
	 */
	public String getCustTaxNo() {
		return custTaxNo;
	}

	/**
	 * @param custTaxNo the custTaxNo to set
	 */
	public void setCustTaxNo(String custTaxNo) {
		this.custTaxNo = custTaxNo;
	}

	/**
	 * 获取  cusName
	 * @return the cusName
	 */
	public String getCusName() {
		return cusName;
	}

	/**
	 * @param cusName the cusName to set
	 */
	public void setCusName(String cusName) {
		this.cusName = cusName;
	}

	/**
	 * 获取  custadd
	 * @return the custadd
	 */
	public String getCustadd() {
		return custadd;
	}

	/**
	 * @param custadd the custadd to set
	 */
	public void setCustadd(String custadd) {
		this.custadd = custadd;
	}

	/**
	 * 获取  account
	 * @return the account
	 */
	public String getAccount() {
		return account;
	}

	/**
	 * @param account the account to set
	 */
	public void setAccount(String account) {
		this.account = account;
	}

	/**
	 * 获取  bankName
	 * @return the bankName
	 */
	public String getBankName() {
		return bankName;
	}

	/**
	 * @param bankName the bankName to set
	 */
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public PolicyList getPolicyList() {
		return policyList;
	}

	public void setPolicyList(PolicyList policyList) {
		this.policyList = policyList;
	}
}
