<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>流浪宠物救助系统|修改密码</title>
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <jsp:include page="include/css.jsp"/>
</head>
<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">
    <jsp:include page="include/header.jsp"/>
    <jsp:include page="include/sider.jsp"/>
    <div class="content-wrapper">
        <section class="content">
            <c:if test="${not empty message}">
                <div class="alert alert-success text-center">${message}</div>
            </c:if>
            <div class="box">
                <div class="box-header with-border">
                    <h3 class="box-title">填写信息</h3>

                    <div class="box-tools pull-right">
                        <button type="button" class="btn btn-box-tool" data-widget="collapse" data-toggle="tooltip"
                                title="Collapse">
                            <i class="fa fa-minus"></i></button>
                        <button type="button" class="btn btn-box-tool" data-widget="remove" data-toggle="tooltip" title="Remove">
                            <i class="fa fa-times"></i></button>
                    </div>
                </div>
                <div class="box-body">
                    <form method="post" id="saveForm" class="form-horizontal">
                        <div class="row">
                            <div class="col-md-8">
                                <div class="form-group">
                                    <label class="col-md-3 control-label">原密码:</label>
                                    <div class="col-md-8">
                                        <input type="password" name="oldpass" class="form-control" autofocus/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 control-label">新密码:</label>
                                    <div class="col-md-8">
                                        <input type="password" name="newpass" class="form-control"/>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </form>
                </div>
                <div class="box-footer">
                    <button class="btn btn-primary pull-right" id="saveBtn">修改</button>
                </div>
            </div>
        </section>
    </div>
    <jsp:include page="include/footer.jsp"></jsp:include>
</div>
<jsp:include page="include/js.jsp"></jsp:include>
<script src="/static/plugins/jquery.validate.min.js"></script>
<script>
    $(function () {
        $("#saveBtn").click(function () {
            layer.confirm('确定修改密码吗？', {
                btn: ['修改','取消'] //按钮
            }, function(){
                $("#saveForm").submit();
            });
        });
        //表单校验
        $("#saveForm").validate({
            errorClass : 'text-danger',
            errorElement : 'span',
            rules : {
                oldpass : {
                    "required" : true,
                    "rangelength":[5,17]
                },
                newpass : {
                    "required" : true,
                    "rangelength":[5,17]
                }
            },
            messages :{
                oldpass : {
                    "required" : "请输入原密码",
                    "rangelength":"密码长度为5-17位"
                },
                newpass : {
                    "required" : "请输入新密码",
                    "rangelength":"密码长度为5-17位"
                }
            }
        });
    })
</script>
</body>
</html>

