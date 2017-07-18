/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.co.icg.imap.tax.function;

/**
 *
 * @author ICG-PRG01
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.HttpVersion;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.log4j.Logger;

public class HttpTransport {

    private static Logger logger = Logger.getLogger(HttpTransport.class);

    private static int socketTimeout = 10;

    public HttpTransport(int socketTimeout) {
        HttpTransport.socketTimeout = socketTimeout;
    }

    public static String submit(String url, Map<String, String> queryString) {
        HttpClient httpclient = new HttpClient();

        httpclient.getParams().setParameter("http.protocol.version", HttpVersion.HTTP_1_1);
        httpclient.getParams().setParameter("http.socket.timeout", socketTimeout * 1000);
        httpclient.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(5, false));
        GetMethod method = new GetMethod(url);

        if (queryString != null) {
            List<NameValuePair> list = new ArrayList<NameValuePair>();
            for (String key : queryString.keySet()) {
                list.add(new NameValuePair(key, queryString.get(key)));
            }
            method.setQueryString(list.toArray(new NameValuePair[list.size()]));
        }

        String response = "";
        try {
            int statusCode = httpclient.executeMethod(method);
            if (statusCode != HttpStatus.SC_OK) {
                logger.error("Method failed: " + method.getStatusLine());
                response = "Error";
            } else {
                response = method.getResponseBodyAsString();
            }
            if (url!="https://roads.googleapis.com/v1/snapToRoads") System.out.println("RESP : " + response);
        } catch (Exception ex) {
            logger.error(ex);
        } finally {
            method.releaseConnection();
        }
        return response;
    }

}