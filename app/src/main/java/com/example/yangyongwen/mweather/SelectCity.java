package com.example.yangyongwen.mweather;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/6/23.
 */
//新建一个SelectCity继承Activity的类，并加载select_city布局
public class SelectCity extends Activity implements View.OnClickListener{
    // 定义一个ListView变量
    private ListView cityListLv;

    private EditText searchEt;
    private Button searchBtn;

    private List<City> mCityList;
    private MyApplication mApplication;
    private ArrayList<String> mArrayList;
    Button locateBtn;

    private String mLocCityCode;
    private MyApplication myApplication;

    //初始化updateCityCode
    private String updateCityCode = null;

    //热门城市
    private Button beijingBtn; //北京
    private Button shanghaiBtn;//上海
    private Button suzhouBtn;//苏州
    private Button zhengzhouBtn;//郑州
    private Button xianBtn; //西安
    private Button nanjingBtn;//南京
    private Button kunmingBtn;//昆明
    private Button chifengBtn;//赤峰
    private Button suizhouBtn;//随州
    private Button shaoyangBtn;//邵阳
    private Button linyiBtn;//临沂
    private Button lasaBtn;//拉萨
    private Button dalianBtn;//大连
    private Button chengduBtn;//成都
    private Button shenzhenBtn;//深圳
    private Button guangzhouBtn;//广州
    private Button shenyangBtn;//沈阳
    private Button wuhanBtn;//武汉
    private Button xiamenBtn;//厦门
    private Button tianjinBtn;//天津
    private Button ningboBtn;//宁波

    private ImageView backmainBtn;//返回主页

