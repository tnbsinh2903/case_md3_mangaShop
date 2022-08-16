<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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

<body style="color:black">

<!-- Begin page -->
<div class="container" id="wrapper">

    <jsp:include page="/layout/nbUserEditAdd.jsp"></jsp:include>


    <div class="container-fluid">
        <div class="row">
            <div class="col-12">
                <div class="page-title-box">
                    <h4 class="text-center">EDIT USER</h4>
                </div>
            </div>
        </div>
        <div class="row ">
            <div class="col-sm-12 card-box">
                <form method="post" class="form-horizontal">
                    <fieldset class="row grid-container ">
                        <c:if test="${user != null}">
                            <input type="hidden" name="id" value="<c:out value='${user.getId()}'/>">
                        </c:if>
                        <div class="col-md-6  ">
                            <label class="col-form-label">NAME</label>
                            <div>
                                <input type="text" class="form-control  text-center" name="name"
                                       value="<c:out value="${user.getName()}"/>">
                            </div>
                        </div>
                        <div class="col-md-6  ">
                            <label class="col-form-label">PHONE</label>
                            <div>
                                <input type="text" class="form-control text-center" name="phone"
                                       value="<c:out value="${user.getPhone()}"/>">
                            </div>
                        </div>
                        <div class="col-md-6 ">
                            <label class=" col-form-label">PASSWORD</label>
                            <div>
                                <input type="password" class="form-control  text-center" name="password"
                                       value="<c:out value="${user.getPassword()}"/>">
                            </div>
                        </div>
                        <div class="col-md-6 ">
                            <label class=" col-form-label">EMAIL</label>
                            <div>
                                <input type="text" class="form-control  text-center" name="email"
                                       value="<c:out value="${user.getEmail()}"/>">
                            </div>
                        </div>
                        <div class="col-md-6 ">
                            <label class=" col-form-label">ADDRESS</label>
                            <div>
                                <input type="text" class="form-control  text-center" name="address"
                                       value="<c:out value="${user.getAddress()}"/>">
                            </div>
                        </div>
                        <div class="col-md-6 ">
                            <label class="  col-form-label">ROLE</label>
                            <select name="idRole" class="form-control  text-center">
                                <c:forEach items="${roleList}" var="role">
                                    <%--                                            <option value="${role.getId()}" >${role.getName()}</option>--%>
                                    <c:choose>
                                        <c:when test="${role.getId() == user.getRole()}">
                                            <option value="${role.getId()}" selected>${role.getName()}</option>
                                        </c:when>
                                        <c:otherwise>
                                            <option value="${role.getId()}">${role.getName()}</option>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                            </select>

                        </div>
                        <div class="col-md-12 mt-4 text-center">
                            <button class="btn btn-outline-pink " type="submit"><i class="fas fa-save"></i> Update
                            </button>
                        </div>
                    </fieldset>
                </form>
            </div>
            <div>
                <c:if test="${requestScope.errors!=null}">
                    <div class="alert alert-icon alert-danger alert-dismissible fade show mb-0" role="alert">
                        <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                            <span aria-hidden="true">x</span>
                        </button>
                        <c:forEach items="${requestScope.errors}" var="e">
                            <%--                                                <strong>${e.key}</strong> </br>--%>
                            <strong>*Field: ${fn:toUpperCase(e.key)}</strong> </br>
                            <c:forEach items="${e.value}" var="item">
                                <span>${item}</span> </br>
                            </c:forEach>
                        </c:forEach>
                    </div>
                </c:if>
                <%--                ${errors}--%>
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


<!-- Vendor js -->
<script src="assets\js\vendor.min.js"></script>
<!-- App js -->
<script src="assets\js\app.min.js"></script>
</body>

</html>