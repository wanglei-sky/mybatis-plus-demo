package com.busd.entity;

import java.sql.Timestamp;

import com.baomidou.mybatisplus.annotation.TableName;

@TableName("t_ada_account")
public class AdaAccount extends SuperEntity<AdaAccount>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5568672316192929435L;

	
	private String accountId;
	
	private String accountName;
	
	private String address;
	
	private Timestamp createTime;
	



	public String getAccountId() {
		return accountId;
	}



	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}



	public String getAccountName() {
		return accountName;
	}



	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}



	public String getAddress() {
		return address;
	}



	public void setAddress(String address) {
		this.address = address;
	}



	public Timestamp getCreateTime() {
		return createTime;
	}



	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}


	
}
