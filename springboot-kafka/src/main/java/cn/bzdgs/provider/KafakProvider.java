package cn.bzdgs.provider;

import cn.bzdgs.model.Message;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class KafakProvider {

    private KafkaTemplate<String,String> template;

    public void send(){
        Message message = new Message();
        message.setId(System.currentTimeMillis());
        message.setDateTime(new Date(System.currentTimeMillis()));
        message.setMsg("hallo world!!");
        log.info("+++++++++++++++++++++  message = {}", JSON.toJSONString(message));
        template.send("test",JSON.toJSONString(message));
    }
}

