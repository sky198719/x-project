package com.xxdai.client;

import java.io.IOException;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;

import org.apache.ws.security.WSPasswordCallback;

import com.xxdai.util.Configuration;



public class ClientValidate implements CallbackHandler {

	public void handle(Callback[] callbacks) throws IOException,UnsupportedCallbackException {
			WSPasswordCallback pc = (WSPasswordCallback) callbacks[0];
			pc.setPassword(Configuration.getInstance().getValue("client"));
	}
}
