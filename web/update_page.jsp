<%-- 
    Document   : update_page
    Created on : Jan 14, 2021, 5:06:59 PM
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
        <title>Update Page</title>
    </head>
    <body>
        <c:set var="user" value="${sessionScope.LOGIN_USER}"/>
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
                <li class="page-item" style="margin: 5px"><a class="page-link" href="MainController?&btnAction=CheckPage"><-- Go back to Search page</a></li>
            </div>
        </div>

        <c:set var="item" value="${sessionScope.UPDATED_ITEM}"/>
        <c:set var="error" value="${requestScope.UPDATE_ERROR}"/>
        <div class="container">
            <form action="MainController" method="POST">
                <div class="row">
                    <div class="col-sm-4">
                        <div class="form-group">
                            <img src="${item.getImage()}" width="300px" height="300px" class="rounded mx-auto d-block" alt="img">
                        </div>
                    </div>
                    <div class="col-sm-8">
                        <div class="form-group row">
                            <label class="col-sm-2 col-form-label">Item name: </label>
                            <div class="col-sm-6">
                                <input type="text" class="form-control" name="txtItemName" value="${item.getItemName()}">
                            </div>
                        </div>
                        <font color="red"> ${error.getItemNameLengthErr()} </font>

                        <div class="form-group row">
                            <label class="col-sm-2 col-form-label">Price ($): </label>
                            <div class="col-sm-6">
                                <input type="text" class="form-control" name="txtItemPrice" value="${item.getPrice()}">
                            </div>
                        </div>
                        <font color="red"> ${error.getItemPriceFormatErr()} </font>

                        <div class="form-group row">
                            <label class="col-sm-2 col-form-label">Quantity: </label>
                            <div class="col-sm-6">
                                <input type="text" class="form-control" name="txtItemQuantity" value="${item.getQuantity()}">
                            </div>
                        </div>
                        <font color="red"> ${error.getItemQuantityFormatErr()} </font>    

                        <div class="form-group row">
                            <label class="col-sm-2 col-form-label">Category: </label>
                            <div class="col-sm-6">
                                <select class="form-control" name="cbxCategory" >
                                    <c:forEach var="category" items="${sessionScope.ALL_CATEGORIES}">
                                        <option <c:if test="${item.getCategoryID() == category}">selected="selected"</c:if>>${category}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>

                        <div class="form-group row">
                            <label class="col-sm-2 col-form-label">Create date: </label>
                            <div class="col-sm-6">
                                <input type="text" class="form-control" name="txtItemName" readonly value="${item.getCreateDate()}">
                            </div>
                        </div>

                        <div class="form-group row">
                            <label class="col-sm-2 col-form-label">Image: </label>
                            <div class="col-sm-6">
                                <input type="file" class="form-control-file" name="imgItem" accept=".jpg,.jpeg,.png"> 
                            </div>
                        </div>
                        <font color="red"> ${error.getImageErr()} </font>  


                        <div class="form-group row">
                            <label class="col-sm-2 col-form-label">Description: </label>
                            <div class="col-sm-6">
                                <textarea class="form-control" name="txtDescription" rows="3">${item.getDescription()}</textarea>
                            </div>
                        </div>
                        <font color="red"> ${error.getDescriptionLengthErr()} </font>  


                        <div class="form-group row">
                            <label class="col-sm-2 col-form-label">Status: </label>
                            <div class="col-sm-6">
                                <select class="form-control" name="cbxStatus">
                                    <option <c:if test="${item.isStatus() == true}">selected</c:if>>True</option>
                                    <option <c:if test="${item.isStatus() == false}">selected</c:if>>False</option>
                                </select>
                            </div>
                        </div>
                        <div class="form-group row">
                            <div class="col-sm-8 text-center">
                                <input type="submit" class="btn btn-outline-success" value="Update Item" name="btnAction"/>
                                <input type="hidden" name="page" value="${requestScope.CURRENT_PAGE}" />
                            </div>
                        </div>
                    </div> 
                </div>
            </form>
        </div>
    </body>
</html>
