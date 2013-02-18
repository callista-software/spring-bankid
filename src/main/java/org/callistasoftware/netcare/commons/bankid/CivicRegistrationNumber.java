package org.callistasoftware.netcare.commons.bankid;

public class CivicRegistrationNumber {

	private String value;

    public CivicRegistrationNumber(String value) {
    	validate(value);
    	this.value = value;
    }

    protected void validate(String value) {
        if (value.length() != 12) {
                throw new RuntimeException("Personnummer has length <> 12");
        }
    }

    public String getValue() {
    	return this.value;
    }
}
