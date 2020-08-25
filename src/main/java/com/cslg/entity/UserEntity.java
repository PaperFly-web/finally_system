package com.cslg.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.apache.poi.ss.usermodel.CellStyle;

import javax.validation.constraints.Email;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author paperfly
 * @email 1430978392@qq.com
 * @date 2020-08-15 12:25:25
 */
@Data
@TableName("user")
public class UserEntity extends BaseRowModel implements Serializable {
	private static final long serialVersionUID = 1L;

	public UserEntity(){

	}
	/**
	 * 用户的唯一表示
	 */
	@TableId
	@ExcelProperty(value = "用户ID",index = 0)
	private Long userId;
	/**
	 * 学号或者工号
	 */
	@ExcelProperty(value = "用户名",index = 1)
	private String userName;
	/**
	 * 要使用MD5等加密密码，加密
	 */

	@ExcelProperty(value = "密码",index = 2)
	private String userPwd;
	/**
	 * 用户的真实姓名
	 */
	@ExcelProperty(value = "姓名",index = 3)
	private String userTrueName;
	/**
	 * 是哪个院系的，那个专业
	 */
	@ExcelProperty(value = "所在院系",index = 4)
	private String userDepartment;
	/**
	 * 用户的邮箱，方便后来管理
	 */
	@Email(message="邮箱不符合格式")
	@ExcelProperty(value = "邮箱",index = 5)
	private String userEmail;
	/**
	 * 学生毕业了，就不能再让他使用了
	 */
	@ExcelProperty(value = "用户是否可用",index = 6)
	private Integer userEnabled;
	/**
	 * 用户注册时间
	 */
	@TableField(fill = FieldFill.INSERT)
	@ExcelProperty(value = "注册时间",index = 7)
	private Date userRegisterTime;
	/**
	 * 用户角色
	 */
	@TableField(exist = false)
	private List<UserRoleEntity> roles;

	/**
	 * 修改密码时候用的
	 */
	@TableField(exist = false)
	private String userOldPwd;
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
