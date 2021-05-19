# fpay-sdk-android-demo v1.0

Tài liệu hướng dẫn tích hợp FPay SDK cho Android

English version see here

# Giới thiệu
FPay SDK là thư viện để các app có thể tương tác FPay. FPay SDK bao gồm các chức năng chính như sau:
- Sử dụngg FID SDK để đăng nhập vào hệ thống của FPay
- Hiện tại FPay SDK hỗ trợ tất cả các thiệt bị cài đặt hệ điều hành Android 4.1 (API 16) trở lên.

## Hỗ trợ
Nếu trong quá trình tích hợp có thắc mắc gì bạn có thể:

- Liên hệ trực tiếp qua email với team SDK để được trợ giúp
- Tham khảo demo [ở đây](https://github.com/n76i/fpay-sdk-android-demo)

## Bước 1
Tích hợp thành công FID SDK, có thể xem hướng dẫn [tại đây](https://github.com/n76i/fid-sdk-android-demo/blob/main/README-vi.md)

## Bước 2
Tải FPay SDK [tại đây](https://github.com/n76i/fpay-sdk-android-demo/raw/main/fpay-sdk/fpay-sdk-release.aar)

## Bước 3: Thêm FPay SDK vào dự án
Trong Android Studio:
- Chọn module app, nhấn chuột phải rồi chọn `New` -> `Module`
- Kéo xuống dưới chọn `Import .JAR/.AAR`-> `Next`
- Ở file name trỏ đường dẫn đến file SDK được tải ở bước 2
- Ở sub project name có thể đổi tên thành `fpay-sdk`

Chú ý nếu bạn thấy đã import thành công nhưng không sử dụng được, có thể do SDK chưa được thêm vào `app`. Bạn có thể vào `File` -> `Project Structure` để thêm SDK vào module `app`

## Bước 4: Cấu hình thêm
### 1, Cấu hình AndroidManifest.xml
Trong `AndroidManifest.xml` thêm đoạn code sau ở trong `application`:
```java
<activity android:name="ai.ftech.fpay.inappbrowser.ChromeTabsManagerActivity"/>
```
### 2, Cài thêm một số thư viện bổ sung
Bổ sung một số thư viện sau:
```
implementation 'org.greenrobot:eventbus:3.2.0'
```

Hoàn tất, để kiểm tra mọi thứ đã hoạt động chúng ta đi đến hướng dẫn sử dụng
## Hướng dẫn sử dụng cơ bản
Ở hướng dẫn này sẽ cần import những class sau:
```java
import ai.ftech.fpay.FPay;
```

### 1, Khởi tạo
Bạn cần đảm bảo khởi tạo FPay trước khi dùng những hàm khác của nó, có thể gọi ở `onCreate` của Activity (ở sau hàm khởi tạo FID)
```java
FPay.initialize(this);
```
Lưu ý FPay sẽ tự config môi trường sanbox nếu FID được cấu hình sandbox

### 2, Đăng nhập vào FPay và sử dụng các chức năng của FPay
Hiện tại FPay SDK đang hỗ trợ đăng nhập và thực hiện các thao tác nạp tiền, rút tiền thông qua website, để mở bạn sử dụng hàm sau:
```java
FPay.browserPayment(MainActivity.this);
```
