package com.chengjia.commons.common;

public class Enums {

	/**
	 * 判断枚举
	 * 
	 * @author Administrator
	 *
	 */
	public static enum IfEnum {
		Y("是"), N("否");

		private final String value;

		private IfEnum(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}

		@Override
		public String toString() {
			return value;
		}
	}

	/**
	 * 性别
	 * 
	 * @author Administrator
	 *
	 */
	public static enum SexEnum {
		MAN("男"), WOMEN("女");

		private final String value;

		private SexEnum(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}

		@Override
		public String toString() {
			return value;
		}
	}

	/**
	 * 申请状态
	 * 
	 * @author Administrator
	 *
	 */
	public static enum IfApplyStatusEnum {
		Y("已申请"), N("未申请");

		private final String value;

		private IfApplyStatusEnum(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}

		@Override
		public String toString() {
			return value;
		}
	}

	/**
	 * 审核状态
	 * 
	 * @author Administrator
	 *
	 */
	public static enum AuditStatusEnum {
		WAITING("待审核"), PROCESSING("审核中"), PASS("通过"), UNPASS("未通过");

		private final String value;

		private AuditStatusEnum(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}

		@Override
		public String toString() {
			return value;
		}
	}

	/**
	 * 处理状态
	 * 
	 * @author Administrator
	 *
	 */
	public static enum ProcessStatusEnum {
		UNPROCESS("待处理"), PROCESSING("处理中"), SECCESS("成功"), FAIL("失败");

		private final String value;

		private ProcessStatusEnum(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	}

	/**
	 * 属性类型
	 * 
	 * @author Administrator
	 *
	 */
	public static enum PropertyTypeEnum {
		SYSTEM("系统"), WECHAT("微信"), APP("app");

		private final String value;

		private PropertyTypeEnum(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}

		@Override
		public String toString() {
			return value;
		}
	}

	/**
	 * 用户类型
	 * 
	 * @author Administrator
	 *
	 */
	public static enum UserTypeEnum {
		SYSTEM("系统"), AGENT("经纪人"), CUSTOMER("客户");

		private final String value;

		private UserTypeEnum(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	}

	/**
	 * 规则类型
	 * 
	 * @author Administrator
	 *
	 */
	public static enum RuleTypeEnum {
		BUILDING("楼盘推荐规则"), HOUSE("房源推荐规则"), AGENT("经纪人推荐规则");

		private final String value;

		private RuleTypeEnum(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	}

	/**
	 * 预约状态
	 * 
	 * @author Administrator
	 *
	 */
	public static enum AppointmentStatusEnum {
		N("未预约"), Y("已预约"), C("已取消");

		private final String value;

		private AppointmentStatusEnum(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}

		@Override
		public String toString() {
			return value;
		}
	}

	/**
	 * 跟进状态
	 * 
	 * @author Administrator
	 *
	 */
	public static enum FllowupStatusEnum {
		WAITING("待跟进"), PROCESSING("跟进中"), OK("已确认"), CANCEL("已取消");

		private final String value;

		private FllowupStatusEnum(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	}

	/**
	 * 地址等级
	 * 
	 * @author Administrator
	 *
	 */
	public static enum AreaLevelEnum {
		P("省和直辖市"), C("市"), A("区县"), S("街道");

		private final String value;

		private AreaLevelEnum(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	}

	/**
	 * 通知方式
	 * 
	 * @author Administrator
	 *
	 */
	public static enum NoteModeEnum {
		ALL("任何"), SMS("短信"), WECHAT("微信"), NONE("无");

		private final String value;

		private NoteModeEnum(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}

		@Override
		public String toString() {
			return value;
		}
	}

	/**
	 * 支付账号类型
	 * 
	 * @author Administrator
	 *
	 */
	public static enum PayAccountTypeEnum {
		WECHAT("微信"), ALIPAY("支付宝"), BANK("银行卡");

		private final String value;

		private PayAccountTypeEnum(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}

		@Override
		public String toString() {
			return value;
		}
	}

