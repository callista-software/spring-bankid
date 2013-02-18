package org.callistasoftware.netcare.commons.bankid.service.impl;

import org.callistasoftware.netcare.commons.bankid.BankIdAuthenticationCallback;
import org.callistasoftware.netcare.commons.bankid.BankIdAuthenticationResult;
import org.callistasoftware.netcare.commons.bankid.service.BankIdUserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class BankIdUserDetailsServiceImpl implements BankIdUserDetailsService {

	private BankIdAuthenticationCallback callback;
	private static final Logger log = LoggerFactory.getLogger(BankIdUserDetailsServiceImpl.class);
	
	
	@Autowired
	public void setCallback(final BankIdAuthenticationCallback callback) {
		this.callback = callback;
	}
	
	@Override
	public UserDetails loadUserDetails(PreAuthenticatedAuthenticationToken authToken)
			throws UsernameNotFoundException {
		log.info("Retrieve information about user. The user is pre-authenticated...");
		
		final BankIdAuthenticationResult preAuthenticated;
		if (authToken.getPrincipal() instanceof BankIdAuthenticationResult) {
			log.debug("Not yet authenticated. We have an authentication result from BankId.");
			preAuthenticated = (BankIdAuthenticationResult) authToken.getPrincipal();
		} else {
			return this.callback.verifyPrincipal(authToken.getPrincipal());
		}
		
		/*
		 * We have an authentication result. Let's deal with it
		 */
		try {
			return this.callback.lookupPrincipal(preAuthenticated);
		} catch (final Exception e) {
			throw new UsernameNotFoundException("Username " + preAuthenticated.getCivicRegistrationNumber() + " is not found");
		}
	}	

}
