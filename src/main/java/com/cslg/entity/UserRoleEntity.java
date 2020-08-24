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
@TableName("user_role")
public class UserRoleEntity implements Serializable {
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
}
