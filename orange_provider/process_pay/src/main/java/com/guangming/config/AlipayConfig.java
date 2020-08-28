package com.guangming.config;

import java.io.FileWriter;
import java.io.IOException;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *修改日期：2017-04-05
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
 */

public class AlipayConfig {

//请在这里配置您的基本信息

    // 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
    public static String app_id = "2016102900776579";

    // 商户私钥，您的PKCS8格式RSA2私钥
    public static String merchant_private_key = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCXuU592tO7+8g5ZsRqGAEDSl7YJKBlJuKNkVsSdQ35LLqW0g0wj+wIMJ9zeQR+6lImb+Kl5eViScxa1DIX5wDy+/sxfqTaaoRfAycLKKBcnsRSoByCjvRqtrq0AzAL2TDoVAddTATQG9wOTA/ouj09P7prCayn0MuxkOA8m/J6uufoN9r8ip/DChH/IQBWBza5lpev5kk42iU9fM7MW01reK8SjbOfDoIPH2SDFEQIY2pApYa8r0QrAEyq5CvVewSs49i1XpTHR9OSwsl4l/e470Z2d64rbqz5JxHz9fm8rKpt6jkMQwNcjA+m/gr5tmT0pa5j7x7ja1y58pXKzoGrAgMBAAECggEBAIA0c2DhkdzcyrnGXt4PXnu7Uh1uBaKzkuhxFPWpt57/7sMWx1JrqtTY0UOv1N6xfa1BCdGJS6UcGvP9Lj8IUcMAraeLDh4Jt8TZUxqYv7jD57yx0qV3yoyJz4Xg066PXkZTCs4JOF/pyq7CEwdip/CjcxCTYjODuTtX4kMUu3Z8Ovv6oqwSL0Q7Ta6rHahoF8VSo0OoBj0CnnFh7KkTdyqreUf2fl+XmqOaV7aYxoxWxfTaN8oe0RPd4dV7efKZxn1AdeGnP5LXYVUusHInxrtYGJFeRTvXl+Su0avfEz5SoJ8SUx/I08dPDa5qhsqobBzH1Hp3HdR1VI2/WVPrwMECgYEA6lKr+3zGmPthCYKHrIx9080VpLKJ61ZK+spNfW2NahE1J1FVlU7AR3ijCWzC1V1U5fAZuic9gvzTR4bJ/7U4O5WmfKvpfze++//2U10XlJYxe79R1uoMtXt5/nNmvqlO2wuXXl1H2ERrlltwGrqabfpvQfcSUPHa2oAB7D3k7tsCgYEApcJ9ocNm9r8IE+oSYLSbG/xGVGZBPaQXpFMjWDwylIr8IIyfUM/+9zgcpigdD2pXS+QKHj0n4uoKfUV4/PRyj7xjduUNat5zPAO0iRzEYlg7d43fLv7BZCvLURVmbyr4jscPC+WmeM1tn5L8Dn4TNwQIGPbdCyHzSJDsM3OAKXECgYEAv8Sm8audUOgx8zl7VdCozaUC+IYRb6Nwu6zDHEqDOHIawTHES+xmWle1fmofTcUTTRdhxsKEieXl95ytRnOGMOV5moCLGM2DpPNHIdSHDtiYaMEb9joX6pLCNWc2iAVB/uyOlfDbcngUYLo2lflvl2Akm6TbvZkh1j561F+XM48CgYAN2vHxYYnruhAMyhX7K12Du1SNnXZ6loq7W4+qwm1f6TYl6lsuwBQJyhDq+pNjRHpDh64o1OfOmc2Cpxnz2uLwjMirmX3DnxB2xcCDbXok1kcyOROctAKqQtbd0jIbvLXGCXGChxGhK1qsafbTp0on4RhkLc5QLehN+n8j1dWyoQKBgFE8yTKf+bzX5ty7UkXRv07cgf7ukyeU+fO9xql/mPCHiqcYBQ5Vs7jjO0U9KT7usid4+Ovn5Z4McRdHiMITsVrHC5gyH6vDc+ZmNGN2ea9l6bybp57Q3O/iI6FzKssCmwXYTSoL1TcFIjQ5a9XZ9Rkw2KkJvFPOis8thRdXAbtk";


    // 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static String alipay_public_key = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDIgHnOn7LLILlKETd6BFRJ0GqgS2Y3mn1wMQmyh9zEyWlz5p1zrahRahbXAfCfSqshSNfqOmAQzSHRVjCqjsAw1jyqrXaPdKBmr90DIpIxmIyKXv4GGAkPyJ/6FTFY99uhpiq0qadD/uSzQsefWo0aTvP/65zi3eof7TcZ32oWpwIDAQAB";
    // 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String notify_url = "https://www.linignan.cn/pay/notifyNotice";

    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String return_url = "https://www.linignan.cn/pay/returnNotice";

    // 签名方式
    public static String sign_type = "RSA2";

    // 字符编码格式
    public static String charset = "utf-8";

    // 支付宝网关
    public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";

    // 支付宝网关
    public static String log_path = "C:\\";

    public static String app_cert_path = "/usr/resources/appCertPublicKey_2016102900776579.crt";
    public static String alipay_cert_path = "/usr/resources/alipayCertPublicKey_RSA2.crt";
    public static String alipay_root_cert_path = "/usr/resources/alipayRootCert.crt";

    /**
     * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
     *
     * @param sWord 要写入日志里的文本内容
     */
    public static void logResult(String sWord) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(log_path + "alipay_log_" + System.currentTimeMillis() + ".txt");
            writer.write(sWord);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

