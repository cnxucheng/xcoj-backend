package com.github.cnxucheng.xcoj.judge.sandbox;

import com.github.cnxucheng.xcoj.judge.model.JudgeRequest;
import com.github.cnxucheng.xcoj.judge.model.JudgeResponse;

public interface Sandbox {

    JudgeResponse judge(JudgeRequest request);
}
