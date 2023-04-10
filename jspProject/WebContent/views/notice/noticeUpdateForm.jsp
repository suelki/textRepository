<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.kh.notice.model.vo.Notice"%>
    
    <%
    	Notice n = (Notice)request.getAttribute("notice");
    %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
        .outer{
            background-color: rgb(153, 139, 245);
            color: black;
            width: 1000px;
            height: 500px;
            margin: auto;
            margin-top: 50px;
        }
        #enroll-form>table{border : 1px solid white;}
        #enroll-form input,textarea{
        	width : 100%;
        	box-sizing:border-box;
        }
</style>
</head>
<body>
	<%@include file="../common/menubar.jsp" %>
	<div class="outer">
		<h2 align="center">공지사항 수정</h2>
		
		<form action="<%=contextPath%>/update.no" method="post" id="enroll-form">
		<input type="hidden" name="nno" value="<%=n.getNoticeNo()%>">
			
			<table align="center">
				<tr>
					<td width="50">제목</td>
					<td width="350"><input type="text" name="title" value="<%=n.getNoticeTitle()%>" required></td>
				</tr>
				<tr>
					<td>내용</td>
					<td></td>
				</tr>
				<tr>
					<td colspan="2">
						<textarea rows="10" cols="20" name="content" style="resize:none"  required>
						<%=n.getNoticeContent() %>
						</textarea>
					</td>
					
				</tr>
			</table>
			<br><br>
			<div align="center">
				 <button type="submit">수정하기</button>
				 <button type="button" onclick="history.back();">뒤로가기</button>
				 
			</div>
		
		</form>
	</div>
</body>
</html>