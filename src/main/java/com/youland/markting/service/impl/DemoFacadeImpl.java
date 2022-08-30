package com.youland.markting.service.impl;

import com.youland.markting.controller.standard.request.DemoRequest;
import com.youland.markting.controller.standard.response.DemoResponse;
import org.springframework.stereotype.Service;

import com.youland.markting.service.DemoFacade;
import com.youland.markting.config.base.BizTemplate;
import com.youland.markting.config.base.callback.ExeCallBack;
import com.youland.markting.config.base.dataobj.CommonResult;
import com.youland.markting.config.base.enums.OperateCode;

@Service
public class DemoFacadeImpl implements DemoFacade {

    @Override
    public CommonResult<DemoResponse> test(DemoRequest user) {
        user.setOperateCode(OperateCode.DEFAULT);
        return BizTemplate.execute(user, new ExeCallBack<DemoRequest, DemoResponse>() {
            @Override
            public void validate(DemoRequest request) {

            }

            @Override
            public void execute(DemoRequest request, CommonResult<DemoResponse> result) {

            }

            @Override
            public void postProcess(DemoRequest Request, CommonResult<DemoResponse> result) {

            }
        });
    }
}
