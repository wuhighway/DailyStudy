package com.highway.annotationdemo;

import android.app.Activity;
import android.view.View;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author JH
 * @date 2017/6/29
 */


public class ViewInjectUtils {

    public static void inject(Activity activity) {
        injectContentView(activity);
        injectView(activity);
        injectEvent(activity);
    }

    private static void injectContentView(Activity activity) {
        Class<? extends Activity> clazz = activity.getClass();
        ContentView contentView = clazz.getAnnotation(ContentView.class);
        if (contentView != null) {
            int layoutid = contentView.value();
            try {
                Method method = clazz.getMethod("setContentView", int.class);
                method.invoke(activity, layoutid);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    private static void injectView(Activity activity) {
        Class<? extends Activity> clazz = activity.getClass();
        // 获取所有的成员变量
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            ViewInject inject = field.getAnnotation(ViewInject.class);
            if (inject != null) {
                int viewId = inject.value();
                View view = activity.findViewById(viewId);
                field.setAccessible(true);
                try {
                    field.set(activity, view);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void injectEvent(final Activity activity) {
        Class<? extends Activity> clazz = activity.getClass();
        Method[] methods = clazz.getDeclaredMethods();
        for (final Method method2 : methods) {
            OnClick onClick = method2.getAnnotation(OnClick.class);
            if (onClick != null) {
                int[] idValues = onClick.value();
                method2.setAccessible(true);
                Object listener = Proxy.newProxyInstance(View.OnClickListener.class.getClassLoader(),
                        new Class[]{View.OnClickListener.class}, new InvocationHandler() {
                            @Override
                            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                                return method2.invoke(activity, args);
                            }
                        });
                for (int id : idValues) {
                    View v = activity.findViewById(id);
                    try {
                        Method method1 = v.getClass().getMethod("setOnClickListener", View.OnClickListener.class);
                        method1.invoke(v, listener);
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
