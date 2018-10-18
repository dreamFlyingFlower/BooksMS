package com.wy.crl;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.wy.entity.BaseBean;
import com.wy.service.BaseService;
import com.wy.utils.MapUtils;
import com.wy.utils.Result;
import com.wy.utils.StrUtils;

/**
 * controller公共类
 * 
 * @author paradiseWy 2018年8月28日
 */
@SuppressWarnings("unchecked")
public abstract class BaseCrl<T> {

	public abstract BaseService<T> getService();

	@PostMapping("/save")
	public Result save(@RequestBody T t) {
		if (t instanceof BaseBean) {
			BaseBean<T> bean = (BaseBean<T>) t;
			if(bean.validAdd()) {				
				// 参数校验
				Map<String,String> checkAdd = bean.validAdd(t);
				if(MapUtils.isNotBlank(checkAdd)) {
					if (StrUtils.isNotBlank(checkAdd.get("checkErr"))) {
						return Result.resultErr(checkAdd.get("checkErr"));
					}
					// 是否排序
					if (StrUtils.isNotBlank(checkAdd.get("sort"))) {
						// 判断是否有自定义排序字段
						return Result.resultOk(getService().addSort(t, checkAdd.get("sort")));
					}
				}
			}
		}
		return Result.result(getService().add(t));
	}

	/**
	 * 批量添加必须在前端对数据进行检测
	 * @param ts 数据源
	 * @return 添加后回显的数据
	 */
	@PostMapping("/saves")
	public Result saves(@RequestBody List<T> ts) {
		return Result.result(getService().adds(ts));
	}

	/**
	 * 主键类型为数字类型删除
	 * 
	 * @param id 主键编号
	 */
	@GetMapping("/remove/{id}")
	public Result remove(@PathVariable Long id) {
		return Result.result(getService().delete(id));
	}

	/**
	 * 主键类型为字符串时删除
	 * 
	 * @param id 主键编号
	 */
	@GetMapping("/removeName/{id}")
	public Result removeName(@PathVariable String id) {
		return Result.result(getService().delete(id));
	}

	/**
	 * 主键类型为数字类型时批量删除
	 * 
	 * @param ids 主键编号集合
	 */
	@GetMapping("/removes/{ids}")
	public Result removes(@PathVariable List<Integer> ids) {
		return Result.result(getService().deletes(ids));
	}

	/**
	 * 主键类型为字符串类型时批量删除
	 * 
	 * @param ids 主键编号集合
	 */
	@GetMapping("/removeNames/{ids}")
	public Result removeNames(@PathVariable List<String> ids) {
		return Result.result(getService().deletes(ids));
	}

	@PostMapping("/modify")
	public Result modify(@RequestBody T t) {
		if (t instanceof BaseBean) {
			BaseBean<T> bean = (BaseBean<T>) t;
			if(bean.validUpdate()) {				
				String error = bean.validUpdate(t);
				if (StrUtils.isNotBlank(error)) {
					return Result.resultErr(error);
				}
			}
		}
		return Result.result(getService().update(t));
	}

	@PostMapping("/modifys")
	public Result modifys(@RequestBody List<T> ts) {
		return Result.result(getService().updates(ts));
	}

	/**
	 * 主键为数字类型时
	 * 
	 * @param id 主键编号
	 */
	@GetMapping("/getById/{id}")
	public Result getById(@PathVariable Long id) {
		return Result.result(getService().getById(id));
	}

	/**
	 * 主键类型为字符串时
	 * 
	 * @param id 主键编号
	 */
	@GetMapping("/getByName/{id}")
	public Result getByName(@PathVariable String id) {
		return Result.result(getService().getById(id));
	}

	/**
	 * 无参数,若需要分页,则需要传pageNumber和pageSize
	 */
	@GetMapping("/getList")
	public Result getList(BaseBean<T> bean) {
		return getService().getList(bean);
	}

	/**
	 * 有参数,根据实体类条件获得多条数据.若需要分页,则需要传pageNumber和pageSize
	 */
	@PostMapping("/getListArgs")
	public Result getListArgs(@RequestBody BaseBean<T> bean) {
		return getService().getListArgs(bean);
	}
}