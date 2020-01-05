package com.library.library.config;

import com.library.library.entity.Result;
import org.apache.tomcat.util.buf.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;


/**
 * 
 * @Description:API接口异常拦截
 * 
 */
@RestControllerAdvice
public class ExceptionControllerAdvice {

	private static final Logger LOG = LoggerFactory.getLogger(ExceptionControllerAdvice.class);

	/**
	 * 拦截Exception异常统一处理遵循restful
	 * @param ex
	 * @param req
	 * @return
	 */
	@ExceptionHandler(value = { Exception.class, RuntimeException.class})
	@ResponseStatus(code = HttpStatus.OK)
	public Result<?> errorHandler(Exception ex, ServletWebRequest req) {
		Map<String, String[]> map = req.getParameterMap();
		StringBuilder reqContext = new StringBuilder(req.getRequest().getRequestURI());
		map.forEach((k, v) -> {
			reqContext.append(" ").append(k).append(":").append(StringUtils.join(v));
		});
		LOG.error(reqContext.toString(), ex);
		return Result.getFailure(ex.getMessage());
	}

	/**
	 * 处理参数异常
	 *
	 * @param response
	 * @return
	 */
	@ExceptionHandler(value = { MethodArgumentNotValidException.class, MissingServletRequestParameterException.class ,HttpMessageNotReadableException.class})
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	public Result<?> handleValidationBodyException(HttpServletResponse response) {
		return Result.getFailure("请求参数错误");
	}

	/**
	 * 请求方式异常
	 * @param response
	 * @return
	 */
	@ExceptionHandler(value = { HttpRequestMethodNotSupportedException.class })
	@ResponseStatus(code = HttpStatus.METHOD_NOT_ALLOWED)
	public Result<?> handleValidationMethodException(HttpServletResponse response) {
		return Result.getFailure("请求方式错误");
	}
	
	/**
	 * 404
	 * @param response
	 * @return
	 */
	@ExceptionHandler(NoHandlerFoundException.class)
	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	public Result<?> notFoundPage404(HttpServletResponse response) {
		return Result.getFailure("请求路径错误");
	}

}
