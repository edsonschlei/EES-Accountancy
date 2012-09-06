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
<title>Accountancy</title>
</head>
<body>
  <div align="center">
    ${savedForm.message}    
  </div>
  <div align="center">
    <form action="${savedForm.forwardPage}" method="post">
      <input type="submit" value="OK" />
    </form>
  </div>
</body>
</html>
