package org.callistasoftware.netcare.commons.bankid;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface BankIdAuthenticationCallback {
	/**
	 * If the user is non existing and the component
	 * is configured to automatically create non
	 * existing users. This method will allow creation
	 * of such users.
	 * @return
	 */
	UserDetails createMissingUser(final BankIdAuthenticationResult auth);
	
	/**
	 * This method is responsible for casting the principal to
	 * correct type.
	 * 
	 * The method MUST return null if the type of the principal
	 * could not be determined
	 * 
	 * @param principal
	 * @return
	 */
	UserDetails verifyPrincipal(final Object principal);
	
	/**
	 * This method is responsible for checking the authentication
	 * result and lookup the user based on what is found.
	 * 
	 * 
	 * @param auth
	 * @throws UsernameNotFoundException if the username could not be found
	 * @return never null, the principal if exist
	 */
	UserDetails lookupPrincipal(final BankIdAuthenticationResult auth) throws UsernameNotFoundException;
}
