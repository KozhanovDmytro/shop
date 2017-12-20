<div class="header">
  <div class="menu">
    <div class="container flex-correct">
      <ul>
        <li><a href="#" class="mobile_menu_burger"><img src="img/burger.png" alt="Burger"></a></li>
        <li class="elem_menu"><a href="/products">home</a></li>
        <li class="elem_menu">
          <div class="count-products">
            <div class="count">${requestScope.count}</div>
          </div>
          <a href="/basket" class="textBasket">your basket</a>
        </li>
        <li class="elem_menu"><a href="/history">History</a></li>
        <li class="elem_menu"><a href="/logOut">Log out</a></li>
      </ul>
      <span>${sessionScope.login}</span>
    </div>
  </div>
  <div class="mobile_menu container">
    <ul class="mobile_elements">
      <li><a href="/products">home</a></li>
      <li><a href="/basket">your basket</a></li>
      <li><a href="/history">History</a></li>
      <li><a href="/logOut">Log out</a></li>
    </ul>
  </div>
  <script>
    $('.mobile_menu_burger').click(function(){
      $('.mobile_menu').fadeToggle();
    });
  </script>
</div>