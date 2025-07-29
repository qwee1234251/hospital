# 醫院管理系統 (Hospital Management System)


## 功能特色

- **護士管理**：新增、編輯、刪除護士資訊
- **站點管理**：管理醫院內各個工作站點
- **關聯管理**：靈活的護士與站點多對多關係分配
- **Web介面**：直觀的網頁操作介面
- **時間追蹤**：自動記錄更新時間和加入時間

## 技術架構

- **後端框架**：Spring Boot 3.x
- **資料庫**：MySQL 8.0+
- **ORM框架**：Spring Data JPA / Hibernate
- **模板引擎**：Thymeleaf
- **前端**：HTML5 + CSS + JavaScript

## 系統需求

### 軟體需求
- Java 17 或更高版本
- MySQL 8.0 或更高版本
- Maven 3.6+

### 硬體需求
- 記憶體：最少 512MB RAM
- 硬碟：最少 100MB 可用空間

## 安裝說明

### 1. 克隆專案
```bash
git clone <your-repository-url>
cd hospital-management-system
```

### 2. 設定 MySQL
確保 MySQL 服務正在運行，並記下以下資訊：
- 主機位址（預設：localhost）
- 連接埠（預設：3306）
- 使用者名稱（例如：root）
- 密碼

### 3. 配置資料庫連線
編輯 `src/main/resources/application.properties`：

```properties
spring.application.name=hospital

# 資料庫連線設定
spring.datasource.url=jdbc:mysql://localhost:3306/hospital_db?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=Asia/Taipei&characterEncoding=utf8
spring.datasource.username=your_mysql_username
spring.datasource.password=your_mysql_password

# JPA 設定
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
```

### 4. 編譯並運行專案
```bash
# 編譯專案
mvn clean compile

# 運行應用程式
mvn spring-boot:run
```

### 5. 訪問應用程式
開啟瀏覽器，訪問：`http://localhost:8080`

## 使用說明

### 護士管理
1. **新增護士**：點擊「新增護士」→ 填入員工編號和姓名 → 選擇工作站點
2. **編輯護士**：在護士列表中點擊「編輯」→ 修改資訊或重新分配站點
3. **刪除護士**：在護士列表中點擊「刪除」

### 站點管理
1. **新增站點**：點擊「新增站點」→ 輸入站點名稱
2. **編輯站點**：在站點列表中點擊「編輯」→ 修改名稱，查看分配的護士
3. **刪除站點**：在站點列表中點擊「刪除」

### 關聯分配
- 在編輯護士頁面，使用雙欄位選擇器來分配站點
- 左側顯示已加入的站點，右側顯示可用的站點
- 使用中間按鈕進行加入/移除操作

## 資料庫結構

### 主要資料表

#### nurse（護士表）
| 欄位 | 類型 | 說明 |
|------|------|------|
| employee_id | VARCHAR | 員工編號（主鍵） |
| name | VARCHAR | 護士姓名 |
| updated_at | TIMESTAMP | 最後更新時間 |

#### site（站點表）
| 欄位 | 類型 | 說明 |
|------|------|------|
| id | BIGINT | 站點ID（主鍵，自增） |
| name | VARCHAR | 站點名稱 |
| updated_at | TIMESTAMP | 最後更新時間 |

#### nurse_site（關聯表）
| 欄位 | 類型 | 說明 |
|------|------|------|
| id | BIGINT | 關聯ID（主鍵，自增） |
| nurse_id | VARCHAR | 護士ID（外鍵） |
| site_id | BIGINT | 站點ID（外鍵） |
| join_time | TIMESTAMP | 加入時間 |

## 專案結構

```
src/
├── main/
│   ├── java/com/example/hospital/
│   │   ├── HospitalApplication.java          # 主程式入口
│   │   ├── controller/                       # 控制器層
│   │   │   ├── HomeController.java
│   │   │   ├── NurseController.java
│   │   │   └── SiteController.java
│   │   ├── model/                           # 實體模型
│   │   │   ├── Nurse.java
│   │   │   ├── Site.java
│   │   │   └── NurseSite.java
│   │   ├── repository/                      # 資料存取層
│   │   │   ├── NurseRepository.java
│   │   │   ├── SiteRepository.java
│   │   │   └── NurseSiteRepository.java
│   │   └── service/                         # 業務邏輯層
│   │       ├── NurseService.java
│   │       └── SiteService.java
│   └── resources/
│       ├── application.properties           # 配置文件
│       └── templates/                       # Thymeleaf 模板
│           ├── index.html                   # 首頁
│           ├── nurse/
│           │   ├── form.html               # 護士表單
│           │   └── list.html               # 護士列表
│           └── site/
│               ├── form.html               # 站點表單
│               └── list.html               # 站點列表
```

## 常見問題

### Q: 啟動時出現資料庫連線錯誤
**A:** 檢查以下項目：
- MySQL 服務是否正在運行
- 資料庫連線資訊是否正確
- 使用者是否有足夠權限

### Q: 頁面顯示異常
**A:** 確認：
- 瀏覽器支援 JavaScript
- 清除瀏覽器快取
- 檢查控制台錯誤訊息

### Q: 資料沒有正確儲存
**A:** 檢查：
- 資料庫連線是否穩定
- 查看應用程式日誌
- 確認表單提交是否完整

## 開發說明

### 添加新功能
1. 在對應的 `model` 包中創建實體類別
2. 在 `repository` 包中創建資料存取介面
3. 在 `service` 包中實作業務邏輯
4. 在 `controller` 包中添加控制器
5. 創建對應的 Thymeleaf 模板

### 修改資料庫結構
- 修改實體類別後，重新啟動應用程式
- Hibernate 會自動更新資料表結構（因為設定了 `ddl-auto=update`）

## 貢獻指南

1. Fork 本專案
2. 創建功能分支 (`git checkout -b feature/AmazingFeature`)
3. 提交變更 (`git commit -m 'Add some AmazingFeature'`)
4. 推送到分支 (`git push origin feature/AmazingFeature`)
5. 開啟 Pull Request

## 授權條款

本專案採用 MIT 授權條款 - 查看 [LICENSE](LICENSE) 文件了解詳情。

## 聯絡資訊

- 專案維護者：[你的名字]
- Email：[你的email]
- 專案連結：[GitHub repository URL]

## 更新日誌

### v1.0.0 (2025-07-29)
- ✨ 初始版本發布
- 🏥 護士管理功能
- 🏢 站點管理功能
- 🔄 護士站點關聯管理
- 📱 Web 操作介面
