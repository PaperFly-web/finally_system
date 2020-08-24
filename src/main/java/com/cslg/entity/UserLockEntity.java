package com.cslg.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 这个表主要是防止用户频繁登陆错误  频繁获取验证码等，超过限制就锁定一段时间
 * 
 * @author paperfly
 * @email 1430978392@qq.com
 * @date 2020-08-15 12:25:25
 */
@Data
@TableName("user_lock")
public class UserLockEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 锁定用户表的唯一标识
	 */
	@TableId
	private Long lockId;
	/**
	 * 用户名（学号或者工号）
	 */
	private String userName;
	/**
	 * 锁定的开始时间
	 */
	private Date lockStartTime;
	/**
	 * 锁定的结束时间
	 */
	private Date lockEndTime;
	/**
	 * 分页查询
	 */
	@TableField(exist = false)
	private Integer page;
}
