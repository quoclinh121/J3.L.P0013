<%-- 
    Document   : paypal
    Created on : Jan 23, 2021, 3:02:16 PM
    Author     : quocl
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
        <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
        <title>Paypal</title>
    </head>
    <body>
        <c:set var="user" value="${sessionScope.LOGIN_USER}"/>
        <c:if test="${user.getRoleID() != 'us'}">
            <c:redirect url="MainController?btnAction=Logout"></c:redirect>
        </c:if>
        <nav class="navbar navbar-expand-md navbar-light bg-light sticky-top">
            <div class="container-fluid">
                <a class="navbar-branch" href="#">
                    <img src="./pictures/logo.png" class="d-inline-block align-top" alt=""> Hana Shop
                </a>
                <c:if test="${not empty user}">
                    <form action="MainController" method="POST">
                        Welcome, ${user.fullname}! <input type="submit" class="btn btn-outline-secondary" value="Logout" name="btnAction"/>
                    </form> 
                </c:if>
            </div>
        </nav>
        <div class="container-fluid padding">
            <div class="row welcome text-left">
                <li class="page-item" style="margin: 5px"><a class="page-link" href="MainController?&btnAction=View cart"><-- Go back to Shopping</a></li>
            </div>
        </div>

        <c:set var="cart" value="${sessionScope.CUSTCART}"/>

        <div class="row welcome text-center">
            <div class="col-12">
                <h2 class="display-6">Pay online via PayPal</h2>
            </div>
        </div> <br/>
        <div class="text-center" >
            <table class="table table-sm">
                <thead>
                    <tr>
                        <th scope="col">No.</th>
                        <th scope="col">Item</th>
                        <th scope="col">Quantity</th>
                        <th scope="col">Price</th>
                        <th scope="col">Amount</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="items" items="${cart.getItems()}" varStatus="counter">
                        <tr>
                            <td scope="row">${counter.count}</td>
                            <td>${items.value.getItemName()}</td>
                            <td>${items.value.getQuantity()}</td>
                            <td>$${items.value.getPrice()}</td>
                            <td>$${items.value.getPrice() * items.value.getQuantity()}</td>
                        </tr>
                    </c:forEach>
                    <tr>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td><b>Total Amount:</b> $${param.total}</td>
                    </tr>
                    <tr>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td>
                            <form action="https://www.sandbox.paypal.com/cgi-bin/webscr" method="post">
                                <input type="hidden" name="business" value="sb-e75ik4818206@personal.example.com">
                                <input type="hidden" name="cmd" value="_xclick">
                                <input type="hidden" name="item_name" value="Bill from Hana Shop">
                                <input type="hidden" name="amount" value="${param.total}">
                                <input type="hidden" name="currency_code" value="USD">
                                <input type="hidden" name="return" value="http://localhost:8084/P0013/CheckOutServlet?total=${param.total}&paymentMethod=${param.paymentMethod}">
                                <input type="hidden" name="cancel_return" value="http://localhost:8084/P0013/viewCartPage.jsp">
                                <input type="submit" class="btn btn-sm btn-primary" name="submit" value="Purchase">
                            </form>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
    </body>
</html>
