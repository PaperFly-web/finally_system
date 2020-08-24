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
@TableName("room")
public class RoomEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 会议室的唯一标识
	 */
	@TableId
	private Long roomId;
	/**
	 * 会议室的名字
	 */
	private String roomName;
	/**
	 * 会议室容纳的人数
	 */
	private Integer roomSeatNumber;
	/**
	 * 会议室的类型
	 */
	private Long roomType;
	/**
	 * 会议室的描述
	 */
	private String roomDesc;
	/**
	 * 会议室是否可用
	 */
	private Integer roomEnabled;
	/**
	 * 会议室的图片
	 */
	private String roomImg;
	/**
	 * 会议室的位置
	 */
	private String roomLocation;

	/**
	 * 会议室类型名称
	 */
	private String roomTypeName;
	/**
	 * 分页查询
	 */
	@TableField(exist = false)
	private Integer page;
}
