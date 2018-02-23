package com.xc.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.cglib.beans.BeanMap;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.*;

public class CommonUtils {

    public static String getRandomString(int length) {
        //随机字符串的随机字符库
        String KeyString = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuffer sb = new StringBuffer();
        int len = KeyString.length();
        for (int i = 0; i < length; i++) {
            sb.append(KeyString.charAt((int) Math.round(Math.random() * (len - 1))));
        }
        return sb.toString();
    }


    public static String getFileNameFromHttp(String url) {
        if (url==null||!url.contains("/")){
            return null;
        }
        return url.substring(url.lastIndexOf("/")+1);
    }

    public static String covertToUrlList(String files,String url){
        String[] file =  files.split(",");
        List<String> list = new ArrayList<String>();
        for(String f:file){
            list.add(url+f);
        }
        return list.toString().replace("[","").replace("]","");
    }


    public static  List<Map<String,Object>> putCityIntoChar(List<Map<String,Object>> list){
        List<Map<String,Object>> return_list = new ArrayList<Map<String,Object>>();
        for(int i=(int)'A';i<'A'+26;i++)
        {
            Map<String,Object> city_list_name = new HashMap<String,Object>();
            List<Map<String,Object>> city_list = new ArrayList<Map<String,Object>>();
            for (Map<String,Object> m:list){
                if(m.get("city_first").equals(""+(char)i)){
                    Map<String,Object> map = new HashMap<String,Object>();
                    map.put("city_name",m.get("city"));
                    city_list.add(map);
                }
            }
            city_list_name.put("FirstLetter",""+(char)i);
            if(city_list.size()!=0){
                city_list_name.put("city_list",city_list);
            }
            return_list.add(city_list_name);

        }
        //System.out.println(city_list_name);
        return return_list;
    }

    public static String parseHttp(String text,String domain){
        String result;
        if (!text.contains("<p>")){
            result = text;
        }
        else {
            Document doc = Jsoup.parse(text);
            Elements imgs = doc.select("img");
            for (Element img : imgs) {
                StringBuilder sb = new StringBuilder(img.attr("src"));
                if (!(sb.indexOf("https://")!=-1)&&!(sb.indexOf("http://")!=-1)){
                    img.attr("src", sb.insert(sb.indexOf("/"), domain).toString());
                }
            }
            result=doc.toString();
        }
        return result;
    }

    public static String add(Object object) {

        String className = object.getClass().getSimpleName();
        StringBuilder sql = new StringBuilder("insert into ");
        sql.append(className.toLowerCase()+" ");
        sql.append("set ");
        String str= getClassValueObj(object).toString().replace("{","").replace("}","");
        sql.append(str);
        return sql.toString();


    }

    public static List<String>  setList(String arrayString) {
        List<String> list = new ArrayList<String>();
        if(arrayString != null) {
            String[] array = arrayString.split(",");
            int length = array.length;
            for (int i = 0; i < length; i++) {
                list.add(array[i]);
            }
        }
        return list;
    }

    public static List<Integer>  setIntList(String arrayString) {
        List<Integer> list = new ArrayList<Integer>();
        if(arrayString != null) {
            String[] array = arrayString.split(",");
            int length = array.length;
            for (int i = 0; i < length; i++) {
                list.add(Integer.parseInt(array[i]));
            }
        }
        return list;
    }


    public static String delete(Object object) {

        String className = object.getClass().getSimpleName();
        StringBuilder sql = new StringBuilder("delete from ");
        sql.append(className+" ");
        sql.append("where id=");
        String str= (String)getClassValueObj(object).get("id");
        sql.append(str);
        return sql.toString();


    }



    public static Map<String, Object> getClassValueObj(Object object) {


        Field[] fields = object.getClass().getDeclaredFields();
        Map<String, Object> paraMap = new HashMap<String, Object>();
        for (int i = 0; i < fields.length; i++) {
            try {
                //获得目标类的接入权限
                boolean isAccess = fields[i].isAccessible();
                if (!isAccess) fields[i].setAccessible(true);

                //System.out.println(fields[i].getName()+"----"+fields[i].getGenericType().toString());
                if(fields[i].getGenericType().toString().equals("class java.lang.String")){
                    paraMap.put(fields[i].getName(), fields[i].get(object)==null?null:"'"+fields[i].get(object)+"'");

                }
                else if(fields[i].getGenericType().toString().equals(
                        "class java.util.Date")){
                    if(fields[i].get(object)==null) {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        paraMap.put(fields[i].getName(), "'" + sdf.format(new Date().getTime()) + "'");
                    }
                    else {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        paraMap.put(fields[i].getName(), "'" + sdf.format(fields[i].get(object)) + "'");
                    }
                }
                //如果属性名为id，即在数据库中自增长，不用插入id字段入数据库，进行空操作
                else if(fields[i].getName().equals("id")){

                }
                else if(fields[i].getGenericType().toString().equals(
                        "java.util.List<java.lang.String>")){

                }
                else {
                    paraMap.put(fields[i].getName(), fields[i].get(object));
                }


                if (!isAccess) fields[i].setAccessible(false);

            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        //System.out.println(paraMap);
        return paraMap;
    }


    public static Map<String, Object> objectToMap(Object obj){
        Map<String, Object> map = new HashMap<String, Object>();
        Class<?> clazz = obj.getClass();
        try {
            System.out.println(clazz);
            for(Field field : clazz.getDeclaredFields()) {
                field.setAccessible(true);
                String fieldName = field.getName();
                Object value = field.get(obj);
                map.put(fieldName, value);
            }

        }catch(IllegalAccessException e){
            return null;
        }
        return map;
     }
}
