<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <title>MANGA</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta content="Responsive bootstrap 4 admin template" name="description">
    <meta content="Coderthemes" name="author">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <!-- App favicon -->
    <link rel="shortcut icon" href="assets\images\favicon.ico">

    <!-- App css -->
    <link href="assets\css\bootstrap.min.css" rel="stylesheet" type="text/css" id="bootstrap-stylesheet">
    <link href="assets\css\icons.min.css" rel="stylesheet" type="text/css">
    <link href="assets\css\app.min.css" rel="stylesheet" type="text/css" id="app-stylesheet">
</head>

<body>

<!-- Begin page -->
<div id="wrapper">

    <%--   navbar-customer--%>
    <jsp:include page="/layout/navbarPr.jsp"></jsp:include>

    <div class="left-side-menu">
        <div class="slimscroll-menu">
            <div id="sidebar-menu">
                <ul class="metismenu" id="side-menu">
                    <li class="menu-title">Navigation</li>
                    <li>
                        <a href="javascript: void(0);">
                            <i class="la la-dashboard"></i>
                            <!--                                    <span class="badge badge-info badge-pill float-right">2</span>-->
                            <span> Dashboards </span>
                        </a>
                        <ul class="nav-second-level" aria-expanded="false">
                            <c:if test="${acc.getRole() == 1}">
                                <li>
                                    <a href="/users">List User</a>
                                </li>

                                <%--                                <li>--%>
                                <%--                                    <a href="/products">List Manga</a>--%>
                                <%--                                </li>--%>
                            </c:if>
                        </ul>
                    </li>
                    <a class="ml-5" href="/cart">
                        <%--                        <span class="badge badge-info badge-pill float-right">2</span>--%>
                        <span style="color: #98a6ad; margin-left:10px "> Cart </span>
                        <i class="fas fa-shopping-cart mr-2"></i>
                    </a>
                    <li>

                    </li>
                </ul>
            </div>
            <div class="clearfix"></div>
        </div>
        <!-- Sidebar -left -->
    </div>
    <!-- Navigation Bar-->

    <div class="content-page">
        <div class="content">
            <div class="container-fluid">
                <div class="row">
                    <div class="col-12">
                        <div class="page-title-box">
                            <div class="page-title-right">
                                <a style="float: right " href="products?action=create"
                                   class="btn btn-outline-light mt-lg-2 width-xl bg-danger">
                                    <i style="color: blue" class="fas fa-plus-square mr-1" aria-hidden="true"></i>
                                    <span style="color: blue" class="font-17 font-weight-bolder ">Create Product</span>
                                </a>
                            </div>
                            <h4 class="page-title">PRODUCT LIST</h4>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-md-12">
                        <div class="card-box">
                            <form action="products">
                                <div style=" margin-bottom: 14px;width:40%;float: right;" class="input-group">
                                    <input type="search" class="form-control rounded" placeholder="Search"
                                           value="${requestScope.q}" name="q"/>
                                    <button type="submit" class="btn btn-outline-primary">search</button>
                                </div>
                            </form>
                            <div class="table-responsive">
                                <table class="table table-hover table-centered m-0 text-center table-bordered-info">
                                    <thead>
                                    <tr>
                                        <th>ID</th>
                                        <th>Name</th>
                                        <th>iMAGE</th>
                                        <th>PRICE</th>
                                        <th>QUANTITY</th>
                                        <th>CATEGORY</th>
<%--                                        <c:if test="${acc.getRole() == 1}">--%>
<%--                                            <th>Action</th>--%>
<%--                                        </c:if>--%>
                                        <th>Action</th>
                                    </tr>
                                    </thead>
                                    <tbody class="table-bordered">
                                    <c:forEach var="product" items="${requestScope.productList}">
                                        <tr>
                                            <td><c:out value="${product.getId()}"/></td>
                                            <td><c:out value="${product.getName()}"/></td>
                                            <td>
                                                <img src="${product.getImage()}" style="width: 100px; height: 100px"
                                                     alt="naruto"/>
                                            </td>
                                            <td><c:out value="${product.getPrice()}"/></td>
                                            <td><c:out value="${product.getQuantity()}"/></td>
                                            <td>
                                                    <%--                                                <c:out value="${product.getCategory()}"/>--%>
                                                <c:forEach items="${applicationScope.categoryList}" var="category">
                                                    <c:if test="${category.getId() == product.getCategory()}">
                                                        <c:out value="${category.getName()}"/>
                                                    </c:if>
                                                </c:forEach>
                                            </td>
                                            <c:if test="${acc.getRole() == 1 }">
                                                <td colspan="2">
                                                    <a title="Edit" data-toggle="tooltip"
                                                       href="products?action=edit&id=${product.getId()}"
                                                       class="btn btn-outline-dark mr-1"><i class="fas fa-edit"></i></a>
                                                    <a title="Delete" data-toggle="tooltip"
                                                       href="products?action=delete&id=${product.getId()}"
                                                       class="btn btn-outline-primary ml-1 mr-1"
                                                       onclick="return confirm('Are you sure you want to delete this User')"><i
                                                            class="fas fa-trash"></i></a>

                                                </td>
                                            </c:if>
                                            <c:if test="${acc.getRole() == 2 }">
                                            <td>
                                                <a title="Cart" data-toggle="tooltip"
                                                   href="products?action=addCart&id=${product.getId()}"
                                                   class="btn btn-outline-warning ml-1"><i
                                                        class="fas fa-shopping-cart"></i></a>
                                            </td>
                                            </c:if>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                                <nav style=" position: relative;top: 15px;" aria-label="Page navigation example">
                                    <ul class="pagination">
                                        <c:if test="${requestScope.currentPage != 1}">
                                            <li class="page-item ">
                                                <a class="page-link "
                                                   href="products?page=${requestScope.currentPage - 1}">Previous</a>
                                            </li>
                                        </c:if>
                                        <%--Để hiển thị số Trang.
                                        Điều kiện khi không hiển thị liên kết cho trang hiện tại--%>
                                        <c:forEach begin="1" end="${noOfPages}" var="i">
                                            <c:choose>
                                                <c:when test="${requestScope.currentPage eq i}">
                                                    <li class="page-item "><a class="page-link"
                                                                              href="products?page=${i}">${i}</a></li>
                                                </c:when>
                                                <c:otherwise>
                                                    <li class="page-item ">
                                                        <a class="page-link"
                                                           href="products?page=${i}">${i}</a></li>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:forEach>
                                        <%--Để hiển thị liên kết Tiếp theo--%>
                                        <c:if test="${requestScope.currentPage lt requestScope.noOfPages}">
                                            <li class="page-item ">
                                                <a class="page-link"
                                                   href="products?page=${requestScope.currentPage + 1}">Next</a>
                                            </li>
                                        </c:if>
                                    </ul>
                                </nav>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>


<!-- Vendor js -->
<script src="assets\js\vendor.min.js"></script>

<script src="assets\libs\morris-js\morris.min.js"></script>
<script src="assets\libs\raphael\raphael.min.js"></script>

<script src="assets\js\pages\dashboard.init.js"></script>

<script src="assets\js\app.min.js"></script>
<!-- App js -->

</body>

</html>