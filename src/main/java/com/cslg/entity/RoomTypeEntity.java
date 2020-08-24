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
@TableName("room_type")
public class RoomTypeEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 会议室类型表的唯一标识
	 */
	@TableId
	private Long roomTypeId;
	/**
	 * 会议室类型的名称
	 */
	private String roomTypeName;
	/**
	 * 分页查询
	 */
	@TableField(exist = false)
	private Integer page;
}
