package jetty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: 2/14/13
 * Time: 1:35 PM
 */
public class GCMUtilsMessageSender {
    private static final Logger logger = LoggerFactory.getLogger(GCMUtilsMessageSender.class);

    public static void main(String[] args) throws IOException {
        String msg = "this is a test message";
        URL url = new URL("http://localhost:10246/?msg=" + URLEncoder.encode(msg, "utf-8"));
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoOutput(true);
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
        int responseCode = connection.getResponseCode();
        connection.disconnect();

        logger.info("Response: {}", responseCode);
    }
}
