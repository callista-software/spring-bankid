package org.callistasoftware.netcare.commons.bankid.service.impl;

import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.callistasoftware.netcare.commons.bankid.BankIdAuthenticationRequest;
import org.callistasoftware.netcare.commons.bankid.BankIdAuthenticationResult;
import org.callistasoftware.netcare.commons.bankid.BankIdAuthenticationResultImpl;
import org.callistasoftware.netcare.commons.bankid.Collect;
import org.callistasoftware.netcare.commons.bankid.ProgressStatus;
import org.callistasoftware.netcare.commons.bankid.service.BankIdAuthenticationService;
import org.callistasoftware.netcare.commons.bankid.service.BankIdService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

@Service
public class BankIdAuthenticationServiceImpl implements BankIdAuthenticationService {

	private Logger log = LoggerFactory.getLogger(BankIdAuthenticationServiceImpl.class);

	@Autowired private BankIdService service;
	
	private Cache<String, BankIdAuthenticationResult> mobileSessionsCache;
    
    @PostConstruct
    public void onInit() {
    	/*
    	 * FIXME Externalize property
    	 */
    	mobileSessionsCache = CacheBuilder.newBuilder()
    			.expireAfterWrite(60, TimeUnit.MINUTES).build();
    }
	
	@Override
	public BankIdAuthenticationResult authenticate(
			BankIdAuthenticationRequest request) {
		log.info("==== BankId Authentication ====");
		
		try {
			final Collect collect = service.collect(request.getOrderReference());
			if (collect.getProgressStatus().equals(ProgressStatus.COMPLETE)) {
				BankIdAuthenticationResultImpl result = BankIdAuthenticationResultImpl.newResult(collect);
				mobileSessionsCache.put(request.getOrderReference(), result);
				
				return result;
			} else {
				return null;
			}
		} catch (final Exception e) {
			log.error("Error: BankId authentication failed. Reason: {}", e.getMessage());
			return null;
		}
		
	}

	@Override
	public BankIdAuthenticationResult getAuthenticationFromCache(
			BankIdAuthenticationRequest request) {
		return mobileSessionsCache.getIfPresent(request.getOrderReference());
	}

}
