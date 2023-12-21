package com.example.memo.controller;

import com.example.memo.dto.MemoRequestDto;
import com.example.memo.dto.MemoResponseDto;
import com.example.memo.entity.Memo;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class MemoController {

    private final Map<Long, Memo> memoList = new HashMap<>();

    @PostMapping("/memos")
    public MemoResponseDto createMemo(@RequestBody MemoRequestDto requestDto) {
        Memo memo = new Memo(requestDto);

        Long maxId = memoList.size() > 0 ? Collections.max(memoList.keySet()) + 1 : 1;
        memo.setId(maxId);

        memoList.put(memo.getId(), memo);

        return new MemoResponseDto(memo);
    }

    @GetMapping("/memos")
    public List<MemoResponseDto> getMemos() {
        List<MemoResponseDto> responseDtoList = memoList.values().stream()
                .map(MemoResponseDto::new).toList();

        return responseDtoList;
    }

    @PutMapping("/memos/{id}")
    public Long updateMemo(@PathVariable Long id, @RequestBody MemoRequestDto requestDto) {
        if(memoList.containsKey(id)) {
            Memo memo = memoList.get(id);

            memo.update(requestDto);
            return memo.getId();
        } else {
            throw new IllegalArgumentException("해당 메모가 존재하지 않습니다.");
        }
    }

    @DeleteMapping("/memos/{id}")
    public String deleteMemo(@PathVariable Long id) {
        if(memoList.containsKey(id)) {
            memoList.remove(id);
            return id+"번 메모가 삭제되었습니다.";
        } else {
            throw new IllegalArgumentException("해당 메모가 존재하지 않습니다.");
        }
    }
}
