<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="ua.khpi.kozhanov.servies.dao.impl.HistoryElement" %>
<%@ page import="java.util.LinkedList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>history</title>
  <link rel="stylesheet" href="css/test3.css">
  <script src="http://code.jquery.com/jquery-latest.min.js"></script>
  <link rel="stylesheet" href="css/history.css">
</head>
<body>
<div class="wrapper">
<jsp:include page="_header.jsp"></jsp:include>

  <div class="content">
    <div class="container flex-column">
      <% LinkedList<HistoryElement> products = (LinkedList<HistoryElement>) request.getAttribute("products");
        for (int i = 0; i < products.size(); i++){
          request.setAttribute("index", products.get(i).id);
      %>
      <div class="linear-product">
        <img src="<%=products.get(i).image%>" alt="<%=products.get(i).name%>">
        <div style="margin: 10px; width: 100%;">
          <div class="info">
            <h1><%=products.get(i).name%></h1>
            <h3>Price: <%=products.get(i).price%> $</h3>
            <h3>Time: <%=products.get(i).time%></h3>
            <p><%=products.get(i).description%></p>
            <%if(!products.get(i).assessment){%>
            <c:url value="/history" var="history">
              <c:param name="index" value="${requestScope.index}"/>
            </c:url>
            <form action="<c:out value="${history}" />" method="post">
              <input type="radio" name="estimate" value="1">
              <input type="radio" name="estimate" value="2">
              <input type="radio" name="estimate" value="3">
              <input type="radio" name="estimate" value="4">
              <input type="radio" name="estimate" value="5">
              <button type="submit">estimate</button>
            </form>
            <%}%>

          </div>
        </div>
      </div>
      <%}%>
    </div>
  </div>

<jsp:include page="_footer.jsp"></jsp:include>
</div>
</body>
</html>
