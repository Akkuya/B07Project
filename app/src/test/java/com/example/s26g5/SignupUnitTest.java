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
public class SignupUnitTest {
    @Mock
    SignupFragment view;
    @Mock
    FirebaseAuthManager model;

    @Test
    public void testSignUpCredsCheckEmailFail() {
        when(view.getEmail()).thenReturn("");
        when(view.getPassword()).thenReturn("123456");
        when(view.getUsername()).thenReturn("Muntaha");
        SignupPresenter presenter = new SignupPresenter(view, model);

        String message = presenter.checkCreds();
        assertEquals("Username, email and password must not be empty", message);
    }

    @Test
    public void testSignUpCredsCheckUsernameFail() {
        when(view.getEmail()).thenReturn("muntaha0108@gmail.com");
        when(view.getPassword()).thenReturn("123456");
        when(view.getUsername()).thenReturn("");
        SignupPresenter presenter = new SignupPresenter(view, model);

        String message = presenter.checkCreds();
        assertEquals("Username, email and password must not be empty", message);
    }

    @Test
    public void testSignUpCredsCheckPwdEmptyFail() {
        when(view.getEmail()).thenReturn("muntaha0108@gmail.com");
        when(view.getPassword()).thenReturn("");
        when(view.getUsername()).thenReturn("Muntaha");
        SignupPresenter presenter = new SignupPresenter(view, model);

        String message = presenter.checkCreds();
        assertEquals("Username, email and password must not be empty", message);
    }

    @Test
    public void testSignUpCredsCheckPwdSmallFail() {
        when(view.getEmail()).thenReturn("muntaha0108@gmail.com");
        when(view.getPassword()).thenReturn("1234");
        when(view.getUsername()).thenReturn("Muntaha");
        SignupPresenter presenter = new SignupPresenter(view, model);

        String message = presenter.checkCreds();
        assertEquals("Length of password must be greater than 5", message);
    }

    @Test
    public void testSignUpCredsCheckSuccess() {
        when(view.getEmail()).thenReturn("muntaha0108@gmail.com");
        when(view.getPassword()).thenReturn("123456");
        when(view.getUsername()).thenReturn("Muntaha");
        SignupPresenter presenter = new SignupPresenter(view, model);

        String message = presenter.checkCreds();
        assertEquals(null, message);
    }

    @Test
    public void testSignUpAuthSuccess() {
        when(view.getEmail()).thenReturn("muntaha0108@gmail.com");
        when(view.getPassword()).thenReturn("123456");
        when(view.getUsername()).thenReturn("Muntaha");
        doAnswer(invocation -> {
            UICallbackInterface callback = (UICallbackInterface) invocation.getArguments()[4];
            callback.onSuccess(null);
            return null;
        })
                .when(model).signupUser(anyString(), anyString(), anyString(), eq("visitor"), any(UICallbackInterface.class) );
        SignupPresenter presenter = new SignupPresenter(view, model);

        presenter.signup();
        verify(model).signupUser(
                eq("muntaha0108@gmail.com"),
                eq("123456"),
                eq("Muntaha"),
                eq("visitor"),
                any(UICallbackInterface.class)
        );
        verify(view).onSuccess();
    }

    @Test
    public void testSignUpFailure() {
        when(view.getEmail()).thenReturn("muntaha0108@gmail.com");
        when(view.getPassword()).thenReturn("123456");
        when(view.getUsername()).thenReturn("Muntaha");
        doAnswer(invocation -> {
            UICallbackInterface callback = (UICallbackInterface) invocation.getArguments()[4];
            callback.onFailure(null);
            return null;
        })
                .when(model).signupUser(anyString(), anyString(), anyString(), eq("visitor"), any(UICallbackInterface.class) );
        SignupPresenter presenter = new SignupPresenter(view, model);
        presenter.signup();
        verify(view).onFailure();
    }
}