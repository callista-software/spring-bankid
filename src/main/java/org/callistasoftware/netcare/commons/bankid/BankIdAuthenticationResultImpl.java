package org.callistasoftware.netcare.commons.bankid;

public class BankIdAuthenticationResultImpl implements BankIdAuthenticationResult {

	private final String name;
	private final String firstName;
	private final String surName;
	private final String civicRegistrationNumber;
	
	BankIdAuthenticationResultImpl(final String name, final String firstName, final String surName, final String crn) {
		this.name = name;
		this.firstName = firstName;
		this.surName = surName;
		this.civicRegistrationNumber = crn;
	}
	
	public static BankIdAuthenticationResultImpl newResult(final Collect collect) {
		return new BankIdAuthenticationResultImpl(collect.getName()
				, collect.getGivenName()
				, collect.getSurName()
				, collect.getCrn());
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public String getFirstName() {
		return this.firstName;
	}

	@Override
	public String getSurName() {
		return this.surName;
	}

	@Override
	public String getCivicRegistrationNumber() {
		return this.civicRegistrationNumber;
	}
}
