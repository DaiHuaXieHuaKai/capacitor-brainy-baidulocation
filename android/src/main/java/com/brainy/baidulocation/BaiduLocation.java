package com.brainy.baidulocation;

import android.Manifest;
import android.util.Log;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.getcapacitor.JSObject;
import com.getcapacitor.NativePlugin;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;

@NativePlugin(
        permissions = {
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
        }
)
public class BaiduLocation extends Plugin {

    public LocationClient locationClient = null;

    private BDAbstractLocationListener bdAbstractLocationListener = null;

    private static final String INFO_TAG = BaiduLocation.class.getName();

    @PluginMethod()
    public void getCurrentPosition(PluginCall call) {
        Log.i(INFO_TAG, "Ready get location");
        if (hasRequiredPermissions()) {
            getPosition(call);
        } else {
            Log.i(INFO_TAG, "No Permission,start request Permission");
            pluginRequestAllPermissions();
        }
    }

    private void getPosition(final PluginCall call) {

        bdAbstractLocationListener = new BDAbstractLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation bdLocation) {
                double latitude = bdLocation.getLatitude();    //获取纬度信息
                double longitude = bdLocation.getLongitude();    //获取经度信息
                float radius = bdLocation.getRadius();    //获取定位精度，默认值为0.0f
                String coorType = bdLocation.getCoorType();//获取经纬度坐标类型，以LocationClientOption中设置过的坐标类型为准
                int errorCode = bdLocation.getLocType();//获取定位类型、定位错误返回码，具体信息可参照类参考中BDLocation类中的说明
                JSObject ret = new JSObject();
                ret.put("latitude", latitude);
                ret.put("longitude", longitude);
                ret.put("radius", radius);
                ret.put("coorType", coorType);
                ret.put("errorCode", errorCode);
                call.success(ret);
            }
        };

        //声明LocationClient类
        locationClient = new LocationClient(getContext());
        setOptions();
        //注册监听函数
        locationClient.registerLocationListener(bdAbstractLocationListener);
        locationClient.start();
    }

    private void setOptions() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        //可选，设置定位模式，默认高精度
        //LocationMode.Hight_Accuracy：高精度；
        //LocationMode. Battery_Saving：低功耗；
        //LocationMode. Device_Sensors：仅使用设备；
        option.setCoorType("bd09ll");
        //可选，设置返回经纬度坐标类型，默认GCJ02
        //GCJ02：国测局坐标；
        //BD09ll：百度经纬度坐标；
        //BD09：百度墨卡托坐标；
        //海外地区定位，无需设置坐标类型，统一返回WGS84类型坐标
        option.setScanSpan(1000);
        //可选，设置发起定位请求的间隔，int类型，单位ms
        //如果设置为0，则代表单次定位，即仅定位一次，默认为0
        //如果设置非0，需设置1000ms以上才有效
        option.setOpenGps(true);
        //可选，设置是否使用gps，默认false
        //使用高精度和仅用设备两种定位模式的，参数必须设置为true
        option.setLocationNotify(true);
        //可选，设置是否当GPS有效时按照1S/1次频率输出GPS结果，默认false
        option.setIgnoreKillProcess(false);
        //可选，定位SDK内部是一个service，并放到了独立进程。
        //设置是否在stop的时候杀死这个进程，默认（建议）不杀死，即setIgnoreKillProcess(true)
        option.SetIgnoreCacheException(false);
        //可选，设置是否收集Crash信息，默认收集，即参数为false
        option.setWifiCacheTimeOut(5 * 60 * 1000);
        //可选，V7.2版本新增能力
        //如果设置了该接口，首次启动定位时，会先判断当前Wi-Fi是否超出有效期，若超出有效期，会先重新扫描Wi-Fi，然后定位
        option.setEnableSimulateGps(false);
        //可选，设置是否需要过滤GPS仿真结果，默认需要，即参数为false

        locationClient.setLocOption(option);
    }

}

