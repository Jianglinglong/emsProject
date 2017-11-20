package com.six.ems.web.service.impl.questions;

import java.util.List;

import com.six.ems.dao.impl.questions.BlankDAOImpl;
import com.six.ems.dao.interfaces.questions.BlankDAO;
import com.six.ems.entity.tables.Blank;
import com.six.ems.utils.CollectionUtils;
import com.six.ems.web.service.interfaces.questions.BlankService;

public class BlankServiceImpl implements BlankService {

	 private BlankDAO blankDAO=  new BlankDAOImpl();
	@Override
	public List<Blank> getAllBlank() {
		
		return blankDAO.getAllBlank();
	}

	@Override
	public Integer deleteBlank(Integer blankId) {

		return blankDAO.deleteBlank(blankId);
	}

	@Override
	public Integer addBlank(Blank blank) {
	
		return blankDAO.addBlank(blank);
	}

	@Override
	public Integer updateBlank(Blank blank) {

		return blankDAO.updateBlank(blank);
	}

	@Override
	public int total() {
		
		return blankDAO.total();
	}

	@Override
	public String blankQuery(int page, int rows) {
		// 调用方法查询信息的总记录数
		List<Blank> list = blankDAO.blankQuery(page, rows);
		int total = blankDAO.getAllBlank().size();
		String json = CollectionUtils.creatDataGritJson(list, total);
		// 返回json字符串
		return json;   	
	}

	@Override
	public Blank getBlankByBlankId(Integer blankId) {

		return blankDAO.getBlankByBlankId(blankId);
	}

	@Override
	public List<Blank> getBlankByCourseId(Integer courseId) {

		return blankDAO.getBlankByCourseId(courseId);
	}
}


