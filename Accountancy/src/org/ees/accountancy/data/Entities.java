package org.ees.accountancy.data;


public enum Entities {

	CONNECTED_USERS("connected_users"),
	ACCOUNTS("accounts"),
	ENTRIES("entries");
	
	
	public String getTableName() {
		return tableName;
	}
	
	private String tableName;

	private Entities(String tableName) {
		this.tableName = tableName;
	}
	
}
