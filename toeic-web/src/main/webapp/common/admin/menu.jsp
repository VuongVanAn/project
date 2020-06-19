<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@include file="/common/taglib.jsp"%>
<c:url value="/admin-guideline-listen-list.html" var="listenGuidelineListUrl">
    <c:param name="urlType" value="url_list"/>
</c:url>
<c:url value="/admin-user-list.html" var="userListUrl">
    <c:param name="urlType" value="url_list"/>
</c:url>
<c:url value="/admin-exercise-list.html" var="exerciseListUrl">
    <c:param name="urlType" value="url_list"/>
</c:url>
<c:url value="/admin-examination-list.html" var="examinationListUrl">
    <c:param name="urlType" value="url_list"/>
</c:url>
<div id="sidebar" class="sidebar                  responsive                    ace-save-state">
    <script type="text/javascript">
        try{ace.settings.loadState('sidebar')}catch(e){}
    </script>

    <div class="sidebar-shortcuts" id="sidebar-shortcuts">
        <div class="sidebar-shortcuts-large" id="sidebar-shortcuts-large">
            <button class="btn btn-success">
                <i class="ace-icon fa fa-signal"></i>
            </button>

            <button class="btn btn-info">
                <i class="ace-icon fa fa-pencil"></i>
            </button>

            <button class="btn btn-warning">
                <i class="ace-icon fa fa-users"></i>
            </button>

            <button class="btn btn-danger">
                <i class="ace-icon fa fa-cogs"></i>
            </button>
        </div>

        <div class="sidebar-shortcuts-mini" id="sidebar-shortcuts-mini">
            <span class="btn btn-success"></span>

            <span class="btn btn-info"></span>

            <span class="btn btn-warning"></span>

            <span class="btn btn-danger"></span>
        </div>
    </div><!-- /.sidebar-shortcuts -->

    <ul class="nav nav-list">
        <li class="">
            <a href="#">
                <i class="menu-icon fa fa-tachometer"></i>
                <span class="menu-text">Trang chủ</span>
            </a>
            <b class="arrow"></b>
        </li>

        <li class="">
            <a href="#" class="dropdown-toggle">
                <i class="menu-icon fa fa-list"></i>
                <span class="menu-text">Quản lý người dùng</span>
                <b class="arrow fa fa-angle-down"></b>
            </a>

            <b class="arrow"></b>
            <ul class="submenu">
                <li class="">
                    <a href="${userListUrl}">
                        <i class="menu-icon fa fa-caret-right"></i>
                        Danh sách người dùng
                    </a>
                    <b class="arrow"></b>
                </li>
            </ul>
        </li>

        <li class="">
            <a href="#" class="dropdown-toggle">
                <i class="menu-icon fa fa-desktop"></i>
                <span class="menu-text">Quản lý HD </span>
                <b class="arrow fa fa-angle-down"></b>
            </a>

            <b class="arrow"></b>
            <ul class="submenu">
                <li class="">
                    <a href="${listenGuidelineListUrl}">
                        <i class="menu-icon fa fa-caret-right"></i>
                        Danh sách HD
                    </a>
                    <b class="arrow"></b>
                </li>
            </ul>
        </li>

        <li class="">
            <a href="#" class="dropdown-toggle">
                <i class="menu-icon fa fa-pencil-square-o"></i>
                <span class="menu-text">Quản lý bài tập</span>
                <b class="arrow fa fa-angle-down"></b>
            </a>

            <b class="arrow"></b>
            <ul class="submenu">
                <li class="">
                    <a href="${exerciseListUrl}">
                        <i class="menu-icon fa fa-caret-right"></i>
                        Danh sách bài tập
                    </a>
                    <b class="arrow"></b>
                </li>
            </ul>
        </li>

        <li class="">
            <a href="#" class="dropdown-toggle">
                <i class="menu-icon fa fa-tag"></i>
                <span class="menu-text">Quản lí đề thi</span>
                <b class="arrow fa fa-angle-down"></b>
            </a>
            <b class="arrow"></b>
            <ul class="submenu">
                <li class="">
                    <a href="${examinationListUrl}">
                        <i class="menu-icon fa fa-caret-right"></i>
                        Danh sách đề thi
                    </a>
                    <b class="arrow"></b>
                </li>
            </ul>
        </li>

        <li class="">
            <a href="<c:url value="/admin-upload.html"/>">
                <i class="menu-icon fa fa-picture-o"></i>
                <span class="menu-text">Audio, hình ảnh</span>
            </a>
            <b class="arrow"></b>
        </li>
    </ul>

    <div class="sidebar-toggle sidebar-collapse" id="sidebar-collapse">
        <i id="sidebar-toggle-icon" class="ace-icon fa fa-angle-double-left ace-save-state" data-icon1="ace-icon fa fa-angle-double-left" data-icon2="ace-icon fa fa-angle-double-right"></i>
    </div>
</div>
