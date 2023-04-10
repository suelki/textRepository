package com.kh.member.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kh.member.model.service.MemberService;
import com.kh.member.model.vo.Member;

/**
 * Servlet implementation class UpdatepwdController
 */
@WebServlet("/updatePwd.me")
public class UpdatepwdController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdatepwdController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		String updatePwd = request.getParameter("updatePwd");
		//session에 담겨있는 loginUser에서 id값 꺼내기
		HttpSession session = request.getSession();
		Member m = (Member)session.getAttribute("loginUser");
		String userId = m.getUserId();
		//String userId = ((Member)session.getAttribute("loginUser")).getUserId(); 
		
//		String userId2 =request.getParameter("userId");
//		System.out.println(userId2);
		
		Member updateMem = new MemberService().updatePwd(updatePwd,userId);
		
		if(updateMem == null) {
			request.setAttribute("errorMsg","비밀번호 수정 실패");
			request.getRequestDispatcher("views/common/errorPage.jsp").forward(request, response);
		}else {
			session.setAttribute("alertMsg","비밀번호 변경 완료. 변경된 비밀번호로 재로그인 해주세요");
			session.setAttribute("loginUser", null);
			response.sendRedirect(request.getContextPath());
			
		}
		}
		
	}


