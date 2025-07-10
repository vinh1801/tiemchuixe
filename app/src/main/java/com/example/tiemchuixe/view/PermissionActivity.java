package com.example.tiemchuixe.view;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.tiemchuixe.R;

public class PermissionActivity extends AppCompatActivity {
    private static final int NOTIFICATION_PERMISSION_REQUEST_CODE = 1001;
    
    private TextView textViewPermissionStatus;
    private Button buttonRequestPermission;
    private Button buttonContinue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);

        textViewPermissionStatus = findViewById(R.id.textViewPermissionStatus);
        buttonRequestPermission = findViewById(R.id.buttonRequestPermission);
        buttonContinue = findViewById(R.id.buttonContinue);

        updatePermissionStatus();

        buttonRequestPermission.setOnClickListener(v -> requestNotificationPermission());
        buttonContinue.setOnClickListener(v -> proceedToMain());
    }

    private void updatePermissionStatus() {
        boolean hasPermission = hasNotificationPermission();
        if (hasPermission) {
            textViewPermissionStatus.setText("✓ Quyền thông báo đã được cấp");
            buttonRequestPermission.setEnabled(false);
            buttonContinue.setEnabled(true);
        } else {
            textViewPermissionStatus.setText("✗ Quyền thông báo chưa được cấp");
            buttonRequestPermission.setEnabled(true);
            buttonContinue.setEnabled(true);
        }
    }

    private boolean hasNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            return ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) 
                   == PackageManager.PERMISSION_GRANTED;
        }
        return true; // For older versions, notification permission is granted by default
    }

    private void requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.POST_NOTIFICATIONS)) {
                // Show explanation to user
                Toast.makeText(this, "Ứng dụng cần quyền thông báo để thông báo về trạng thái phiếu rửa xe", Toast.LENGTH_LONG).show();
            }
            
            ActivityCompat.requestPermissions(this, 
                new String[]{Manifest.permission.POST_NOTIFICATIONS}, 
                NOTIFICATION_PERMISSION_REQUEST_CODE);
        }
    }

    private void proceedToMain() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        
        if (requestCode == NOTIFICATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Quyền thông báo đã được cấp", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Quyền thông báo bị từ chối. Bạn có thể cấp quyền sau trong cài đặt", Toast.LENGTH_LONG).show();
            }
            updatePermissionStatus();
        }
    }
} 