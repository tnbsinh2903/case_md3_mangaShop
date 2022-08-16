<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <title>User</title>
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
                            <li>
                                <a href="/products">List Manga </a>
                            </li>
                            <li>

                            </li>
                        </ul>
                    </li>
                </ul>
            </div>
            <div class="clearfix"></div>
        </div>
        <!-- Sidebar -left -->
    </div>

    <div class="content-page">
        <div class="content">
            <div class="container-fluid">
                <div class="row">
                    <div class="col-12">
                        <div class="page-title-box">
                            <div class="page-title-right">
                                <a style="float: right " href="users?action=create" class="btn btn-outline-light mt-lg-2 width-xl bg-danger">
                                    <i style="color: blue" class="fas fa-plus-square mr-1" aria-hidden="true"></i>
                                    <span style="color: blue" class="font-17 font-weight-bolder ">Create User</span>
                                </a>

                            </div>

                            <h4 class="page-title"> <i class="fas fa-users"></i> USER LIST</h4>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-md-12">
                        <div class="card-box">
                            <form action="users">
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
                                        <th>Phone</th>
<%--                                        <th>PassWord</th>--%>
                                        <th>Email</th>
                                        <th>Address</th>
                                        <th>Role</th>
                                        <th>Action</th>
                                    </tr>
                                    </thead>
                                    <tbody class="table-bordered">
                                    <c:forEach var="user" items="${requestScope.listUser}">
                                        <tr>
                                            <td><c:out value="${user.getId()}"/></td>
                                            <td><c:out value="${user.getName()}"/></td>
                                            <td><c:out value="${user.getPhone()}"/></td>
<%--                                            <td>${user.getPassword()}</td>--%>
                                            <td><c:out value="${user.getEmail()}"/></td>
                                            <td><c:out value="${user.getAddress()}"/></td>
                                            <td>
                                                <c:forEach items="${applicationScope.roleList }" var="role">
                                                    <c:if test="${role.getId() == user.getRole()}">
                                                        <c:out value="${role.getName()}"/>
                                                    </c:if>
                                                </c:forEach>
                                            </td>
                                            <td colspan="2">
                                                <a  title="Edit" data-toggle="tooltip" href="users?action=edit&id=${user.getId()}"
                                                   class="btn btn-outline-dark mr-1"><i class="fas fa-edit"></i></a>
                                                <a  title="Delete" data-toggle="tooltip" href="users?action=delete&id=${user.getId()}"
                                                   class="btn btn-outline-primary ml-1"
                                                   onclick="return confirm('Are you sure you want to delete this User')"><i class="fas fa-trash"></i></a>

                                            </td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                                <nav style=" position: relative;top: 15px;" aria-label="Page navigation example">
                                    <ul class="pagination">
                                        <c:if test="${requestScope.currentPage != 1}">
                                            <li class="page-item ">
                                                <a class="page-link " href="users?page=${requestScope.currentPage - 1}">Previous</a>
                                            </li>
                                        </c:if>
                                        <%--Để hiển thị số Trang.
                                        Điều kiện khi không hiển thị liên kết cho trang hiện tại--%>
                                        <c:forEach begin="1" end="${noOfPages}" var="i">
                                            <c:choose>
                                                <c:when test="${requestScope.currentPage eq i}">
                                                    <li class="page-item "><a class="page-link"
                                                                              href="users?page=${i}">${i}</a></li>
                                                </c:when>
                                                <c:otherwise>
                                                    <li class="page-item ">
                                                        <a class="page-link"
                                                           href="users?page=${i}">${i}</a></li>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:forEach>
                                        <%--Để hiển thị liên kết Tiếp theo--%>
                                        <c:if test="${requestScope.currentPage lt requestScope.noOfPages}">
                                            <li class="page-item ">
                                                <a class="page-link" href="users?page=${requestScope.currentPage + 1}">Next</a>
                                            </li>
                                        </c:if>
                                    </ul>
                                </nav>
                            </div>
                            <div>
                                <c:if test="${requestScope['rowDeleted'] == true}">
                                    <ul class="success">
                                        <li>Delete sucsess</li>
                                    </ul>
                                </c:if>
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