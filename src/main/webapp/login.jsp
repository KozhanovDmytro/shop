<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Login</title>
  <meta charset="utf-8">
  <link rel="stylesheet" href="css/login.css">
</head>
<body>
<%
  if(request.getSession().getAttribute("idUser") != null) {
    response.sendRedirect("/products");
  }

%>
<div class="login-page">
  <div class="form">
    <p> <fonts style="color: red;">${requestScope.error}</fonts> </p>
    <p> <fonts style="color: #f4ab76;">${requestScope.message}</fonts> </p>
    <form class="login-form" method="POST" action="/login">
      <input type="text" placeholder="Your login..." name="login" required/>
      <input type="password" placeholder="Your password..." name="password" required/>
      <button type="submit">login</button>
      <p class="message">Are you not registered? <a href="/registration">Sign up here</a></p>
    </form>
  </div>
</div>
</body>
</html>
