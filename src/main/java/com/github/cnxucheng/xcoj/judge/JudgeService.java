package com.github.cnxucheng.xcoj.judge;

import com.github.cnxucheng.xcoj.model.entity.Submission;

public interface JudgeService {

    void doJudge(Submission submission);
}
