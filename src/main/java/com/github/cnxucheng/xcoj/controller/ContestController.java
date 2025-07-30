package com.github.cnxucheng.xcoj.controller;

import com.github.cnxucheng.xcoj.common.MyPage;
import com.github.cnxucheng.xcoj.common.PageRequest;
import com.github.cnxucheng.xcoj.common.Result;
import com.github.cnxucheng.xcoj.model.dto.submision.SubmissionSubmitDTO;
import com.github.cnxucheng.xcoj.model.vo.ProblemVO;
import com.github.cnxucheng.xcoj.model.vo.ContestVO;
import com.github.cnxucheng.xcoj.model.vo.ContestSampleVO;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/contest")
public class ContestController {

    /**
     * 获取比赛详细信息
     */
    @GetMapping("/")
    public Result<ContestVO> getDetail(@RequestParam int id) {
        return null;
    }

    /**
     * 分页获取比赛
     */
    @PostMapping("/page")
    public Result<MyPage<ContestSampleVO>> getPage(@RequestBody PageRequest page) {
        return null;
    }

    /**
     * 获取比赛题目
     */
    @GetMapping("/problem/{index}")
    public Result<ProblemVO> getProblem(@PathVariable int index) {
        return null;
    }

    /**
     * 提交题目
     */
    @PostMapping("/problem/{index}/submit")
    public Result<?> submitProblem(@PathVariable int index, @RequestBody SubmissionSubmitDTO submitDTO) {
        return null;
    }
}
