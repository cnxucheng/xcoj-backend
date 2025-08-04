package com.github.cnxucheng.xcoj.judge.sandbox;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.github.cnxucheng.xcoj.common.ErrorCode;
import com.github.cnxucheng.xcoj.exception.BusinessException;
import com.github.cnxucheng.xcoj.judge.model.JudgeRequest;
import com.github.cnxucheng.xcoj.judge.model.JudgeResponse;
import org.springframework.stereotype.Component;

@Component
public class SystemSandbox implements Sandbox {

    @Override
    public JudgeResponse judge(JudgeRequest request) {
        String url = "http://192.168.1.110:8001/run";
        String requestString = JSONUtil.toJsonStr(request);
        String responseStr = HttpUtil.createPost(url)
                .header("auth", "xcoj-system-auth-secret")
                .body(requestString)
                .execute()
                .body();
        if (StringUtils.isBlank(responseStr)) {
            throw new BusinessException(ErrorCode.API_ERROR, "executeCode remoteSandbox error, message = " + responseStr);
        }
        return JSONUtil.toBean(responseStr, JudgeResponse.class);
    }
}
