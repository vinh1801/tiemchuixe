# Hướng dẫn sử dụng chức năng thông báo - Tiệm Chùi Xe

## Tổng quan
Ứng dụng Tiệm Chùi Xe đã được tích hợp chức năng thông báo để thông báo về việc tạo và cập nhật trạng thái phiếu rửa xe.

## Các tính năng thông báo

### 1. Thông báo khi tạo phiếu mới
- **Khi nào**: Khi nhân viên tạo một phiếu rửa xe mới
- **Nội dung**: Hiển thị thông tin phiếu số, tên khách hàng, biển số xe và tổng tiền
- **Hành động**: Nhấn vào thông báo sẽ mở chi tiết phiếu

### 2. Thông báo khi cập nhật trạng thái
- **Khi nào**: Khi nhân viên cập nhật trạng thái phiếu (Chưa hoàn thành → Đang rửa → Hoàn thành)
- **Nội dung**: Hiển thị thông tin phiếu và sự thay đổi trạng thái
- **Hành động**: Nhấn vào thông báo sẽ mở chi tiết phiếu

### 3. Thông báo khi hoàn thành
- **Khi nào**: Khi phiếu được đánh dấu là "Hoàn thành"
- **Nội dung**: Thông báo đặc biệt với độ ưu tiên cao
- **Hành động**: Nhấn vào thông báo sẽ mở chi tiết phiếu

## Cấu trúc code

### 1. NotificationHelper.java
- **Vị trí**: `app/src/main/java/com/example/tiemchuixe/controller/NotificationHelper.java`
- **Chức năng**: Quản lý tất cả các thông báo
- **Các phương thức chính**:
  - `showTicketCreatedNotification()`: Thông báo tạo phiếu mới
  - `showStatusUpdateNotification()`: Thông báo cập nhật trạng thái
  - `showTicketCompletedNotification()`: Thông báo hoàn thành

### 2. PermissionActivity.java
- **Vị trí**: `app/src/main/java/com/example/tiemchuixe/view/PermissionActivity.java`
- **Chức năng**: Xin quyền thông báo từ người dùng
- **Layout**: `app/src/main/res/layout/activity_permission.xml`

### 3. Cập nhật trong các Activity
- **CreateTicketActivity**: Thêm thông báo khi tạo phiếu thành công
- **TicketDetailActivity**: Thêm thông báo khi cập nhật trạng thái

## Cách sử dụng

### 1. Khởi động ứng dụng
- Khi khởi động lần đầu, ứng dụng sẽ hiển thị màn hình xin quyền thông báo
- Nhấn "Cấp quyền thông báo" để cho phép ứng dụng gửi thông báo
- Hoặc nhấn "Tiếp tục" để bỏ qua (có thể cấp quyền sau)

### 2. Tạo phiếu mới
- Vào màn hình "Tạo phiếu"
- Nhập thông tin khách hàng và chọn dịch vụ
- Nhấn "Tạo phiếu"
- Hệ thống sẽ hiển thị thông báo về phiếu mới được tạo

### 3. Cập nhật trạng thái
- Vào màn hình "Danh sách phiếu"
- Chọn một phiếu để xem chi tiết
- Nhấn các nút "Bắt đầu rửa", "Hoàn thành" hoặc "Hủy"
- Hệ thống sẽ hiển thị thông báo về việc cập nhật trạng thái

## Cấu hình

### 1. AndroidManifest.xml
Đã thêm quyền thông báo:
```xml
<uses-permission android:name="android.permission.POST_NOTIFICATIONS"/>
```

### 2. build.gradle.kts
Đã thêm dependency:
```kotlin
implementation("androidx.core:core:1.12.0")
```

## Lưu ý kỹ thuật

### 1. Phiên bản Android
- **Android 13+ (API 33+)**: Cần xin quyền POST_NOTIFICATIONS
- **Android 12 trở xuống**: Quyền thông báo được cấp mặc định

### 2. Notification Channel
- Tạo channel "Tiệm Chùi Xe" với độ ưu tiên mặc định
- Channel được tạo tự động khi khởi tạo NotificationHelper

### 3. PendingIntent
- Sử dụng FLAG_UPDATE_CURRENT để cập nhật intent
- Mỗi loại thông báo có request code khác nhau để tránh xung đột

### 4. Error Handling
- Xử lý trường hợp người dùng từ chối quyền thông báo
- Thông báo vẫn hoạt động bình thường nếu không có quyền

## Troubleshooting

### 1. Thông báo không hiển thị
- Kiểm tra quyền thông báo trong cài đặt ứng dụng
- Đảm bảo không bật chế độ "Không làm phiền"
- Kiểm tra cài đặt thông báo của ứng dụng

### 2. Thông báo không mở đúng màn hình
- Kiểm tra Intent flags và extras
- Đảm bảo TicketDetailActivity được khai báo trong AndroidManifest.xml

### 3. Lỗi build
- Kiểm tra dependencies trong build.gradle.kts
- Đảm bảo compileSdk >= 33 cho Android 13+
- Kiểm tra imports trong các file Java

## Tương lai

### Các tính năng có thể thêm:
1. Thông báo theo lịch trình (reminder)
2. Thông báo push từ server
3. Tùy chỉnh âm thanh thông báo
4. Thông báo nhóm theo loại
5. Thống kê thông báo đã gửi 