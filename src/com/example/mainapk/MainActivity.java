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
import android.app.SearchManager.OnCancelListener;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener{
   private Button installApk;
    
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
       installApk=(Button)this.findViewById(R.id.bt);
       boolean isfind=findApk();
       if (isfind) {
    	   installApk.setText("������������");
	    }else{
          installApk.setText("��װ��������");
	    }
       installApk.setOnClickListener(this);
    }

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt:
			//�����ǲ���apk�Ƿ�װ�����û��װ��copy����װ
			boolean isfind=findApk();
			if (isfind) {
				String action = "me.joysinfo.testaction01";
		        Intent it = new Intent(action);
				startActivity(it);
			}else{
				//��װ
				Install(this,"test.apk");
			}
			break;
		default:
			break;
		}
		
	 }

	private boolean findApk() {
		boolean isfind=false;
		String action = "me.joysinfo.testaction01";
        Intent n = new Intent(action);
        PackageManager packageManager = getBaseContext().getPackageManager();
        final Intent intent = new Intent(action);
        List<ResolveInfo> resolveInfo = packageManager
                .queryIntentActivities(intent,
                        PackageManager.MATCH_DEFAULT_ONLY);
        if (resolveInfo.size() > 0) {
        	isfind=true;
            Toast.makeText(getBaseContext(), "�ҵ���ƥ���activity", 0).show();
        }
        else{
        	isfind=false;
            Toast.makeText(getBaseContext(), "δ�ҵ�ƥ���activity", 0).show();
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
        } catch (Exception e) {
        }
        intentInstall.setAction(android.content.Intent.ACTION_VIEW);
        intentInstall.setDataAndType(Uri.fromFile(file),
                "application/vnd.android.package-archive");
        ctx.startActivity(intentInstall);
    }

}
