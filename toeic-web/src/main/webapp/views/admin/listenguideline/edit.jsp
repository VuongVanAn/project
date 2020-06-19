<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/common/taglib.jsp"%>
<c:url var="formUrl" value="/admin-guideline-listen-edit.html" />
<html>
<head>
    <title>Chỉnh sửa bài hướng dẫn</title>
    <style>
        .error{
            color: red;
        }
    </style>
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
                    <a href="#"></a>
                </li>
                <li class="active"></li>
            </ul><!-- /.breadcrumb -->
        </div>
        <div class="page-content">
            <div class="row">
                <div class="col-xs-12">
                    <c:if test="${not empty messageResponse}">
                        <div class="alert alert-block alert-">
                            <button type="button" class="close" data-dismiss="alert">
                                <i class="ace-icon fa fa-times"></i>
                            </button>
                                ${messageResponse}
                        </div>
                    </c:if>
                    <form action="${formUrl}" method="post" enctype="multipart/form-data" id="formEdit">
                        <div class="form-group">
                            <label class="col-sm-3 control-label no-padding-right"><fmt:message key="label.guideline.title" bundle="${lang}"/></label>
                            <div class="col-sm-9">
                                <input class="form-control" type="text" name="pojo.title" id="title" value="${item.pojo.title}"/>
                            </div>
                        </div>
                        <br/><br/><br/>
                        <div class="form-group">
                            <label class="col-sm-3 control-label no-padding-right"><fmt:message key="label.grammarguideline.upload.image" bundle="${lang}"/></label>
                                <input type="file" name="file" id="uploadImage"/>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label no-padding-right"></label>
                            <div class="col-sm-9">
                                <c:if test="${not empty item.pojo.image}">
                                    <c:set var="image" value="/repository/${item.pojo.image}"/>
                                </c:if>
                                <img src="${image}" id="viewImage" width="150px" height="150ox" style="margin: 15px 0 15px;">
                            </div>
                        </div>
                        <br/><br/><br/>
                        <div class="form-group">
                            <label class="col-sm-3 control-label no-padding-right"><fmt:message key="label.guideline.desc" bundle="${lang}"/></label>
                            <div class="col-sm-9">
                                <input class="form-control" type="text" name="pojo.description" id="description" value="${item.pojo.description}" style="margin-bottom: 15px;"/>
                            </div>
                        </div>
                        <br/><br/><br/>
                        <div class="form-group">
                            <label class="col-sm-3 control-label no-padding-right"><fmt:message key="label.guideline.content" bundle="${lang}"/></label>
                            <div class="col-sm-9">
                                <c:if test="${not empty item.pojo.content}">
                                    <c:set var="content" value="${item.pojo.content}"/>
                                </c:if>
                                <textarea name="pojo.content" id="ListenGuidelineContent" style="width: 820px;height: 175px">${content}</textarea>
                            </div>
                        </div>
                        <br/>
                        <div class="form-group">
                            <div class="col-sm-12">
                                <input type="submit" class="btn btn-white btn-warning btn-bold" value="<fmt:message key="label.done" bundle="${lang}"/>"/>
                            </div>
                        </div>
                        <c:if test="${not empty item.pojo.listenGuidelineId}">
                            <input type="hidden" name="pojo.listenGuidelineId" value="${item.pojo.listenGuidelineId}"/>
                        </c:if>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
 <script>
     var listenGuidelineId = '';
     <c:if test="${not empty item.pojo.listenGuidelineId}">
     listenGuidelineId = ${item.pojo.listenGuidelineId};
     </c:if>
     var editor = '';
     $(document).ready(function () {
         editor = CKEDITOR.replace( 'ListenGuidelineContent' );
         CKFinder.setupCKEditor( editor, '/ckfinder/' );
         validateData();
         $('#uploadImage').change(function () {
             readURL(this, "viewImage");
         });
     });
     function validateData() {
         $('#formEdit').validate({
             ignore: [],
             rules: [],
             messages: []
         });
         $("#title").rules( "add", {
             required: true,
             messages: {
                 required: '<fmt:message key="label.empty" bundle="${lang}"/>'
             }
         });
         if (listenGuidelineId == '') {
             $("#uploadImage").rules( "add", {
                 required: true,
                 messages: {
                     required: '<fmt:message key="label.empty" bundle="${lang}"/>'
                 }
             });
         }
         $("#description").rules( "add", {
             required: true,
             messages: {
                 required: ''
             }
         });
         $("#ListenGuidelineContent").rules( "add", {
             required: function () {
                 CKEDITOR.instances.ListenGuidelineContent.updateElement();
             },
             messages: {
                 required: '<fmt:message key="label.empty" bundle="${lang}"/>'
             }
         });
     }
     function readURL(input, imageId) {
         if (input.files && input.files[0]) {
             var reader = new FileReader();
             reader.onload = function (e) {
                 $('#' +imageId).attr('src', reader.result);
             }
             reader.readAsDataURL(input.files[0]);
         }
     }
 </script>
</body>
</html>