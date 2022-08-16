<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <title>Product</title>
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
<div class="container" id="wrapper">

    <div class="container">
        <div class="row">
            <!-- LOGO -->
            <div class="logo-box col-sm-4 bg-primary">
                <a href="#" class="logo text-center">
                                    <span class="logo-lg">
                                        <img src="assets\images\logo-dark.png" alt="" height="33">
                                    </span>
                    <span class="logo-sm">
                                        <img src="assets\images\logo-dark.png" alt="" height="22">
                                    </span>
                </a>
            </div>
            <div class="col-sm-8 bg-secondary">

                <a style="float: right " href="products" class="btn btn-outline-light mt-lg-2 width-xl bg-soft-success">
                    <i class="fas fa-user mr-1" aria-hidden="true"></i>
                    <span class="font-17 font-weight-bolder">List Product</span>
                </a>
            </div>
        </div>
    </div>

    <!-- Start Content-->
    <div class="container-fluid">
        <div class="row">
            <div class="col-12">
                <div class="page-title-box">
                    <h4 class="text-center">CREATE PRODUCT</h4>
                </div>
            </div>
        </div>

        <div class="row ">
            <div class="col-sm-12 card-box">
                <form method="post" class="form-horizontal">
                    <fieldset class="row grid-container ">
                        <c:if test="${product != null}">
                            <input type="hidden" name="idPro" value="<c:out value='${product.getId()}'/>">
                        </c:if>
                        <div class="col-md-6">
                            <label class="col-form-label">NAME</label>
                            <div>
                                <input type="text" class="form-control" name="namePro"
                                       value="${product.getName()}">
                            </div>
                        </div>
                        <div class="col-md-6">
                            <label class="col-form-label">IMAGE</label>
                            <div>
                                <input type="text" class="form-control" name="image"
                                       value="${product.getImage()}">
                            </div>
                        </div>
                        <div class="col-md-6">
                            <label class="col-form-label">PRICE</label>
                            <div>
                                <input type="number" class="form-control" name="price"
                                       value="${procduct.getPrice()}">
                                <%--                                        <span style="color: red; font-size: 15px;">${errorPrice}</span>--%>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <label class="col-form-label">QUANTITY</label>
                            <div>
                                <input type="number" class="form-control" name="quantity"
                                       value="${product.getQuantity()}">
                            </div>
                        </div>
                        <div class="col-md-6">
                            <label class="col-form-label">CATEGORY</label>
                            <select class="form-control  text-center" name="idCate">
                                <c:forEach items="${categoryList}" var="cate">
                                    <option value="${cate.getId()}">${cate.getName()}</option>
                                </c:forEach>
                            </select>

                        </div>
                        <div class="col-md-12 mt-4 text-center">
                            <button class="btn btn-outline-pink " type="submit"><i class="fas fa-plus-circle"></i>
                                Create
                            </button>
                        </div>
                    </fieldset>
                </form>
            </div>
            <div>
<%--                <c:if test="${requestScope.errors!=null}">--%>
<%--                    <div class="alert alert-icon alert-danger alert-dismissible fade show mb-0" role="alert">--%>
<%--                        <button type="button" class="close" data-dismiss="alert" aria-label="Close">--%>
<%--                            <span aria-hidden="true">x</span>--%>
<%--                        </button>--%>
<%--                        <c:forEach items="${requestScope.errors}" var="e">--%>
<%--                            &lt;%&ndash;                                                <strong>${e.key}</strong> </br>&ndash;%&gt;--%>
<%--                            <strong>*Field: ${fn:toUpperCase(e.key)}</strong> </br>--%>
<%--                            <c:forEach items="${e.value}" var="item">--%>
<%--                                <span>${item}</span> </br>--%>
<%--                            </c:forEach>--%>
<%--                        </c:forEach>--%>
<%--                    </div>--%>
<%--                </c:if>--%>
            </div>
<%--            <div>--%>
<%--                <c:if test="${requestScope.error!=null}">--%>
<%--                    <div class="alert alert-icon alert-danger alert-dismissible fade show mb-0" role="alert">--%>
<%--                        <button type="button" class="close" data-dismiss="alert" aria-label="Close">--%>
<%--                            <span aria-hidden="true">x</span>--%>
<%--                        </button>--%>
<%--                            ${error}--%>
<%--                    </div>--%>
<%--                </c:if>--%>
<%--            </div>--%>
            <div>
                <c:if test="${requestScope.success!=null}">
                    <div class="alert alert-icon alert-danger alert-dismissible fade show mb-0 bg-success" role="alert">
                            <%--                        <button type="button" class="close" data-dismiss="alert" aria-label="Close">--%>
                            <%--                            <span aria-hidden="true">x</span>--%>
                            <%--                        </button>--%>
                            ${success}
                        <i class="fas fa-check"></i>
                    </div>
                </c:if>
            </div>
        </div>
    </div>

</div>


<!-- end content -->

<!-- Footer Start -->
<footer style="position: fixed;left: 0; bottom: 0;  width: 100vw;  padding-right: 50px; color: #0f5132;}">
    <div >
        <c:if test="${requestScope.errors!=null}">
            <div class="alert alert-icon alert-danger alert-dismissible fade show mb-0" role="alert">
                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                    <span aria-hidden="true">x</span>
                </button>
                <c:forEach items="${requestScope.errors}" var="e">
                    <%--                                                <strong>${e.key}</strong> </br>--%>
                    <strong >*Field: ${fn:toUpperCase(e.key)}</strong> </br>
                    <c:forEach items="${e.value}" var="item">
                        <span>${item}</span> </br>
                    </c:forEach>
                </c:forEach>
            </div>
        </c:if>
    </div>
    <div>
        <c:if test="${requestScope.error!=null}">
            <div class="alert alert-icon alert-danger alert-dismissible fade show mb-0" role="alert">
                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                    <span aria-hidden="true">x</span>
                </button>
                    ${error}
            </div>
        </c:if>
    </div>
<%--    <div>--%>
<%--        <c:if test="${requestScope.success!=null}">--%>
<%--            <div class="alert alert-icon alert-danger alert-dismissible fade show mb-0 bg-success" role="alert">--%>
<%--                    &lt;%&ndash;                        <button type="button" class="close" data-dismiss="alert" aria-label="Close">&ndash;%&gt;--%>
<%--                    &lt;%&ndash;                            <span aria-hidden="true">x</span>&ndash;%&gt;--%>
<%--                    &lt;%&ndash;                        </button>&ndash;%&gt;--%>
<%--                    ${success}--%>
<%--                <i class="fas fa-check"></i>--%>
<%--            </div>--%>
<%--        </c:if>--%>
<%--    </div>--%>
</footer>


<!-- Vendor js -->
<script src="assets\js\vendor.min.js"></script>

<!-- App js -->
<script src="assets\js\app.min.js"></script>

</body>

</html>
