package uluc.f.qrcodescanner;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

/**
 * Created by uluc on 27.03.2018.
 */

public class QrScannerPage extends Activity implements ZXingScannerView.ResultHandler {
    private ZXingScannerView mScannerView;

    @Override
    protected void onCreate(Bundle savedınstanceState) {
        super.onCreate(savedınstanceState);
        setContentView(R.layout.layout);

    }

    public void onClick(View v) {
        mScannerView =new ZXingScannerView((this));
        setContentView(mScannerView);
        mScannerView.setResultHandler(this);
        if(checkPermission()){
            Toast.makeText(getApplicationContext(),"Permission is Granted",Toast.LENGTH_LONG).show();

        }else{
            requestPermissions();
        }
        mScannerView.startCamera();
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        mScannerView.stopCamera();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }

    @Override
    public void handleResult(Result result){
        Log.w("handeleResult",result.getText());
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Scan Result");
        builder.setMessage(result.getText());
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        mScannerView.resumeCameraPreview(this);

    }

    private boolean checkPermission(){
        return (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA)== PackageManager.PERMISSION_GRANTED);
    }

    private void requestPermissions(){
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA},2);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==2){
            if(grantResults.length >0 && grantResults[0]== PackageManager.PERMISSION_GRANTED) {

                //Toast.makeText(getApplicationContext(), "Okey", Toast.LENGTH_LONG).show();
                onClick(null);
            }
            else{
                Toast.makeText(getApplicationContext(),"You have to accept to continue",Toast.LENGTH_LONG).show();
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

}
