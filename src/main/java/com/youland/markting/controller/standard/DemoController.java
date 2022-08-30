package com.youland.markting.controller.standard;


import com.youland.markting.controller.standard.request.DemoRequest;
import com.youland.markting.controller.standard.response.DemoResponse;
import com.youland.markting.service.DemoFacade;
import com.youland.markting.config.base.WebTemplate;
import com.youland.markting.config.base.callback.ExtCallBack;
import com.youland.markting.config.base.dataobj.CommonResult;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("demo")
@RequiredArgsConstructor
public class DemoController {

    private final DemoFacade testFacade;

    @GetMapping("/test")
    @ResponseBody
    public DemoResponse test(@PathVariable String procInstId) {
        return WebTemplate.execute(procInstId, new ExtCallBack<String, DemoResponse>() {
            @Override
            public CommonResult<DemoResponse> initResult() {
                return new CommonResult<>(true);
            }

            @Override
            public void call(String request, CommonResult<DemoResponse> result) {
                testFacade.test(new DemoRequest("123"));
            }
        });
    }
}
