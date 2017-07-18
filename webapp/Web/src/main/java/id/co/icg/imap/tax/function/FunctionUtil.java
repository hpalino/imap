/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.co.icg.imap.tax.function;

import id.co.icg.imap.tax.web.ActionBean;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.codehaus.jackson.map.ObjectMapper;

/**
 *
 * @author ICG-PRG01
 */
public class FunctionUtil {

    public final static Date stringToDate(String date, String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        Date d = null;
        try {
            d = formatter.parse(date);
        } catch (ParseException ex) {
            Logger.getLogger(ActionBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return d;
    }

    public final static Date stringToDate(String date) {
        Date d = null;
        String regex = "^[0-3]?[0-9]/[0-3]?[0-9]/(?:[0-9]{2})?[0-9]{2}$";
        Pattern pattern = Pattern.compile(regex);
        if (date != null) {
            if (date.length() == 10) {
                Matcher matcher = pattern.matcher(date);
                if (matcher.matches()){
                    d = stringToDate(date, "MM/dd/yyyy");
                } else {
                    d = stringToDate(date, "yyyy-MM-dd");
                }
            } else if (date.length() == 19) {
                d = stringToDate(date, "yyyy-MM-dd HH:mm:ss");
            }
        }
        return d;
    }

    public final static String dateTimeFormat(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        if (date != null) {
            return sdf.format(date);
        } else {
            return "-";
        }
    }

    public final static String jSon(List lists) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            Object[] array = lists.toArray(new Object[lists.size()]);
            return mapper.writeValueAsString(array);
        } catch (IOException e) {
            return null;
        }
    }

    public final static String jSon(Object object) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(object);
        } catch (IOException e) {
            return null;
        }
    }

    public final static String numberToAccounting(Object num) {
        return num == null ? "-" : NumberFormat.getNumberInstance(Locale.ITALY).format(num);
    }

    public final static String numberToPercentage(Object num, Integer decimalLeads) {
        NumberFormat percentFormat = NumberFormat.getPercentInstance();
        percentFormat.setMaximumFractionDigits(decimalLeads!=null ? decimalLeads : 0);
        return num == null ? "-" : percentFormat.format(num);
    }

    public final static String numberToString(Object num) {
        return num == null ? "" : num.toString();
    }

    public final static String formatSeparator(String text, String insert, int period) {
        Pattern p = Pattern.compile("(.{" + period + "})", Pattern.DOTALL);
        Matcher m = p.matcher(text);
        return m.replaceAll("$1" + insert);
    }

    public final static Date getEndOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }
}
