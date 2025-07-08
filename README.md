# Task Management API

API quản lý tác vụ RESTful được xây dựng bằng Kotlin và Spring Boot, cho phép người dùng quản lý các tác vụ, phân loại chúng và liên kết chúng với tài khoản người dùng cụ thể. Dự án này phục vụ như một bài tập thực hành toàn diện để củng cố các kỹ năng phát triển phụ trợ bằng cách sử dụng các công nghệ hiện đại.

-----

## ✨ Tính năng chính

Dự án này triển khai các chức năng cốt lõi sau:

### Quản lý người dùng (User Management)

* **Đăng ký người dùng mới**: Cho phép người dùng mới tạo tài khoản.
* **Đăng nhập người dùng**: Xác thực người dùng và cấp token JWT để truy cập an toàn.
* **Lấy thông tin hồ sơ người dùng hiện tại**: Truy xuất chi tiết hồ sơ của người dùng đã đăng nhập.
* **Nâng cao (Tùy chọn)**: Cập nhật thông tin hồ sơ người dùng.

### Quản lý tác vụ (Task Management)

* **Tạo tác vụ mới**: Cho phép người dùng đã đăng nhập tạo tác vụ mới.
* **Lấy danh sách tác vụ**: Truy xuất danh sách tác vụ của người dùng hiện tại với các tùy chọn phân trang, lọc và sắp xếp.
* **Lấy chi tiết tác vụ**: Xem chi tiết của một tác vụ cụ thể bằng ID.
* **Cập nhật tác vụ**: Sửa đổi các thuộc tính tác vụ như tiêu đề, mô tả hoặc trạng thái.
* **Xóa tác vụ**: Xóa tác vụ khỏi hệ thống.

### Quản lý danh mục (Category Management)

* **Tạo danh mục mới**: Cho phép tạo các danh mục tác vụ (ví dụ: "Công việc", "Cá nhân", "Học tập").
* **Lấy danh sách tất cả danh mục**: Truy xuất tất cả các danh mục có sẵn.
* **Nâng cao (Tùy chọn)**: Chỉnh sửa và xóa danh mục.

-----

## 🛠️ Công nghệ và Công cụ

* **Ngôn ngữ**: Kotlin
* **Framework**: Spring Boot 
* **Database**: MySQL 8+
* **ORM/Truy cập dữ liệu**: Spring Data JPA với Hibernate
* **Bảo mật**: Spring Security (sử dụng JWT để xác thực và ủy quyền)
* **Công cụ build**: Gradle Kotlin DSL
* **Testing**: chưa được triển khai, nhưng có thể sử dụng JUnit và MockK
* **Tài liệu API**: Springdoc OpenAPI (Swagger UI)
* **Môi trường phát triển**: IntelliJ IDEA Community/Ultimate