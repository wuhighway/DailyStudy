package com.highway.study.hardware;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/**
 * Created by JH
 * on 2017/3/13.
 */

public class ShakeListener implements SensorEventListener {
    /** TAG.*/
    private static final String TAG = "ShakeListener";
    /**
     * 摇一摇时最小震动间隔
     */
    private static final int MIN_SHAKE_INTERVAL_MILLS = 900;
    /**
     * 将速度单位转换成米/秒
     */
    private static final int YI_WAN = 10000;

    /** 速度阈值，当摇晃速度达到这值后产生作用.*/
    private static int mShakeSpeed;
    /** 两次检测的时间间隔.*/
    private static int mIntervalTime;
    /** 传感器管理器.*/
    private SensorManager sensorManager;
    /** 传感器.*/
    private Sensor sensor;
    /** 重力感应监听器.*/
    private OnShakeListener onShakeListener;
    /** 上下文.*/
    private Context mContext;
    /** 手机上一个位置时重力感应坐标.*/
    private float lastX;
    /** 手机上一个位置时重力感应坐标.*/
    private float lastY;
    /** 手机上一个位置时重力感应坐标.*/
    private float lastZ;
    /** 上次检测时间.*/
    private long lastUpdateTime;
    /** 记录上一次震动的时间.*/
    private long lastShakeTime;
    /**
     * constructor
     * @param c context
     */
    public ShakeListener(Context c) {
        // 获得监听对象
        mContext = c;
        start();
        mShakeSpeed = 3000;
        mIntervalTime = 70;
    }

    /**
     * 开始
     */
    public void start() {
        // 获得传感器管理器
        sensorManager = (SensorManager) mContext
                .getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager != null) {
            // 获得重力传感器
            sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        }
        // 注册
        if (sensor != null) {
            sensorManager.registerListener(this, sensor,
                    SensorManager.SENSOR_DELAY_GAME);
        } else {
            //不支持摇一摇
//			throw new UnsupportedOperationException("Sensor not supported");
        }

    }

    /**
     * 停止检测
     */
    public void stop() {
        sensorManager.unregisterListener(this);
    }

    /**
     * 设置重力感应监听器
     * @param listener 重力感应监听器
     */
    public void setOnShakeListener(OnShakeListener listener) {
        onShakeListener = listener;
    }

    /**
     * 重力感应器感应获得变化数据
     * @param event sensorevent
     */
    public void onSensorChanged(SensorEvent event) {
//		if (DEBUG) {
//			Log.v(TAG, "onSensorChanged");
//		}
        // 现在检测时间
        long currentUpdateTime = System.currentTimeMillis();
        // 两次检测的时间间隔
        long timeInterval = currentUpdateTime - lastUpdateTime;
        // 判断是否达到了检测时间间隔
        if (timeInterval < mIntervalTime) {
            return;
        }
        // 现在的时间变成last时间
        lastUpdateTime = currentUpdateTime;
        // 获得x,y,z坐标
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];

        // 获得x,y,z的变化值
        float deltaX = x - lastX;
        float deltaY = y - lastY;
        float deltaZ = z - lastZ;

        // 将现在的坐标变成last坐标
        lastX = x;
        lastY = y;
        lastZ = z;
        double speed = Math.sqrt(deltaX * deltaX + deltaY * deltaY + deltaZ
                * deltaZ) / timeInterval * YI_WAN;
//        if (DEBUG) {
//            Log.v(TAG, "speed:" + speed);
//        }
        // 达到速度阀值，发出提示
        if (speed >= mShakeSpeed && null != onShakeListener) {
            if (lastShakeTime != 0 && System.currentTimeMillis() - lastShakeTime < MIN_SHAKE_INTERVAL_MILLS) {
                return;
            }
            onShakeListener.onShake();
            lastShakeTime = System.currentTimeMillis();
        }
    }

    /**
     * onAccuracyChanged
     * @param sor sensor
     * @param accuracy accuracy
     */
    public void onAccuracyChanged(Sensor sor, int accuracy) {
        //DO NOTHING
    }

    /**
     * 摇晃监听接口
     * @author ldq
     * @since 2013
     */
    public interface OnShakeListener {
        /**
         * 摇一摇触发
         */
        void onShake();
    }

    /**
     * remove shake listener from system
     */
    public void removeListener() {
        if (sensor != null) {
            sensorManager.unregisterListener(this, sensor);
        }
        onShakeListener = null;
    }
}
