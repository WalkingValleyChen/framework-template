package com.chen.controller;



import com.chen.enums.ResponseCodeEnum;
import com.chen.exception.BaseException;
import com.chen.response.BaseResponse;
import com.chen.utils.LoggerUtil;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * spring controller基类
 * 定义了异常拦截栈，业务方法中异常最终会被此类捕获，给用户更好的错误提示
 */
@Controller("baseController")
public abstract class BaseController {



	private MappingJackson2JsonView jsonView = new MappingJackson2JsonView();

	@ModelAttribute
	public void globalBaseModel(Model model, HttpServletRequest request, HttpServletResponse response) {
		model.addAttribute("ctx", request.getContextPath());



		//回写请求参数，方便写表单
		Map<String, String[]> parameterMap = request.getParameterMap();
		for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
			if (entry.getValue().length == 1)
				model.addAttribute(entry.getKey(), entry.getValue()[0]);
			else
				model.addAttribute(entry.getKey(), entry.getValue());
		}


	}




	@ExceptionHandler(BindException.class)
	@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
	@ResponseBody
	public ModelAndView handException(HttpServletRequest request, BindException e) {
		Map<String, String> errorMap = new HashMap<String, String>();
		BindingResult bindingResult = e.getBindingResult();
		List<FieldError> fieldErrors = bindingResult.getFieldErrors();
		for (FieldError fe : fieldErrors) {
			errorMap.put(fe.getField(), fe.getDefaultMessage());
		}
		return getModelAndView(request, "500", new BaseResponse(ResponseCodeEnum.ERROR.getCode(), "请求参数异常", e).toMap());
	}


	@ExceptionHandler(BaseException.class)
	@ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
	@ResponseBody
	public ModelAndView handBaseException(HttpServletRequest request, BaseException e) {
		LoggerUtil.logException(e);
		return getModelAndView(request, "503", new BaseResponse(ResponseCodeEnum.ERROR.getCode(), e.getMessage(), e).toMap());
	}

	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
	@ResponseBody
	public ModelAndView handException(HttpServletRequest request, Exception ex) {
		LoggerUtil.logException(ex);
		return getModelAndView(request, "500", new BaseResponse(ResponseCodeEnum.ERROR.getCode(), "您访问的服务异常\n" + ex.getMessage(), ex).toMap());
	}

	/**
	 * 判断ajax请求
	 *
	 * @param request
	 * @return
	 */
	boolean isAjax(HttpServletRequest request) {
		return (request.getHeader("X-Requested-With") != null && "XMLHttpRequest".equals(request.getHeader("X-Requested-With").toString()));
	}


	private ModelAndView getModelAndView(HttpServletRequest request, String view, Map<String, Object> value) {
		ModelAndView modelAndView;//json返回
		if (isAjax(request)) {
			modelAndView = new ModelAndView(jsonView);
		} else {
			modelAndView = new ModelAndView(view);
		}
		modelAndView.addObject("ctx", request.getContextPath());
		modelAndView.addAllObjects(value);
		return modelAndView;
	}


}