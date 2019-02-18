package com.icv.fota.message.thread;

import com.aliyun.mns.client.CloudAccount;
import com.aliyun.mns.client.CloudQueue;
import com.aliyun.mns.client.MNSClient;
import com.icv.fota.message.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Map;

@Component
public class MessageCenter implements Runnable {
    private final Logger log = LoggerFactory.getLogger(MessageCenter.class);

    public static String ENDPOINT = "http://1765789652209016.mns.cn-hangzhou.aliyuncs.com/";
    public static String KEY_ID = "LTAIRFOw2YpbWhAq";
    public static String KEY_SECRET = "x35HfniH4CRvNmp8bd67qK6XnAralX";

    private MNSClient client;
    private CloudQueue queue;
    volatile boolean flag = true;
//    @Value("${accesskey_id}")
//    private String accesskey_id;

    public MessageCenter(){
        CloudAccount account = new CloudAccount(KEY_ID, KEY_SECRET, ENDPOINT);
        client = account.getMNSClient();
        queue = client.getQueueRef("dev-icv-strategy");
    }


    @Override
    public void run() {
//        System.out.println("accessLogs -> " + accesskey_id);
        while (flag) {
            com.aliyun.mns.model.Message popMsg = null;
            try {
                popMsg = queue.popMessage(30);
                log.info(Thread.currentThread().getName() + " : " + Instant.now().getEpochSecond()+"  :  dev-icv-strategy");
                if (null != popMsg) {
                    log.info(Thread.currentThread().getName() + "获得资源, messageId:" + popMsg.getMessageId() + "    requestId" + popMsg.getRequestId() + "  content:" + popMsg.getMessageBody());
                    queue.deleteMessage(popMsg.getReceiptHandle());
                }
            } catch (Exception requestE) {
                queue = client.getQueueRef("icv-message");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    log.error(e.getMessage());
                    Thread.currentThread().interrupt();
                }
            }

            Map<String,Object> messageMap ;
            try {
                if (null != popMsg) {
                    messageMap = JsonUtil.stringToObject(popMsg.getMessageBody(), Map.class);
                    System.out.println(messageMap);
                } else {
                    Thread.sleep(1);
                }

            } catch (Exception e) {

            }
        }
    }

}