	/**
	 * 支付方式
	 * 
	 * @author Administrator
	 *
	 */
	public static enum PaymentEnum {
		WECHAT("微信"), ALIPAY("支付宝"), TRANSFER("银行转账"), POST("邮局汇款"), CASH("现金"), OTHER("其他");

		private final String value;

		private PaymentEnum(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}

		@Override
		public String toString() {
			return value;
		}
	}

	/**
	 * 支付状态
	 * 
	 * @author Administrator
	 *
	 */
	public static enum PayStatusEnum {
		UNPAY("待支付"), PAYING("支付中"), PROCESSING("处理中"), SECCESS("成功"), FAIL("失败"), OTHER("其他");

		private final String value;

		private PayStatusEnum(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}

		@Override
		public String toString() {
			return value;
		}
	}

	/**
	 * 交易类型
	 * 
	 * @author Administrator
	 *
	 */
	public static enum TradeTypeEnum {
		COMMISION("佣金提成");

		private final String value;

		private TradeTypeEnum(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	}

	/**
	 * 交易状态
	 * 
	 * @author Administrator
	 *
	 */
	public static enum TradeStatusEnum {
		//TRADING("交易中"), PAUSE("暂停"), SUCCESS("成功"), FAIL("失败");
		ONE("签订合同"),TWO("派单"),THREE("准备资料"),
		FOUR("通知"),FIVE("银行受理"),SIX("评估中"),SEVEN("面签"),
		EIGHT("网签"),NINE("过户"),TEN("交税"),ELEVEN("拿证"),TWELVE("进抵押"),
		THIRTEEN("出产权证"),FOURTEEN("交易完成");
		private final String value;
		

		private TradeStatusEnum(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}

		@Override
		public String toString() {
			return value;
		}
	}

	/**
	 * 账单类型
	 * 
	 * @author Administrator
	 *
	 */
	public static enum BillTypeEnum {
		I("入账"), O("出账");

		private final String value;

		private BillTypeEnum(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}

		@Override
		public String toString() {
			return value;
		}
	}

	/**
	 * 附件类型
	 * 
	 * @author Administrator
	 *
	 */
	public static enum AttachmentTypeEnum {
		DOC("文档"), IMG("图片"), MEDIA("媒体");

		private final String value;

		private AttachmentTypeEnum(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	}

	/**
	 * 文件类型后缀
	 * 
	 * @author Administrator
	 *
	 */
	public static enum AttachmentExtensionsEnum {
		DOC("txt,rtf,doc,docx,xls,xls,xlsx,csv,pdf"), IMG("gif,png,ico,pg,jpeg,jpg"), MEDIA(
				"wav,mp3,mp4,avi,mpg,dat,swf");

		private final String value;

		private AttachmentExtensionsEnum(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	}

	/**
	 * 附件拥有者
	 * 
	 * @author Administrator
	 *
	 */
	public static enum AttachmentOwnerEnum {
		BUILDING("楼盘"), HOUSE("房源"), AGENT("经纪人"), CUSTOMER("客户"), SYSTEM("系统");

		private final String value;

		private AttachmentOwnerEnum(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	}

	/**
	 * 装修类型
	 * 
	 * @author Administrator
	 *
	 */
	public static enum DecorationTypeEnum {
		ROUGH("毛坯"),SIMPLE("简装"),MIDDLE("中档"), HARDCOVER("精装"),LUXURY("豪华"), UNKNOWN("未知");

		private final String value;

		private DecorationTypeEnum(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}

		@Override
		public String toString() {
			return value;
		}
	}

	/**
	 * 停车场类型
	 * 
	 * @author Administrator
	 *
	 */
	public static enum ParkingSpaceEnum {
		INDOOR("室内"), OUTDOOR("室外"), ALL("室内室外"), NONE("无"), UNKNOWN("未知");
		private final String value;

		private ParkingSpaceEnum(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}

		@Override
		public String toString() {
			return value;
		}
	}

