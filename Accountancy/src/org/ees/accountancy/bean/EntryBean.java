package org.ees.accountancy.bean;

import java.io.Serializable;

@SuppressWarnings("serial")
public class EntryBean implements Serializable {

    private String date;
    private String account;
    private String debit;
    private String credit;
    private String balance;

    public final String getDate() {
	return date;
    }

    public final void setDate(String date) {
	this.date = date;
    }

    public final String getAccount() {
	return account;
    }

    public final void setAccount(String account) {
	this.account = account;
    }

    public final String getDebit() {
	return debit;
    }

    public final void setDebit(String debit) {
	this.debit = debit;
    }

    public final String getCredit() {
	return credit;
    }

    public final void setCredit(String credit) {
	this.credit = credit;
    }

    public final String getBalance() {
	return balance;
    }

    public final void setBalance(String balance) {
	this.balance = balance;
    }

    @Override
    public String toString() {
	return "EntryBean [date=" + date + ", account=" + account + ", debit=" + debit
		+ ", credit=" + credit + ", balance=" + balance + "]\n";
    }

}
