/**
 * 
 */
package com.omg.XmlBean;

import javax.xml.bind.annotation.*;

/** 
* @author cyb
* @data 2018年8月30日 下午4:58:19
*/
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="policynumber")
@XmlType
public class Policynumber {
	private String policynumber;

	public String getPolicynumber() {
		return policynumber;
	}

	public void setPolicynumber(String policynumber) {
		this.policynumber = policynumber;
	}
}