    private MainActivity mainActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_city);

        //点击返回按钮，返回到主界面
        backmainBtn = (ImageView) findViewById(R.id.backmain);
        backmainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();


            }

        });

        //北京市
        final Intent intent = new Intent(this,MainActivity.class);
        beijingBtn = (Button)findViewById(R.id.beijing);
        beijingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myApplication = (MyApplication) getApplication();
                mCityList = myApplication.getCityList();
                for (City city : mCityList) {
                    String locateCityName = beijingBtn.getText().toString();
                    if (city.getCity().equals(locateCityName.substring(0, locateCityName.length() - 1))) {
                        mLocCityCode = city.getNumber();
                        Log.d("locate", locateCityName.substring(0, locateCityName.length() - 1));
                    }

                }

                // 用Shareperfrrenc存储最近一次的citycode
                SharedPreferences sharedPreferences = getSharedPreferences("CityCodePreference", Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("citycode", mLocCityCode);
                editor.commit();

                intent.putExtra("citycode", mLocCityCode);
                startActivity(intent);
            }
        });

        //上海市
        shanghaiBtn= (Button)findViewById(R.id.shanghai);
        shanghaiBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myApplication = (MyApplication) getApplication();
                mCityList = myApplication.getCityList();
                for (City city : mCityList) {
                    String locateCityName = shanghaiBtn.getText().toString();
                    if (city.getCity().equals(locateCityName.substring(0, locateCityName.length() - 1))) {
                        mLocCityCode = city.getNumber();
                        Log.d("locate", locateCityName.substring(0, locateCityName.length() - 1));
                    }

                }

                // 用Shareperfrrenc存储最近一次的citycode
                SharedPreferences sharedPreferences = getSharedPreferences("CityCodePreference", Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("citycode", mLocCityCode);
                editor.commit();

                intent.putExtra("citycode", mLocCityCode);
                startActivity(intent);
            }
        });

        //苏州
        suzhouBtn = (Button)findViewById(R.id.suzhou);
        suzhouBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myApplication = (MyApplication) getApplication();
                mCityList = myApplication.getCityList();
                for (City city : mCityList) {
                    String locateCityName = suzhouBtn.getText().toString();
                    if (city.getCity().equals(locateCityName.substring(0, locateCityName.length() - 1))) {
                        mLocCityCode = city.getNumber();
                        Log.d("locate", locateCityName.substring(0, locateCityName.length() - 1));
                    }

                }

                // 用Shareperfrrenc存储最近一次的citycode
                SharedPreferences sharedPreferences = getSharedPreferences("CityCodePreference", Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("citycode", mLocCityCode);
                editor.commit();

                intent.putExtra("citycode", mLocCityCode);
                startActivity(intent);
            }
        });

        //郑州
        zhengzhouBtn = (Button)findViewById(R.id.zhengzhou);
        zhengzhouBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myApplication = (MyApplication) getApplication();
                mCityList = myApplication.getCityList();
                for (City city : mCityList) {
                    String locateCityName = zhengzhouBtn.getText().toString();
                    if (city.getCity().equals(locateCityName.substring(0, locateCityName.length() - 1))) {
                        mLocCityCode = city.getNumber();
                        Log.d("locate", locateCityName.substring(0, locateCityName.length() - 1));
                    }

                }

                // 用Shareperfrrenc存储最近一次的citycode
                SharedPreferences sharedPreferences = getSharedPreferences("CityCodePreference", Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("citycode", mLocCityCode);
                editor.commit();

                intent.putExtra("citycode", mLocCityCode);
                startActivity(intent);
            }
        });

        //西安
        xianBtn = (Button)findViewById(R.id.xian);
        xianBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myApplication = (MyApplication) getApplication();
                mCityList = myApplication.getCityList();
                for (City city : mCityList) {
                    String locateCityName = xianBtn.getText().toString();
                    if (city.getCity().equals(locateCityName.substring(0, locateCityName.length() - 1))) {
                        mLocCityCode = city.getNumber();
                        Log.d("locate", locateCityName.substring(0, locateCityName.length() - 1));
                    }

                }

                // 用Shareperfrrenc存储最近一次的citycode
                SharedPreferences sharedPreferences = getSharedPreferences("CityCodePreference", Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("citycode", mLocCityCode);
                editor.commit();

                intent.putExtra("citycode", mLocCityCode);
                startActivity(intent);
            }
        });

        //南京
        nanjingBtn = (Button)findViewById(R.id.nanjing);
        nanjingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myApplication = (MyApplication) getApplication();
                mCityList = myApplication.getCityList();
                for (City city : mCityList) {
                    String locateCityName = nanjingBtn.getText().toString();
                    if (city.getCity().equals(locateCityName.substring(0, locateCityName.length() - 1))) {
                        mLocCityCode = city.getNumber();
                        Log.d("locate", locateCityName.substring(0, locateCityName.length() - 1));
                    }

                }

                // 用Shareperfrrenc存储最近一次的citycode
                SharedPreferences sharedPreferences = getSharedPreferences("CityCodePreference", Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("citycode", mLocCityCode);
                editor.commit();

                intent.putExtra("citycode", mLocCityCode);
                startActivity(intent);
            }
        });

        //昆明
        kunmingBtn = (Button)findViewById(R.id.kunming);
        kunmingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myApplication = (MyApplication) getApplication();
                mCityList = myApplication.getCityList();
                for (City city : mCityList) {
                    String locateCityName = kunmingBtn.getText().toString();
                    if (city.getCity().equals(locateCityName.substring(0, locateCityName.length() - 1))) {
                        mLocCityCode = city.getNumber();
                        Log.d("locate", locateCityName.substring(0, locateCityName.length() - 1));
                    }

                }

                // 用Shareperfrrenc存储最近一次的citycode
                SharedPreferences sharedPreferences = getSharedPreferences("CityCodePreference", Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("citycode", mLocCityCode);
                editor.commit();

                intent.putExtra("citycode", mLocCityCode);
                startActivity(intent);
            }
        });

        //赤峰
        chifengBtn = (Button)findViewById(R.id.chifeng);
        chifengBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myApplication = (MyApplication) getApplication();
                mCityList = myApplication.getCityList();
                for (City city : mCityList) {
                    String locateCityName = chifengBtn.getText().toString();
                    if (city.getCity().equals(locateCityName.substring(0, locateCityName.length() - 1))) {
                        mLocCityCode = city.getNumber();
                        Log.d("locate", locateCityName.substring(0, locateCityName.length() - 1));
                    }

                }

                // 用Shareperfrrenc存储最近一次的citycode
                SharedPreferences sharedPreferences = getSharedPreferences("CityCodePreference", Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("citycode", mLocCityCode);
                editor.commit();

                intent.putExtra("citycode", mLocCityCode);
                startActivity(intent);
            }
        });

        //随州
        suizhouBtn = (Button)findViewById(R.id.suizhou);
        suizhouBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myApplication = (MyApplication) getApplication();
                mCityList = myApplication.getCityList();
                for (City city : mCityList) {
                    String locateCityName = suizhouBtn.getText().toString();
                    if (city.getCity().equals(locateCityName.substring(0, locateCityName.length() - 1))) {
                        mLocCityCode = city.getNumber();
                        Log.d("locate", locateCityName.substring(0, locateCityName.length() - 1));
                    }

                }

                // 用Shareperfrrenc存储最近一次的citycode
                SharedPreferences sharedPreferences = getSharedPreferences("CityCodePreference", Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("citycode", mLocCityCode);
                editor.commit();

                intent.putExtra("citycode", mLocCityCode);
                startActivity(intent);
            }
        });

        //邵阳
        shaoyangBtn = (Button)findViewById(R.id.shaoyang);
        shaoyangBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myApplication = (MyApplication) getApplication();
                mCityList = myApplication.getCityList();
                for (City city : mCityList) {
                    String locateCityName = shaoyangBtn.getText().toString();
                    if (city.getCity().equals(locateCityName.substring(0, locateCityName.length() - 1))) {
                        mLocCityCode = city.getNumber();
                        Log.d("locate", locateCityName.substring(0, locateCityName.length() - 1));
                    }

                }

                // 用Shareperfrrenc存储最近一次的citycode
                SharedPreferences sharedPreferences = getSharedPreferences("CityCodePreference", Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("citycode", mLocCityCode);
                editor.commit();

                intent.putExtra("citycode", mLocCityCode);
                startActivity(intent);
            }
        });

        //临沂
        linyiBtn = (Button)findViewById(R.id.linyi);
        linyiBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myApplication = (MyApplication) getApplication();
                mCityList = myApplication.getCityList();
                for (City city : mCityList) {
                    String locateCityName = linyiBtn.getText().toString();
                    if (city.getCity().equals(locateCityName.substring(0, locateCityName.length() - 1))) {
                        mLocCityCode = city.getNumber();
                        Log.d("locate", locateCityName.substring(0, locateCityName.length() - 1));
                    }

                }

                // 用Shareperfrrenc存储最近一次的citycode
                SharedPreferences sharedPreferences = getSharedPreferences("CityCodePreference", Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("citycode", mLocCityCode);
                editor.commit();

                intent.putExtra("citycode", mLocCityCode);
                startActivity(intent);
            }
        });

        //拉萨
        lasaBtn = (Button)findViewById(R.id.lasa);
        lasaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myApplication = (MyApplication) getApplication();
                mCityList = myApplication.getCityList();
                for (City city : mCityList) {
                    String locateCityName = lasaBtn.getText().toString();
                    if (city.getCity().equals(locateCityName.substring(0, locateCityName.length() - 1))) {
                        mLocCityCode = city.getNumber();
                        Log.d("locate", locateCityName.substring(0, locateCityName.length() - 1));
                    }

                }

                // 用Shareperfrrenc存储最近一次的citycode
                SharedPreferences sharedPreferences = getSharedPreferences("CityCodePreference", Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("citycode", mLocCityCode);
                editor.commit();

                intent.putExtra("citycode", mLocCityCode);
                startActivity(intent);
            }
        });

        //大连
        dalianBtn = (Button)findViewById(R.id.dalian);
        dalianBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myApplication = (MyApplication) getApplication();
                mCityList = myApplication.getCityList();
                for (City city : mCityList) {
                    String locateCityName = dalianBtn.getText().toString();
                    if (city.getCity().equals(locateCityName.substring(0, locateCityName.length() - 1))) {
                        mLocCityCode = city.getNumber();
                        Log.d("locate", locateCityName.substring(0, locateCityName.length() - 1));
                    }

                }

                // 用Shareperfrrenc存储最近一次的citycode
                SharedPreferences sharedPreferences = getSharedPreferences("CityCodePreference", Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("citycode", mLocCityCode);
                editor.commit();

                intent.putExtra("citycode", mLocCityCode);
                startActivity(intent);
            }
        });

        //成都
        chengduBtn = (Button)findViewById(R.id.chengdu);
        chengduBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myApplication = (MyApplication) getApplication();
                mCityList = myApplication.getCityList();
                for (City city : mCityList) {
                    String locateCityName = chengduBtn.getText().toString();
                    if (city.getCity().equals(locateCityName.substring(0, locateCityName.length() - 1))) {
                        mLocCityCode = city.getNumber();
                        Log.d("locate", locateCityName.substring(0, locateCityName.length() - 1));
                    }

                }

                // 用Shareperfrrenc存储最近一次的citycode
                SharedPreferences sharedPreferences = getSharedPreferences("CityCodePreference", Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("citycode", mLocCityCode);
                editor.commit();

                intent.putExtra("citycode", mLocCityCode);
                startActivity(intent);
            }
        });

        //深圳
        shenzhenBtn = (Button)findViewById(R.id.shenzhen);
        shenzhenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myApplication = (MyApplication) getApplication();
                mCityList = myApplication.getCityList();
                for (City city : mCityList) {
                    String locateCityName = shenzhenBtn.getText().toString();
                    if (city.getCity().equals(locateCityName.substring(0, locateCityName.length() - 1))) {
                        mLocCityCode = city.getNumber();
                        Log.d("locate", locateCityName.substring(0, locateCityName.length() - 1));
                    }

                }

                // 用Shareperfrrenc存储最近一次的citycode
                SharedPreferences sharedPreferences = getSharedPreferences("CityCodePreference", Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("citycode", mLocCityCode);
                editor.commit();

                intent.putExtra("citycode", mLocCityCode);
                startActivity(intent);
            }
        });

        //广州
        guangzhouBtn = (Button)findViewById(R.id.guangzhou);
        guangzhouBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myApplication = (MyApplication) getApplication();
                mCityList = myApplication.getCityList();
                for (City city : mCityList) {
                    String locateCityName = guangzhouBtn.getText().toString();
                    if (city.getCity().equals(locateCityName.substring(0, locateCityName.length() - 1))) {
                        mLocCityCode = city.getNumber();
                        Log.d("locate", locateCityName.substring(0, locateCityName.length() - 1));
                    }

                }

                // 用Shareperfrrenc存储最近一次的citycode
                SharedPreferences sharedPreferences = getSharedPreferences("CityCodePreference", Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("citycode", mLocCityCode);
                editor.commit();

                intent.putExtra("citycode", mLocCityCode);
                startActivity(intent);
            }
        });

        //沈阳
        shenyangBtn = (Button)findViewById(R.id.shenyang);
        shenyangBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myApplication = (MyApplication) getApplication();
                mCityList = myApplication.getCityList();
                for (City city : mCityList) {
                    String locateCityName = shenyangBtn.getText().toString();
                    if (city.getCity().equals(locateCityName.substring(0, locateCityName.length() - 1))) {
                        mLocCityCode = city.getNumber();
                        Log.d("locate", locateCityName.substring(0, locateCityName.length() - 1));
                    }

                }

                // 用Shareperfrrenc存储最近一次的citycode
                SharedPreferences sharedPreferences = getSharedPreferences("CityCodePreference", Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("citycode", mLocCityCode);
                editor.commit();

                intent.putExtra("citycode", mLocCityCode);
                startActivity(intent);
            }
        });

        //武汉
        wuhanBtn = (Button)findViewById(R.id.wuhan);
        wuhanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myApplication = (MyApplication) getApplication();
                mCityList = myApplication.getCityList();
                for (City city : mCityList) {
                    String locateCityName = wuhanBtn.getText().toString();
                    if (city.getCity().equals(locateCityName.substring(0, locateCityName.length() - 1))) {
                        mLocCityCode = city.getNumber();
                        Log.d("locate", locateCityName.substring(0, locateCityName.length() - 1));
                    }

                }

                // 用Shareperfrrenc存储最近一次的citycode
                SharedPreferences sharedPreferences = getSharedPreferences("CityCodePreference", Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("citycode", mLocCityCode);
                editor.commit();

                intent.putExtra("citycode", mLocCityCode);
                startActivity(intent);
            }
        });

        //厦门
        xiamenBtn = (Button)findViewById(R.id.xiamen);
        xiamenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myApplication = (MyApplication) getApplication();
                mCityList = myApplication.getCityList();
                for (City city : mCityList) {
                    String locateCityName = xiamenBtn.getText().toString();
                    if (city.getCity().equals(locateCityName.substring(0, locateCityName.length() - 1))) {
                        mLocCityCode = city.getNumber();
                        Log.d("locate", locateCityName.substring(0, locateCityName.length() - 1));
                    }

                }

                // 用Shareperfrrenc存储最近一次的citycode
                SharedPreferences sharedPreferences = getSharedPreferences("CityCodePreference", Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("citycode", mLocCityCode);
                editor.commit();

                intent.putExtra("citycode", mLocCityCode);
                startActivity(intent);
            }
        });

        //天津
        tianjinBtn = (Button)findViewById(R.id.tianjin);
        tianjinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myApplication = (MyApplication) getApplication();
                mCityList = myApplication.getCityList();
                for (City city : mCityList) {
                    String locateCityName = tianjinBtn.getText().toString();
                    if (city.getCity().equals(locateCityName.substring(0, locateCityName.length() - 1))) {
                        mLocCityCode = city.getNumber();
                        Log.d("locate", locateCityName.substring(0, locateCityName.length() - 1));
                    }

                }

                // 用Shareperfrrenc存储最近一次的citycode
                SharedPreferences sharedPreferences = getSharedPreferences("CityCodePreference", Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("citycode", mLocCityCode);
                editor.commit();

                intent.putExtra("citycode", mLocCityCode);
                startActivity(intent);
            }
        });

        //宁波
        ningboBtn = (Button)findViewById(R.id.ningbo);
        ningboBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myApplication = (MyApplication) getApplication();
                mCityList = myApplication.getCityList();
                for (City city : mCityList) {
                    String locateCityName = ningboBtn.getText().toString();
                    if (city.getCity().equals(locateCityName.substring(0, locateCityName.length() - 1))) {
                        mLocCityCode = city.getNumber();
                        Log.d("locate", locateCityName.substring(0, locateCityName.length() - 1));
                    }

                }

                // 用Shareperfrrenc存储最近一次的citycode
                SharedPreferences sharedPreferences = getSharedPreferences("CityCodePreference", Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("citycode", mLocCityCode);
                editor.commit();

                intent.putExtra("citycode", mLocCityCode);
                startActivity(intent);
            }
        });

        searchEt = (EditText)findViewById(R.id.selectcity_search);
        searchBtn = (Button)findViewById(R.id.selectcity_search_button);
        searchBtn.setOnClickListener(this);

