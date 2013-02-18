package org.callistasoftware.netcare.commons.bankid.service;

import org.callistasoftware.netcare.commons.bankid.BankIdAuthenticationRequest;
import org.callistasoftware.netcare.commons.bankid.BankIdAuthenticationResult;

public interface BankIdAuthenticationService {
	
	BankIdAuthenticationResult authenticate(final BankIdAuthenticationRequest request);
	
	BankIdAuthenticationResult getAuthenticationFromCache(final BankIdAuthenticationRequest request);
	
}
