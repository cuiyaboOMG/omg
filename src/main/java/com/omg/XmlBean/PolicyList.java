/**
 * 
 */
package com.omg.XmlBean;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/** 
* @author cyb
* @data 2018年8月30日 下午4:17:38
*/
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="PolicyList")
@XmlType
public class PolicyList {
	private List<String> policynumber;

	public List<String> getPolicynumber() {
		return policynumber;
	}

	public void setPolicynumber(List<String> policynumber) {
		this.policynumber = policynumber;
	}
}
