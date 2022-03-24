package com.wohr.mocks.testCase;

import lombok.Data;

@Data
public class LoginService {

	private LoginDao loginDao;
	private String currentUser;

	public boolean login(UserForm userForm) {
		assert null != userForm;

		int loginResults = loginDao.login(userForm);

		switch (loginResults) {
		case 1:
			return true;
		default:
			return false;
		}
	}
}
