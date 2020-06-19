<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@include file="/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Trang chủ</title>
</head>
<body>
<!--Carousel
==================================================-->
<div id="myCarousel" class="carousel slide">
    <div class="carousel-inner">
        <div class="active item">
            <div class="container">
                <div class="row">
                    <div class="span6">
                        <div class="carousel-caption">
                            <h1>Toeic 24h Online</h1>
                            <p class="lead">Thi thử Toeic miễn phí mọi lúc mọi nơi, chỉ có trên website 24h.com</p>
                            <a class="btn btn-large btn-primary" href="/danh-sach-bai-thi.html">Thi thử ngay</a>
                        </div>
                    </div>
                    <div class="span6"> <img src='<c:url value="/template/web/img/slide/banner.webp"/> ' width="500px" height="500px" class="img-rounded"></div>
                </div>
            </div>
        </div>
        <div class="item">
            <div class="container">
                <div class="row">
                    <div class="span6">
                        <div class="carousel-caption">
                            <h1>Đăng nhập tài khoản</h1>
                            <p class="lead">Đăng nhập hoặc tạo tài khoản miễn phí để làm nhiều đề thi hơn</p>
                            <a class="btn btn-large btn-primary" href="#">Đăng nhập ngay</a>
                        </div>
                    </div>
                    <div class="span6"> <img src='<c:url value="/template/web/img/slide/slide2.jpg"/> '></div>
                </div>
            </div>
        </div>
    </div>
    <!-- Carousel nav -->
    <a class="carousel-control left " href="#myCarousel" data-slide="prev"><i class="icon-chevron-left"></i></a>
    <a class="carousel-control right" href="#myCarousel" data-slide="next"><i class="icon-chevron-right"></i></a>
    <!-- /.Carousel nav -->
</div>
<!-- /Carousel -->
<!-- Feature
==============================================-->
<div class="row feature-box">
    <div class="span12 cnt-title">
        <h1>Tại sao chọn Toeic24.com?</h1>
        <span>Trải nghiệm, nâng cao kĩ năng trước khi bước vào kì thi.</span>
    </div>
    <div class="span4">
        <img src='<c:url value="/template/web/img/icon3.png"/> '>
        <h2>Hướng dẫn học</h2>
        <p>
            Giúp các bạn nắm chắc các kiến thức cơ bản về kì thi Toeic.
        </p>
        <a href="<c:url value="/danh-sach-huong-dan-nghe.html"/>">Đọc thêm &rarr;</a>
    </div>

    <div class="span4">
        <img src='<c:url value="/template/web/img/icon2.png"/> '>
        <h2>Luyện tập</h2>
        <p>
            Rèn luyện 2 kỹ năng TOEIC Reading và TOEIC Listening nhanh nhất.
        </p>
        <c:url var="listExercise" value="/danh-sach-bai-tap.html">
            <c:param name="pojo.type" value="listening"/>
        </c:url>
        <a href="${listExercise}">Đọc thêm &rarr;</a>
    </div>

    <div class="span4">
        <img src='<c:url value="/template/web/img/icon1.png"/> '>
        <h2>Thi thử ngay</h2>
        <p>
            Sau khi làm bài bạn có thể kiểm tra đáp án, giải thích chi tiết từng đáp án.
        </p>
        <a href="<c:url value="/danh-sach-bai-thi.html"/>">Đọc thêm &rarr;</a>
    </div>
</div>
<!-- /.Feature -->
<div class="hr-divider"></div>
<!-- Row View -->
<div class="row">
    <div class="span6"><img src='<c:url value="/template/web/img/slide/slide1.jpg"/> '></div>
    <div class="span6">
        <img src='<c:url value="/template/web/img/icon4.png"/> '>
        <h1>Đánh giá của người dùng</h1>
        <p>Website rất tiện lợi, có thể luyện đề thi TOEIC mọi lúc mọi nơi, có chấm điểm và lời giải chi tiết sau khi làm xong đề.</p>
        <a href="#">Read More &rarr;</a>
    </div>
</div>
</body>
</html>