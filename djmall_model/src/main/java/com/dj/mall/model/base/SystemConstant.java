package com.dj.mall.model.base;


public interface SystemConstant {


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
     * 用户状态：已激活 1
     */
    Integer USER_STATUS_ACTIVATE = 1;

    /**
     * 2：非正常显示
     */
    Integer NOT_IS_DEL = 2;



}
