package com.github.cnxucheng.xcoj.judge.sandbox;

import com.github.cnxucheng.xcoj.model.dto.judge.JudgeRequest;
import com.github.cnxucheng.xcoj.model.vo.JudgeResponse;

public interface Sandbox {

    JudgeResponse judge(JudgeRequest request);
}
