package com.cslg.entity;

import com.alibaba.excel.metadata.BaseRowModel;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.apache.poi.ss.usermodel.CellStyle;

import java.io.Serializable;
import java.util.Date;
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
@TableName("user_log")
public class UserLogEntity extends BaseRowModel implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 用户登录日志表的唯一标识
	 */
	@TableId
	private Long logId;
	/**
	 * 用户名（学号或者工号）
	 */
	private String userName;
	/**
	 * 登录日志描述
	 */
	private String logContent;
	/**
	 * 登录时间
	 */
	@TableField(fill = FieldFill.INSERT)
	private Date logTime;
	/**
	 * 登录的ip地址
	 */
	private String ipAddress;
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
