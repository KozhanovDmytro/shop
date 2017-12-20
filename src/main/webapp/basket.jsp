<%@ page import="java.util.LinkedList" %>
<%@ page import="ua.khpi.kozhanov.servies.dao.impl.ProductDaoImpl" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>products</title>
  <link rel="stylesheet" href="css/test.css">
  <link rel="stylesheet" href="css/test3.css">
  <script src="http://code.jquery.com/jquery-latest.min.js"></script>
</head>
<body>
<div class="wrapper">
  <jsp:include page="_header.jsp"></jsp:include>
  <div class="content">
    <div class="container products">
    <%
      LinkedList<ProductDaoImpl> products = (LinkedList<ProductDaoImpl>) request.getAttribute("products");
      try{
      for (ProductDaoImpl product : products) {
        request.setAttribute("index", product.getId());
    %>
      <div class="product">
        <img src="<%= product.getImagePath() %>" alt="name">

        <p><%= product.getName()%>
        </p>

        <div style="display: flex; margin-bottom: 10px;">
          <%
            if (product.getDiscount() == 0) {
          %>
          <%= "<div class=\"price\">\n" +
                  "            <div class=\"without-discount\"> " + product.getPrice() + " $</div>\n" +
                  "          </div>"
          %>
          <%} else { %>
          <%= "          <div class=\"price\">\n" +
                  "            <div class=\"old\"><del>" + product.getPrice() + " $</del></div>\n" +
                  "            <div class=\"discount\">" + product.getDiscount() + " $</div>\n" +
                  "          </div>"  %>
          <%}%>
          <div class="stars">
            <%
              String stars = "";
              for (int j = 0; j < 5; j++) {
                if (product.getPopular() > j) {
                  stars += "<div class=\"active-star\"></div>\n";
                } else {
                  stars += "<div class=\"star\"></div>\n";
                }
              }
            %>
            <%= stars %>
          </div>
        </div>
        <c:url value="/deleteFromBasket" var="deleteFromBasket">
          <c:param name="index" value="${requestScope.index}"/>
        </c:url>
        <form action="<c:out value="${deleteFromBasket}" />" method="post">
          <button type="submit">DELETE</button>
        </form>
        <form action="#">
          <button type="submit">DESCRIPTION</button>
        </form>
      </div>
      <%} }catch(Exception e){}%>
  </div>
  <div class="container buy">
    <span class="inputAmount">total: ${requestScope.amount} $</span>

    <form action="/addToHistory">
      <button>BUY</button>
    </form>
  </div>
  </div>
  <jsp:include page="_footer.jsp"></jsp:include>
</div>
</body>
</html>
