package com.springbook.view.board;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.springbook.biz.board.BoardService;
import com.springbook.biz.board.BoardVO;

@Controller
@SessionAttributes("board")
public class BoardController {
	
	@Autowired
	private BoardService boardService;

	@RequestMapping(value="/insertBoard.do")
	public String insertBoard(BoardVO vo) {
		boardService.insertBoard(vo);

		return "getBoardList.do";
	}

	@RequestMapping("/updateBoard.do")
	public String updateBoard(@ModelAttribute("board") BoardVO vo) {

		System.out.println("번호: " +  vo.getWriter());
		System.out.println("제목: " +  vo.getTitle());
		System.out.println("작성자: " +  vo.getWriter());
		System.out.println("내용: " +  vo.getContent());
		System.out.println("등록일: " +  vo.getRegDate());
		System.out.println("조회수: " +  vo.getCnt());
		boardService.updateBoard(vo);

		return "getBoardList.do";
	}

	@RequestMapping("/deleteBoard.do")
	public String deleteBoard(BoardVO vo) {

		boardService.deleteBoard(vo);

		return "getBoardList.do";
	}

	@RequestMapping("/getBoard.do")
	public String getBoard(BoardVO vo, Model model) {

		model.addAttribute("board", boardService.getBoard(vo));

		return "getBoard.jsp";
	}
	
	// 검색 조건 목록 설정
	//action="getBoardList.do"
	@ModelAttribute("conditionMap")
	public Map<String, String> searchConditionMap() {
		Map<String, String> conditionMap = new HashMap<String, String>();

		conditionMap.put("제목", "TITLE");
		conditionMap.put("내용", "CONTENT");

		return conditionMap;
	}

	@RequestMapping("/getBoardList.do")
	public String getBoardList(
			@RequestParam(value="searchCondition", defaultValue="TITLE", required=false) String condition,
			@RequestParam(value="searchKeyword", defaultValue="", required=false) String keyword,
			BoardVO vo, Model model) {

		System.out.println("검색 조건: " + condition);
		System.out.println("검색 단어: " + keyword);

		model.addAttribute("boardList", boardService.getBoardList(vo));

		return "getBoardList.jsp";
	}
}
