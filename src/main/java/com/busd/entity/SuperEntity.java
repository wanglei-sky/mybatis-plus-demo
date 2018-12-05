package com.busd.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;


@SuppressWarnings("rawtypes")
public class SuperEntity<T extends Model> extends Model<T> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	@TableId(value="id", type=IdType.AUTO)
	private Long id;
	
	

	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	@Override
	protected Serializable pkVal() {
		// TODO Auto-generated method stub
		return this.id;
	}

	
}
