package com.wohr.mocks.mockito;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import com.wohr.mocks.testCase.LoginController;
import com.wohr.mocks.testCase.LoginDao;
import com.wohr.mocks.testCase.LoginService;
import com.wohr.mocks.testCase.UserForm;

//@RunWith(MockitoJUnitRunner.class)
public class LoginControllerIntegrationTest {

//    @Mock
//    private LoginDao loginDao;
//
//    @Spy
//    @InjectMocks
//    private LoginService spiedLoginService;

    @Mock
    private LoginService loginService;

    @InjectMocks
    private LoginController loginController;
    
//    @InjectMocks
//    private LoginController loginController = new LoginController();

    @Before
    public void setUp() {
        loginController = new LoginController();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void assertThatNoMethodHasBeenCalled() {
        loginController.login(null);
        // no method called?
        Mockito.verifyNoInteractions(loginService);
    }

    @Test
    public void assertTwoMethodsHaveBeenCalled() {
        UserForm userForm = new UserForm();
        userForm.setUsername("foo");
        Mockito.when(loginService.login(userForm))
            .thenReturn(true);

        String login = loginController.login(userForm);

        Assert.assertEquals("OK", login);
        Mockito.verify(loginService)
            .login(userForm);
        Mockito.verify(loginService)
            .setCurrentUser("foo");
    }

    @Test
    public void assertOnlyOneMethodHasBeenCalled() {
        UserForm userForm = new UserForm();
        userForm.setUsername("foo");
        Mockito.when(loginService.login(userForm))
            .thenReturn(false);

        String login = loginController.login(userForm);

        Assert.assertEquals("KO", login);
        Mockito.verify(loginService)
            .login(userForm);
        Mockito.verifyNoMoreInteractions(loginService);
    }

    @Test
    public void mockExceptionThrowing() {
        UserForm userForm = new UserForm();
        Mockito.when(loginService.login(userForm))
            .thenThrow(IllegalArgumentException.class);

        String login = loginController.login(userForm);

        Assert.assertEquals("ERROR", login);
        Mockito.verify(loginService)
            .login(userForm);
        Mockito.verifyNoMoreInteractions(loginService);
    }

    @Test
    public void mockAnObjectToPassAround() {
        UserForm userForm = Mockito.when(Mockito.mock(UserForm.class)
            .getUsername())
            .thenReturn("foo")
            .getMock();
        Mockito.when(loginService.login(userForm))
            .thenReturn(true);

        String login = loginController.login(userForm);

        Assert.assertEquals("OK", login);
        Mockito.verify(loginService)
            .login(userForm);
        Mockito.verify(loginService)
            .setCurrentUser("foo");
    }

    @Test
    public void argumentMatching() {
        UserForm userForm = new UserForm();
        userForm.setUsername("foo");
        // default matcher
        Mockito.when(loginService.login(Mockito.any(UserForm.class)))
            .thenReturn(true);

        String login = loginController.login(userForm);

        Assert.assertEquals("OK", login);
        Mockito.verify(loginService)
            .login(userForm);
        // complex matcher
        Mockito.verify(loginService)
            .setCurrentUser(ArgumentMatchers.argThat(new ArgumentMatcher<String>() {
                @Override
                public boolean matches(String argument) {
                    return argument.startsWith("foo");
                }
            }));
    }

    @Test
    public void partialMocking() {
        // use partial mock
        loginController.setLoginService(spiedLoginService);
        
        UserForm userForm = new UserForm();
        userForm.setUsername("foo");
        // let service's login use implementation so let's mock DAO call
        Mockito.when(loginDao.login(userForm))
            .thenReturn(null);
        
        String login = loginController.login(userForm);

        Assert.assertEquals("OK", login);
        // verify mocked call
        Mockito.verify(spiedLoginService)
            .login(userForm);
    }
}
