package com.example.tiemchuixe.controller;

import android.content.Context;
import android.widget.Toast;

/**
 * Helper class để test chức năng thông báo
 * Sử dụng trong quá trình phát triển và debug
 */
public class NotificationTestHelper {
    
    private Context context;
    private NotificationHelper notificationHelper;
    
    public NotificationTestHelper(Context context) {
        this.context = context;
        this.notificationHelper = new NotificationHelper(context);
    }
    
    /**
     * Test thông báo tạo phiếu mới
     */
    public void testTicketCreatedNotification() {
        notificationHelper.showTicketCreatedNotification(
            1001, 
            "Nguyễn Văn A", 
            "51A-12345", 
            150000.0
        );
        Toast.makeText(context, "Đã gửi thông báo tạo phiếu mới", Toast.LENGTH_SHORT).show();
    }
    
    /**
     * Test thông báo cập nhật trạng thái
     */
    public void testStatusUpdateNotification() {
        notificationHelper.showStatusUpdateNotification(
            1001, 
            "Nguyễn Văn A", 
            "51A-12345", 
            "Chưa hoàn thành", 
            "Đang rửa"
        );
        Toast.makeText(context, "Đã gửi thông báo cập nhật trạng thái", Toast.LENGTH_SHORT).show();
    }
    
    /**
     * Test thông báo hoàn thành
     */
    public void testTicketCompletedNotification() {
        notificationHelper.showTicketCompletedNotification(
            1001, 
            "Nguyễn Văn A", 
            "51A-12345", 
            150000.0
        );
        Toast.makeText(context, "Đã gửi thông báo hoàn thành", Toast.LENGTH_SHORT).show();
    }
    
    /**
     * Test tất cả các loại thông báo
     */
    public void testAllNotifications() {
        testTicketCreatedNotification();
        
        // Delay để thông báo không bị chồng lấp
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        testStatusUpdateNotification();
        
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        testTicketCompletedNotification();
        
        Toast.makeText(context, "Đã test tất cả thông báo", Toast.LENGTH_LONG).show();
    }
    
    /**
     * Kiểm tra quyền thông báo
     */
    public boolean checkNotificationPermission() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            return context.checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS) 
                   == android.content.pm.PackageManager.PERMISSION_GRANTED;
        }
        return true;
    }
    
    /**
     * Hiển thị trạng thái quyền thông báo
     */
    public void showPermissionStatus() {
        boolean hasPermission = checkNotificationPermission();
        String message = hasPermission ? 
            "✓ Có quyền thông báo" : 
            "✗ Không có quyền thông báo";
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
} 