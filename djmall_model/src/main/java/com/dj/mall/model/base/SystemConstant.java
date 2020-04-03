package com.dj.mall.model.base;



public interface SystemConstant {

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
     * 用户状态：已激活 10
     */
    Integer USER_STATUS_ACTIVATE = 10;

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
     * BASE_DATA_MANAGER
     */
    String BASE_DATA_MANAGER = "BASE_DATA_MANAGER";


    /**
     * 10激活，11未激活
     */
    String USER_STATUS = "STATUS";



}
