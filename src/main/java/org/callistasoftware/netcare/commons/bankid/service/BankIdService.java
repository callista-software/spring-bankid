package org.callistasoftware.netcare.commons.bankid.service;

import org.callistasoftware.netcare.commons.bankid.CivicRegistrationNumber;
import org.callistasoftware.netcare.commons.bankid.Collect;

public interface BankIdService {
	/**
	 * Initiate a bank id session
	 * @param crn
	 * @return
	 * @throws Exception
	 */
	 String authenticate(CivicRegistrationNumber crn) throws Exception;
	 
	 /**
	  * Completes the bank id authentication flow
	  * @param orderRef
	  * @return
	  * @throws Exception
	  */
	 Collect collect(String orderRef) throws Exception;
}
