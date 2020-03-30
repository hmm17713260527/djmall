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








//    /**
//     * "验证码已失效"
//     */
//    String MESSAGE = "验证码已失效";
//
//    /**
//     * "用户已被删除!"
//     */
//    String USER_DEL = "用户已被删除!";
//
//
//    /**
//     * "手机号与验证码不匹配!!"
//     */
//    String PHONE_LOGIN = "手机号与验证码不匹配!!";
//
//    /**
//     * 系统正在维护...请稍后再试
//     */
//    String ERROR = "系统正在维护...请稍后再试";
//
//    /**
//     * 还款分期
//     */
//    Integer REFUND_DATE = 5;
//
//
//    /**
//     * 1：未删除 //月份差 //借款状态
//     */
//    Integer NOT_DELETE_IS_DEL = 1;
//
//    /**
//     * 2：删除
//     */
//    Integer DELETE_IS_DEL = 2;
//
//    /**
//     * 输入不能为空
//     */
//    String NOT_NULL = "输入不能为空";
//
//    /**
//     * 服务器异常
//     */
//    String EXCEPTION = "";
//
//
//
//    /**
//     * 输入有误
//     */
//    String INPUT_ERROR = "输入有误";
//
//    /**
//     * 交易记录
//     */
//    String PARENT_NAME  = "-";
//
//    /**
//     * 用户名不存在
//     */
//    String NULL_USERNAME  = "用户名不存在";
//
//    /**
//     * 手机号不存在，请注册
//     **/
//    String PHONE_REGISTER  = "手机号不存在，请注册";
//
//    /**
//     * 操作成功
//     **/
//    String SUCCESS = "操作成功";
//
//    /**
//     * 银行类型p_id = 10 //信誉积分
//     **/
//    Integer BANK_TYPE_PID= 10;
//
//    /**
//     * 银行审核状态p_id = 15
//     */
//    Integer BANK_STATUS_PID = 15;
//
//    /**
//     *
//     */
//   Integer CARD_STATUS_AWAIT = 16;
//
//    /**
//     * 信誉积分：1
//     */
//    Integer CREDIT_INTEGRAL = 1;
//
//    /**
//     * 积分
//     */
//    Integer INTEGRAL = 100;
//
//    /**
//     * 用户等级
//     */
//    Integer FIRST = 1;
//
//    /**
//     * @Description:银行卡密码输入错误，请重新输入
//     * @Author: Liuwf
//     * @Date:
//     * @param null:
//     * @return: null
//     **/
//    String INPUT_PASSWORD_ERROR = "银行卡密码输入错误，请重新输入";
//
//
//    /**
//     * 充值
//     */
//    String ADD = "充值";
//
//
//    /**
//     * 余额不足
//     */
//     String NOT_SUFFICIENT_FUNDS  = "余额不足";
//     /**
//      * @Description:每人只能申请同一银行类型的银行卡一张
//      * @Author: Liuwf
//      * @Date:
//      * @param null:
//      * @return: null
//      **/
//    String BANK_CARD_TYPE_IS_ONE = "每人只能申请同一银行类型的银行卡一张";
//
//    /**
//     * 转账卡号检验
//     */
//    String BANK_CARD_NUMBER_TEST = "不可以给自己转账";
//    /**
//     * 可以转账
//     */
//    String TRANSFER_YES = "可以转账";
//    /**
//     * 当前时刻
//     */
//    Date NOW_TIME = new Date();
//    /**
//     * 转账
//     */
//    String TRANSFER = "转账";
//
//
//    /**
//     *您有银行卡因未准时还款，以冻结
//     */
//    String ACCOUNT_IS_FROZEN = "您有银行卡因未准时还款，以冻结";
//
//    /**
//     * 欢迎登陆掌上银行APP
//     */
//    String WELCOME = "欢迎登陆掌上银行APP";
//
//    /**
//     * 借款类型 0  //月份差
//     */
//    Integer TYPE = 0;
//
//    /**
//     * 基础数据表中审核通过状态的id 17
//     */
//    Integer APPROVE_STATUS = 17;
//
//    /**
//     * 个人总积分初始值 0
//     */
//    Integer SUM_INTEGRAL = 0;
//
//    /**
//     * 符号 .
//     */
//    String SYMBOL = ".";
//    /**
//     * @Description:baseData id 银行卡冻结
//     * @Author: Liuwf
//     * @Date:
//     * @param null:
//     * @return: null
//     **/
//    Integer BANK_CARD_LOCK = 19;
//    /**
//     * @Description: 用于判断减少信誉积分：1：已减少;
//     * @Author: Liuwf
//     * @Date:
//     * @param null:
//     * @return: null
//     **/
//    Integer CARD_integral_TYPE = 1;
//    /**
//     * @Description:初始信誉值 60；
//     * @Author: Liuwf
//     * @Date:
//     * @param null:
//     * @return: null
//     **/
//    Integer FRIST_REPUTATION_VALUE = 60;
//    /**
//     * @Description:初始积分值 1000；
//     * @Author: Liuwf
//     * @Date:
//     * @param null:
//     * @return: null
//     **/
//    Integer FRIST_INTEGRAL = 1000;
//    /**
//     * @Description初始借款额度
//     * @Author: Liuwf
//     * @Date:
//     * @param null:
//     * @return: null
//     **/
//    Double FRIST_BORROWBALANCE_MONEY= 30000.00;
//    /**
//     * @Description: 余额 0.00
//     * @Author: Liuwf
//     * @Date:
//     * @param null:
//     * @return: null
//     **/
//    Double FRIST_BALANCE = 0.00;
//
//    /**
//     * 审核未通过
//     */
//    Integer LOANS_STATUS_NOT = 18;
//
//    /**
//     * 审核通过
//     */
//    Integer LOANS_STATUS = 17;
//
//    /**
//     * 资源查看
//     */
//    Integer RESOURCES_TYPEZ = 1;
//
//    /**
//     * user资源查看
//     */
//    Integer RESOURCES_TYPEZ_USER = 2;
//
//    /**
//     * 管理资源查看
//     */
//    Integer RESOURCES_TYPEZ_ADMIN = 3;

}