	/**
	 * 建筑用途
	 * 
	 * @author Administrator
	 *
	 */
	public static enum BuildingUseEnum {
		HOUSE("住宅"), OFFICE("写字楼"), SHOP("商铺"), BUSINESS_LIVING("商住混合"), APARTMENT("公寓"), VILLA("别墅"), FACTORY(
				"厂房"), WAREHOUSE("仓库"), OTHER("其他");
		private final String value;

		private BuildingUseEnum(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}

		@Override
		public String toString() {
			return value;
		}
	}

	/**
	 * 楼层属性
	 * 
	 * @author Administrator
	 *
	 */
	public static enum FloorPropertyEnum {
		HIGHT("高层"), SMALL_HIGHT("小高层"), LOWER("低层"), COTTAGE("平房"), OTHER("其他");
		private final String value;

		private FloorPropertyEnum(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}

		@Override
		public String toString() {
			return value;
		}
	}

	/**
	 * 建筑属性
	 * 
	 * @author Administrator
	 *
	 */
	public static enum BuildingPropertyEnum {
		FORWARD("期房"), EXISTING("现房");
		private final String value;

		private BuildingPropertyEnum(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}

		@Override
		public String toString() {
			return value;
		}
	}

	/**
	 * 楼盘房源图片分类
	 * 
	 * @author Administrator
	 *
	 */
	public static enum BuildingHouseImgCategoryEnum {
		EFFECT("效果图"), TEMPLET("样板图"), SCENERY("实景图"), TRAVEL("交通图"), HOUSE_TYPE("户型图"), ASSORT("配套图"), INDOOR("室内图");
		private final String value;

		private BuildingHouseImgCategoryEnum(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	}

	/**
	 * 合同类型分类
	 * 
	 * @author Administrator
	 *
	 */
	public static enum ContractTypeEnum {
		RENTING("租赁"), TRANSCATION("买卖");
		private final String value;

		private ContractTypeEnum(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}

		@Override
		public String toString() {
			return value;
		}
	}

	/**
	 * 房源意向
	 * 
	 * @author Administrator
	 *
	 */
	public static enum IntentionEnum {
		RENTING("出租"), WANTING("求租"), BUY("买房"), SALE("卖房");
		private final String value;

		private IntentionEnum(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}

		@Override
		public String toString() {
			return value;
		}
	}

	/**
	 * 意向等级
	 * 
	 * @author Administrator
	 *
	 */
	public static enum IntentionGradeEnum {
		CLEAR("意向明确"), WANTING("对比观望"), CASUAL("随便看看");
		private final String value;

		private IntentionGradeEnum(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}

	}

	/**
	 * 重要等级
	 * 
	 * @author Administrator
	 *
	 */
	public static enum ImportantGradeEnum {
		COMMON("一般"), IMPORTANT("重要");
		private final String value;

		private ImportantGradeEnum(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	}

	/**
	 * 项目认可度
	 * 
	 * @author Administrator
	 *
	 */
	public static enum AcceptGradeEnum {
		FULL("完全认可"), PART("部分接受"), REJECT("拒绝");
		private final String value;

		private AcceptGradeEnum(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	}

	/**
	 * 房源交易状态
	 * 
	 * @author Administrator
	 *
	 */
	public static enum HouseTransactionStatusEnum {
		WAITING_RENT_SALE("待租售"), WAITING_RENT("待租"), WAITING_SALE("待售"), RENTED("已租"), SALED("已售");
		private final String value;

		private HouseTransactionStatusEnum(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}

		@Override
		public String toString() {
			return value;
		}
	}

	/**
	 * 房源计划
	 * 
	 * @author Administrator
	 *
	 */
	public static enum HouseSchameEnum {
		RENT_SALE("租售"), RENT("出租"), SALE("出售");
		private final String value;

		private HouseSchameEnum(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}

		@Override
		public String toString() {
			return value;
		}
	}

	/**
	 * 公司性质
	 * 
	 * @author Administrator
	 *
	 */
	public static enum CompanyCategory {
		COUNTRY("国有企业"), GROUP("集体企业"), JOIN("联营企业"), PRIVATE("私有企业");
		private final String value;

