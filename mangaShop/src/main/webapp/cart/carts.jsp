<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <title>ORDER</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta content="Responsive bootstrap 4 admin template" name="description">
    <meta content="Coderthemes" name="author">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <!-- App favicon -->
    <link rel="shortcut icon" href="\assets\images\favicon.ico">
    <!-- App css -->
    <link href="\assets\css\bootstrap.min.css" rel="stylesheet" type="text/css" id="bootstrap-stylesheet">
    <link href="\assets\css\icons.min.css" rel="stylesheet" type="text/css">
    <link href="\assets\css\app.min.css" rel="stylesheet" type="text/css" id="app-stylesheet">
</head>

<body>
<!-- Begin page -->
<div id="wrapper">

    <%--   navbar-customer--%>
    <%--  <jsp:include page="/layout/navbarPr.jsp"></jsp:include>--%>
    <div class="navbar-custom">
        <!-- LOGO -->
        <div class="logo-box">
            <a href="#" class="logo text-center">
                        <span class="logo-lg bg-soft-success">
                            <img src="/assets/images/logo-dark.png" alt="" height="24">
                            <!-- <span class="logo-lg-text-light">Upvex</span> -->
                        </span>
                <span class="logo-sm">
                            <!-- <span class="logo-sm-text-dark">X</span> -->
                            <img src="/assets/images/logo-dark.png" alt="" height="28">
                        </span>
            </a>
        </div>
    </div>
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
                                <a href="haii.jsp">Dashboard 1</a>
                            </li>
                            <li>
                                <a href="dashboard-2.html">Dashboard 2</a>
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
                            <h4 class="page-title"><i class="fas fa-users"></i> LIST ORDER</h4>
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
                                        <th>NAME PRODUCT</th>
                                        <th>PRICE</th>
                                        <th>QUANTITY</th>
                                        <th>Total Price</th>
                                        <th>ACTION</th>
                                    </tr>
                                    </thead>
                                    <tbody class="table-bordered">
                                    <c:forEach var="item" items="${requestScope.order}">
                                        <tr>
                                            <td>${item.getId()}</td>
                                            <td>${item.getName()}</td>
                                            <td>${item.getPrice()}</td>
                                            <td>${item.getQuantity()}</td>
                                            <td>${item.getTotal()}</td>
                                            <td>
                                                <a title="Delete" data-toggle="tooltip"
                                                   href="users?action=delete&id=${item.getId()}"
                                                   class="btn btn-outline-primary ml-1"
                                                   onclick="return confirm('Are you sure you want to delete this User')"><i
                                                        class="fas fa-trash"></i>
                                                </a>

                                            </td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                                <%--                <div class="container">--%>
                                <%--                  <div class="row py-5 p-4 bg-white rounded shadow-sm">--%>
                                <%--                    <div class="col-lg-6">--%>
                                <%--                      <div class="bg-light rounded-pill px-4 py-3 text-uppercase font-weight-bold">Voucher</div>--%>
                                <%--                      <div class="p-4">--%>
                                <%--                        <div class="input-group mb-4 border rounded-pill p-2">--%>
                                <%--                          <input type="text" placeholder="Nhập Voucher" aria-describedby="button-addon3"--%>
                                <%--                                 class="form-control border-0">--%>
                                <%--                          <div class="input-group-append border-0">--%>
                                <%--                            <button   type="button" class="btn btn-dark px-4 rounded-pill"><i--%>
                                <%--                                    class="fa fa-gift mr-2"></i>Sử dụng--%>
                                <%--                            </button>--%>
                                <%--                          </div>--%>
                                <%--                        </div>--%>
                                <%--                      </div>--%>
                                <%--                    </div>--%>
                                <%--                    <div class="col-lg-6">--%>
                                <%--                      <div class="bg-light rounded-pill px-4 py-3 text-uppercase font-weight-bold">Thành tiền</div>--%>
                                <%--                      <div class="p-4">--%>
                                <%--                        <ul class="list-unstyled mb-4">--%>
                                <%--                          <li class="d-flex justify-content-between py-3 border-bottom"><strong--%>
                                <%--                                  class="text-muted">Tổng tiền hàng</strong><strong>100 $</strong></li>--%>
                                <%--                          <li class="d-flex justify-content-between py-3 border-bottom"><strong--%>
                                <%--                                  class="text-muted">Phí vận chuyển</strong><strong>Free ship</strong></li>--%>
                                <%--                          <li class="d-flex justify-content-between py-3 border-bottom"><strong--%>
                                <%--                                  class="text-muted">VAT</strong><strong>10 $</strong></li>--%>
                                <%--                          <li class="d-flex justify-content-between py-3 border-bottom"><strong--%>
                                <%--                                  class="text-muted">Tổng thanh toán</strong>--%>
                                <%--                            <h5 class="font-weight-bold">110 $</h5>--%>
                                <%--                          </li>--%>
                                <%--                        </ul>--%>
                                <%--                        <a href="buy" class="btn btn-dark rounded-pill py-2 btn-block">Mua hàng</a>--%>
                                <%--                      </div>--%>
                                <%--                    </div>--%>
                                <%--                  </div>--%>

                                <%--                </div>--%>
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
<script src="\assets\js\vendor.min.js"></script>

<script src="\assets\libs\morris-js\morris.min.js"></script>
<script src="\assets\libs\raphael\raphael.min.js"></script>

<script src="\assets\js\pages\dashboard.init.js"></script>

<script src="\assets\js\app.min.js"></script>
<!-- App js -->

</body>

</html>
