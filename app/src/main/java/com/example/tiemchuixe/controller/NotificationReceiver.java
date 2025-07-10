package com.example.tiemchuixe.controller;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class NotificationReceiver extends BroadcastReceiver {
    public static final String ACTION_TICKET_CREATED = "com.example.tiemchuixe.TICKET_CREATED";
    public static final String ACTION_STATUS_UPDATED = "com.example.tiemchuixe.STATUS_UPDATED";
    public static final String ACTION_TICKET_COMPLETED = "com.example.tiemchuixe.TICKET_COMPLETED";

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        
        if (action != null) {
            switch (action) {
                case ACTION_TICKET_CREATED:
                    int maPhieu = intent.getIntExtra("MA_PHIEU", -1);
                    String customerName = intent.getStringExtra("CUSTOMER_NAME");
                    String licensePlate = intent.getStringExtra("LICENSE_PLATE");
                    double totalAmount = intent.getDoubleExtra("TOTAL_AMOUNT", 0.0);
                    
                    String message = String.format("Phiếu #%d - %s (%s) đã được tạo", 
                            maPhieu, customerName, licensePlate);
                    Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                    break;
                    
                case ACTION_STATUS_UPDATED:
                    int maPhieuUpdate = intent.getIntExtra("MA_PHIEU", -1);
                    String oldStatus = intent.getStringExtra("OLD_STATUS");
                    String newStatus = intent.getStringExtra("NEW_STATUS");
                    
                    String updateMessage = String.format("Phiếu #%d: %s → %s", 
                            maPhieuUpdate, oldStatus, newStatus);
                    Toast.makeText(context, updateMessage, Toast.LENGTH_LONG).show();
                    break;
                    
                case ACTION_TICKET_COMPLETED:
                    int maPhieuCompleted = intent.getIntExtra("MA_PHIEU", -1);
                    String completedMessage = String.format("Phiếu #%d đã hoàn thành!", maPhieuCompleted);
                    Toast.makeText(context, completedMessage, Toast.LENGTH_LONG).show();
                    break;
            }
        }
    }
} 