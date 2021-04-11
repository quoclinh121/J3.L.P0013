<%-- 
Document   : admin_page
Created on : Jan 11, 2021, 7:37:24 PM
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
        <style><%@include file="/css/admin_page.css"%></style>
        <title>Hana Shop</title>
        <script type="text/javascript">
            $('input').popup();
        </script>
    </head>
    <body>
        <c:set var="user" value="${sessionScope.LOGIN_USER}"/>
        <c:if test="${user.getRoleID() != 'ad'}">
            <c:redirect url="MainController?btnAction=Logout"></c:redirect>
        </c:if>

        <nav class="navbar navbar-expand-md navbar-light bg-light sticky-top">
            <div class="container-fluid">
                <a class="navbar-branch" href="#">
                    <img src="./pictures/logo.png" class="d-inline-block align-top" alt=""> Hana Shop
                </a>
                <h1 class="nav justify-content-center" style="color: red">Administrator Page</h1>
                <form action="MainController" method="POST">
                    Welcome, ${user.getFullname()}! <input type="submit" class="btn btn-outline-secondary" value="Logout" name="btnAction"/>
                </form> 
            </div>
        </nav>
        <div style="float: right; margin-top: 10px; margin-right: 10px">
            <form action="MainController" method="POST">
                <input type="submit" class="btn btn-outline-primary" value="Add new Item" name="btnAction"/>
            </form>
        </div> <br/> <br/>
        <div class="container-fluid padding">
            <div class="row welcome text-center">
                <div class="col-12">
                    <h2 class="display-6">Search Items</h2>
                </div>
                <form action="MainController" class="search-form" method="POST">
                    <div class="form-inline">
                        <select class="form-control" name="cbxCategory">
                            <option>All Categories</option>
                            <c:forEach var="category" items="${sessionScope.ALL_CATEGORIES}">
                                <option <c:if test="${sessionScope.CATEGORY == category}">selected</c:if>>${category}</option>
                            </c:forEach>
                        </select>
                        <input type="text" class="form-control" placeholder="Search" name="txtSearchValue" value="${sessionScope.SEARCH_VALUE}" />
                        <input type="submit" class="btn btn-outline-secondary" value="Search" name="btnAction"/>
                        <input type="hidden" name="isClickSearch" value="true" />
                    </div>
                    <div data-role="rangeslider">
                        <label for="range-1a">Range of price:</label>
                        $0<input type="range" name="range_price" id="range-1a" min="0" max="1000" value="${sessionScope.RANGE}" data-popup-enabled="true" data-show-value="true"> $1000
                    </div>
                </form>
                <hr>
            </div>
        </div>

        <c:if test="${not empty requestScope.DELETE_SUCCESS}">
            <div class="alert alert-success alert-dismissible fade show" role="alert" style="margin: 0 auto; width: 70%; text-align: center;">
                ${requestScope.DELETE_SUCCESS}
                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
        </c:if>
        <c:if test="${not empty requestScope.UPDATE_SUCCESS}">
            <div class="alert alert-success alert-dismissible fade show" role="alert" style="margin: 0 auto; width: 70%; text-align: center;">
                ${requestScope.UPDATE_SUCCESS}
                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
        </c:if>
        <c:if test="${not empty requestScope.ADD_SUCCESS}">
            <div class="alert alert-success alert-dismissible fade show" role="alert" style="margin: 0 auto; width: 70%; text-align: center;">
                ${requestScope.ADD_SUCCESS}
                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
        </c:if>
        <c:set var="itemList" value="${sessionScope.ITEM_LIST}"/>
        <c:if test="${not empty itemList}">

            <table class="table table-striped">
                <thead>
                    <tr>
                        <th scope="col">No.</th>
                        <th scope="col">Name</th>
                        <th scope="col">Image</th>
                        <th scope="col">Description</th>
                        <th scope="col">Price</th>
                        <th scope="col">Quantity</th>
                        <th scope="col">Create Date</th>
                        <th scope="col">Category</th>
                        <th scope="col">Status</th>
                        <th scope="col">Update</th>
                        <th scope="col">Delete</th>
                    </tr>
                </thead>
                <tbody> 
                    <c:forEach var="dto" items="${itemList}" varStatus="counter">
                    <form action="MainController" method="POST">
                        <tr>
                            <td>${counter.count}</td>
                            <td>${dto.getItemName()}</td>
                            <td><img src="${dto.getImage()}" alt="img" width="100" height="100"></td>
                            <td>${dto.getDescription()}</td>
                            <td>$${dto.getPrice()}</td>
                            <td>${dto.getQuantity()}</td>
                            <td><fmt:formatDate pattern="dd/MM/yyyy" value="${dto.getCreateDate()}"/></td>
                            <td>${dto.getCategoryID()}</td>
                            <td>
                                <c:if test="${dto.isStatus() == true}">True</c:if>
                                <c:if test="${dto.isStatus() == false}">False</c:if>
                                </td>
                                <td>
                                    <input type="hidden" name="updatedItem" value="${dto.getItemID()}" />
                                <input type="submit" class="btn btn-outline-success" value="Update" name="btnAction"/>
                                <input type="hidden" name="page" value="${requestScope.CURRENT_PAGE}" />
                            </td>
                            <td>

                                <button type="submit" class="btn btn-outline-danger" value="Delete" name="btnAction" onclick="return confirm('Do you want to delete ${dto.getItemName()}?')">
                                    Delete
                                </button>
                                <input type="hidden" name="itemID" value="${dto.getItemID()}" />
                                <input type="hidden" name="page" value="${requestScope.CURRENT_PAGE}" />
                            </td>
                        </tr>
                    </form>
                </c:forEach>
            </tbody>
        </table>

        <nav>
            <ul class="pagination nav justify-content-center">
                <c:if test="${requestScope.CURRENT_PAGE != 1}">
                    <li class="page-item"><a class="page-link" href="MainController?&btnAction=CheckPage&page=${requestScope.CURRENT_PAGE - 1}">Previous</a></li>
                    </c:if>
                    <c:forEach var="i" begin="1" end="${requestScope.NO_OF_PAGE}">
                        <c:choose>
                            <c:when test="${requestScope.CURRENT_PAGE == i}">
                            <li class="page-item active"><a class="page-link" href="#">${i}</a></li>
                            </c:when>
                            <c:otherwise>
                            <li class="page-item"><a class="page-link" href="MainController?&btnAction=CheckPage&page=${i}">${i}</a></li>
                            </c:otherwise>
                        </c:choose>

                </c:forEach>
                <c:if test="${requestScope.CURRENT_PAGE != requestScope.NO_OF_PAGE}">
                    <li class="page-item"><a class="page-link" href="MainController?&btnAction=CheckPage&page=${requestScope.CURRENT_PAGE + 1}">Next</a></li>
                    </c:if>
            </ul>
        </nav>
    </c:if>
</body>
</html>
