package com.icv.fota.message.service;

import com.icv.fota.message.util.JsonUtil;
import com.xiaomi.xmpush.server.Constants;
import com.xiaomi.xmpush.server.Message;
import com.xiaomi.xmpush.server.Sender;
import com.xiaomi.xmpush.server.Sender.BROADCAST_TOPIC_OP;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 小米推送工具类
 *
 * @date 2018年3月14日
 */
@Service
public class XiaoMiPushService {

    private static String APP_SECRET_KEY_ANDROID = "YD6InICFy97ahjkFY5vbKA==";
    private static String MY_PACKAGE_NAME = "com.zhaisoft.app.ota";

    /**
     * 向所有设备发送推送
     *
     * @param messagePayload 消息
     * @param title          消息标题
     * @param description    消息描述
     * @param ads_type       消息类型
     * @param ads_links      消息链接
     * @throws IOException
     * @throws ParseException
     */
    public void sendAllBroadcast(String messagePayload, String title, String description, String ads_type,
                                        String ads_links) throws IOException, ParseException {

        Constants.useOfficial();
        Map map = new HashMap();
        map.put("ads_type", ads_type);
        map.put("ads_links", ads_links);



//        Message message = new Message.Builder().title(title).description(description).payload(messagePayload)
//                .extra("data", JSONObject.fromObject(map).toString()).restrictedPackageName(MY_PACKAGE_NAME)
//                .notifyType(1).passThrough(0) // 使用默认提示音提示
//                .build();

        Message message = new Message.Builder().title(title).description(description).payload(messagePayload)
                .extra("data", JsonUtil.objectToString(map)).restrictedPackageName(MY_PACKAGE_NAME)
                .notifyType(1).passThrough(0) // 使用默认提示音提示
                .build();
        //安卓推送
        Sender sender_android = new Sender(APP_SECRET_KEY_ANDROID);
        // 根据topicList做并集运算, 发送消息到指定一组设备上
        sender_android.broadcastAll(message, 3);

    }

    /**
     * 指定标签推送(推送指定类型用户)
     *
     * @param messagePayload 消息
     * @param title          消息标题
     * @param description    消息描述
     * @param ads_type       消息类型
     * @param ads_links      消息链接
     * @param topicList      指定推送类型
     * @throws IOException
     * @throws ParseException
     */
    public void sendBroadcast(String messagePayload, String title, String description, String ads_type,
                                     String ads_links, List<String> topicList) throws IOException, ParseException {

        Constants.useOfficial();
        Map map = new HashMap();
        map.put("ads_type", ads_type);
        map.put("ads_links", ads_links);

        Message message = new Message.Builder().title(title).description(description).payload(messagePayload)
                .extra("data", JsonUtil.objectToString(map)).restrictedPackageName(MY_PACKAGE_NAME)
                .notifyType(1).passThrough(0) // 使用默认提示音提示
                .build();

        // 安卓推送
        Sender sender_android = new Sender(APP_SECRET_KEY_ANDROID);
        // 根据topicList做并集运算, 发送消息到指定一组设备上
        sender_android.multiTopicBroadcast(message, topicList, BROADCAST_TOPIC_OP.UNION, 3);


    }

    /**
     * 指定alias推送(单个或多个)
     *
     * @param messagePayload 消息
     * @param title          消息标题
     * @param description    消息描述
     * @param ads_type       消息类型
     * @param ads_links      消息链接
     * @param aliasList      指定alias
     * @throws IOException
     * @throws ParseException
     */
    public void sendMessageToAliases(String messagePayload, String title, String description, String ads_type,
                                            String ads_links, List<String> aliasList) throws IOException, ParseException {
        Constants.useOfficial();
        Map map = new HashMap();
        map.put("ads_type", ads_type);
        map.put("ads_links", ads_links);

        Message message = new Message.Builder().title(title).description(description).payload(messagePayload)
                .extra("data", JsonUtil.objectToString(map)).restrictedPackageName(MY_PACKAGE_NAME)
                .notifyType(1).passThrough(0) // 使用默认提示音提示
                .build();

        //安卓推送
        Sender sender_android = new Sender(APP_SECRET_KEY_ANDROID);
        sender_android.sendToAlias(message, aliasList, 3);
    }

//    public static void main(String args[]){
//        XiaoMiPushService xiaoMiPushService = new XiaoMiPushService();
//        try {
//            // // 王强 71821A4AF92E
//            // // 翟熙贵 8DF78A00E73F
//            List<String> aliasList = new ArrayList<>();
//            aliasList.add("8DF78A00E73F");
//            aliasList.add("71821A4AF92E");
//            xiaoMiPushService.sendMessageToAliases("333333333","yyyyyyy","777777","0","1",aliasList);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//    }
}
