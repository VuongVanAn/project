<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<div class="header">
    <div class="headertop_desc">
        <div class="call">
            <p><span>Need help?</span> call us <span class="number">1-22-3456789</span></p>
        </div>
        <div class="account_desc">
            <ul>
                <li><a href='<c:url value="/home.html"/> '><span>Trang chủ</span></a></li>
                <c:if test="${not empty login_name}">
                    <li>Xin chào: ${login_name}</li>
                    <c:url var="logoutUrl" value="/logout.html">
                        <c:param name="action" value="logout"/>
                    </c:url>
                    <li><a href="${logoutUrl}"><span>Thoát</span></a></li>
                </c:if>
                <c:if test="${empty login_name}">
                    <c:url var="loginUrl" value="/login.html">
                        <c:param name="action" value="login"/>
                    </c:url>
                    <li><a href="${loginUrl}"><span>Đăng nhập</span></a></li>
                </c:if>
            </ul>
        </div>
        <div class="clear"></div>
    </div>
</div>