//        Button btn = (Button)findViewById(R.id.selectcity_search_button);
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                EditText et = (EditText)findViewById(R.id.selectcity_search);//获取edittext组件
//                String s = et.getText().toString().trim();
//                if(TextUtils.isEmpty(s)){
//                    Toast.makeText(getApplicationContext(),"请输入",Toast.LENGTH_LONG).show();
//                }
//            }
//        });

        //将ListView内容加载为我们从数据库文件读到的城市列表
        mApplication = (MyApplication) getApplication();
        mCityList = mApplication.getCityList();
        mArrayList = new ArrayList<String>(); //不new会指向空
        for (int i = 0; i < mCityList.size(); i++) {
            //改成每个item包含id，citycode，省份，城市信息。
//            String No_ = Integer.toString(i+1);
//            String number = mCityList.get(i).getNumber();
//            String provinceName = mCityList.get(i).getProvince();
            String cityName = mCityList.get(i).getCity();
            mArrayList.add(cityName);
        }
       // cityListLv = (ListView) findViewById(R.id.selectcity_lv);
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(SelectCity.this, android.R.layout.simple_list_item_1, mArrayList);
//        cityListLv.setAdapter(adapter);
//
//        //添加ListView项的点击事件的动作
//        AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
//            @Override
//
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//                int updateCityCode2 = Integer.parseInt(mCityList.get(position).getNumber());
//                String updateCityCode3 = Integer.toString(updateCityCode2);  //记得转换为string类型
//                updateCityCode = updateCityCode3; //进行赋值
//
//                //SharedPreferences 存储最近一次的citycode
//                SharedPreferences sharedPreferences = getSharedPreferences("CityCodePreference",Activity.MODE_PRIVATE);
//                SharedPreferences.Editor editor = sharedPreferences.edit();
//                editor.putString("citycode", updateCityCode);
//                editor.commit();
//
//
//            }
//
//        };
//        //为组件绑定监听
//        cityListLv.setOnItemClickListener(itemClickListener);

    }


    @Override
    //为返回按钮添加返回主界面的动作
    public void onClick(View v) {
       switch (v.getId()) {
           //设置动作，将获取EditText获取的数据（这里我们获取的是citycode），传给MainActivity更新天气数据
           case R.id.selectcity_search_button:
               EditText et = (EditText) findViewById(R.id.selectcity_search);//获取edittext组件
               String cityname = et.getText().toString();
               if (TextUtils.isEmpty(cityname)) {
                   Toast.makeText(getApplicationContext(), "请输入搜索城市！", Toast.LENGTH_LONG).show();
               } else {
                   Log.d("Search", cityname);
                   //获取EditText的内容，现在我们用它来更新ListView
                   ArrayList<String> mSearchList = new ArrayList<String>();
                   for (int i = 0; i < mCityList.size(); i++) {
                       String No_ = Integer.toString(i + 1);
                       String number = mCityList.get(i).getNumber();
                       String provinceName = mCityList.get(i).getProvince();
                       String cityName = mCityList.get(i).getCity();

                       if (cityName.equals(cityname)) {
                           mSearchList.add(cityName);
                           Log.d("changed adapter data", cityName);
                           updateCityCode = number;//把搜索出来的城市的城市代码（citycode）复制给 updateCityCode

                       }

                       cityListLv = (ListView) findViewById(R.id.selectcity_lv);
                       ArrayAdapter<String> adapter = new ArrayAdapter<String>(SelectCity.this, android.R.layout.simple_list_item_1, mSearchList);
                       cityListLv.setAdapter(adapter);
                       adapter.notifyDataSetChanged();

                       //SharedPreferences 存储最近一次的citycode
                       SharedPreferences sharedPreferences = getSharedPreferences("CityCodePreference", Activity.MODE_PRIVATE);
                       SharedPreferences.Editor editor = sharedPreferences.edit();
                       editor.putString("citycode", updateCityCode);
                       editor.commit();


                   }
                   Intent intent = new Intent(this, MainActivity.class);
                   intent.putExtra("citycode", updateCityCode);
                   startActivity(intent);
                   break;

               }
       }
    }


}
