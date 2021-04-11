<%-- 
    Document   : viewCartPage
    Created on : Jan 18, 2021, 3:42:16 PM
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
        <title>View Cart Page</title>
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
                <li class="page-item" style="margin: 5px"><a class="page-link" href="MainController?&btnAction=CheckPage"><-- Go back to Shopping</a></li>
            </div>
        </div>

        <c:set var="cart" value="${sessionScope.CUSTCART}"/>
        <c:if test="${not empty cart}">
            <div class="row welcome text-center">
                <div class="col-12">
                    <h2 class="display-6">Your cart</h2>
                </div>
            </div>
            <hr>
            <c:if test="${not empty requestScope.OUT_OF_STOCK}">
                <div class="alert alert-danger alert-dismissible fade show" role="alert" style="margin: 0 auto; width: 70%; text-align: center;">
                    ${requestScope.OUT_OF_STOCK}
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
            </c:if>
            <table class="table table-striped">
                <thead>
                    <tr>
                        <th scope="col">No.</th>
                        <th scope="col">Name</th>
                        <th scope="col">Image</th>
                        <th scope="col">Price</th>
                        <th scope="col">Quantity</th>
                        <th scope="col">Amount</th>
                        <th></th>
                    </tr>
                </thead>
                <tbody>
                    <c:set var="totalPrice" value="0"/>
                    <c:forEach var="items" items="${cart.getItems()}" varStatus="counter">
                    <form action="MainController">
                        <tr>
                            <td>${counter.count}</td>
                            <td>${items.value.getItemName()}</td>
                            <td><img src="${items.value.getImage()}" alt="img" width="100" height="100"></td>
                            <td>$${items.value.getPrice()}</td>
                            <td>
                                <button name="btnAction" class="btn btn-light" value="Descrease" style="margin-right: 5px" <c:if test="${items.value.getQuantity() == 1}">disabled</c:if>><img src="./pictures/descrease.svg"></button>
                                    ${items.value.getQuantity()}
                                <button name="btnAction" class="btn btn-light" value="Increase" style="margin-left: 5px"><img src="./pictures/increase.svg"></button>
                                <input type="hidden" name="itemID" value="${items.key}" />
                            </td>
                            <td>
                                $${items.value.getPrice() * items.value.getQuantity()}
                                <c:set var="totalPrice" value="${totalPrice + (items.value.getPrice() * items.value.getQuantity())}"/>
                            </td>
                            <td>
                                <button type="submit" class="btn btn-sm btn-danger" name="btnAction" value="Remove Item" onclick="return confirm('Do you want to remove ${items.value.getItemName()} out of your cart?')"><img src="./pictures/remove.svg"></button>
                                <input type="hidden" name="deleteItem" value="${items.key}"/>
                            </td>
                        </tr>
                    </form>
                </c:forEach>
                <tr>
                    <td colspan="5"></td>
                    <td><b>Total Amount:</b> $${totalPrice}</td>
                    <td></td>
                </tr>
            </tbody>
        </table>

        <div class="align-center text-center">
            <div>
                <form action="MainController" method="POST">
                    <input type="button" class="btn btn-lg btn-danger" data-toggle="modal" data-target="#confirmCheckOut" value="Checkout" />

                    <!-- Modal -->
                    <div class="modal fade" id="confirmCheckOut" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
                        <div class="modal-dialog" role="document">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title" id="exampleModalLabel">Checkout</h5>
                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                        <span aria-hidden="true">&times;</span>
                                    </button>
                                </div>
                                <div class="modal-body">
                                    Do you want to checkout?
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Continue shopping</button>
                                    <input type="submit" class="btn btn-primary" value="Checkout" name="btnAction" />
                                    <input type="hidden" name="total" value="${totalPrice}" />
                                </div>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
            <br/>
            <div class="row welcome text-center">
                <div class="col-12">
                    <h4 class="display-6">Or pay online</h4>
                </div>
            </div>
            <br/>
            <div>
                <form action="MainController" method="POST">
                    <input type="button" class="btn btn-lg btn-primary" data-toggle="modal" data-target="#confirmPaypal" value="Purchase by Paypal" />
                    <!-- Modal -->
                    <div class="modal fade" id="confirmPaypal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
                        <div class="modal-dialog" role="document">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title" id="exampleModalLabel">Checkout</h5>
                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                        <span aria-hidden="true">&times;</span>
                                    </button>
                                </div>
                                <div class="modal-body">
                                    Do you want to purchase by Paypal?
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Continue shopping</button>
                                    <input type="submit" class="btn btn-primary" value="Purchase" name="btnAction" />
                                    <input type="hidden" name="total" value="${totalPrice}" />
                                    <input type="hidden" name="paymentMethod" value="paypal" />
                                </div>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </c:if>
    <c:if test="${empty cart}">
        <div class="row welcome text-center">
            <div class="col-12">
                <h2 class="display-6" style="color: red">Your cart is empty!!!!</h2>
            </div>
        </div>
    </c:if>
</body>
</html>
