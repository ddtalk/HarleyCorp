package com.alibaba.dingtalk.openapi.springbootdemo.api;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;

@Api(description = "Simple Test Api")
@Slf4j
@RestController
@RequestMapping("api/v1/oa/simple")
public class SimpleApiRestController {

    @GetMapping("get-test/{param}")
    public ResponseEntity<String> getTest(@Valid @NotNull @PathVariable(name = "param") String param) {
        return ResponseEntity.ok(param);
    }

    @PostMapping("get-test/{param}")
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public void getTestAsPost(@Valid @NotNull @PathVariable(name = "param") String param) {
    }


    @PostMapping("post-test/{param}")
    public ResponseEntity<String> postTest(@Valid @NotNull @PathVariable(name = "param") String param) {
        return ResponseEntity.created(URI.create("http://localhost:8001/api/v1/oa/simple/get-test/param")).build();
    }

    @GetMapping("post-test/{param}")
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public void postTestAsGet(@Valid @NotNull @PathVariable(name = "param") String param) {
    }
}
