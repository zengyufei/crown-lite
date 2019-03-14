/*
 * Copyright (c) 2018-2022 Caratacus, (caratacus@qq.com).
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package org.crown.framework.responses;

import org.crown.framework.model.ErrorCode;
import org.crown.framework.utils.ResponseUtils;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * GET: 200 OK
 * POST: 201 Created
 * PUT: 200 OK
 * PATCH: 200 OK
 * DELETE: 204 No Content
 * 接口返回(多态)
 * @author Caratacus
 */
public class ApiResponses<T> implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final String SUCCESS = "SUCCESS";
    private static final String ERROR = "ERROR";


    /**
     * 不需要返回结果
     * @param status
     */
    public static ApiResponses<Void> success(HttpServletResponse response, HttpStatus status) {
        response.setStatus(status.value());
        return SuccessResponses.<Void>builder().status(SUCCESS).code(status.value()).build();

    }

    /**
     * 成功返回
     * @param object
     */
    public static <T> ApiResponses<T> success(HttpServletResponse response, T object) {
        return success(response, HttpStatus.OK, object);

    }

    /**
     * 成功返回
     * @param status
     * @param object
     */
    public static <T> ApiResponses<T> success(HttpServletResponse response, HttpStatus status, T object) {
        response.setStatus(status.value());
        return SuccessResponses.<T>builder().status(SUCCESS).code(status.value()).data(object).build();

    }

    /**
     * 失败返回
     * @param errorCode
     * @param exception
     */
    public static <T> ApiResponses<T> failure(ErrorCode errorCode, Exception exception) {
        return ResponseUtils.exceptionMsg(FailedResponse.builder().status(ERROR).msg(errorCode.getMsg()), exception)
                .error(errorCode.getError())
                .show(errorCode.isShow())
                .time(LocalDateTime.now())
                .code(errorCode.getHttpCode())
                .build();
    }

}
