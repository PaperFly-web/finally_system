package com.cslg.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.apache.poi.ss.usermodel.CellStyle;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
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
@TableName("room_order")
public class RoomOrderEntity extends BaseRowModel implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 申请会议室的唯一标识
	 */
	@TableId
	@ExcelProperty(value = "订单号",index = 0)
	private Long roomOrderId;
	/**
	 * 会议室的唯一标识（也就是你你申请哪个会议室）
	 */
	@ExcelProperty(value = "房间ID",index = 1)
	private Long roomId;
	/**
	 * 哪个用户申请的（学号或者工号）
	 */
	@NotNull
	@ExcelProperty(value = "用户名",index = 2)
	private String userName;

	/**
	 *开始和结束时间的字符串描述
	 */
	@ExcelProperty(value = "时间使用",index = 3)
	private String startEndTime;
	/**
	 * 一个会议室一天有4个时段可以预约
	 * 1->9:00->12:00
	 * 2->13:00->15:00
	 * 3->15:30->17:30
	 * 4->19:30->22:00
	 */
	@Range(min = 1,max = 4,message = "预定会议室的时间段只有1,2,3,4")
	private Integer  timeSlot;

	/**
	 * 描述预约的会议室是哪天，只精确到天
	 */
	private Date day;
	/**
	 * 要用会议室做什么活动的描述
	 */
	@ExcelProperty(value = "活动",index = 6)
	private String activity;
	/**
	 * 申请状态0代表不通过，1代表通过
	 */
	@Range(min = 0,max = 1,message = "状态只有,0:不通过,1:通过")
	@ExcelProperty(value = "申请状态",index = 7)
	private Integer status;
	/**
	 * 申请的密码，由管理员填写
	 */
	private String pwd;
	/**
	 * 拒绝申请的理由
	 */
	@ExcelProperty(value = "决绝申请的理由",index = 8)
	private String rejectReason;
	/**
	 * 申请的时间
	 */
	@TableField(fill = FieldFill.INSERT)
	@ExcelProperty(value = "申请时间",index = 9)
	private Date orderTime;

	/**
	 * 处理的申请的时间
	 */
	@TableField(fill = FieldFill.UPDATE)
	@ExcelProperty(value = "处理申请时间",index = 10)
	private Date authenticTime;

	/**
	 * 在距离使用会议室多长时间不能修改会议室的申请
	 */
	private Integer overTime;

	/**
	 * 使用会议室的人数
	 */
	@ExcelProperty(value = "使用会议室的人数",index = 11)
	@Min(value = 1,message = "会议室最低使用人数为1")
	private Integer peopleNum;
	/**
	 * 会议室的相关信息
	 */
	@TableField(exist = false)
	private RoomEntity room;
	/**
	 * 会议室开始使用的时间
	 */
	private Long startTime;

	/**
	 * 会议室结束使用的时间
	 */
	private Long endTime;
	/**
	 * 分页查询
	 */
	@TableField(exist = false)
	private Integer page;

	/**
	 * 每页数量
	 */
	@TableField(exist = false)
	private Integer pageSize;

	/**
	 * 去除继承的类在mybatisplus中的影响
	 */
	@TableField(exist = false)
	private Map<Integer, CellStyle> cellStyleMap = new HashMap<Integer,CellStyle>();
}
