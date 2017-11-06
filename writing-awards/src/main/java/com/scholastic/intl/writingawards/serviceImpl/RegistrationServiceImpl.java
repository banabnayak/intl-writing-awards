package com.scholastic.intl.writingawards.serviceImpl;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.scholastic.intl.writingawards.dao.impl.RegistrationDAOImpl;
import com.scholastic.intl.writingawards.dao.impl.UserDAOImpl;
import com.scholastic.intl.writingawards.entity.School;
import com.scholastic.intl.writingawards.entity.Student;
import com.scholastic.intl.writingawards.entity.User;
import com.scholastic.intl.writingawards.helper.PasswordUtil;
import com.scholastic.intl.writingawards.model.EmailMessageVO;
import com.scholastic.intl.writingawards.model.StudentRegistrationVO;
import com.scholastic.intl.writingawards.service.RegistrationService;
import com.scholastic.intl.writingawards.service.SchoolService;

@Stateless
public class RegistrationServiceImpl implements RegistrationService {

	private static final Logger LOGGER = LoggerFactory.getLogger(RegistrationServiceImpl.class);

	@Inject
	RegistrationDAOImpl registrationDAOImpl;

	@Inject
	SchoolService schoolService;

	@Inject
	UserDAOImpl userDAOImpl;

	@Inject
	EmailServiceImpl emailServiceImpl;

	@Override
	public String registerStudent(StudentRegistrationVO studentVO) {
		String userExist = null;
		LOGGER.info("1Service:parent email:" + studentVO.getStudent().getParentEmail());
		Student student = studentVO.getStudent();
		School school = studentVO.getSchool();
		User user = new User();
		Set<Student> students = new HashSet<Student>();

		String userPassword = PasswordUtil.getPassword();
		if (student != null
				&& registrationDAOImpl.getStudentByEmail(student.getParentEmail()) == null) {
			LOGGER.info("2Service:parent email:" + studentVO.getStudent().getParentEmail());

			student.setCreatedDate(new Date());
			String regNo = PasswordUtil.getPassword();

			student.setRegNo(regNo);
			student.setDeleted(Boolean.FALSE);
			user.setFullName(studentVO.getStudent().getFullName());
			user.setCreatedDate(new Date());
			user.setUserId(student.getParentEmail());
			user.setPassword(userPassword);
			user.setRole("Student");
			user.setRegNo(student.getRegNo());
			user.setDeleted(Boolean.FALSE);

		} else {
			// Need error message to send back.
			if (student != null) {
				userExist = "User with email-id \"" + student.getParentEmail()
						+ "\" is already exist..!!";
			}
			return userExist;
		}

		if (school != null) {
			school.setDeleted(Boolean.FALSE);
			school.setNetworkSchool(Boolean.FALSE);
			students.add(student);
			School schoolExist = schoolService.saveSchool(school);
			student.setSchool(schoolExist);
		}

		registrationDAOImpl.save(student);
		userDAOImpl.save(user);

		final EmailMessageVO emailMessageVO = emailServiceImpl.setEmailMessageVO(user,
				"registration");
		emailServiceImpl.sendEmail(emailMessageVO);

		return userExist;
	}

	@Override
	public Student getStudent(Long studentId) {
		return null;
	}

}
