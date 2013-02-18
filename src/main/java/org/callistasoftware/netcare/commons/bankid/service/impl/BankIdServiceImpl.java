package org.callistasoftware.netcare.commons.bankid.service.impl;

import org.callistasoftware.netcare.commons.bankid.CivicRegistrationNumber;
import org.callistasoftware.netcare.commons.bankid.Collect;
import org.callistasoftware.netcare.commons.bankid.service.BankIdService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.logica.mbi.service.v1_0.AuthenticateRequestType;
import com.logica.mbi.service.v1_0.AuthenticateResponseType;
import com.logica.mbi.service.v1_0.CollectRequestType;
import com.logica.mbi.service.v1_0.CollectResponseType;
import com.logica.mbi.service.v1_0_0.MbiFault;
import com.logica.mbi.service.v1_0_0.MbiServicePortType;

@Service
public class BankIdServiceImpl implements BankIdService {
        
	private static Logger log = LoggerFactory.getLogger(BankIdServiceImpl.class);
    private MbiServicePortType rpServicePortType;

    @Value("${bankid.displayName}")
    private String displayName;
    
    @Value("${bankid.serviceId}")
    private String serviceId;
    
    @Autowired
    public void setRpServicePortType(final MbiServicePortType rpServicePortType) {
    	this.rpServicePortType = rpServicePortType;
    }

    @Override
    public String authenticate(CivicRegistrationNumber crn) throws Exception {
        if (crn == null) {
            throw new IllegalArgumentException("Personummer får inte vara null");
        }

        String orderRef = null;
        try {
        	log.info("Authenticating against bank id server using {}", crn.getValue());
        	
        	final AuthenticateRequestType rType = new AuthenticateRequestType();
        	rType.setDisplayName(this.displayName);
        	rType.setPolicy(this.serviceId);
        	rType.setPersonalNumber(crn.getValue());
        	
        	final AuthenticateResponseType responseType = rpServicePortType.authenticate(rType);
        	orderRef = responseType.getOrderRef();
        } catch (MbiFault e) {
            throw new Exception(e.getMessage(), e);
        }
        
        log.info("Returning orderref {}", orderRef);
        return orderRef;
    }
    
    @Override
    public Collect collect(String orderRef) throws Exception {
        if(orderRef == null) {
            throw new IllegalArgumentException("OrderRef får inte vara null");
        }
        CollectResponseType response = null;
        try {
        	final CollectRequestType crt = new CollectRequestType();
        	crt.setDisplayName(this.displayName);
        	crt.setOrderRef(orderRef);
        	crt.setPolicy(this.serviceId);
        	
            response = rpServicePortType.collect(crt);
        } catch (MbiFault e) {
            throw new Exception(e.getMessage(), e);
        }
        return Collect.createFromEntity(response);
    }
    
}
