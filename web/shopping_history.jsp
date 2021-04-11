<%-- 
    Document   : shopping_history
    Created on : Jan 20, 2021, 4:30:37 PM
    Author     : quocl
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
        <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
        <style><%@include file="/css/search_page.css"%></style>
        <link href="https://code.jquery.com/ui/1.11.3/themes/smoothness/jquery-ui.css" rel="stylesheet"/>
        <script src="https://code.jquery.com/jquery-2.2.4.js" ></script>
        <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
        <title>Shopping History</title>
        <script type="text/javascript">
            $('input').popup();
        </script>
        <script>
            $(document).ready(function () {
                $("#fromDate").datepicker({
                    showAnim: 'drop',
                    numberOfMonth: 1,
                    minDate: null,
                    dateFormat: 'dd/mm/yy',
                    onClose: function (selectedDate) {
                        $('#toDate').datepicker("option", "minDate",
                                selectedDate)
                    }
                });
                $("#toDate").datepicker({
                    showAnim: 'drop',
                    numberOfMonth: 1,
                    dateFormat: 'dd/mm/yy',
                    onClose: function (selectedDate) {
                        $('#fromDate').datepicker("option", "maxDate", selectedDate)
                    }
                });
            });
        </script>
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
        <div class="row welcome text-center">
            <div class="col-12">
                <h2 class="display-6">Your Shopping History</h2>
            </div>
            <form action="MainController" class="search-form" method="POST">
                <div class="col-12">
                    <div class="form-inline" style="margin-left: 70px">
                        <input type="text" class="form-control" placeholder="Search" name="txtSearchValue" value="" />
                        <input type="submit" class="btn btn-outline-secondary" name="btnAction" value="Search Order" />
                    </div>
                </div>
                <div class="col-12">
                    <div class="form-inline">
                        <div class="input-group date" >
                            From: <input type="text" id="fromDate" class="form-control" name="fromDate" value="" />
                        </div>
                        <div class="input-group date" >
                            To: <input type="text" id="toDate" class="form-control" name="toDate" value="" />
                        </div>
                    </div>
                    <c:if test="${not empty requestScope.FROM_DATE_ERROR}"><font color="red">${requestScope.FROM_DATE_ERROR}</font></c:if>
                    <c:if test="${not empty requestScope.TO_DATE_ERROR}"><font color="red">${requestScope.TO_DATE_ERROR}</font></c:if>
                </div>
            </form>
        </div>
        <hr>

        <c:set var="listOrder" value="${sessionScope.LIST_ORDERS_DETAILS}"/>
        <c:if test="${not empty listOrder}">
            <table class="table table-striped">
                <thead>
                    <tr>
                        <th scope="col">No.</th>
                        <th scope="col">Order ID</th>
                        <th scope="col">Total amount</th>
                        <th scope="col">Details</th>
                        <th scope="col">Payment date</th>
                        <th scope="col">Payment method</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="order" items="${listOrder}" varStatus="counter">
                        <tr>
                            <td>${counter.count}</td>
                            <td>${order.key.getOrderID()}</td>
                            <td>$${order.key.getTotalPrice()}</td>
                            <td>
                                <c:forEach var="detail" items="${order.value}">
                        <li> ${detail.getItem()}: ${detail.getQuantity()}</li>
                        </c:forEach>
                </td>
                <td><fmt:formatDate pattern="dd/MM/yyyy" value="${order.key.getPaymentDate()}"/></td>
                <td>${order.key.getPaymentMethod()}</td>
            </tr>
        </c:forEach>
    </tbody>
</table>

</c:if>
<c:if test="${empty listOrder}">
    <div class="row welcome text-center">
        <div class="col-12">
            <h2 class="display-6" style="color: red">You don't have any orders</h2>
        </div>
    </div>
</c:if>    
</body>
</html>
