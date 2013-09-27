package com.example.mainapk;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;
import com.joysinfo.main.R;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener{
   private Button installApk,setting;
   private String photoAction="com.joysinfo.photoactivity";
   private String settingAction="com.joysinfo.settingactivity";
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
       installApk=(Button)this.findViewById(R.id.bt);
       setting=(Button) this.findViewById(R.id.bt_setting);
       setting.setOnClickListener(this);
       boolean isfind=findApk();
       if (isfind) {
    	   installApk.setText("启动来电秀插件");
	    }else{
           installApk.setText("安装来电秀插件");
	    }
       installApk.setOnClickListener(this);
    }

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt:
			//首先是查找apk是否安装，如果没安装则copy，安装
			boolean isfind=findApk();
			if (isfind) {
		        Intent it = new Intent(photoAction);
				startActivity(it);
			}else{
				/*首先判断assets中是否存在,需要的文件
				 * 如果不存在则显示对话框提示，是否下载？如果下载则开始下载，弹出下载界面
				 * */
			//开启下载，并存储到sdcard中，安装。
				
				//安装
				//Install(this,"test.apk");
			}
			break;
		case R.id.bt_setting:
			 Intent it = new Intent(settingAction);
			 startActivity(it);
			break;
		default:
			break;
		}
	 }

	private boolean findApk() {
		boolean isfind=false;
        PackageManager packageManager = getBaseContext().getPackageManager();
        final Intent intent = new Intent(photoAction);
        List<ResolveInfo> resolveInfo = packageManager
                .queryIntentActivities(intent,
                        PackageManager.MATCH_DEFAULT_ONLY);
        if (resolveInfo.size()>0) {
        	isfind=true;
            Toast.makeText(getBaseContext(), "找到了匹配的activity", 0).show();
        }else{
        	isfind=false;
            Toast.makeText(getBaseContext(), "未找到匹配的activity", 0).show();
        }
		return isfind;
	}
	
	public  void Install(Context ctx, String strLocalFile) {
        Intent intentInstall = new Intent();
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), strLocalFile);
        try {
               InputStream is = ctx.getAssets().open(strLocalFile);
                file.createNewFile();
                FileOutputStream os = new FileOutputStream(file);
                Log.d("CUI", file.getName());
                byte[] bytes = new byte[1024];
                int i = -1;
                while ((i = is.read(bytes)) !=-1) {
                    os.write(bytes);
                }
                os.close();
                is.close();
        } catch (Exception e){
        }
        intentInstall.setAction(android.content.Intent.ACTION_VIEW);
        intentInstall.setDataAndType(Uri.fromFile(file),
                "application/vnd.android.package-archive");
        ctx.startActivity(intentInstall);
    }
}
