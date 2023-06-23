<%@page import="java.util.ArrayList" %>
<%@ page import="pe.edu.pucp.tel131lab9.bean.Post" %>
<jsp:useBean id="posts" type="java.util.ArrayList<pe.edu.pucp.tel131lab9.bean.Post>" scope="request"/>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Lista empleados</title>
    <jsp:include page="../includes/headCss.jsp"></jsp:include>
</head>
<body>
<div class='container'>
    <jsp:include page="../includes/navbar.jsp">
        <jsp:param name="currentPage" value="newPost"/>
    </jsp:include>
    <div class="row mb-5 mt-4">
        <div class="col-md-7">
            <h1>New Post</h1>
        </div>
    </div>

    <div class="tabla">
        <form method="POST" action="<%=request.getContextPath()%>/PostServlet?a=new">
            <div class="mb-3">
                <label class='text-light' for="title">titulo</label>
                <input type="text" class="form-control" name="title" id="title">
            </div>
            <div class="mb-3">
                <label class='text-light' for="content">Contenido</label>
                <input type="text" class="form-control" name="content" id="content">
            </div>
            <a class="btn btn-danger" href="<%=request.getContextPath()%>/">Cancelar</a>
            <button type="submit" class="btn btn-light">Guardar</button>
        </form>
    </div>


    <jsp:include page="../includes/footer.jsp"/>
</div>
</body>
</html>
