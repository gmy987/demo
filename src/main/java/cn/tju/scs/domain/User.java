package cn.tju.scs.domain;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author daisygao
 * @since 2016-12-19
 */
public class User extends Model<User> {

    private static final long serialVersionUID = 1L;

	/**
	 * 主键ID
	 */
	@TableId(type = IdType.AUTO)
	@TableField(value="test_id")
	private Long testId;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 年龄
	 */
	private Integer age;

	/**
	 * 测试下划线字段命名类型
	 */
	@TableField(value="test_type")
	private Integer testType;

	/**
	 * 
	 */
	private Long role;

	/**
	 * 
	 */
	private String phone;



	public Long getTestId() {
		return testId;
	}

	public void setTestId(Long testId) {
		this.testId = testId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Integer getTestType() {
		return testType;
	}

	public void setTestType(Integer testType) {
		this.testType = testType;
	}

	public Long getRole() {
		return role;
	}

	public void setRole(Long role) {
		this.role = role;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Override
	protected Serializable pkVal() {
		return testId;
	}
}
