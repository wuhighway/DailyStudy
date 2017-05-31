package com.highway.study.util;

import android.text.TextUtils;
import android.util.Log;

import com.highway.study.BuildConfig;

//import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * @author JH
 * @date 2017/5/31
 */


public class Logger {
    /**
     * 是否需要打印bug
     */
    private static boolean DEBUG = BuildConfig.DEBUG;

    private static final String TAG = "TAG";

    private static final int V = 0x1;
    private static final int D = 0x2;
    private static final int I = 0x3;
    private static final int W = 0x4;
    private static final int E = 0x5;
    private static final int A = 0x6;
    private static final int JSON = 0x7;
    private static final int JSON_INDENT = 4;
    /**
     * 行号分隔符
     */
    private static final String LINE_SEPARATOR = System.getProperty("line.separator");

    // ********** 下面四个是默认tag的方法 **********/

    public static void i(String msg) {
        if (DEBUG) {
            printLog(I, TAG, msg);
        }
    }

    public static void i(String tag, String msg, Object... args) {
        if (DEBUG) {
            printLog(I, tag, msg);
        }
    }

    public static void d(String msg) {
        if (DEBUG) {
            printLog(D, TAG, msg);
        }
    }

    public static void e(String msg) {
        if (DEBUG) {
            printLog(E, TAG, msg);
        }
    }

    public static void v(String msg) {
        if (DEBUG) {
            printLog(V, TAG, msg);
        }
    }

    public static void w(String msg) {
        if (DEBUG) {
            printLog(W, TAG, msg);
        }
    }

    // ********** 下面是传入自定义tag的方法 **********/

    public static void i(String tag, String msg) {
        if (DEBUG) {
            printLog(I, tag, msg);
        }
    }

    public static void d(String tag, String msg) {
        if (DEBUG) {
            printLog(D, tag, msg);
        }
    }

    public static void e(String tag, String msg) {
        if (DEBUG) {
            printLog(E, tag, msg);
        }
    }

    public static void v(String tag, String msg) {
        if (DEBUG) {
            printLog(V, tag, msg);
        }
    }

    public static void w(String tag, String msg) {
        if (DEBUG) {
            printLog(W, tag, msg);
        }
    }

    public static void i(String tag, Throwable throwable) {
        if (DEBUG) {
            printLog(I, tag, throwable.toString());
        }
    }

    public static void d(String tag, Throwable throwable) {
        if (DEBUG) {
            printLog(D, tag, throwable.toString());
        }
    }

    public static void e(String tag, Throwable throwable) {
        if (DEBUG) {
            printLog(E, tag, throwable.toString());
        }
    }

    public static void v(String tag, Throwable throwable) {
        if (DEBUG) {
            printLog(V, tag, throwable.toString());
        }
    }

    public static void w(String tag, Throwable throwable) {
        if (DEBUG) {
            printLog(W, tag, throwable.toString());
        }
    }

    public static void json(String jsonFormat) {
        printLog(JSON, TAG, jsonFormat);
    }

    public static void json(String tag, String jsonFormat) {
        printLog(JSON, tag, jsonFormat);
    }

//    public static void params(String url, List<NameValuePair> list) {
//        params(TAG, url, list);
//    }
//
//    public static void params(String tag, String url, List<NameValuePair> list) {
//        i(tag, "请求参数：" + list.toString());
//        StringBuilder builder = new StringBuilder(url);
//        builder.append("?");
//        for (NameValuePair pair : list) {
//            builder.append(pair.getName()).append("=").append(pair.getValue()).append("&");
//        }
//        builder.deleteCharAt(builder.length() - 1);
//        i(tag, "url = " + builder.toString());
//    }

    private static void printLog(int type, String tagStr, Object objectMsg) {
        String msg;
        if (!DEBUG) {
            return;
        }
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        int index = 4;
        String className = stackTraceElements[index].getFileName();
        String methodName = stackTraceElements[index].getMethodName();
        int lineNumber = stackTraceElements[index].getLineNumber();
        String tag = (tagStr == null ? className : tagStr);
//        methodName = methodName.substring(0, 1).toUpperCase() + methodName.substring(1);
        StringBuilder builder = new StringBuilder();
        builder.append("[ (").append(className).append(":").append(lineNumber).append(")#").append(methodName).append(" ]");
        builder.append("\n");
        if (objectMsg == null) {
            msg = "log with null object";
        } else {
            msg = objectMsg.toString();
        }
        if (msg != null && type != JSON) {
            builder.append(msg);
        }
        String logStr = builder.toString();
        switch (type) {
            case V:
                Log.v(tag, logStr);
                break;
            case D:
                Log.d(tag, logStr);
                break;
            case I:
                Log.i(tag, logStr);
                break;
            case W:
                Log.w(tag, logStr);
                break;
            case E:
                Log.e(tag, logStr);
                break;
            case A:
                Log.d(tag, logStr);
                break;
            case JSON: {
                if (TextUtils.isEmpty(msg)) {
                    Log.d(tag, "Empty or null json content");
                    return;
                }
                String message = null;
                try {
                    if (msg.startsWith("{")) {
                        JSONObject jsonObject = new JSONObject(msg);
                        message = jsonObject.toString(JSON_INDENT);
                    } else if (msg.startsWith("[")) {
                        JSONArray jsonArray = new JSONArray(msg);
                        message = jsonArray.toString(JSON_INDENT);
                    }
                } catch (JSONException e) {
                    e(tag, e.getCause().getMessage() + "\n" + msg);
                    return;
                }
                printLine(tag, true);
                message = logStr + LINE_SEPARATOR + message;
                String[] lines = message.split(LINE_SEPARATOR);
                StringBuilder jsonContent = new StringBuilder();
                for (String line : lines) {
                    jsonContent.append("|| ").append(line).append(LINE_SEPARATOR);
                }
                Log.d(tag, jsonContent.toString());
                printLine(tag, false);

            }
            break;
            default:
                break;
        }
    }

    private static void printLine(String tag, boolean isTop) {
        if (isTop) {
            Log.d(tag, "╔═══════════════════════════════════════════════════════════════════════════════════════");
        } else {
            Log.d(tag, "╚═══════════════════════════════════════════════════════════════════════════════════════");
        }
    }
}
