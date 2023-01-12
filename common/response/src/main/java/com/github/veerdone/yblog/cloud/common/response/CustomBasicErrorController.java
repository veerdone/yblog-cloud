/*
 * Copyright 2022
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.veerdone.yblog.cloud.common.response;

import com.github.veerdone.yblog.cloud.common.exception.ServiceExceptionEnum;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("${server.error.path:${error.path:/error}}")
@Primary
public class CustomBasicErrorController extends BasicErrorController {
    public CustomBasicErrorController(ServerProperties serverProperties) {
        super(new DefaultErrorAttributes(), serverProperties.getError());
    }

    @Override
    public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {
        HttpStatus status = super.getStatus(request);
        Map<String, Object> body = new HashMap<>(5);
        if (status.equals(HttpStatus.NOT_FOUND)) {
            ServiceExceptionEnum notFound = ServiceExceptionEnum.NOT_FOUND;
            body.put("code", notFound.getCode());
            body.put("msg", notFound.getMsg());
            body.put("err_code", notFound.getErrCode());
        } else {
            ServiceExceptionEnum unknownException = ServiceExceptionEnum.UNKNOWN_EXCEPTION;
            body.put("code", unknownException.getCode());
            body.put("msg", unknownException.getMsg());
            body.put("err_code", unknownException.getErrCode());
        }
        body.put("request_path", request.getRequestURI());
        return new ResponseEntity<>(body, HttpStatus.OK);
    }
}
