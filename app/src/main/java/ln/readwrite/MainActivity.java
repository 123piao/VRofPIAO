package ln.readwrite;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.baidu.duersdk.DuerSDK;
import com.baidu.duersdk.DuerSDKFactory;
import com.baidu.duersdk.DuerSDKImpl;
import com.baidu.duersdk.sdkverify.SdkVerifyManager;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    private static String appid = "dm95BBA8BD85310928";
    private static String appkey = "5E5ADAA549FB41F492F7DA0263B00430";

    private Button button1,button2;
    private TextView textView1,textView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button1= (Button) findViewById(R.id.button1);
        button2= (Button) findViewById(R.id.button2);
        textView1= (TextView) findViewById(R.id.textView);
        textView2= (TextView) findViewById(R.id.textView2);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                write(MainActivity.this,"!!!!aaaaaaaaaaaaaaaaaaaaaaaaa" +
                        "啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊" +
//                        "啊啊啊啊啊啊啊啊啊阿啊啊啊啊啊啊啊啊阿啊啊啊啊啊啊" +
//                        "啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊" +
//                        "啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊" +
//                        "啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊" +
                        "啊啊啊啊啊啊啊啊啊啊啊啊啊啊aaaaaaaaaaaa",Context.MODE_APPEND,"hehe");
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                read(MainActivity.this,"hehe");
            }
        });
        init();
    }


    private void init() {
        //初始化duersdk
        DuerSDK duerSDK = DuerSDKFactory.getDuerSDK();

        duerSDK.initSDK(getApplicationContext());


        duerSDK.registSDK(appid, appkey, new SdkVerifyManager.VerifyCallBack() {

            @Override
            public void onSucess() {
                Log.d("============","success");
            }

            @Override
            public void onFailure(int arg0, String arg1) {
            }

        });
        /**    false：线上， true：线下地址    **/
        DuerSDKImpl.getSdkConfig().setIsOffline(false);

    }


    /**
     * 新的读取文件内容
     *
     * @param context
     * @return
     */
    public String read(Context context, String filename) {
        try {
            FileInputStream inStream = context.openFileInput(filename);
            InputStreamReader read = new InputStreamReader(inStream,"UTF-8");
            char[] bytes = new char[1024];
//            int hasRead = 0;
//            StringBuilder sb = new StringBuilder();
//            while ((hasRead = inStream.read(buffer)) != -1) {
//                sb.append(new String(buffer, 0, hasRead));
//            }
//            BufferedReader bufferedReader = new BufferedReader(read);
            StringBuffer txt = new StringBuffer();
            BufferedReader bufferedReader = new BufferedReader(read);
            String lineTxt = null;
            while((lineTxt = bufferedReader.readLine()) != null){
                textView1.setText(lineTxt+"###");
                System.out.println("=======lineTxt======"+lineTxt);
                Log.i("mytxt",lineTxt);
                txt.append(lineTxt);
            }
            read.close();
            bufferedReader.close();
            inStream.close();
            textView2.setText(txt.toString()+"@@@");
            Log.i("mytxt.toString()",txt.toString());
            return txt.toString();
        } catch (Exception e) {
//            e.printStackTrace();
            return null;
        }
    }


    /**
     * 原来老的读取文件内容
     *
     * @param context
     * @return
     */
    public String read2(Context context, String filename) {
        try {
            FileInputStream inStream = context.openFileInput(filename);
            byte[] buffer = new byte[1024];
            int hasRead = 0;
            StringBuilder sb = new StringBuilder();
            while ((hasRead = inStream.read(buffer)) != -1) {
                sb.append(new String(buffer, 0, hasRead));
            }

            inStream.close();
            textView2.setText(sb.toString());
            return sb.toString();
        } catch (Exception e) {
//            e.printStackTrace();
            return null;
        }
    }


    /**
     * 写入文件内容
     *
     * @param context
     * @param msg
     * @param mode
     */
    public void write(Context context, String msg, int mode, String filename) {
        // 步骤1：获取输入值
        if (msg == null) return;
        try {
            // 步骤2:创建一个FileOutputStream对象,MODE_APPEND追加模式
            // MODE_PRIVATE：为默认操作模式，代表该文件是私有数据，只能被应用本身访问，在该模式下，写入的内容会覆盖原文件的内容
            FileOutputStream fos = context.openFileOutput(filename,
                    mode);
            // 步骤3：将获取过来的值放入文件
            fos.write(msg.getBytes());
            // 步骤4：关闭数据流
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
