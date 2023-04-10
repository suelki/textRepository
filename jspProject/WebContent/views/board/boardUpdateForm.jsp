<%@page import="com.kh.board.model.vo.Category"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.kh.board.model.vo.Attachment"%>
<%@page import="com.kh.board.model.vo.Board"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%
    	Board b = (Board)request.getAttribute("board");
    	Attachment at = (Attachment)request.getAttribute("attachment");
    	ArrayList<Category> list = (ArrayList<Category>)request.getAttribute("clist");
    	
    %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
	
	 #update-form>table{border : 1px solid white;}
     #update-form input,textarea{
        	width : 100%;
        	box-sizing:border-box;
        }
	
</style>
</head>
<body>
	<%@include file="../common/menubar.jsp" %>
	
 	 <div class="outer">
        <br><br>
        <h1 align="center">일반게사판 상세보기 수정</h1>
        <br><br>
        <form action="<%=contextPath%>/update.bo" method="post" id="update-form" enctype ="multipart/form-data">
        <input type="hidden" name="boardNo" value="<%=b.getBoardNo()%>">
			<script>
				$(function(){
					//option에 있는 text와 조회해온 게시글 카데고리와 일치하는지 찾아내어 선택되어있게 작업하기
					$("#update-form option").each(function(){
						//현재 접근된 요소객체의 text와 조회해온 카테고리가 같다면
						if($(this).text()== "<%=b.getCategory()%>"){
							//해당 요소를 선택되어있게 만들기
							$(this).attr("selected",true);
						}
					});
				});
			</script>
			<table align="center">
				<tr>
					<th width="100">카테고리</th>
					<td width="500">
						<select name="category">
							<%for(Category c : list){ %>
								<option value="<%=c.getCategoryNo() %>"><%=c.getCategoryName()%></option>
							<%} %>
							
						</select>
					</td>
				</tr>
				
				<tr>
					<th>제목</th>
					<td><input type="text" name="title" value="<%=b.getBoardTitle()%>" required></td>
				</tr>
				<tr>
					<th>내용</th>
					<td>
						<textarea name="content" rows="10" cols="30" required style="resize:none;"><%=b.getBoardContent() %></textarea>
					</td>
				</tr>
				
				
				<tr>
	                <th>첨부파일<th>
					<td>
						<%if(at!=null){ %>
						<!-- 기존 첨부파일이 있었을 경우 수정할 때 첨부파일 정보를 보내야한다. -->
						<!-- 파일번호,변경된 파일명 전달하기 -->
						<%=at.getOriginName() %>
							<input type="hidden" name="fileNo" value="<%=at.getFileNo()%>">
							<input type="hidden" name="originFileName" value="<%=at.getChangeName()%>">
						<%} %>	
						<input type="file" name="reUpfile">
					</td>	
	            </tr>
				</table>
				<br>
				<div align="center">
					<button type="submit">수정완료</button>
					<button type="reset">초기화</button>
				</div>
        
        	</form>
       </div>
</body>
</html>