		private CompanyCategory(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}

		@Override
		public String toString() {
			return value;
		}
	}

	/**
	 * 经纪人类型
	 * 
	 * @author Administrator
	 *
	 */
	public static enum AgentUserType {
		INDEPENDENT("独立经纪人"), COMPANY("公司经纪人");
		private final String value;

		private AgentUserType(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}

		@Override
		public String toString() {
			return value;
		}
	}

	/**
	 * 模块
	 * 
	 * @author Administrator
	 *
	 */
	public static enum ModelEnums {
		MENU("菜单管理"), DEPARTMENT("部门管理"), SYSTEMUSER("用户管理"), ROLE("角色管理"),
		PERMISSION("权限管理"), RULE("规则管理"), LOG("日志管理"), CONTRACT("合同管理"),
		TRADERECORD("房源交易管理"), BUSINESSPLATE("商圈管理"), AGENTUSER("经纪人管理"),
		AGENTCYBILL("转账记录"), HOUSEACCESSRECORD("看房记录"), BUILDING("楼盘管理"),
		AREA("区域管理"), SYSTEMPROPERTIES("系统属性"), COMPANY("公司管理"),LOGIN("登录"),
		CUSTOMER("客源列表"), RESOURCE("资源管理"), HOUSE("房源管理"), STORE("门店管理"),
		RESOURCETYPE("资源类型"), AGENTACCOUNT("账户管理"), AGENCYWITHDRAW("提现申请"),
		FEEDBACK("意见反馈");
		
		private final String value;

		private ModelEnums(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}

		@Override
		public String toString() {
			return value;
		}
	}

	/**
	 * 操作
	 * 
	 * @author Administrator
	 *
	 */
	public static enum OptionEnums {
		CREATE("新增"), DELETE("删除"), UPDATE("修改"), SELECT("查询"), LOGIN("登录");

		private final String value;

		private OptionEnums(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}

		@Override
		public String toString() {
			return value;
		}
	}

	/**
	 * 操作类型
	 * 
	 * @author Administrator
	 *
	 */
	public static enum OptionTypeEnums {
		CONSOLE("后台"), WECHART("微信"), APP("APP");

		private final String value;

		private OptionTypeEnums(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}

		@Override
		public String toString() {
			return value;
		}
	}

	/**
	 * 企业性质
	 * 
	 * @author Administrator
	 *
	 */
	public static enum CompanyCategoryEnums {
		COUNTRY("国有企业"), GROUP("集体企业"), JOIN("联营企业"), PRIVATE("私营企业");
		private final String value;

		private CompanyCategoryEnums(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}

		@Override
		public String toString() {
			return value;
		}
	}

	/**
	 * 看房状态
	 * 
	 * @author Administrator
	 *
	 */
	public static enum AccessStatusEnums {
		Y("已看房"), N("未看房");

		private final String value;

		private AccessStatusEnums(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}

		@Override
		public String toString() {
			return value;
		}
	}

	/**
	 * 是否需要更换经纪人
	 * 
	 * @author Administrator
	 *
	 */
	public static enum IfChangeAgent {
		Y("需要"), N("不需要");

		private final String value;

		private IfChangeAgent(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}

		@Override
		public String toString() {
			return value;
		}
	}
	
	/**
	 * 意见反馈类型
	 * 
	 * @author Administrator
	 *
	 */
	public static enum FeedBackTypeEnum {
		QUESTION("问题"), SUGGESTION("建议");

		private final String value;

		private FeedBackTypeEnum(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}

		@Override
		public String toString(){
			return value;
		}
	}
	
	/**
	 * 问题反馈处理状态
	 * 
	 * @author Administrator
	 *
	 */
	public static enum QuestionRepayStatusEnum {
		WAITING("待处理"), PROCESSING("处理中"), PROCESSED("已处理"), CLOSED("已关闭");

		private final String value;

		private QuestionRepayStatusEnum(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}

		@Override
		public String toString(){
			return value;
		}
	}
}
