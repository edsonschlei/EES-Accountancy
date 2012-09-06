<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" %>

<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
    
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<link rel="stylesheet"
      href="./css/styles.css"
      type="text/css"/>
      
<script src="./scripts/ajax-utils.js"
        type="text/javascript"></script>
        
<script src="./scripts/show-user-entries-form.js"
        type="text/javascript"></script>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>Accountancy</title>
</head>
<body>
<table>
  <tr> 
    <td>
      <table width="100%">
        <tr>
          <th align="left">
            <a href="create-user-data-account-servlet"> New Account </a> &#160;&#160;&#160;
            <a href="create-user-data-entry-servlet"> New Entry </a>
          </th>
        </tr>
      </table>
    </td>
  </tr>
  <tr>
    <td>
      <fieldset>
        <legend>Filter</legend>
        <table>
        <tr>
          <td>Year:</td>
          <td>
            <select id="yearBox"  onChange='changeYear(this)'>
              <c:forEach var="year" items="${userDataEntries.years}">
                  <option value="${year}" > ${year} </option>
              </c:forEach>
            </select>
          </td> 
        </tr>  
        <tr> 
          <td>Month:</td>
          <td>
            <select id="monthBox"  onChange='changeMonth(this)'>
                <c:forEach var="month" items="${userDataEntries.months[userDataEntries.years[0]]}">
                  <option value="${month.number}" > ${month.description} </option>
                </c:forEach>
            </select>
          </td>
        </tr>
        <tr>
          <td>Account:</td>
          <td>
          <select id="accountBox"  onChange='changeAccount(this)'>
              <option value="" > Choose an account </option>
            <c:forEach var="account" items="${userDataEntries.accounts}">
              <option value="${account.code}" > ${account.description} </option>
            </c:forEach>
           </select>
          </td>
        </tr>      
        </table>
      </fieldset>
    </td>
  </tr>
  <tr>
    <td>
      <div id="entries"></div>
    </td>
  </tr>
</table>
    
</body>
</html>