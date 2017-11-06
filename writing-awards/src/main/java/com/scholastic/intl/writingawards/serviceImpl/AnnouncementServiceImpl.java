package com.scholastic.intl.writingawards.serviceImpl;

import java.util.List;

import javax.inject.Inject;

import com.scholastic.intl.writingawards.dao.impl.StudentDAOImpl;
import com.scholastic.intl.writingawards.entity.Student;
import com.scholastic.intl.writingawards.entity.User;
import com.scholastic.intl.writingawards.model.EmailMessageVO;
import com.scholastic.intl.writingawards.service.AnnouncementService;

public class AnnouncementServiceImpl implements AnnouncementService {

	@Inject
	EmailServiceImpl emailServiceImpl;

	@Inject
	StudentDAOImpl studentDAOImpl;

	@Override
	public void sendAnnouncements() {
		User user = null;
		List<Student> students = studentDAOImpl.getAllStudents();
		for (Student student : students) {
			user = new User();
			user.setId(student.getId());
			user.setUserId(student.getParentEmail());
			EmailMessageVO emailMessageVO = emailServiceImpl.setEmailMessageVO(user,
					"result_announcment");
			emailServiceImpl.sendEmail(emailMessageVO);
		}

	}

}
