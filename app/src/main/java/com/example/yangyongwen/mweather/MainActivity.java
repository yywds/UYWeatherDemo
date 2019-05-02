package com.example.yangyongwen.mweather;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.SDKInitializer;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Administrator on 2018/6/23.
 */

public class MainActivity extends Activity implements View.OnClickListener {
    private String updateCityCode;
    TodayWeather todayWeather = null;
    //定义一个ImageView的变量，将这个变量与布局中表示更新的ImageView绑定
    private ImageView UpdateBtn;

    //设置选择城市ImageView变量，并绑定对应组件，设置监听器和点击动作
    private ImageView SelectCityBtn;

    //今日天气
    private TextView cityT, timeT, humidityT, weekT, pmDataT, pmQualityT,
            temperatureT, climateT, windT, cityNameT;

    //未来15天天气
    private TextView  week1T,week2T,week3T,week4T,week5T,week6T,
                       climate1T, climate2T,climate3T,climate4T,climate5T,climate6T,
                       temperature1T,temperature2T, temperature3T,temperature4T,temperature5T,temperature6T,
                       wind1T,wind2T, wind3T,wind4T,wind5T,wind6T;

    //定义ImageView变量，并绑定组件，设置更新动作
    private ImageView weatherStateImg,pmStateImg;
   //更新未来15天的天气图片
    private ImageView weatherStateImg3,weatherStateImg4,weatherStateImg5,weatherStateImg6,weatherStateImg7,
                       weatherStateImg8;

    private ImageView locatemap;

    private ImageView shareBtn;

    private LocationManager lm;//【位置管理】

    //用handler启动更新
    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message message) {
            switch (message.what) {
                case 1:
                    updateTodayWeather((TodayWeather) message.obj);
                    break;
                default:
                    break;
            }
        }

    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        ////此方法要再setContentView方法之前实现
        SDKInitializer.initialize(getApplicationContext());


        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        //添加点击监听
        UpdateBtn = (ImageView) findViewById(R.id.title_city_update);
        UpdateBtn.setOnClickListener(this);

        //添加点击监听
        SelectCityBtn = (ImageView) findViewById(R.id.title_city_manager);
        SelectCityBtn.setOnClickListener(this);

        //添加点击监听
        locatemap = (ImageView) findViewById(R.id.title_city_locate);
        locatemap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Locatemap.class);
                startActivity(intent);
            }
        });

        //添加监听
        shareBtn = (ImageView) findViewById(R.id.title_city_share);
        shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Share.class);
                startActivity(intent);
            }
        });

        //在onCreate中调用initView()
        initView();

//        //得到系统的位置服务，判断GPS是否激活
//        lm=(LocationManager) getSystemService(LOCATION_SERVICE);
//        boolean ok=lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
//
//        if(ok){
//
//            Log.d("MWEATHER","GPS已开启！");
//            Toast.makeText(MainActivity.this,"GPS已开启！",Toast.LENGTH_LONG).show();
//        }else{
//            Toast.makeText(this, "系统检测到未开启GPS定位服务", 1).show();
//            Intent intent=new Intent();
//            intent.setAction(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
//            startActivity(intent);
//        }

        updateCityCode =getIntent().getStringExtra("citycode");
        getWeatherDatafromNet(updateCityCode);
        //updateCityCode = getIntent().getStringExtra("citycode");
