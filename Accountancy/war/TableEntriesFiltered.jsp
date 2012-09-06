<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

{ 
  headings: ["Date", "Account", "Debit", "Credit", "Balance"],
  rows: [
    <c:forEach var="entry" items="${filtered.entries}" varStatus="status">
      ["${entry.date}","${entry.account}","${entry.debit}","${entry.credit}","${entry.balance}"]
      <c:if test="${!status.last}">,</c:if>
    </c:forEach>
  ]
}


