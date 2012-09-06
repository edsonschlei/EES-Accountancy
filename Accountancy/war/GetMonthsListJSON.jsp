
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

{
  months: [   
      <c:forEach var="month" items="${monthList}" varStatus="status">
        {
        number: "${month.number}",
        description: "${month.description}"
        }
      <c:if test="${!status.last}">,</c:if>
    </c:forEach>
  ]


}

