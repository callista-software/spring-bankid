package org.callistasoftware.netcare.commons.bankid;

import java.util.Iterator;
import java.util.List;

import com.logica.mbi.service.v1_0.CollectResponseType;
import com.logica.mbi.service.v1_0.Property;

public class Collect {
	private ProgressStatus progressStatus;
    private String signature;
    
    private String name = "";
    private String givenName = "";
    private String surName = "";
    private String crn = "";
    
    public static final Collect EMPTY_RESPONSE = new Collect();

    Collect() {
    }

    public static Collect createFromEntity(final CollectResponseType response) {
    	final Collect collect = new Collect();
    	collect.setProgressStatus(ProgressStatus.valueOf(response.getProgressStatus().name()));
    	collect.setSignature(response.getSignature());
    	
    	final List<Property> attributes = response.getAttributes();
    	final Iterator<Property> it = attributes.iterator();
    	
    	while (it.hasNext()) {
    		final Property property = it.next();
    		if (property.getName().equals("Subject.CommonName")) {
    			collect.setName(property.getValue());
    			continue;
    		}
    		
    		if (property.getName().equals("Subject.GivenName")) {
    			collect.setGivenName(property.getValue());
    			continue;
    		}
    		
    		if (property.getName().equals("Subject.Surname")) {
    			collect.setSurName(property.getValue());
    			continue;
    		}
    		
    		if (property.getName().equals("Subject.SerialNumber")) {
    			collect.setCrn(property.getValue());
    			continue;
    		}
    	}
    	
        return collect;
    }
    
    void setProgressStatus(ProgressStatus progressStatus) {
        this.progressStatus = progressStatus;
    }

    void setSignature(String signature) {
        this.signature = signature;
    }

    public ProgressStatus getProgressStatus() {
        return progressStatus;
    }

    String getSignature() {
        return signature;
    }

	public String getName() {
		return name;
	}

	void setName(String name) {
		this.name = name;
	}

	public String getGivenName() {
		return givenName;
	}

	void setGivenName(String givenName) {
		this.givenName = givenName;
	}

	public String getSurName() {
		return surName;
	}

	void setSurName(String surName) {
		this.surName = surName;
	}

	public String getCrn() {
		return crn;
	}

	void setCrn(String crn) {
		this.crn = crn;
	}
}
