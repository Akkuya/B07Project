package com.example.s26g5;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import com.example.s26g5.data.FirebaseAuthManager;
import com.example.s26g5.user.LoginFragment;
import com.example.s26g5.user.LoginPresenter;
import com.example.s26g5.user.SignupFragment;
import com.example.s26g5.user.SignupPresenter;
import com.example.s26g5.user.UICallbackInterface;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(MockitoJUnitRunner.class)
public class ExampleUnitTest {
    @Mock
    LoginFragment view;
    @Mock
    SignupFragment viewSignup;
    @Mock
    FirebaseAuthManager model;

    @Test
    public void testLoginCredsCheck() {
        when(view.getEmail()).thenReturn("");
        when(view.getPassword()).thenReturn("123456");
        LoginPresenter presenter = new LoginPresenter(view, model);

        String message = presenter.checkCreds();
        assertEquals("Email and password must be entered", message);
    }

    @Test
    public void testLoginAuthSuccess() {
        when(view.getEmail()).thenReturn("muntaha0108@gmail.com");
        when(view.getPassword()).thenReturn("123456");
        doAnswer(invocation -> {
            UICallbackInterface callback = (UICallbackInterface) invocation.getArguments()[2];
            callback.onSuccess(null);
            return null;
        })
                .when(model).loginUser(anyString(), anyString(), any(UICallbackInterface.class) );
        LoginPresenter presenter = new LoginPresenter(view, model);

        presenter.login();
        verify(model).loginUser(
                eq("muntaha0108@gmail.com"),
                eq("123456"),
                any(UICallbackInterface.class)
        );
        verify(view).onSuccess();
    }

    @Test
    public void testLoginFailure() {
        when(view.getEmail()).thenReturn("muntaha0108@gmail.com");
        when(view.getPassword()).thenReturn("123456");
        doAnswer(invocation -> {
            UICallbackInterface callback = (UICallbackInterface) invocation.getArguments()[2];
            callback.onFailure(null);
            return null;
        })
                .when(model).loginUser(anyString(), anyString(), any(UICallbackInterface.class) );
        LoginPresenter presenter = new LoginPresenter(view, model);
        presenter.login();
        verify(view).onFailure();
    }


    @Test
    public void testSignUpCredsCheck() {
        when(viewSignup.getEmail()).thenReturn("");
        when(viewSignup.getPassword()).thenReturn("123456");
        when(viewSignup.getUsername()).thenReturn("Muntaha");
        SignupPresenter presenter = new SignupPresenter(viewSignup, model);

        String message = presenter.checkCreds();
        assertEquals("Username, email and password must not be empty", message);
    }

    @Test
    public void testSignUpAuthSuccess() {
        when(viewSignup.getEmail()).thenReturn("muntaha0108@gmail.com");
        when(viewSignup.getPassword()).thenReturn("123456");
        when(viewSignup.getUsername()).thenReturn("Muntaha");
        doAnswer(invocation -> {
            UICallbackInterface callback = (UICallbackInterface) invocation.getArguments()[4];
            callback.onSuccess(null);
            return null;
        })
                .when(model).signupUser(anyString(), anyString(), anyString(), eq("visitor"), any(UICallbackInterface.class) );
        SignupPresenter presenter = new SignupPresenter(viewSignup, model);

        presenter.signup();
        verify(model).signupUser(
                eq("muntaha0108@gmail.com"),
                eq("123456"),
                eq("Muntaha"),
                eq("visitor"),
                any(UICallbackInterface.class)
        );
        verify(viewSignup).onSuccess();
    }

    @Test
    public void testSignUpFailure() {
        when(viewSignup.getEmail()).thenReturn("muntaha0108@gmail.com");
        when(viewSignup.getPassword()).thenReturn("123456");
        when(viewSignup.getUsername()).thenReturn("Muntaha");
        doAnswer(invocation -> {
            UICallbackInterface callback = (UICallbackInterface) invocation.getArguments()[4];
            callback.onFailure(null);
            return null;
        })
                .when(model).signupUser(anyString(), anyString(), anyString(), eq("visitor"), any(UICallbackInterface.class) );
        SignupPresenter presenter = new SignupPresenter(viewSignup, model);
        presenter.signup();
        verify(viewSignup).onFailure();
    }
}