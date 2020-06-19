<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/common/taglib.jsp"%>
<c:url var="validateExcel" value="/admin-exercise-import-validate.html"/>
<c:url var="importExcel" value="/admin-exercise-import.html"/>
<html>
<head>
    <title>Thêm bài tập</title>
</head>
<body>
<div class="main-content">
    <div class="main-content-inner">
        <div class="breadcrumbs" id="breadcrumbs">
            <script type="text/javascript">
                try{ace.settings.check('breadcrumbs' , 'fixed')}catch(e){}
            </script>
            <ul class="breadcrumb">
                <li>
                    <i class="ace-icon fa fa-home home-icon"></i>
                    <a href="#">Trang chủ</a>
                </li>
                <li>Danh sách bài tập</li>
                <li class="active">Thêm bài tập</li>
            </ul><!-- /.breadcrumb -->
        </div>
        <div class="page-content">
            <div class="row">
                <div class="col-xs-12">
                    <c:if test="${messageResponse!=null}">
                        <div class="alert alert-block alert-${alert}">
                            <button type="button" class="close" data-dismiss="alert">
                                <i class="ace-icon fa fa-times"></i>
                            </button>
                                ${messageResponse}
                        </div>
                    </c:if>
                    <form action="${validateExcel}" method="post" enctype="multipart/form-data" id="formImport">
                        <div class="row">
                            <div class="col-xs-12">
                                <div class="col-sm-12">
                                    <input type="file" name="file"/>
                                    <br/>
                                    <button type="button" class="dt-button buttons-html5 btn btn-white btn-primary btn-bold" id="validateData">
                                        <fmt:message key="label.file.validate.import" bundle="${lang}"/>
                                    </button>
                                </div>
                            </div>
                        </div>
                        <c:if test="${not empty items.exerciseQuestionDTOS}">
                            <div class="row">
                                <div class="col-xs-12">
                                    <div class="table-responsive">
                                        <fmt:bundle basename="ResourcesBundle">
                                            <display:table name="items.exerciseQuestionDTOS" cellspacing="0" cellpadding="0" requestURI="${requestUrl}"
                                                           partialList="true" sort="external" size="${items.totalItems}" id="tableList" excludedParams="checkList"
                                                           pagesize="${items.maxPageItems}" export="false"
                                                           class="table table-fcv-ace table-striped table-bordered table-hover dataTable no-footer"
                                                           style="margin: 3em 0 1.5em;">
                                                <display:column headerClass="text-left" property="image" titleKey="label.exercise.image"/>
                                                <display:column headerClass="text-left" property="audio" titleKey="label.exercise.audio"/>
                                                <display:column headerClass="text-left" property="question" titleKey="label.exercise.question"/>
                                                <display:column headerClass="text-left" property="exerciseId" titleKey="label.exercise.exerciseId"/>
                                                <display:column headerClass="text-left" property="error" titleKey="label.import.error"/>
                                            </display:table>
                                        </fmt:bundle>
                                    </div>
                                </div>
                            </div>
                            <button type="button" class="dt-button buttons-html5 btn btn-white btn-primary btn-bold" id="importData">
                                <fmt:message key="label.user.import" bundle="${lang}"/>
                            </button>
                        </c:if>
                        <input type="hidden" name="urlType" id="urlType"/>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<script>
    $(document).ready(function () {
        $('#validateData').click(function () {
            $('#urlType').val('read_excel');
            $('#formImport').submit();
        });
        $('#importData').click(function () {
            $('#urlType').val('import_data');
            $('#formImport').prop('enctype', false);
            $('#formImport').attr('action', '${importExcel}');
            $('#formImport').submit();
        });
    });
</script>
</body>
</html>