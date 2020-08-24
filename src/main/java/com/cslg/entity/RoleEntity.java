package com.cslg.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 
 * 
 * @author paperfly
 * @email 1430978392@qq.com
 * @date 2020-08-15 12:25:25
 */
@Data
@TableName("role")
public class RoleEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 角色表的唯一表示
	 */
	@TableId
	private Long roleId;
	/**
	 * 角色的名称
	 */
	private String roleName;

	/**
	 * 分页查询
	 */
	@TableField(exist = false)
	private Integer page;
}
