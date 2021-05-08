package com.company.iendoplus.utils;

/**
 * Created by Lovelin on 2019/3/13
 * <p>
 * Describe:
 */

import android.content.Context;
import android.content.SharedPreferences;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * SharePreference  工具类
 */
public class SharePreferenceUtil {
    /**
     * 保存在手机里面的文件名
     */
    public static final String FILE_NAME = "share_data";
    public static final String UID = "uid";
    public static final String UserId = "userid";       //当前用户id
    //boolean 类型 ,数据库用户表是否创建了？
    public static final String Flag_User_IsCreator = "Flag_User_IsCreator";


    /**
     * http格式下图片和视频访问路径
     * http://192.168.66.42:8008/Images/3915/thumb/001.jpg    //模糊图
     * http://192.168.66.42:8008/Images/3915/001.jpg   //高清图
     * http://192.168.66.42:8008/Videos/3874/444420210318164613550.mp4
     */

    public static final String Current_BaseUrl = "BaseUrl";     //末尾不加斜杠  http://192.168.66.42:8008




    /**
     * 引导界面
     */
    //引导页
    public static final String is_First_in = "is_first_in";                    //是否第一次登入   默认是第一次登入  true
    public static final String is_login = "is_login";                          //是否已经登入   默认是未登录     false
    /**
     * 登录界面
     */
    //这个是登入模式
    public static final String isOnline = "online";                            //默认在线登录
    public static final String Choose_Section = "section";                     //耳鼻喉科,妇科,泌尿科
    public static final String is_Remember_Password = "is_remember_password";  //默认不记住
    public static final String Current_Username = "Admin";                     // 虽然是不同字段表示，但是"admin" 如果同时存储username和password  后者会覆盖前者
    public static final String Current_Password = "";
    public static final String Current_UserType = "11";                             //0超级管理员     1普通用户
    public static final String Current_Can_Delete = "4";                             //当前用户是否具有删除的权限
    public static final String Current_Case_Num = "3";                             //3耳鼻喉科,4妇科,6泌尿科,存的是数字


    /**
     * 添加服务器界面
     */

    public static final String Current_DeviceType = "一体机";
    public static final String Current_IP = "ip";
    public static final String Current_Port = "port";
    public static final String Current_Host = "host";  // 加了---/---的

    //用户登入次数
    public static final String Current_LoginOnlineTime = "times";
    //用户登入时间
    public static final String Current_CreateTime = "online_data";
    //上次登录时间
    public static final String Current_LastOnlineTime = "last_online_data";

    /**
     * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
     *
     * @param context
     * @param key
     * @param object
     */
    public static void put(Context context, String key, Object object) {

        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        if (object instanceof String) {
            editor.putString(key, (String) object);
        } else if (object instanceof Integer) {
            editor.putInt(key, (Integer) object);
        } else if (object instanceof Boolean) {
            editor.putBoolean(key, (Boolean) object);
        } else if (object instanceof Float) {
            editor.putFloat(key, (Float) object);
        } else if (object instanceof Long) {
            editor.putLong(key, (Long) object);
        } else {
            editor.putString(key, object.toString());
        }
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
     *
     * @param context
     * @param key
     * @param defaultObject
     * @return
     */
    public static Object get(Context context, String key, Object defaultObject) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);

        if (defaultObject instanceof String) {
            return sp.getString(key, (String) defaultObject);
        } else if (defaultObject instanceof Integer) {
            return sp.getInt(key, (Integer) defaultObject);
        } else if (defaultObject instanceof Boolean) {
            return sp.getBoolean(key, (Boolean) defaultObject);
        } else if (defaultObject instanceof Float) {
            return sp.getFloat(key, (Float) defaultObject);
        } else if (defaultObject instanceof Long) {
            return sp.getLong(key, (Long) defaultObject);
        }

        return null;
    }

    /**
     * 移除某个key值已经对应的值
     *
     * @param context
     * @param key
     */
    public static void remove(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 清除所有数据
     *
     * @param context
     */
    public static void clear(Context context) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 查询某个key是否已经存在
     *
     * @param context
     * @param key
     * @return
     */
    public static boolean contains(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        return sp.contains(key);
    }

    /**
     * 返回所有的键值对
     *
     * @param context
     * @return
     */
    public static Map<String, ?> getAll(Context context) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        return sp.getAll();
    }

    /**
     * 创建一个解决SharedPreferencesCompat.apply方法的一个兼容类
     *
     * @author zhy
     */
    private static class SharedPreferencesCompat {
        private static final Method sApplyMethod = findApplyMethod();

        /**
         * 反射查找apply的方法
         *
         * @return
         */
        @SuppressWarnings({"unchecked", "rawtypes"})
        private static Method findApplyMethod() {
            try {
                Class clz = SharedPreferences.Editor.class;
                return clz.getMethod("apply");
            } catch (NoSuchMethodException e) {
            }

            return null;
        }

        /**
         * 如果找到则使用apply执行，否则使用commit
         *
         * @param editor
         */
        public static void apply(SharedPreferences.Editor editor) {
            try {
                if (sApplyMethod != null) {
                    sApplyMethod.invoke(editor);
                    return;
                }
            } catch (IllegalArgumentException e) {
            } catch (IllegalAccessException e) {
            } catch (InvocationTargetException e) {
            }
            editor.commit();
        }
    }


}
