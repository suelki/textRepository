package com.kh.notice.model.service;

import java.sql.Connection;
import java.util.ArrayList;

import com.kh.common.JDBCTemplate;
import com.kh.notice.model.dao.NoticeDao;
import com.kh.notice.model.vo.Notice;

public class NoticeService {

	public ArrayList<Notice> selectList() {
		Connection conn = JDBCTemplate.getConnection();
		
		ArrayList<Notice> list = new NoticeDao().selectList(conn);
		
		JDBCTemplate.close(conn);
		return list;
	}

	public int insertNotice(Notice n) {
		Connection conn = JDBCTemplate.getConnection();
		
		 int result= new NoticeDao().insertNotice(conn,n);
		 
		 if(result>0) {
			 JDBCTemplate.commit(conn);
		 }else {
			 JDBCTemplate.rollback(conn);
		 }
		 
		 JDBCTemplate.close(conn);
		 
		 return result;
		
	}

	public Notice selectNotice(int noticeNo) {
		Connection conn = JDBCTemplate.getConnection();
		
		Notice n = new NoticeDao().selectNotice(conn,noticeNo);
		
		JDBCTemplate.close(conn);
		return n;
		
	}

	public int increaseCount(int noticeNo) {
		Connection conn = JDBCTemplate.getConnection();
		
		int result = new NoticeDao().increaseCount(conn,noticeNo);
		
		if(result>0) {
			JDBCTemplate.commit(conn);
		}else {
			JDBCTemplate.rollback(conn);
		}
		JDBCTemplate.close(conn);
		 
		 return result;
	}

	public int updateNotice( Notice no) {
		Connection conn = JDBCTemplate.getConnection();
		
		int result= new NoticeDao().updateNotice(conn,no);
		
		if(result>0) {
			JDBCTemplate.commit(conn);
		}else {
			JDBCTemplate.rollback(conn);
		}
		
		JDBCTemplate.close(conn);
		
		return result;
	}

	public int deleteNotice(int noticeNo) {
		Connection conn = JDBCTemplate.getConnection();
		
		int result = new NoticeDao().deleteNotice(conn,noticeNo);
		
		if(result>0) {
			JDBCTemplate.commit(conn);
		}else {
			JDBCTemplate.rollback(conn);
		}
		
		JDBCTemplate.close(conn);
		return result;
	}

	

}
