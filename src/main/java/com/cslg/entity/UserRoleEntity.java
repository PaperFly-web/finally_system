package com.cslg.entity;

import com.alibaba.excel.metadata.BaseRowModel;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.apache.poi.ss.usermodel.CellStyle;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * 
 * @author paperfly
 * @email 1430978392@qq.com
 * @date 2020-08-15 12:25:25
 */
@Data
@TableName("user_role")
public class UserRoleEntity extends BaseRowModel implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 用户名（学号或者工号）
	 */
	private String userName;
	/**
	 * 用户的角色名称
	 */
	private String roleName;
	/**
	 * 用户角色表的唯一标识
	 */
	@TableId
	private Long userRoleId;
	/**
	 * 分页查询
	 */
	@TableField(exist = false)
	private Integer page;

	/**
	 * 去除继承的类在mybatisplus中的影响
	 */
	@TableField(exist = false)
	private Map<Integer, CellStyle> cellStyleMap = new HashMap<Integer,CellStyle>();
}
