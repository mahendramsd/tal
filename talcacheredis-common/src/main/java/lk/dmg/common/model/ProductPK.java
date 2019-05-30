/*
 *  Copyright (c) 1995-2018,  The Data Management Group Ltd   All Rights Reserved.
 *  *  PROPRIETARY AND COPYRIGHT NOTICE.
 *
 *    This software product contains information which is proprietary to
 *    and considered a trade secret The Data management Group Ltd .
 *    It is expressly agreed that it shall not be reproduced in whole or part,
 *    disclosed, divulged or otherwise made available to any third party directly
 *    or indirectly.  Reproduction of this product for any purpose is prohibited
 *    without written authorisation from the The Data Management Group Ltd
 *    All Rights Reserved.
 *
 *    E-Mail andyj@datam.co.uk
 *    URL : www.datam.co.uk
 *    Created By : Mahendra Sri Dayarathna
 *
 */
package lk.dmg.common.model;

import java.io.Serializable;

/**
 * The primary key class for the products database table.
 * 
 */

public class ProductPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private String productcode;
	private String roomcode;
	private String subhotelcode;
	private java.util.Date fromdate;
	private java.util.Date todate;

	public ProductPK() {
	}
	public String getProductcode() {
		return this.productcode;
	}
	public void setProductcode(String productcode) {
		this.productcode = productcode;
	}
	public String getRoomcode() {
		return this.roomcode;
	}
	public void setRoomcode(String roomcode) {
		this.roomcode = roomcode;
	}
	public String getSubhotelcode() {
		return this.subhotelcode;
	}
	public void setSubhotelcode(String subhotelcode) {
		this.subhotelcode = subhotelcode;
	}
	public java.util.Date getFromdate() {
		return this.fromdate;
	}
	public void setFromdate(java.util.Date fromdate) {
		this.fromdate = fromdate;
	}
	public java.util.Date getTodate() {
		return this.todate;
	}
	public void setTodate(java.util.Date todate) {
		this.todate = todate;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof ProductPK)) {
			return false;
		}
		ProductPK castOther = (ProductPK)other;
		return 
			this.productcode.equals(castOther.productcode)
			&& this.roomcode.equals(castOther.roomcode)
			&& this.subhotelcode.equals(castOther.subhotelcode)
			&& this.fromdate.equals(castOther.fromdate)
			&& this.todate.equals(castOther.todate);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.productcode.hashCode();
		hash = hash * prime + this.roomcode.hashCode();
		hash = hash * prime + this.subhotelcode.hashCode();
		hash = hash * prime + this.fromdate.hashCode();
		hash = hash * prime + this.todate.hashCode();
		
		return hash;
	}
}