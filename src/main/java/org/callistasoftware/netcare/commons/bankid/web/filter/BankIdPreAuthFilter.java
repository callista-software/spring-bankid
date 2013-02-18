package org.callistasoftware.netcare.commons.bankid.web.filter;

import javax.servlet.http.HttpServletRequest;

import org.callistasoftware.netcare.commons.bankid.BankIdAuthenticationRequest;
import org.callistasoftware.netcare.commons.bankid.BankIdAuthenticationResult;
import org.callistasoftware.netcare.commons.bankid.service.BankIdAuthenticationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

public class BankIdPreAuthFilter extends AbstractPreAuthenticatedProcessingFilter {

	private static final Logger log = LoggerFactory.getLogger(BankIdPreAuthFilter.class);
    
    @Autowired
    private BankIdAuthenticationService authService;

    @Override
    protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
            return "n/a";
    }

    @Override
    protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
        log.info("Getting preauthenticated principal");
        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        
        log.debug("Current authentication is: {}", auth);
        
        if (auth != null) {
        	log.info("Already authenticated with bank id. Return existing session");
        	return auth;
        }
        
        /*
         * In the first step the client have issued an authentication request which
         * basically creates a session at the auth provider.
         * 
         * The client then uses his bankid client and authenticates.
         * 
         * On successful authentication the client must tell us that the
         * auth was successful and we need to verify this.
         * 
         * Therfore, we now expect an order id and the only thing we need to
         * do is to validate this order and see if the user have a valid session
         * at the auth provider. If there is a session, check which user it is and
         * lookup him up.
         * 
         */
        
        /*
         * First step is to ask for authentication
         */
        final String order = request.getHeader("X-netcare-order");
        if (order != null) {
            log.debug("Orderref parameter found...");
            final BankIdAuthenticationRequest req = new BankIdAuthenticationRequest() {
				@Override
				public String getOrderReference() {
					return order;
				}
			};
            
            final BankIdAuthenticationResult result = authService.getAuthenticationFromCache(req);
            if (result != null) {
            	log.debug("User already authenticated. Return cached instance...");
            	return result;
            }
            
            try {
        		return authService.authenticate(new BankIdAuthenticationRequest() {
					
					@Override
					public String getOrderReference() {
						return order;
					}
				});
        	} catch (final Exception e) {
            	throw new RuntimeException(e);
            }
        } else {
        	throw new IllegalStateException("No X-netcare-order header.");
        }
    }
}
