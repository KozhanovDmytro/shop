<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Login</title>
  <meta charset="utf-8">
  <link rel="stylesheet" href="css/login.css">
</head>
<body>
<div class="login-page">
  <div class="form">
    <p class="error"> <fonts style="color: red;">${error}</fonts> </p>
    <form class="login-form" method="POST" action="/registration">
      <input type="text" placeholder="Login"        name="login" required/>
      <input type="text" placeholder="Your email"   name="email" required/>
      <input type="password" placeholder="password" name="password" required/>
      <button type="submit">Registration</button>
      <p class="message">Do you have account? <a href="/login">Sign in!</a></p>
    </form>
  </div>
</div>
</body>
</html>
