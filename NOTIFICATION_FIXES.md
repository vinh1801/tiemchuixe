# Khắc phục lỗi thông báo - Tiệm Chùi Xe

## Lỗi đã gặp phải

### 1. Lỗi ic_car_wash.xml
**Lỗi**: `ClassCastException: class androidx.constraintlayout.widget.ConstraintLayout cannot be cast to android.graphics.drawable.Drawable`

**Nguyên nhân**: File `ic_car_wash.xml` chứa ConstraintLayout thay vì vector drawable

**Đã khắc phục**:
- Thay thế nội dung file `ic_car_wash.xml` bằng vector drawable hợp lệ
- Cập nhật layout `activity_permission.xml` sử dụng icon mặc định
- Cập nhật `NotificationHelper.java` sử dụng `android.R.drawable.ic_dialog_info`

### 2. Lỗi PendingIntent FLAG_IMMUTABLE
**Lỗi**: `IllegalArgumentException: Targeting S+ (version 31 and above) requires that one of FLAG_IMMUTABLE or FLAG_MUTABLE be specified when creating a PendingIntent`

**Nguyên nhân**: Từ Android 12 (API 31) trở lên, PendingIntent phải có flag FLAG_IMMUTABLE hoặc FLAG_MUTABLE

**Đã khắc phục**:
- Thêm `| PendingIntent.FLAG_IMMUTABLE` vào tất cả PendingIntent.getActivity()
- Sử dụng FLAG_IMMUTABLE vì không cần thay đổi intent sau khi tạo

## Các thay đổi đã thực hiện

### 1. Sửa file ic_car_wash.xml
```xml
<?xml version="1.0" encoding="utf-8"?>
<vector xmlns:android="http://schemas.android.com/apk/res/android"
    android:width="24dp"
    android:height="24dp"
    android:viewportWidth="24"
    android:viewportHeight="24">
    
    <!-- Car body -->
    <path
        android:fillColor="#2196F3"
        android:pathData="M18.92,6.01C18.72,5.42 18.16,5 17.5,5h-11c-0.66,0 -1.22,0.42 -1.42,1.01L3,12v8c0,0.55 0.45,1 1,1h1c0.55,0 1,-0.45 1,-1v-1h12v1c0,0.55 0.45,1 1,1h1c0.55,0 1,-0.45 1,-1v-8l-2.08,-5.99zM6.5,16c-0.83,0 -1.5,-0.67 -1.5,-1.5S5.67,13 6.5,13s1.5,0.67 1.5,1.5S7.33,16 6.5,16zM17.5,16c-0.83,0 -1.5,-0.67 -1.5,-1.5s0.67,-1.5 1.5,-1.5 1.5,0.67 1.5,1.5 -0.67,1.5 -1.5,1.5zM5,11l1.5,-4.5h11L19,11H5z"/>
    
    <!-- Water drops and spray -->
    <!-- ... các path khác ... -->
</vector>
```

### 2. Cập nhật activity_permission.xml
```xml
<ImageView
    android:layout_width="120dp"
    android:layout_height="120dp"
    android:src="@android:drawable/ic_dialog_info"
    android:layout_marginBottom="32dp"
    android:contentDescription="App Icon"
    android:tint="@color/design_default_color_primary" />
```

### 3. Cập nhật NotificationHelper.java
```java
// Thay thế tất cả R.drawable.ic_car_wash bằng android.R.drawable.ic_dialog_info
.setSmallIcon(android.R.drawable.ic_dialog_info)

// Thêm FLAG_IMMUTABLE cho tất cả PendingIntent
PendingIntent pendingIntent = PendingIntent.getActivity(
    context, 
    maPhieu, 
    intent, 
    PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
);
```

## Cách test ứng dụng

### 1. Build và chạy ứng dụng
```bash
./gradlew assembleDebug
./gradlew installDebug
```

### 2. Kiểm tra quyền thông báo
- Ứng dụng sẽ hiển thị màn hình PermissionActivity đầu tiên
- Nhấn "Cấp quyền thông báo" để cho phép
- Hoặc nhấn "Tiếp tục" để bỏ qua

### 3. Test thông báo
- Tạo phiếu mới → Kiểm tra thông báo xuất hiện
- Cập nhật trạng thái → Kiểm tra thông báo cập nhật
- Hoàn thành phiếu → Kiểm tra thông báo hoàn thành

## Lưu ý quan trọng

### 1. Android 13+ (API 33+)
- Cần quyền POST_NOTIFICATIONS
- PermissionActivity sẽ xin quyền tự động

### 2. Android 12 trở xuống
- Quyền thông báo được cấp mặc định
- Không cần xin quyền đặc biệt

### 3. Icon thông báo
- Hiện tại sử dụng icon mặc định của Android
- Có thể thay thế bằng icon tùy chỉnh sau

## Troubleshooting

### 1. Thông báo không hiển thị
- Kiểm tra quyền trong Settings > Apps > Tiệm Chùi Xe > Permissions
- Đảm bảo không bật "Do Not Disturb"
- Kiểm tra cài đặt thông báo của ứng dụng

### 2. Lỗi build
- Clean project: `./gradlew clean`
- Rebuild: `./gradlew assembleDebug`
- Kiểm tra dependencies trong build.gradle.kts

### 3. Lỗi runtime
- Kiểm tra logcat để xem lỗi chi tiết
- Đảm bảo tất cả Activity được khai báo trong AndroidManifest.xml

## Tương lai

### Các cải tiến có thể thực hiện:
1. Tạo icon car wash vector drawable đẹp hơn
2. Thêm âm thanh thông báo tùy chỉnh
3. Thông báo theo lịch trình
4. Thông báo push từ server
5. Tùy chỉnh giao diện thông báo 