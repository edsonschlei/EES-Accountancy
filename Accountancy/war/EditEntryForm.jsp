<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
  	
<head>

<link rel="stylesheet"
      href="./css/styles.css"
      type="text/css"/>

<script src="./scripts/ajax-utils.js"
        type="text/javascript"></script>

<meta name="viewport" content="width=480" />

<script language="JavaScript">
<!--

function changeCredit(form) {
    var option = form.creditBox.options[form.creditBox.selectedIndex]
    form.credit.value = option.value;
}
  
function changeDebit(form) {
    var option = form.debitBox.options[form.debitBox.selectedIndex]
 	  form.debit.value = option.value;
}
  

-->

</script>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>Accountancy</title>
</head>
<body>
<div>
  This software is developed as a study case using several technologies available to develop an APP 
  for the Google App Engine.<br />  
  List of technologies used: JSP, JavaScrip, html, css, Servlet, AJAX, JSON.
</div>
<table>
<tr> <td>
<table width="100%">
  <tr>
    <th align="left">
      <a href="create-user-data-account-servlet"> New Account </a> &#160;&#160;&#160;
      <a href="create-user-data-entries-filter-servlet"> Show Entries</a>
    </th>
  </tr>
</table>
</td>
</tr>
<tr> <td>
<fieldset>
  <legend>New Entry</legend>
  <form id="entryForm" action="save-entry-servlet" method="post">
    <table>
      <tr> 
        <td>Date:</td>
        <td><input type="date" name="date" value="${userDataEntry.date}" /> </td>
      </tr>
      <tr>
        <td>Value:</td> 
        <td><input type="text" name="value" value="${userDataEntry.value}"  /></td>
      </tr>
      <tr>
        <td>Credit:</td>
        <td> 
          <select id="creditBox"  onChange='changeCredit(this.form)'>
            <c:forEach var="account" items="${userDataEntry.accounts}">
              <c:choose>
                <c:when test="${userDataEntry.credit == account.code}">
                  <option value="${account.code}" selected="selected"> ${account.description} </option>
                </c:when>
                <c:otherwise>
                  <option value="${account.code}" > ${account.description} </option>
                </c:otherwise>
              </c:choose>
            </c:forEach>
           </select>
           <input type="hidden" name="credit" value="${userDataEntry.credit}"  />
        </td>
      </tr>
      <tr>
        <td>Debit:</td>
        <td>
          <select id="debitBox" onChange='changeDebit(this.form)'>
            <c:forEach var="account" items="${userDataEntry.accounts}">
              <c:choose>
                <c:when test="${userDataEntry.debit == account.code}">
                  <option value="${account.code}" selected="selected"> ${account.description} </option>
                </c:when>
                <c:otherwise>
                  <option value="${account.code}" > ${account.description} </option>
                </c:otherwise>
              </c:choose>
            </c:forEach>
          </select>
          <input type="hidden" name="debit" value="${userDataEntry.debit}"  />
        </td>
      </tr>
      <tr>
        <td>Description:</td> 
        <td><input type="text" name="description" value="${userDataEntry.description}"  /></td>
	    </tr>
      <tr>
        <td></td>
        <td>
	        <input type="submit" value="Save" />
        </td>
      </tr>
      <c:forEach var="invalidMsg" items="${userDataEntry.invalidMessages}">
        <tr>
          <td colspan="2", class="error">
            ${invalidMsg}
          </td>
        </tr>
      </c:forEach>      
	  </table>
  </form>
</fieldset>
</td>
</tr>
</table>

</body>

</html>
