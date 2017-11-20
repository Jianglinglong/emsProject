package com.six.ems.web.service.impl.paper;

import java.util.List;

import com.six.ems.dao.impl.papers.PaperDAOImpl;
import com.six.ems.dao.interfaces.papers.PaperDAO;
import com.six.ems.entity.tables.Paper;
import com.six.ems.web.service.interfaces.paper.PaperService;

public class PaperServiceImpl implements PaperService {
	private PaperDAO paperDAO = new PaperDAOImpl();
	
	@Override
	public Paper getPaperById(Integer paperId) {

		return paperDAO.getPaperByID(paperId);
	}

	@Override
	public List<Paper> getAllPapers() {

		return paperDAO.getAllPapers();
	}

	@Override
	public int deletePaper(Integer paperId) {
		
		return paperDAO.deletePaper(paperId);
	}

	@Override
	public int addPaper(Paper paper) {

		return paperDAO.addPaper(paper);
	}

	@Override
	public int updatePaper(Paper paper) {

		return paperDAO.updatePaper(paper);
	}

	@Override
	public List<Paper> getPagePapers(Integer pageSize, Integer page) {

		return paperDAO.getPagePapers(pageSize, page);
	}

	@Override
	public List<Paper> getPaperByRuleId(Integer ruleId) {

		return paperDAO.getPaperByRuleId(ruleId);
	}

	@Override
	public List<Paper> getPaperByCourseId(Integer courseId) {

		return paperDAO.getPaperByCourseId(courseId);
	}

}
