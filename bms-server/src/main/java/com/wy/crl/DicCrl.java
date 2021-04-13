package com.wy.crl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wy.entity.Dic;
import com.wy.service.DicService;
import com.wy.utils.Result;

@RestController
@RequestMapping("dic")
public class DicCrl extends BaseCrl<Dic> {
	@Autowired
	private DicService dicService;

	@Override
	public DicService getService() {
		return dicService;
	}

	/**
	 * 根据父级编号获取父级和直接子级信息
	 * @return 父级和子级信息
	 */
	@GetMapping("/getPc/{dicId}")
	public Result getPc(@PathVariable Integer dicId) {
		return Result.result(dicService.getPc(dicId));
	}

	/**
	 * 根据父级唯一标识获取直接子级数据
	 * @param dicCode 父级标识
	 * @return 子级数据
	 */
	@GetMapping("/getChildren/{dicCode}")
	public Result getChildren(@PathVariable String dicCode) {
		return Result.resultOk(dicService.getChildren(dicCode));
	}

	/**
	 * 根据父级编号获得数据字典树形结构
	 * @param dicId 父级编号
	 * @return 树形结构
	 */
	@GetMapping("/getTrees/{dicId}")
	public Result getTrees(@PathVariable Integer dicId) {
		return Result.resultOk(dicService.getTrees(dicId));
	}
}