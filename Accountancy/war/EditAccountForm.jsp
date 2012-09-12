<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<link rel="stylesheet"
      href="./css/styles.css"
      type="text/css"/>
      
<script src="./scripts/ajax-utils.js"
        type="text/javascript"></script>
      

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<meta name="viewport" content="width=${browser_size.width}" />

<title>Accountancy</title>
</head>
<body>
<table>
  <tr> 
    <td>
      <table width="100%">
        <tr>
          <th align="left">
            <a href="create-user-data-entry-servlet"> New Entry </a> &#160;&#160;&#160;
            <a href="create-user-data-entries-filter-servlet"> Show Entries</a>  &#160;&#160;&#160;
            <a href="/about.html"> About </a>
          </th>
        </tr>
      </table>
    </td>
  </tr>
  <tr> <td>
    <fieldset>
      <legend>New Account</legend>
      <form id="accountForm" action="save-account-servlet" method="post">
        <table>
          <tr> 
            <td>Code:</td>
            <td><input type="text" name="code" value="${userDataAccount.code}" /> </td>
          </tr>
          <tr>
            <td>Description:</td> 
            <td><input type="text" name="description" value="${userDataAccount.description}"  /></td>
          </tr>
          <tr>
            <td></td>
            <td>
              <input type="submit" value="Save" />
            </td>
          </tr>
          <c:forEach var="invalidMsg" items="${userDataAccount.invalidMessages}">
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