package com.wy.filters;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.wy.common.ResultException;
import com.wy.enums.TipsEnum;
import com.wy.utils.Result;

/**
 * 统一异常处理
 * 
 * @author wanyang
 */
@RestControllerAdvice
public class ExceptionFilter {

	private Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 必传参数为空异常
	 */
	@ExceptionHandler(MissingServletRequestParameterException.class)
	public Result missingServletRequestParameterException(MissingServletRequestParameterException e) {
		return Result.resultErr(-500, TipsEnum.TIP_PARAM_EMPTY.getTips(e.getParameterName()));
	}

	@ExceptionHandler(value = BindException.class)
	public Result bindException(BindException e) {
		StringBuilder sb = new StringBuilder();
		// 解析原错误信息，封装后返回，此处返回非法的字段名称，原始值，错误信息
		for (FieldError error : e.getFieldErrors()) {
			sb.append(error.getDefaultMessage() + ";");
		}
		return Result.resultErr(-500, sb.toString());
	}

	/**
	 * 参数未通过验证异常
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Result methodArgumentNotValidHandler(MethodArgumentNotValidException e) {
		StringBuilder sb = new StringBuilder();
		// 解析原错误信息,封装后返回,此处返回非法的字段名称，原始值，错误信息
		for (FieldError error : e.getBindingResult().getFieldErrors()) {
			sb.append("字段：" + error.getField() + "-" + error.getRejectedValue() + ";");
		}
		return Result.resultErr(-500, sb.toString());
	}

	/**
	 * 无法解析参数异常
	 */
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public Result httpMessageNotReadableHandler(HttpServletRequest request,
			HttpMessageNotReadableException exception) {
		return Result.resultErr(-500, "参数无法正常解析");
	}

	/**
	 * 实体类字段序列化异常
	 */
	@ExceptionHandler(ConstraintViolationException.class)
	public Result constraintViolationException(HttpServletRequest request, ConstraintViolationException e) {
		logger.info(request.getRequestURL().toString(), e.getMessage());
		return Result.resultErr(-500, e.getMessage());
	}

	/*-----------------需要springboot的spring-boot-starter-security包 -----------------*/
	 /**
	 * 权限访问异常,
	 */
//	 @ExceptionHandler(value = AuthenticationException.class)
//	 public Object AuthenticationExceptionHandler(AuthenticationException e) {
//	 return Result.resultErr(-500, "没有访问权限");
//	 }
	
	 /**
	 * 方法访问权限不足异常
	 */
//	 @ExceptionHandler(AccessDeniedException.class)
//	 public Object AccessDeniedExceptionHandler(AccessDeniedException exception)
//	 throws Exception {
//	 return Result.resultErr(-500, "没有访问权限");
//	 }
	
	 /**
	 * 非正常的权限访问异常
	 */
//	 @ExceptionHandler(value = BadCredentialsException.class)
//	 public Object BadCredentialsExceptionHandler(BadCredentialsException e)
//	 throws Exception {
//	 return Result.resultErr(-500, "请求验证异常");
//	 }
	/*-----------------需要springboot的spring-boot-starter-security包 -----------------*/

	/**
	 * 数据库字段异常
	 */
	@ExceptionHandler(value = DuplicateKeyException.class)
	public Object DuplicateKeyExceptionHandler(DuplicateKeyException e) throws Exception {
		return Result.resultErr(-500, "数据库异常");
	}

	/**
	 * 参数不合法异常
	 */
	@ExceptionHandler(IllegalArgumentException.class)
	public Result illegalArgumentException(HttpServletRequest request, IllegalArgumentException e) {
		logger.info(request.getRequestURL().toString(), e.getMessage());
		return Result.resultErr(-501, e.getMessage());
	}

	/**
	 * 接口不存在异常
	 */
	@ExceptionHandler(NoHandlerFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public Result noHandlerFoundException(NoHandlerFoundException e) {
		return Result.resultErr(-404, e.getMessage());
	}

	/**
	 * http请求方式不支持异常
	 */
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public Result httpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
		return Result.resultErr(-500, e.getMessage());
	}

	/**
	 * 自定义异常
	 */
	@ExceptionHandler(ResultException.class)
	public Result resultException(HttpServletRequest request, ResultException e) {
		logger.info(request.getRequestURL().toString(), e.getMessage());
		return Result.resultErr(-500, e.getMessage());
	}

	@ExceptionHandler(value = Exception.class)
	public Result handleException(HttpServletRequest request, Exception e) {
		logger.error(request.getRemoteHost(), request.getRequestURL(), e);
		return Result.resultErr(-500, e.getMessage());
	}
}