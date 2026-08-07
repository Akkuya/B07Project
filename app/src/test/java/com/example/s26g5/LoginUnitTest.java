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
import com.example.s26g5.user.UICallbackInterface;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(MockitoJUnitRunner.class)
public class LoginUnitTest {
    @Mock
    LoginFragment view;
    @Mock
    FirebaseAuthManager model;

    @Test
    public void testLoginCredsCheckSuccess() {
        when(view.getEmail()).thenReturn("muntaha0108@gmail.com");
        when(view.getPassword()).thenReturn("123456");
        LoginPresenter presenter = new LoginPresenter(view, model);

        String message = presenter.checkCreds();
        assertEquals(null, message);
    }

    @Test
    public void testLoginCredsCheckEmailFail() {
        when(view.getEmail()).thenReturn("");
        when(view.getPassword()).thenReturn("123456");
        LoginPresenter presenter = new LoginPresenter(view, model);

        String message = presenter.checkCreds();
        assertEquals("Email and password must be entered", message);
    }

    @Test
    public void testLoginCredsCheckPwdFail() {
        when(view.getEmail()).thenReturn("muntaha0108@gmail.com");
        when(view.getPassword()).thenReturn("");
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

}