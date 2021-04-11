<%-- 
    Document   : add_new
    Created on : Jan 17, 2021, 8:09:26 PM
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
        <title>Add New Page</title>
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
        <div class="container-fluid padding">
            <div class="row welcome text-left">
                <c:url var="goBack" value="MainController">
                    <c:param name="btnAction" value="CheckPage"/>
                </c:url>
                <li class="page-item" style="margin: 5px"><a class="page-link" href="${goBack}"><-- Go back to Search page</a></li>
            </div>
        </div>

        <c:set var="error" value="${requestScope.ADD_ERROR}"/>
        <c:set var="itemTemp" value="${requestScope.NEW_ITEM}"/>
        <div class="container">
            <form action="MainController" method="POST">
                <div class="row welcome text-center">
                    <div class="col-12">
                        <h2 class="display-6">Add new Item</h2>
                    </div>
                </div>

                <div class="row justify-content-center">
                    <div class="col-8"  style="margin: 0 auto">
                        <div class="form-group row">
                            <label class="col-sm-2 col-form-label">Item name: </label>
                            <div class="col-sm-5">
                                <input type="text" class="form-control" name="txtItemName" placeholder="Item name" value="${itemTemp.getItemName()}">
                            </div>
                        </div>
                        <font color="red"> ${error.getItemNameLengthErr()} </font>

                        <div class="form-group row">
                            <label class="col-sm-2 col-form-label">Price ($): </label>
                            <div class="col-sm-5">
                                <input type="text" class="form-control" name="txtItemPrice" placeholder="Price" value="${itemTemp.getPrice()}">
                            </div>
                        </div>
                        <font color="red"> ${error.getItemPriceFormatErr()} </font>

                        <div class="form-group row">
                            <label class="col-sm-2 col-form-label">Quantity: </label>
                            <div class="col-sm-5">
                                <input type="text" class="form-control" name="txtItemQuantity" placeholder="Quantity" value="${itemTemp.getQuantity()}">
                            </div>
                        </div>
                        <font color="red"> ${error.getItemQuantityFormatErr()} </font>    

                        <div class="form-group row">
                            <label class="col-sm-2 col-form-label">Category: </label>
                            <div class="col-sm-5">
                                <select class="form-control" name="cbxCategory" >
                                    <c:forEach var="category" items="${sessionScope.ALL_CATEGORIES}">
                                        <option <c:if test="${itemTemp.getCategoryID() == category}">selected</c:if>>${category}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>

                        <div class="form-group row">
                            <label class="col-sm-2 col-form-label">Image: </label>
                            <div class="col-sm-5">
                                <input type="file" class="form-control-file" name="imgItem" accept=".jpg,.jpeg,.png"> 
                            </div>
                        </div>
                        <font color="red"> ${error.getImageErr()} </font>  


                        <div class="form-group row">
                            <label class="col-sm-2 col-form-label">Description: </label>
                            <div class="col-sm-5">
                                <textarea class="form-control" name="txtDescription" placeholder="Description" rows="3">${itemTemp.getDescription()}</textarea>
                            </div>
                        </div>
                        <font color="red"> ${error.getDescriptionLengthErr()} </font> 
                        <font color="red">${requestScope.ADD_FAIL}</font>
                        <div class="form-group" style="margin-top: 10px">
                            <div class="col-sm-7 text-center">
                                <input type="submit" class="btn btn-outline-success" value="Add new" name="btnAction"/>
                            </div>
                        </div>
                    </div>
                </div>
            </form>
        </div>
    </body>
</html>