//         if (updateCityCode != "-1") {
//             getWeatherDatafromNet(updateCityCode);
//         }

        //检查网络连接状态
        if (CheckNet.getNetState(this) == CheckNet.NET_NONE)
        {
            Log.d("MWEATHER","网络不通");
            Toast.makeText(MainActivity.this,"网络不通",Toast.LENGTH_LONG).show();
        }else
        {
            Log.d("MWEATHER", "网络OK");
            Toast.makeText(MainActivity.this,"网络OK",Toast.LENGTH_LONG).show();
            //在MainActivity的onCreate方法中调用该方法
            //getWeatherDatafromNet("101010100");
        }
    }

    @Override
    //添加点击后的响应动作
    public void onClick(View v) {
        if (v.getId() == R.id.title_city_update)
        {
            //从SharePreference存储最近天气文件中获取最近选择的城市天气数据
            SharedPreferences mySharePre = getSharedPreferences("CityCodePreference",Activity.MODE_PRIVATE);
            String sharecode = mySharePre.getString("citycode","");
            if (!sharecode.equals(""))
            {
                Log.d("sharecode",sharecode);
                getWeatherDatafromNet(sharecode);
            }else {
                getWeatherDatafromNet("101010100");
            }

        }
        if (v.getId() == R.id.title_city_manager)
        {
            Intent intent = new Intent(this,SelectCity.class);
            startActivity(intent);
        }
    }

    //借助HttpUrlConnection（java.net.HttpUrlConnection）,获取Url网页上的数据
    public void getWeatherDatafromNet(String cityCode) {
        final String address = "http://wthrcdn.etouch.cn/WeatherApi?citykey="+cityCode;
        Log.d("Address:", address);
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection urlConnection = null;
                try {
                    URL url = new URL(address);
                    urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("GET");
                    urlConnection.setConnectTimeout(8000);
                    urlConnection.setReadTimeout(8000);
                    InputStream in = urlConnection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    StringBuffer sb = new StringBuffer();
                    String str;
                    while((str=reader.readLine())!=null)
                    {
                        sb.append(str);
                        Log.d("date from url",str);
                    }
                    String response = sb.toString();
                    Log.d("response",response);
                    todayWeather = parseXML(response);
                    if (todayWeather != null)
                    {
                        Message message = new Message();
                        message.what = 1;
                        message.obj = todayWeather;
                        mHandler.sendMessage(message);
                    }
                    //在获取网页数据之后，调用这个函数
                   // parseXML(response);
                }catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    //5.通过xmlPullParser解析xml数据
    //修改parseXML(String xmlData)函数：将返回值类型改为TodayWeather，函数中定义一个TodayWeather变量，并边解析边赋值
    //未来天气
    private TodayWeather parseXML(String xmlData) {
        TodayWeather todayWeather = null;

        int fengliCount = 0;
        int fengxiangCount = 0;
        int dateCount = 0;
        int highCount = 0;
        int lowCount = 0;
        int typeCount = 0;

        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xmlPullParser = factory.newPullParser();
            xmlPullParser.setInput(new StringReader(xmlData));

            int eventType = xmlPullParser.getEventType();
            Log.d("MWeater","start parse xml");

            while(eventType!=xmlPullParser.END_DOCUMENT)
            {
                switch (eventType)
                {
                    //文档开始位置
                    case XmlPullParser.START_DOCUMENT:
                        Log.d("parse","start doc");
                        break;
                    //标签元素开始位置
                    case XmlPullParser.START_TAG:
                        if(xmlPullParser.getName().equals("resp"))
                        {
                            todayWeather = new TodayWeather();
                        }
                        if(todayWeather!=null) {
                            if (xmlPullParser.getName().equals("city")) {
                                eventType = xmlPullParser.next();
                                Log.d("city", xmlPullParser.getText());
                                todayWeather.setCity(xmlPullParser.getText());
                            } else if (xmlPullParser.getName().equals("updatetime")) {
                                eventType = xmlPullParser.next();
                                Log.d("updatetime", xmlPullParser.getText());
                                todayWeather.setUpdatetime(xmlPullParser.getText());
                            } else if (xmlPullParser.getName().equals("wendu")) {
                                eventType = xmlPullParser.next();
                                Log.d("wendu", xmlPullParser.getText());
                                todayWeather.setWendu(xmlPullParser.getText());
                            } else if (xmlPullParser.getName().equals("fengli") && fengliCount == 0) {
                                eventType = xmlPullParser.next();
                                Log.d("fengli", xmlPullParser.getText());
                                todayWeather.setFengli(xmlPullParser.getText());
                                fengliCount++;
                            } else if (xmlPullParser.getName().equals("shidu")) {
                                eventType = xmlPullParser.next();
                                Log.d("shidu", xmlPullParser.getText());
                                todayWeather.setShidu(xmlPullParser.getText());
                            } else if (xmlPullParser.getName().equals("fengxiang") && fengxiangCount == 0) {
                                eventType = xmlPullParser.next();
                                Log.d("fengxiang", xmlPullParser.getText());
                                todayWeather.setFengxiang(xmlPullParser.getText());
                                fengxiangCount++;
                            } else if (xmlPullParser.getName().equals("pm25")) {
                                eventType = xmlPullParser.next();
                                Log.d("pm25", xmlPullParser.getText());
                                todayWeather.setPm25(xmlPullParser.getText());
                            } else if (xmlPullParser.getName().equals("quality")) {
                                eventType = xmlPullParser.next();
                                Log.d("quelity", xmlPullParser.getText());
                                todayWeather.setQuality(xmlPullParser.getText());
                            } else if (xmlPullParser.getName().equals("date") && dateCount == 0) {
                                eventType = xmlPullParser.next();
                                Log.d("date", xmlPullParser.getText());
                                todayWeather.setDate(xmlPullParser.getText());
                                dateCount++;
                            } else if (xmlPullParser.getName().equals("high") && highCount == 0) {
                                eventType = xmlPullParser.next();
                                Log.d("high", xmlPullParser.getText());
                                todayWeather.setHigh(xmlPullParser.getText());
                                highCount++;
                            } else if (xmlPullParser.getName().equals("low") && lowCount == 0) {
                                eventType = xmlPullParser.next();
                                Log.d("low", xmlPullParser.getText());
                                todayWeather.setLow(xmlPullParser.getText());
                                lowCount++;
                            } else if (xmlPullParser.getName().equals("type") && typeCount == 0) {
                                eventType = xmlPullParser.next();
                                Log.d("type", xmlPullParser.getText());
                                todayWeather.setType(xmlPullParser.getText());
                                typeCount++;
                            }
                            //第二天 天气
                            else if (xmlPullParser.getName().equals("date") && dateCount == 1) {
                                eventType = xmlPullParser.next();
                                Log.d("date1", xmlPullParser.getText());
                                todayWeather.setDate1(xmlPullParser.getText());
                                dateCount++;
                            }else if (xmlPullParser.getName().equals("low") && lowCount == 1) {
                                eventType = xmlPullParser.next();
                                Log.d("low1", xmlPullParser.getText());
                                todayWeather.setLow1(xmlPullParser.getText());
                                lowCount++;
                            }else if (xmlPullParser.getName().equals("high") && highCount == 1) {
                                eventType = xmlPullParser.next();
                                Log.d("high1", xmlPullParser.getText());
                                todayWeather.setHigh1(xmlPullParser.getText());
                                highCount++;
                            }else if (xmlPullParser.getName().equals("type") && typeCount == 1) {
                                eventType = xmlPullParser.next();
                                Log.d("type1", xmlPullParser.getText());
                                todayWeather.setType1(xmlPullParser.getText());
                                typeCount++;
                            }else if (xmlPullParser.getName().equals("fengli") && fengliCount == 1) {
                                eventType = xmlPullParser.next();
                                Log.d("fengli1", xmlPullParser.getText());
                                todayWeather.setFengli1(xmlPullParser.getText());
                                fengliCount++;
                            }

                            //第三天 天气
                            else if (xmlPullParser.getName().equals("date") && dateCount == 2) {
                                eventType = xmlPullParser.next();
                                Log.d("date2", xmlPullParser.getText());
                                todayWeather.setDate2(xmlPullParser.getText());
                                dateCount++;
                            }else if (xmlPullParser.getName().equals("low") && lowCount == 2) {
                                eventType = xmlPullParser.next();
                                Log.d("low2", xmlPullParser.getText());
                                todayWeather.setLow2(xmlPullParser.getText());
                                lowCount++;
                            }else if (xmlPullParser.getName().equals("high") && highCount == 2) {
                                eventType = xmlPullParser.next();
                                Log.d("high2", xmlPullParser.getText());
                                todayWeather.setHigh2(xmlPullParser.getText());
                                highCount++;
                            }else if (xmlPullParser.getName().equals("type") && typeCount == 2) {
                                eventType = xmlPullParser.next();
                                Log.d("type2", xmlPullParser.getText());
                                todayWeather.setType2(xmlPullParser.getText());
                                typeCount++;
                            }else if (xmlPullParser.getName().equals("fengli") && fengliCount == 2) {
                                eventType = xmlPullParser.next();
                                Log.d("fengli2", xmlPullParser.getText());
                                todayWeather.setFengli2(xmlPullParser.getText());
                                fengliCount++;
                            }

                            //第四天 天气
                            else if (xmlPullParser.getName().equals("date") && dateCount == 3) {
                                eventType = xmlPullParser.next();
                                Log.d("date3", xmlPullParser.getText());
                                todayWeather.setDate3(xmlPullParser.getText());
                                dateCount++;
                            }else if (xmlPullParser.getName().equals("low") && lowCount == 3) {
                                eventType = xmlPullParser.next();
                                Log.d("low3", xmlPullParser.getText());
                                todayWeather.setLow3(xmlPullParser.getText());
                                lowCount++;
                            }else if (xmlPullParser.getName().equals("high") && highCount == 3) {
                                eventType = xmlPullParser.next();
                                Log.d("high3", xmlPullParser.getText());
                                todayWeather.setHigh3(xmlPullParser.getText());
                                highCount++;
                            }else if (xmlPullParser.getName().equals("type") && typeCount == 3) {
                                eventType = xmlPullParser.next();
                                Log.d("type3", xmlPullParser.getText());
                                todayWeather.setType3(xmlPullParser.getText());
                                typeCount++;
                            }else if (xmlPullParser.getName().equals("fengli") && fengliCount == 3) {
                                eventType = xmlPullParser.next();
                                Log.d("fengli3", xmlPullParser.getText());
                                todayWeather.setFengli3(xmlPullParser.getText());
                                fengliCount++;
                            }

                            //第五天天气
                            else if (xmlPullParser.getName().equals("date") && dateCount == 4) {
                                eventType = xmlPullParser.next();
                                Log.d("date4", xmlPullParser.getText());
                                todayWeather.setDate4(xmlPullParser.getText());
                                dateCount++;
                            }else if (xmlPullParser.getName().equals("low") && lowCount == 4) {
                                eventType = xmlPullParser.next();
                                Log.d("low4", xmlPullParser.getText());
                                todayWeather.setLow4(xmlPullParser.getText());
                                lowCount++;
                            }else if (xmlPullParser.getName().equals("high") && highCount == 4) {
                                eventType = xmlPullParser.next();
                                Log.d("high4", xmlPullParser.getText());
                                todayWeather.setHigh4(xmlPullParser.getText());
                                highCount++;
                            }else if (xmlPullParser.getName().equals("type") && typeCount == 4) {
                                eventType = xmlPullParser.next();
                                Log.d("type4", xmlPullParser.getText());
                                todayWeather.setType4(xmlPullParser.getText());
                                typeCount++;
                            }else if (xmlPullParser.getName().equals("fengli") && fengliCount == 4) {
                                eventType = xmlPullParser.next();
                                Log.d("fengli4", xmlPullParser.getText());
                                todayWeather.setFengli4(xmlPullParser.getText());
                                fengliCount++;
                            }
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        break;
                }
                eventType=xmlPullParser.next();
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return todayWeather;
    }

    //6.更新界面数据
    //编写initView()方法
    void initView() {
        //title
        cityNameT = (TextView)findViewById(R.id.title_city_name);

        //today weather
        cityT = (TextView)findViewById(R.id.todayinfo1_cityName);
        timeT = (TextView)findViewById(R.id.todayinfo1_updateTime);
        humidityT = (TextView)findViewById(R.id.todayinfo1_humidity);
        weekT = (TextView)findViewById(R.id.todayinfo2_week);
        pmDataT = (TextView)findViewById(R.id.todayinfo1_pm25);
        pmQualityT = (TextView)findViewById(R.id.todayinfo1_pm25status);
        temperatureT = (TextView)findViewById(R.id.todayinfo2_temperature);
        climateT = (TextView)findViewById(R.id.todayinfo2_weatherState);
        windT = (TextView)findViewById(R.id.todayinfo2_wind);

        weatherStateImg = (ImageView)findViewById(R.id.todayinfo2_weatherStatusImg);
        pmStateImg = (ImageView)findViewById(R.id.todayinfo1_pm25img);

        //更新未来15天的天气图片
        weatherStateImg3 = (ImageView)findViewById(R.id.todayinfo3_weatherStatusImg);
        weatherStateImg4 = (ImageView)findViewById(R.id.todayinfo4_weatherStatusImg);
        weatherStateImg5 = (ImageView)findViewById(R.id.todayinfo5_weatherStatusImg);
        weatherStateImg6 = (ImageView)findViewById(R.id.todayinfo6_weatherStatusImg);

        //明天
        week1T = (TextView)findViewById(R.id.todayinfo3_week);
        temperature1T = (TextView)findViewById(R.id.todayinfo3_temperature);
        climate1T = (TextView)findViewById(R.id.todayinfo3_weatherState);
        wind1T = (TextView)findViewById(R.id.todayinfo3_wind);

        //后天
        week2T = (TextView)findViewById(R.id.todayinfo4_week);
        temperature2T = (TextView)findViewById(R.id.todayinfo4_temperature);
        climate2T = (TextView)findViewById(R.id.todayinfo4_weatherState);
        wind2T = (TextView)findViewById(R.id.todayinfo4_wind);

        //大后天
        week3T = (TextView)findViewById(R.id.todayinfo5_week);
        temperature3T = (TextView)findViewById(R.id.todayinfo5_temperature);
        climate3T = (TextView)findViewById(R.id.todayinfo5_weatherState);
        wind3T = (TextView)findViewById(R.id.todayinfo5_wind);

        //第五天
        week4T = (TextView)findViewById(R.id.todayinfo6_week);
        temperature4T = (TextView)findViewById(R.id.todayinfo6_temperature);
        climate4T = (TextView)findViewById(R.id.todayinfo6_weatherState);
        wind4T = (TextView)findViewById(R.id.todayinfo6_wind);


        cityNameT.setText("N/A");
        cityT.setText("N/A");
        timeT.setText("N/A");
        humidityT.setText("N/A");
        weekT.setText("N/A");
        pmDataT.setText("N/A");
        pmQualityT.setText("N/A");
        temperatureT.setText("N/A");
        climateT.setText("N/A");
    }

    //编写函数updateTodayWeather(TodayWeather)更新组件
    void updateTodayWeather(TodayWeather todayWeather) {
        cityNameT.setText(todayWeather.getCity()+"天气");
        cityT.setText(todayWeather.getCity());
        timeT.setText("发布时间：" + todayWeather.getUpdatetime());
        humidityT.setText("湿度:"+todayWeather.getShidu());
        pmDataT.setText(todayWeather.getPm25());
        pmQualityT.setText(todayWeather.getQuality());
        climateT.setText("今日天气：" + todayWeather.getType());
        temperatureT.setText(todayWeather.getLow()+"~"+todayWeather.getHigh());
        windT.setText("风力:"+todayWeather.getFengli());
        weekT.setText(todayWeather.getDate());

        //第二天天气
        climate1T.setText("天气情况：" +todayWeather.getType1());
        temperature1T.setText(todayWeather.getLow1()+"~"+todayWeather.getHigh1());
        wind1T.setText("风力:"+todayWeather.getFengli1());
        week1T.setText(todayWeather.getDate1());

        //第三天天气
        climate2T.setText("天气情况："+ todayWeather.getType2());
        temperature2T.setText(todayWeather.getLow2()+"~"+todayWeather.getHigh2());
        wind2T.setText("风力:"+todayWeather.getFengli2());
        week2T.setText(todayWeather.getDate2());

        //第四天天气
        climate3T.setText("天气情况：" + todayWeather.getType3());
        temperature3T.setText(todayWeather.getLow3()+"~"+todayWeather.getHigh3());
        wind3T.setText("风力:"+todayWeather.getFengli3());
        week3T.setText(todayWeather.getDate3());

        //第五天天气
        climate4T.setText("天气情况：" + todayWeather.getType4());
        temperature4T.setText(todayWeather.getLow4()+"~"+todayWeather.getHigh4());
        wind4T.setText("风力:"+todayWeather.getFengli4());
        week4T.setText(todayWeather.getDate4());

        if(todayWeather.getPm25()!=null) {
            int pm25 = Integer.parseInt(todayWeather.getPm25());
            if (pm25 <= 50) {
                pmStateImg.setImageResource(R.drawable.biz_plugin_weather_0_50);
            } else if (pm25 >= 51 && pm25 <= 100) {
                pmStateImg.setImageResource(R.drawable.biz_plugin_weather_51_100);
            } else if (pm25 >= 101 && pm25 <= 150) {
                pmStateImg.setImageResource(R.drawable.biz_plugin_weather_101_150);
            } else if (pm25 >= 151 && pm25 <= 200) {
                pmStateImg.setImageResource(R.drawable.biz_plugin_weather_151_200);
            } else if (pm25 >= 201 && pm25 <= 300) {
                pmStateImg.setImageResource(R.drawable.biz_plugin_weather_201_300);
            }
        }
        if(todayWeather.getType()!=null) {
            Log.d("type", todayWeather.getType());
            switch (todayWeather.getType()) {
                case "晴":
                    weatherStateImg.setImageResource(R.drawable.biz_plugin_weather_qing);
                    break;
                case "阴":
                    weatherStateImg.setImageResource(R.drawable.biz_plugin_weather_yin);
                    break;
                case "雾":
                    weatherStateImg.setImageResource(R.drawable.biz_plugin_weather_wu);
                    break;
                case "多云":
                    weatherStateImg.setImageResource(R.drawable.biz_plugin_weather_duoyun);
                    break;
                case "小雨":
                    weatherStateImg.setImageResource(R.drawable.biz_plugin_weather_xiaoyu);
                    break;
                case "中雨":
                    weatherStateImg.setImageResource(R.drawable.biz_plugin_weather_zhongyu);
                    break;
                case "大雨":
                    weatherStateImg.setImageResource(R.drawable.biz_plugin_weather_dayu);
                    break;
                case "阵雨":
                    weatherStateImg.setImageResource(R.drawable.biz_plugin_weather_zhenyu);
                    break;
                case "雷阵雨":
                    weatherStateImg.setImageResource(R.drawable.biz_plugin_weather_leizhenyu);
                    break;
                case "雷阵雨加暴":
                    weatherStateImg.setImageResource(R.drawable.biz_plugin_weather_leizhenyubingbao);
                    break;
                case "暴雨":
                    weatherStateImg.setImageResource(R.drawable.biz_plugin_weather_baoyu);
                    break;
                case "大暴雨":
                    weatherStateImg.setImageResource(R.drawable.biz_plugin_weather_dabaoyu);
                    break;
                case "特大暴雨":
                    weatherStateImg.setImageResource(R.drawable.biz_plugin_weather_tedabaoyu);
                    break;
                case "阵雪":
                    weatherStateImg.setImageResource(R.drawable.biz_plugin_weather_zhenxue);
                    break;
                case "暴雪":
                    weatherStateImg.setImageResource(R.drawable.biz_plugin_weather_baoxue);
                    break;
                case "大雪":
                    weatherStateImg.setImageResource(R.drawable.biz_plugin_weather_daxue);
                    break;
                case "小雪":
                    weatherStateImg.setImageResource(R.drawable.biz_plugin_weather_xiaoxue);
                    break;
                case "雨夹雪":
                    weatherStateImg.setImageResource(R.drawable.biz_plugin_weather_yujiaxue);
                    break;
                case "中雪":
                    weatherStateImg.setImageResource(R.drawable.biz_plugin_weather_zhongxue);
                    break;
                case "沙尘暴":
                    weatherStateImg.setImageResource(R.drawable.biz_plugin_weather_shachenbao);
                    break;
                default:
                    break;
            }
        }

            //更新明天的天气图片
            if(todayWeather.getType1()!=null) {
                Log.d("type1", todayWeather.getType1());
                switch (todayWeather.getType1()) {
                    case "晴":
                        weatherStateImg3.setImageResource(R.drawable.biz_plugin_weather_qing);
                        break;
                    case "阴":
                        weatherStateImg3.setImageResource(R.drawable.biz_plugin_weather_yin);
                        break;
                    case "雾":
                        weatherStateImg3.setImageResource(R.drawable.biz_plugin_weather_wu);
                        break;
                    case "多云":
                        weatherStateImg3.setImageResource(R.drawable.biz_plugin_weather_duoyun);
                        break;
                    case "小雨":
                        weatherStateImg3.setImageResource(R.drawable.biz_plugin_weather_xiaoyu);
                        break;
                    case "中雨":
                        weatherStateImg3.setImageResource(R.drawable.biz_plugin_weather_zhongyu);
                        break;
                    case "大雨":
                        weatherStateImg3.setImageResource(R.drawable.biz_plugin_weather_dayu);
                        break;
                    case "阵雨":
                        weatherStateImg3.setImageResource(R.drawable.biz_plugin_weather_zhenyu);
                        break;
                    case "雷阵雨":
                        weatherStateImg3.setImageResource(R.drawable.biz_plugin_weather_leizhenyu);
                        break;
                    case "雷阵雨加暴":
                        weatherStateImg3.setImageResource(R.drawable.biz_plugin_weather_leizhenyubingbao);
                        break;
                    case "暴雨":
                        weatherStateImg3.setImageResource(R.drawable.biz_plugin_weather_baoyu);
                        break;
                    case "大暴雨":
                        weatherStateImg3.setImageResource(R.drawable.biz_plugin_weather_dabaoyu);
                        break;
                    case "特大暴雨":
                        weatherStateImg3.setImageResource(R.drawable.biz_plugin_weather_tedabaoyu);
                        break;
                    case "阵雪":
                        weatherStateImg3.setImageResource(R.drawable.biz_plugin_weather_zhenxue);
                        break;
                    case "暴雪":
                        weatherStateImg3.setImageResource(R.drawable.biz_plugin_weather_baoxue);
                        break;
                    case "大雪":
                        weatherStateImg3.setImageResource(R.drawable.biz_plugin_weather_daxue);
                        break;
                    case "小雪":
                        weatherStateImg3.setImageResource(R.drawable.biz_plugin_weather_xiaoxue);
                        break;
                    case "雨夹雪":
                        weatherStateImg3.setImageResource(R.drawable.biz_plugin_weather_yujiaxue);
                        break;
                    case "中雪":
                        weatherStateImg3.setImageResource(R.drawable.biz_plugin_weather_zhongxue);
                        break;
                    case "沙尘暴":
                        weatherStateImg3.setImageResource(R.drawable.biz_plugin_weather_shachenbao);
                        break;
                    default:
                        break;
                }
        }

        //更新后天的天气图片
        if(todayWeather.getType2()!=null) {
            Log.d("type2", todayWeather.getType2());
            switch (todayWeather.getType2()) {
                case "晴":
                    weatherStateImg4.setImageResource(R.drawable.biz_plugin_weather_qing);
                    break;
                case "阴":
                    weatherStateImg4.setImageResource(R.drawable.biz_plugin_weather_yin);
                    break;
                case "雾":
                    weatherStateImg4.setImageResource(R.drawable.biz_plugin_weather_wu);
                    break;
                case "多云":
                    weatherStateImg4.setImageResource(R.drawable.biz_plugin_weather_duoyun);
                    break;
                case "小雨":
                    weatherStateImg4.setImageResource(R.drawable.biz_plugin_weather_xiaoyu);
                    break;
                case "中雨":
                    weatherStateImg4.setImageResource(R.drawable.biz_plugin_weather_zhongyu);
                    break;
                case "大雨":
                    weatherStateImg4.setImageResource(R.drawable.biz_plugin_weather_dayu);
                    break;
                case "阵雨":
                    weatherStateImg4.setImageResource(R.drawable.biz_plugin_weather_zhenyu);
                    break;
                case "雷阵雨":
                    weatherStateImg4.setImageResource(R.drawable.biz_plugin_weather_leizhenyu);
                    break;
                case "雷阵雨加暴":
                    weatherStateImg4.setImageResource(R.drawable.biz_plugin_weather_leizhenyubingbao);
                    break;
                case "暴雨":
                    weatherStateImg4.setImageResource(R.drawable.biz_plugin_weather_baoyu);
                    break;
                case "大暴雨":
                    weatherStateImg4.setImageResource(R.drawable.biz_plugin_weather_dabaoyu);
                    break;
                case "特大暴雨":
                    weatherStateImg4.setImageResource(R.drawable.biz_plugin_weather_tedabaoyu);
                    break;
                case "阵雪":
                    weatherStateImg4.setImageResource(R.drawable.biz_plugin_weather_zhenxue);
                    break;
                case "暴雪":
                    weatherStateImg4.setImageResource(R.drawable.biz_plugin_weather_baoxue);
                    break;
                case "大雪":
                    weatherStateImg4.setImageResource(R.drawable.biz_plugin_weather_daxue);
                    break;
                case "小雪":
                    weatherStateImg4.setImageResource(R.drawable.biz_plugin_weather_xiaoxue);
                    break;
                case "雨夹雪":
                    weatherStateImg4.setImageResource(R.drawable.biz_plugin_weather_yujiaxue);
                    break;
                case "中雪":
                    weatherStateImg4.setImageResource(R.drawable.biz_plugin_weather_zhongxue);
                    break;
                case "沙尘暴":
                    weatherStateImg4.setImageResource(R.drawable.biz_plugin_weather_shachenbao);
                    break;
                default:
                    break;
            }
        }

        //更新大后天的天气图片
        if(todayWeather.getType3()!=null) {
            Log.d("type3", todayWeather.getType3());
            switch (todayWeather.getType3()) {
                case "晴":
                    weatherStateImg5.setImageResource(R.drawable.biz_plugin_weather_qing);
                    break;
                case "阴":
                    weatherStateImg5.setImageResource(R.drawable.biz_plugin_weather_yin);
                    break;
                case "雾":
                    weatherStateImg5.setImageResource(R.drawable.biz_plugin_weather_wu);
                    break;
                case "多云":
                    weatherStateImg5.setImageResource(R.drawable.biz_plugin_weather_duoyun);
                    break;
                case "小雨":
                    weatherStateImg5.setImageResource(R.drawable.biz_plugin_weather_xiaoyu);
                    break;
                case "中雨":
                    weatherStateImg5.setImageResource(R.drawable.biz_plugin_weather_zhongyu);
                    break;
                case "大雨":
                    weatherStateImg5.setImageResource(R.drawable.biz_plugin_weather_dayu);
                    break;
                case "阵雨":
                    weatherStateImg5.setImageResource(R.drawable.biz_plugin_weather_zhenyu);
                    break;
                case "雷阵雨":
                    weatherStateImg5.setImageResource(R.drawable.biz_plugin_weather_leizhenyu);
                    break;
                case "雷阵雨加暴":
                    weatherStateImg5.setImageResource(R.drawable.biz_plugin_weather_leizhenyubingbao);
                    break;
                case "暴雨":
                    weatherStateImg5.setImageResource(R.drawable.biz_plugin_weather_baoyu);
                    break;
                case "大暴雨":
                    weatherStateImg5.setImageResource(R.drawable.biz_plugin_weather_dabaoyu);
                    break;
                case "特大暴雨":
                    weatherStateImg5.setImageResource(R.drawable.biz_plugin_weather_tedabaoyu);
                    break;
                case "阵雪":
                    weatherStateImg5.setImageResource(R.drawable.biz_plugin_weather_zhenxue);
                    break;
                case "暴雪":
                    weatherStateImg5.setImageResource(R.drawable.biz_plugin_weather_baoxue);
                    break;
                case "大雪":
                    weatherStateImg5.setImageResource(R.drawable.biz_plugin_weather_daxue);
                    break;
                case "小雪":
                    weatherStateImg5.setImageResource(R.drawable.biz_plugin_weather_xiaoxue);
                    break;
                case "雨夹雪":
                    weatherStateImg5.setImageResource(R.drawable.biz_plugin_weather_yujiaxue);
                    break;
                case "中雪":
                    weatherStateImg5.setImageResource(R.drawable.biz_plugin_weather_zhongxue);
                    break;
                case "沙尘暴":
                    weatherStateImg5.setImageResource(R.drawable.biz_plugin_weather_shachenbao);
                    break;
                default:
                    break;
            }
        }

        //第五天天气
        if(todayWeather.getType4()!=null) {
            Log.d("type4", todayWeather.getType4());
            switch (todayWeather.getType4()) {
                case "晴":
                    weatherStateImg6.setImageResource(R.drawable.biz_plugin_weather_qing);
                    break;
                case "阴":
                    weatherStateImg6.setImageResource(R.drawable.biz_plugin_weather_yin);
                    break;
                case "雾":
                    weatherStateImg6.setImageResource(R.drawable.biz_plugin_weather_wu);
                    break;
                case "多云":
                    weatherStateImg6.setImageResource(R.drawable.biz_plugin_weather_duoyun);
                    break;
                case "小雨":
                    weatherStateImg6.setImageResource(R.drawable.biz_plugin_weather_xiaoyu);
                    break;
                case "中雨":
                    weatherStateImg6.setImageResource(R.drawable.biz_plugin_weather_zhongyu);
                    break;
                case "大雨":
                    weatherStateImg6.setImageResource(R.drawable.biz_plugin_weather_dayu);
                    break;
                case "阵雨":
                    weatherStateImg6.setImageResource(R.drawable.biz_plugin_weather_zhenyu);
                    break;
                case "雷阵雨":
                    weatherStateImg6.setImageResource(R.drawable.biz_plugin_weather_leizhenyu);
                    break;
                case "雷阵雨加暴":
                    weatherStateImg6.setImageResource(R.drawable.biz_plugin_weather_leizhenyubingbao);
                    break;
                case "暴雨":
                    weatherStateImg6.setImageResource(R.drawable.biz_plugin_weather_baoyu);
                    break;
                case "大暴雨":
                    weatherStateImg6.setImageResource(R.drawable.biz_plugin_weather_dabaoyu);
                    break;
                case "特大暴雨":
                    weatherStateImg6.setImageResource(R.drawable.biz_plugin_weather_tedabaoyu);
                    break;
                case "阵雪":
                    weatherStateImg6.setImageResource(R.drawable.biz_plugin_weather_zhenxue);
                    break;
                case "暴雪":
                    weatherStateImg6.setImageResource(R.drawable.biz_plugin_weather_baoxue);
                    break;
                case "大雪":
                    weatherStateImg6.setImageResource(R.drawable.biz_plugin_weather_daxue);
                    break;
                case "小雪":
                    weatherStateImg6.setImageResource(R.drawable.biz_plugin_weather_xiaoxue);
                    break;
                case "雨夹雪":
                    weatherStateImg6.setImageResource(R.drawable.biz_plugin_weather_yujiaxue);
                    break;
                case "中雪":
                    weatherStateImg6.setImageResource(R.drawable.biz_plugin_weather_zhongxue);
                    break;
                case "沙尘暴":
                    weatherStateImg6.setImageResource(R.drawable.biz_plugin_weather_shachenbao);
                    break;
                default:
                    break;
            }
        }
        Toast.makeText(MainActivity.this,"更新成功",Toast.LENGTH_SHORT).show();
    }

    @Override
    //返回手机桌面
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent home = new Intent(Intent.ACTION_MAIN);
            home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            home.addCategory(Intent.CATEGORY_HOME);
            startActivity(home);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    }

