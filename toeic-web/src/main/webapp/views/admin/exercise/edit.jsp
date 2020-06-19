<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/common/taglib.jsp"%>
<c:url var="editExerciseUrl" value="/ajax-admin-exercise-edit.html">
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
                    <c:if test="${not empty item.pojo.exerciseId}">
                        <h4 class="modal-title"><fmt:message key="label.exercise.edit" bundle="${lang}"/></h4>
                    </c:if>
                    <c:if test="${empty item.pojo.exerciseId}">
                        <h4 class="modal-title"><fmt:message key="label.exercise.add" bundle="${lang}"/></h4>
                    </c:if>
                </div>
                <form action="${editExerciseUrl}" method="POST" id="editExerciseForm">
                    <div class="modal-body">
                        <div class="row">
                            <div class="col-md-12">
                                <div class="md-form">
                                    <input type="text" placeholder="Enter title exercise" class="form-control" value="${item.pojo.name}" id="name" name="pojo.name" required/>
                                </div>
                            </div>
                            <br/>
                            <br/>
                            <div class="col-md-12">
                                <div class="md-form">
                                    <input type="text" placeholder="Enter type exercise" class="form-control" value="${item.pojo.type}" name="pojo.type"/>
                                </div>
                            </div>
                        </div>
                    </div>
                    <c:if test="${not empty item.pojo.exerciseId}">
                        <input type="hidden" name="pojo.exerciseId" value="${item.pojo.exerciseId}"/>
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