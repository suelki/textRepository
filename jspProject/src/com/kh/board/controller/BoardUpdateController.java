package com.kh.board.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

import com.kh.board.model.service.BoardService;
import com.kh.board.model.vo.Attachment;
import com.kh.board.model.vo.Board;
import com.kh.board.model.vo.Category;
import com.kh.common.MyFileRenamePolicy;
import com.oreilly.servlet.MultipartRequest;

/**
 * Servlet implementation class BoardUpdateController
 */
@WebServlet("/update.bo")
public class BoardUpdateController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BoardUpdateController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//받은 글번호로 해당 게시글 정보 조회해와서 수정페이지로 전달 (수정페이지 - 작성하기 페이지 이용하여 해보기)
		int boardNo = Integer.parseInt(request.getParameter("bno"));
		
		
		
		Board b = new BoardService().selectBoard(boardNo);
		Attachment at = new BoardService().selectAttachment(boardNo);
		ArrayList<Category> clist = new BoardService().categoryList();
		request.setAttribute("board", b);
		request.setAttribute("attachment", at);
		request.setAttribute("clist", clist);
		
		request.getRequestDispatcher("views/board/boardUpdateForm.jsp").forward(request, response);
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//수정처리
		//첨부파일 있고 없고 차이 생각해보기
		//있는경우 원래 있떤 첨부파일 데이터에서 수정해야함
		//없는경우 새롭게 추가해야함(지금 작성되어있는 게시글에 추가되기)
		//성공시 상세보기페이지 띄워주기
		//실패시 에러페이지
		request.setCharacterEncoding("UTF-8");
		
		if(ServletFileUpload.isMultipartContent(request)) {
			int maxSize = 10 *1024*1024;
			
			//저장시킬 서버 저장경로 찾기(물리적인 서버 폴더경로)
			String savePath = request.getSession().getServletContext().getRealPath("/resources/board_files");
			
			//파일명 수정 작업객체 추가하기
			MultipartRequest multiRequest = new MultipartRequest(request,savePath,maxSize,"UTF-8",new MyFileRenamePolicy());
			
			//수정작업에 필요한 기존 데이터 추출하기
			int boardNo = Integer.parseInt(multiRequest.getParameter("boardNo"));
			String category = multiRequest.getParameter("category");
			String title = multiRequest.getParameter("title");
			String content = multiRequest.getParameter("content");
			
			Board bo = new Board();
			bo.setBoardNo(boardNo);
			bo.setCategory(category);
			bo.setBoardTitle(title);
			bo.setBoardContent(content);
			
			Attachment at = null;
			//새롭게 전달된 첨부파일이 있다면 처리하기
			if(multiRequest.getOriginalFileName("reUpfile") != null) {
				at = new Attachment();
				
				at.setOriginName(multiRequest.getOriginalFileName("reUpfile"));
				at.setChangeName(multiRequest.getFilesystemName("reUpfile"));
				at.setFilePath("/resources/board_files");
				
				//기존에 첨부파일이 있었을 경우 (해당 데이터에서 수정작업을 해야한다)
				///form에서 hidden으로 파일번호와 변경된 이름 (서버에 저장된 이름)을 전달했기때문에
				//해당 정보가 있는지 없는지 판별해주면 된다.
				if(multiRequest.getParameter("fileNo") != null) {
					//새로운 첨부파일이 있고 기존에도 첨부파일이 있는경우
					//update attachment
					//기존 파일번호(식별자)를 이용하여 데이터 변경하기
					at.setFileNo(Integer.parseInt(multiRequest.getParameter("fileNo")));
					
					//기존에 첨부파일 삭제하기.
					new File(savePath+"/"+multiRequest.getParameter("originFileName")).delete();
				}else {//원래 첨부파일이 없었고 새롭게 들어온 경우
					//현재 게시글 번호 참조하게 INSERT 하기
					at.setRefBno(boardNo);
				}
			}
			
			int result = new BoardService().updateBoard(bo,at);
			//새로운 첨부파일 없고 기존 첨부파일도 없는경우- board update
			//새로운 첨부파일 있고 기존 첨부파일 없는 경우 - Board-update / Attachment-insert
			//새로운  첨부파일 있고 기존 첨부파일 있는 경우 - Board-update / Attachment-update
			
			if(result>0) {
				request.getSession().setAttribute("alertMsg", "게시글 수정 성공");
				response.sendRedirect(request.getContextPath()+"/detail.bo?bno="+boardNo);
			}else {
				request.setAttribute("errorMsg", "게시글 수정 실패");
				request.getRequestDispatcher("views/common/errorPage.jsp").forward(request, response);
			}
			
		}
		
		
		
		
	}

}
