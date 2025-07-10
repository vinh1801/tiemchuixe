package com.example.tiemchuixe.controller;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.app.Notification;

import com.example.tiemchuixe.R;
import com.example.tiemchuixe.view.TicketDetailActivity;

public class NotificationHelper {
    private static final String CHANNEL_ID = "tiemchuixe_channel";
    private static final String CHANNEL_NAME = "Tiệm Chùi Xe";
    private static final String CHANNEL_DESCRIPTION = "Thông báo từ ứng dụng Tiệm Chùi Xe";
    
    private Context context;
    private NotificationManager notificationManager;

    public NotificationHelper(Context context) {
        this.context = context;
        this.notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        createNotificationChannel();
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            channel.setDescription(CHANNEL_DESCRIPTION);
            
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
    }

    public void showTicketCreatedNotification(int maPhieu, String customerName, String licensePlate, double totalAmount) {
        String title = "Phiếu rửa xe mới";
        String content = String.format("Phiếu #%d - %s (%s) - %.0f VNĐ", 
                maPhieu, customerName, licensePlate, totalAmount);

        Intent intent = new Intent(context, TicketDetailActivity.class);
        intent.putExtra("MA_PHIEU", maPhieu);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(
                context, 
                maPhieu, 
                intent, 
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        Notification.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder = new Notification.Builder(context, CHANNEL_ID);
        } else {
            builder = new Notification.Builder(context);
        }

        Notification notification = builder
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle(title)
                .setContentText(content)
                .setPriority(Notification.PRIORITY_DEFAULT)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .build();

        if (notificationManager != null) {
            notificationManager.notify(maPhieu, notification);
        }
    }

    public void showStatusUpdateNotification(int maPhieu, String customerName, String licensePlate, String oldStatus, String newStatus) {
        String title = "Cập nhật trạng thái phiếu";
        String content = String.format("Phiếu #%d - %s (%s): %s → %s", 
                maPhieu, customerName, licensePlate, oldStatus, newStatus);

        Intent intent = new Intent(context, TicketDetailActivity.class);
        intent.putExtra("MA_PHIEU", maPhieu);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(
                context, 
                maPhieu + 1000, // Different request code to avoid conflicts
                intent, 
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        Notification.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder = new Notification.Builder(context, CHANNEL_ID);
        } else {
            builder = new Notification.Builder(context);
        }

        Notification notification = builder
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle(title)
                .setContentText(content)
                .setPriority(Notification.PRIORITY_DEFAULT)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .build();

        if (notificationManager != null) {
            notificationManager.notify(maPhieu + 1000, notification);
        }
    }

    public void showTicketCompletedNotification(int maPhieu, String customerName, String licensePlate, double totalAmount) {
        String title = "Hoàn thành rửa xe";
        String content = String.format("Phiếu #%d - %s (%s) đã hoàn thành. Tổng tiền: %.0f VNĐ", 
                maPhieu, customerName, licensePlate, totalAmount);

        Intent intent = new Intent(context, TicketDetailActivity.class);
        intent.putExtra("MA_PHIEU", maPhieu);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(
                context, 
                maPhieu + 2000, // Different request code
                intent, 
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        Notification.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder = new Notification.Builder(context, CHANNEL_ID);
        } else {
            builder = new Notification.Builder(context);
        }

        Notification notification = builder
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle(title)
                .setContentText(content)
                .setPriority(Notification.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .build();

        if (notificationManager != null) {
            notificationManager.notify(maPhieu + 2000, notification);
        }
    }
} 