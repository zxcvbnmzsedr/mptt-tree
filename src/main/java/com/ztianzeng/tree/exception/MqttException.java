package com.ztianzeng.tree.exception;

/**
 * MQTT exception
 *
 * @author zhaotianzeng
 * @version V1.0
 * @date 2019-07-12 16:16
 */
public class MqttException extends RuntimeException {
    public MqttException(String message) {
        super(message);
    }
}