package com.dj.mall.model.base;



public interface SystemConstant {


    /**
     * 请点击短信验证码发送
     */
    String SEND_LOAD = "请点击短信验证码发送";

    /**
     * 验证码超时，请重新发送
     */
    String AUTH_RRROR = "验证码超时，请重新发送";

    /**
     * "无，按照原价"
     */
    String SKU_RATE_SHOW = "无，按照原价";

    /**
     * "手机号不能为空"
     */
    String PHONE_NULL = "手机号不能为空";

    /**
     * 短信验证码不能为空
     */
    String SEND_NULL = "短信验证码不能为空";

    /**
     * 图形验证码输入错误
     */
    String NOT_IMG_AUTH = "图形验证码输入错误";

    /**
     * 图形验证码不能为空
     */
    String IMG_AUTH_NULL = "图形验证码不能为空";



    /**
     * 已达上限
     */
    String CODE_ERROR = "已达上限";

    /**
     * 验证码
     */
    String SESSION_VERIFY_CODE = "VERIFY_CODE";

    /**
     * 角色不匹配
     */
    String USER_NOT_ROLE = "角色不匹配";

    /**
     * 用户角色：商户-1
     */
    Integer USER_ROLE_BUYER_ID = 1;

    /**
     * 用户角色：买家-3
     */
    Integer USER_ROLE_USER_ID = 3;
    /**
     * String方法调用：-
     */
    String PARENT_NAME  = "-";

    /**
     * String方法调用：""
     */
    String EXCEPTION = "";

    /**
     * String方法调用：.
     */
    String SYMBOL = ".";

    /**
     * 页面大小
     */
    Integer PAGE_SIZE = 5;

    /**
     * 用户角色ID
     */
    Integer USER_ROLE_ID = 1;

    /**
     * 集合下标
     */
    Integer ARRAY_SUB = 0;

    /**
     * 是否默认[0默认,1不默认]
     */
    Integer IS_DEFAULT_YES = 0;

    /**
     * 基础数据，性别
     */
    String USER_SEX = "SEX";

    /**
     * 密码已重置，请重新修改密码
     */
    String RSEET_PWD_IS_DEL_CODE = "密码已重置，请重新修改密码";

    /**
     * 重置密码=0
     */
    Integer RSEET_PWD_IS_DEL = 0;

    /**
     * 待激活
     */
    String USER_NOT_STATUS = "NOT_ACTIVE";

    /**
     * 待激活CODE
     */
    String USER_STATUS_CODE = "用户未激活";

    /**
     * 重置密码信息1
     */
    String EMAIL_RESET_PWD_CODE_1 = "尊敬的";

    /**
     * 重置密码信息2
     */
    String EMAIL_RESET_PWD_CODE_2 = ",您的密码已被管理员";

    /**
     * 重置密码信息3
     */
    String EMAIL_RESET_PWD_CODE_3 = ",于";

    /**
     * 重置密码信息5
     */
    String EMAIL_RESET_PWD_CODE_5 = "重置为";

    /**
     * 重置密码信息4
     */
    String EMAIL_RESET_PWD_CODE_4 = ",为了您的账户安全，请及时修改，点我去登录";


    /**
     * 重置密码
     */
    String RESET_PWD = "重置密码:";

    /**
     * EMAIL_CODE
     */
    String EMAIL_ADD_CODE = "点击激活";

    /**
     * 手机号不存在，请注册
     **/
    String PHONE_REGISTER  = "手机号不存在，请注册";

    /**
     * "手机号与验证码不匹配!!"
     */
    String PHONE_LOGIN = "手机号与验证码不匹配!!";

    /**
     * 用户不存在
     */
    String USER_NOT_Z = "用户不存在";

    /**
     * 登陆输入为null
     */
    String LOGIN_NULL = "登陆输入为null";


    /**
     *请求成功
     */
    String REQ_YES= "请求成功";

    /**
     * 用户session USER_SESSION
     */
    String USER_SESSION = "USER_SESSION";

    /**
     * 账号密码错误
     */
    String IS_DEL_NOT = "账号密码错误";

    /**
     * 激活链接
     */
   String STRING_EMAIL = "激活链接";

    /**
     * 注册成功,需邮箱激活
     */
    String STRING_4 = "注册成功,需邮箱激活";

   /**
    * 1：正常显示
    */
   Integer IS_DEL = 1;


    /**
     * role_id = 1
     */
    Integer ROLE_IS_DEL_YES = 1;

    /**
     * 按钮 res_type = 1
     */
    Integer RES_TYPE = 1;


    /**
     * 账号以激活
     */
    String ID_ACTIVATE = "账号已激活";

    /**
     * 用户状态：已激活
     */
    String USER_STATUS_ACTIVATE = "ACTIVE";

    /**
     * 2：非正常显示
     */
    Integer NOT_IS_DEL = 2;

    /**
     * RESOURCE_MANAGER
     */
    String RESOURCE_MANAGER = "RESOURCE_MANAGER";

    /**
     * ROLE_MANAGER
     */
    String ROLE_MANAGER = "ROLE_MANAGER";

    /**
     * USER_MANAGER
     */
    String USER_MANAGER = "USER_MANAGER";

    /**
     * ORDER_MANAGER
     */
    String ORDER_MANAGER = "ORDER_MANAGER";

    /**
     * BASE_DATA_MANAGER
     */
    String BASE_DATA_MANAGER = "BASE_DATA_MANAGER";

    /**
     * FREIGHT_MANAGER
     */
    String FREIGHT_MANAGER = "FREIGHT_MANAGER";

    /**
     * PRODUCT_MANAGER
     */
    String PRODUCT_MANAGER = "PRODUCT_MANAGER";



    /**
     * USER_STATUS
     */
    String USER_STATUS = "STATUS";

    /**
     * LOGISTICS
     */
    String LOGISTICS = "LOGISTICS";

    /**
     * PRODUCT_ATTR_MANAGER
     */
    String PRODUCT_ATTR_MANAGER = "PRODUCT_ATTR_MANAGER";

    /**
     * PRODUCT_SKU_MANAGER
     */
    String PRODUCT_SKU_MANAGER = "PRODUCT_SKU_MANAGER";

    /**
     * 字典商品类型
     */
    String PRODUCT_TYPE = "PRODUCT_TYPE";

    /**
     * 字符串split方法
     */
    String SPLIT = "-";

    /**
     * 默认商品不可以下架
     */
    String NOT_UPDATE_PRODUCT_SKU_STATUS = "默认商品不可以下架";

    /**
     * 该商品已下架，不能设为默认
     */
    String NOT_UPDATE_PRODUCT_SKU_IS_DEFAULT = "该商品已下架，不能设为默认";

    /**
     * 用户已激活
     */
    String ACTIVE = "ACTIVE";

}
