<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/common/taglib.jsp"%>
<c:url var="editExaminationUrl" value="/ajax-admin-examination-edit.html">
    <c:param name="urlType" value="url_edit"/>
</c:url>
<c:choose>
    <c:when test="${not empty messageResponse}">
        ${messageResponse}
    </c:when>
    <c:otherwise>
        <div class="modal-dialog">
            <!-- Modal content-->
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                    <c:if test="${not empty item.pojo.examinationId}">
                        <h4 class="modal-title"><fmt:message key="label.examination.edit" bundle="${lang}"/></h4>
                    </c:if>
                    <c:if test="${empty item.pojo.examinationId}">
                        <h4 class="modal-title"><fmt:message key="label.examination.add" bundle="${lang}"/></h4>
                    </c:if>
                </div>
                <form action="${editExaminationUrl}" method="POST" id="editExaminationForm">
                    <div class="modal-body">
                        <div class="row">
                            <div class="col-md-12">
                                <div class="md-form">
                                    <input type="text" placeholder="Enter title examination" class="form-control" value="${item.pojo.name}" id="name" name="pojo.name" required/>
                                </div>
                            </div>
                        </div>
                    </div>
                    <c:if test="${not empty item.pojo.examinationId}">
                        <input type="hidden" name="pojo.examinationId" value="${item.pojo.examinationId}"/>
                    </c:if>
                    <input type="hidden" name="crudaction" id="crudactionEdit"/>
                </form>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal"><fmt:message key="label.close" bundle="${lang}"/></button>
                    <button type="button" id="btnSave" class="btn btn-primary"><fmt:message key="label.save" bundle="${lang}"/></button>
                </div>
            </div>
        </div>
    </c:otherwise>
</c:choose>