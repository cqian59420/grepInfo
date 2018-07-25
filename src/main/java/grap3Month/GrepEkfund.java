package grap3Month;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
public class GrepEkfund {

    private static final String url = "http://m.ekeyfund.com/borrowInfo/list.htm";

    public static void main(String[] args) {
        Executors.newScheduledThreadPool(1).scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                try {
                    start();
                    log.error("开始获取借款信息,{}",1111111);
                    System.out.println("开始获取借款信息3月标 "+ new Date());
                } catch (Exception e) {

                }
            }
        }, 0, 8, TimeUnit.MINUTES);
    }

    public static void start() throws Exception {

        log.info("开始获取借款信息,{}",new Date());
        ObjectMapper mapper = new ObjectMapper();

        JsonNode jsonNode = mapper.readTree(new URL(url));
        String text = jsonNode.asText();
        String substring = text.substring(0, text.length());

        JsonNode node = mapper.readTree(substring);
        JsonNode listBorrow = node.path("listBorrow");

        if (listBorrow != null) {
            JavaType javaType = getCollectionType(mapper, ArrayList.class, Object.class);
            List<Object> list = mapper.readValue(listBorrow.toString(), javaType);
            List<BorrowInfo> borrowInfos = DozerUtil.mapList(list, BorrowInfo.class);

            List<BorrowInfo> collect = borrowInfos.stream().filter(borrowInfo -> !borrowInfo.getBorrowMoney().equalsIgnoreCase(borrowInfo.getRaiseMoney())).filter(
                    borrowInfo -> borrowInfo.getDurType().equalsIgnoreCase("MONTH")
            ).collect(Collectors.toList());

            for (BorrowInfo borrowInfo : collect) {
                if (is3Month(borrowInfo)) {
                    System.out.println("3月标来了！,剩下："+borrowInfo.shortDesc());
                    sendMeMail("3月标来了！,剩下 ");
                }

                System.out.println(borrowInfo);
            }
        }
    }

    private static boolean is3Month(BorrowInfo borrowInfo) {
        return StringUtils.isNotEmpty(borrowInfo.getDurType()) && borrowInfo.getDurType().equalsIgnoreCase("MONTH")
                &&
                StringUtils.isNotEmpty(borrowInfo.getBorrowDuration()) && borrowInfo.getBorrowDuration().equalsIgnoreCase("3");
    }

    public static JavaType getCollectionType(ObjectMapper mapper, Class<?> collectionClass, Class<?>... elementClasses) {
        return mapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
    }


    private static void sendMeMail(String msg) {
        MailServer.instance().sendMail(msg);
    }


}


