/**
 * 
 */
package org.ees.accountancy.data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.google.appengine.tools.development.testing.LocalUserServiceTestConfig;

/**
 * @author schlei
 * 
 */
public class EntryTest {

    private final LocalServiceTestHelper serviceHelper = new LocalServiceTestHelper(
	    new LocalDatastoreServiceTestConfig(), new LocalUserServiceTestConfig()).setEnvIsAdmin(
	    true).setEnvIsLoggedIn(true);

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
	serviceHelper.setUp();
	serviceHelper.setEnvEmail("teste@teste.com");
	serviceHelper.setEnvAuthDomain("teste.com");
	ConnectedUser.createOrUpdate();
	Assert.assertTrue(Entry.getAll().isEmpty());
	String[][] data = getEntryData();
	DataBuilder.createEntries(data);
	List<Entity> all = Entry.getAll();
	Assert.assertEquals(data.length, all.size());
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
	serviceHelper.tearDown();
    }

    /**
     * Test method for
     * {@link org.ees.accountancy.data.Entry#createOrUpdate(java.lang.String, java.lang.String, double, java.lang.String, java.lang.String, java.lang.String, java.lang.String)}
     * .
     * @see {@link #setUp()}
     */
    @Test
    public final void testCreateOrUpdate() {
	// setup validate the create part
	// TODO the update test
    }

    /**
     * Return data that is used in the tests.
     * Zurück Daten, die in den Tests verwendet wird.
     * 
     * data array format:
     * String id = val[0];
     * long date = Long.parseLong(val[1]);
     * double value = Double.parseDouble(val[2]);
     * String credit = val[3];
     * String debit = val[4];
     * String description = val[5];
     * String location = val[6];
     * 
     * @return
     */
    private String[][] getEntryData() {
	    
	String[][] data = new String[][] {
		{null, getTS("01/01/2012"), "10", "1.1.1", "1.2.1", "desc 1", null},	
		{null, getTS("02/01/2012"), "10", "1.1.2", "1.2.2", "desc 2", null},	
		{null, getTS("03/01/2012"), "10", "1.1.3", "1.2.1", "desc 3", "12,23"},	
		{null, getTS("04/01/2012"), "10", "1.2.2", "1.1.1", "desc 4", "24,35"},	
		{null, getTS("05/01/2012"), "10", "1.2.1", "1.1.2", "desc 5", "5,68"},	
		{null, getTS("06/01/2012"), "10", "1.2.2", "1.1.3", "desc 6", null},	
	};
	
	return data;
    }

    private String getTS(String date) {
	try {
	    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.ROOT);
	    System.out.println(date + " = " + sdf.parse(date));
	    return String.valueOf(sdf.parse(date).getTime());
	} catch (ParseException e) {
	    e.printStackTrace();
	}
	return "0";
    }

    /**
     * Test method for {@link org.ees.accountancy.data.Entry#getAll()}.
     * @see {@link #setUp()}
     */
    @Test
    public final void testGetAll() {
	// setup validate the getAll() method.
	// TODO validate the values of each record field with the informed data. 
    }

    /**
     * Test method for
     * {@link org.ees.accountancy.data.Entry#getDate(com.google.appengine.api.datastore.Entity)}
     * .
     */
    @Test
    public final void testGetDate() throws Exception {
	List<Entity> all = Entry.getAll();
	Entity entity = all.get(0);
	Date date = Entry.getDate(entity);
	
	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.ROOT);
	Date expDate = sdf.parse("01/01/2012");
	
	Assert.assertEquals(expDate, date);
    }

    /**
     * Test method for
     * {@link org.ees.accountancy.data.Entry#getAll(int, int, java.lang.String)}
     * .
     */
    @Test
    public final void testGetAllIntIntString() {
	int year = 2012;
	int month = 1;
	List<Entity> all = Entry.getAll(year, month, "1.1.1");
	Assert.assertEquals(2, all.size());
    }

    /**
     * Test method for
     * {@link org.ees.accountancy.data.Entry#getAll(java.lang.String)}.
     */
    @Test
    public final void testGetAllString() {
	List<Entity> all = Entry.getAll("1.1.1");
	Assert.assertEquals(2, all.size());
    }

    /**
     * Test method for
     * {@link org.ees.accountancy.data.Entry#getCreditAccount(com.google.appengine.api.datastore.Entity)}
     * .
     */
    @Test
    public final void testGetCreditAccount() {
	List<Entity> all = Entry.getAll();
	Entity entity = all.get(0);
	
	String creditAccount = Entry.getCreditAccount(entity);
	
	Assert.assertEquals("1.1.1", creditAccount);
    }

    /**
     * Test method for
     * {@link org.ees.accountancy.data.Entry#getDebitAccount(com.google.appengine.api.datastore.Entity)}
     * .
     */
    @Test
    public final void testGetDebitAccount() {
	List<Entity> all = Entry.getAll();
	Entity entity = all.get(0);
	
	String debitAccount = Entry.getDebitAccount(entity);
	
	Assert.assertEquals("1.2.1", debitAccount);
    }

    /**
     * Test method for
     * {@link org.ees.accountancy.data.Entry#getValue(com.google.appengine.api.datastore.Entity)}
     * .
     */
    @Test
    public final void testGetValue() {
	List<Entity> all = Entry.getAll();
	Entity entity = all.get(0);
	
	double value = Entry.getValue(entity);
	
	Assert.assertEquals(10d, value);
    }

}
