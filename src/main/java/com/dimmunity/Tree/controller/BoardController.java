package com.dimmunity.Tree.controller;

import com.dimmunity.Tree.dto.BoardDTO;
import com.dimmunity.Tree.dto.BookDTO;
import com.dimmunity.Tree.entity.BoardEntity;
import com.dimmunity.Tree.service.BoardService;
import com.dimmunity.Tree.service.BookService;
import lombok.RequiredArgsConstructor;
import org.hibernate.query.criteria.JpaCriteriaUpdate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board") // /board가 상위 주소인데, 계속 부를테니까 아예 구분하여 선언해놓음
public class BoardController {

    private final BoardService boardService;
    private BookService bookService;

    @GetMapping("/bookSave")
    public String saveForm(){
        return "bookSave";
    }

    //post로 보냈기 때문에 postmapping 사용
    @PostMapping("/bookSave")
    public String save(@ModelAttribute BoardDTO boardDTO){
        boardService.save(boardDTO); // board bookSave 완료
        return "index";
    }

    // 게시글 목록
    // 나중에 프론트랑 연결할때 / 고려해야하는지 고민
//    @GetMapping("/")
//    public String findAll(Model model) {
//        // DB에서 전체 게시글 데이터를 가져와서 phraseList.html에 보여준다.
//        List<BoardDTO> boardDTOList = boardService.findAll();
//        model.addAttribute("boardList",boardDTOList);
//        return "phraseList";
//    }

    // 게시글 상세 조회
    @GetMapping("/{id}")
    public String findById(@PathVariable("id") Long id, Model model,
                           @PageableDefault(page=1) Pageable pageable){
        // 해당 게시글의 조회수를 하나 올리고, 게시글 데이터를 가져와서 detail.html에 출력
        // 두번의 메소드 호출 이뤄짐
        boardService.updateHits(id);
        BoardDTO boardDTO = boardService.findById(id);

        model.addAttribute("board", boardDTO);
        model.addAttribute("page", pageable.getPageNumber());

        return "boardDetail";
    }

    // 게시글 수정
    @GetMapping("/phraseUpdate/{id}")
    public String updateForm(@PathVariable("id") Long id, Model model){
        BoardDTO boardDTO = boardService.findById(id);
        model.addAttribute("boardUpdate",boardDTO);
        return "phraseUpdate";
    }

    @PostMapping("/phraseUpdate")
    public String update(@ModelAttribute BoardDTO boardDTO, Model model) {
        BoardDTO board = boardService.update(boardDTO);
        model.addAttribute("board", board);
        return "redirect:/board/" + boardDTO.getId();
    }

    // 게시글 삭제
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id){
        boardService.delete(id);
        return "redirect:/board/";
    }

    // 페이징 처리
    @GetMapping("/paging")
    public String paging(@PageableDefault(page=1) Pageable pageable, Model model){
//        pageable.getPageNumber();
        // 페이지 값을 가져오기 위함
        Page<BoardDTO> boardList = boardService.paging(pageable);

        // page 가 몇개까지 보여지게 될건지 설정 -> 우리는 전페이지, 현재페이지, 이후페이지 (이렇게 3페이지만 보여지게 될것)
        int blockLimit = 3;
        int startPage = (((int)(Math.ceil((double)pageable.getPageNumber() / blockLimit))) - 1) * blockLimit + 1; // 1 4 7 10 ~ 이렇게 계산되어 나올것
        int endPage = ((startPage + blockLimit -1) < boardList.getTotalPages()) ? startPage + blockLimit -1 : boardList.getTotalPages();

        model.addAttribute("boardList", boardList);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "boardPaging";
    }

}

