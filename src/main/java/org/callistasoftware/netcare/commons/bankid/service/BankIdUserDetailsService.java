package org.callistasoftware.netcare.commons.bankid.service;

import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

public interface BankIdUserDetailsService extends AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken> {

}
