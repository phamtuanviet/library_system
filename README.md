# Hệ Thống Quản Lý Thư Viện

## Giới Thiệu
Ứng dụng quản lý thư viện được phát triển để giúp việc quản lý tài liệu, độc giả và hoạt động mượn trả sách được dễ dàng và hiệu quả.

## Các Tính Năng Chính

### 1. Trang Đăng Nhập (Login Page)
![Trang Đăng Nhập](/path/to/login-screenshot.png)

**Mô Tả:** 
- Giao diện đăng nhập an toàn và thân thiện
- Hỗ trợ xác thực cho các loại người dùng khác nhau:
  - Quản trị viên (Admin)
  - Nhân viên thư viện

**Chức Năng:**
- Nhập tên đăng nhập và mật khẩu
- Kiểm tra quyền truy cập

### 2. Trang Chính (Home Pane)
![Trang Chính](/path/to/home-screenshot.png)

**Mô Tả:** 
- Tổng quan nhanh về tình trạng thư viện
- Thống kê và dashboard

**Các Widget Chính:**
- Số lượng sách trong thư viện
- Số lượng sách đang được mượn
- Thống kê độc giả
- Số lượng mượn trả

### 3. Quản Lý Người Dùng (User Management Pane)
![Quản Lý Người Dùng](/path/to/user-management-screenshot.png)

**Mô Tả:**
- Quản lý toàn diện thông tin độc giả và nhân viên

**Chức Năng:**
- Thêm mới người dùng
- Chỉnh sửa thông tin cá nhân
- Theo dõi lịch sử mượn trả
- Tìm kiếm và lọc người dùng

### 4. Quản Lý Tài Liệu (Document Management Pane)
![Quản Lý Tài Liệu](/path/to/document-management-screenshot.png)

**Mô Tả:**
- Quản lý kho sách và tài liệu một cách chi tiết

**Chức Năng:**
- Nhập liệu sách mới
- Quản lý mã vạch và mã sách
- Thống kê lượng sách
- Tìm kiếm nâng cao
- Quản lý kho sách

### 5. Quản Lý Mượn Trả (Borrowing Management Pane)
![Quản Lý Mượn Trả](/path/to/borrowing-management-screenshot.png)

**Mô Tả:**
- Theo dõi và quản lý chi tiết quy trình mượn và trả sách

**Chức Năng:**
- Ghi nhận phiếu mượn sách
- Kiểm tra tình trạng sách
- Báo cáo chi tiết về hoạt động mượn trả

## Công Nghệ Sử Dụng
- Frontend: React
- Backend: Node.js
- Cơ Sở Dữ Liệu: MongoDB
- Xác Thực: JWT
- Giao Diện: Tailwind CSS

## Cài Đặt

### Yêu Cầu
- Node.js v18+
- MongoDB v5+

### Bước Cài Đặt
```bash
# Cài đặt dependencies
npm install

# Khởi chạy môi trường phát triển
npm run dev
```

## Tác Giả
[VTV]

## Giấy Phép
MIT License
