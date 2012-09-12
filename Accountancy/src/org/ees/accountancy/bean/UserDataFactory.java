package org.ees.accountancy.bean;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.ees.accountancy.data.Account;
import org.ees.accountancy.data.Entry;

import com.google.appengine.api.datastore.Entity;

public class UserDataFactory {

    private static final Logger logger = Logger.getLogger(UserDataFactory.class.getCanonicalName());

    private UserDataFactory() {
    }

    public static UserDataEntryBean getUserDataEntryBean() {
	UserDataEntryBean bean = new UserDataEntryBean();
	bean.setDate(getCurrentDate());
	bean.setAccounts(getAccounts());
	if (!bean.getAccounts().isEmpty()) {
	    bean.setDebit(bean.getAccounts().get(0).getCode());
	    bean.setCredit(bean.getAccounts().get(0).getCode());
	}
	System.out.println(bean);
	return bean;
    }

    private static List<AccountBean> getAccounts() {
	List<Entity> accounts = Account.getAll();
	List<AccountBean> list = new ArrayList<AccountBean>(accounts.size());
	for (Entity entity : accounts) {
	    String description = Account.getDescription(entity);
	    String code = Account.getCode(entity);
	    AccountBean bean = new AccountBean(code, description);
	    list.add(bean);
	}
	return list;
    }

    private static String getCurrentDate() {
	return DateFormat.getDateInstance(DateFormat.SHORT).format(new Date());
    }

    public static UserDataAccountBean getUserDataAccountBean() {
	return new UserDataAccountBean();
    }

    public static UserDataEntriesFilterBean getUserDataEntriesFilterBean() {
	UserDataEntriesFilterBean bean = new UserDataEntriesFilterBean();
	bean.setAccounts(getAccounts());
	createYearMonth(bean);
	return bean;
    }

    private static void createYearMonth(UserDataEntriesFilterBean bean) {
	List<Entity> all = Entry.getAll();
	Collections.sort(all, getComparatorByDate());
	SimpleDateFormat sdfMonthNum = new SimpleDateFormat("M");
	SimpleDateFormat sdfMonthDesc = new SimpleDateFormat("MMMM");
	SimpleDateFormat sdfYear = new SimpleDateFormat("yyyy");
	Set<String> yearSet = new HashSet<String>();
	Map<String, List<MonthBean>> monthMap = new HashMap<String, List<MonthBean>>();
	for (Entity entity : all) {
	    Date date = Entry.getDate(entity);
	    String monthNum = sdfMonthNum.format(date);
	    String monthDesc = sdfMonthDesc.format(date);
	    MonthBean month = new MonthBean(monthNum, monthDesc);
	    String year = sdfYear.format(date);
	    yearSet.add(year);
	    List<MonthBean> list = monthMap.get(year);
	    if (list == null) {
		list = new ArrayList<MonthBean>();
		monthMap.put(year, list);
	    }
	    if (!list.contains(month)) {
		list.add(month);
	    }
	}

	List<String> yearList = new ArrayList<String>(yearSet);

	Collections.sort(yearList, Collections.reverseOrder());
	bean.setYears(yearList);
	bean.setMonths(monthMap);
    }

    private static Comparator<Entity> getComparatorByDate() {
	return new Comparator<Entity>() {
	    public int compare(Entity o1, Entity o2) {
		Date date1 = Entry.getDate(o1);
		Date date2 = Entry.getDate(o2);
		return date1.compareTo(date2);
	    };
	};
    }

    /**
     * Return a bean with the EntryBean of the year, month and Account code
     * informed.
     * 
     * @param year
     * @param month
     * @param code
     * @return
     */
    public static UserDataEntriesFilteredBean getUserData(String year, String month, String code) {
	System.out.println(year + " " + month + " " + code);
	UserDataEntriesFilteredBean bean = new UserDataEntriesFilteredBean();

	List<Entity> list = Entry.getAll(year, month, code);
	Collections.sort(list, getComparatorByDate());

	DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.SHORT);
	NumberFormat currencyInstance = NumberFormat.getCurrencyInstance();

	List<EntryBean> entryBeanList = new ArrayList<EntryBean>(list.size());

	{
	    Entity baseAccountEntity = Account.getEntity(code);

	    String accCode = Account.getCode(baseAccountEntity);
	    String accDesc = Account.getDescription(baseAccountEntity);

	    String date = "1." + month + "." + year;
	    String account = accCode + "-" + accDesc;
	    String credit = "";
	    String debit = "";
	    String balance = currencyInstance.format(0);

	    EntryBean ebean = new EntryBean();
	    ebean.setDate(date);
	    ebean.setAccount(account);
	    ebean.setCredit(credit);
	    ebean.setDebit(debit);
	    ebean.setBalance(balance);
	    entryBeanList.add(ebean);
	}

	double balanceValue = 0.0;

	for (Entity entity : list) {
	    Date edate = Entry.getDate(entity);
	    String cAccount = Entry.getCreditAccount(entity);
	    String dAccount = Entry.getDebitAccount(entity);
	    Double eValue = Entry.getValue(entity);

	    String credit = "";
	    String debit = "";
	    Entity accountEntity = null;
	    if (cAccount.equals(code)) {
		accountEntity = Account.getEntity(dAccount);
		credit = currencyInstance.format(eValue.doubleValue());
		balanceValue += eValue.doubleValue();
	    } else {
		assert dAccount.equals(code);
		accountEntity = Account.getEntity(cAccount);
		debit = currencyInstance.format(eValue.doubleValue());
		balanceValue -= eValue.doubleValue();
	    }
	    if (accountEntity == null) {
		String accountNotFound = cAccount.equals(code) ? dAccount : cAccount;
		String msg = String.format("Account not found: %s", accountNotFound);
		logger.log(Level.SEVERE, msg);
		continue;
	    }
	    String accCode = Account.getCode(accountEntity);
	    String accDesc = Account.getDescription(accountEntity);

	    String date = dateFormat.format(edate);
	    String account = accCode + "-" + accDesc;
	    String balance = currencyInstance.format(balanceValue);

	    EntryBean ebean = new EntryBean();
	    ebean.setDate(date);
	    ebean.setAccount(account);
	    ebean.setCredit(credit);
	    ebean.setDebit(debit);
	    ebean.setBalance(balance);
	    entryBeanList.add(ebean);
	}
	bean.setEntries(entryBeanList);
	
	System.out.println(bean);
	
	return bean;
    }

}
