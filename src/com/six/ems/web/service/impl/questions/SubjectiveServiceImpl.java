package com.six.ems.web.service.impl.questions;

import java.util.List;

import com.six.ems.dao.impl.questions.SubjectiveDAOImpl;
import com.six.ems.dao.interfaces.questions.SubjectiveDAO;
import com.six.ems.entity.tables.Subjective;
import com.six.ems.utils.CollectionUtils;
import com.six.ems.web.service.interfaces.questions.SubjectiveService;

public class SubjectiveServiceImpl implements SubjectiveService {
	private SubjectiveDAO subjectiveDAO = new SubjectiveDAOImpl();
	
	@Override
	public List<Subjective> getAllSubjective() {

		return subjectiveDAO.getAllSubjective();
	}

	@Override
	public Integer deleteSubjective(Integer subjectiveId) {

		return subjectiveDAO.deleteSubjective(subjectiveId);
	}

	@Override
	public Integer addSubjective(Subjective subjective) {

		return subjectiveDAO.addSubjective(subjective);
	}

	@Override
	public Integer updateSubjective(Subjective subjective) {

		return subjectiveDAO.updateSubjective(subjective);
	}

	@Override
	public Subjective getSubjectiveBySubId(Integer subId) {
		
		return subjectiveDAO.getSubjectiveBySubId(subId);
	}

	@Override
	public int total() {
		return subjectiveDAO.total();
	}

	@Override
	public String sbujectiveQuery(int page, int rows) {
		List<Subjective> list=subjectiveDAO.sbujectiveQuery(page, rows);
		int total=subjectiveDAO.getAllSubjective().size();
		String json=CollectionUtils.creatDataGritJson(list,total);
		return json;
	}

	public List<Subjective> getSubjectiveByCourseId(Integer courseId) {
		return subjectiveDAO.getSubjectiveByCourseId(courseId);
	}

}
