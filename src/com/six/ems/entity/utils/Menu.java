package com.six.ems.entity.utils;

import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

public class Menu {
	@JSONField(name="basic")
	private List<MenuItem> list;

	public List<MenuItem> getList() {
		return list;
	}

	public void setList(List<MenuItem> list) {
		this.list = list;
	}

}
